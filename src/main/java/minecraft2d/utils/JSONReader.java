package utils;

import java.io.*;

/**
 * This is a class used to read a .json file or a .json String, returning a JSONObject with the parsed content.
 *
 * @author samminhch
 * @see JSONObject
 * @since December 9, 2021
 */
public class JSONReader {
    /**
     * Parses the .json file by creating a JSONObject of that file
     *
     * @return <code>JSONObject</code>, the file
     * @throws IOException              if an error occurred reading in the file.
     * @throws IllegalArgumentException if file given is not a valid .json file.
     * @see JSONObject
     */
    public static JSONObject interpretFile(File file) throws IOException, IllegalArgumentException {
        // file validation
        String inName = file.getName().contains("/") ? file.getName() : "./" + file.getName();
        if (!file.exists())
            throw new IllegalArgumentException(String.format("File at path %s doesn't exist.", inName));
        else if (!file.getName().matches(".*\\.json"))
            throw new IllegalArgumentException(String.format("File at path %s is not a JSON file", file.getName()));


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
        fi.close();

        // the file is one object, so create a JSONObject with that
        return new JSONObject(file.getName(), json.toString());
    }

    /**
     * This interprets a .json file if it's passed through as a stream.
     *
     * @param fileName          name of the .json file
     * @param inputStreamReader stream to read the .json contents from
     * @return a JSONObject of the .json file that's being passed
     * @throws IOException If something goes wrong while reading in inputStreamReader
     */
    public static JSONObject interpretJSONString(String fileName, Reader inputStreamReader) throws IOException {
        BufferedReader reader = new BufferedReader(inputStreamReader);
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
        reader.close();

        // the file is one object, so create a JSONObject with that
        return new JSONObject(fileName, json.toString());
    }
}