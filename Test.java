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


import utils.*;
class Test {
    public static void main(String[] args)throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Give me a Minecraft username: ");
        
        String username = in.nextLine();
        in.close();
        try {
            URL url = new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s", username));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            // fetch result from url and parse that into a JSONObject
            // root is an arbitrary name used throughout this method ⤵
            JSONObject userInfo = JSONReader.interpretJSONString("root", new InputStreamReader(connection.getInputStream()));

            // use the uid to fetch user profile from minecraft database
            url = new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s", userInfo.get("id")));
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            JSONObject userProfile = JSONReader.interpretJSONString("root", new InputStreamReader(connection.getInputStream()));

            // fetch value from properties array
            ArrayList<Object> userProperties = (ArrayList<Object>) userProfile.get("properties");            
            JSONObject propertyValue = (JSONObject) userProperties.get(0);

            // decrypt decoded string from value
            String encryptedString = (String) propertyValue.get("value");
            //System.out.println(encryptedString);

            String decryptedString = new String(Base64.getDecoder().decode(encryptedString));
            System.out.println(decryptedString);
            
            // create a JSONObject from decryptedString
            JSONObject decryptedJSON = JSONReader.interpretJSONString("root", new StringReader(decryptedString));
            // get textures -> skin -> url
            JSONObject userTextures = (JSONObject) decryptedJSON.get("textures");
            System.out.println(userTextures);
            
            JSONObject userSkin = (JSONObject) userTextures.get("SKIN");
            String skinURL = (String) userSkin.get("url");
            System.out.println(skinURL);
            
        } catch (IOException e) {
            System.out.println(String.format("\"%s\" was not found on the Minecraft database.\n%s\n", username, e.toString()));
        }
    }
}