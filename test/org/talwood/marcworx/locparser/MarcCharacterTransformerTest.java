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
import org.marc4j.util.AnselToUnicode;

/**
 *
 * @author twalker
 */
public class MarcCharacterTransformerTest {
    
    public MarcCharacterTransformerTest() {
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

    @Test
    public void testConvertMarc8ToUnicodeBasic() throws Exception {
        byte[] data = "abcd".getBytes();
        String result = MarcCharacterTransformer.convertMarc8ToUnicode(data);
        assertEquals("abcd", result);
    }
    @Test
    public void testConvertMarc8EuroToUnicode() throws Exception {
        byte[] data = {(byte)0xc8};
        String result = MarcCharacterTransformer.convertMarc8ToUnicode(data);
        assertEquals("Should find Euro", "\u20AC", result);
    }
    @Test
    public void testConvertMarc8InvertedExclamationMarcToUnicode() throws Exception {
        byte[] data = {(byte)0xc6};
        String result = MarcCharacterTransformer.convertMarc8ToUnicode(data);
        assertEquals("Should find Euro", "\u00A1", result);
    }
    @Test
    public void testConvertMarc8ToUnicodeG1Combining() throws Exception {
//        byte[] data = {(byte)0x1b, (byte)0x45, };
        byte[] data = {(byte)0xe1, (byte)0x65};
        String expected = AnselToUnicode.convert(new String(data));
//        byte[] data = "abcd".getBytes();
        String result = MarcCharacterTransformer.convertMarc8ToUnicode(data);
        byte[] exp = expected.getBytes();
        byte[] res = result.getBytes();
        assertEquals(expected, result);
    }
    /**
     * Test of convertMarc8ToUnicode method, of class MarcCharacterTransformer.
     */
    @Test
    public void testConvertMarc8ToUnicode() throws Exception {
//        System.out.println("convertMarc8ToUnicode");
//        byte[] data = null;
//        String expResult = "";
//        String result = MarcCharacterTransformer.convertMarc8ToUnicode(data);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of convertUnicodeToMarc8 method, of class MarcCharacterTransformer.
     */
    @Test
    public void testConvertUnicodeToMarc8() {
//        System.out.println("convertUnicodeToMarc8");
//        byte[] data = null;
//        String expResult = "";
//        String result = MarcCharacterTransformer.convertUnicodeToMarc8(data);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}