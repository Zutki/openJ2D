package utils;

import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class JSONReader {
    private File file;
    private ArrayList<JSONObject> args;

    /**
     * Constructer for JSONReader. Parses file if given file is a JSON File.
     * 
     * @param in the JSON File
     * @throws IOException              if any errors occur while reading in JSON
     *                                  File
     * @throws IllegalArgumentException if given file is not a JSON File
     */
    public JSONReader(File in) throws IOException, IllegalArgumentException {
        // file validation
        String inName = in.getName().contains("/") ? in.getName() : "./" + in.getName();
        if (!in.exists())
            throw new IllegalArgumentException(String.format("File at path %s doesn't exist.", inName));
        else if (!in.getName().matches(".json$"))
            throw new IllegalArgumentException(String.format("File at path %s is not a JSON file", in.getName()));
        else if (in.canRead())
            throw new IllegalArgumentException(String.format("File at path %s cannot be read.", inName));

        // read in the JSON File
        file = in;

        // parse the file one line at a time
        args = new ArrayList<JSONObject>();
        interpretFile(file);
    }

    /**
     * @return all objets in the JSON file
     */
    public JSONObject[] getObjects() {
        return (JSONObject[]) args.toArray();
    }

    /**
     * Parses the JSON file for its keys and values and stores them into a HashMap
     * for public access
     * 
     * @param file JSON file being read in
     * @throws IOException if error occurs while reading in JSON file
     */
    private void interpretFile(File file) throws IOException {
        BufferedReader fi = new BufferedReader(new FileReader(file));
        StringBuilder json = new StringBuilder();

        String line = "";
        while (line != null) {
            line = fi.readLine();
            line.replaceAll("\\s", "");
            json.append(line);
        }
        makeObjects(json.toString());
    }

    private void makeObjects(String str) {
        
    }
}