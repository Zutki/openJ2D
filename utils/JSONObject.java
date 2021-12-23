package utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An object used to store JSON Objects. A low-priority thing for me to complete
 * because
 * the JSON Files used in this project don't contain JSON objects.
 */
public class JSONObject {
    private final String name;
    private final HashMap<String, Object> map;
    // TODO: fix the regex below; it doesn't work for nested curly brackets!
    private final String JSON_COMMA_SPLITTER = "(,)(?=\")(?=(((?!\\]).)*\\[)|[^\\[\\]]*$)(?=(((?!\\}).)*\\{)|[^\\{\\}]*$)";

    // TODO: fix the regex below; it doesn't work for nested curly brackets!
    private final String JSON_COMMA_SPLITTER = "(,)(?=\")(?=(((?!\\]).)*\\[)|[^\\[\\]]*$)(?=(((?!\\}).)*\\{)|[^\\{\\}]*$)";

    public JSONObject(String name, String content) {
        this.name = name;
        map = new HashMap<>();
        parseContent(content);
    }

    /**
     * Returns the value of given key in JSONObject
     *
     * @param key the key to find the value of
     * @return the value from the key
     * @throws IllegalArgumentException if no such key can be found.
     */
    public Object get(String key) throws IllegalArgumentException {
        if (!map.containsKey(key))
            throw new IllegalArgumentException(
                    String.format("Key \"%s\" was not found in JSONObject listed.\nHere are all the keys for %s: %s",
                            key, name, map.keySet()));
        return map.get(key);
    }

    public ArrayList<String> getKeys() {
        return new ArrayList<>(map.keySet());
    }

    /**
     * Algorithm for parsing the JSON string:
     * 1. Split string by commas (outside of quotation marks) into array of key-pairs
     * 2. Iterate through each pair:
     *      a. add key & value pair to hashmap
     *
     * @param content JSON String that's going to be processed
     */
    private void parseContent(String content) {
        String[] pairs = content.split(JSON_COMMA_SPLITTER);
        for (String pair : pairs) {
            int indexColon = pair.indexOf(":");
            String key = pair.substring(0, indexColon).replace("\"","");
            String val = pair.substring(indexColon + 1);
            map.put(key, interpretVal(key, val));
        }
    }

    /**
     * @param val String that's getting processed
     * @return <code>Object</code>, will be an instance of String, int, double,
     *         boolean, or null depending on the contents of val.
     */
    private Object interpretVal(String key, String val) {
        // all known basic datatypes for JSON files: integer*, floating-point, boolean,
        // *will be parsed as a Long because JSON integers can be pretty big!
        // null, and String
        Object o;
        if (val.matches("^\\d+")) // check for integer
            o = Long.parseLong(val);
        else if (val.matches("\\d*\\.\\d+")) // check for floating-point number
            o = Double.parseDouble(val);
        else if (val.equals("true")) // check for boolean (true)
            o = true;
        else if (val.equals("false")) // check for boolean (false)
            o = false;
        else if (val.equals("null")) // check for null
            o = null;
        else if (val.matches("\\[.*]")) { // check for array
            ArrayList<Object> list = new ArrayList<>();
            for (String d : val.substring(val.indexOf('[') + 1, val.lastIndexOf(']')).split(JSON_COMMA_SPLITTER))
                list.add(interpretVal(key, d));
            o = list;
        }
        else if (val.matches("\\{.*}")) { // check for object
            o = new JSONObject(key, val.substring(val.indexOf("{") + 1, val.lastIndexOf("}")));
        }
        else // by default just store the value as a string
            o = val.replace("\"", "");
        return o;
    }

    public String toString() {
        return String.format("%s", map.toString());
    }
}