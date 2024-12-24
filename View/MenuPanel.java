package View;

import javax.sound.sampled.*;
import javax.swing.*;
import Controller.TreasureHuntGame;
import Model.LevelEditor;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private BufferedImage background; 
    private int difficultyLevel; 

    public MenuPanel(TreasureHuntGame game) {
        System.out.println("Старт");
        loadBackgroundImage();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        createButtons(game);
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        revalidate(); 
        repaint(); 

    }

    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(new File("Textures/MenuBackground.png")); 
            System.out.println("Фон загружен успешно."+background);
        } catch (Exception e) {
            System.err.println("Ошибка загрузки фонового изображения: " + e.getMessage());
        }
    }

    private void createButtons(TreasureHuntGame game) {
        add(Box.createVerticalGlue());
    
        JButton startButton = createButton("Textures/start_button.png", "Start Game", e -> {
            playButtonSound(); 
            startGame(game);
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 10))); 
    
        JButton difficultyButton = createDifficultyButton(game);
        difficultyButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(difficultyButton);
        add(Box.createRigidArea(new Dimension(0, 10))); 
    
        JButton randomLevelButton = createButton("Textures/random_level_button.png", "Random Level", e -> {
            playButtonSound(); 
            game.startRandomGame(difficultyLevel);
        });
        randomLevelButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(randomLevelButton);
        add(Box.createRigidArea(new Dimension(0, 10))); 
    
        JButton exitButton = createButton("Textures/exit_button.png", "Exit", e -> {
            playButtonSound(); 
            System.exit(0);
        });
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(exitButton);
        add(Box.createRigidArea(new Dimension(0, 10))); 
    
        JButton leaderboardButton = createButton("Textures/leaderboard_button.png", "Leaderboard", e -> {
            playButtonSound(); 
            showLeaderboard(game);
        });
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(leaderboardButton);
        add(Box.createRigidArea(new Dimension(0, 10))); 
    
        JButton levelEditorButton = createButton("Textures/level_editor_button.png", "Level Editor", e -> {
            playButtonSound(); 
            openLevelEditor(game);
        });
        levelEditorButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(levelEditorButton);
        add(Box.createRigidArea(new Dimension(0, 10))); 
    
        JButton customLevelButton = createCustomLevelButton(game);
        customLevelButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(customLevelButton);
    
        add(Box.createVerticalGlue());
    }

    private JButton createDifficultyButton(TreasureHuntGame game) {
        JButton difficultyButton = new JButton(new ImageIcon("Textures/Difficult_level.png"));
        difficultyButton.setPreferredSize(new Dimension(150, 40));
        difficultyButton.setMaximumSize(new Dimension(150, 40));
        
        difficultyButton.setToolTipText("Выбрать уровень сложности");
        
        difficultyButton.addActionListener(e -> {
            String[] options = {"Легкий", "Средний", "Сложный"};
            int selectedOption = JOptionPane.showOptionDialog(this,
                    "Выберите уровень сложности:",
                    "Уровень сложности",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);
            
            if (selectedOption != -1) {
                difficultyLevel = selectedOption + 1; 
                System.out.println("Выбранный уровень сложности: " + difficultyLevel);
            }
        });
        
        return difficultyButton; 
    }

    private JButton createButton(String iconPath, String tooltip, ActionListener action) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton(icon);
        
        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));
        
        button.setToolTipText(tooltip);
        
        button.addActionListener(action);
        
        return button;
    }

    private JButton createCustomLevelButton(TreasureHuntGame game) {
        JButton customLevelButton = new JButton(new ImageIcon("Textures/custom_level_button.png"));
        
        customLevelButton.setPreferredSize(new Dimension(150, 40));
        customLevelButton.setMaximumSize(new Dimension(150, 40));
        
        customLevelButton.setToolTipText("Load Custom Level");
        
        customLevelButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Выберите файл уровня");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Файлы уровней (*.txt)", "txt"));
            fileChooser.setCurrentDirectory(new File("CustomLevels"));
            fileChooser.setAcceptAllFileFilterUsed(false); 

            int userSelection = fileChooser.showOpenDialog(game);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                game.startCustomGame(selectedFile.getAbsolutePath(), difficultyLevel); 
            }
        });

        return customLevelButton; 
    }

    private void showLeaderboard(TreasureHuntGame game) {
        List<ScoreEntry> highScores = game.getScoreManager().getHighScores();
        JFrame leaderboardFrame = new JFrame("Таблица рекордов");
        leaderboardFrame.setSize(800, 400); 
        leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboardFrame.setLocationRelativeTo(null);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); 
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        leaderboardFrame.add(scrollPane, BorderLayout.CENTER);
    
        
        for (int i = 0; i < highScores.size(); i++) {
            ScoreEntry entry = highScores.get(i);
            textArea.append((i + 1) + ": " + entry.getPlayerName() +", Очки: " + entry.getScore() +
                            ", Монеты: " + entry.getCoins() +
                            ", Побеждено монстров: " + entry.getMonsters() +
                            ", Собрано мечей: " + entry.getSwords() +
                            ", Собрано эликсиров: " + entry.getElixirs() +
                            ", Собрано сокровищ: " + entry.getTreasures() + "\n");
        }
    
        leaderboardFrame.setVisible(true);
    }

    private void openLevelEditor(TreasureHuntGame game) {
       LevelEditor levelEditor = new LevelEditor(game); 
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (background != null) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}

    private void startGame(TreasureHuntGame game) {
       String[] options = {"Уровень 1", "Уровень 2", "Уровень 3","Уровень 4","Уровень 5"};
       int level = JOptionPane.showOptionDialog(game,
               "Выберите уровень:", "Выбор уровня",
               JOptionPane.DEFAULT_OPTION,
               JOptionPane.INFORMATION_MESSAGE,
               null,
               options,
               options[0]);
       
       if (level != -1) {
           game.startCompanyGame("levels/level" + (level + 1) + "dif" + difficultyLevel + ".txt", difficultyLevel);
       }
    }

    private void playButtonSound() {
       try {
           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/cnop.wav")); 
           Clip clip = AudioSystem.getClip();
           clip.open(audioInputStream);
           clip.start();
           
           clip.addLineListener(event -> {
               if (event.getType() == LineEvent.Type.STOP) {
                   clip.close();
               }
           });
           
       } catch (Exception e) {
           System.err.println("Ошибка при воспроизведении звука: " + e.getMessage());
       }
   }
}
