/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.talwood.marcworx.validator;

import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.talwood.marcworx.helpers.MarcWorxTestHelper;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcTag;
import org.talwood.marcworx.marc.io.MarcReader;
import org.talwood.marcworx.validator.constants.DisplayFlagSettingsElement;
import org.talwood.marcworx.validator.elements.MarcTagValue;
import org.talwood.marcworx.validator.elements.MarcValidatorValue;
import org.talwood.marcworx.validator.results.MarcValidatorResultRow;
import org.talwood.marcworx.validator.results.MarcValidatorResultType;

/**
 *
 * @author twalker
 */
public class MarcValidatorProcessorTest {
    
    public MarcValidatorProcessorTest() {
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
     * Test of validateRecord method, of class MarcValidatorProcessor.
     */
    @Test
    public void testValidateBibliographicRecord() throws Exception {
        MarcReader instance = null;
        try {
            File file = MarcWorxTestHelper.getTestFile("testdata.001");
            assertNotNull(file);
            assertTrue(file.exists());
            instance = new MarcReader(file);
            MarcRecord record = instance.getNextRecord();
            MarcValidatorContainer container = MarcValidatorContainer.getValidator();
            assertNotNull(container);
            MarcValidatorValue value = container.getElementForRecord(record);
            assertNotNull(value);
            DisplayFlagSettingsElement flags = new DisplayFlagSettingsElement();
            List<MarcValidatorResultRow> result = MarcValidatorProcessor.validateRecord(value, record, true, flags.generateValidatorFlag());
            assertNotNull(result);
            assertEquals(14, result.size());
            MarcValidatorResultRow rr = result.get(0);
            assertNotNull(rr);
            assertEquals(6, rr.getTagNumber());
            assertEquals("'0' is not a valid element for Illustrations (up to 4)", rr.getDisplayData());
            assertEquals("Data \"'0' is not a valid element for Illustrations (up to 4)\".", rr.getMessage());
            assertEquals(MarcValidatorResultType.INVALID_FIXED_DATA, rr.getType());
        } finally {
            if(instance != null) {
                instance.close();
            }
        }
    }
}