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
    
}
