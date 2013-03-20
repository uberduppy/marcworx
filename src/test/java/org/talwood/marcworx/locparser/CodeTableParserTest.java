/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.talwood.marcworx.locparser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.talwood.marcworx.locparser.constants.MarcTransformerSpecs;
import org.talwood.marcworx.locparser.containers.CodeElementMap;

/**
 *
 * @author twalker
 */
public class CodeTableParserTest {
    
    public CodeTableParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCodeTableParser method, of class CodeTableParser.
     */
    @Test
    public void testGetCodeTableParser() throws Exception {
        CodeTableParser result = CodeTableParser.getCodeTableParser();
        assertNotNull(result);
    }

    @Test
    public void testGetCodeTableParserLevelOne() throws Exception {
        CodeTableParser result = CodeTableParser.getCodeTableParser();
        assertNotNull(result);
        assertEquals(9, result.getContainer().getCodeTables().size());
    }
    
    @Test
    public void testGetCodeTableCodeCount() throws Exception {
        CodeTableParser result = CodeTableParser.getCodeTableParser();
        assertNotNull(result);
        assertEquals(16398, result.getElements().size());
    }
    
    @Test
    public void testGetCodeTablesMaxChars() throws Exception {
        CodeTableParser result = CodeTableParser.getCodeTableParser();
        assertNotNull(result);
        CodeElementMap map = result.findListForCodeTable(MarcTransformerSpecs.BASIC_ARABIC);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.BASIC_CYRILLIC);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.BASIC_GREEK);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.BASIC_HEBREW);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.DEFAULT_G0_SET);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.DEFAULT_G1_SET);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.EAST_ASIAN);
        assertEquals(6, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.EXTENDED_ARABIC);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.EXTENDED_CYRILLIC);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.EXTENDED_LATIN_ANSEL);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.GREEK_SYMBOLS);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.SUBSCRIPTS);
        assertEquals(2, map.determineLongestMarcCode());
        map = result.findListForCodeTable(MarcTransformerSpecs.SUPERSCRIPTS);
        assertEquals(2, map.determineLongestMarcCode());
    }
}