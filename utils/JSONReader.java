package utils;

import java.util.HashMap;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class JSONReader {
    private File file;

    private BufferedReader fi;
    private HashMap<String, Object> map;

    /**
     * Constructer for JSONReader. Parses file if given file is a JSON File.
     * @param in the JSON File
     * @throws IOException if any errors occur while reading in JSON File
     * @throws IllegalArgumentException if given file is not a JSON File
     */
    public JSONReader(File in)throws IOException, IllegalArgumentException{
        if (!in.getName().matches("(\\.json)$"))
            throw new IllegalArgumentException(String.format("File %s must be a .json file.\n", in.getName()));
                 
        // read in the JSON File
        this.file = in;
        fi = new BufferedReader(new FileReader(file));
        map = new HashMap<>();
        
        // parse the file one line at a time
        interpretFile(file);
    }

    public Object get(String key)
    {
        return map.get(key);
    }

    /**
     * Parses the JSON file for its keys and values and stores
     * them into a HashMap for public access
     * @param file JSON file being read in
     * @throws IOException if error occurs while reading in JSON file
     */
    private void interpretFile(File file)throws IOException {
        StringBuilder key = new StringBuilder();
        StringBuilder val = new StringBuilder();
        boolean isStr = false;
        boolean isKey = false;
        int c = 0;
  
        // parsing the file
        while (c != -1) {
            c = fi.read();
            String s = Character.toString(c);

            // if there is a key and value add it to the map
            if (s.matches("\\s") && !key.isEmpty() && !val.isEmpty())
                map.put(key.toString(), new JSONObject(val.toString()));
            
        }
    }

}