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

import View.MessageWindow;
import View.ScoreManager;

public class GameField {
    public int moveCount;//количесво ходов
    private int rows; // Количество строк
    private int cols; // Количество столбцов
    public char[][] grid; // Игровая карта
    public Hero hero; // Главный герой
    public List<Monster> monsters; // Список монстров
    public List<Monster2> monsters2; // Список монстров, которые ходят на две клетки
    public Boss boss;
    public List<Treasure> treasures; // Список сокровищ
    public int coins; // Количество собранных монет
    public int moves; // Количество сделанных ходов
    public int level; // Текущий уровень
    public boolean sword;// меч 
    public boolean elecsir;//элескир
    public int moveLimit; // 
    public int swordCooldown; // 
    public int bossFreezeTurns; 
    public int elixirTurns;
    public int modelevel;
    public int difficultyLevel;
    public int cristal;
    private String filename;
    private static final int MAX_MOVE_LIMIT = 30; // 
    private static final int TARGET_ROW = 10; // 
    private static final int TARGET_COL = 10; // 

    public boolean isCustomLevel(String levelFile) {
        if(levelFile==null){
            return false;
        }
        return !levelFile.matches("levels/level\\d+dif\\d+\\.txt");
    }

    public GameField(int rows, int cols,int difficultyLevel ) {
        this(rows, cols, null ,difficultyLevel );
        this.modelevel=1;
         
    }
    

    public GameField(int rows, int cols, String levelFile,int difficultyLevel) {
        System.out.println("Сборка");
        this.difficultyLevel = difficultyLevel;
        this.moveCount = 0;
        this.cristal = 0;
        this.elixirTurns = 0;
        this.bossFreezeTurns=0;
        this.filename=levelFile;
        this.rows = rows;
        this.cols = cols;
        this.level = 1; 
        this.grid = new char[rows][cols];
        this.monsters = new ArrayList<>();
        this.monsters2 = new ArrayList<>();
        this.treasures = new ArrayList<>();
        this.coins = 0;
        this.moves = 0;
        this.moveLimit=30;
        this.modelevel=0;
        this.swordCooldown=0;
        this.sword = false;
        this.elecsir = false;
        System.out.println(levelFile);
        
    
        if (levelFile == null) {
            this.rows = rows;
            this.cols = cols;
            initializeGrid(difficultyLevel);
             
        } 
        else if(isCustomLevel(levelFile)){
            System.out.println(isCustomLevel(levelFile));
            this.modelevel=3;
            loadCustomLevel(levelFile);
            this.rows = grid.length;
            this.cols = grid[0].length;
        }
        else {
            System.out.println(isCustomLevel(levelFile));
            loadCompanyLevel(levelFile);
            this.rows = grid.length;
            this.cols = grid[0].length;
            
        }
        
    }

