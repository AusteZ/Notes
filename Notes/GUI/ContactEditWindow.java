package GUI;
import Notes.*;
import java.util.*;
import javax.swing.*;
import GUI.*;
import java.awt.event.*;
import java.awt.*;
import Threads.*;
import javax.swing.border.*;

public class ContactEditWindow extends JFrame implements ActionListener{
    Color bgColor = new Color(0x210F60);
    Border border = BorderFactory.createLineBorder(bgColor, 5);
    JPanel notePanel = new JPanel();
    NoteButton saveButton = new NoteButton("Save Contact");
    NoteButton deleteButton = new NoteButton("Delete Contact");
    NoteButton noteListButton = new NoteButton("Contact List");
    NoteButton homeButton = new NoteButton("Home");
    LinkedList<Note> notes = new LinkedList<Note>();
    JTextArea noteEdit = new JTextArea();
    JTextArea nameField = new JTextArea();
    JTextArea numberField = new JTextArea();
    int u;

    public ContactEditWindow(LinkedList<Note> notes, int u){
        this.notes = notes;
        this.u = u;
        noteEdit.setText(notes.get(u).getNote());
        nameField.setText(notes.get(u).getName());
        numberField.setText(((Contact)(notes.get(u))).getPhoneNumber());
        initial();
        this.add(notePanel);
        this.add(saveButton);
        //this.add(deleteButton);
        this.add(noteListButton);
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

        notePanel.setBounds(280,130,640,450);
        notePanel.setLayout(new GridLayout(1,1));
        notePanel.setBackground(new Color(0xFFFFFF));

        noteEdit.setLineWrap(true);
        noteEdit.setWrapStyleWord(true);
        noteEdit.setFont(new Font("Times New Roman", Font.BOLD, 16));
        noteEdit.setForeground(bgColor);
        noteEdit.setBackground(new Color(0x2A71B0));
        notePanel.add(noteEdit);


        saveButton.setBounds(40, 320, 200, 50);
        saveButton.addActionListener(this);
        deleteButton.setBounds(40, 380, 200, 50);
        deleteButton.addActionListener(this);
        noteListButton.setBounds(40, 180, 200, 100);
        noteListButton.setFont(new Font("Times New Roman", Font.BOLD, 32));
        noteListButton.addActionListener(this);
        homeButton.setBounds(40, 40, 200, 100);
        homeButton.setFont(new Font("Times New Roman", Font.BOLD, 40)); 
        homeButton.addActionListener(this);

        nameField.setBounds(300,30,400,40);
        nameField.setForeground(new Color(0x2A71B0));
        nameField.setBackground(new Color(0x3B4199));
        nameField.setFont(new Font("Times New Roman", Font.BOLD, 30));
        numberField.setBounds(300,80,400,40);
        numberField.setForeground(new Color(0x2A71B0));
        numberField.setBackground(new Color(0x3B4199));
        numberField.setFont(new Font("Times New Roman", Font.BOLD, 30)); 
        this.add(nameField);
        this.add(numberField);
    }
    private void save(){
        /**Saves the tasks into the file*/
        notes.get(u).modify(noteEdit.getText());
        notes.get(u).modifyName(nameField.getText());
        ((Contact)(notes.get(u))).modifyNumber(numberField.getText());
        Save save = new Save(notes, "Files/contacts.ser");
        save.start();
    }
    public void actionPerformed(ActionEvent e) {
        /**Action on clicking a specific button*/
        if(e.getSource() == saveButton){
            save();
        }
        else if(e.getSource() == homeButton){
            save();
            this.dispose();
            new HomeWindow();
        }
        else if(e.getSource() == noteListButton){
            save();
            this.dispose();
            new ContactWindow();
        }
        else if(e.getSource() == deleteButton){
            notes.remove(u);
            save();
            this.dispose();
            new ContactWindow();
        }
    }
}
