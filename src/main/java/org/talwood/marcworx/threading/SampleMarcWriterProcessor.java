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
package org.talwood.marcworx.threading;

import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.marc.containers.MarcRecord;

public class SampleMarcWriterProcessor implements IMarcWorxThreadProcessor {
    public SampleMarcWriterProcessor() {
        
    }

    @Override
    public boolean processRecord(MarcRecord record) throws MarcException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void handleMarcException(MarcException ex) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void preProcess() throws MarcException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postProcess() throws MarcException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
