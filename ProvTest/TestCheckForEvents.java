package ProvTest;
import Model.*;
public class TestCheckForEvents {

    public static void TestCheckForEvents() {
        
        GameField field = new GameField(5, 5, "CustomLevels/Test1.txt",1); // 5x5, сложность 1

  
        field.hero = new Hero(2, 2); 
        field.grid[2][2] = 'H'; 
        field.printgrid();

      
        field.coins = 0;
        field.sword = false;
        field.elecsir = false;
        field.moveLimit = 0;

       
        PlayingField2 playingField = new PlayingField2(5, 5, 1);
        playingField.Field = field;
        playingField.Field.printgrid();

        
        field.grid[1][1] = 'M'; 
        field.coins = 10; 
        playingField.checkForEvents(1, 1);
        boolean monsterEncountered = field.grid[1][1] == '.' && field.coins == 5;
        System.out.println("Тест монстра без меча: " + (monsterEncountered ? "Пройден" : "Не пройден"));

        
        field.grid[1][1] = 'X'; 
        field.coins = 0; 
        playingField.checkForEvents(1, 1);
        boolean obstacleDestroyed = field.grid[1][1] == '.' && field.coins == 1;
        System.out.println("Тест препятствия: " + (obstacleDestroyed ? "Пройден" : "Не пройден"));

      
        field.grid[1][1] = 'S'; 
        playingField.checkForEvents(1, 1);
        boolean swordCollected = field.grid[1][1] == '.' && field.sword;
        System.out.println("Тест меча: " + (swordCollected ? "Пройден" : "Не пройден"));

     
        field.grid[1][1] = 'E'; 
        playingField.checkForEvents(1, 1);
        boolean elixirCollected = field.grid[1][1] == '.' && field.elecsir;
        System.out.println("Тест эликсира: " + (elixirCollected ? "Пройден" : "Не пройден"));

   
        field.grid[1][1] = 'K'; 
        field.moveLimit = 0; 
        playingField.checkForEvents(1, 1);
        boolean crystalCollected = field.grid[1][1] == '.' && field.moveLimit == 6;
        System.out.println("Тест кристалла: " + (crystalCollected ? "Пройден" : "Не пройден"));

      
         field.grid[1][1] = 'T'; 
         playingField.checkForEvents(1, 1);
         boolean treasureCollected = field.grid[1][1] == '.' && field.coins == 10;
         System.out.println("Тест сокровища: " + (treasureCollected ? "Пройден" : "Не пройден"));
    }


}
