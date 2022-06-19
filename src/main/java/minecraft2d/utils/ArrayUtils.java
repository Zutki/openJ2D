package minecraft2d.utils;

/**
 * Utils for working with arrays
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class ArrayUtils {
    /**
     * returns whether the array contains the specified element
     * @param array the array to check
     * @param object the object o check
     * @return if array contains object
     */
    public static boolean contains(String[] array, String object) {
        for (String q : array) {
            if (object.equals(q)) { return true; }
        }
        return false;
    }
    /**
     * returns whether the array contains the specified element
     * @param array the array to check
     * @param object the object o check
     * @return if array contains object
     */
    public static boolean contains(int[] array, int object) {
        for (int q : array) {
            if (object == q) { return true; }
        }
        return false;
    }
}
