package Model;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import View.*;

public class PlayingField2 {

    public GameField Field;
    private int coins; // Количество собранных монет

    private boolean sword;// меч 

    private ScoreManager scoreManager;
 
    private int swordCooldown; // 
    private int bossFreezeTurns; // 
    private int difficultyLevel;
    private String player_name;


    public static class Coordinate {
        public int row;
        public int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public PlayingField2(int rows, int cols,int difficultyLevel) {
        this(rows, cols, null ,difficultyLevel );
        this.Field.modelevel=1;
         
    }


    public PlayingField2(int rows, int cols, String levelFile, int difficultyLevel) {
        //System.out.println(levelFile);
        this.scoreManager = new ScoreManager();
        this.difficultyLevel = difficultyLevel;
        if(levelFile==null){
            this.Field = new GameField(rows, cols, difficultyLevel);
        }
        else{
            this.Field = new GameField(rows, cols, levelFile, difficultyLevel);
        }
        
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.player_name = "p" + timeStamp;
    }

    public static List<Coordinate> Cor(int x, int y, int deltaRow, int deltaCol){
        List<Coordinate> traectpath=new ArrayList<>();
        
        if(deltaRow==0 && deltaCol>0){
            int b=0;
            for(int i=1;i<=deltaCol;i++){//ВПраво
                traectpath.add(new Coordinate(x,y+i));
            }
        }
        else if(deltaCol==0 && deltaRow>0){//Вниз
            for(int i=1;i<=deltaRow;i++){
                traectpath.add(new Coordinate(x+i,y));
            }
        }
        else if(deltaRow<0 && deltaCol==0){//Ввлево
            for(int i=-1;i>=deltaRow;i--){
                traectpath.add(new Coordinate(x+i,y));
            }
        }
        else if(deltaCol<0 && deltaRow==0){//Вверх
            for(int i=-1;i>=deltaCol;i--){
                traectpath.add(new Coordinate(x,y+i));
            }
        }

        return traectpath;

    }

