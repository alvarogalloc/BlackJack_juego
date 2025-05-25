package game;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private HumanPlayer player;
    private Dealer dealer;
    private Deck deck;

    public enum GameState {
        AWAITING_BET,
        PLAYER_TURN,
        DEALER_TURN,
        GAME_OVER_ROUND
    }

    private GameState currentState;

    public BlackjackGame(int initialPlayerMoney) {
        this.player = new HumanPlayer("Player 1", initialPlayerMoney);
        this.dealer = new Dealer();
        this.deck = new Deck();
        this.currentState = GameState.AWAITING_BET;
    }

    public HumanPlayer getPlayer() {
        return player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void startGameRound() {
        if (player.getCurrentBet() == 0) {
            throw new IllegalStateException("Player must place a bet before starting.");
        }
        deck.resetUsedCards();
        player.clearHand();
        dealer.clearHand();

        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        int playerValue = player.calculateHandValue();
        int dealerValue = dealer.calculateHandValue();

        boolean playerBlackjack = (player.getHand().size() == 2 && playerValue == 21);
        boolean dealerBlackjack = (dealer.getHand().size() == 2 && dealerValue == 21);

        if (playerBlackjack && dealerBlackjack) {
            resolvePush("Both have Blackjack!");
        } else if (playerBlackjack) {
            resolvePlayerWin("Player wins with Blackjack!", true);
        } else if (dealerBlackjack) {
            resolveDealerWin("Dealer has Blackjack!");
        } else {
            currentState = GameState.PLAYER_TURN;
        }
    }

    public Card hitPlayer() {
        if (currentState != GameState.PLAYER_TURN) {
            throw new IllegalStateException("It's not the player's turn to hit.");
        }
        Card newCard = deck.dealCard();
        player.addCard(newCard);
        if (player.calculateHandValue() > 21) {
            resolveDealerWin("Player busted!");
        }
        return newCard;
    }

    public void standPlayer() {
        if (currentState != GameState.PLAYER_TURN) {
            throw new IllegalStateException("It's not the player's turn to stand.");
        }
        currentState = GameState.DEALER_TURN;

    }

    public void doubleDownPlayer() {
        if (currentState != GameState.PLAYER_TURN || player.getHand().size() != 2
                || !player.canAfford(player.getCurrentBet())) {
            throw new IllegalStateException("Cannot double down at this time.");
        }
        player.placeBet(player.getCurrentBet());
        Card newCard = deck.dealCard();
        player.addCard(newCard);
        if (player.calculateHandValue() > 21) {
            resolveDealerWin("Player busted on double down!");
        } else {
            currentState = GameState.DEALER_TURN;
        }
    }

    public List<Card> dealerPlay() {
        if (currentState != GameState.DEALER_TURN) {
            throw new IllegalStateException("It's not the dealer's turn.");
        }
        List<Card> newCards = new ArrayList<>();
        while (dealer.wantsToHit(player.calculateHandValue())) {
            Card newCard = deck.dealCard();
            dealer.addCard(newCard);
            newCards.add(newCard);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        resolveWinner();
        return newCards;
    }

    private void resolveWinner() {
        int playerValue = player.calculateHandValue();
        int dealerValue = dealer.calculateHandValue();

        if (playerValue > 21) {
            resolveDealerWin("Player busted! Dealer wins.");
        } else if (dealerValue > 21) {
            resolvePlayerWin("Dealer busted! Player wins.", false);
        } else if (playerValue > dealerValue) {
            resolvePlayerWin("Player has a higher score! Player wins.", false);
        } else if (dealerValue > playerValue) {
            resolveDealerWin("Dealer has a higher score! Dealer wins.");
        } else {
            resolvePush("It's a push!");
        }
    }

    private void resolvePlayerWin(String message, boolean isBlackjack) {
        int winnings = player.getCurrentBet();
        if (isBlackjack) {
            winnings = (int) (winnings * 2.5);
        } else {
            winnings *= 2;
        }
        player.addMoney(winnings);
        currentState = GameState.GAME_OVER_ROUND;
        System.out.println(message + " Player wins $" + winnings + ". New money: $" + player.getMoney());

    }

    private void resolveDealerWin(String message) {

        currentState = GameState.GAME_OVER_ROUND;
        System.out.println(message + " Player loses $" + player.getCurrentBet() + ". New money: $" + player.getMoney());

    }

    private void resolvePush(String message) {
        player.addMoney(player.getCurrentBet());
        currentState = GameState.GAME_OVER_ROUND;
        System.out.println(message + " Player gets bet back. New money: $" + player.getMoney());

    }

    public void resetForNewRound() {
        player.resetBet();
        currentState = GameState.AWAITING_BET;
    }
}