
package ui.components;

import javax.swing.*;

import java.awt.BorderLayout;

import utils.ImageLoader;

public class CardPanel extends JPanel {
    private JLabel cardLabel;
    private ImageIcon cardImage;

    public CardPanel(String cardFileName, int width, int height) {
        setOpaque(false);
        setLayout(new BorderLayout());

        setCard(cardFileName, width, height);
    }

    public void setCard(String cardFileName, int width, int height) {
        try {
            cardImage = ImageLoader.loadCardImage(cardFileName, width, height);
            if (cardLabel == null) {
                cardLabel = new JLabel(cardImage);
                add(cardLabel, BorderLayout.CENTER);
            } else {
                cardLabel.setIcon(cardImage);
            }
        } catch (Exception e) {
            System.err.println("Error loading card image: " + cardFileName + " - " + e.getMessage());

        }
        revalidate();
        repaint();
    }
}