    public void moveHero(int deltaRow, int deltaCol) {
        //System.out.println("move hero: deltaRow="+deltaRow+"   deltaCol="+deltaCol);
    
        if (Field.level == 4 && Field.moveCount >= Field.moveLimit) {
            System.out.println("Вы исчерпали лимит ходов для этого уровня!");
            JOptionPane.showMessageDialog(null, "У вас закончились ходы", "Информация", JOptionPane.INFORMATION_MESSAGE);
            GameMenu.showMenu(this, "moveLimit");
            return;
        }
    
        int moveDistance = (this.Field.elecsir && Field.elixirTurns > 0) ? 2 : 1; 
        List<Coordinate> traectpath = Cor(Field.hero.getRow(), Field.hero.getCol(), deltaRow * moveDistance, deltaCol * moveDistance);
    
        for (Coordinate e : traectpath) {
            if(e.row<0 || e.col<0 ||e.row>Field.getRows()||e.col>Field.getCols()){
                break;
            }
            char cell = Field.getGrid()[e.row][e.col];
            boolean canMove = isValidMove(e.row, e.col) && cell != 'M' && cell != 'N' && cell != 'B';
            
            if (canMove) {
                
                checkForEvents(Field.hero.getRow(), Field.hero.getCol());
    
                if (checkForEvents(e.row, e.col)) {
                    Field.getGrid()[0][0] = 'H';
                    return;
                }
    
                Field.getGrid()[Field.hero.getRow()][Field.hero.getCol()] = '.';
                Field.hero.move(e.row, e.col);
                Field.getGrid()[e.row][e.col] = 'H';
                
            } else {
                System.out.println("Невозможно переместиться в указанную позицию.");
                break;
            }
        }
        Field.moves++;
        Field.moveCount++; 
    
        updateMonsters();
        if (this.Field.boss != null) {
            updateBoss();
        }
    
        decrementCooldowns();
    }
    
public void updateMonsters() {
    for (int i = 0; i < Field.monsters.size(); i++) {
        //System.out.println("Монстр 1 появился" + i);
        MovableMonster monster = Field.monsters.get(i);
        if (monster.moveTowardsHero(Field.hero, Field.getGrid())) {
            if (!this.Field.sword || Field.swordCooldown>0) {
                
                int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Монстр поймал вас! Отдать 5 монет или закончить игру?",
                    "Выбор",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    
                    if (this.Field.coins < 5) {
                        System.out.println("У вас недостаточно монет! Игра окончена.");
                        GameMenu.showMenu(this, "monster");
                    } else {
                        this.Field.coins -= 5;
                        JOptionPane.showMessageDialog(
                            null,
                            "Монстр забрал ваши монеты: " + 5,
                            "Информация",
                            JOptionPane.INFORMATION_MESSAGE
                        );

                        
                        int heroRow = Field.hero.getRow();
                        int heroCol = Field.hero.getCol();

                        int[][] directions = {
                            {heroRow - 1, heroCol},
                            {heroRow + 1, heroCol},
                            {heroRow, heroCol - 1},
                            {heroRow, heroCol + 1}
                        };

                        boolean moved = false;

                        for (int[] dir : directions) {
                            int newRow = dir[0];
                            int newCol = dir[1];

                            if (isValidMove(newRow, newCol)) {
                                Field.hero.move(newRow, newCol);
                                Field.getGrid()[heroRow][heroCol] = '.';
                                Field.getGrid()[newRow][newCol] = 'H';
                                moved = true;
                                break;
                            }
                        }

                        if (!moved) {
                            System.out.println("Не удалось сбежать от монстра.");
                        }
                    }
                } else {
                    
                    System.out.println("Игра окончена.");
                    GameMenu.showMenu(this, "monster");
                }
            } else if (swordCooldown == 0) {
                System.out.println("Монстр убит!");
                new MessageWindow("Монстр убит!");
                Field.getGrid()[monster.getRow()][monster.getCol()] = '.';
                Field.monsters.remove(i);
                this.Field.coins += 10;
                Field.swordCooldown = 4;
                i--;
                scoreManager.addOrUpdateScore(player_name, 10, 10, 1, 0, 0, 0);
            } else {
                System.out.println("Меч на перезарядке! Подождите " + Field.swordCooldown + " ходов.");
                this.coins -= 5;
            }
        } else {
            Field.getGrid()[monster.getRow()][monster.getCol()] = 'M';
        }
    }

    
    for (int i = 0; i < Field.monsters2.size(); i++) {
        //System.out.println("Монстр 2 появился" + i);
        MovableMonster monster2 = Field.monsters2.get(i);
        if (monster2.moveTowardsHero(Field.hero, Field.getGrid())) {
            if (!this.Field.sword || Field.swordCooldown>0) {
                
                int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Монстр поймал вас! Отдать 5 монет или закончить игру?",
                    "Выбор",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    
                    if (this.Field.coins < 5) {
                        System.out.println("У вас недостаточно монет! Игра окончена.");
                        GameMenu.showMenu(this, "monster");
                    } else {
                        this.Field.coins -= 5;
                        JOptionPane.showMessageDialog(
                            null,
                            "Монстр забрал ваши монеты: " + 5,
                            "Информация",
                            JOptionPane.INFORMATION_MESSAGE
                        );

                        
                        int heroRow = Field.hero.getRow();
                        int heroCol = Field.hero.getCol();

                        int[][] directions = {
                            {heroRow - 1, heroCol},
                            {heroRow + 1, heroCol},
                            {heroRow, heroCol - 1},
                            {heroRow, heroCol + 1}
                        };

                        boolean moved = false;

                        for (int[] dir : directions) {
                            int newRow = dir[0];
                            int newCol = dir[1];

                            if (isValidMove(newRow, newCol)) {
                                Field.hero.move(newRow, newCol);
                                Field.getGrid()[heroRow][heroCol] = '.';
                                Field.getGrid()[newRow][newCol] = 'H';
                                moved = true;
                                break;
                            }
                        }

                        if (!moved) {
                            System.out.println("Не удалось сбежать от второго монстра.");
                        }
                    }
                } else {
                    
                    System.out.println("Игра окончена.");
                    GameMenu.showMenu(this, "monster");
                }
            } else if (Field.swordCooldown == 0) {
                System.out.println("Монстр убит!");
                new MessageWindow("Монстр убит!");
                Field.getGrid()[monster2.getRow()][monster2.getCol()] = '.';
                Field.monsters2.remove(i);
                this.Field.coins += 15;
                Field.swordCooldown = 4;
                i--;
                scoreManager.addOrUpdateScore(player_name, 15, 15, 1, 0, 0, 0);
            } else {
                System.out.println("Меч на перезарядке! Подождите " + swordCooldown + " ходов.");
                this.Field.coins -= 5;
            }
        } else {
            Field.getGrid()[monster2.getRow()][monster2.getCol()] = 'N';
        }
    }

}

