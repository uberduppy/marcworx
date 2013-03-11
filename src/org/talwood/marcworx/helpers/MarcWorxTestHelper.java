package org.talwood.marcworx.helpers;

import java.io.File;

public class MarcWorxTestHelper {
    public static File getTestFile(String filename) {
        File file = null;
        try {
            String fileName = MarcWorxFileHelper.locateResource(MarcWorxTestHelper.class, "org/talwood/marcworx/data/testdata/" + filename);
            file = new File(fileName);
        } catch (Exception ex) {
            // TODO Change this, perhaps a constraint exception?
            System.out.println("Error loading resource");
            ex.printStackTrace(System.out);
        }
        return file;
    }
}
