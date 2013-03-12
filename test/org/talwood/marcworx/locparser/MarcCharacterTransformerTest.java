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