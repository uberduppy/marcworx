/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.talwood.marcworx.innovation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.talwood.marcworx.helpers.MarcWorxTestHelper;
import org.talwood.marcworx.innovation.containers.TitleTransformerElement;
import org.talwood.marcworx.marc.constants.MarcSubfieldConstants;
import org.talwood.marcworx.marc.constants.RecordTypeConstants;
import org.talwood.marcworx.marc.containers.MarcLeader;
import org.talwood.marcworx.marc.containers.MarcRecord;
import org.talwood.marcworx.marc.containers.MarcSubfield;
import org.talwood.marcworx.marc.containers.MarcTag;

/**
 *
 * @author twalker
 */
public class MarcRecordTransformerTest {
    
    public MarcRecordTransformerTest() {
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
     * Test of toXml method, of class MarcRecordTransformer.
     */
    @Test
    public void testToXml() throws Exception {
        MarcRecord record = new MarcRecord(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_BOOK);
        MarcLeader leader = record.getLeader();
        leader.setPosition18('i');
        record.setLeader(leader);
        record.addOrUpdateTag(new MarcTag(8, "121299s1990                             "));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(10, ' ', ' ', new MarcSubfield('a', "   76372665")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(20, ' ', ' ', new MarcSubfield('a', "0356051056")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(20, ' ', ' ', new MarcSubfield('a', "0590444255")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(22, ' ', ' ', new MarcSubfield('a', "0361-0845")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(22, ' ', ' ', new MarcSubfield('a', "0072-9000")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(40, ' ', ' ', new MarcSubfield('e', "rda")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(100, '1', '0', new MarcSubfield('a', "Benedict"), new MarcSubfield('b', "XVI,"), new MarcSubfield('c', "Pope,"), new MarcSubfield('d', "1928-")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(245, '0', '4', new MarcSubfield('a', "The Juggernaut :"), new MarcSubfield('h', "[electronic resource]"), new MarcSubfield('b', "a treasury of poems /"), new MarcSubfield('c', "compilation by Fred Jones"), new MarcSubfield('k', "First form"), new MarcSubfield('k', "Second Form")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(264, ' ', '1', new MarcSubfield('a', "New York :"), new MarcSubfield('b', "George Publishing"), new MarcSubfield('c', "1957")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(264, ' ', '4', new MarcSubfield('c', "1995")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(521, '0', ' ', new MarcSubfield('a', "3.1"), new MarcSubfield('b', "Follett")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(521, '3', ' ', new MarcSubfield('a', "[Green,.]"), new MarcSubfield('b', "Special")));
        record.addOrUpdateTag(new MarcTag(526, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "aProgramName" + MarcSubfieldConstants.SUBFIELD_CODE + "b5-10." + MarcSubfieldConstants.SUBFIELD_CODE + "c6.0"+ MarcSubfieldConstants.SUBFIELD_CODE + "d75."+ MarcSubfieldConstants.SUBFIELD_CODE + "5FOLLY" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 1" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 2" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 3" ));
        record.addOrUpdateTag(new MarcTag(526, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "aAnotherName" + MarcSubfieldConstants.SUBFIELD_CODE + "b8-12." + MarcSubfieldConstants.SUBFIELD_CODE + "c6.0"+ MarcSubfieldConstants.SUBFIELD_CODE + "d75."+ MarcSubfieldConstants.SUBFIELD_CODE + "5FOL" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 4" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 5" ));

        MarcRecordTransformer instance = new MarcRecordTransformer(record);
        assertFalse(instance.hasProcessingErrors());

        System.out.print(instance.toXml());
    }

    /**
     * Test of getTitle method, of class MarcRecordTransformer.
     */
    @Test
    public void testGetTitle() throws Exception {
        MarcRecord record = new MarcRecord(RecordTypeConstants.RECORD_TYPE_BIBLIOGRAPHIC_BOOK);
        MarcLeader leader = record.getLeader();
        leader.setPosition18('i');
        record.setLeader(leader);
        record.addOrUpdateTag(new MarcTag(8, "121299s1990                             "));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(10, ' ', ' ', new MarcSubfield('a', "   76372665")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(20, ' ', ' ', new MarcSubfield('a', "0356051056")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(20, ' ', ' ', new MarcSubfield('a', "0590444255")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(22, ' ', ' ', new MarcSubfield('a', "0361-0845")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(22, ' ', ' ', new MarcSubfield('a', "0072-9000")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(40, ' ', ' ', new MarcSubfield('e', "rda")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(100, '1', '0', new MarcSubfield('a', "Benedict"), new MarcSubfield('b', "XVI,"), new MarcSubfield('c', "Pope,"), new MarcSubfield('d', "1928-")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(245, '0', '4', new MarcSubfield('a', "The Juggernaut :"), new MarcSubfield('h', "[electronic resource]"), new MarcSubfield('b', "a treasury of poems /"), new MarcSubfield('c', "compilation by Fred Jones")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(264, ' ', '1', new MarcSubfield('a', "New York :"), new MarcSubfield('b', "George Publishing"), new MarcSubfield('c', "1957")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(264, ' ', '4', new MarcSubfield('c', "1995")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(521, '0', ' ', new MarcSubfield('a', "3.1"), new MarcSubfield('b', "Follett")));
        record.addOrUpdateTag(MarcWorxTestHelper.buildMarcTag(521, '3', ' ', new MarcSubfield('a', "[Green,.]"), new MarcSubfield('b', "Special")));
        record.addOrUpdateTag(new MarcTag(526, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "aProgramName" + MarcSubfieldConstants.SUBFIELD_CODE + "b5-10." + MarcSubfieldConstants.SUBFIELD_CODE + "c6.0"+ MarcSubfieldConstants.SUBFIELD_CODE + "d75."+ MarcSubfieldConstants.SUBFIELD_CODE + "5FOLLY" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 1" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 2" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 3" ));
        record.addOrUpdateTag(new MarcTag(526, "0 " + MarcSubfieldConstants.SUBFIELD_CODE + "aAnotherName" + MarcSubfieldConstants.SUBFIELD_CODE + "b8-12." + MarcSubfieldConstants.SUBFIELD_CODE + "c6.0"+ MarcSubfieldConstants.SUBFIELD_CODE + "d75."+ MarcSubfieldConstants.SUBFIELD_CODE + "5FOL" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 4" + MarcSubfieldConstants.SUBFIELD_CODE + "zNote 5" ));

        MarcRecordTransformer instance = new MarcRecordTransformer(record);
        assertFalse(instance.hasProcessingErrors());
        
        TitleTransformerElement title = instance.getTitle();
        assertNotNull(title);
        assertEquals("Juggernaut", title.getTitle());
        assertEquals("The", title.getArticle());

    }

}