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

package org.talwood.marcworx.smarc.containers;

import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
public abstract class SmartMarcBaseTag  {
    
    public SmartMarcBaseTag(MarcTag tag) {
        repopulateData(tag);
    }
    
    public abstract void repopulateData(MarcTag tag);
    
    public void setTagData(MarcTag tag) {
        repopulateData(tag);
    }
    
}