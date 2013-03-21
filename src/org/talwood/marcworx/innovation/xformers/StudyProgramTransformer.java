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

package org.talwood.marcworx.innovation.xformers;

import java.io.Serializable;
import org.talwood.marcworx.innovation.containers.BaseTransformerElement;
import org.talwood.marcworx.innovation.containers.StudyProgramTransformerElement;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
public class StudyProgramTransformer extends BaseTransformer implements Serializable {
    private static final long serialVersionUID = 1L;

    public StudyProgramTransformer(MarcTag tag) {
        super(tag);
    }

    @Override
    public BaseTransformerElement buildElement() {
        return new StudyProgramTransformerElement();
    }

    @Override
    public void doSpecialProcessing(BaseTransformerElement element) {
        // Nothing to do yet
    }
}
