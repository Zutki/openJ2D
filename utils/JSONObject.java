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
     * TODO: Algorithm for parsing the JSON string:
     *      1. read in the key
     *          a. store all characters from string until colon is reached
     *      2. read until comma or eof
     *      3. parse the value and add both key and value to the hashmap
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
        // all known datatypes: integer, double, boolean, null, and String
        Object o;
        if (dat.matches("^\\d+"))
            o = Integer.parseInt(dat);
        else if (dat.matches("\\d*\\.\\d+"))
            o = Double.parseDouble(dat);
        else if (dat.equals("true"))
            o = true;
        else if (dat.equals("false"))
            o = false;
        else if (dat.equals("null"))
            o = null;
        else
            o = dat.substring(1, dat.length() - 1);
        return o;
    }

    public String toString() {
        return String.format("{%s:%s}", name, map.toString());
    }
}
