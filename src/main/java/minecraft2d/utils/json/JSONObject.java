package minecraft2d.utils.json;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An object used to store JSON Objects as key-value pair with methods to retrieve each key and value. The constructor
 * should not be used; it's recommended to use the JSONReader to create JSONObjects, and from there, using JSONObject
 * methods to get the keys and values from that JSONObject.
 *
 * @author samminhch
 * @see JSONReader
 * @since December 9, 2021
 */
public class JSONObject {
    // the name of the .json object
    private final String name;
    // used to store each key and value of a .json object
    private final HashMap<String, Object> map;
    // splits JSON by its outermost commas (doesn't work for curly brackets)
    // TODO: come up with a better algorithm to parse a JSONObject; just RegEx isn't good enough. (low priority)
    private final String JSON_COMMA_SPLITTER = "(,)(?=\")*(?=(((?!\\]).)*\\[)|[^\\[\\]]*$)(?=(((?!\\}).)*\\{)|[^\\{\\}]*$)";

    /**
     * Instantiates a new JSONObject.
     *
     * @param name    the name of the .json file
     * @param content the content of the .json file
     */
    public JSONObject(String name, String content) {
        this.name = name;
        map = new HashMap<>();
        parseContent(content);
    }

    /**
     * Returns the value of given key in JSONObject
     *
     * @param key the key to find the value of
     * @return an Object containing the value from the key
     * @throws IllegalArgumentException if no such key can be found.
     */
    public Object get(String key) throws IllegalArgumentException {
        if (!map.containsKey(key))
            throw new IllegalArgumentException(
                    String.format("Key \"%s\" was not found in JSONObject listed.\nHere are all the keys for %s: %s",
                            key, name, map.keySet()));
        return map.get(key);
    }

    /**
     * Gets keys from the .json file
     *
     * @return an ArrayList of Strings containing each key in the JSONObject
     */
    public ArrayList<String> getKeys() {
        return new ArrayList<>(map.keySet());
    }

    /**
     * This method splits the .json file by splitting it by its key-value pairs and adding them to the HashMap map.
     * <p>
     *     Algorithm for parsing the JSON string:
     * </p>
     * <p>
     *     If the <i>String</i> passed is an object (everything is surrounded by an open and closing curly bracket):
     *     <ol>
     *         <li>
     *             Split string by commas (outside of quotation marks) into array of key-pairs
     *         </li>
     *         <li>
     *             Iterate through each pair:
     *             <ol type="a">
     *                 <li>
     *                     Split pair by the location of each colon, and store them into a key and value variable
     *                 </li>
     *                 <li>
     *                     Interpret the value
     *                 </li>
     *                 <li>
     *                     Add the key and value to the <i>HashMap</i> <b>map</b>
     *                 </li>
     *             </ol>
     *         </li>
     *     </ol>
     *     Else, put in file name as key to the <i>HashMap</i> <b>map</b>, and put in the contents of the file as the map's value
     * </p>
     *
     * @param content JSON String that's going to be processed
     * @see JSONObject#interpretVal(String key, String val)
     */
    private void parseContent(String content) {
        // check if the *content* is an object
        if (content.charAt(0) == '{' && content.charAt(content.length() - 1) == '}') {
            // get rid of the outmost curly brackets, and then splitting the string into an array of key-pairs
            String[] pairs = content.substring(1, content.length() - 1).split(JSON_COMMA_SPLITTER);

            // iterating through each pair
            for (String pair : pairs) {
                int indexColon = pair.indexOf(":");
                String key = pair.substring(0, indexColon).replace("\"", "");
                String val = pair.substring(indexColon + 1);
                map.put(key, interpretVal(key, val));
            }
        } else // key is file name, value is the contents of the file
            map.put(name, interpretVal(name, content));
    }

    /**
     * This method parses each value in the key-value pair into its appropriate datatypes
     *
     * @param val String that's getting processed
     * @return <code>Object</code>, will be an instance of String, int, double,
     * boolean, or null depending on the contents of val.
     */
    private Object interpretVal(String key, String val) {
        // all known basic datatypes for JSON files: integer*, floating-point, boolean,
        // *will be parsed as a Long because JSON numbers can be pretty big!
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
            // debugging purposes (84 -> 87)
            // String str = val.substring(1, val.length() - 1);
            // String[] arr = str.split(JSON_COMMA_SPLITTER);
            // for (String string : arr)
            //     System.out.println(string);

            for (String d : val.substring(1, val.length() - 1).split(JSON_COMMA_SPLITTER))
                list.add(interpretVal(key, d));
            o = list;
        } else if (val.matches("\\{.*}")) // check for object
            o = new JSONObject(key, val);
        else // by default just store the value as a string
            o = val.replace("\"", "");
        return o;
    }

    @Override
    public String toString() {
        return String.format("%s", map.toString());
    }
}