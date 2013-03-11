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
    /**
     * Test of getCodeTableParser method, of class CodeTableParser.
     */
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
}