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

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.marc.constants.MarcSubfieldConstants;
import org.talwood.marcworx.marc.constants.MarcTagConstants;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.iface.BaseMarcSubfield;
import org.talwood.marcworx.marc.iface.BaseMarcTag;

public class MarcTag extends BaseMarcTag {
    
    public MarcTag(int tagNumber) {
        super(tagNumber);
    }

    public MarcTag(int tagNumber, String tagData) {
        super(tagNumber, tagData);
    }



    public MarcSubfield cloneSubfield(BaseMarcSubfield subfield) {
        MarcSubfield newSubfield = new MarcSubfield(subfield.getCode(), subfield.getData());
        return newSubfield;
    }
    
    public List<MarcSubfield> createSubfieldList() {
        return new ArrayList<MarcSubfield>();
    }

    @Override
    public void preConstruction() {
    }

    @Override
    public void postConstruction() {
        // nothing to do here, list is constructed
    }


    
}
