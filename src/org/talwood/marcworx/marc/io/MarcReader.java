/* MarcWorx MARC Library - Utilities for manipulation of MARC records
    Copyright (C) 2013  Todd Walker, Talwood Solutions

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.talwood.marcworx.marc.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.talwood.marcworx.marc.constants.MarcReaderConstants;
import org.talwood.marcworx.marc.containers.MarcLeader;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.marc.enums.MarcFileReadStatus;
import org.talwood.marcworx.marc.enums.RecordType;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.exception.MarcExceptionType;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxFileHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;

public class MarcReader {

    private byte[] buffer;
    private int bufferPosition;
    private int dataLength;
    private long fileSize;
    private long filePosition;
    private int objectStatus;
    private MarcFileReadStatus readStatus = MarcFileReadStatus.VALID;
    private RandomAccessFile fileIn = null;
    private File inputFile = null;
    private int badCommFormatTags = 0;
    private int recordsWithBadTags = 0;
    private int recordCount = 0;
    private int displayableRecordCount = 0;
    private int recordNumber = 0;
    private int lastRecordLength = 0;
    private long[] offsets = new long[MarcReaderConstants.INCREMENT_SIZE];
    
    public MarcReader(File file) throws MarcException {
        inputFile = file;
        initializeFileData();
        readDataFromFile();
    }
    
    private void initializeFileData() throws MarcException {
        // 100000 bytes is enough
        buffer = new byte[MarcReaderConstants.BUFFER_SIZE];
        bufferPosition = 0;
        filePosition = 0;
        objectStatus = 0;
        fileSize = inputFile.length();
        if(fileIn != null) {
            try {
                fileIn.close();
            } catch (IOException ex) {
                throw new MarcException(MarcExceptionType.INPUT_FILE_NOT_FOUND, "Unable to open MARC file", ex);
            }
        }
        try {
            fileIn = new RandomAccessFile(inputFile, "r");
        } catch (FileNotFoundException ex) {
            throw new MarcException(MarcExceptionType.INPUT_FILE_NOT_FOUND, "Unable to open MARC file", ex);
        }

    }

    private MarcFileReadStatus packBuffer(long uTestSize) throws MarcException {

        if (bufferPosition < dataLength) {
            // Is there at least testsize bytes left ?
            if (uTestSize <= dataLength - bufferPosition) {
                return MarcFileReadStatus.VALID;
            }
        }
        if (readStatus == MarcFileReadStatus.ALLDONE) {
            // If not, is file exhausted?
            if (bufferPosition == dataLength) {
                return MarcFileReadStatus.ALLDONE;
            } else {
                return MarcFileReadStatus.ERROR;
            }
        }

        if (readStatus == MarcFileReadStatus.ERROR) {
            return MarcFileReadStatus.ERROR;
        }

        if(bufferPosition > 0 && !isEOF()) {
            // Move things over
            byte[] interim = buffer;
            buffer = new byte[MarcReaderConstants.BUFFER_SIZE];
            System.arraycopy(interim, bufferPosition, buffer, 0, dataLength - bufferPosition);
            dataLength -= bufferPosition;
            bufferPosition = 0;
        }

        // And get more data
        readDataFromFile();
        if (readStatus == MarcFileReadStatus.ERROR) {
            return MarcFileReadStatus.ERROR;
        }
        return MarcFileReadStatus.VALID;
    }
    
    private void readDataFromFile() throws MarcException {
        if(fileIn != null) {
            try {
                int newDataLength = fileIn.read(buffer, dataLength, MarcReaderConstants.BUFFER_SIZE - dataLength);
                if(newDataLength == -1) {
                    readStatus = MarcFileReadStatus.ALLDONE;
                } else {
                    dataLength += newDataLength;
                    filePosition += newDataLength;
                }
            } catch (IOException ex) {
                throw new MarcException(MarcExceptionType.INPUT_FILE_NOT_READ, "Unable to read MARC file", ex);
            }
        }
    }

    private MarcFileReadStatus checkCommFormat() throws MarcException {
        if (fileSize == filePosition && bufferPosition == dataLength) {
            return MarcFileReadStatus.ALLDONE;
        }
        MarcFileReadStatus eStatus = packBuffer(MarcReaderConstants.MINIMUM_RECORD);
        if(eStatus != MarcFileReadStatus.VALID) {
            return eStatus;
        }
        boolean t_goodRecordFound = false;
        do {
            int i_index;
            for (i_index = 0; i_index < 5; i_index++) {
                if (!(Character.isDigit(buffer[bufferPosition + i_index]))) {
                    break;
                }
            }
            if (!(i_index == 5
                    && ((buffer[bufferPosition + 10] == '2' && buffer[bufferPosition + 11] == '2')
                    || (buffer[bufferPosition + 10] == '1' && buffer[bufferPosition + 11] == '1'))
                    && (buffer[bufferPosition + 8] == ' ' || buffer[bufferPosition + 8] == 'a')
                    && (buffer[bufferPosition + 9] == ' ' || buffer[bufferPosition + 9] == 'a')
                    && buffer[bufferPosition + 20] == '4' && buffer[bufferPosition + 21] == '5')) {
                bufferPosition++;
                eStatus = packBuffer(MarcReaderConstants.MINIMUM_RECORD);

                if (eStatus != MarcFileReadStatus.VALID) {
                    return eStatus;
                }
            } else {
                t_goodRecordFound = true;
            }
        } while (t_goodRecordFound == false);
        return eStatus;
    }

    private MarcRecord translateCommFormatFromBytes(byte[] workBuffer, int workPosition, int recLength) throws MarcException {
        MarcFileReadStatus status = MarcFileReadStatus.VALID;
        // Get record length.
        for (int index = 0; index < 5; index++) {
            if (!(Character.isDigit(workBuffer[workPosition + index])
                    && Character.isDigit(workBuffer[workPosition + index + 12]))) {
                status = MarcFileReadStatus.ERROR;
                break;
            }
        }
        String recordLengthBuffer = new String(workBuffer, workPosition, 5);
        String baseAddressBuffer = new String(workBuffer, workPosition + 12, 5);
        int u_recordLength = Integer.parseInt(recordLengthBuffer);
        int u_recordDataStart = Integer.parseInt(baseAddressBuffer);
        if (u_recordLength > recLength) {
            status = MarcFileReadStatus.ERROR;
        }
        if(status == MarcFileReadStatus.ERROR) {
            MarcRecord recordx = MarcWorxDataHelper.createMarcRecord(RecordType.BIBLIOGRAPHIC_BOOK);
            recordx.setReadStatus(status);
            return recordx;
        }
        MarcLeader leader = MarcWorxDataHelper.createLeader(new String(workBuffer, workPosition, 24));
        MarcRecord record = MarcWorxDataHelper.createMarcRecord(leader);
        
        boolean badTagFound = false;
        for (int i_index2 = 0; i_index2 < u_recordDataStart - 25; i_index2 += 12) {
            String tagNumberBuffer = new String(workBuffer, workPosition + 24 + i_index2, 3);
            String tagLengthBuffer = new String(workBuffer, workPosition + 27 + i_index2, 4);
            String tagOffsetBuffer = new String(workBuffer, workPosition + 31 + i_index2, 5);
            int n_tagNumber = Integer.parseInt(tagNumberBuffer);
            int n_tagLength = Integer.parseInt(tagLengthBuffer);
            int n_tagOffset = Integer.parseInt(tagOffsetBuffer);
            if (n_tagNumber > 0 && n_tagNumber <= 999 && n_tagLength + n_tagOffset < recLength) {
                String tagBuffer;
                if(leader.isUTF8()) {
                    tagBuffer = MarcWorxDataHelper.convertUTF8ToUnicode(workBuffer, workPosition + u_recordDataStart + n_tagOffset, n_tagLength);
                } else {
                    tagBuffer = MarcWorxDataHelper.convertMARCToUnicode(workBuffer, workPosition + u_recordDataStart + n_tagOffset, n_tagLength);
                }
                while (tagBuffer.length() > 0 && (tagBuffer.charAt(tagBuffer.length() - 1) == MarcReaderConstants.END_OF_TAG || tagBuffer.charAt(tagBuffer.length() - 1) == MarcReaderConstants.END_OF_RECORD)) {
                    tagBuffer = MarcWorxStringHelper.removeChars(tagBuffer, tagBuffer.length() - 1, 1);
                }
                record.addOrUpdateTag(MarcWorxDataHelper.cloneTag(new MarcTag(n_tagNumber, tagBuffer)));
            } else {
                badCommFormatTags++;
                badTagFound = true;
            }
        }
        if (badTagFound == true) {
            recordsWithBadTags++;
        }
        
        
        return record;
    }
    
//    private MarcRecord translateCommFormat(String rxData) throws MarcException {
//        MarcFileReadStatus status = MarcFileReadStatus.VALID;
//        for (short i_index = 0; i_index < 5; i_index++) {
//            if (!(Character.isDigit(rxData.charAt(i_index))
//                    && Character.isDigit(rxData.charAt(i_index + 12)))) {
//                status = MarcFileReadStatus.ERROR;
//                break;
//            }
//        }
//        String recordLengthBuffer = rxData.substring(0, 5);
//        String baseAddressBuffer = rxData.substring(12, 17);
//        int u_recordLength = Integer.parseInt(recordLengthBuffer);
//        int u_recordDataStart = Integer.parseInt(baseAddressBuffer);
//        if (u_recordLength > rxData.length()) {
//            status = MarcFileReadStatus.ERROR;
//        }
//        
//        if(status == MarcFileReadStatus.ERROR) {
//            MarcRecord recordx = MarcWorxDataHelper.createMarcRecord(RecordType.BIBLIOGRAPHIC_BOOK);
//            recordx.setReadStatus(status);
//            return recordx;
//        }
//        String tagNumberBuffer;
//        String tagLengthBuffer;
//        String tagOffsetBuffer;
//        String tagBuffer;
//        MarcLeader leader = MarcWorxDataHelper.createLeader(ObjectHelper.substring(rxData, 0, 24));
//        MarcRecord record = MarcWorxDataHelper.createMarcRecord(leader);
////        MarcLeader leader = new MarcLeader(StringHelper.substring(rxData, 0, 24));
//        String directory = ObjectHelper.substring(rxData, 24, u_recordDataStart - 25);
//        boolean t_badTagFound = false;
//        for (int i_index2 = 0; i_index2 < u_recordDataStart - 25; i_index2 += 12) {
//            tagNumberBuffer = ObjectHelper.substring(rxData, 24 + i_index2, 3);
//            tagLengthBuffer = ObjectHelper.substring(rxData, 27 + i_index2, 4);
//            tagOffsetBuffer = ObjectHelper.substring(rxData, 31 + i_index2, 5);
//            int n_tagNumber = Integer.parseInt(tagNumberBuffer);
//            int n_tagLength = Integer.parseInt(tagLengthBuffer);
//            int n_tagOffset = Integer.parseInt(tagOffsetBuffer);
//            if (n_tagNumber > 0 && n_tagNumber <= 999 && n_tagLength + n_tagOffset < rxData.length()) {
//                tagBuffer = ObjectHelper.substring(rxData, u_recordDataStart + n_tagOffset, n_tagLength);
//                while (tagBuffer.length() > 0 && (tagBuffer.charAt(tagBuffer.length() - 1) == MarcReaderConstants.END_OF_TAG || tagBuffer.charAt(tagBuffer.length() - 1) == MarcReaderConstants.END_OF_RECORD)) {
//                    tagBuffer = ObjectHelper.removeChars(tagBuffer, tagBuffer.length() - 1, 1);
//                }
//                record.addOrUpdateTag(MarcWorxDataHelper.cloneTag(new MarcTag(n_tagNumber, tagBuffer)));
//            } else {
//                badCommFormatTags++;
//                t_badTagFound = true;
//            }
//        }
//        if (t_badTagFound == true) {
//            recordsWithBadTags++;
//        }
//        return record;
//    }

    public MarcRecord getPreviousRecord() throws MarcException {
        return getNthRecord(getRecordNumber() - 1);
    }

    private void moveToPosition(int recordNumberToMoveTo) throws MarcException {
        int maxOffset = recordNumberToMoveTo / MarcReaderConstants.INCREMENT_COUNT;
        int newRecordNumber = 0;
        long newFileOffset = 0;
        for(int x = 1; x <= maxOffset && x < MarcReaderConstants.INCREMENT_SIZE; x++) {
            if(offsets[x] > 0) {
                newRecordNumber = (x * MarcReaderConstants.INCREMENT_COUNT) - 1;
                newFileOffset = offsets[x];
            }
        }
        // Jump to the proper offset;
        seekToOffset(newFileOffset);
        bufferPosition = 0;
        recordNumber = newRecordNumber;
        packBuffer(MarcReaderConstants.MINIMUM_RECORD);

    }

    public MarcRecord getNthRecord(int newRecordNumber) throws MarcException {
        MarcRecord rec = MarcWorxDataHelper.createMarcRecord(RecordType.BIBLIOGRAPHIC_BOOK);
        moveToPosition(newRecordNumber);
        while(recordNumber < newRecordNumber) {
            rec = getNextRecord();
            if(rec.getReadStatus() != MarcFileReadStatus.VALID) {
                break;
            }
        }
        return rec;
    }

    public MarcRecord getNextRecord() throws MarcException {
        MarcRecord rec = MarcWorxDataHelper.createMarcRecord(RecordType.BIBLIOGRAPHIC_BOOK);
        if (objectStatus != 0) {
            rec.setReadStatus(MarcFileReadStatus.ERROR);
            return rec;
        }
        
        MarcFileReadStatus eStatus = packBuffer(MarcReaderConstants.MINIMUM_RECORD);
        if (eStatus != MarcFileReadStatus.VALID) {
            if (filePosition == fileSize && bufferPosition == dataLength) {
                eStatus = MarcFileReadStatus.ALLDONE;
            }
            rec.setReadStatus(eStatus);
            return rec;
        }
        int u_recordLength = 0;
        eStatus = checkCommFormat();
        if(eStatus != MarcFileReadStatus.VALID) {
            rec.setReadStatus(eStatus);
            return rec;
        }
        String commFormatLength = new String(buffer, bufferPosition, 5);
        try {
            u_recordLength = Integer.parseInt(commFormatLength);
        } catch (NumberFormatException ex) {
            throw new MarcException(MarcExceptionType.NUMBER_FORMAT, "Unable to determine length of USMArc record", ex);
        }
        eStatus = packBuffer(u_recordLength);
        if(eStatus != MarcFileReadStatus.VALID) {
            rec.setReadStatus(eStatus);
            return rec;
        }
//        String commFormatRecord = new String(buffer, bufferPosition, u_recordLength);
        rec = translateCommFormatFromBytes(buffer, bufferPosition, u_recordLength);
        if(rec.getReadStatus() == MarcFileReadStatus.ERROR) {
            return rec;
        }

        bufferPosition += u_recordLength;
        recordNumber++;
        return rec;
    }

    private void incrementAndFillArray(int u_reclen) {
        ++recordCount;
        if (recordCount % MarcReaderConstants.INCREMENT_COUNT == 0 && recordCount < 4095750L) {
            int u_arrayCount = recordCount / MarcReaderConstants.INCREMENT_COUNT;
            if (offsets != null && u_arrayCount < MarcReaderConstants.INCREMENT_SIZE) {
                offsets[u_arrayCount] = getReadFilePosition() - u_reclen;
            }
        }
    }

    public MarcFileReadStatus fastFileScan() throws MarcException {
        MarcFileReadStatus e_status;
        // Does mpacBuffer contain enough bytes to determine reclen?
        e_status = packBuffer(MarcReaderConstants.MINIMUM_RECORD);
        if (e_status != MarcFileReadStatus.VALID) {
            if (filePosition == fileSize && bufferPosition == dataLength) {
                e_status = MarcFileReadStatus.ALLDONE;
            }
            return e_status;
        }
        int u_recordLength;
        e_status = checkCommFormat();
        if (e_status != MarcFileReadStatus.VALID) {
            return e_status;
        }
        String commFormatLength = new String(buffer, bufferPosition, 5);
        try {
            u_recordLength = Integer.parseInt(commFormatLength);
        } catch (NumberFormatException ex) {
            throw new MarcException(MarcExceptionType.NUMBER_FORMAT, "Unable to determine length of USMArc record", ex);
        }
        // Does file end before record?
        e_status = packBuffer(u_recordLength);
        if (e_status != MarcFileReadStatus.VALID) {
            return e_status;
        }
        bufferPosition += u_recordLength;
        incrementAndFillArray(u_recordLength);
        return MarcFileReadStatus.VALID;
    }
    
    private long getReadFilePosition() {
        return (filePosition - ((dataLength) - (bufferPosition)));
    }

    public int getRecordCount() {
        return recordCount;
    }


    private void seekToOffset(long dOffset) throws MarcException {
    if (dOffset > fileSize) {
        throw new MarcException(MarcExceptionType.UNABLE_TO_FIND_OFFSET);
    }
        try {
            fileIn.seek(dOffset);
        } catch (IOException ex) {
            throw new MarcException(MarcExceptionType.UNABLE_TO_FIND_OFFSET);
        }
    bufferPosition = 0;
    dataLength = 0;
    filePosition = dOffset;
    readDataFromFile();
}

    public boolean isEOF() {
        return(readStatus == MarcFileReadStatus.ALLDONE);
    }

    public void close() {
        if(fileIn != null) {
            MarcWorxFileHelper.closeNoThrow(fileIn);
            fileIn = null;
        }
    }

    public File getInputFile() {
        return inputFile;
    }

    public int getRecordNumber() {
        return recordNumber;
    }

    public int getDisplayableRecordCount() {
        return displayableRecordCount;
    }

    public void setDisplayableRecordCount(int displayableRecordCount) {
        this.displayableRecordCount = displayableRecordCount;
    }

    public int calculatePercentComplete() {
        int value = (int)((filePosition + bufferPosition) / (fileSize / 100));
        return value;
    }

    public int getBadCommFormatTags() {
        return badCommFormatTags;
    }

    public int getRecordsWithBadTags() {
        return recordsWithBadTags;
    }

    
    
}
