import utils.JSONObject;
import utils.JSONReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Test {
    public static void main(String[] args) throws IOException {
        JSONObject object = JSONReader.interpretFile(new File("itemInfo.json"));
        
        ArrayList<JSONObject> list = (ArrayList<JSONObject>) object.get("itemInfo.json");
        JSONObject obj1 = list.get(0);
        System.out.println(obj1.get("id"));
        
    }
}