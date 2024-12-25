package ProvTest;

import Model.GameField;
import Model.Hero;
import Model.Boss;
import Model.PlayingField2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestUpdateBoss {

    private GameField field;
    private PlayingField2 playingField;

    @BeforeEach
    public void setUp() {
        field = new GameField(5, 5, "CustomLevels/Test1.txt", 1);
        field.hero = new Hero(2, 2);
        field.boss = new Boss(1, 1);
        
        field.grid[2][2] = 'H'; 
        field.grid[1][1] = 'B'; 
        
        playingField = new PlayingField2(5, 5, 1);
        playingField.Field = field;
    }

    @Test
    public void testUpdateBoss() {
        playingField.updateBoss();

        char[][] expectedGrid = {
            {'.', '.', '.', '.', '.'},
            {'.', 'B', '.', '.', '.'},
            {'.', '.', 'H', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'}
        };

        assertArrayEquals(expectedGrid, field.grid, "Ошибка в состоянии поля после обновления босса.");
    }
}

