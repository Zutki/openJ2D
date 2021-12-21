import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import utils.*;
class Test {
    public static void main(String[] args)throws IOException {
        JSONReader reader = new JSONReader(new File("test.json"));
        JSONObject res = reader.interpretFile();
        System.out.println(res);
    }
}
