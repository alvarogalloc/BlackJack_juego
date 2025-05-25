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
        this.player = new HumanPlayer(initialPlayerMoney);
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
            resolvePush();
        } else if (playerBlackjack) {
            resolvePlayerWin(true);
        } else if (dealerBlackjack) {
            resolveDealerWin();
        } else {
            currentState = GameState.PLAYER_TURN;
        }
    }

    public Card hitPlayer() {
        Card newCard = deck.dealCard();
        player.addCard(newCard);
        if (player.calculateHandValue() > 21) {
            resolveDealerWin();
        }
        return newCard;
    }

    public void standPlayer() {
        currentState = GameState.DEALER_TURN;
    }

    public void doubleDownPlayer() {
        player.placeBet(player.getCurrentBet());
        Card newCard = deck.dealCard();
        player.addCard(newCard);
        if (player.calculateHandValue() > 21) {
            resolveDealerWin();
        } else {
            currentState = GameState.DEALER_TURN;
        }
    }

    public List<Card> dealerPlay() {
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
            resolveDealerWin();
        } else if (dealerValue > 21) {
            resolvePlayerWin( false);
        } else if (playerValue > dealerValue) {
            resolvePlayerWin( false);
        } else if (dealerValue > playerValue) {
            resolveDealerWin();
        } else {
            resolvePush();
        }
    }

    private void resolvePlayerWin(boolean isBlackjack) {
        int winnings = player.getCurrentBet();
        if (isBlackjack) {
            winnings = (int) (winnings * 2.5);
        } else {
            winnings *= 2;
        }
        player.addMoney(winnings);
        currentState = GameState.GAME_OVER_ROUND;
    }

    private void resolveDealerWin() {
        currentState = GameState.GAME_OVER_ROUND;
    }

    private void resolvePush() {
        player.addMoney(player.getCurrentBet());
        currentState = GameState.GAME_OVER_ROUND;
    }

    public void resetForNewRound() {
        player.resetBet();
        currentState = GameState.AWAITING_BET;
    }
}