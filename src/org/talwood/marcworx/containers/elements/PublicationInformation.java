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

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
@XmlRootElement
public class PublicationInformation extends BaseTagDataElement {
    
    private String placeOfProduction;
    private String nameOfProducer;
    private String dateOfProduction;
    private String placeOfDistribution;
    private String nameOfDistributer;
    private String dateOfDistribution;
    private String placeOfPublication;
    private String nameOfPublisher;
    private String dateOfPublication;
    private String dateOfCopyright;
    private String placeOfManufacture;
    private String manufacturer;
    private String dateOfManufacture;
    
    
    public PublicationInformation() {
        
    }
    
    public PublicationInformation(List<MarcTag> tags) {
        parseTags(tags);
    }

    private void parseTags(List<MarcTag> tags) {
        // Process in this order 264, 008, 260
        // Only fill a field if it's already empty
        int[] tagOrder = {264, 8, 260};
        for(int tagNum : tagOrder) {
            for(MarcTag tag : tags) {
                if(tag.getTagNumber() == tagNum) {
                    switch(tagNum) {
                        case 8:
                            populateFrom008(tag);
                            break;
                        case 260:
                            populateFrom260(tag);
                            break;
                        case 264:
                            populateFrom264(tag);
                            break;

                    }
                }
            }
        }
    }
    
    private void populateFrom008(MarcTag tag) {
        // Create a decorated 008 tag.
    }
    private void populateFrom260(MarcTag tag) {
        
    }
    private void populateFrom264(MarcTag tag) {
        
    }
    
    public String getPlaceOfProduction() {
        return placeOfProduction;
    }

    public void setPlaceOfProduction(String placeOfProduction) {
        this.placeOfProduction = placeOfProduction;
    }

    public String getNameOfProducer() {
        return nameOfProducer;
    }

    public void setNameOfProducer(String nameOfProducer) {
        this.nameOfProducer = nameOfProducer;
    }

    public String getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(String dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public String getPlaceOfDistribution() {
        return placeOfDistribution;
    }

    public void setPlaceOfDistribution(String placeOfDistribution) {
        this.placeOfDistribution = placeOfDistribution;
    }

    public String getNameOfDistributer() {
        return nameOfDistributer;
    }

    public void setNameOfDistributer(String nameOfDistributer) {
        this.nameOfDistributer = nameOfDistributer;
    }

    public String getDateOfDistribution() {
        return dateOfDistribution;
    }

    public void setDateOfDistribution(String dateOfDistribution) {
        this.dateOfDistribution = dateOfDistribution;
    }

    public String getPlaceOfPublication() {
        return placeOfPublication;
    }

    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    public String getNameOfPublisher() {
        return nameOfPublisher;
    }

    public void setNameOfPublisher(String nameOfPublisher) {
        this.nameOfPublisher = nameOfPublisher;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getDateOfCopyright() {
        return dateOfCopyright;
    }

    public void setDateOfCopyright(String dateOfCopyright) {
        this.dateOfCopyright = dateOfCopyright;
    }

    public String getPlaceOfManufacture() {
        return placeOfManufacture;
    }

    public void setPlaceOfManufacture(String placeOfManufacture) {
        this.placeOfManufacture = placeOfManufacture;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(String dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }
    
    
}
