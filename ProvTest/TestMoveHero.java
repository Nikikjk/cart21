package ProvTest;

import Model.GameField;
import Model.Hero;
import Model.PlayingField2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMoveHero {

    private GameField field;
    private PlayingField2 playingField;

    @BeforeEach
    public void setUp() {
        field = new GameField(5, 5, "CustomLevels/Test1.txt", 1);
        field.hero = new Hero(2, 2);
        field.grid[2][2] = 'H';
        field.moveCount = 0;
        field.moves = 0;

        playingField = new PlayingField2(5, 5, 1);
        playingField.Field = field;
    }

    @Test
    public void testMoveHero() {
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

        assertArrayEquals(expectedGrid, field.grid, "Ошибка в состоянии поля.");

        assertEquals(expectedMoveCount, field.moveCount, "Ошибка в moveCount: " + field.moveCount + " != " + expectedMoveCount);


        assertEquals(expectedMoves, field.moves,"Ошибка в moves: " + field.moves + " != " + expectedMoves);
    }
}
