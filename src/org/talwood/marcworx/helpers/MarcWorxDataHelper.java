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
package org.talwood.marcworx.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import org.marc4j.util.AnselToUnicode;
import org.marc4j.util.UnicodeToAnsel;
import org.talwood.marcworx.marc.constants.MarcLeaderConstants;
import org.talwood.marcworx.marc.constants.MarcRecordConstants;
import org.talwood.marcworx.marc.containers.MarcLeader;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.marc.enums.RecordType;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.exception.MarcExceptionType;
import org.talwood.marcworx.xmlgen.XMLBuilder;
import org.talwood.marcworx.xmlgen.XMLEntry;

public class MarcWorxDataHelper {
    // Static implementations of Helper methods.
    
    public static MarcSubfield cloneSubfield(MarcSubfield subfield) {
        MarcSubfield newSubfield = new MarcSubfield(subfield.getCode(), subfield.getData());
        return newSubfield;
    }

    public static MarcTag cloneTag(MarcTag tag) {
        MarcTag newTag = new MarcTag(tag.getTagNumber(), tag.getCurrentTagData());

        return newTag;
    }

    public static MarcRecord createMarcRecord(MarcLeader leader) throws MarcException {
        MarcRecord baseRecord = createMarcRecord(leader.getRecordTypeEnum());
        baseRecord.setLeader(leader);
        return baseRecord;
    }
    public static MarcRecord createMarcRecord(RecordType rt) throws MarcException {
        MarcRecord baseRecord = new MarcRecord(rt);
        return baseRecord;
    }
    
    public static MarcLeader createLeader(RecordType recordType) throws MarcException {
        MarcLeader leader = new MarcLeader(recordType);
        return leader;
    }
    
    public static MarcLeader createLeader(String leaderData) throws MarcException {
        if(MarcWorxStringHelper.getLength(leaderData) != MarcLeaderConstants.LEADER_LENGTH) {
            throw new MarcException(MarcExceptionType.INVALID_LEADER);
        }
        MarcLeader leader = new MarcLeader(leaderData);
        return leader;
    }
    
    public static MarcLeader cloneLeader(MarcLeader leader) throws MarcException {
        
        MarcLeader newLeader = new MarcLeader(leader.getCurrentLeaderData());
        return newLeader;
    }

    public static byte[] convertMARCToExportFormat(MarcRecord record) throws IOException {
        DecimalFormat formatTagNum = new DecimalFormat("000");
        DecimalFormat formatLength = new DecimalFormat("0000");
        DecimalFormat formatStart = new DecimalFormat("00000");

        int offset = 0;
        int tagCount = 0;
        int maxSize = 99999;
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        ByteArrayOutputStream dirs = new ByteArrayOutputStream();


        for(MarcTag item : record.getTags()) {
            byte[] chunk = null;
            if( item.getTagNumber() < 10) {
                // Process Control (Fixed) tags
                int questionMarks = MarcWorxStringHelper.countNumberOfOccurances(item.getCurrentTagData(), "?");
                chunk = convertToCharacterEncoding(record.getLeader().getPosition09(), item.getCurrentTagData());
                int questionMarksInResult = 0;
                for ( int x = 0; x < chunk.length; x++ ) {
                    if ( chunk[x] == '?') {
                        questionMarksInResult++;
                    }
                }
            } else { // Tags > 10
                chunk = convertTagToExportData(item, record.getLeader().getPosition09());
            }
            if (chunk != null &&  (chunk.length + data.size() + dirs.size()) <= maxSize - MarcRecordConstants.CONST_USMARC_LEADER_SIZE) {
                data.write(chunk);
                data.write(MarcRecordConstants.CHAR_FIELD_TERMINATOR);

                dirs.write(formatTagNum.format(item.getTagNumber()).getBytes());
                dirs.write(formatLength.format(chunk.length+1).getBytes());
                dirs.write(formatStart.format(offset).getBytes());
                offset += chunk.length + 1;
                tagCount++;
            }
        }
        // At this point, we have objects containing a base MARC record with no copies.
        ByteArrayOutputStream recordData = new ByteArrayOutputStream();
        ByteArrayOutputStream recordDirs = new ByteArrayOutputStream();

        recordData.write(data.toByteArray());
        recordDirs.write(dirs.toByteArray());
        int recordOffset = offset;
        int recordTagCount = tagCount;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        MarcLeader l = record.getLeader();
        l.setBaseAddressOfData(MarcRecordConstants.CONST_USMARC_LEADER_SIZE + (recordTagCount * MarcRecordConstants.CONST_USMARC_DIRECTORY_ENTRY_SIZE) + 1);
        l.setRecordLength(MarcRecordConstants.CONST_USMARC_LEADER_SIZE + recordOffset + (recordTagCount * MarcRecordConstants.CONST_USMARC_DIRECTORY_ENTRY_SIZE) + 1 + 1);

        baos.write(l.getCurrentLeaderData().getBytes());
        recordDirs.writeTo(baos); // .writeTo(dirs);
        baos.write(MarcRecordConstants.CHAR_FIELD_TERMINATOR);
        recordData.writeTo(baos);
        baos.write(MarcRecordConstants.CHAR_RECORD_TERMINATOR);
        return baos.toByteArray();
    }