    public void initializeGrid(int difficultyLevel) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 'X'; 
            }
        }
        
        placeHero();
        placeTreasure(); 
        placeMonsters(difficultyLevel);
        placeMonsters2(difficultyLevel);
        placesword();
        placeelecsir();
        placecristal();
        
    }


    public void placeHero() {
        hero = new Hero(0, 0); 
        grid[hero.getRow()][hero.getCol()] = 'H'; // 'H' герой
    }

    public void placeTreasure() {
        Random rand = new Random();
        for (int i = 0; i < 1; i++) { 
            int row, col;
            do {
                row = rand.nextInt(rows);
                col = rand.nextInt(cols);
            } while (grid[row][col] != 'X'); 

            Treasure treasure = new Treasure(row, col); 
            treasures.add(treasure); 
            grid[treasure.getRow()][treasure.getCol()] = 'T'; // 'T' сокровище
        }
    }

    public void placeMonsters(int difficultyLevel) {
        Random rand = new Random();
        if(difficultyLevel==1){
            for (int i = 0; i < 1; i++) { 
                int row, col;
                do {
                    row = rand.nextInt(rows);
                    col = rand.nextInt(cols);
                } while (grid[row][col] != 'X'); 
    
                Monster monster = new Monster(row, col);
                monsters.add(monster);
                grid[row][col] = 'M'; // 'M' монстр
            }
        }
        else if(difficultyLevel==2){
            for (int i = 0; i < 3; i++) { 
                int row, col;
                do {
                    row = rand.nextInt(rows);
                    col = rand.nextInt(cols);
                } while (grid[row][col] != 'X'); 
    
                Monster monster = new Monster(row, col);
                monsters.add(monster);
                grid[row][col] = 'M'; // 'M' монстр
            }
        }
        else if(difficultyLevel==3){
            for (int i = 0; i < 5; i++) { 
                int row, col;
                do {
                    row = rand.nextInt(rows);
                    col = rand.nextInt(cols);
                } while (grid[row][col] != 'X'); 
    
                Monster monster = new Monster(row, col);
                monsters.add(monster);
                grid[row][col] = 'M'; // 'M' монстр
            }
        }
        
    }

    public void placeMonsters2(int difficultyLevel) {
        Random rand = new Random();
        if(difficultyLevel==1){
            for (int i = 0; i < 1; i++) { 
                int row, col;
                do {
                    row = rand.nextInt(rows);
                    col = rand.nextInt(cols);
                } while (grid[row][col] != 'X'); 
    
                Monster2 monster2 = new Monster2(row, col);
                monsters2.add(monster2);
                grid[row][col] = 'N'; // 'M' монстр
            }
        }
        else if(difficultyLevel==2){
            for (int i = 0; i < 2; i++) { 
                int row, col;
                do {
                    row = rand.nextInt(rows);
                    col = rand.nextInt(cols);
                } while (grid[row][col] != 'X'); 
    
                Monster2 monster2 = new Monster2(row, col);
                monsters2.add(monster2);
                grid[row][col] = 'N'; // 'M' монстр
            }
        }
        else if(difficultyLevel==3){
            for (int i = 0; i < 3; i++) { 
                int row, col;
                do {
                    row = rand.nextInt(rows);
                    col = rand.nextInt(cols);
                } while (grid[row][col] != 'X'); 
    
                Monster2 monster2 = new Monster2(row, col);
                monsters2.add(monster2);
                grid[row][col] = 'N'; // 'M' монстр
            }
        }
    }

    public void placeBoss() {
        Random rand = new Random();
        for (int i = 0; i < 1; i++) { 
            int row, col;
            do {
                row = rand.nextInt(rows);
                col = rand.nextInt(cols);
            } while (grid[row][col] != 'X'); 
    
            boss = new Boss(row, col);
            grid[row][col] = 'B'; // 'B' Босс
            }
    }
        
        
 
    public void placesword() {
        Random rand = new Random();
        for (int i = 0; i < 4; i++) { // Например, 30 препятствий
            int row, col;
            do {
                row = rand.nextInt(rows);
                col = rand.nextInt(cols);
            } while (grid[row][col] != 'X'); 

            grid[row][col] = 'S'; // 'X'  препятствие
        }
    }

    public void placecristal() {
        Random rand = new Random();
        for (int i = 0; i < 4; i++) { // Например, 30 препятствий
            int row, col;
            do {
                row = rand.nextInt(rows);
                col = rand.nextInt(cols);
            } while (grid[row][col] != 'X'); 

            grid[row][col] = 'K'; // 'X'  препятствие
        }
    }

    public void placeelecsir() {
        Random rand = new Random();
        for (int i = 0; i < 3; i++) { // Например, 30 препятствий
            int row, col;
            do {
                row = rand.nextInt(rows);
                col = rand.nextInt(cols);
            } while (grid[row][col] != 'X'); 

            grid[row][col] = 'E'; // 'E'  элексир
        }
    }

    public void nextLevel(int difficultyLevel) {
        level++;
        System.out.println("Теперь вы на уровне " + level + "!");
        this.coins=0;
        this.moveCount=0;
        this.sword=false;
        this.elecsir=false;
        this.monsters = new ArrayList<>();
        this.monsters2 = new ArrayList<>();
        this.boss = null;
        //grid[hero.getRow()][hero.getCol()] = 'H';
        
        initializeGrid(difficultyLevel); 
        //grid[0][0]='X';
        if(level==4){
            moveLimit=MAX_MOVE_LIMIT;
        }
        if(level==5){
            monsters.clear();
            monsters2.clear();
            placeBoss();

        }
    }

    public void nextLevelcompany(int difficultyLevel) {
        level++;
        this.coins=0;
        this.moveCount=0;
        this.sword=false;
        this.elecsir=false;
        this.monsters = new ArrayList<>();
        this.monsters2 = new ArrayList<>();
        this.boss = null;
        if((level>5 || difficultyLevel>3)){
            new MessageWindow("Все уровни пройдены игра пройдена.");
            System.exit(0); // Завершение игры
        }
        this.filename="levels/level"+level+"dif"+difficultyLevel+".txt";
        loadCompanyLevel(this.filename);
        if(level==4){
            moveLimit=MAX_MOVE_LIMIT;
        }
    }

    public void restartLevel(int difficultyLevel) {
        if (modelevel==1) {
            level--;
            reset(difficultyLevel);
            this.coins = 0;
            this.moveCount = 0;
            this.sword = false;
            this.elecsir = false;
            this.monsters = new ArrayList<>();
            this.monsters2 = new ArrayList<>();
            this.boss = null;
            nextLevel(difficultyLevel);
            System.out.println(hero.toString());
            System.out.println(level);
        } 
        else if(modelevel==0) {
            reset(difficultyLevel);
            this.coins = 0;
            this.moveCount = 0;
            this.sword = false;
            this.elecsir = false;
            this.monsters = new ArrayList<>();
            this.monsters2 = new ArrayList<>();
            this.boss = null;
            loadCompanyLevel(filename);
            System.out.println(hero.toString());
            System.out.println(level);
        }
        else{
            reset(difficultyLevel);
            this.coins = 0;
            this.moveCount = 0;
            this.sword = false;
            this.elecsir = false;
            this.monsters = new ArrayList<>();
            this.monsters2 = new ArrayList<>();
            this.boss = null;
            loadCustomLevel(filename);
            System.out.println(hero.toString());
            System.out.println(level);
        }
    
    }
    
    
    public void loadCustomLevel(String filename) {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<char[]> newGrid = new ArrayList<>();
            
            
            
            while ((line = br.readLine()) != null) {
                String s = String.join("", line.split("\\s+"));
                newGrid.add(s.toCharArray());
            }
            for (int i = 0; i < newGrid.size(); i++) {
                    System.out.println(""+newGrid.get(i).length+" "+i);
                }
            
            if (newGrid.isEmpty()) {
                throw new IllegalArgumentException("Файл уровня пуст: " + filename);
            }
    
            
            this.grid = new char[newGrid.size()][newGrid.get(0).length];
            for(int i=0;i<newGrid.size();i++){
                for(int j=0;j<newGrid.get(0).length;j++){
                    grid[i][j]='.';

                }
            }
            for (int i = 0; i < newGrid.size(); i++) {
                if (newGrid.get(i).length != grid[0].length) {
                    throw new IllegalArgumentException("Неверная ширина строки в файле уровня: " + filename);
                }
                this.grid[i] = newGrid.get(i);
            }
            
            
            monsters.clear();
            monsters2.clear();
            treasures.clear();
            hero=null;
            
            boolean heroFound = false; 
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    char cell = grid[i][j];
                    if (cell == 'H') {
                        hero = new Hero(i, j);
                        heroFound = true;
                        System.out.println("Герой 1" + hero);
                    } else if (cell == 'M') {
                        monsters.add(new Monster(i, j));
                    } else if (cell == 'N') {
                        monsters2.add(new Monster2(i, j));
                    }else if (cell == 'B') {
                        boss=new Boss(i,j);
                    } else if (cell == 'T') {
                        treasures.add(new Treasure(i, j));
                    }
                    
                }
            }
    
            if (!heroFound) {
                throw new IllegalArgumentException("Герой не найден в файле уровня: " + filename);
            }
    
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Герой " + hero);
    }



    public void loadCompanyLevel(String filename) {
        if(isCustomLevel(filename)){
            loadCustomLevel(filename);
        }
        level=filename.charAt(12)-'0';
        loadCustomLevel(filename);

        
    }
    public void printgrid(){
        for(int i=0;i < grid.length;i++){
            for(int j=0;j < grid[0].length;j++){
                System.out.print(grid[i][j]);
            
            }
            System.out.print("\n");
        }
    }

    

    public void reset(int difficultyLevel) {
        this.coins = 0;
        this.moves = 0;
        this.moveCount = 0;
        this.hero=null;
        this.sword = false;
        this.elecsir = false;
        this.boss=null;
        this.monsters.clear();
        this.monsters2.clear();
        this.treasures.clear();
        initializeGrid(difficultyLevel);
    }
    
    
    public char[][] getGrid() { 
         return grid; 
     }

     public Hero getHero() { 
         return hero; 
     }

     public List<Monster> getMonsters() { 
         return monsters; 
     }

     public List<Monster2> getMonsters2() { 
        return monsters2; 
    }
    public Boss getBoss() { 
        return boss; 
    }

     public List<Treasure> getTreasures() { 
         return treasures; 
     }

     public int getCoins() { 
         return coins; 
     }
     public boolean getSword() {
        return sword; 
    } 

    public boolean getElecsir() {
        return elecsir; // Returns true if the elixir has been collected
    }

    public int getElixirTurns() {
        return elixirTurns; 
    }
    

    public int getMoves() { 
         return moves; 
    } 

    public int getLevel() { 
         return level; 
    }

    public int getDifficultyLevel() { 
        return difficultyLevel; 
    }

    
    public int getMoveCount() { 
        return moveCount;
    }
    public int getmoveLimit() { 
        return moveLimit;
    }
    public int getswordCooldown() { 
        return swordCooldown;
    }

    public int getRows() { 
        return rows; 
   } 
   public int getCols() { 
    return cols; 
} 


}
