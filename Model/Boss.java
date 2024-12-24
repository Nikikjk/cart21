package Model;
public class Boss extends Monster implements MovableMonster {

    public Boss(int row, int col){
        super(row,col);
    }

    @Override
    public boolean moveTowardsHero(Hero hero, char[][] grid) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        int deltaRow = 0;
        int deltaCol = 0;
    
        
        int rowDiff = heroRow - getRow();
        int colDiff = heroCol - getCol();

        if (Math.abs(rowDiff) > Math.abs(colDiff)) {
            if (rowDiff < 0) {
                deltaRow = -1; 
            } else if (rowDiff > 0) {
                deltaRow = 1; 
            }
            
            if (colDiff < 0) {
                deltaCol = -1; 
            } else if (colDiff > 0) {
                deltaCol = 1; 
            }
        } else { 
            
            if (colDiff < 0) {
                deltaCol = -1; 
            } else if (colDiff > 0) {
                deltaCol = 1; 
            }
            
            if (rowDiff < 0) {
                deltaRow = -1; 
            } else if (rowDiff > 0) {
                deltaRow = 1; 
            }
        }
    
        return moveMonster(deltaRow, deltaCol, grid);
    }

    @Override
    public boolean moveMonster(int deltaRow, int deltaCol, char[][] grid) {
    
    int previousRow = getRow();
    int previousCol = getCol();

    
    int newRow = previousRow + deltaRow;
    int newCol = previousCol + deltaCol;

    
    if (isValidMove(newRow, newCol, grid) && grid[newRow][newCol] != 'M' && grid[newRow][newCol] != 'N') {
        
        if (grid[newRow][newCol] == 'H') {
            setRow(previousRow);
            setCol(previousCol);
            grid[previousRow][previousCol] = 'B'; 
            return true; 
        }

        
        grid[previousRow][previousCol] = '.'; 
        setRow(newRow); 
        setCol(newCol);
        grid[newRow][newCol] = 'B'; 

        return false; 
    }

    return false; 
}
    
    
    
}
