package GUI;
import Notes.*;
import java.util.*;
import javax.swing.*;
import GUI.*;
import java.awt.event.*;
import java.awt.*;
import Threads.*;
import javax.swing.border.*;
import java.time.*;
import java.text.*;

public class CalendarWindow extends JFrame implements ActionListener{
    Color bgColor = new Color(0x210F60);
    Border border = BorderFactory.createLineBorder(bgColor, 5);
    JPanel notePanel = new JPanel();
    NoteButton nextButton = new NoteButton("Next");
    NoteButton previousButton = new NoteButton("Previous");
    NoteButton noteListButton = new NoteButton("Task List");
    NoteButton homeButton = new NoteButton("Home");
    LinkedList<Note> notes = new LinkedList<Note>();
    JLabel monthName = new JLabel();
    int monthNr = 5;

    public CalendarWindow(LinkedList<Note> notes){
        this.notes = notes;
        initial();
        this.add(monthName);
        this.add(previousButton);
        this.add(nextButton);
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

        notePanel.setBounds(280,150,640,430);
        //notePanel.setLayout(new GridLayout(1,1));
        notePanel.setBackground(new Color(0x2A71B0));
        monthName.setForeground(new Color(0x2A71B0));
        monthName.setBounds(520,40,200,100);
        monthName.setFont(new Font("Times New Roman", Font.BOLD, 60));
        CalendarPanel(5);
        
        previousButton.setBounds(280,90,100,50);
        previousButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        previousButton.addActionListener(this);
        nextButton.setBounds(820,90,100,50);
        nextButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        nextButton.addActionListener(this);

        noteListButton.setBounds(40, 180, 200, 100);
        noteListButton.setFont(new Font("Times New Roman", Font.BOLD, 32));
        noteListButton.addActionListener(this);
        homeButton.setBounds(40, 40, 200, 100);
        homeButton.setFont(new Font("Times New Roman", Font.BOLD, 40)); 
        homeButton.addActionListener(this);
    }
    public void CalendarPanel(int month){
        this.remove(notePanel);
        monthNr = month;
        this.monthName.setText(""+Month.of(month));
        notePanel.removeAll();
        JTable calendarTable = new JTable(7,7);
        calendarTable.setRowHeight(0, 40);
        calendarTable.setBackground(new Color(0x2A71B0));
        for(int i = 0; i < 7; ++i){
            calendarTable.getColumnModel().getColumn(i).setPreferredWidth(90);
            calendarTable.setRowHeight(i, 60);
        }
        calendarTable.setRowHeight(0, 40);
        calendarTable.setValueAt("Monday", 0, 0);
        calendarTable.setValueAt("Tuesday", 0, 1);
        calendarTable.setValueAt("Wednesday", 0, 2);
        calendarTable.setValueAt("Thursday", 0, 3);
        calendarTable.setValueAt("Friday", 0, 4);
        calendarTable.setValueAt("Saturday", 0, 5);
        calendarTable.setValueAt("Sunday", 0, 6);
        calendarTable.setForeground(bgColor);
        calendarTable.setEnabled(false);
        LocalDate today = LocalDate.now();
        LocalDate day = LocalDate.of(today.getYear(), month, 1);
        DayOfWeek weekday = day.getDayOfWeek();
        int current = weekday.getValue();
        int countOfDays = 0;
        int u = 1;
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            countOfDays = 31;
        }
        else if(month == 4 || month == 6 || month == 9 || month == 11){
            countOfDays = 30;
        }
        else if(month == 2){
            countOfDays = 28;
        }
        calendarTable.setRowHeight(1, 60);
        for(int j = current-1; j < 7;  ++j){
            calendarTable.setValueAt(u, 1, j);
            ++u;
        }
        
        for(int i = 2; i < 7; ++i){
            for(int j = 0; j < 7;  ++j){
                calendarTable.setValueAt(u, i, j);
                if (u == countOfDays){
                    i = 7;
                    break;
                }
                ++u;
            }
        }
        addTasks(notes, month, calendarTable);
        notePanel.add(calendarTable);
        this.add(notePanel);
        this.revalidate();
        this.repaint();
    }
    public void addTasks(LinkedList<Note> tasks, int m, JTable calendarTable){
        int u = 0, check = 0;
        for(Note k : tasks){
            Task task = (Task)k;
            Date date;
            try{
                if(k.getName() != ""){
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(task.getDate());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    if (m == cal.get(Calendar.MONTH)+1 && LocalDate.now().getYear() == cal.get(Calendar.YEAR)){
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        for(int i = day/7+1; i < 7; ++i){
                            for(int j = 0; j < 7; ++j){
                                if(calendarTable.getValueAt(i,j) != null){
                                    check = 1;
                                    try{
                                        int r = (int)calendarTable.getModel().getValueAt(i,j);
                                        if(day == r && !(task.getCompletion())){
                                            calendarTable.setValueAt(day + " " + task.getName(),i,j);
                                            i = 7;
                                            break;
                                        }
                                    }
                                    catch(Exception e){}
                                }
                                else if(check == 1){
                                    //System.out.println("IDK");
                                    i = 7;
                                    break;
                                }
                            }
                        }
                    }
                    this.remove(calendarTable);
                    this.add(calendarTable);
                }
            }
            catch(Exception e){
                //System.out.println("Nope");
            }
            ++u;
            check = 0;
        }
    }
    public void actionPerformed(ActionEvent e) {
        /**Action on clicking a specific button*/
        if(e.getSource() == homeButton){
            this.dispose();
            new HomeWindow();
        }
        else if(e.getSource() == noteListButton){
            this.dispose();
            new TaskWindow();
        }
        else if(e.getSource() == nextButton){
            CalendarPanel(++monthNr);
        }
        else if(e.getSource() == previousButton){
            CalendarPanel(--monthNr);
        }
    }
}