public void updateBoss() {
        Field.getGrid()[Field.boss.getRow()][Field.boss.getCol()] = '.'; 

    //System.out.println("Босс появился");
    if (Field.bossFreezeTurns > 0) {
        System.out.println("Босс заморожен! Осталось ходов: " + bossFreezeTurns);
        new MessageWindow("Босс заморожен! Осталось ходов:"+ bossFreezeTurns);
        Field.bossFreezeTurns--; 
        Field.getGrid()[Field.boss.getRow()][Field.boss.getCol()] = 'B'; 
        return; 
    }

    if (Field.boss.moveTowardsHero(Field.hero, Field.getGrid())) {
        if (!this.Field.sword ||Field.swordCooldown>0) {
            
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Босс поймал вас! Отдать 5 монет или закончить игру?",
                "Выбор",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                
                if (this.Field.coins < 5) {
                    System.out.println("У вас недостаточно монет! Игра окончена.");
                    GameMenu.showMenu(this, "monster");
                } else {
                    this.Field.coins -= 5;
                    JOptionPane.showMessageDialog(
                        null,
                        "Босс забрал ваши монеты: " + 5,
                        "Информация",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    
                    int heroRow = Field.hero.getRow();
                    int heroCol = Field.hero.getCol();

                    int[][] directions = {
                        {heroRow - 1, heroCol},
                        {heroRow + 1, heroCol},
                        {heroRow, heroCol - 1},
                        {heroRow, heroCol + 1}
                    };

                    boolean moved = false;

                    for (int[] dir : directions) {
                        int newRow = dir[0];
                        int newCol = dir[1];

                        if (isValidMove(newRow, newCol)) {
                            Field.hero.move(newRow, newCol);
                            Field.getGrid()[heroRow][heroCol] = '.';
                            Field.getGrid()[newRow][newCol] = 'H';
                            moved = true;
                            break;
                        }
                    }

                    if (!moved) {
                        System.out.println("Не удалось сбежать от босса.");
                    }
                }
            } else {
                
                System.out.println("Игра окончена.");
                GameMenu.showMenu(this, "monster");
            }
        } else if (Field.swordCooldown == 0) {
            System.out.println("Босс замарожен");
            new MessageWindow("Босс замарожен на 4 хода");
            Field.swordCooldown = 6;
            Field.bossFreezeTurns=4;
            scoreManager.addOrUpdateScore(player_name, 15, 15, 1, 0, 0, 0); 
        } else {
            System.out.println("Меч на перезарядке! Подождите " + Field.swordCooldown + " ходов.");
            this.Field.coins -= 5;
        }
    } else {
        Field.getGrid()[Field.boss.getRow()][Field.boss.getCol()] = 'B'; 
    }
    
}

