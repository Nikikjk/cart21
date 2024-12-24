package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageWindow extends JFrame {
    public MessageWindow(String message) {
        setTitle("Ситуация");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        add(label);

        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width - getWidth(), 1); 

        
        Timer timer = new Timer(1000, new ActionListener() { // 1000мс
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });
        timer.setRepeats(false); 
        timer.start();

        setVisible(true);
    }
}