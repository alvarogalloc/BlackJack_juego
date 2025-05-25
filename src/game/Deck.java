
package game;

import java.util.*;

public class Deck {
    private static final Map<String, Integer> cardValues;
    private final List<Card> cards;
    private final List<Card> usedCards;

    static {
        cardValues = new HashMap<>();

        cardValues.put("2C.png", 2);
        cardValues.put("2D.png", 2);
        cardValues.put("2H.png", 2);
        cardValues.put("2S.png", 2);
        cardValues.put("3C.png", 3);
        cardValues.put("3D.png", 3);
        cardValues.put("3H.png", 3);
        cardValues.put("3S.png", 3);
        cardValues.put("4C.png", 4);
        cardValues.put("4D.png", 4);
        cardValues.put("4H.png", 4);
        cardValues.put("4S.png", 4);
        cardValues.put("5C.png", 5);
        cardValues.put("5D.png", 5);
        cardValues.put("5H.png", 5);
        cardValues.put("5S.png", 5);
        cardValues.put("6C.png", 6);
        cardValues.put("6D.png", 6);
        cardValues.put("6H.png", 6);
        cardValues.put("6S.png", 6);
        cardValues.put("7C.png", 7);
        cardValues.put("7D.png", 7);
        cardValues.put("7H.png", 7);
        cardValues.put("7S.png", 7);
        cardValues.put("8C.png", 8);
        cardValues.put("8D.png", 8);
        cardValues.put("8H.png", 8);
        cardValues.put("8S.png", 8);
        cardValues.put("9C.png", 9);
        cardValues.put("9D.png", 9);
        cardValues.put("9H.png", 9);
        cardValues.put("9S.png", 9);
        cardValues.put("10C.png", 10);
        cardValues.put("10D.png", 10);
        cardValues.put("10H.png", 10);
        cardValues.put("10S.png", 10);

        cardValues.put("JC.png", 10);
        cardValues.put("JD.png", 10);
        cardValues.put("JH.png", 10);
        cardValues.put("JS.png", 10);
        cardValues.put("KC.png", 10);
        cardValues.put("KD.png", 10);
        cardValues.put("KH.png", 10);
        cardValues.put("KS.png", 10);
        cardValues.put("QC.png", 10);
        cardValues.put("QD.png", 10);
        cardValues.put("QH.png", 10);
        cardValues.put("QS.png", 10);

        cardValues.put("AC.png", 11);
        cardValues.put("AD.png", 11);
        cardValues.put("AH.png", 11);
        cardValues.put("AS.png", 11);
    }

    public static int getValue(String cardName) {
        try {
            return cardValues.get(cardName);
        } catch (Exception e) {
            System.out.println("No existe esa carta!!");
        }
        return 0;
    }

    public Deck() {
        cards = new ArrayList<>();
        usedCards = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        for (Map.Entry<String, Integer> entry : cardValues.entrySet()) {
            cards.add(new Card(entry.getKey(), entry.getValue()));
        }
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (cards.isEmpty()) {

            resetUsedCards();
            initializeDeck();
        }
        Card card = cards.remove(0);
        usedCards.add(card);
        return card;
    }

    public void resetUsedCards() {
        usedCards.clear();
        cards.clear();
        initializeDeck();
    }
}