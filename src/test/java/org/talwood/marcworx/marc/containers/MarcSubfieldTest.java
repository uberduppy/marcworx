package org.talwood.marcworx.marc.containers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MarcSubfieldTest {
    
    public MarcSubfieldTest() {
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
    public void testGetCode() {
        MarcSubfield instance = new MarcSubfield('a', "Data");
        assertEquals('a', instance.getCode());
    }

    @Test
    public void testSetCode() {
        MarcSubfield instance = new MarcSubfield('a', "Data");
        instance.setCode('b');
        // TODO review the generated test code and remove the default call to fail.
        assertEquals('b', instance.getCode());
    }

    @Test
    public void testGetData() {
        MarcSubfield instance = new MarcSubfield('a', "Data");
        assertEquals("Data", instance.getData());
    }

    @Test
    public void testSetData() {
        MarcSubfield instance = new MarcSubfield('a', "Data");
        instance.setData("MoreData");
        assertEquals("MoreData", instance.getData());
    }
}