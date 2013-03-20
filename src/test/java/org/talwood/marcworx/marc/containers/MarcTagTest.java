/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.talwood.marcworx.marc.containers;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.talwood.marcworx.marc.constants.MarcSubfieldConstants;

/**
 *
 * @author twalker
 */
public class MarcTagTest {
    
    public MarcTagTest() {
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
    public void testGetTagNumber() {
        MarcTag instance = new MarcTag(100, "");
        assertEquals(100, instance.getTagNumber());
    }

    /**
     * Test of setTagNumber method, of class MarcTag.
     */
    @Test
    public void testSetTagNumber() {
        MarcTag instance = new MarcTag(50, "");
        instance.setTagNumber(100);
        assertEquals(100, instance.getTagNumber());
    }

    /**
     * Test of getTagData method, of class MarcTag.
     */
    @Test
    public void testGetTagData() {
        MarcTag instance = new MarcTag(50, "Beer");
        assertEquals("Beer", instance.getTagData());
    }

    /**
     * Test of setTagData method, of class MarcTag.
     */
    @Test
    public void testSetTagData() {
        MarcTag instance = new MarcTag(50, "Beer");
        instance.setTagData("Fred");
        assertEquals("Fred", instance.getTagData());
    }

    /**
     * Test of getFirstIndicator method, of class MarcTag.
     */
    @Test
    public void testGetFirstIndicator() {
        MarcTag instance = new MarcTag(50, "12" + MarcSubfieldConstants.SUBFIELD_CODE + "aData");
        assertEquals('1', instance.getFirstIndicator());
    }

    /**
     * Test of setFirstIndicator method, of class MarcTag.
     */
    @Test
    public void testSetFirstIndicator() {
        MarcTag instance = new MarcTag(50, "32" + MarcSubfieldConstants.SUBFIELD_CODE + "aData");
        instance.setFirstIndicator('1');
        assertEquals('1', instance.getFirstIndicator());
    }

    /**
     * Test of getSecondIndicator method, of class MarcTag.
     */
    @Test
    public void testGetSecondIndicator() {
        MarcTag instance = new MarcTag(50, "12" + MarcSubfieldConstants.SUBFIELD_CODE + "aData");
        assertEquals('2', instance.getSecondIndicator());
    }

    /**
     * Test of setSecondIndicator method, of class MarcTag.
     */
    @Test
    public void testSetSecondIndicator() {
        MarcTag instance = new MarcTag(50, "32" + MarcSubfieldConstants.SUBFIELD_CODE + "aData");
        instance.setSecondIndicator('1');
        assertEquals('1', instance.getSecondIndicator());
    }

    /**
     * Test of getSubfields method, of class MarcTag.
     */
    @Test
    public void testGetSubfields_0args() {
        MarcTag instance = new MarcTag(50, "32" + MarcSubfieldConstants.SUBFIELD_CODE + "aData" + MarcSubfieldConstants.SUBFIELD_CODE + "cMoreData");
        List<MarcSubfield> result = instance.getSubfields();
        assertEquals(2, result.size());
    }

}