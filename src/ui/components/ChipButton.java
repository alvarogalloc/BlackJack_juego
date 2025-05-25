
package ui.components;

import javax.swing.*;
import java.awt.Image;
import utils.ImageLoader;

public class ChipButton extends JButton {
    private int value;

    public ChipButton(String imagePath, int value) {
        this.value = value;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        try {
            ImageIcon icon = ImageLoader.loadImageIcon(imagePath);
            setIcon(new ImageIcon(icon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            System.err.println("Error loading chip image: " + imagePath + " - " + e.getMessage());
            setText("$" + value);
        }
    }

    public int getValue() {
        return value;
    }
}