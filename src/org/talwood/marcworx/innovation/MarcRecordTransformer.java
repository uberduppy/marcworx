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

package org.talwood.marcworx.innovation;

import java.io.Serializable;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.containers.MarcRecordDataContainer;
import org.talwood.marcworx.innovation.containers.TitleTransformerElement;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.xform.XMLFormatter;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="marcdata")
public class MarcRecordTransformer implements Serializable {

    private TitleTransformerElement title;
    
    private MarcRecordTransformer() {}
    
    public MarcRecordTransformer(MarcRecord record) {
        parseData(record);
    }

    private void parseData(MarcRecord record) {
        // Let's get the 245 going.
        
    }

    public String toXml() throws Exception {
        JAXBContext context = JAXBContext.newInstance(MarcRecordDataContainer.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.marshal(this, sw);
        XMLFormatter xmlf = new XMLFormatter();
        return xmlf.format(sw.toString());
    }

    public TitleTransformerElement getTitle() {
        return title;
    }

    public void setTitle(TitleTransformerElement title) {
        this.title = title;
    }
    
    
}
