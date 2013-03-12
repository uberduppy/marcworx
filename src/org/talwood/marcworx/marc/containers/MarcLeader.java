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
package org.talwood.marcworx.marc.containers;

import java.text.DecimalFormat;
import org.talwood.marcworx.marc.constants.MarcLeaderConstants;
import org.talwood.marcworx.marc.enums.DataType;
import org.talwood.marcworx.marc.enums.FormatType;
import org.talwood.marcworx.marc.enums.RecordStatus;
import org.talwood.marcworx.marc.enums.RecordType;

public class MarcLeader {
    private String originalLeaderData;

    private char position05;
    private char position06;
    private char position07;
    private char position08;
    private char position09;
    private char position10;
    private char position11;
    private char position17;
    private char position18;
    private char position19;
    private char position20;
    private char position21;
    private char position22;
    private char position23;
    
    private int recordLength;
    private int baseAddressOfData;
    
    private RecordType recordType = RecordType.RECORD_TYPE_UNKNOWN;
    
    private MarcLeader() {}
    
    public MarcLeader(RecordType recordType) {
        this.recordType = recordType;
        populateLeader(recordType);
    }

    public MarcLeader(String data) {
        assignData(data);
        recordType = RecordType.findByRecordTypeCode(position05, RecordType.RECORD_TYPE_UNKNOWN);
    }
    
    private void populateLeader(RecordType recordType) {
        String leaderData = "00000" + RecordStatus.NEW.getCode() +
                recordType.getCode() +
                DataType.UNKNOWN.getCode() +
                "  2200000   4500";
        
        assignData(leaderData);
    }

    private void assignData(String leaderData) {
        setOriginalLeaderData(leaderData);
        setRecordLength(Integer.parseInt(leaderData.substring(0, 5)));
        setPosition05(leaderData.charAt(5));
        setPosition06(leaderData.charAt(6));
        setPosition07(leaderData.charAt(7));
        setPosition08(leaderData.charAt(8));
        setPosition09(leaderData.charAt(9));
        setPosition10(leaderData.charAt(10));
        setPosition11(leaderData.charAt(11));
        setBaseAddressOfData(Integer.parseInt(leaderData.substring(12, 17)));
        setPosition17(leaderData.charAt(17));
        setPosition18(leaderData.charAt(18));
        setPosition19(leaderData.charAt(19));
        setPosition20(leaderData.charAt(20));
        setPosition21(leaderData.charAt(21));
        setPosition22(leaderData.charAt(22));
        setPosition23(leaderData.charAt(23));
    }

    public String getOriginalLeaderData() {
        return originalLeaderData;
    }

    public void setOriginalLeaderData(String originalLeaderData) {
        this.originalLeaderData = originalLeaderData;
    }

    public char getPosition05() {
        return position05;
    }

    public void setPosition05(char position05) {
        this.position05 = position05;
    }

    public char getPosition06() {
        return position06;
    }

    public void setPosition06(char position06) {
        this.position06 = position06;
    }

    public char getPosition07() {
        return position07;
    }

    public void setPosition07(char position07) {
        this.position07 = position07;
    }

    public char getPosition08() {
        return position08;
    }

    public void setPosition08(char position08) {
        this.position08 = position08;
    }

    public char getPosition09() {
        return position09;
    }

    public void setPosition09(char position09) {
        this.position09 = position09;
    }

    public char getPosition10() {
        return position10;
    }

    public void setPosition10(char position10) {
        this.position10 = position10;
    }

    public char getPosition11() {
        return position11;
    }

    public void setPosition11(char position11) {
        this.position11 = position11;
    }

    public char getPosition17() {
        return position17;
    }

    public void setPosition17(char position17) {
        this.position17 = position17;
    }

    public char getPosition18() {
        return position18;
    }

    public void setPosition18(char position18) {
        this.position18 = position18;
    }

    public char getPosition19() {
        return position19;
    }

    public void setPosition19(char position19) {
        this.position19 = position19;
    }

    public char getPosition20() {
        return position20;
    }

    public void setPosition20(char position20) {
        this.position20 = position20;
    }

    public char getPosition21() {
        return position21;
    }

    public void setPosition21(char position21) {
        this.position21 = position21;
    }

    public char getPosition22() {
        return position22;
    }

    public void setPosition22(char position22) {
        this.position22 = position22;
    }

    public char getPosition23() {
        return position23;
    }

    public void setPosition23(char position23) {
        this.position23 = position23;
    }

    public int getRecordLength() {
        return recordLength;
    }

    public void setRecordLength(int recordLength) {
        this.recordLength = recordLength;
    }

    public int getBaseAddressOfData() {
        return baseAddressOfData;
    }

    public void setBaseAddressOfData(int baseAddressOfData) {
        this.baseAddressOfData = baseAddressOfData;
    }

    public RecordType getRecordTypeEnum() {
        return recordType;
    }
    
    public FormatType getFormatTypeEnum() {
        return recordType.getFormatType();
    }
    
    public String getCurrentLeaderData() {
        DecimalFormat formatStart = new DecimalFormat("00000");
        StringBuilder result = new StringBuilder();
        result.append(formatStart.format(getRecordLength()));
        result.append(getPosition05());
        result.append(getPosition06());
        result.append(getPosition07());
        result.append(getPosition08());
        result.append(getPosition09());
        result.append(getPosition10());
        result.append(getPosition11());
        result.append(formatStart.format(getBaseAddressOfData()));
        result.append(getPosition17());
        result.append(getPosition18());
        result.append(getPosition19());
        result.append(getPosition20());
        result.append(getPosition21());
        result.append(getPosition22());
        result.append(getPosition23());
        return result.toString();
    }

    public char getRecordStatus() {
        return position05;
    }
    public void setRecordStatus(char recordStatus) {
        position05 = recordStatus;
    }
    
    public char getRecordType() {
        return position06;
    }
    public void setRecordtype(char recordType) {
        position06 = recordType;
    }
    
    public char getCharacterCodingScheme() {
        return position09;
    }
    public void setCharacterCodingScheme(char characterCodingScheme) {
        position09 = characterCodingScheme;
    }
    
    public char getIndicatorCount() {
        return position10;
    }
    public void setIndicatorCount(char indicatorCount) {
        position10 = indicatorCount;
    }
    
    public char getSubfieldCodeCount() {
        return position11;
    }
    public void setSubfieldCodeCount(char subfieldCodeCount) {
        position11 = subfieldCodeCount;
    }
    
    public boolean isUTF8() {
        return (getCharacterCodingScheme() == MarcLeaderConstants.CHARACTER_CODING_SCHEME_UTF8);
    }
    
}
