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

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.locparser.constants.MarcTransformerSpecs;
import org.talwood.marcworx.locparser.containers.CodeElementMap;
import org.talwood.marcworx.locparser.containers.MarcTransformerStats;
import org.talwood.marcworx.locparser.elements.CodeElement;

public class MarcCharacterTransformer {

    private static int determineCharacterSet(char[] data, int workingPosition, MarcTransformerStats stats) {
        int newAddon = 1;
        if(data[workingPosition] == MarcTransformerSpecs.ESCAPE) {
            if(workingPosition + 1 < data.length) {
                switch(data[workingPosition + 1]) {
                    case 0x28:
                    case 0x2c:
                        stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x24:
                        if(workingPosition + 2 < data.length) {
                            switch(data[workingPosition + 2]) {
                                case 0x2c:
                                    stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
                                    stats.setMultibyte(true);
                                    newAddon = 3;
                                    break;
                                case 0x29:
                                    stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G1_SET);
                                    stats.setMultibyte(true);
                                    newAddon = 3;
                                case 0x2d:
                                    stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G1_SET);
                                    stats.setMultibyte(true);
                                    newAddon = 3;
                                    break;
                                default:
                                    stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
                                    stats.setMultibyte(true);
                                    newAddon = 2;
                                    break;
                                
                            }
                        } else {
                            stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G0_SET);
                            stats.setMultibyte(true);
                            newAddon = 2;
                        }
                        break;
                    case 0x29:
                    case 0x2d:
                        stats.setWorkingSet(MarcTransformerSpecs.DEFAULT_G1_SET);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x21:
                        if(workingPosition + 2 < data.length && data[workingPosition + 2] == 0x45) {
                            stats.setWorkingSet(MarcTransformerSpecs.EXTENDED_LATIN_ANSEL);
                            stats.setMultibyte(true);
                            newAddon = 3;
                        }
                        break;
                    case 0x45: // Extended ANSEL
                        stats.setWorkingSet(MarcTransformerSpecs.EXTENDED_LATIN_ANSEL);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x67: // Greek Symbols
                        stats.setWorkingSet(MarcTransformerSpecs.GREEK_SYMBOLS);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x62: // Subscripts
                        stats.setWorkingSet(MarcTransformerSpecs.SUBSCRIPTS);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x70: // Superscripts
                        stats.setWorkingSet(MarcTransformerSpecs.SUPERSCRIPTS);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x32: // Hebrew
                        stats.setWorkingSet(MarcTransformerSpecs.BASIC_HEBREW);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x4e: // Cyrillic
                        stats.setWorkingSet(MarcTransformerSpecs.BASIC_CYRILLIC);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x51: // Extended Cyrillic
                        stats.setWorkingSet(MarcTransformerSpecs.EXTENDED_CYRILLIC);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x33: // Basic Arabic
                        stats.setWorkingSet(MarcTransformerSpecs.BASIC_ARABIC);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x34: // Extended Arabic
                        stats.setWorkingSet(MarcTransformerSpecs.EXTENDED_ARABIC);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x53: // Basic Greek
                        stats.setWorkingSet(MarcTransformerSpecs.BASIC_GREEK);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x31: // Asian
                        stats.setWorkingSet(MarcTransformerSpecs.EAST_ASIAN);
                        stats.setMultibyte(true);
                        newAddon = 2;
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
    
    public static String convertMarc8ToUnicode(byte[] data) throws ConstraintException {
        return convertMarc8ToUnicode(new String(data).toCharArray());
    }
    
//    private static CodeElement determineCodeElement(char[] data, int workingSet, Integer position, int workingPosition) {
//        CodeElement ce = null;
//        if(workingSet == MarcTransformerSpecs.DEFAULT_G0_SET) {
//            if(data[workingPosition] <= 0x7e) {
//                ce = defaultG0Map.findByID(position);
//            } else {
//                ce = defaultG1Map.findByID(position);
//            }
//        } else {
//            ce = codeMap.findByID(position);
//        }
//        return ce;
//        
//    }
    private static String convertMarc8ToUnicode(char[] data) throws ConstraintException {
        StringBuilder sb = new StringBuilder();

        // Use default settings
        MarcTransformerStats stats = new MarcTransformerStats();
        
        // OK, each character is either "in or it's "out".
        int workingPosition = 0;
        int workingSet = MarcTransformerSpecs.DEFAULT_G0_SET;
        stats.setWorkingSet(workingSet);
        CodeElementMap codeMap = stats.getWorkingMap();
        while(workingPosition < data.length) {
            if(data[workingPosition] == MarcTransformerSpecs.ESCAPE) {
                // OK, starting new character set
                workingPosition = determineCharacterSet(data, workingPosition, stats);
                codeMap = stats.getG0Map();
            } else {
                // This is a character in my current working set. (or possibly G1 if I'm in G0.)
                Integer i = new Integer(data[workingPosition]);
                CodeElement ce = stats.determineCodeElement(data[workingPosition]);
//                if(workingSet == MarcTransformerSpecs.DEFAULT_G0_SET) {
//                    if(data[workingPosition] <= 0x7e) {
//                        ce = defaultG0Map.findByID(i);
//                    } else {
//                        ce = defaultG1Map.findByID(i);
//                    }
//                } else {
//                    ce = codeMap.findByID(i);
//                }
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
                        if(c2 != 0x00) {
                            sb.append(c2);
                        }
                        for(Character ch : combinedData) {
                            sb.append(ch.charValue());
                        }
                        
                    } else if (workingSet == MarcTransformerSpecs.EAST_ASIAN) {
                        // East Asian, multiple characters. Up to 3.
                    } else if (stats.isMultibyte()) {
                        // Is this a multibyte character?
                        String unicode = ce.getUtf8();
                    } else {
                        // OK, what set am I in?
                        char c = (char)Integer.parseInt(ce.getUcs(), 16);
                        sb.append(c);
                        
                        // Could not find Code Element in this set
//                        char c = getChar(data[workingPosition], stats.getWorkingG0Set(), stats.getWorkingG1Set());
//                        if (c != 0) {
//                            sb.append(c);
//                        } else {
//                            String val = "0000"+Integer.toHexString((int)(data[workingPosition]));
//                            sb.append("<U+"+ (val.substring(val.length()-4, val.length()))+ ">" );
//                        }
//                        workingPosition++;
                        
                    }
                } else {
                    // We couldn't find it
                }
                // Rifle all possible characters...
                workingPosition++;
            }
        }
        
        
        
//        while (cdt.offset < data.length) 
//        {
//            if (ct.isCombining(data[cdt.offset], cdt.g0, cdt.g1)
//                    && hasNext(cdt.offset, len)) 
//            {
//
//                while (ct.isCombining(data[cdt.offset], cdt.g0, cdt.g1)
//                        && hasNext(cdt.offset, len)) 
//                {
//                    char c = getChar(data[cdt.offset], cdt.g0, cdt.g1);
//                    if (c != 0) diacritics.put(new Character(c));
//                    cdt.offset++;
//                    checkMode(data, cdt);
//                }
//
//                char c2 = getChar(data[cdt.offset], cdt.g0, cdt.g1);
//                cdt.offset++;
//                checkMode(data, cdt);
//                if (c2 != 0) sb.append(c2);
//
//                while (!diacritics.isEmpty()) 
//                {
//                    char c1 = ((Character) diacritics.get()).charValue();
//                    sb.append(c1);
//                }
//
//            } 
//            else if (cdt.multibyte)
//            {
//                if (data[cdt.offset]== 0x20)
//                {
//                    // if a 0x20 byte occurs amidst a sequence of multibyte characters
//                    // skip over it and output a space.
//                    // Hmmm.  If the following line is present it seems to output two spaces 
//                    // when a space occurs in multibytes chars, without it one seems to be output.
//                    //    sb.append(getChar(data[cdt.offset], cdt.g0, cdt.g1));
//                    cdt.offset += 1;
//                }
//                else if (cdt.offset + 3 <= data.length && (errorList == null || data[cdt.offset+1]!= 0x20 && data[cdt.offset+2]!= 0x20)) 
//                {
//                    char c = getMBChar(makeMultibyte(data[cdt.offset], data[cdt.offset+1], data[cdt.offset+2]));
//                    if (errorList == null  || c != 0)
//                    { 
//                        sb.append(c);
//                        cdt.offset += 3;
//                    }
//                    else if (cdt.offset + 6 <= data.length && data[cdt.offset+4]!= 0x20 && data[cdt.offset+5]!= 0x20 &&
//                            getMBChar(makeMultibyte(data[cdt.offset+3], data[cdt.offset+4], data[cdt.offset+5])) != 0)
//                    {
//                        if (errorList != null)
//                        {
//                            errorList.addError(ErrorHandler.MINOR_ERROR, "Erroneous MARC8 multibyte character, Discarding bad character and continuing reading Multibyte characters");
//                            sb.append("[?]");
//                            cdt.offset += 3;
//                        }
//                    }
//                    else if (cdt.offset + 4 <= data.length && data[cdt.offset] > 0x7f && 
//                            getMBChar(makeMultibyte(data[cdt.offset+1], data[cdt.offset+2], data[cdt.offset+3])) != 0)
//                    {
//                        if (errorList != null)
//                        {
//                            errorList.addError(ErrorHandler.MINOR_ERROR, "Erroneous character in MARC8 multibyte character, Copying bad character and continuing reading Multibyte characters");
//                            sb.append(getChar(data[cdt.offset], 0x42, 0x45));
//                            cdt.offset += 1;
//                        }
//                    }
//                    else
//                    {
//                        if (errorList != null)
//                        {
//                            errorList.addError(ErrorHandler.MINOR_ERROR, "Erroneous MARC8 multibyte character, inserting change to default character set");
//                        }
//                        cdt.multibyte = false;
//                        cdt.g0 = 0x42;
//                        cdt.g1 = 0x45;
//                    }
//                } 
//                else if (errorList != null && cdt.offset + 4 <= data.length && ( data[cdt.offset+1] == 0x20 || data[cdt.offset+2]== 0x20)) 
//                {
//                    int multiByte = makeMultibyte( data[cdt.offset], ((data[cdt.offset+1] != 0x20)? data[cdt.offset+1] : data[cdt.offset+2]),  data[cdt.offset+3]);
//                    char c = getMBChar(multiByte);
//                    if (c != 0) 
//                    {
//                        if (errorList != null)
//                        {
//                            errorList.addError(ErrorHandler.ERROR_TYPO, "Extraneous space found within MARC8 multibyte character");
//                        }
//                        sb.append(c);
//                        sb.append(' ');
//                        cdt.offset += 4;
//                    }
//                    else
//                    {
//                        if (errorList != null)
//                        {
//                            errorList.addError(ErrorHandler.MINOR_ERROR, "Erroneous MARC8 multibyte character, inserting change to default character set");
//                        }
//                        cdt.multibyte = false;
//                        cdt.g0 = 0x42;
//                        cdt.g1 = 0x45;
//                    }
//                } 
//                else if (cdt.offset + 3 > data.length) 
//                {
//                    if (errorList != null)
//                    {
//                        errorList.addError(ErrorHandler.MINOR_ERROR, "Partial MARC8 multibyte character, inserting change to default character set");
//                        cdt.multibyte = false;
//                        cdt.g0 = 0x42;
//                        cdt.g1 = 0x45;
//                    }
//                    // if a field ends with an incomplete encoding of a multibyte character
//                    // simply discard that final partial character.
//                    else 
//                    {
//                        cdt.offset += 3;
//                    }
//                } 
//            }
//            else 
//            {
//                char c = getChar(data[cdt.offset], cdt.g0, cdt.g1);
//                if (c != 0) sb.append(c);
//                else 
//                {
//                    String val = "0000"+Integer.toHexString((int)(data[cdt.offset]));
//                    sb.append("<U+"+ (val.substring(val.length()-4, val.length()))+ ">" );
//                }
//                cdt.offset += 1;
//            }
//            if (hasNext(cdt.offset, len))
//            {
//                checkMode(data, cdt);
//            }
//        }
        
        
        return sb.toString();
    }
    
    public static String convertUnicodeToMarc8(byte[] data) {
        StringBuilder sb = new StringBuilder();
        int len = data.length;
        return sb.toString();
    }
}
