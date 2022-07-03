package GUI;
import Notes.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import Threads.*;
import javax.swing.border.*;

/**@author Austė Žuklytė*/
public class NoteWindow extends JFrame implements ActionListener, MouseListener{
    //210F60    3B4199       2A71B0       0696BB
    Color bgColor = new Color(0x210F60);
    Border border = BorderFactory.createLineBorder(bgColor, 5);

    JPanel notePanel = new JPanel();
    JButton createButton = StyleSheet.buttonStyle("Create Note");
    JButton deleteButton = StyleSheet.buttonStyle("Delete Note");
    JButton editButton = StyleSheet.buttonStyle("Edit Note");
    JButton saveButton = StyleSheet.buttonStyle("Save List");
    JButton homeButton = StyleSheet.buttonStyle("Home");

    LinkedList<JPanel> noteDisplays = new LinkedList<JPanel>();
    LinkedList<Note> notes = new LinkedList<Note>();
    LinkedList<JTable> tables = new LinkedList<JTable>();
    int clicked = -1;

    public NoteWindow(){
        Load load = new Load("Files/notes.ser");
        try{
            load.start();
            load.join();
            notes = load.getLinkedList();
        }
        catch(Exception e){
            System.out.println("Files couldn't be read");
            this.dispose();
        }
        styling();
        displayNotes(notes);
        this.add(notePanel);
        this.add(createButton);
        this.add(deleteButton);
        this.add(editButton);
        this.add(saveButton);
        this.add(homeButton);
        this.setVisible(true);
    }

    private void styling(){
        StyleSheet.frameStyle(this);

        notePanel.setBounds(280,80,640,400);
        notePanel.setLayout(new GridLayout(10,1));
        notePanel.setBackground(bgColor);

        createButton.setBounds(40, 320, 200, 50);
        createButton.addActionListener(this);

        deleteButton.setBounds(40, 440, 200, 50);
        deleteButton.addActionListener(this);

        editButton.setBounds(40, 380, 200, 50);
        editButton.addActionListener(this);

        saveButton.setBounds(40, 180, 200, 100);
        saveButton.setFont(new Font("Times New Roman", Font.BOLD, 40));
        saveButton.addActionListener(this);

        homeButton.setBounds(40, 40, 200, 100);
        homeButton.setFont(new Font("Times New Roman", Font.BOLD, 40)); 
        homeButton.addActionListener(this);

        JLabel nameLabel = new JLabel();
        nameLabel.setText("Note name");
        nameLabel.setBounds(300,40,200,40);
        nameLabel.setForeground(new Color(0x2A71B0));
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 22)); 
        this.add(nameLabel);

        JLabel editionLabel = new JLabel();
        editionLabel.setText("Edition date");
        editionLabel.setBounds(620,40,200,40);
        editionLabel.setForeground(new Color(0x2A71B0));
        editionLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
        this.add(editionLabel);

        JLabel creationDate = new JLabel();
        creationDate.setText("Creation date");
        creationDate.setBounds(770,40,200,40);
        creationDate.setForeground(new Color(0x2A71B0));
        creationDate.setFont(new Font("Times New Roman", Font.BOLD, 22)); 
        this.add(creationDate); 
    }
    private void displayNotes(LinkedList<Note> inputNotes){
        for(Note i:inputNotes){
            noteDisplays.add(customPanels(i));
            notePanel.add(noteDisplays.getLast());
        }
    }
    private JPanel customPanels(Note noteTemp){
        JPanel temp = new JPanel();
        JTable tempTable = new JTable(1, 3);
        tempTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        temp.setBorder(border);
        tempTable.setValueAt(noteTemp.getName(), 0,0);
        tempTable.setValueAt(noteTemp.getEdiDate(), 0,1);
        tempTable.setValueAt(noteTemp.getCreDate(), 0,2);
        temp.setBackground(new Color(0x2A71B0));
        tempTable.setEnabled(false);
        tempTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        tempTable.setBackground(new Color(0x2A71B0));
        tempTable.setForeground(bgColor);
        tempTable.setShowGrid(false);
        tempTable.addMouseListener(this);
        tables.add(tempTable);

        tempTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                
            }
        });



        temp.add(tempTable);
        temp.setLayout(new BoxLayout(temp, BoxLayout.X_AXIS));
        temp.addMouseListener(this);
        return temp;

    }
    private void save(){
        Save save = new Save(notes, "Files/notes.ser");
        save.start();
    }
        
    public void actionPerformed(ActionEvent e) {
        /**Action on clicking a specific button*/
        if(e.getSource() == createButton){

            notes.add(new Note("Title", "Text"));
            notes.lastIndexOf(notes.getLast());
            this.dispose();
            new NoteEditWindow(notes, notes.lastIndexOf(notes.getLast()));            
        }
        else if(e.getSource() == editButton){
            this.dispose();
            new NoteEditWindow(notes, clicked);
        }
        else if(e.getSource() == deleteButton){
            if(clicked != -1){
                this.remove(notePanel);
                notePanel.remove(noteDisplays.get(clicked));
                noteDisplays.remove(clicked);
                notes.remove(clicked);
                this.add(notePanel);
                this.revalidate();
                this.repaint();
            }
            clicked = -1;
        }
        else if(e.getSource() == homeButton){
            save();
            this.dispose();
            new HomeWindow();
        }
    }
    public void mouseClicked(MouseEvent e){
        mousePressed(e);
    }

    public void mousePressed(MouseEvent e){
        /**Implementation of MouseListener, to select specific notes*/
        if(clicked != -1){
            noteDisplays.get(clicked).setBorder(border);
        }
        clicked = -1;
        int  u = 0;
        for(JPanel i:noteDisplays){
            if(e.getSource() == i || e.getSource() == tables.get(u)){
                Border clickedBorder = BorderFactory.createLineBorder(new Color(0x99CCFF), 5);
                i.setBorder(clickedBorder);
                clicked = u;
                break;
            }
            ++u;
        }
    }
    public void mouseReleased(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseExited(MouseEvent e){}
    
}
