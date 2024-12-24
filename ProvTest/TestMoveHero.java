package ProvTest;

import Model.*;
public class TestMoveHero {

    public static void TestMoveHero() {
        
        GameField field = new GameField(5, 5, "CustomLevels/Test1.txt",1); // 5x5, сложность 1

        
        field.hero = new Hero(2, 2); 
        field.grid[2][2] = 'H'; 

        
        field.moveCount = 0;
        field.moves = 0;

        
        PlayingField2 playingField = new PlayingField2(5, 5, 1);
        playingField.Field = field;
        playingField.moveHero(-1, 0); 

        
        char[][] expectedGrid = {
            {'.', '.', '.', '.', '.'},
            {'.', '.', 'H', '.', '.'}, 
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'}
        };

        
        int expectedMoveCount = 1;
        int expectedMoves = 1;

        
        boolean gridResult = compareGrids(field.grid, expectedGrid);
        boolean moveCountResult = field.moveCount == expectedMoveCount;
        boolean movesResult = field.moves == expectedMoves;

        
        if (gridResult && moveCountResult && movesResult) {
            System.out.println("Тест moveHero пройден успешно!");
        } else {
            System.out.println("Тест moveHero не пройден!");
            if (!gridResult) {
                System.out.println("Ошибка в состоянии поля.");
            }
            if (!moveCountResult) {
                System.out.println("Ошибка в moveCount: " + field.moveCount + " != " + expectedMoveCount);
            }
            if (!movesResult) {
                System.out.println("Ошибка в moves: " + field.moves + " != " + expectedMoves);
            }
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
