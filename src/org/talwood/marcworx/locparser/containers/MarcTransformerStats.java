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
package org.talwood.marcworx.locparser.containers;

import org.talwood.marcworx.locparser.elements.CodeElement;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.locparser.CodeTableParser;
import org.talwood.marcworx.locparser.constants.MarcTransformerSpecs;
import org.talwood.marcworx.locparser.helpers.MarcTransformerHelper;

public class MarcTransformerStats {
    private CodeTableParser parser = null;
   
    private int workingSet;
    private int workingOffset;
    
    public MarcTransformerStats() throws ConstraintException {
        this.workingOffset = 0;
        this.parser = CodeTableParser.getCodeTableParser();
    }
    
    public MarcTransformerStats(boolean multibyte, int startingOffset) throws ConstraintException {
        this.workingOffset = startingOffset;
        this.parser = CodeTableParser.getCodeTableParser();
    }

    public char determineEastAsianCodeElement(char checkData1, char checkData2, char checkData3) {
        // Compose a String with these three pieces
        char result = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(checkData1));
        sb.append(Integer.toHexString(checkData2));
        sb.append(Integer.toHexString(checkData3));
        int checkData = MarcTransformerHelper.convertCodeToInteger(sb.toString());
        CodeElement ce = getWorkingMap().findByID(checkData);
        if(ce != null) {
            ce.getUcs();
            result = (char)Integer.parseInt(ce.getUcs(), 16);
        }
        return result;
    }
    
    public CodeElement determineCodeElement(char checkData) {
        CodeElement ce;
        if(workingSet == MarcTransformerSpecs.DEFAULT_G0_SET) {
            if(checkData <= 0x7e) {
                ce = getG0Map().findByID(checkData);
            } else {
                ce = getG1Map().findByID(checkData);
            }
        } else {
            ce = getWorkingMap().findByID(checkData);
        }
        return ce;
    }
    
    public CodeElementMap getG0Map() {
        return parser.findListForCodeTable(MarcTransformerSpecs.DEFAULT_G0_SET);
    }
    
    public CodeElementMap getG1Map() {
        return parser.findListForCodeTable(MarcTransformerSpecs.DEFAULT_G1_SET);
    }
    
    public CodeElementMap getWorkingMap() {
        return parser.findListForCodeTable(workingSet);
    }
    
    public int getWorkingOffset() {
        return workingOffset;
    }

    public void setWorkingOffset(int workingOffset) {
        this.workingOffset = workingOffset;
    }
    
    public void incrementWorkingOffset(int position) {
        workingOffset += position;
    }

    public int getWorkingSet() {
        return workingSet;
    }

    public void setWorkingSet(int workingSet) {
        this.workingSet = workingSet;
    }
    
    
}
