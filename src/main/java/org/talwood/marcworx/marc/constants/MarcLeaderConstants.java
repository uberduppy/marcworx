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
package org.talwood.marcworx.marc.constants;

public class MarcLeaderConstants {
    public final static int LEADER_LENGTH = 24;
    
    public static final int UNKNOWN = 0;
    public static final int BIBLIOGRAPHIC = 1;
    public static final int AUTHORITY = 2;
    public static final int HOLDINGS = 3;
    public static final int CLASSIFICATION = 4;
    public static final int COMMUNITY = 5;

    public static final String DESC_UNKNOWN = "unknown";
    public static final String DESC_BIBLIOGRAPHIC = "Bibliographic";
    public static final String DESC_AUTHORITY = "Authority";
    public static final String DESC_HOLDINGS = "Holdings";
    public static final String DESC_CLASSIFICATION = "Classification";
    public static final String DESC_COMMUNITY = "Community";

    public static final char CHARACTER_CODING_SCHEME_MARC_8 = ' ';
    public static final char CHARACTER_CODING_SCHEME_UTF8 = 'a';
    
    public static final char DESC_CAT_FORM_ISBD_NO_PUNCT = 'c';
    public static final char DESC_CAT_FORM_ISBD_PUNCT = 'i';
    
}
