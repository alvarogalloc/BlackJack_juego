package game;

public class Dealer extends Player {
    public Dealer() {
        super("Dealer");
    }

    @Override
    public boolean wantsToHit(int dealerUpCardValue) {
        return calculateHandValue() < 17;
    }
}