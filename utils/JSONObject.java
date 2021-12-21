package utils;

import java.util.HashMap;

/**
 * An object used to store JSON Objects. A low-priority thing for me to complete
 * because
 * the JSON Files used in this project don't contain JSON objects.
 */
public class JSONObject {
    private String name;
    private HashMap<String, Object> map;

    public JSONObject(String name, String content) {
        this.name = name;
        map = new HashMap<>();
        parseContent(content);
    }

    /**
     * Returns the value of given key in JSONObject
     * @param key the key to find the value of
     * @return the value from the key
     * @throws IllegalArgumentException if no such key can be found.
     */
    public Object get(String key) throws IllegalArgumentException {
        if (!map.containsKey(key))
            throw new IllegalArgumentException(
                    String.format("Key %s was not found in JSONObject listed.\nHere are the keys available: %s", key,
                            map.keySet()));
        return map.get(key);
    }

    /**
     * Algorithm for parsing the JSON string:
     *      1. read in the key
     *          a. store all characters from string until colon is reached
     *      2. read until comma or eof
     *      3. parse the value and add both key and value to the hashmap
     * Recursive algorithm because I hurt my head thinking of a non-recursive solution.
     * I'm sure there's a faster way to parse this but my head hurts thinking about it.
     * @param content
     */
    private void parseContent(String content) {
        int indexColon = content.indexOf(":");
        int indexComma = content.indexOf(",");
        String key = content.substring(1, indexColon - 1);
        String val;
        if (indexComma == -1) {
            val = content.substring(indexColon + 1);
            map.put(key, interpretVal(val));
        }
        else {
            val = content.substring(indexColon + 1, indexComma);
            map.put(key, interpretVal(val));
            parseContent(content.substring(indexComma + 1));
        }
    }

    /**
     * @param dat
     * @return <code>Object</code>, will be an instance of String, int, double,
     *         boolean, or null depending on the contents of dat.
     */
    private Object interpretVal(String dat) {
        // all known basic datatypes for JSON files: integer, floating-point, boolean, null, and String
        Object o;
        if (dat.matches("^\\d+")) // check for integer
            o = Integer.parseInt(dat);
        else if (dat.matches("\\d*\\.\\d+")) // check for floating-point number
            o = Double.parseDouble(dat);
        else if (dat.equals("true")) // check for boolean (true)
            o = true;
        else if (dat.equals("false")) // check for boolean (false)
            o = false;
        else if (dat.equals("null")) // check for null
            o = null;
        else // by default just store the value as a string
            o = dat.substring(dat.indexOf("\""), dat.lastIndexOf("\""));
        return o;
    }

    public String toString() {
        return String.format("{%s:%s}", name, map.toString());
    }
}
