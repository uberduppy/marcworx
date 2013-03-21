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

package org.talwood.marcworx.innovation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.talwood.marcworx.marc.constants.DataWorkConstants;

/**
 *
 * @author twalker
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NonFilingIndicatorFieldString {
    int indicatorToUse();
    // What subfield (or subfields) are required for this object
    boolean before();
    // What subfield (or subfields) are required for this object
    char subfield();
    // Do I need to strip punctuation?
    boolean stripPunctuation() default true;
    // Leading punctuation to strip
    String leadingPunctuation() default DataWorkConstants.STANDARD_FRONT_PUNCT_TO_STRIP;
    // Trailing punctuation to strip
    String trailingPunctuation() default DataWorkConstants.STANDARD_BACK_PUNCT_TO_STRIP;

}
