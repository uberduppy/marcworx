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

public class MarcTransformerStats {
    private CodeTableParser parser = null;
   
    private int workingSet;
    private boolean multibyte;
    private int workingOffset;
    
    public MarcTransformerStats() throws ConstraintException {
        this.multibyte = false;
        this.workingOffset = 0;
        this.parser = CodeTableParser.getCodeTableParser();
    }
    
    public MarcTransformerStats(boolean multibyte, int startingOffset) throws ConstraintException {
        this.multibyte = multibyte;
        this.workingOffset = startingOffset;
        this.parser = CodeTableParser.getCodeTableParser();
    }

    public CodeElement determineCodeElement(char checkData) {
        CodeElement ce = null;
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
    
    public boolean isMultibyte() {
        return multibyte;
    }

    public void setMultibyte(boolean multibyte) {
        this.multibyte = multibyte;
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
