
import java.util.*;

public class Deck {
    private static final Map<String, Integer> cardValues;
    private final List<String> usedCards;

    static {
        cardValues = new HashMap<String, Integer>();

        // Asignar valores a las cartas numéricas
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

        // Asignar valores a las cartas de figuras
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

        // Asignar valor al As (puede ser 1 o 11, dependiendo del contexto del juego)
        cardValues.put("AC.png", 11);
        cardValues.put("AD.png", 11);
        cardValues.put("AH.png", 11);
        cardValues.put("AS.png", 11);

        // (AH, AS, QS) -> 11 + 11 + 10
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
        usedCards = new ArrayList<>();
    }

    public String getRandomCard() {
        List<String> keys = new ArrayList<>(cardValues.keySet());

        Random random = new Random();
        int randomIndex = random.nextInt(keys.size());

        String randomCard = keys.get(randomIndex);
        usedCards.add(randomCard);

        return randomCard;
    }

    public void resetUsedCards() {
        usedCards.clear();
    }

}