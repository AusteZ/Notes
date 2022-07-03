package GUI;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;

public class StyleSheet {
    public static void frameStyle(JFrame frame){
        frame.setTitle("Notes");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1000,680);
        frame.getContentPane().setBackground(new Color(0x210F60));
        frame.setVisible(true);
    }
    public static JButton buttonStyle(String text){
        JButton button = new JButton(text);
        button.setBackground(new Color(0x2A71B0));
        button.setFont(new Font("Times New Roman", Font.BOLD,22));
        button.setFocusable(false);
        button.setForeground(new Color(0x210F60));
        button.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return button;
    }
    public static void ffff(){
        
    }
    
}