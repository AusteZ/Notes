package GUI;
import Notes.*;
import java.util.*;
import javax.swing.*;
import GUI.*;
import java.awt.event.*;
import java.awt.*;
import Threads.*;
import javax.swing.border.*;
import java.awt.font.*;
import java.text.*;
/**@author Austė Žuklytė*/
public class TaskWindow extends JFrame implements ActionListener, MouseListener{
    //210F60    3B4199       2A71B0       0696BB
    Color bgColor = new Color(0x210F60);
    Border border = BorderFactory.createLineBorder(bgColor, 5);
    Font font = new Font("Times New Roman", Font.PLAIN, 12);
    JPanel notePanel = new JPanel();
    NoteButton createButton = new NoteButton("Create Task");
    NoteButton deleteButton = new NoteButton("Delete Task");
    NoteButton editButton = new NoteButton("Edit Task");
    NoteButton saveButton = new NoteButton("Save List");
    NoteButton calendarButton = new NoteButton("Calendar");
    NoteButton homeButton = new NoteButton("Home");
    LinkedList<JPanel> noteDisplays = new LinkedList<JPanel>();
    LinkedList<JCheckBox> noteChecks = new LinkedList<JCheckBox>();
    LinkedList<Note> notes = new LinkedList<Note>();
    int clicked = -1;

    public TaskWindow(){
        Load load = new Load("Files/tasks.ser");
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
        this.add(calendarButton);
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


        createButton.setBounds(40, 460, 200, 50);
        createButton.addActionListener(this);
        editButton.setBounds(40, 520, 200, 50);
        deleteButton.addActionListener(this);
        deleteButton.setBounds(40, 580, 200, 50);
        editButton.addActionListener(this);
        saveButton.setBounds(40, 320, 200, 100);
        saveButton.setFont(new Font("Times New Roman", Font.BOLD, 40));
        saveButton.addActionListener(this);
        calendarButton.setBounds(40, 180, 200, 100);
        calendarButton.setFont(new Font("Times New Roman", Font.BOLD, 40));
        calendarButton.addActionListener(this);
        homeButton.setBounds(40, 40, 200, 100);
        homeButton.setFont(new Font("Times New Roman", Font.BOLD, 40)); 
        homeButton.addActionListener(this);

        JLabel nameLabel = new JLabel();
        nameLabel.setText("Note name");
        nameLabel.setBounds(300,40,200,40);
        nameLabel.setForeground(new Color(0x2A71B0));
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 22)); 
        this.add(nameLabel);
        JLabel creationDate = new JLabel();
        creationDate.setText("Due date");
        creationDate.setBounds(770,40,200,40);
        creationDate.setForeground(new Color(0x2A71B0));
        creationDate.setFont(new Font("Times New Roman", Font.BOLD, 22)); 
        this.add(creationDate); 
    }
    private void displayNotes(LinkedList<Note> inputNotes){
        for(Note i:inputNotes){
            noteDisplays.add(custPanels((Task)i));
            notePanel.add(noteDisplays.getLast());
        }
    }
    private JPanel custPanels(Task noteTemp){
        JPanel temp = new JPanel();
        JCheckBox tempCheck = new JCheckBox();
        tempCheck.setSelected(noteTemp.getCompletion());
        tempCheck.setBackground(new Color(0x2A71B0));
        tempCheck.addActionListener(this);
        temp.add(tempCheck);
        noteChecks.add(tempCheck);

        JTable tempTable = new JTable(1, 2);//no trabaja fuera linea con esto
        tempTable.getColumnModel().getColumn(0).setPreferredWidth(380);
        temp.setBorder(border);
        tempTable.setValueAt(noteTemp.getName(), 0,0);
        //tempTable.setValueAt(noteTemp.getEdiDate(), 0,1);
        tempTable.setValueAt(noteTemp.getDate(), 0,1);
        temp.setBackground(new Color(0x2A71B0));
        tempTable.setEnabled(false);
        tempTable.setFont(font);
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
        int count = 0;
        for(JCheckBox i:noteChecks){
            ((Task)(notes.get(count))).setCompleted(i.isSelected());
            count++;
        }
        Save save = new Save(notes, "Files/tasks.ser");
        save.start();
    }
        
    public void actionPerformed(ActionEvent e) {
        /**Action on clicking a specific button*/
        if(e.getSource() == createButton){
            save();
            notes.add((Note)(new Task("Task", "About Task")));
            notes.lastIndexOf(notes.getLast());
            this.dispose();
            new TaskEditWindow(notes, notes.lastIndexOf(notes.getLast()));            
        }
        else if(e.getSource() == editButton){
            save();
            this.dispose();
            new TaskEditWindow(notes, clicked);
        }
        else if(e.getSource() == saveButton){
            save();
        }
        else if(e.getSource() == calendarButton){
            save();
            this.dispose();
            new CalendarWindow(notes);
        }
        else if(e.getSource() == deleteButton){
            if(clicked != -1){
                this.remove(notePanel);
                notePanel.remove(noteDisplays.get(clicked));
                noteDisplays.remove(clicked);
                noteChecks.remove(clicked);
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