public void decrementCooldowns() {
    if (Field.swordCooldown > 0) {
        Field.swordCooldown--;
    }
    
    if (Field.bossFreezeTurns > 0) {
        Field.bossFreezeTurns--;
        
    }
    if (Field.elixirTurns > 0) {
        Field.elixirTurns--;
        if (Field.elixirTurns == 0) {
            Field.elecsir = false; 
            new MessageWindow("Действие эликсира закончилось!");
        }
    }
}


    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < Field.getRows() && col >= 0 && col < Field.getCols(); 
    }
    public boolean checkForEvents(int row, int col) {
        //System.out.println("чек");
        if (Field.getGrid()[row][col] == 'T') { 
            //System.out.println("вошел в сокровище");
            new MessageWindow("Поздравляем! Вы нашли сокровище!");
            
            Field.getGrid()[row][col] = '.'; 
            this.Field.coins += 10; 

            scoreManager.addOrUpdateScore(player_name, 10, 10,0,0,0,1); 
            
            Field.treasures.removeIf(t -> t.getRow() == row && t.getCol() == col); 
            
            if (Field.treasures.isEmpty() && Field.modelevel==1) { 
                new MessageWindow("Вы собрали все сокровища на уровне " + Field.level + "! Переходите на следующий уровень.");
                JOptionPane.showMessageDialog(null, "Вы собрали все сокровища на уровне " + Field.level + "! Переходите на следующий уровень.", "Уровень завершен", JOptionPane.INFORMATION_MESSAGE);
                Field.nextLevel(difficultyLevel);
                return true; 
            }
            else if (Field.treasures.isEmpty() && Field.modelevel==0) { 
                new MessageWindow("Вы собрали все сокровища на уровне " + Field.level + "! Переходите на следующий уровень.");
                JOptionPane.showMessageDialog(null, "Вы собрали все сокровища на уровне " + Field.level + "! Переходите на следующий уровень.", "Уровень завершен", JOptionPane.INFORMATION_MESSAGE);
                if((Field.getLevel()>=5 || difficultyLevel>3)){
                    new MessageWindow("Все уровни пройдены игра пройдена.");
                    System.exit(0); 
                }
                Field.nextLevelcompany(difficultyLevel);
                return true; 
            }
            else if(Field.modelevel==3){
                //System.out.println("modelevel=" + Field.modelevel);
                new MessageWindow("Все уровни пройдены игра пройдена.");
                System.exit(0); 
            }
            
        } else if (Field.getGrid()[row][col] == 'M' || Field.getGrid()[row][col] == 'N' || Field.getGrid()[row][col] == 'B' ) { 
            if (!this.sword) { 
                if (this.coins < 5 || this.coins < 0) { 
                    new MessageWindow("Вы встретили монстра! Игра окончена.");
                    System.exit(0); 
                    
                }
                new MessageWindow("Монстр забрал у вас 5 монет.");
                this.Field.coins -= 5;
    
                
                scoreManager.addOrUpdateScore(player_name, -5, -5,0,0,0,0); 
                
            } else { 
                Field.coins += 5; 
                new MessageWindow("Вы победили монстра и получили 5 монет!");
                Field.getGrid()[row][col] = '.'; 
                

                scoreManager.addOrUpdateScore(player_name, 5, 5,1,0,0,0); 
            }
            
        } else if (Field.getGrid()[row][col] == 'X') { 
            //System.out.println("вошел в чек "+row+" "+col); 
            //System.out.println("вошел в препятвие");
            
            Field.coins++; 
            new MessageWindow("Вы разрушили препятствие и получили монету!");
            
            Field.getGrid()[row][col] = '.';
            
          
            scoreManager.addOrUpdateScore(player_name, 1, 1,0,0,0,0);
            
        } else if (Field.getGrid()[row][col] == 'S') { 
            System.out.println("вошел в меч");
            new MessageWindow("Вы нашли меч, вы можете атаковать монстров!");
            this.Field.sword = true;
           scoreManager.addOrUpdateScore(player_name, 0, 0,0,1,0,0); 
            
       } else if (Field.getGrid()[row][col] == 'E') { 
           System.out.println("вошел в эликсир");
           
           new MessageWindow("Вы выпили эликсир, дальность хода увеличена на 2 на 5 ходов!");

           this.Field.elecsir = true;
           this.Field.elixirTurns += 5; 
    
       
           scoreManager.addOrUpdateScore(player_name, 0, 0,0,0,1,0);
            
       } else if (Field.getGrid()[row][col] == 'K') { 
            System.out.println("вошел в кристал");
            
            new MessageWindow("Вы нашли кристал предельное время увеличено на  6 ходов!");
            Field.moveLimit+=6;
    

            
    }
       return false; 
    }

    public boolean checkForItemCollection() {
        int heroRow = Field.hero.getRow();
        int heroCol = Field.hero.getCol();
        
        if (Field.getGrid()[heroRow][heroCol] == 'T' || Field.getGrid()[heroRow][heroCol] == 'M' || Field.getGrid()[heroRow][heroCol] == 'N' ||Field.getGrid()[heroRow][heroCol] == 'B' || Field.getGrid()[heroRow][heroCol] == 'S' || Field.getGrid()[heroRow][heroCol] == 'E' || Field.getGrid()[heroRow][heroCol] == 'X' || Field.getGrid()[heroRow][heroCol] == 'K') {
            System.out.println("heroRow"+heroRow+"heroCol"+heroCol);
            checkForEvents(heroRow, heroCol);
            return true; 
        }
        
        return false; 
    }

    public void restartLevel(int difficultyLevel){
        Field.restartLevel(difficultyLevel);
    }

    public void loadLevel(String filename) {
        System.out.println(filename);
        if(Field.isCustomLevel(filename)){
            Field.loadCustomLevel(filename);
        }
        else{
            Field.loadCompanyLevel(filename);
        }
        
    }

    public void printgrid(){
        Field.printgrid();
    }
  
    public char[][] getGrid() { 
         return Field.getGrid(); 
     }

     public Hero getHero() { 
         return Field.getHero(); 
     }

     public List<Monster> getMonsters() { 
         return Field.getMonsters(); 
     }

     public List<Monster2> getMonsters2() { 
        return Field.getMonsters2(); 
    }
    public Boss getBoss() { 
        return Field.getBoss(); 
    }

     public List<Treasure> getTreasures() { 
         return Field.getTreasures(); 
     }

     public int getCoins() { 
         return Field.getCoins(); 
     }
     public boolean getSword() {
        return Field.getSword(); 
    } 

    public boolean getElecsir() {
        return Field.getElecsir(); 
    }

    public int getElixirTurns() {
        return Field.getElixirTurns(); 
    }
    

    public int getMoves() { 
         return Field.getMoves(); 
    } 

    public int getLevel() { 
         return Field.getLevel(); 
    }

    public int getDifficultyLevel() { 
        return Field.getDifficultyLevel(); 
    }
    public int getMoveCount() { 
        return Field.getMoveCount();
    }
    public int getmoveLimit() { 
        return Field.getmoveLimit();
    }
    public int getswordCooldown() { 
        return Field.getswordCooldown();
    }

    
}