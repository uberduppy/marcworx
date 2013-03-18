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

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
public class SmartMarc260Tag extends SmartMarcSubfieldTag {
    
    List<String> placeOfPublication = new ArrayList<String>();
    List<String> nameOfPublisher = new ArrayList<String>();
    List<String> publicationDate = new ArrayList<String>();
    List<String> copyrightDate = new ArrayList<String>();
    List<String> placeOfManufacture = new ArrayList<String>();
    List<String> manufacturer = new ArrayList<String>();
    List<String> dateOfManufacture = new ArrayList<String>();
    
    public SmartMarc260Tag(MarcTag tag) {
        super(tag);
        initialize();
    }
    
    private void initialize() {
        for(MarcSubfield sub : getSubfields()) {
            switch(sub.getCode()) {
                case 'a':
                    placeOfPublication.add(sub.getDataUnpunctuated());
                    break;
                case 'b':
                    nameOfPublisher.add(sub.getDataUnpunctuated());
                    break;
                case 'c':
                    String pubYr = MarcWorxDataHelper.gimmePublicationYear(sub.getData());
                    if(MarcWorxStringHelper.isNotEmpty(pubYr)) {
                        publicationDate.add(pubYr);
                    }
                    String copyYr = MarcWorxDataHelper.gimmeCopyrightYear(sub.getData());
                    if(MarcWorxStringHelper.isNotEmpty(copyYr)) {
                        copyrightDate.add(copyYr);
                    }
                    break;
                case 'e':
                    placeOfManufacture.add(sub.getDataUnpunctuated());
                    break;
                case 'f':
                    manufacturer.add(sub.getDataUnpunctuated());
                    break;
                case 'g':
                    String manYr = MarcWorxDataHelper.gimmePublicationYear(sub.getData());
                    if(MarcWorxStringHelper.isNotEmpty(manYr)) {
                        dateOfManufacture.add(manYr);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public List<String> getPlaceOfPublication() {
        return placeOfPublication;
    }

    public List<String> getNameOfPublisher() {
        return nameOfPublisher;
    }

    public List<String> getPublicationDate() {
        return publicationDate;
    }

    public List<String> getCopyrightDate() {
        return copyrightDate;
    }

    public List<String> getPlaceOfManufacture() {
        return placeOfManufacture;
    }

    public List<String> getManufacturer() {
        return manufacturer;
    }

    public List<String> getDateOfManufacture() {
        return dateOfManufacture;
    }
    
    
}
