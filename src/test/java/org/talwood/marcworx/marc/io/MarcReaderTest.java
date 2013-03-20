/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.talwood.marcworx.marc.io;

import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.talwood.marcworx.helpers.MarcWorxTestHelper;
import org.talwood.marcworx.marc.containers.MarcLeader;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.marc.enums.MarcFileReadStatus;

/**
 *
 * @author twalker
 */
public class MarcReaderTest {
    
    public MarcReaderTest() {
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
    public void testGetPreviousRecord() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            MarcRecord result = instance.getNextRecord();
            assertEquals(MarcFileReadStatus.VALID, result.getReadStatus());
            List<MarcTag> tags = result.getAllTags(245);
            assertEquals(1, tags.size());
            MarcTag tag = tags.get(0);
            assertNotNull(tag);
            assertEquals('1', tag.getFirstIndicator());
            assertEquals('0', tag.getSecondIndicator());
            MarcSubfield sub = tag.getSubfield('a', 1);
            assertNotNull(sub);
            assertEquals('a', sub.getCode());
            assertEquals("Come back, Georgie", sub.getData());
            MarcLeader leader = result.getLeader();
            assertNotNull(leader);
            assertEquals('n', leader.getRecordStatus());

            result = instance.getNextRecord();
            assertEquals(MarcFileReadStatus.VALID, result.getReadStatus());
            tags = result.getAllTags(245);
            assertEquals(1, tags.size());
            tag = tags.get(0);
            assertNotNull(tag);
            assertEquals('1', tag.getFirstIndicator());
            assertEquals('0', tag.getSecondIndicator());
            sub = tag.getSubfield('a', 1);
            assertNotNull(sub);
            assertEquals('a', sub.getCode());
            assertEquals("This was my valley", sub.getData());
            leader = result.getLeader();
            assertNotNull(leader);
            assertEquals('n', leader.getRecordStatus());
            
            result = instance.getPreviousRecord();
            assertEquals(MarcFileReadStatus.VALID, result.getReadStatus());
            tags = result.getAllTags(245);
            assertEquals(1, tags.size());
            tag = tags.get(0);
            assertNotNull(tag);
            assertEquals('1', tag.getFirstIndicator());
            assertEquals('0', tag.getSecondIndicator());
            sub = tag.getSubfield('a', 1);
            assertNotNull(sub);
            assertEquals('a', sub.getCode());
            assertEquals("Come back, Georgie", sub.getData());
            leader = result.getLeader();
            assertNotNull(leader);
            assertEquals('n', leader.getRecordStatus());
            
        } finally {
            if(instance != null) {
                instance.close();
            }
        }
    }

    /**
     * Test of getNthRecord method, of class MarcReader.
     */
    @Test
    public void testGetNthRecord() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            MarcRecord result = instance.getNthRecord(2);
            assertEquals(MarcFileReadStatus.VALID, result.getReadStatus());
            List<MarcTag> tags = result.getAllTags(245);
            assertEquals(1, tags.size());
            MarcTag tag = tags.get(0);
            assertNotNull(tag);
            assertEquals('1', tag.getFirstIndicator());
            assertEquals('0', tag.getSecondIndicator());
            MarcSubfield sub = tag.getSubfield('a', 1);
            assertNotNull(sub);
            assertEquals('a', sub.getCode());
            assertEquals("This was my valley", sub.getData());
            MarcLeader leader = result.getLeader();
            assertNotNull(leader);
            assertEquals('n', leader.getRecordStatus());

        } finally {
            if(instance != null) {
                instance.close();
            }
        }
    }

    /**
     * Test of getNextRecord method, of class MarcReader.
     */
    @Test
    public void testGetNextRecord() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            MarcRecord result = instance.getNextRecord();
            assertEquals(MarcFileReadStatus.VALID, result.getReadStatus());
            List<MarcTag> tags = result.getAllTags(245);
            assertEquals(1, tags.size());
            MarcTag tag = tags.get(0);
            assertNotNull(tag);
            assertEquals('1', tag.getFirstIndicator());
            assertEquals('0', tag.getSecondIndicator());
            MarcSubfield sub = tag.getSubfield('a', 1);
            assertNotNull(sub);
            assertEquals('a', sub.getCode());
            assertEquals("Come back, Georgie", sub.getData());
            MarcLeader leader = result.getLeader();
            assertNotNull(leader);
            assertEquals('n', leader.getRecordStatus());

            result = instance.getNextRecord();
            assertEquals(MarcFileReadStatus.VALID, result.getReadStatus());
            tags = result.getAllTags(245);
            assertEquals(1, tags.size());
            tag = tags.get(0);
            assertNotNull(tag);
            assertEquals('1', tag.getFirstIndicator());
            assertEquals('0', tag.getSecondIndicator());
            sub = tag.getSubfield('a', 1);
            assertNotNull(sub);
            assertEquals('a', sub.getCode());
            assertEquals("This was my valley", sub.getData());
            leader = result.getLeader();
            assertNotNull(leader);
            assertEquals('n', leader.getRecordStatus());
        } finally {
            if(instance != null) {
                instance.close();
            }
        }
    }

    /**
     * Test of fastFileScan method, of class MarcReader.
     */
    @Test
    public void testFastFileScan() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            assertEquals(MarcFileReadStatus.VALID, instance.fastFileScan());
            assertEquals(MarcFileReadStatus.VALID, instance.fastFileScan());
            assertEquals(MarcFileReadStatus.VALID, instance.fastFileScan());
            assertEquals(MarcFileReadStatus.ALLDONE, instance.fastFileScan());
        } finally {
            if(instance != null) {
                instance.close();
            }
        }
    }

    /**
     * Test of isEOF method, of class MarcReader.
     */
    @Test
    public void testIsEOF() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            assertEquals(MarcFileReadStatus.VALID, instance.fastFileScan());
            assertFalse(instance.isEOF());
            assertEquals(MarcFileReadStatus.VALID, instance.fastFileScan());
            assertFalse(instance.isEOF());
            assertEquals(MarcFileReadStatus.VALID, instance.fastFileScan());
            assertFalse(instance.isEOF());
            assertEquals(MarcFileReadStatus.ALLDONE, instance.fastFileScan());
            assertTrue(instance.isEOF());
        } finally {
            if(instance != null) {
                instance.close();
            }
        }
    }

    /**
     * Test of getRecordNumber method, of class MarcReader.
     */
    @Test
    public void testGetRecordNumber() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            MarcRecord record = instance.getNextRecord();
            assertEquals(1, instance.getRecordNumber());
            record = instance.getNextRecord();
            assertEquals(2, instance.getRecordNumber());
        } finally {
            if(instance != null) {
                instance.close();
            }
        }
    }

}