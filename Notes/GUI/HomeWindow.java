package GUI;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class HomeWindow extends JFrame implements ActionListener{
    //210F60    3B4199       2A71B0       0696BB
    JButton noteButton = StyleSheet.buttonStyle("Notes");
    JButton contactButton = StyleSheet.buttonStyle("Contacts");
    JButton taskButton = StyleSheet.buttonStyle("Tasks & Calendar");
    
    public HomeWindow(){
        styling();
        this.add(taskButton);
        this.add(contactButton);
        this.add(noteButton);
    }
    private void styling(){
        StyleSheet.frameStyle(this);

        Font biggerFont = new Font("Times New Roman", Font.BOLD, 40);

        noteButton.setBounds(320, 100, 360, 100);
        noteButton.setFont(biggerFont);
        noteButton.addActionListener(this);

        contactButton.setBounds(320, 250, 360, 100);
        contactButton.setFont(biggerFont);
        contactButton.addActionListener(this);

        taskButton.setBounds(320, 400, 360, 100);
        taskButton.setFont(biggerFont); 
        taskButton.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == noteButton){
            this.dispose();
            new NoteWindow();
        }
        else if(e.getSource() == taskButton){
            this.dispose();
            new TaskWindow();
        }
        else if(e.getSource() == contactButton){
            this.dispose();
            new ContactWindow();
        }
    }
}
