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

package org.talwood.marcworx.containers.elements;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.TupleContainer;
import org.talwood.marcworx.marc.constants.DataWorkConstants;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
@XmlRootElement
public class TitleElement extends BaseTagDataElement implements Serializable {
    
    private String article;
    private String title;
    private String titleRemainder;
    private String stmtOfResponsibility;
    private String inclusiveDates;
    private String bulkDates;
    private String medium;
    private String form;
    private String version;
    private String partNumber;
    private String partName;
    private String displayableTitle;
            
    
    public TitleElement() {
        
    }
    
    public TitleElement(MarcTag tag) {
        parseData(tag);
    }
    
    private void parseData(MarcTag tag) {
        char nonfiling = tag.getSecondIndicator();
        MarcSubfield suba = tag.getSubfield('a', 1);
        if(suba != null) {
            String subdata = suba.getData();
            TupleContainer<String, String> splitData = MarcWorxDataHelper.splitString(subdata, nonfiling);
            setArticle(splitData.getLeftSide());
            setTitle(splitData.getRightSide());
        }
        setTitleRemainder(MarcWorxDataHelper.pullDataFromSubfield(tag, 'b', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
        setStmtOfResponsibility(MarcWorxDataHelper.pullDataFromSubfield(tag, 'c', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
        setInclusiveDates(MarcWorxDataHelper.pullDataFromSubfield(tag, 'f', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
        setBulkDates(MarcWorxDataHelper.pullDataFromSubfield(tag, 'g', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
        setMedium(MarcWorxDataHelper.pullDataFromSubfield(tag, 'h', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
        setForm(MarcWorxDataHelper.pullAppendedDataFromSubfields(tag, "k", " ", DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
        setPartNumber(MarcWorxDataHelper.pullAppendedDataFromSubfields(tag, "n", " ", DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
        setPartName(MarcWorxDataHelper.pullAppendedDataFromSubfields(tag, "p", " ", DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
        setVersion(MarcWorxDataHelper.pullDataFromSubfield(tag, 's', 1, DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP));
        
        // TODO sA displayable title is requested "abnp"
        setDisplayableTitle(tag.getSubfieldData("abnp", " "));
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


