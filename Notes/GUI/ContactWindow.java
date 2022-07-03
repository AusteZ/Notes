package GUI;
import Notes.*;
import java.util.*;
import javax.swing.*;
import GUI.*;
import java.awt.event.*;
import java.awt.*;
import Threads.*;
import javax.swing.border.*;

/**@author Austė Žuklytė*/
public class ContactWindow extends JFrame implements ActionListener, MouseListener{
    //210F60    3B4199       2A71B0       0696BB
    Color bgColor = new Color(0x210F60);
    Border border = BorderFactory.createLineBorder(bgColor, 5);
    JPanel notePanel = new JPanel();
    NoteButton createButton = new NoteButton("Create Contact");
    NoteButton deleteButton = new NoteButton("Delete Contact");
    NoteButton editButton = new NoteButton("Edit Contact");
    NoteButton saveButton = new NoteButton("Save List");
    NoteButton homeButton = new NoteButton("Home");
    LinkedList<JPanel> noteDisplays = new LinkedList<JPanel>();
    LinkedList<Note> notes = new LinkedList<Note>();
    int clicked = -1;

    public ContactWindow(){
        Load load = new Load("Files/contacts.ser");
        try{
            load.start();
            load.join();
            notes = load.getLinkedList();
        }
        catch(Exception e){}
        initial();
        displayNotes(notes);
        this.add(notePanel);
        this.add(createButton);
        this.add(deleteButton);
        this.add(editButton);
        this.add(saveButton);
        this.add(homeButton);
        this.setVisible(true);
    }

    private void initial(){
        /**Creates the initial look of the window that is unchanging no matter how many things are added*/
        this.setTitle("Notes");
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000,680);
        this.getContentPane().setBackground(bgColor);

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
        editionLabel.setText("Number");
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
            noteDisplays.add(custPanels((Contact)i));
            notePanel.add(noteDisplays.getLast());
        }
    }
    private JPanel custPanels(Contact noteTemp){
        JPanel temp = new JPanel();
        JTable tempTable = new JTable(1, 3);//no trabaja fuera linea con esto
        tempTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        temp.setBorder(border);
        tempTable.setValueAt(noteTemp.getName(), 0,0);
        tempTable.setValueAt(noteTemp.getPhoneNumber(), 0,1);
        tempTable.setValueAt(noteTemp.getCreDate(), 0,2);
        temp.setBackground(new Color(0x2A71B0));
        tempTable.setEnabled(false);
        tempTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        tempTable.setBackground(new Color(0x2A71B0));
        tempTable.setForeground(bgColor);
        temp.add(tempTable);
        tempTable.setShowGrid(false);
        temp.setLayout(new BoxLayout(temp, BoxLayout.X_AXIS));
        temp.addMouseListener(this);
        return temp;

    }
    private void save(){
        Save save = new Save(notes, "Files/contacts.ser");
        save.start();
    }
        
    public void actionPerformed(ActionEvent e) {
        /**Action on clicking a specific button*/
        if(e.getSource() == createButton){

            notes.add((Note)(new Contact("Name", "Phone Number", "About Person")));
            notes.lastIndexOf(notes.getLast());
            this.dispose();
            new ContactEditWindow(notes, notes.lastIndexOf(notes.getLast()));            
        }
        else if(e.getSource() == editButton){
            this.dispose();
            new ContactEditWindow(notes, clicked);
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
            if(e.getSource() == i){
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
