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
package org.talwood.marcworx.validator.constants;

import java.util.ArrayList;
import java.util.List;
import org.talwood.marcworx.helpers.TupleContainer;

public class MarcLimitersConstants {
    public final static int DISPLAY_ALL = -1;
    public final static int DISPLAY_NONE = 0;

    public final static int DISPLAY_NR_TAGS = 1;
    public final static int DISPLAY_OBS_TAGS = 1 << 1;
    public final static int DISPLAY_INV_TAGS = 1 << 2;
    public final static int DISPLAY_OBS_IND = 1 << 3;
    public final static int DISPLAY_INV_IND = 1 << 4;
    public final static int DISPLAY_NR_SUBS = 1 << 5;
    public final static int DISPLAY_OBS_SUBS = 1 << 6;
    public final static int DISPLAY_INV_SUBS = 1 << 7;
    public final static int DISPLAY_REQ_SUBS = 1 << 8;
    public final static int DISPLAY_TAG_DATA_VAL = 1 << 9;
    public final static int DISPLAY_SUB_DATA_VAL = 1 << 10;

    public final static String DISPLAY_NR_TAGS_DISPLAY = "Validate nonrepeatable tags";
    public final static String DISPLAY_OBS_TAGS_DISPLAY = "Validate obsolete tags";
    public final static String DISPLAY_INV_TAGS_DISPLAY = "Validate invalid tags";
    public final static String DISPLAY_OBS_IND_DISPLAY = "Validate obsolete indicators";
    public final static String DISPLAY_INV_IND_DISPLAY = "Validate invalid indicators";
    public final static String DISPLAY_NR_SUBS_DISPLAY = "Validate nonrepeatable subfields";
    public final static String DISPLAY_OBS_SUBS_DISPLAY = "Validate obsolete subfields";
    public final static String DISPLAY_INV_SUBS_DISPLAY = "Validate invalid subfields";
    public final static String DISPLAY_REQ_SUBS_DISPLAY = "Validate required subfields";
    public final static String DISPLAY_TAG_DATA_VAL_DISPLAY = "Validate tag data";
    public final static String DISPLAY_SUB_DATA_VAL_DISPLAY = "Validate subfield data";

    private static final List<TupleContainer<Long, String>> constantList;

    static {
        constantList = new ArrayList<TupleContainer<Long, String>>();

        addToList(DISPLAY_NR_TAGS, DISPLAY_NR_TAGS_DISPLAY);
        addToList(DISPLAY_OBS_TAGS, DISPLAY_OBS_TAGS_DISPLAY);
        addToList(DISPLAY_INV_TAGS, DISPLAY_INV_TAGS_DISPLAY);
        addToList(DISPLAY_OBS_IND, DISPLAY_OBS_IND_DISPLAY);
        addToList(DISPLAY_INV_IND, DISPLAY_INV_IND_DISPLAY);
        addToList(DISPLAY_NR_SUBS, DISPLAY_NR_SUBS_DISPLAY);
        addToList(DISPLAY_OBS_SUBS, DISPLAY_OBS_SUBS_DISPLAY);
        addToList(DISPLAY_INV_SUBS, DISPLAY_INV_SUBS_DISPLAY);
        addToList(DISPLAY_REQ_SUBS, DISPLAY_REQ_SUBS_DISPLAY);
        addToList(DISPLAY_TAG_DATA_VAL, DISPLAY_TAG_DATA_VAL_DISPLAY);
        addToList(DISPLAY_SUB_DATA_VAL, DISPLAY_SUB_DATA_VAL_DISPLAY);
    }

    private static void addToList(int flag, String display) {
        constantList.add(new TupleContainer<Long, String>(new Long(flag), display));
    }
    
    public static int buildConstant(boolean checkNonRepeatableTags, boolean checkObsoleteTags,
            boolean checkInvalidTags, boolean checkObsoleteIndicators, boolean checkInvalidIndicators,
            boolean checkNonRepeatableSubfields, boolean checkObsoleteSubfields,
            boolean checkInvalidSubfields, boolean checkRequiredSubfields,
            boolean checkTagData, boolean checkSubfieldData) {
        int result = 0;
        result += checkNonRepeatableTags ? DISPLAY_NR_TAGS : 0;
        result += checkObsoleteTags ? DISPLAY_OBS_TAGS : 0;
        result += checkInvalidTags ? DISPLAY_INV_TAGS : 0;
        result += checkObsoleteIndicators ? DISPLAY_OBS_IND : 0;
        result += checkInvalidIndicators ? DISPLAY_INV_IND : 0;
        result += checkNonRepeatableSubfields ? DISPLAY_NR_SUBS : 0;
        result += checkObsoleteSubfields ? DISPLAY_OBS_SUBS : 0;
        result += checkInvalidSubfields ? DISPLAY_INV_SUBS : 0;
        result += checkRequiredSubfields ? DISPLAY_REQ_SUBS : 0;
        result += checkTagData ? DISPLAY_TAG_DATA_VAL : 0;
        result += checkSubfieldData ? DISPLAY_SUB_DATA_VAL : 0;
        return result;
    }

    private MarcLimitersConstants() {}

    public static boolean containsFlag(int flag, int singleFlag) {
        return ((flag == DISPLAY_ALL) || ((flag & singleFlag) != 0));
    }

    public static List<TupleContainer<Long, String>> getConstantList() {
        return constantList;
    }

}
