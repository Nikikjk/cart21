package View;

import javax.swing.*;

import Controller.TreasureHuntGame;
import Model.PlayingField2;

public class GameMenu {
    private static int difficultyLevel;
    private TreasureHuntGame game;  

    public GameMenu(TreasureHuntGame game){
        this.game=game;
    }
    
    public static void showMenu(PlayingField2 playingField, String situation) {
        
        String message;
        if ("monster".equals(situation)) {
            message = "Вы были пойманы монстром!";
        } else if ("moveLimit".equals(situation)) {
            message = "Вы исчерпали лимит ходов для этого уровня!";
        } else {
            message = "Неизвестная ситуация!";
        }

        String[] options = {"Перезапустить уровень", "Выйти из игры"};
        int choice = JOptionPane.showOptionDialog(null,
                message,
                "Меню",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            playingField.restartLevel(difficultyLevel); 
        }  else if (choice == 1) {
            System.exit(0);
        }
    }

    public static void setDifficultyLevel(int level) {
        difficultyLevel = level; 
    }


}
