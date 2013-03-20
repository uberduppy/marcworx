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
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.talwood.marcworx.exception.MarcException;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxStringHelper;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.smarc.containers.SmartMarc008Tag;
import org.talwood.marcworx.smarc.containers.SmartMarc260Tag;
import org.talwood.marcworx.smarc.containers.SmartMarc264Tag;

/**
 *
 * @author twalker
 */
@XmlRootElement
public class PublicationInformation extends BaseTagDataElement implements Serializable {
    
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
    private String nameOfManufacturer;
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
        try {
            // Create a decorated 008 tag.
            SmartMarc008Tag workTag = new SmartMarc008Tag(tag);
            if("epst".indexOf(workTag.getTypeOfDate()) >= 0) {
                setDateOfPublication(MarcWorxDataHelper.gimmePublicationYear(workTag.getDate1()));
            }
        } catch (MarcException ex) {
            // Do nothing, just don't parse the date from it!
        }
    }
    private void populateFrom260(MarcTag tag) {
        SmartMarc260Tag tag260 = new SmartMarc260Tag(tag);
        for(String data : tag260.getPlaceOfPublication()) {
            setPlaceOfPublication(data);
        }
        for(String data : tag260.getCopyrightDate()) {
            setDateOfCopyright(data);
        }
        for(String data : tag260.getDateOfManufacture()) {
            setDateOfManufacture(data);
        }
        for(String data : tag260.getManufacturer()) {
            setNameOfManufacturer(data);
        }
        for(String data : tag260.getNameOfPublisher()) {
            setNameOfPublisher(data);
        }
        for(String data : tag260.getPlaceOfManufacture()) {
            setPlaceOfManufacture(data);
        }
        for(String data : tag260.getPublicationDate()) {
            setDateOfPublication(data);
        }
    }
    private void populateFrom264(MarcTag tag) {
        SmartMarc264Tag tag264 = new SmartMarc264Tag(tag);
        for(String data : tag264.getCopyrightDate()) {
            setDateOfCopyright(data);
        }
        for(String data : tag264.getDistributionDate()) {
            setDateOfDistribution(data);
        }
        for(String data : tag264.getManufactureDate()) {
            setDateOfManufacture(data);
        }
        for(String data : tag264.getNameOfDistributor()) {
            setNameOfDistributer(data);
        }
        for(String data : tag264.getNameOfManufacturer()) {
            setNameOfManufacturer(data);
        }
        for(String data : tag264.getNameOfProducer()) {
            setNameOfProducer(data);
        }
        for(String data : tag264.getNameOfPublisher()) {
            setNameOfPublisher(data);
        }
        for(String data : tag264.getPlaceOfDistribution()) {
            setPlaceOfDistribution(data);
        }
        for(String data : tag264.getPlaceOfManufacture()) {
            setPlaceOfManufacture(data);
        }
        for(String data : tag264.getPlaceOfProduction()) {
            setPlaceOfProduction(data);
        }
        for(String data : tag264.getPlaceOfPublication()) {
            setPlaceOfPublication(data);
        }
        for(String data : tag264.getProductionDate()) {
            setDateOfProduction(data);
        }
        for(String data : tag264.getPublicationDate()) {
            setDateOfPublication(data);
        }
    }

    @XmlElement(name="productionPlace")
    public String getPlaceOfProduction() {
        return placeOfProduction;
    }

    public void setPlaceOfProduction(String placeOfProduction) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.placeOfProduction)) {
            this.placeOfProduction = placeOfProduction;
        }
    }

    @XmlElement(name="producerName")
    public String getNameOfProducer() {
        return nameOfProducer;
    }

    public void setNameOfProducer(String nameOfProducer) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.nameOfProducer)) {
            this.nameOfProducer = nameOfProducer;
        }
    }

    @XmlElement(name="productionDate")
    public String getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(String dateOfProduction) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.dateOfProduction)) {
            this.dateOfProduction = dateOfProduction;
        }
    }

    @XmlElement(name="distributionPlace")
    public String getPlaceOfDistribution() {
        return placeOfDistribution;
    }

    public void setPlaceOfDistribution(String placeOfDistribution) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.placeOfDistribution)) {
            this.placeOfDistribution = placeOfDistribution;
        }
    }

    @XmlElement(name="distributerName")
    public String getNameOfDistributer() {
        return nameOfDistributer;
    }

    public void setNameOfDistributer(String nameOfDistributer) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.nameOfDistributer)) {
            this.nameOfDistributer = nameOfDistributer;
        }
    }

    @XmlElement(name="distributionDate")
    public String getDateOfDistribution() {
        return dateOfDistribution;
    }

    public void setDateOfDistribution(String dateOfDistribution) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.dateOfDistribution)) {
            this.dateOfDistribution = dateOfDistribution;
        }
    }

    @XmlElement(name="publicationPlace")
    public String getPlaceOfPublication() {
        return placeOfPublication;
    }

    public void setPlaceOfPublication(String placeOfPublication) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.placeOfPublication)) {
            this.placeOfPublication = placeOfPublication;
        }
    }

    @XmlElement(name="publisherName")
    public String getNameOfPublisher() {
        return nameOfPublisher;
    }

    public void setNameOfPublisher(String nameOfPublisher) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.nameOfPublisher)) {
            this.nameOfPublisher = nameOfPublisher;
        }
    }

    @XmlElement(name="publicationDate")
    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.dateOfPublication)) {
            this.dateOfPublication = dateOfPublication;
        }
    }

    @XmlElement(name="copyrightDate")
    public String getDateOfCopyright() {
        return dateOfCopyright;
    }

    public void setDateOfCopyright(String dateOfCopyright) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.dateOfCopyright)) {
            this.dateOfCopyright = dateOfCopyright;
        }
    }

    @XmlElement(name="mannufacturePlace")
    public String getPlaceOfManufacture() {
        return placeOfManufacture;
    }

    public void setPlaceOfManufacture(String placeOfManufacture) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.placeOfManufacture)) {
            this.placeOfManufacture = placeOfManufacture;
        }
    }

    @XmlElement(name="manufacturerName")
    public String getNameOfManufacturer() {
        return nameOfManufacturer;
    }

    public void setNameOfManufacturer(String nameOfManufacturer) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.nameOfManufacturer)) {
            this.nameOfManufacturer = nameOfManufacturer;
        }
    }

    @XmlElement(name="manufacturePlace")
    public String getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(String dateOfManufacture) {
        // Each setter will only take the first attempt
        if(MarcWorxStringHelper.isEmpty(this.dateOfManufacture)) {
            this.dateOfManufacture = dateOfManufacture;
        }
    }
    
    
}
