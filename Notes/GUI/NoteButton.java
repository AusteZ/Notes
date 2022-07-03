package GUI;
import Notes.*;
import javax.swing.*;
import java.awt.*;
/**@author Austė Žuklytė*/
public class NoteButton extends JButton{

    public NoteButton(String text){
        //210F60    3B4199       2A71B0       0696BB
        /**Custom buttons for the gui system
         * @param String*/
        super(text);
        //setOpaque(true);
        this.setBackground(new Color(0x2A71B0));
        this.setFont(new Font("Times New Roman", Font.BOLD,22));
        this.setFocusable(false);
        this.setForeground(new Color(0x210F60));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }
        


}