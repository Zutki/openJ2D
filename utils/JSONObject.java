package utils;

import java.util.HashMap;

/**
 * An object used to store JSON Objects. A low-priority thing for me to complete because
 * the JSON Files used in this project don't contain JSON objects.
 */
public class JSONObject {
    String key;
    Object val;
    
    public JSONObject(String str) {

    }
    /**
     * 
     * @param dat
     * @return
     */
    private Object interpretData(String dat) {
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
            o = dat;
        return o;
    }
}
