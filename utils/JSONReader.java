package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JSONReader {
    private File file;

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
        else if (!in.getName().matches(".*\\.json"))
            throw new IllegalArgumentException(String.format("File at path %s is not a JSON file", in.getName()));

        file = in;
    }

    /**
     * Parses the .json file by creating a JSONObject of that file
     *
     * @return <code>JSONObject</code>, the file
     * @throws IOException if an error occured reading in the file.
     */
    public JSONObject interpretFile() throws IOException {
        BufferedReader fi = new BufferedReader(new FileReader(file));

        // removes all whitespaces from the file that's not inside quotes
        StringBuilder json = new StringBuilder();
        String line = fi.readLine();
        while (line != null) {
            boolean inQuotes = false;
            for (int i = 0; i < line.length(); i++) {
                String let = line.substring(i, i + 1);
                if (let.equals("\""))
                    inQuotes = !inQuotes;

                if (inQuotes || let.matches("\\S"))
                    json.append(let);
            }
            line = fi.readLine();
        }

        // the file is one object, so create a JSONObject with that
        return new JSONObject(file.getName(), json.toString());
    }

    public static JSONObject interpretJSONString(String fileName, Reader inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(inputStream);
        StringBuilder json = new StringBuilder();

        String line = reader.readLine();
        while (line != null) {
            boolean inQuotes = false;
            for (int i = 0; i < line.length(); i++) {
                String let = line.substring(i, i + 1);
                if (let.equals("\""))
                    inQuotes = !inQuotes;

                if (inQuotes || let.matches("\\S"))
                    json.append(let);
            }
            line = reader.readLine();
        }

        // the file is one object, so create a JSONObject with that
        return new JSONObject(fileName, json.toString());
    }
}