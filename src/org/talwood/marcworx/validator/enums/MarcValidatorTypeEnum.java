/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.talwood.marcworx.validator.enums;

/**
 *
 * @author twalker
 */
public enum MarcValidatorTypeEnum {
    UNNOWN(0),
    FIXED(1),
    SUBFIELD(2),
    TAG(3),
    ;

    private int index;
    private MarcValidatorTypeEnum(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }


}
