package org.talwood.marcworx.locparser;

import java.util.List;
import org.talwood.marcworx.exception.ConstraintException;
import org.talwood.marcworx.locparser.constants.MarcTransformerSpecs;
import org.talwood.marcworx.locparser.containers.MarcTransformerStats;
import org.talwood.marcworx.locparser.elements.CodeElement;

public class MarcCharacterTransformer {

    private static int determineCharacterSet(byte[] data, int workingPosition, MarcTransformerStats stats) {
        int newAddon = 1;
        if(data[workingPosition] == MarcTransformerSpecs.ESCAPE) {
            if(workingPosition + 1 < data.length) {
                switch(data[workingPosition + 1]) {
                    case 0x28:
                    case 0x2c:
                        stats.setWorkingG0Set(MarcTransformerSpecs.DEFAULT_G0_SET);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x24:
                        if(workingPosition + 2 < data.length) {
                            switch(data[workingPosition + 2]) {
                                case 0x2c:
                                    stats.setWorkingG0Set(MarcTransformerSpecs.DEFAULT_G0_SET);
                                    stats.setMultibyte(true);
                                    newAddon = 3;
                                    break;
                                case 0x29:
                                    stats.setWorkingG0Set(MarcTransformerSpecs.DEFAULT_G1_SET);
                                    stats.setMultibyte(true);
                                    newAddon = 3;
                                case 0x2d:
                                    stats.setWorkingG0Set(MarcTransformerSpecs.DEFAULT_G1_SET);
                                    stats.setMultibyte(true);
                                    newAddon = 3;
                                    break;
                                default:
                                    stats.setWorkingG0Set(MarcTransformerSpecs.DEFAULT_G0_SET);
                                    stats.setMultibyte(true);
                                    newAddon = 2;
                                    break;
                                
                            }
                        } else {
                            stats.setWorkingG0Set(MarcTransformerSpecs.DEFAULT_G0_SET);
                            stats.setMultibyte(true);
                            newAddon = 2;
                        }
                        break;
                    case 0x29:
                    case 0x2d:
                        stats.setWorkingG0Set(MarcTransformerSpecs.DEFAULT_G1_SET);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x21:
                        if(workingPosition + 2 < data.length && data[workingPosition + 2] == 0x45) {
                            stats.setWorkingG0Set(MarcTransformerSpecs.EXTENDED_LATIN_ANSEL);
                            stats.setMultibyte(true);
                            newAddon = 3;
                        }
                        break;
                    case 0x45: // Extended ANSEL
                        stats.setWorkingG0Set(MarcTransformerSpecs.EXTENDED_LATIN_ANSEL);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x67: // Greek Symbols
                        stats.setWorkingG0Set(MarcTransformerSpecs.GREEK_SYMBOLS);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x62: // Subscripts
                        stats.setWorkingG0Set(MarcTransformerSpecs.SUBSCRIPTS);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x70: // Superscripts
                        stats.setWorkingG0Set(MarcTransformerSpecs.SUPERSCRIPTS);
                        stats.setMultibyte(false);
                        newAddon = 2;
                        break;
                    case 0x32: // Hebrew
                        stats.setWorkingG0Set(MarcTransformerSpecs.BASIC_HEBREW);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x4e: // Cyrillic
                        stats.setWorkingG0Set(MarcTransformerSpecs.BASIC_CYRILLIC);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x51: // Extended Cyrillic
                        stats.setWorkingG0Set(MarcTransformerSpecs.EXTENDED_CYRILLIC);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x33: // Basic Arabic
                        stats.setWorkingG0Set(MarcTransformerSpecs.BASIC_ARABIC);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x34: // Extended Arabic
                        stats.setWorkingG0Set(MarcTransformerSpecs.EXTENDED_ARABIC);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x53: // Basic Greek
                        stats.setWorkingG0Set(MarcTransformerSpecs.BASIC_GREEK);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                    case 0x31: // Asian
                        stats.setWorkingG0Set(MarcTransformerSpecs.EAST_ASIAN);
                        stats.setMultibyte(true);
                        newAddon = 2;
                        break;
                }
            }
        }
        return workingPosition + newAddon;
        
    }
    public static String convertMarc8ToUnicode(byte[] data) throws ConstraintException {
        StringBuilder sb = new StringBuilder();

        CodeTableParser parser = CodeTableParser.getCodeTableParser();
        // Use default settings
//        CodeTracker cdt = new CodeTracker();
        MarcTransformerStats stats = new MarcTransformerStats();
        
//        Vector<Character> multiChars = new Vector<Character>();
        
        // OK, each character is either "in or it's "out".
        int workingPosition = 0;
        int workingSet = MarcTransformerSpecs.DEFAULT_G0_SET;
        List<CodeElement> codeList = parser.findListForCodeTable(workingSet);
        while(workingPosition < data.length) {
            if(data[workingPosition] == MarcTransformerSpecs.ESCAPE) {
                // OK, starting new character set
                workingPosition = determineCharacterSet(data, workingPosition, stats);
                codeList = parser.findListForCodeTable(stats.getWorkingG0Set());
            } else {
                // This is a character in my current working set.
                // Rifle all possible characters...
                
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
