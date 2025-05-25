
package game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected List<Card> hand;

    public Player() {
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public void clearHand() {
        hand.clear();
    }

    public int calculateHandValue() {
        int sum = 0;
        int aces = 0;
        for (Card card : hand) {
            sum += card.getValue();
            if (card.getName().startsWith("A")) {
                aces++;
            }
        }
        while (sum > 21 && aces > 0) {
            sum -= 10;
            aces--;
        }
        return sum;
    }

    public abstract boolean wantsToHit(int dealerUpCardValue);
}