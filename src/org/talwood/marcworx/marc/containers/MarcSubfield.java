package org.talwood.marcworx.marc.containers;

import org.talwood.marcworx.marc.constants.MarcSubfieldConstants;

public class MarcSubfield {
    private char code;
    private String data;
    private int subfieldIndex;
    
    private MarcSubfield() {}
    
    public MarcSubfield(char code, String data) {
        this.code = code;
        this.data = data;
        this.subfieldIndex = MarcSubfieldConstants.UNKNOWN_SUBFIELD_INDEX;
    }

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSubfieldIndex() {
        return subfieldIndex;
    }

    public void setSubfieldIndex(int subfieldIndex) {
        this.subfieldIndex = subfieldIndex;
    }
    
    public boolean isIndexed() {
        return subfieldIndex != MarcSubfieldConstants.UNKNOWN_SUBFIELD_INDEX;
    }
    
}
