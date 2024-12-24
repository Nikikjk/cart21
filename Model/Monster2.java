package Model;
import java.util.ArrayList;
import java.util.List;

import Model.PlayingField2.Coordinate;

public class Monster2 extends Monster implements MovableMonster{
    public Monster2(int row, int col){
        super(row,col);
    }
    
    @Override
    public boolean moveTowardsHero(Hero hero, char[][] grid) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        int deltaRow=0;
        int deltaCol=0;

        
        if (Math.abs(heroRow - getRow()) >= Math.abs(heroCol - getCol())) { 
            if (heroRow < getRow() ) {
                deltaRow=-2; // Движение вверх на 2 клетки
            } else if (heroRow > getRow() ) {
                deltaRow=+2; // Движение вниз на 2 клетки
            }
        } else { 
            if (heroCol < getCol() ) {
                deltaCol=-2; // Движение влево на 2 клетки
            } else if (heroCol > getCol() ) {
                deltaCol=+2; // Движение вправо на 2 клетки
            }
        }
        return moveMonster(deltaRow,deltaCol,grid);

       
    }
 
}
