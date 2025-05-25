
package game;

public class HumanPlayer extends Player {
    private int money;
    private int currentBet;

    public HumanPlayer(String name, int initialMoney) {
        super(name);
        this.money = initialMoney;
        this.currentBet = 0;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public boolean canAfford(int amount) {
        return money >= amount;
    }

    public void placeBet(int amount) throws IllegalArgumentException {
        if (!canAfford(amount)) {
            throw new IllegalArgumentException("Not enough money to place this bet.");
        }
        this.money -= amount;
        this.currentBet += amount;
    }

    public void resetBet() {
        this.currentBet = 0;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    @Override
    public boolean wantsToHit(int dealerUpCardValue) {

        return false;
    }
}