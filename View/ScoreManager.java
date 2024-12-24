package View;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    private List<ScoreEntry> scores;
    private static final String SCORE_FILE = "scores.json"; 

    public ScoreManager() {
        scores = new ArrayList<>();
        loadScores();
    }

    public void addOrUpdateScore(String playerName, int additionalScore, int additionalCoins, int additionalmonsters, int additionalswords, int additionalelexir, int additionaltresures) {
        for (ScoreEntry entry : scores) {
            if (entry.getPlayerName().equals(playerName)) {
                entry.updateScore(additionalScore, additionalCoins, additionalmonsters, additionalswords, additionalelexir, additionaltresures);
                saveScores();
                return;
            }
        }

        scores.add(new ScoreEntry(playerName, additionalScore, additionalCoins, additionalmonsters, additionalswords, additionalelexir, additionaltresures));
        saveScores();
    }

    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String playerName = jsonObject.optString("playerName", "Unknown Player");
                int score = jsonObject.optInt("score", 0);
                int coins = jsonObject.optInt("coins", 0);
                int monsters = jsonObject.optInt("monsters", 0);
                int swords = jsonObject.optInt("sword", 0);
                int elixirs = jsonObject.optInt("elixir", 0);
                int treasures = jsonObject.optInt("treasures", 0);

                scores.add(new ScoreEntry(playerName, score, coins, monsters, swords, elixirs, treasures));
            }
        } catch (FileNotFoundException e) {
            scores = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            JSONArray jsonArray = new JSONArray();
            for (ScoreEntry scoreEntry : scores) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("playerName", scoreEntry.getPlayerName());
                jsonObject.put("score", scoreEntry.getScore());
                jsonObject.put("coins", scoreEntry.getCoins());
                jsonObject.put("monsters", scoreEntry.getMonsters());
                jsonObject.put("sword", scoreEntry.getSwords());
                jsonObject.put("elixir", scoreEntry.getElixirs());
                jsonObject.put("treasures", scoreEntry.getTreasures());

                jsonArray.put(jsonObject);
            }
            writer.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHighScores() {
        
        JFrame frame = new JFrame("High Scores");
        frame.setSize(400, 300); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); 
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        
        for (ScoreEntry entry : scores) {
            textArea.append(entry.toString() + "\n");
        }

       
        frame.setVisible(true);
    }

    public List<ScoreEntry> getHighScores() {
        return scores;
    }
}