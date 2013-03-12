/* MarcWorx MARC Library - Utilities for manipulation of MARC records
    Copyright (C) 2013  Todd Walker, Talwood Solutions

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.talwood.marcworx.helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

public class MarcWorxFileHelper {
    public static final java.lang.String ENCODING_ISO_8859_1 = "ISO_8859-1";
    public static final java.lang.String ENCODING_UTF8 = "UTF-8";

    public static void closeNoThrow(InputStream s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (Exception ex) {
        }
    }

    public static void closeNoThrow(RandomAccessFile s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (Exception ex) {
        }
    }

/*----------------------------------------------------------------------------*/
    public static void closeNoThrow(OutputStream s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (Exception ex) {
        }
    }

/*----------------------------------------------------------------------------*/
    public static void closeNoThrow(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (Exception ex) {
        }
    }

/*----------------------------------------------------------------------------*/
    public static void closeNoThrow(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (Exception ex) {
        }
    }

    public static File createTempFile(String fileName, String fileExtWithPeriod) throws IOException {
        // This wrapper was created to ensure the deleteOnExit() was called to
        // remove this temporary file
        File tempFile = File.createTempFile(fileName, fileExtWithPeriod);
        tempFile.deleteOnExit(); // This will delete this temp file when the
                                    // java vm exits
        return tempFile;
    }


    public static String getTempDirectory() {
        //get the TMP directory
        String tempDir = System.getProperty("java.io.tmpdir");
        if (tempDir != null) {
            tempDir = tempDir.replace('\\', '/');
        }
        return tempDir;
    }

    public static boolean copyFile(String src, String dest) throws IOException {
        return copyFile(new File(src), new File(dest));
    }

    public static boolean copyFile(File srcFile, File destFile) throws IOException {
        boolean result = true;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        // First let's see if the directory specified in dest exists, if not
        // create it
        File pFile = destFile.getParentFile();

        if (pFile != null && !pFile.exists()) {
            pFile.mkdirs();
        }
        // now copy the file over
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);
            byte[] buffer = new byte[32768];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException ex) {
            result = false;
        } finally {
            closeNoThrow(bis);
            closeNoThrow(bos);
            closeNoThrow(fis); // make sure these close - don't count on bis close
            closeNoThrow(fos); // make sure these clsoe - don't count on bos close
        }
        return result;
    }
    
    public static String getFileNameOnlyFromFileObject(File fileToProcess) {
        String result = fileToProcess.getAbsolutePath();
        int index = result.lastIndexOf("/");
        if(index == -1) {
            index = result.lastIndexOf("\\");
        }
        if(index != -1 && (index + 1) < result.length()) {
            result = result.substring(index + 1);
        }
        
        return result;
    }
    
    public static String locateResource(Class aClass, String name) throws IOException {
        return locateResource(aClass, name, null, true, true);
    }
    
    public static String locateResource(Class aClass, String name, String destPath, boolean deleteOnExit, boolean addRandomNumber) throws IOException {
        String resource = null;
        URL theUrl = aClass.getClassLoader().getResource(name);
        if (theUrl != null) {
            resource = copyResource(theUrl, destPath, deleteOnExit, addRandomNumber);
        }
        return resource;
    }
    
    public static String copyResource(URL theUrl, String destinationFileDir, boolean deleteOnExit, boolean addRandomNumber) throws IOException {
        return copyResourceFile(theUrl, destinationFileDir, deleteOnExit, addRandomNumber).getPath();
    }
    
/*----------------------------------------------------------------------------*/
    public static String getExtension(String fileName) {
        String result = null;
        if (MarcWorxStringHelper.isNotEmpty(fileName)) {
            int lastPeriod = fileName.lastIndexOf(".");
            if (lastPeriod != -1 && fileName.length() != (lastPeriod + 1)) {
                result = MarcWorxStringHelper.rightMost(fileName, fileName.length()
                    - lastPeriod - 1);
            }
        }
        return result;
    }
    
    public static void copyResource(URL theUrl, File destinationFile, boolean deleteOnExit) throws IOException {
        InputStream input = null;
        OutputStream output = null;

        try {
            int     bytesRead = 0;
            byte    data[] = new byte[1024];

            File parent = destinationFile.getParentFile();
            parent.mkdirs();

            input = new BufferedInputStream(theUrl.openStream());
            output = new BufferedOutputStream(new FileOutputStream(destinationFile));

            // -1 means end of file
            while(bytesRead != -1) {
                bytesRead = input.read(data);
                if(bytesRead > 0) {
                    output.write(data, 0, bytesRead);
                }
            }
        }
        finally {
            try {
                if(input != null) {
                    input.close();
                }
            }
            finally {
                try {
                    if(output != null) {
                        output.close();
                    }
                }
                finally {
                    if(deleteOnExit && destinationFile != null) {
                        // this will delete this temp file when the java vm exits
                        destinationFile.deleteOnExit();
                    }
                }
            }
        }
    }
    
    public static File copyResourceFile(URL theUrl, String destinationFileDir, 
        boolean deleteOnExit, boolean addRandomNumber) throws IOException {
        
        File theFile = null;

        String extension = getExtension(theUrl.toString());
        if (!MarcWorxStringHelper.isEmpty(extension)) {
            extension = "." + extension;
        }
        if (destinationFileDir == null) {
            if (extension == null) {
                extension = "";
            }
            theFile = File.createTempFile("test", "temp" + extension);
            // this will delete this temp file when the java vm exits
            theFile.deleteOnExit();
        } else {
            String fileName = getFileNameWithoutExtension(theUrl.toString());
            if (!destinationFileDir.endsWith("\\") && !destinationFileDir.endsWith("/")) {
                destinationFileDir = destinationFileDir + "//";
            }
            
            if ( addRandomNumber ){
                File file = new File(destinationFileDir);
                file.mkdirs();
                theFile = File.createTempFile(fileName, extension, file);
            } else {
                theFile = new File(destinationFileDir + fileName + extension);
            }
        }

        copyResource(theUrl, theFile, deleteOnExit);

        return theFile;
    }
    
    public static String getFileNameWithoutExtension(String fileName) {
        String result = null;

        if (MarcWorxStringHelper.isNotEmpty(fileName)) {
            // Chop off any path component
            int pos = MarcWorxStringHelper.reverseFindIndexOfAny(fileName, "\\/", fileName.length());
            if (pos != -1) {
                // If found...then RIP off all of the text before it
                result = fileName.substring(pos+1, fileName.length());
            } else {
                result = fileName;
            }

            // Chop off the extension
            pos = result.lastIndexOf( "." );
            if (pos != -1) {
                result = result.substring(0, pos);
            }
        }

        return result;
    }
    

    
}
