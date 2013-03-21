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

package org.talwood.marcworx.innovation.containers;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.innovation.annotation.CompositeFieldString;
import org.talwood.marcworx.innovation.annotation.NonFilingIndicatorFieldString;
import org.talwood.marcworx.innovation.annotation.NonRepeatableFieldString;
import org.talwood.marcworx.innovation.annotation.RepeatableFieldString;

/**
 *
 * @author twalker
 */
@XmlRootElement(name="Title")
public class TitleTransformerElement extends BaseTransformerElement implements Serializable {

    @NonFilingIndicatorFieldString(subfield='a', before=true, indicatorToUse=2) private String article;
    @NonFilingIndicatorFieldString(subfield='a', before=false, indicatorToUse=2) private String title;
    @NonRepeatableFieldString(subfield = 'b') private String titleRemainder;
    @NonRepeatableFieldString(subfield = 'c') private String stmtOfResponsibility;
    @NonRepeatableFieldString(subfield = 'f') private String inclusiveDates;
    @NonRepeatableFieldString(subfield = 'g') private String bulkDates;
    @NonRepeatableFieldString(subfield = 'h') private String medium;
    @RepeatableFieldString(subfield = 'k') private String form;
    @RepeatableFieldString(subfield = 's') private String version;
    @RepeatableFieldString(subfield = 'n') private String partNumber;
    @RepeatableFieldString(subfield = 'p') private String partName;
    @CompositeFieldString(subfields = "abnp") private String displayableTitle;
    
    public TitleTransformerElement() {
        
    }

    @XmlElement(name="Article")
    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    
    @XmlElement(name="Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name="TitleRemainder")
    public String getTitleRemainder() {
        return titleRemainder;
    }

    public void setTitleRemainder(String titleRemainder) {
        this.titleRemainder = titleRemainder;
    }

    @XmlElement(name="StmtOfResponsibility")
    public String getStmtOfResponsibility() {
        return stmtOfResponsibility;
    }

    public void setStmtOfResponsibility(String stmtOfResponsibility) {
        this.stmtOfResponsibility = stmtOfResponsibility;
    }

    @XmlElement(name="InclusiveDates")
    public String getInclusiveDates() {
        return inclusiveDates;
    }

    public void setInclusiveDates(String inclusiveDates) {
        this.inclusiveDates = inclusiveDates;
    }

    @XmlElement(name="BulkDates")
    public String getBulkDates() {
        return bulkDates;
    }

    public void setBulkDates(String bulkDates) {
        this.bulkDates = bulkDates;
    }

    @XmlElement(name="Medium")
    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
    
    @XmlElement(name="Form")
    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    @XmlElement(name="Version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement(name="PartNumber")
    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    @XmlElement(name="PartName")
    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @XmlElement(name="DisplayableTitle")
    public String getDisplayableTitle() {
        return displayableTitle;
    }

    public void setDisplayableTitle(String displayableTitle) {
        this.displayableTitle = displayableTitle;
    }
    
    
    
    
}
