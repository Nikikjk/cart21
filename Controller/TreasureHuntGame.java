package Controller;
import javax.swing.*;

import Model.PlayingField2;
import View.*;
import ProvTest.TestUpdateMonsters;
import ProvTest.TestUpdateBoss;
import ProvTest.TestMoveHero;
//import ProvTest.TestCheckForEvents;

import java.awt.*;
import java.util.List;

public class TreasureHuntGame extends JFrame {
    private CardLayout cardLayout;
    public  MenuPanel menuPanel;
    private TresureGame gamePanel;
    private ScoreManager scoreManager;

    public TreasureHuntGame() {
        System.out.println("Старт");
        setTitle("Treasure Hunt Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        scoreManager = new ScoreManager();
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        menuPanel = new MenuPanel(this);
        add(menuPanel, "Menu");
        gamePanel = null;
        setVisible(true);

    }

    public void startCompanyGame(String levelFile,int difficultyLevel) {
        if(difficultyLevel==0){
            int level=levelFile.charAt(12)-'0';
            difficultyLevel=1;
            levelFile="levels/level"+level+"dif"+difficultyLevel+".txt";
        }
        PlayingField2 playingField = new PlayingField2(20, 20, levelFile, difficultyLevel); 
        gamePanel = new TresureGame(playingField, this, difficultyLevel);
        
        add(gamePanel, "Game");
        cardLayout.show(this.getContentPane(), "Game");
    }

    public void startCustomGame(String levelFile,int difficultyLevel) {
        PlayingField2 playingField = new PlayingField2(20, 20, levelFile, difficultyLevel); 
        gamePanel = new TresureGame(playingField, this, difficultyLevel);
        
        add(gamePanel, "Game");
        cardLayout.show(this.getContentPane(), "Game");
    }
    
    public void startRandomGame(int difficultyLevel) {
        PlayingField2 playingField = new PlayingField2(20, 20,difficultyLevel); 
        
        if (gamePanel != null) {
            remove(gamePanel); 
        }
        
        gamePanel = new TresureGame(playingField, this, difficultyLevel);
        add(gamePanel, "Game");
        cardLayout.show(this.getContentPane(), "Game");
        gamePanel.requestFocusInWindow(); 
    }

    public void showMenu() {
        cardLayout.show(this.getContentPane(), "Menu");
    }

    public ScoreManager getScoreManager() {
        return scoreManager; 
    }

    

    public static void main(String[] args) {
        System.out.println("Старт");
        if(args.length>0){
            TestUpdateMonsters.TestUpdateMonsters();
            TestMoveHero.TestMoveHero();
            TestUpdateBoss.TestUpdateBoss();
        }
        else{
            SwingUtilities.invokeLater(TreasureHuntGame::new);
        }
        
    }
}
