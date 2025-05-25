
package ui;

import javax.swing.JFrame;
import game.BlackjackGame;

public class GameWindow extends JFrame {

    private GamePanel gamePanel;
    private BlackjackGame game;

    public GameWindow() {
        this.setTitle("Blackjack");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 600);
        this.setLocationRelativeTo(null);

        game = new BlackjackGame(1000);

        gamePanel = new GamePanel(game);
        this.add(gamePanel);

        this.setVisible(true);
    }

}