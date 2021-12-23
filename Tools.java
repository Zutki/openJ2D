import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Base64;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


import utils.JSONObject;
import utils.JSONReader;


public class Tools {
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    // credit:
    // http://www.java2s.com/Code/Java/2D-Graphics-GUI/MakeimageTransparency.htm
    public static BufferedImage makeImageTranslucent(BufferedImage source, float alpha) {
        BufferedImage target = new BufferedImage(source.getWidth(), source.getHeight(),
                java.awt.Transparency.TRANSLUCENT);
        // Get the images graphics
        Graphics2D g = target.createGraphics();
        // Set the Graphics composite to Alpha
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        // Draw the image into the prepared reciver image
        g.drawImage(source, null, 0, 0);
        // let go of all system resources in this Graphics
        g.dispose();
        // Return the image
        return target;
    }

    /**
     * Returns an image of the minecraft avatar from specified username.
     * Used resources to create this function:
     * - https://www.baeldung.com/java-http-request
     * -
     * https://ourcodeworld.com/articles/read/1293/how-to-retrieve-the-skin-of-a-minecraft-user-from-mojang-using-python-3
     * 
     * @param username the username to the Minecraft skin of
     * @return <code>Image</code> the Minecraft skin of the username
     * @throws IllegalArgumentException if username cannot be found within database
     */
    public static BufferedImage fetchMinecraftSkin(String username) throws IllegalArgumentException {
        BufferedImage minecraftSkin = null;
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
            //System.out.println(decryptedString);
            
            // create a JSONObject from decryptedString
            JSONObject decryptedJSON = JSONReader.interpretJSONString("root", new StringReader(decryptedString));
            // get textures -> skin -> url
            JSONObject userTextures = (JSONObject) decryptedJSON.get("textures");
            //System.out.println(userTextures);
            
            // this is as far as I can get with my regex statement in JSONObject.java. I'm going to need to fix that regex in order for this to work...
            // JSONObject userSkin = (JSONObject) userTextures.get("SKIN");
            // String skinURL = (String) userSkin.get("url");
            // System.out.println(skinURL);

            // actually i can do it with my buggy json class watch this
            String skinUrl = (String) userTextures.get("SKIN");
            minecraftSkin = ImageIO.read(new URL(skinUrl.substring(skinUrl.indexOf("http://"))));
        } catch (IOException e) {
            System.out.println(String.format("\"%s\" was not found on the Minecraft database.\n%s\n", username, e.toString()));
        }
        return minecraftSkin;
    }
}