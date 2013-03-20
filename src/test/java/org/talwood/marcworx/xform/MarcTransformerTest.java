/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.talwood.marcworx.xform;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.talwood.marcworx.helpers.MarcWorxDataHelper;
import org.talwood.marcworx.helpers.MarcWorxTestHelper;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.io.MarcReader;

/**
 *
 * @author twalker
 */
public class MarcTransformerTest {
    
    public MarcTransformerTest() {
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
     * Test of translateMarcToMods method, of class MarcTransformer.
     */
    @Test
    public void testTranslateMarcToMods() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            MarcRecord record = instance.getNextRecord();
            MarcTransformer xformer = MarcTransformer.getTransformer();
            String result = xformer.translateMarcToMods(MarcWorxDataHelper.convertMarcRecordToMarcXml(record));
            assertNotNull(result);
            assertEquals(result, xformer.translateMarcToMods(record));
//            System.out.print(result);
        } finally {
            if(instance != null) {
                instance.close();
            }
        }
    }

    /**
     * Test of translateMarcToDublinCoreRdf method, of class MarcTransformer.
     */
    @Test
    public void testTranslateMarcToDublinCoreRdf() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            MarcRecord record = instance.getNextRecord();
            MarcTransformer xformer = MarcTransformer.getTransformer();
            String result = xformer.translateMarcToDublinCoreRdf(MarcWorxDataHelper.convertMarcRecordToMarcXml(record));
            assertNotNull(result);
//            System.out.print(result);
        } finally {
            if(instance != null) {
                instance.close();
            }
        }
        
    }

    /**
     * Test of translateMarcToDublinCoreSrw method, of class MarcTransformer.
     */
    @Test
    public void testTranslateMarcToDublinCoreSrw() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            MarcRecord record = instance.getNextRecord();
            MarcTransformer xformer = MarcTransformer.getTransformer();
            String result = xformer.translateMarcToDublinCoreSrw(MarcWorxDataHelper.convertMarcRecordToMarcXml(record));
            assertNotNull(result);
//            System.out.print(result);
        } finally {
            if(instance != null) {
                instance.close();
            }
        }
    }
}