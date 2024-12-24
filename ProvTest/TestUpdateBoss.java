package ProvTest;
import Model.*;
public class TestUpdateBoss {
    public static void TestUpdateBoss() {
        
        GameField field = new GameField(5, 5, "CustomLevels/Test1.txt",1); 
    
        
        field.hero = new Hero(2, 2); 
        field.boss = new Boss(1, 1); 
    
        
        field.grid[2][2] = 'H'; 
        field.grid[1][1] = 'B'; 
    
        
        PlayingField2 playingField = new PlayingField2(5, 5, 1);
        playingField.Field = field;
        playingField.updateBoss();
    
        
        char[][] expectedGrid = {
            {'.', '.', '.', '.', '.'},
            {'.', 'B', '.', '.', '.'},
            {'.', '.', 'H', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'}
        };
    
        
        boolean result = compareGrids(field.grid, expectedGrid);
    
        
        if (result) {
            System.out.println("Тест босса пройден успешно!");
        } else {
            System.out.println("Тест босса не пройден!");
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

