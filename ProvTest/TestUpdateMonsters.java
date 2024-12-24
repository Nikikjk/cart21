package ProvTest;
import Model.*;

public class TestUpdateMonsters {

    public static void TestUpdateMonsters() {
        
        GameField field = new GameField(5, 5,"CustomLevels/Test1.txt" ,1); // 5x5, сложность 1

       
        field.hero = new Hero(2, 2); 
        field.monsters.add(new Monster(1, 1)); 
        field.monsters.add(new Monster(3, 3));
        field.monsters2.add(new Monster2(1, 3)); 
        field.monsters2.add(new Monster2(3, 1)); 
        

       
        field.grid[2][2] = 'H'; 
        field.grid[1][1] = 'M'; 
        field.grid[3][3] = 'M';
        field.grid[1][3] = 'N'; 
        field.grid[3][1] = 'N';  
        field.printgrid();

        
        PlayingField2 playingField = new PlayingField2(5, 5, 1);
        playingField.Field = field;
        
        playingField.updateMonsters();
        field.printgrid();

        
        char[][] expectedGrid = {
            {'.', '.', '.', '.', '.'},
            {'.', 'N', 'M', '.', '.'},
            {'.', '.', 'H', '.', '.'},
            {'.', '.', 'M', 'N', '.'},
            {'.', '.', '.', '.', '.'}
        };

        
        boolean result = compareGrids(field.grid, expectedGrid);

        
        if (result) {
            System.out.println("Тест монстров пройден успешно!");
        } else {
            System.out.println("Тест монстров не пройден!");
        }
    }

    
    private static boolean compareGrids(char[][] actual, char[][] expected) {
        if (actual.length != expected.length || actual[0].length != expected[0].length) {
            return false;
        }

        for (int i = 0; i < actual.length; i++) {
            for (int j = 0; j < actual[i].length; j++) {
                if (actual[i][j] != expected[i][j]) {
                    System.out.println("Ошибка на позиции [" + i + "][" + j + "]: " + actual[i][j] + " != " + expected[i][j]);
                    return false;
                }
            }
        }

        return true;
    }

}
