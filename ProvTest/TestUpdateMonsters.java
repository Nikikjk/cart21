package ProvTest;

import Model.GameField;
import Model.Hero;
import Model.Monster;
import Model.Monster2;
import Model.PlayingField2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestUpdateMonsters {

    private GameField field;
    private PlayingField2 playingField;

    @BeforeEach
    public void setUp() {
        field = new GameField(5, 5, "CustomLevels/Test1.txt", 1); 
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

        playingField = new PlayingField2(5, 5, 1);
        playingField.Field = field;
    }

    @Test
    public void testUpdateMonsters() {
        playingField.updateMonsters(); 

        char[][] expectedGrid = {
            {'.', '.', '.', '.', '.'},
            {'.', 'N', 'M', '.', '.'},
            {'.', '.', 'H', '.', '.'},
            {'.', '.', 'M', 'N', '.'},
            {'.', '.', '.', '.', '.'}
        };


        assertArrayEquals(expectedGrid, field.grid, "Ошибка в состоянии поля после обновления монстров.");
    }
}
