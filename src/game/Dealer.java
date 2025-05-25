package game;

public class Dealer extends Player {


    @Override
    public boolean wantsToHit(int dealerUpCardValue) {
        return calculateHandValue() < 17;
    }
}