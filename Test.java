import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import utils.*;
class Test {
    public static void main(String[] args)throws IOException {
        String test = "\"name\":\"jason\",\"age\":16.35,\"friends,and,family\":[\"charlie\",\"keila\",\"leila\"]";
        String regex = "(,)(?=\")|(?:\\[(.*?)\\])";
        System.out.println(Arrays.toString(test.split(regex)));
    }
}
