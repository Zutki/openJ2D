package minecraft2d.utils.file;

import minecraft2d.utils.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * The ZipUtils class provides tools for working with zip files
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class ZipUtils {

    public static Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * Extracts a zip file to a specified directory and ignores and files/directories that are specified in ignoreList
     * @param zipLocation the location of the zip file to extract
     * @param extractToDir the location where to extract the files to
     * @param ignoreList any files or directories that match names specified in ignoreList will not be extracted, can be null
     * @return An array of the created files
     */
    public static ArrayList<File> extractZip(String zipLocation, String extractToDir, String[] ignoreList) throws IOException {
        FileInputStream fs;
        ZipInputStream zs;
        ZipEntry ze;
        ArrayList<File> outputArray = new ArrayList<File>();

        File extractionDir = new File(extractToDir);

        // check if the extraction directory exists, if not throw an exception
        if (!extractionDir.exists()) {
            throw new NoSuchFileException("The specified extraction directory does not exist!");
        }

        // check if the extraction directory is a directory, if not throw an exception
        if (!extractionDir.isDirectory()) {
            throw new IOException("The specified extraction directory is not a file!");
        }

        try {
            fs = new FileInputStream(zipLocation);
            zs = new ZipInputStream(new BufferedInputStream(fs));

            // iterate through each entry in the zip input stream
            while ((ze = zs.getNextEntry()) != null) {
                outputArray.add(createFile(extractToDir, ze, zs, ignoreList));
            }
            // close it since we are done with it
            zs.close();

        } catch (NoSuchFileException ex) {
            LOGGER.error("File "+ zipLocation +" does not exist.");
            ex.printStackTrace();
            System.exit(1);
        } catch (IOException ex) {
            LOGGER.error("Error while trying to read "+ zipLocation +" printing stack trace");
            ex.printStackTrace();
            System.exit(1);
        }

        return outputArray;
    }

    /**
     * Creates a file from a specified ZipEntry and ZipInputStream
     *
     * @param location the base dir the files will be extracted to
     * @param zipEntry       the ZipEntry to extract
     * @param zipInputStream the ZipInputStream
     * @param ignoreList list passed from extractZip for files/directories to ignore, can be null
     * @return the file created
     * @throws IOException when an IOException is caught
     */
    private static File createFile(String location, ZipEntry zipEntry, ZipInputStream zipInputStream, String[] ignoreList) throws IOException {
        // TODO (LOW PRIORITY): Find some way to make this more efficient
        
        // quick check so you don't have to make an empty string
        if (ignoreList != null) {
            // check if the file name is in the ignore list
            if (ignoreList.length != 0) { // check if we actually have to do this check
                for (String q : ignoreList) { // iterate through each item in the ignore list
                    if (ArrayUtils.contains(zipEntry.getName().split("/"), q)) { // if the file is in the ignore list
                        LOGGER.info("File " + zipEntry.getName() + " is in the ignore list, ignoring...");
                        return null;
                    }
                }
            }
        }
        String type = zipEntry.isDirectory() ? "directory" : "file";
        LOGGER.info("Creating "+type+": "+location+zipEntry.getName());
        File file = new File(location+zipEntry.getName());

        // if the file is a directory or not
        if (!zipEntry.isDirectory()) { // not a directory

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(zipInputStream.readAllBytes());

        }
        else { // is a directory
            // if the file is a directory then make it
            if (file.mkdirs() == false) {
                LOGGER.error("WAS UNABLE TO CREATE DIRECTORY");
                System.exit(1);
            }
        }
        return file;
    }
}