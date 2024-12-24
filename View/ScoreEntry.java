package View;
public class ScoreEntry {
    private String playerName;
    private int score;
    private int coins; 
    private int monsters; 
    private int swords; 
    private int elixirs; 
    private int treasures; 

    public ScoreEntry(String playerName, int score, int coins, int monsters, int swords, int elixirs, int treasures) {
        this.playerName = playerName;
        this.score = score;
        this.coins = coins;
        this.monsters = monsters;
        this.swords = swords;
        this.elixirs = elixirs;
        this.treasures = treasures; 
    }

 
    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public int getCoins() {
        return coins;
    }

    public int getMonsters() {
        return monsters;
    }

    public int getSwords() {
        return swords;
    }

    public int getElixirs() {
        return elixirs;
    }

    public int getTreasures() {
        return treasures; 
    }

    public void updateScore(int additionalScore, int additionalCoins, int additionalMonsters, int additionalSwords, int additionalElixirs, int additionalTreasures) {
        this.score += additionalScore;
        this.coins += additionalCoins; 
        this.monsters += additionalMonsters; 
        this.swords += additionalSwords; 
        this.elixirs += additionalElixirs; 
        this.treasures += additionalTreasures; 
    }
}
