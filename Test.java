import utils.JSONObject;
import utils.JSONReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Test {
    public static void main(String[] args) throws IOException {
        JSONObject object = JSONReader.interpretFile(new File("test.json"));

        System.out.println(object);
    }
}