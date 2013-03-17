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

import org.talwood.marcworx.marc.iface.BaseMarcLeader;

public class MarcLeader extends BaseMarcLeader {
    
    

    public MarcLeader(char recordType) {
        super(recordType);
    }

    public MarcLeader(String data) {
        super(data);
    }
    
    
    @Override
    public void preConstruction() {
        // Nothing special needed at this time
    }

    @Override
    public void postConstruction() {
        // Nothing special needed at this time
    }
    
}
