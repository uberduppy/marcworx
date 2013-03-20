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

import java.io.File;
import org.talwood.marcworx.marc.constants.MarcSubfieldConstants;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;

public class MarcWorxTestHelper {
    public static File getTestFile(String filename) {
        File file = null;
        try {
            String fileName = MarcWorxFileHelper.locateResource(MarcWorxTestHelper.class, "org/talwood/marcworx/data/testdata/" + filename);
            file = new File(fileName);
        } catch (Exception ex) {
            // TODO Change this, perhaps a constraint exception?
            System.out.println("Error loading resource");
            ex.printStackTrace(System.out);
        }
        return file;
    }
    
    public static MarcTag buildMarcTag(int tagNumber, char ind1, char ind2, MarcSubfield... subs) {
        StringBuilder sb = new StringBuilder();
        sb.append(ind1).append(ind2);
        for(MarcSubfield sub : subs) {
            sb.append(MarcSubfieldConstants.SUBFIELD_CODE).append(sub.getCode()).append(sub.getData());
        }
        return new MarcTag(tagNumber, sb.toString());
    }
}
