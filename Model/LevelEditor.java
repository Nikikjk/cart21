package Model;
import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.TreasureHuntGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;

public class LevelEditor extends JFrame {
    private char[][] grid;
    private int rows = 20; 
    private int cols = 20; 
    private JButton[][] buttons;
    private TreasureHuntGame game; 

    private BufferedImage heroImage;
    private BufferedImage monsterImage;
    private BufferedImage treasureImage;
    private BufferedImage obstacleImage; 
    private BufferedImage emptyImage;
    private BufferedImage swordImage; 
    private BufferedImage elecsirImage;
    private BufferedImage monster2Image;
    private BufferedImage BossImage;

    private char selectedObject = '.'; 

    public LevelEditor(TreasureHuntGame game) { 
        this.game = game; 
        setTitle("Конструктор уровня");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); 

        grid = new char[rows][cols];
        buttons = new JButton[rows][cols];

        loadImages(); 

        
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new FlowLayout());

        
        toolPanel.add(createToolButton("Пусто", '.', emptyImage));
        toolPanel.add(createToolButton("Камень", 'X', obstacleImage));
        toolPanel.add(createToolButton("Герой", 'H', heroImage));
        toolPanel.add(createToolButton("Обычный монстр", 'M', monsterImage));
        toolPanel.add(createToolButton("Сильный монстр", 'N', monster2Image));
        toolPanel.add(createToolButton("Меч", 'S', swordImage));
        toolPanel.add(createToolButton("Эликсир", 'E', elecsirImage));
        toolPanel.add(createToolButton("Сокровище", 'T', treasureImage));
        toolPanel.add(createToolButton("Босс", 'B', BossImage));

        add(toolPanel, BorderLayout.NORTH); 

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols)); 
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.'; 
                buttons[i][j] = new JButton();
                buttons[i][j].setActionCommand(i + "," + j);
                buttons[i][j].addActionListener(new ButtonClickListener());
                updateButtonIcon(i, j); 
                buttons[i][j].setPreferredSize(new Dimension(40, 40)); 
                gridPanel.add(buttons[i][j]);
            }
        }
        add(gridPanel, BorderLayout.CENTER); 

        JPanel controlPanel = new JPanel(); 
        JTextField levelNameField = new JTextField("Введите имя уровня", 20);
        controlPanel.add(levelNameField); 

        JButton saveButton = new JButton("Сохранить уровень");
        saveButton.addActionListener(e -> {
            String levelName = levelNameField.getText().trim();
            if (!levelName.isEmpty()) {
                saveLevel(levelName); 
            } else {
                JOptionPane.showMessageDialog(this, "Имя уровня не может быть пустым.");
            }
        });
        controlPanel.add(saveButton); 

        JButton backButton = new JButton("Назад в меню");
        backButton.addActionListener(e -> {
            this.dispose(); 
            game.showMenu(); 
        });
        controlPanel.add(backButton); 

        add(controlPanel, BorderLayout.SOUTH); 

        setVisible(true);
    }

    private void loadImages() {
        try {
            heroImage = ImageIO.read(new File("Textures/Hero.png"));
            monsterImage = ImageIO.read(new File("Textures/Monster.png"));
            treasureImage = ImageIO.read(new File("Textures/Tresure.png"));
            obstacleImage = ImageIO.read(new File("Textures/Prep.png")); 
            emptyImage = ImageIO.read(new File("Textures/Pust.png")); 
            swordImage = ImageIO.read(new File("Textures/sword.png")); 
            elecsirImage = ImageIO.read(new File("Textures/elecsir.png")); 
            monster2Image = ImageIO.read(new File("Textures/Monster2.png")); 
            BossImage = ImageIO.read(new File("Textures/Boss.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка при загрузке изображений: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateButtonIcon(int row, int col) {
        switch (grid[row][col]) {
            case '.':
                if (emptyImage != null) {
                    buttons[row][col].setIcon(resizeImage(emptyImage, 90, 40)); 
                }
                break;
            case 'H':
                if (heroImage != null) {
                    buttons[row][col].setIcon(resizeImage(heroImage, 90, 40)); 
                }
                break;
            case 'M':
                if (monsterImage != null) {
                    buttons[row][col].setIcon(resizeImage(monsterImage, 90, 40)); 
                }
                break;
            case 'N':
                if (monster2Image != null) {
                    buttons[row][col].setIcon(resizeImage(monster2Image, 90, 40)); 
                }
                break;
            case 'B':
                if (BossImage != null) {
                    buttons[row][col].setIcon(resizeImage(BossImage, 90, 40)); 
                }
                break;
            case 'S':
                if (swordImage != null) {
                    buttons[row][col].setIcon(resizeImage(swordImage, 90, 40)); 
                }
                break;
            case 'X':
                if (obstacleImage != null) {
                    buttons[row][col].setIcon(resizeImage(obstacleImage, 90, 40)); 
                }
                break;
            case 'E':
                if (elecsirImage != null) {
                    buttons[row][col].setIcon(resizeImage(elecsirImage, 90, 40)); 
                }
                break;
            case 'T':
                if (treasureImage != null) {
                    buttons[row][col].setIcon(resizeImage(treasureImage, 90, 40)); 
                }
                break;
            default:
                buttons[row][col].setIcon(null); 
                break;
        }
    }

    private ImageIcon resizeImage(BufferedImage img, int width, int height) {
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    private JButton createToolButton(String label, char object, BufferedImage image) {
        JButton button = new JButton(label);
        if (image != null) {
            button.setIcon(resizeImage(image, 40, 40));
        }
        button.addActionListener(e -> selectedObject = object); 
        return button;
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String[] coords = command.split(",");
            int row = Integer.parseInt(coords[0]);
            int col = Integer.parseInt(coords[1]);

            grid[row][col] = selectedObject; 
            updateButtonIcon(row, col); 
        }
    }

    private void saveLevel(String levelName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CustomLevels/" + levelName + ".txt"))) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    writer.write(grid[i][j]);
                }
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Уровень \"" + levelName + "\" сохранен!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка при сохранении уровня.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LevelEditor(new TreasureHuntGame())); 
    }
}