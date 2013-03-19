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
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
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
    
    
}


