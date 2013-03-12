package org.talwood.marcworx.locparser.helpers;

public class MarcTransformerHelper {

    public static Integer convertCodeToInteger(String code) {
        return new Integer(Integer.parseInt(code, 16));
    }
    
}
