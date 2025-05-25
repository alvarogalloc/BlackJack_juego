package ui;

import game.BlackjackGame;
import game.Card;
import game.HumanPlayer;
import game.Dealer;
import game.Deck;
import ui.components.ChipButton;
import utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private BlackjackGame game;
    private BufferedImage backgroundImage;

    private JLabel textoInicio;
    private JButton allInButton;
    private JButton empezarButton;
    private JButton salirButton;
    private JButton doblarButton;
    private JButton plantarseButton;
    private JButton pedirCartaButton;

    private JLabel apuestaTexto;
    private JLabel dineroTexto;

    private ChipButton ficha100;
    private ChipButton ficha50;
    private ChipButton ficha10;

    private List<JLabel> playerCardLabels;
    private List<JLabel> dealerCardLabels;
    private JLabel dealerHiddenCardLabel;

    public GamePanel(BlackjackGame game) {
        this.game = game;
        this.setLayout(null);
        this.setOpaque(false);

        loadBackgroundImage();
        initializeComponents();
        updateUIForGameState();
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageLoader.loadImage("/resources/blackjack-table.jpg");
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
            backgroundImage = null;
        }
    }

    private void initializeComponents() {
        ficha100 = new ChipButton("/resources/chips/100.png", 100);
        ficha50 = new ChipButton("/resources/chips/50.png", 50);
        ficha10 = new ChipButton("/resources/chips/10.png", 10);

        ficha100.setBounds(30, 300, 75, 75);
        ficha50.setBounds(30, 375, 75, 75);
        ficha10.setBounds(30, 450, 75, 75);

        ficha100.addActionListener(e -> handleBet(100));
        ficha50.addActionListener(e -> handleBet(50));
        ficha10.addActionListener(e -> handleBet(10));

        this.add(ficha100);
        this.add(ficha50);
        this.add(ficha10);

        Font font = new Font("Times New Roman", Font.BOLD, 30);
        apuestaTexto = new JLabel("Apuesta: $" + game.getPlayer().getCurrentBet());
        dineroTexto = new JLabel("Dinero: $" + game.getPlayer().getMoney());

        apuestaTexto.setBounds(20, 230, 200, 50);
        dineroTexto.setBounds(940, 10, 300, 50);

        apuestaTexto.setFont(font);
        dineroTexto.setFont(font);
        apuestaTexto.setForeground(Color.white);
        dineroTexto.setForeground(Color.white);

        this.add(apuestaTexto);
        this.add(dineroTexto);

        allInButton = new JButton("All-In");
        empezarButton = new JButton("Empezar");
        salirButton = new JButton("Salir");

        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        allInButton.setFont(buttonFont);
        allInButton.setBackground(new Color(255, 140, 0)); 
        allInButton.setForeground(Color.WHITE);

        empezarButton.setFont(buttonFont);
        empezarButton.setBackground(new Color(0, 120, 215));
        empezarButton.setForeground(Color.WHITE);

        salirButton.setFont(buttonFont);
        salirButton.setBackground(new Color(220, 53, 69));
        salirButton.setForeground(Color.WHITE);

        allInButton.setBounds(440, 500, 100, 40);
        empezarButton.setBounds(590, 500, 150, 40);
        salirButton.setBounds(790, 500, 100, 40);

        allInButton.addActionListener(e -> handleAllIn());
        empezarButton.addActionListener(e -> handleEmpezar());
        salirButton.addActionListener(e -> handleSalir());

        this.add(allInButton);
        this.add(empezarButton);
        this.add(salirButton);

        doblarButton = new JButton("Doblar");
        plantarseButton = new JButton("Plantarse");
        pedirCartaButton = new JButton("Pedir carta");

        doblarButton.setBounds(1050, 200, 150, 40);
        plantarseButton.setBounds(1050, 260, 150, 40);
        pedirCartaButton.setBounds(1050, 320, 150, 40);

        doblarButton.setFont(buttonFont);
        doblarButton.setBackground(new Color(255, 140, 0)); 
        doblarButton.setForeground(Color.WHITE);

        plantarseButton.setFont(buttonFont);
        plantarseButton.setBackground(new Color(220, 53, 69));
        plantarseButton.setForeground(Color.WHITE);

        pedirCartaButton.setFont(buttonFont);
        pedirCartaButton.setBackground(new Color(0, 120, 215));
        pedirCartaButton.setForeground(Color.WHITE);

        doblarButton.addActionListener(e -> handleDoblar());
        plantarseButton.addActionListener(e -> handlePlantarse());
        pedirCartaButton.addActionListener(e -> handlePedirCarta());

        this.add(doblarButton);
        this.add(plantarseButton);
        this.add(pedirCartaButton);

        playerCardLabels = new ArrayList<>();
        dealerCardLabels = new ArrayList<>();

        textoInicio = new JLabel("Bienvenido a Blackjack");
        textoInicio.setFont(new Font("Times New Roman", Font.BOLD, 50));
        textoInicio.setBounds(350, 10, 550, 50);
        textoInicio.setForeground(Color.black);
        this.add(textoInicio);

        addDeckBackCards();
    }

    private void addDeckBackCards() {
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/resources/cartas/red_back.png"));
        Image cartaOriginal = iconoOriginal.getImage();
        Image cartaEscalada = cartaOriginal.getScaledInstance(90, 130, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(cartaEscalada);

        JLabel cartaMonton1 = new JLabel(iconoEscalado);
        JLabel cartaMonton2 = new JLabel(iconoEscalado);
        JLabel cartaMonton3 = new JLabel(iconoEscalado);

        cartaMonton1.setBounds(20, 20, 90, 130);
        cartaMonton2.setBounds(27, 27, 90, 130);
        cartaMonton3.setBounds(34, 34, 90, 130);

        this.add(cartaMonton1);
        this.add(cartaMonton2);
        this.add(cartaMonton3);
    }

    private void updateUIForGameState() {
        BlackjackGame.GameState state = game.getCurrentState();

        boolean showInitialButtons = (state == BlackjackGame.GameState.AWAITING_BET);
        textoInicio.setVisible(showInitialButtons);
        allInButton.setVisible(showInitialButtons);
        empezarButton.setVisible(showInitialButtons);
        salirButton.setVisible(showInitialButtons);

        ficha10.setEnabled(showInitialButtons);
        ficha50.setEnabled(showInitialButtons);
        ficha100.setEnabled(showInitialButtons);

        boolean showGameButtons = (state == BlackjackGame.GameState.PLAYER_TURN
                || state == BlackjackGame.GameState.DEALER_TURN);

        doblarButton.setVisible(false);
        plantarseButton.setVisible(false);
        pedirCartaButton.setVisible(false);

        if (state == BlackjackGame.GameState.PLAYER_TURN) {
            if (game.getPlayer().getHand().size() == 2
                    && game.getPlayer().canAfford(game.getPlayer().getCurrentBet())) {
                doblarButton.setVisible(true);
            }
            plantarseButton.setVisible(true);
            pedirCartaButton.setVisible(true);
        } else if (state == BlackjackGame.GameState.DEALER_TURN) {
        }

        apuestaTexto.setText("Apuesta: $" + game.getPlayer().getCurrentBet());
        dineroTexto.setText("Dinero: $" + game.getPlayer().getMoney());

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void handleBet(int amount) {
        try {
            game.getPlayer().placeBet(amount);
            updateUIForGameState();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleAllIn() {
        if (game.getPlayer().getMoney() == 0) {
            JOptionPane.showMessageDialog(this, "No tienes dinero para jugar. ¡Gracias por jugar!", "Sin dinero",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int remainingMoney = game.getPlayer().getMoney();
        try {
            game.getPlayer().placeBet(remainingMoney);
            updateUIForGameState();
            startGameRound();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleEmpezar() {
        if (game.getPlayer().getMoney() == 0 && game.getPlayer().getCurrentBet() == 0) {
            JOptionPane.showMessageDialog(this, "No tienes dinero para jugar. ¡Gracias por jugar!", "Sin dinero",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (game.getPlayer().getCurrentBet() == 0) {
            JOptionPane.showMessageDialog(this, "Debes realizar una apuesta antes de empezar.", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        startGameRound();
    }

    private void handleSalir() {
        System.exit(0);
    }

    private void handleDoblar() {
        try {
            game.doubleDownPlayer();
            drawPlayerCards();
            if (game.getPlayer().calculateHandValue() <= 21) {
                new Thread(this::runDealerTurn).start();
            }
            updateUIForGameState();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlePlantarse() {
        game.standPlayer();
        new Thread(this::runDealerTurn).start();
        updateUIForGameState();
    }

    private void handlePedirCarta() {
        try {
            game.hitPlayer();
            drawPlayerCards();
            updateUIForGameState();
            if (game.getPlayer().calculateHandValue() > 21) {
                displayGameResult();
            } else if (game.getPlayer().calculateHandValue() == 21) {
                game.standPlayer();
                new Thread(this::runDealerTurn).start();
            }
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startGameRound() {
        clearCardDisplay();
        game.startGameRound();
        drawInitialCards();
        updateUIForGameState();

        if (game.getCurrentState() == BlackjackGame.GameState.GAME_OVER_ROUND) {
            displayGameResult();
        }
    }

    private void drawInitialCards() {
        drawPlayerCards();

        Card dealerUpCard = game.getDealer().getHand().get(0);
        ImageIcon upCardIcon = ImageLoader.loadCardImage(dealerUpCard.getName(), 90, 130);
        JLabel upCardLabel = new JLabel(upCardIcon);
        upCardLabel.setName("dealer_card_0");
        dealerCardLabels.add(upCardLabel);

        ImageIcon backCardIcon = ImageLoader.loadCardImage("/red_back.png", 90, 130);
        dealerHiddenCardLabel = new JLabel(backCardIcon);
        dealerHiddenCardLabel.setName("dealer_hidden_card");

        int xInitialDealer = (getWidth() - (90 + 20 + 90)) / 2;
        upCardLabel.setBounds(xInitialDealer, 120, 90, 130);
        dealerHiddenCardLabel.setBounds(xInitialDealer + 90 + 20, 120, 90, 130);

        this.add(upCardLabel);
        this.add(dealerHiddenCardLabel);
        this.revalidate();
        this.repaint();
    }

    private void drawPlayerCards() {
        for (JLabel label : playerCardLabels) {
            this.remove(label);
        }
        playerCardLabels.clear();

        int anchoCarta = 90;
        int espacio = 20;
        int total = game.getPlayer().getHand().size();
        int anchoTotal = total * anchoCarta + (total - 1) * espacio;
        int xInicial = (getWidth() - anchoTotal) / 2;
        int y = 350;

        for (int i = 0; i < total; i++) {
            Card card = game.getPlayer().getHand().get(i);
            ImageIcon icon = ImageLoader.loadCardImage(card.getName(), anchoCarta, 130);
            JLabel cardLabel = new JLabel(icon);
            cardLabel.setBounds(xInicial + i * (anchoCarta + espacio), y, anchoCarta, 130);
            playerCardLabels.add(cardLabel);
            this.add(cardLabel);
        }
        this.revalidate();
        this.repaint();
    }

    private void drawDealerCards(boolean revealHidden) {
        if (dealerHiddenCardLabel != null) {
            this.remove(dealerHiddenCardLabel);
            dealerHiddenCardLabel = null;
        }
        for (JLabel label : dealerCardLabels) {
            this.remove(label);
        }
        dealerCardLabels.clear();

        int anchoCarta = 90;
        int espacio = 20;
        List<Card> dealerHand = game.getDealer().getHand();
        int total = dealerHand.size();
        int anchoTotal = total * anchoCarta + (total - 1) * espacio;
        int xInicial = (getWidth() - anchoTotal) / 2;
        int y = 120;

        for (int i = 0; i < total; i++) {
            Card card = dealerHand.get(i);
            ImageIcon icon;
            if (i == 1 && !revealHidden && total == 2) {
                icon = ImageLoader.loadCardImage("/resources/cartas", anchoCarta, 130);
            } else {
                icon = ImageLoader.loadCardImage(card.getName(), anchoCarta, 130);
            }

            JLabel cardLabel = new JLabel(icon);
            cardLabel.setBounds(xInicial + i * (anchoCarta + espacio), y, anchoCarta, 130);
            dealerCardLabels.add(cardLabel);
            this.add(cardLabel);
        }
        this.revalidate();
        this.repaint();
    }

    private void runDealerTurn() {
        if (dealerHiddenCardLabel != null) {
            this.remove(dealerHiddenCardLabel);
            dealerHiddenCardLabel = null;
        }
        drawDealerCards(true);
        this.revalidate();
        this.repaint();

        List<Card> newDealerCards = game.dealerPlay();

        drawDealerCards(true);

        displayGameResult();
        updateUIForGameState();
    }

    private void displayGameResult() {
        String message = "";
        int playerValue = game.getPlayer().calculateHandValue();
        int dealerValue = game.getDealer().calculateHandValue();

        boolean playerBlackjack = (game.getPlayer().getHand().size() == 2 && playerValue == 21);
        boolean dealerBlackjack = (game.getDealer().getHand().size() == 2 && dealerValue == 21);

        if (playerValue > 21) {
            message = "¡Te pasaste de 21! Has perdido.";
        } else if (dealerValue > 21) {
            message = "¡La casa se pasó de 21! ¡Ganaste!";
        } else if (playerValue > dealerValue) {
            message = "¡Ganaste! Tu puntaje es mayor.";
        } else if (dealerValue > playerValue) {
            message = "La casa gana. Has perdido.";
        } else {
            message = "¡Empate!";
        }

        if (playerBlackjack && dealerBlackjack) {
            message = "¡Empate! Ambos tienen Blackjack.";
        } else if (playerBlackjack) {
            message = "¡Ganaste con Blackjack!";
        } else if (dealerBlackjack) {
            message = "La casa tiene Blackjack. Has perdido.";
        }

        int opcion = JOptionPane.showOptionDialog(
                this,
                message + "\n¿Quieres volver a jugar?",
                "Resultado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] { "Volver a jugar", "Retirarse" },
                "Volver a jugar");

        if (opcion == 0) {
            resetGameUI();
            game.resetForNewRound();
            updateUIForGameState();
        } else {
            System.exit(0);
        }
    }

    private void clearCardDisplay() {
        for (JLabel label : playerCardLabels) {
            this.remove(label);
        }
        playerCardLabels.clear();

        for (JLabel label : dealerCardLabels) {
            this.remove(label);
        }
        dealerCardLabels.clear();

        if (dealerHiddenCardLabel != null) {
            this.remove(dealerHiddenCardLabel);
            dealerHiddenCardLabel = null;
        }
        revalidate();
        repaint();
    }

    private void resetGameUI() {
        clearCardDisplay();
        addDeckBackCards();
    }
}