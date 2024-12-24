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
    
        // Вычисляем разницу между позицией героя и босса
        int rowDiff = heroRow - getRow();
        int colDiff = heroCol - getCol();
    
        // Определяем направление движения по вертикали
        if (Math.abs(rowDiff) > Math.abs(colDiff)) {
            if (rowDiff < 0) {
                deltaRow = -1; // Движение вверх на 1 клетку
            } else if (rowDiff > 0) {
                deltaRow = 1; // Движение вниз на 1 клетку
            }
            // Определяем направление движения по горизонтали
            if (colDiff < 0) {
                deltaCol = -1; // Движение влево на 1 клетку
            } else if (colDiff > 0) {
                deltaCol = 1; // Движение вправо на 1 клетку
            }
        } else { 
            // Если разница по столбцам больше или равна, то двигаемся по горизонтали
            if (colDiff < 0) {
                deltaCol = -1; // Движение влево на 1 клетку
            } else if (colDiff > 0) {
                deltaCol = 1; // Движение вправо на 1 клетку
            }
            // Определяем направление движения по вертикали
            if (rowDiff < 0) {
                deltaRow = -1; // Движение вверх на 1 клетку
            } else if (rowDiff > 0) {
                deltaRow = 1; // Движение вниз на 1 клетку
            }
        }
    
        return moveMonster(deltaRow, deltaCol, grid);
    }

    @Override
    public boolean moveMonster(int deltaRow, int deltaCol, char[][] grid) {
    // Сохраняем текущую позицию босса
    int previousRow = getRow();
    int previousCol = getCol();

    // Вычисляем новую позицию босса
    int newRow = previousRow + deltaRow;
    int newCol = previousCol + deltaCol;

    // Проверяем, может ли босс двигаться на новую позицию
    if (isValidMove(newRow, newCol, grid) && grid[newRow][newCol] != 'M' && grid[newRow][newCol] != 'N') {
        // Проверяем, не попадает ли босс на позицию героя
        if (grid[newRow][newCol] == 'H') {
            // Если босс пытается занять клетку героя, возвращаем его на предыдущую позицию
            setRow(previousRow);
            setCol(previousCol);
            grid[previousRow][previousCol] = 'B'; // Возвращаем босса на предыдущую позицию
            return true; // Босс поймал героя
        }

        // Обновляем позицию босса в сетке
        grid[previousRow][previousCol] = '.'; // Убираем босса с текущей позиции
        setRow(newRow); // Обновляем координаты босса
        setCol(newCol);
        grid[newRow][newCol] = 'B'; // Устанавливаем босса на новую позицию

        return false; // Босс успешно переместился, но не поймал героя
    }

    return false; // Босс не смог переместиться
}
    
    
    
}
