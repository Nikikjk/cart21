package Model;

import java.util.List;

public class Monster implements MovableMonster {
    private int row;
    private int col;

    public Monster(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    protected void setRow(int row) {
        this.row = row;
    }
    
    protected void setCol(int col) {
        this.col = col;
    }
    public boolean moveMonster(int deltaRow, int deltaCol,char[][] grid) {
        
        List<PlayingField2.Coordinate> traectpath=PlayingField2.Cor(getRow(),getCol(),deltaRow,deltaCol);
        
        
        for(PlayingField2.Coordinate e: traectpath){
     
            boolean canMove = isValidMove(e.row, e.col,grid) && grid[e.row][e.col] != 'M' && grid[e.row][e.col] != 'N'; 
            if(grid[e.row][e.col] == 'H'){
                return true; 
            } 
            if (canMove) {
                
                grid[getRow()][getCol()] = '.';
                setRow(e.row);
                setCol(e.col);
                 
                grid[e.row][e.col] = 'M';
                
            }
            else{
                break;
            } 
            

        }
        return false;

    }

    public boolean moveTowardsHero(Hero hero, char[][] grid) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        int deltaRow=0;
        int deltaCol=0;

        
        if (Math.abs(heroRow - getRow()) > Math.abs(heroCol - getCol())) { 
            if (heroRow < getRow() ) {
                deltaRow=-1; // Движение вверх на 2 клетки
            } else if (heroRow > getRow() ) {
                deltaRow=+1; // Движение вниз на 2 клетки
            }
        } else { 
            if (heroCol < getCol() ) {
                deltaCol=-1; // Движение влево на 2 клетки
            } else if (heroCol > getCol() ) {
                deltaCol=+1; // Движение вправо на 2 клетки
            }
        }
        return moveMonster(deltaRow,deltaCol,grid);

       
    }

    protected boolean isValidMove(int newRow, int newCol, char[][] grid) {
        
        return newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] != 'M' && grid[newRow][newCol] != 'T' ;
    }
   
}