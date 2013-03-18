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

import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.marc.constants.MarcSubfieldConstants;

public class MarcSubfield {
    
    private char code;
    private String data;
    private int subfieldIndex;
    
    private MarcSubfield() {}
    
    public MarcSubfield(char code, String data) {
        this.code = code;
        this.data = data;
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
    
    public String getDataUnpunctuated() {
        return MarcWorxDataHelper.stripStandardPunctuation(getData());
    }
}