    public static byte[] convertToCharacterEncoding(char encoding, String tagData) {

        byte[] retVal = null;

        if(encoding == MarcRecordConstants.CHARACTER_CODING_SCHEME_MARC_8) {
            retVal = UnicodeToAnsel.convert(tagData).getBytes();
        } else {
            try {
                retVal = tagData.getBytes("UTF-8");
            } catch (UnsupportedEncodingException ex) {
                // Should never get here since we have hard-coded a valid value for the encoding.
            }
        }
        return retVal;
    }

    public static byte[] convertTagToExportData(MarcTag tag, char encoding) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Write out the tag indicators.
        baos.write(tag.getFirstIndicator());
        baos.write(tag.getSecondIndicator());

        List<MarcSubfield> subs = tag.getSubfields();
        Iterator<MarcSubfield> iter = subs.iterator();

        while ((iter.hasNext()) &&  baos.size() <= (MarcRecordConstants.TAG_MAX_SIZE - 3) ) {  //9998 is max without EndOfTag - 3 min for next subfield (2 Ind + 1 Data)
            MarcSubfield sub = iter.next();
            if (sub.getData() != null) {
                baos.write(0x1f);
                baos.write(sub.getCode());

                if (encoding == MarcRecordConstants.CHARACTER_CODING_SCHEME_MARC_8) {
                    String s = sub.getData();
                    String s2 = UnicodeToAnsel.convert(s);
                    baos.write(s2.getBytes());
                } else {
                    baos.write(sub.getData().getBytes("UTF-8"));
                }
            }
        }
        byte[] retVal = baos.toByteArray();
        if(baos.size() > MarcRecordConstants.TAG_MAX_SIZE) {
            retVal = new byte[MarcRecordConstants.TAG_MAX_SIZE];
            System.arraycopy(baos.toByteArray(), 0, retVal, 0, MarcRecordConstants.TAG_MAX_SIZE);
        }
        return retVal;
    }
    
    public static String convertUTF8ToUnicode(byte[] utfBytes, int offset, int length) {
        String unicode = null;
        
        try {
            unicode = new String(utfBytes, offset, length, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // we are passing in known good UTF-8, so this should never happen...
        }
        return unicode;
    }
    
/*----------------------------------------------------------------------------*/
    public static String convertMARCToUnicode(byte[] marcData, int offset, int length) {
        String unicode = null;
        
        try {
            unicode = AnselToUnicode.convert(new String(marcData, offset, length, "windows-1252"));
        } catch (UnsupportedEncodingException ex) {
            // we are passing in known good UTF-8, so this should never happen...
        }
        return unicode;
    }

    private static void addSubfieldToMarcXml(XMLEntry parent, MarcSubfield subfield) {
        XMLEntry entry = parent.createEntry("marc:subfield");
        entry.setAttribute("code", subfield.getCode());
        entry.setData(subfield.getData());
    }
    
    private static void addFixedTagToMarcXml(XMLEntry parent, MarcTag tag) {
        XMLEntry entry = parent.createEntry("marc:controlfield");
        entry.setAttribute("tag", getFormattedTagNumber(tag.getTagNumber()));
        entry.setData(tag.getTagData());
    }
    
    private static void addTagToMarcXml(XMLEntry parent, MarcTag tag) {
        XMLEntry entry = parent.createEntry("marc:datafield");
        entry.setAttribute("tag", getFormattedTagNumber(tag.getTagNumber()));
        entry.setAttribute("ind1", tag.getFirstIndicator());
        entry.setAttribute("ind2", tag.getSecondIndicator());
        for(MarcSubfield sub : tag.getSubfields()) {
            addSubfieldToMarcXml(entry, sub);
        }
    }
    private static void addLeaderToMarcXml(XMLEntry entry, MarcLeader leader) {
        entry.setParameter("marc:leader", leader.getCurrentLeaderData());
    }

    public static String convertMarcRecordToMarcXml(MarcRecord record) {
        XMLBuilder builder = new XMLBuilder("marc:collection", MarcWorxFileHelper.ENCODING_UTF8);
        builder.setAttribute("xmlns:marc", "http://www.loc.gov/MARC21/slim");
        builder.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        builder.setAttribute("xsi:schemaLocation", "http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd");
        
        XMLEntry entry = builder.createEntry("marc:record");
        populateMarcRecordToMarcXmlEntry(entry, record);
        return builder.toXML();
    }
    
    private static void populateMarcRecordToMarcXmlEntry(XMLEntry entry, MarcRecord record) {
        addLeaderToMarcXml(entry, record.getLeader());
        for(MarcTag tag : record.getTags()) {
            if(tag.getTagNumber() < 10) {
                addFixedTagToMarcXml(entry, tag);
            } else {
                addTagToMarcXml(entry, tag);
            }
        }
    }
    
    public static XMLEntry convertSingletonMarcRecordToMarcXmlEntry(MarcRecord record) {
        XMLEntry entry = XMLEntry.createMainEntry("marc:record");
        populateMarcRecordToMarcXmlEntry(entry, record);
        return entry;
    }
    
    public String buildXmlFooter() {
//</marc:collection>        
        return "</marc:collection>\r\n";
    }
    public String buildXmlHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        sb.append("<marc:collection xmlns:marc=\"http://www.loc.gov/MARC21/slim\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.loc.gov/MARC21/slim http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd\">\r\n");
        return sb.toString();
    }
    
    
    public static String getFormattedTagNumber(int tagNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(tagNumber);
        while(sb.length() < 3) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }
    
}
