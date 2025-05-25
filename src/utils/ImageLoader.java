
package utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {

    public static BufferedImage loadImage(String fileName) throws IOException {

        URL resource = ImageLoader.class.getResource("/resources/cartas/" + fileName);
        if (resource != null) {
            return ImageIO.read(resource);
        } else {

            File file = new File("src/" + fileName);
            if (file.exists()) {
                return ImageIO.read(file);
            }
        }
        throw new IOException("Image not found: " + fileName);
    }

    public static ImageIcon loadImageIcon(String fileName) throws IOException {
        URL resource = ImageLoader.class.getResource(fileName);
        if (resource != null) {
            return new ImageIcon(resource);
        } else {

            File file = new File("src" + fileName);
            if (file.exists()) {
                return new ImageIcon(file.getPath());
            }
        }
        throw new IOException("Image Icon not found: " + fileName);
    }

    public static ImageIcon loadCardImage(String cardFileName, int width, int height) {
        try {
            ImageIcon icon = loadImageIcon("/resources/cartas/" + cardFileName);
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            System.err.println("Error loading card: " + cardFileName + " - " + e.getMessage());
            return null;
        }
    }
}