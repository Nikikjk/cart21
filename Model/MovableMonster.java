package Model;
public interface MovableMonster {
    int getRow();
    int getCol();
    boolean moveTowardsHero(Hero hero, char[][] grid);
}
