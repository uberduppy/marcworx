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

package org.talwood.marcworx.containers.specs;

/**
 *
 * @author twalker
 */
public class TargetAudienceSpecs {
    public final static char UNDEFINED = 'X';
    public final static char AUDIENCE_GRADE_LEVEL = ' ';
    public final static char READING_GRADE_LEVEL = '0';
    public final static char INTEREST_AGE_LEVEL = '1';
    public final static char INTEREST_GRADE_LEVEL = '2';
    public final static char SPECIAL_AUDIENCE_LEVEL = '3';
    public final static char MOTIVATION_LEVEL = '4';
    public final static char NO_LEVEL = '8';
    
    public final static String UNDEFINED_DESC = "Undefined";
    public final static String AUDIENCE_GRADE_LEVEL_DESC = "Audience";
    public final static String READING_GRADE_LEVEL_DESC = "Reading grade level";
    public final static String INTEREST_AGE_LEVEL_DESC = "Interest age level";
    public final static String INTEREST_GRADE_LEVEL_DESC = "Interest grade level";
    public final static String SPECIAL_AUDIENCE_LEVEL_DESC = "Special audience";
    public final static String MOTIVATION_LEVEL_DESC = "Motivation";
    public final static String NO_LEVEL_DESC = "No display";
    
}
