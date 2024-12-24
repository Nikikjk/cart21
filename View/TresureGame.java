package View;
import javax.swing.*;

import Controller.TreasureHuntGame;
import Model.PlayingField2;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TresureGame extends JPanel {
    private PlayingField2 playingField;
    private JLabel coinsLabel;
    private JLabel movesLabel; 
    private JLabel messageLabel;
    private JLabel swordLabel;
    private JLabel moveLimitLabel; 
    private JLabel levelLabel;  
    private JLabel difficultyLevelLabel;
    private JLabel elecsirLimitLabel;

    
    private BufferedImage heroImage;
    private BufferedImage monsterImage;
    private BufferedImage monsterImage2;
    private BufferedImage BossImage;
    private BufferedImage treasureImage;
    private BufferedImage swordImage; 
    private BufferedImage elecsirImage;
    private BufferedImage PrepImage;
    private BufferedImage PustImage;
    private BufferedImage crystalImage;
    private BufferedImage background;

    public TresureGame(PlayingField2 playingField, TreasureHuntGame mainFrame, int difficultyLevel) {
        this.playingField = playingField;
        setPreferredSize(new Dimension(400, 400));
        loadBackgroundImage();

        setFocusable(true);

        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.anchor = GridBagConstraints.EAST; 
        
        
        coinsLabel = new JLabel("Монеты: " + playingField.getCoins());
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0; add(coinsLabel, gbc);

        movesLabel = new JLabel("Ходы: " + playingField.getMoveCount());
        gbc.gridy = 1; add(movesLabel, gbc);

        swordLabel = new JLabel("До перезарядки меча: " + playingField.getswordCooldown());
        gbc.gridy = 2; add(swordLabel, gbc);

        elecsirLimitLabel = new JLabel("Действие элексира: " + playingField.getElixirTurns());
        gbc.gridy = 3; add(elecsirLimitLabel, gbc);

        levelLabel = new JLabel("Уровень: " + playingField.getLevel());
        gbc.gridy = 4; add(levelLabel, gbc);

        difficultyLevelLabel = new JLabel("Уровень сложности: " + playingField.getDifficultyLevel());
        gbc.gridy = 5; add(difficultyLevelLabel, gbc);
        if(playingField.getLevel()==4){
            moveLimitLabel = new JLabel("Оставшиеся Ходы: " + playingField.getmoveLimit());
            gbc.gridy = 10; add(moveLimitLabel, gbc);
        }
        else{
            moveLimitLabel = new JLabel("Оставшиеся Ходы: неограничено " );
            gbc.gridy = 10; add(moveLimitLabel, gbc);
        }
        

        

        messageLabel = new JLabel("");
        gbc.gridy = 6; add(messageLabel, gbc);

        loadImages();

       
        JButton restartButton = new JButton("Перезапустить уровень");
        restartButton.addActionListener(e -> restartLevel(difficultyLevel));
        gbc.gridy = 7; add(restartButton, gbc);

        
        JButton backToMenuButton = new JButton("Вернуться в меню");
        backToMenuButton.addActionListener(e -> {
        mainFrame.showMenu(); 
        });
        gbc.gridy = 8; add(backToMenuButton, gbc);

        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                System.out.println("Движение");

                
                switch (key) {
                    case KeyEvent.VK_UP:
                        playingField.moveHero(-1, 0);
                        break;
                    case KeyEvent.VK_DOWN:
                        playingField.moveHero(1, 0);
                        break;
                    case KeyEvent.VK_LEFT:
                        playingField.moveHero(0, -1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        playingField.moveHero(0, 1);
                        break;
                }

                displayMessage("Вы движетесь."); 

                if (playingField.checkForItemCollection()) {
                    JOptionPane.showMessageDialog(TresureGame.this, "Вы собрали монету!", "Собран предмет", JOptionPane.INFORMATION_MESSAGE);
                }

                repaint();
                coinsLabel.setText("Монеты: " + playingField.getCoins());
                movesLabel.setText("Ходы: " + playingField.getMoveCount());
                if(playingField.getLevel()==4){
                    moveLimitLabel.setText("Оставшиеся Ходы: " + (playingField.getmoveLimit()-playingField.getMoveCount()));
                }
                swordLabel.setText("До перезарядки меча: " + playingField.getswordCooldown());
                elecsirLimitLabel.setText("Действие элексира: "+playingField.getElixirTurns());
                levelLabel.setText("Уровень: " + playingField.getLevel());
                difficultyLevelLabel.setText("Уровень сложности: " + playingField.getDifficultyLevel());
            }
        });

        setFocusable(true);
    }
    

    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(new File("Textures/GameBackground.png")); 
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }

    private void restartLevel(int difficultyLevel) {
        
        playingField.restartLevel(difficultyLevel); 
        updateLabels();
        displayMessage("Уровень перезапущен!");
        repaint();
    
        
        requestFocusInWindow();
    }

    private void updateLabels() {
        coinsLabel.setText("Монеты: " + playingField.getCoins());
        movesLabel.setText("Ходы: " + playingField.getMoveCount());
        moveLimitLabel.setText("Оставшиеся Ходы: " + playingField.getmoveLimit());
        swordLabel.setText("До перезарядки меча: " + playingField.getswordCooldown());
        elecsirLimitLabel.setText("Действие элексира: "+playingField.getElixirTurns());
        levelLabel.setText("До перезарядки меча: " + playingField.getLevel());
        difficultyLevelLabel.setText("До перезарядки меча: " + playingField.getDifficultyLevel());
    }

    public void displayMessage(String message) {
        messageLabel.setText(message); 
        System.out.println(message); 
    }

    private void drawGrid(Graphics g) {
        char[][] grid = playingField.getGrid();
        int cellSize = 40;
    
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                switch (grid[i][j]) {
                    case 'H':
                        if (heroImage != null) {
                            g.drawImage(heroImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                        break;
                    case 'T':
                        if (treasureImage != null) {
                            g.drawImage(treasureImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                        break;
                    case 'M':
                        if (monsterImage != null) {
                            g.drawImage(monsterImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                        break;
                    case 'N':
                        if (monsterImage2 != null) {
                            g.drawImage(monsterImage2, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                        break;
                    case 'B':
                        if (BossImage != null) {
                            g.drawImage(BossImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                        break;
                    case 'S':
                        if (swordImage != null) {
                            g.drawImage(swordImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                        break;
                    case 'E': 
                        if (elecsirImage != null) {
                            g.drawImage(elecsirImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                        break;
                    case 'K': 
                        if (crystalImage != null) {
                            g.drawImage(crystalImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                        break;
                    case 'X': 
                        if (PrepImage != null) {
                            g.drawImage(PrepImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                        break;
                    default:
                        if (PustImage != null) {
                            g.drawImage(PustImage, j * cellSize, i * cellSize, cellSize, cellSize, null);
                        }
                }
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize); 
            }
        }
    }

    private void loadImages() {
        try {
            heroImage = ImageIO.read(new File("Textures/Hero.png"));
            monsterImage = ImageIO.read(new File("Textures/Monster.png"));
            monsterImage2 = ImageIO.read(new File("Textures/Monster2.png"));
            BossImage= ImageIO.read(new File("Textures/Boss.png"));
            treasureImage = ImageIO.read(new File("Textures/Tresure.png"));
            swordImage = ImageIO.read(new File("Textures/sword.png"));
            elecsirImage = ImageIO.read(new File("Textures/elecsir.png"));
            crystalImage = ImageIO.read(new File("Textures/crystal.png"));
            PrepImage= ImageIO.read(new File("Textures/Prep.png"));
            PustImage= ImageIO.read(new File("Textures/Pust.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
    }
}