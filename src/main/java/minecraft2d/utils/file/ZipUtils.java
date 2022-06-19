package minecraft2d.utils.file;

import minecraft2d.utils.ArrayUtils;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// URGENT TODO: Use paths instead of strings!
// BECAUSE THIS IS JANK to do with strings

/**
 * The ZipUtils class provides tools for working with zip files
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class ZipUtils {

    /**
     * Extracts a zip file to a specified directory and ignores and files/directories that are specified in ignoreFiles and ignoreDirs
     * @param zipLocation the location of the zip file to extract
     * @param extractionLocation the location where to extract the files to
     * @param ignoreFiles list of files to ignore
     * @param ignoreDirs list of directories to extract
     */
    public static void extractZip(String zipLocation, String extractionLocation, String[] ignoreFiles, String[] ignoreDirs) {
        FileInputStream fs;
        ZipInputStream zs;
        ZipEntry ze;

        // make sure the extraction location has a / at the end, if not add it
        if (!extractionLocation.endsWith("/")) {
            extractionLocation = extractionLocation + "/";
        }

        try {
            fs = new FileInputStream(zipLocation);
            zs = new ZipInputStream(new BufferedInputStream(fs));

            // make the directory we are extracting files to
            File file = new File(extractionLocation);
            // check if the directory already exists before making it
            if (!file.isDirectory()) { file.mkdir(); }

            while ((ze = zs.getNextEntry()) != null) {
                createFile(extractionLocation, ze, zs, ignoreFiles, ignoreDirs);
            }
            zs.close();

        } catch (NoSuchFileException ex) {
            System.out.println("File "+ zipLocation +" does not exist.");
            ex.printStackTrace();
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error while trying to read "+ zipLocation +" printing stack trace");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates a file from a specified ZipEntry and ZipInputStream
     *
     * @param zipEntry       the ZipEntry to extract
     * @param zipInputStream the ZipInputStream
     * @throws IOException when an IOException is caught
     */
    private static void createFile(String location, ZipEntry zipEntry, ZipInputStream zipInputStream, String[] ignoreFiles, String[] ignoreDirs) throws IOException {
        System.out.println("Creating file: "+location+zipEntry.getName());
        File file = new File(location+zipEntry.getName());

        // if the file is a directory or not
        if (!zipEntry.isDirectory()) {
            String fileName = file.getName().substring(file.getName().lastIndexOf("/"));

            // if the file is in the ignore list then return
            if (ArrayUtils.contains(ignoreFiles, fileName)) { return; }

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(zipInputStream.readAllBytes());
        }
        else {
            String dirName = file.getName().substring(file.getName().lastIndexOf(file.getParent()));
            // if the file is a directory then make it
            file.mkdir();
        }
    }
}
