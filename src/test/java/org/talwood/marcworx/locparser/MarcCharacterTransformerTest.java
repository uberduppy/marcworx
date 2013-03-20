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
        byte[] data = {(byte)0xe1, (byte)0x65};
        String expected = "\u00E8";
        String result = MarcCharacterTransformer.convertMarc8ToUnicode(data);
        byte[] exp = expected.getBytes();
        byte[] res = result.getBytes();
        assertEquals(expected, result);
    }

    @Test
    public void testConvertMarc8ToUnicodeHebrew() throws Exception {
        
    }
    @Test
    public void testConvertMarc8ToUnicodeEastAsian() throws Exception {
        byte[] data = {(byte)0x61, (byte)0x1b, (byte)0x31, (byte)0x21, (byte)0x30, (byte)0x23, (byte)0x21, (byte)0x30, (byte)0x25, (byte)0x1b, (byte)0x42, (byte)0x65};
        String expected = "a\u4E03\u4E0Be";
        String result = MarcCharacterTransformer.convertMarc8ToUnicode(data);
        assertEquals(expected, result);
    }

}