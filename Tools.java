import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
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

/**
 * This class contains functions to manipulate images, more specifically, block and player images. <b>Zutki</b> created
 * functions to resize images, and to get specific player model images. <b>Minh</b> created a function to fetch a
 * Minecraft skin using Minecraft's API.
 *
 * @author samminhch
 * @author Zutki
 */
public class Tools {
    /**
     * Resize buffered image.
     *
     * @param img  the img
     * @param newW the new w
     * @param newH the new h
     * @return the buffered image
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    /**
     * Make image translucent buffered image.//
     * Credit: <a href="http://www.java2s.com/Code/Java/2D-Graphics-GUI/MakeimageTransparency.htm">java2s.com</a>
     * @param source the source
     * @param alpha  the alpha
     * @return the buffered image
     * @author Zutki
     */
    public static BufferedImage makeImageTranslucent(BufferedImage source, float alpha) {
        BufferedImage target = new BufferedImage(source.getWidth(), source.getHeight(),
                java.awt.Transparency.TRANSLUCENT);
        // Get the images graphics
        Graphics2D g = target.createGraphics();
        // Set the Graphics composite to Alpha
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        // Draw the image into the prepared receiver image
        g.drawImage(source, null, 0, 0);
        // let go of all system resources in this Graphics
        g.dispose();
        // Return the image
        return target;
    }

    /**
     * Returns an image of the minecraft avatar from specified username through the use of Minecraft's player-base API.
     * <p>
     * Used resources to create this function:
     *     <ul>
     *         <li>
     *             <a href="https://www.baeldung.com/java-http-request">
     *                 baeldung.com
     *             </a>
     *         </li>
     *         <li>
     *             <a href="https://ourcodeworld.com/articles/read/1293/how-to-retrieve-the-skin-of-a-minecraft-user-from-mojang-using-python-3">
     *                 ourcodeworld.com
     *             </a>
     *         </li>
     *     </ul>
     * </p>
     *
     * @param username a String representing a Minecraft username
     * @return <code>BufferedImage</code> the Minecraft skin of the username
     */
    public static BufferedImage fetchMinecraftSkin(String username) {
        BufferedImage minecraftSkin = null;
        try {
            URL url = new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s", username));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // fetch result from url and parse that into a JSONObject
            // root is an arbitrary name used throughout this method ⤵
            JSONObject userInfo = JSONReader.interpretJSONString("root",
                    new InputStreamReader(connection.getInputStream()));

            // use the uid to fetch user profile from minecraft database
            url = new URL(
                    String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s", userInfo.get("id")));
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            JSONObject userProfile = JSONReader.interpretJSONString("root",
                    new InputStreamReader(connection.getInputStream()));

            // fetch value from properties array
            ArrayList<Object> userProperties = (ArrayList<Object>) userProfile.get("properties");
            JSONObject propertyValue = (JSONObject) userProperties.get(0);

            // decrypt decoded string from value
            String encryptedString = (String) propertyValue.get("value");
            // System.out.println(encryptedString);

            String decryptedString = new String(Base64.getDecoder().decode(encryptedString)).trim();
            // System.out.println(decryptedString);

            // create a JSONObject from decryptedString
            JSONObject decryptedJSON = JSONReader.interpretJSONString("root", new StringReader(decryptedString));
            // System.out.println(decryptedJSON.getKeys());


            // get textures -> skin -> url
            JSONObject userTextures = (JSONObject) decryptedJSON.get("textures");
            // System.out.println(userTextures);

            // this is as far as I can get with my regex statement in JSONObject.java. I'm
            // going to need to fix that regex in order for this to work...
            // JSONObject userSkin = (JSONObject) userTextures.get("SKIN");
            // String skinURL = (String) userSkin.get("url");
            // System.out.println(skinURL);

            // actually i can do it with my buggy json class watch this
            // it's probably worth fixing the bug in JSONObject.java though but this still
            // works fine as-is.
            String skinURL;
            if (userTextures.get("SKIN") instanceof String) {
                String userSkin = (String) userTextures.get("SKIN");
                skinURL = userSkin.substring(userSkin.indexOf("http://"));
            }
            else {
                JSONObject userSkin = (JSONObject) userTextures.get("SKIN");
                skinURL = (String) userSkin.get("url");
            }
            // debug statement
            System.out.printf("Skin url for username \"%s\": %s\n", username, skinURL);
            minecraftSkin = ImageIO.read(new URL(skinURL));
        } catch (IOException e) {
            // this should be the only reason why a Minecraft skin wasn't fetched ⤵
            System.out.printf("\"%s\" was not found on the Minecraft database.\n%s\n\n", username, e);

            // set Steve as the Minecraft username (nested try-catch statement because i don't want to add a throw exception)
            // i am a lazy bum... -minh
            try {
                minecraftSkin = ImageIO.read(new File("assets/steve/steve.png"));

            } catch (IOException f) {
                System.out.printf("Could not find default Minecraft skin.\n%s\n\n", f);
            }
        }
        return minecraftSkin;
    }

    // Player image decoding from skin image
    /**
     * Gets player facing front.
     *
     * @param skinImage the skin image
     * @return the player facing front
     */
    public static BufferedImage getPlayerFacingFront(BufferedImage skinImage) {
        // define some buffered images for different parts of the body
        BufferedImage torso;
        BufferedImage head;
        BufferedImage lArm;
        BufferedImage rArm;
        BufferedImage lLeg;
        BufferedImage rLeg;

        torso = skinImage.getSubimage(20, 20, 8, 12);
        head = skinImage.getSubimage(8, 8, 8, 8);
        lArm = skinImage.getSubimage(36, 52, 4, 12);
        rArm = skinImage.getSubimage(44, 20, 4, 12);
        lLeg = skinImage.getSubimage(20, 52, 4, 12);
        rLeg = skinImage.getSubimage(4, 20, 4, 12);

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

    /**
     * Gets player facing left.
     *
     * @param skinImage the skin image
     * @return the player facing left
     */
    public static BufferedImage getPlayerFacingLeft(BufferedImage skinImage) {
        // define parts of the image
        BufferedImage head;
        BufferedImage arm;
        BufferedImage leg;

        // crop full image to get body parts
        head = skinImage.getSubimage(16, 8, 8, 8);
        arm = skinImage.getSubimage(40, 52, 4, 12);
        leg = skinImage.getSubimage(24, 52, 4, 12);

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

    /**
     * Gets player facing right.
     *
     * @param skinImage the skin image
     * @return the player facing right
     */
    public static BufferedImage getPlayerFacingRight(BufferedImage skinImage) {
        // define parts of the image
        BufferedImage head;
        BufferedImage arm;
        BufferedImage leg;

        // crop full image to get body parts
        head = skinImage.getSubimage(0, 8, 8, 8);
        arm = skinImage.getSubimage(40, 20, 4, 12);
        leg = skinImage.getSubimage(0, 20, 4, 12);

        // draw player
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
