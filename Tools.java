import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

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
     * - https://ourcodeworld.com/articles/read/1293/how-to-retrieve-the-skin-of-a-minecraft-user-from-mojang-using-python-3
     * 
     * @param username the username to the Minecraft skin of
     * @return <code>BufferedImage</code> the Minecraft skin of the username
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
            // it's probably worth fixing the bug in JSONObject.java though but this still works fine as-is.
            String skinUrl = (String) userTextures.get("SKIN");
            minecraftSkin = ImageIO.read(new URL(skinUrl.substring(skinUrl.indexOf("http://"))));
        } catch (IOException e) {
            // this should be the only reason why a Minecraft skin wasn't fetched ⤵
            System.out.println(String.format("\"%s\" was not found on the Minecraft database.\n%s\n", username, e.toString()));
        }
        return minecraftSkin;
    }

   // Player image decoding from skin image
   public static BufferedImage getPlayerFacingFront(File skinImage) {
       // define some buffered images for different parts of the body
       BufferedImage bi = null;
       BufferedImage torso;
       BufferedImage head;
       BufferedImage lArm;
       BufferedImage rArm;
       BufferedImage lLeg;
       BufferedImage rLeg;
        
       // crop out the parts of the image we need
       try {
           bi = ImageIO.read(skinImage);
       }
       catch (IOException e) {
           System.out.println(e);
       }
       
       torso = bi.getSubimage(20, 20, 8, 12);
       head = bi.getSubimage(8, 8, 8, 8);
       lArm = bi.getSubimage(36, 52, 4, 12);
       rArm = bi.getSubimage(44, 20, 4, 12);
       lLeg = bi.getSubimage(20, 52, 4, 12);
       rLeg = bi.getSubimage(4, 20, 4, 12);
       
       // draw the player
       int height = 32;
       int width = 16;

       BufferedImage playerImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
       Graphics2D g2d = playerImg.createGraphics();
       
       // head
       g2d.drawImage(head, 4, 0, null);
       
       // torso
       g2d.drawImage(torso, 4, 8, null);
        
       // left arm
       g2d.drawImage(lArm, 0, 8, null);
        
       // right arm
       g2d.drawImage(rArm, 12, 8, null);
        
       // left leg
       g2d.drawImage(lLeg, 4, 20, null);
    
       // right leg
       g2d.drawImage(rLeg, 8, 20, null);
        
       // return the image
       g2d.dispose();
       return playerImg;
   }

   public static BufferedImage getPlayerFacingLeft(File skinImage) {
       // define parts of the image
       BufferedImage bi = null;
       BufferedImage head;
       BufferedImage arm;
       BufferedImage leg;

       // load image
       try {
           bi = ImageIO.read(skinImage);
       }
       catch (IOException e) {
           System.out.println(e);
       }

       // crop full image to get body parts
       head = bi.getSubimage(16, 8, 8, 8);
       arm = bi.getSubimage(40, 52, 4, 12);
       leg = bi.getSubimage(24, 52, 4, 12);
        
       // draw the player
       int height = 32;
       int width = 16;

       BufferedImage playerImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
       Graphics2D g2d = playerImg.createGraphics();

       // head
       g2d.drawImage(head, 4, 0, null);

       // arm
       g2d.drawImage(arm, 6, 8, null);

       // leg
       g2d.drawImage(leg, 6, 20, null);

       // return image
       g2d.dispose();
       return playerImg;
   }

   public static BufferedImage getPlayerFacingRight(File skinImage) {
       // define parts of the image
       BufferedImage bi = null;
       BufferedImage head;
       BufferedImage arm;
       BufferedImage leg;

       // load image
       try {
           bi = ImageIO.read(skinImage);
       }
       catch (IOException e) {
           System.out.println(e);
       }

       // crop full image to get body parts
       head = bi.getSubimage(0, 8, 8, 8);
       arm = bi.getSubimage(40, 20, 4, 12);
       leg = bi.getSubimage(0, 20, 4, 12);

       //draw player
       int height = 32;
       int width = 16;

       BufferedImage playerImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
       Graphics g2d = playerImg.createGraphics();

       // head
       g2d.drawImage(head, 4, 0, null);
       
       // arm
       g2d.drawImage(arm, 6, 8, null);
    
       // leg
       g2d.drawImage(leg, 6, 20, null);

       // return image
       g2d.dispose();
       return playerImg;
   }
}