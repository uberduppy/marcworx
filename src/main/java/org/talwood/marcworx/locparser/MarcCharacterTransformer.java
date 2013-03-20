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
package org.talwood.marcworx.locparser;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.locparser.constants.MarcTransformerSpecs;
import org.talwood.marcworx.locparser.containers.MarcTransformerStats;
import org.talwood.marcworx.locparser.elements.CodeElement;

public class MarcCharacterTransformer {

    private static int determineCharacterSet(char[] data, int workingPosition, MarcTransformerStats stats) {
        int newAddon = 1;
        if(data[workingPosition] == MarcTransformerSpecs.ESCAPE) {
            if(workingPosition + 1 < data.length) {
                newAddon = 2;
                switch(data[workingPosition + 1]) {
                    case 0x28:
                    case 0x2c:
                    case 0x42: // It's what's in the XML from LOC
                        stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
                        break;
                    case 0x24:
                        if(workingPosition + 2 < data.length) {
                            switch(data[workingPosition + 2]) {
                                case 0x2c:
                                    stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
                                    newAddon = 3;
                                    break;
                                case 0x29:
                                case 0x2d:
                                    stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G1_SET);
                                    newAddon = 3;
                                    break;
                                default:
                                    stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
                                    break;
                                
                            }
                        } else {
                            stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
                        }
                        break;
                    case 0x29:
                    case 0x2d:
                        stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G1_SET);
                        break;
                    case 0x21:
                        if(workingPosition + 2 < data.length && data[workingPosition + 2] == 0x45) {
                            stats.setWorkingSet(MarcTransformerSpecs.EXTENDED_LATIN_ANSEL);
                            newAddon = 3;
                        }
                        break;
                    case 0x45: // Extended ANSEL
                        stats.setWorkingSet(MarcTransformerSpecs.EXTENDED_LATIN_ANSEL);
                        break;
                    case 0x67: // Greek Symbols
                        stats.setWorkingSet(MarcTransformerSpecs.GREEK_SYMBOLS);
                        break;
                    case 0x62: // Subscripts
                        stats.setWorkingSet(MarcTransformerSpecs.SUBSCRIPTS);
                        break;
                    case 0x70: // Superscripts
                        stats.setWorkingSet(MarcTransformerSpecs.SUPERSCRIPTS);
                        break;
                    case 0x32: // Hebrew
                        stats.setWorkingSet(MarcTransformerSpecs.BASIC_HEBREW);
                        break;
                    case 0x4e: // Cyrillic
                        stats.setWorkingSet(MarcTransformerSpecs.BASIC_CYRILLIC);
                        break;
                    case 0x51: // Extended Cyrillic
                        stats.setWorkingSet(MarcTransformerSpecs.EXTENDED_CYRILLIC);
                        break;
                    case 0x33: // Basic Arabic
                        stats.setWorkingSet(MarcTransformerSpecs.BASIC_ARABIC);
                        break;
                    case 0x34: // Extended Arabic
                        stats.setWorkingSet(MarcTransformerSpecs.EXTENDED_ARABIC);
                        break;
                    case 0x53: // Basic Greek
                        stats.setWorkingSet(MarcTransformerSpecs.BASIC_GREEK);
                        break;
                    case 0x31: // Asian
                        stats.setWorkingSet(MarcTransformerSpecs.EAST_ASIAN);
                        break;
                    default:
                        stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
                        newAddon = 1;
                        break;
                }
            }
        }
        return workingPosition + newAddon;
        
    }
    
    private static int countRemainingCharacters(char[] buffer, int currentPosition) {
        int result = 0;
        if(buffer != null) {
            result = buffer.length - currentPosition - 1;
        }
        return result;
    }
    
    public static String convertMarc8ToUnicode(String data) throws ConstraintException {
        return convertMarc8ToUnicode(data.toCharArray());
    }
    
    public static String convertMarc8ToUnicode(byte[] data) throws ConstraintException {
        return convertMarc8ToUnicode(new String(data).toCharArray());
    }
    
    private static String convertMarc8ToUnicode(char[] data) throws ConstraintException {
        StringBuilder sb = new StringBuilder();

        // Use default settings
        MarcTransformerStats stats = new MarcTransformerStats();
        
        // OK, each character is either "in or it's "out".
        int workingPosition = 0;
//        int workingSet = MarcTransformerSpecs.DEFAULT_G0_SET;
        stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
        while(workingPosition < data.length) {
            if(data[workingPosition] == MarcTransformerSpecs.ESCAPE) {
                // OK, starting new character set
                workingPosition = determineCharacterSet(data, workingPosition, stats);
            } else {
                if (stats.getWorkingSet() == MarcTransformerSpecs.EAST_ASIAN) {
                    if(countRemainingCharacters(data, workingPosition) > 2) {
                        char asianCharacter = stats.determineEastAsianCodeElement(data[workingPosition], data[workingPosition + 1], data[workingPosition + 2]);
                        if(asianCharacter != 0x00) {
                            sb.append(asianCharacter);
                            workingPosition += 3; 
                        } else {
                            sb.append("\uFFFD"); // Unknown character
                            // Move ahead one and try again
                            workingPosition ++; 
                        }
                    }
                } else {
                    // This is a character in my current working set. (or possibly G1 if I'm in G0.)
                    CodeElement ce = stats.determineCodeElement(data[workingPosition]);
                    // First, is it multiple charactered?
                    if(ce != null) {
                        if(ce.isCombining() && countRemainingCharacters(data, workingPosition) > 0) {
                            // Do combining magic here
                            List<Character> combinedData = new ArrayList<Character>();
                            while(ce.isCombining() && countRemainingCharacters(data, workingPosition) > 0) {
                                char c = (char)Integer.parseInt(ce.getUcs(), 16);
                                if(c != 0x00) {
                                    combinedData.add(new Character(c));
                                }
                                workingPosition++;
                                ce = stats.determineCodeElement(data[workingPosition]);
                            }
                            char c2 = (char)Integer.parseInt(ce.getUcs(), 16);
                            // Let's try to compose,
                            if(c2 != 0x00) {
                                StringBuilder innerString = new StringBuilder();
                                innerString.append(c2);
                                for(Character ch : combinedData) {
                                    innerString.append(ch.charValue());
                                }
                                String innerData = Normalizer.normalize(innerString, Normalizer.Form.NFKC);
                                sb.append(innerData);
                            }
                        } else if (stats.getWorkingSet() == MarcTransformerSpecs.EAST_ASIAN) {
                            // East Asian, multiple characters. 3 characters at at time.
                            if(countRemainingCharacters(data, workingPosition) > 2) {
                                char asianCharacter = stats.determineEastAsianCodeElement(data[workingPosition], data[workingPosition + 1], data[workingPosition + 2]);
                                if(asianCharacter != 0x00) {
                                    sb.append(asianCharacter);
                                    workingPosition += 2; // The third character will be handled at the end of the loop.
                                }
                            }
    //                    } else if (stats.isMultibyte()) {
    //                        // Is this a multibyte character?
    //                        String unicode = ce.getUtf8();
                        } else {
                            // OK, what set am I in?
                            char c = (char)Integer.parseInt(ce.getUcs(), 16);
                            sb.append(c);

                        }
                    } else {
                        // We couldn't find it, dump it out straight.
                        sb.append(data[workingPosition]);
                    }
                    // Rifle all possible characters...
                    workingPosition++;
                }
            }
        }
        return sb.toString();
    }
}
