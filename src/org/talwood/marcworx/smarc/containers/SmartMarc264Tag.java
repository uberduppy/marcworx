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
public class SmartMarc264Tag extends SmartMarcSubfieldTag {
    
    List<String> placeOfProduction = new ArrayList<String>();
    List<String> nameOfProducer = new ArrayList<String>();
    List<String> productionDate = new ArrayList<String>();
    List<String> placeOfPublication = new ArrayList<String>();
    List<String> nameOfPublisher = new ArrayList<String>();
    List<String> publicationDate = new ArrayList<String>();
    List<String> placeOfDistribution = new ArrayList<String>();
    List<String> nameOfDistributor = new ArrayList<String>();
    List<String> distributionDate = new ArrayList<String>();
    List<String> placeOfManufacture = new ArrayList<String>();
    List<String> nameOfManufacturer = new ArrayList<String>();
    List<String> manufactureDate = new ArrayList<String>();
    List<String> copyrightDate = new ArrayList<String>();
    
    public SmartMarc264Tag(MarcTag tag) {
        super(tag);
        initialize();
    }

    private void initialize() {
        switch(getSecondIndicator()) {
            case '0': // Production
                initializeProduction();
                break;
            case '1': // Publication
                initializePublication();
                break;
            case '2': // Distribution
                initializeDistribution();
                break;
            case '3': // Manufacture
                initializeManufacture();
                break;
            case '4': // Copyright
                initializeCopyright();
                break;
            default:
                // Do nothing. Bad indicator
                break;
        }
    }
    
    private void initializeProduction() {
        for(MarcSubfield sub : getSubfields()) {
            switch(sub.getCode()) {
                case 'a':
                    placeOfProduction.add(sub.getDataUnpunctuated());
                    break;
                case 'b':
                    nameOfProducer.add(sub.getDataUnpunctuated());
                    break;
                case 'c':
                    String pubYr = MarcWorxDataHelper.gimmePublicationYear(sub.getData());
                    if(MarcWorxStringHelper.isNotEmpty(pubYr)) {
                        productionDate.add(pubYr);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    
    private void initializePublication() {
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
                    break;
                default:
                    break;
            }
        }
    }
    
    private void initializeDistribution() {
        for(MarcSubfield sub : getSubfields()) {
            switch(sub.getCode()) {
                case 'a':
                    placeOfDistribution.add(sub.getDataUnpunctuated());
                    break;
                case 'b':
                    nameOfDistributor.add(sub.getDataUnpunctuated());
                    break;
                case 'c':
                    String pubYr = MarcWorxDataHelper.gimmePublicationYear(sub.getData());
                    if(MarcWorxStringHelper.isNotEmpty(pubYr)) {
                        distributionDate.add(pubYr);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void initializeManufacture() {
        for(MarcSubfield sub : getSubfields()) {
            switch(sub.getCode()) {
                case 'a':
                    placeOfManufacture.add(sub.getDataUnpunctuated());
                    break;
                case 'b':
                    nameOfManufacturer.add(sub.getDataUnpunctuated());
                    break;
                case 'c':
                    String pubYr = MarcWorxDataHelper.gimmePublicationYear(sub.getData());
                    if(MarcWorxStringHelper.isNotEmpty(pubYr)) {
                        manufactureDate.add(pubYr);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    
    private void initializeCopyright() {
        for(MarcSubfield sub : getSubfields()) {
            switch(sub.getCode()) {
                case 'a':
                    placeOfProduction.add(sub.getDataUnpunctuated());
                    break;
                case 'b':
                    nameOfProducer.add(sub.getDataUnpunctuated());
                    break;
                case 'c':
                    String pubYr = MarcWorxDataHelper.gimmePublicationYear(sub.getData());
                    if(MarcWorxStringHelper.isNotEmpty(pubYr)) {
                        copyrightDate.add(pubYr);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public List<String> getPlaceOfProduction() {
        return placeOfProduction;
    }

    public List<String> getNameOfProducer() {
        return nameOfProducer;
    }

    public List<String> getProductionDate() {
        return productionDate;
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

    public List<String> getPlaceOfDistribution() {
        return placeOfDistribution;
    }

    public List<String> getNameOfDistributor() {
        return nameOfDistributor;
    }

    public List<String> getDistributionDate() {
        return distributionDate;
    }

    public List<String> getPlaceOfManufacture() {
        return placeOfManufacture;
    }

    public List<String> getNameOfManufacturer() {
        return nameOfManufacturer;
    }

    public List<String> getManufactureDate() {
        return manufactureDate;
    }

    public List<String> getCopyrightDate() {
        return copyrightDate;
    }
    
    
}
