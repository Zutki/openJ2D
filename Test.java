import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

import java.awt.image.BufferedImage;


import utils.*;
class Test {
    public static void main(String[] args)throws IOException {
        BufferedImage skin = Tools.fetchMinecraftSkin("marshie_maid");
        System.out.println(skin);
        
        JSONReader reader = new JSONReader(new File("test.json"));
        JSONObject object = reader.interpretFile();
        
        System.out.println(object);
    }
}