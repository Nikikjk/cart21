package ProvTest;

import Model.GameField;
import Model.Hero;
import Model.PlayingField2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCheckForEvents {

    private GameField field;
    private PlayingField2 playingField;


    @BeforeEach
    public void setUp() {
        field = new GameField(5, 5, "CustomLevels/Test1.txt", 1);
        field.hero = new Hero(2, 2);
        field.coins = 0; 
        field.sword = false; 
        field.elecsir = false; 
        field.moveLimit = 0; 
        playingField = new PlayingField2(5, 5, 1);
        playingField.Field = field;
    }

    @Test
    public void testTreasureFound() {
        field.grid[1][1] = 'T'; 
        boolean result = playingField.checkForEvents(1, 1); 

        assertTrue(result);
        assertEquals(10, field.coins); 
        assertEquals('.', field.grid[1][1]); 
    }

    @Test
    public void testMonsterEncounterWithoutSword() {
        field.coins = 4; 
        field.grid[1][1] = 'M'; 
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            playingField.checkForEvents(1, 1); 
        });

        assertEquals("Вы встретили монстра! Игра окончена.", exception.getMessage());
    }

    @Test
    public void testMonsterEncounterWithSword() {
        field.coins = 10; 
        field.sword = true; 
        field.grid[1][1] = 'M'; 

        boolean result = playingField.checkForEvents(1, 1); 

        assertTrue(result);
        assertEquals(15, field.coins); 
        assertEquals('.', field.grid[1][1]); 
    }

    @Test
    public void testObstacleDestruction() {
        field.grid[1][1] = 'X'; 

        boolean result = playingField.checkForEvents(1, 1); 

        assertFalse(result);
        assertEquals(1, field.coins); 
        assertEquals('.', field.grid[1][1]); 
    }

    @Test
    public void testSwordFound() {
        field.grid[2][2] = 'S'; 
        boolean result = playingField.checkForEvents(2, 2); 

        assertFalse(result);
        assertTrue(field.sword); 
    }

    @Test
    public void testElixirFound() {
        field.grid[3][3] = 'E'; 

        boolean result = playingField.checkForEvents(3, 3); 

        assertFalse(result);
        assertTrue(field.elecsir); 
        assertEquals(5, field.elixirTurns); 
    }

    @Test
    public void testCrystalFound() {
        field.grid[4][4] = 'K'; 

        boolean result = playingField.checkForEvents(4, 4); 

        assertFalse(result);
        assertEquals(6, field.moveLimit); 
    }
}
