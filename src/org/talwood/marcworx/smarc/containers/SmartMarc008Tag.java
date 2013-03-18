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

import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.exception.MarcExceptionType;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
public class SmartMarc008Tag extends SmartMarcFixedTag {
    
    private String dateEnteredOnFile;
    private char typeOfDate;
    private String date1;
    private String date2;
    private String placeOfPublication;
    private SmartMarc006Base configSection;
    private String language;
    private char modifiedRecord;
    private char catalogingSource;
    
    public SmartMarc008Tag(MarcTag tag) throws MarcException {
        super(tag);
        String data = tag.getCurrentTagData();
        if(MarcWorxStringHelper.getLength(data) != 40) {
            throw new MarcException(MarcExceptionType.INVALID_TAG_LENGTH, "008 field must be 40 characters long");
        }
        dateEnteredOnFile = data.substring(0, 6);
        typeOfDate = data.charAt(6);
        date1 = data.substring(7, 11);
        date2 = data.substring(11, 15);
        placeOfPublication = data.substring(15, 18);
        /* TODO Add a section */
        language = data.substring(35, 38);
        modifiedRecord = data.charAt(38);
        catalogingSource = data.charAt(39);
    }

    public String getDateEnteredOnFile() {
        return dateEnteredOnFile;
    }

    public void setDateEnteredOnFile(String dateEnteredOnFile) {
        this.dateEnteredOnFile = dateEnteredOnFile;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getPlaceOfPublication() {
        return placeOfPublication;
    }

    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    public SmartMarc006Base getConfigSection() {
        return configSection;
    }

    public void setConfigSection(SmartMarc006Base configSection) {
        this.configSection = configSection;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public char getTypeOfDate() {
        return typeOfDate;
    }

    public void setTypeOfDate(char typeOfDate) {
        this.typeOfDate = typeOfDate;
    }

    public char getModifiedRecord() {
        return modifiedRecord;
    }

    public void setModifiedRecord(char modifiedRecord) {
        this.modifiedRecord = modifiedRecord;
    }

    public char getCatalogingSource() {
        return catalogingSource;
    }

    public void setCatalogingSource(char catalogingSource) {
        this.catalogingSource = catalogingSource;
    }

    
    
}
