package GUI;
import Notes.*;
import java.util.*;
import javax.swing.*;
import GUI.*;
import java.awt.event.*;
import java.awt.*;
import Threads.*;
import javax.swing.border.*;
import java.text.*;
import java.time.*;
import java.time.LocalDate;



public class CalendarPanel extends JPanel{
    Color bgColor = new Color(0,49,83);
    JTable dayTable = new JTable(7,7);
    Border noteTextBorder = BorderFactory.createLineBorder(bgColor, 5);
    LinkedList<JTextField> dateTexts = new LinkedList<JTextField>();
    LinkedList<JPanel> taskPanels = new LinkedList<JPanel>();
    int current = 0;
    public CalendarPanel(){
        this.setBounds(580, 40, 360, 280);
        this.setBackground(new Color(0x668398));
        this.add(new JLabel("May"));
        dayTable.setPreferredSize(new Dimension(360, 300));
        dayTable.setBackground(new Color(0x668398));
        LocalDate today = LocalDate.now();
        LocalDate day = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
        DayOfWeek weekday = day.getDayOfWeek();
        YearMonth yearMonthObject = YearMonth.of(today.getYear(), today.getMonthValue());  
        current = weekday.getValue();
        dayTable.setValueAt(1,1, current-1);
        int u = 1;
        dayTable.setRowHeight(1, 40);
        dayTable.setValueAt("Monday", 0, 0);
        dayTable.setValueAt("Tuesday", 0, 1);
        dayTable.setValueAt("Wednesday", 0, 2);
        dayTable.setValueAt("Thursday", 0, 3);
        dayTable.setValueAt("Friday", 0, 4);
        dayTable.setValueAt("Saturday", 0, 5);
        dayTable.setValueAt("Sunday", 0, 6);
        for(int j = current-1; j < 7;  ++j){
            dayTable.setValueAt(u, 1, j);
            ++u;
        }
        
        for(int i = 2; i < 7; ++i){
            for(int j = 0; j < 7;  ++j){
                dayTable.setRowHeight(i, 40);
                dayTable.setValueAt(u, i, j);
                if (u == yearMonthObject.lengthOfMonth()){
                    i = 7;
                    break;
                }
                ++u;
            }
        }
        this.add(dayTable);
    }
    public void addTasks(LinkedList<JTextField> dates, LinkedList<JTextField> tasks){
        int u = 0, check = 0;
        for(JTextField k : tasks){
            Date date;
            try{
                if(dates.get(u).getText() != ""){
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(dates.get(u).getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    if (LocalDate.now().getMonthValue() == cal.get(Calendar.MONTH)+1 && LocalDate.now().getYear() == cal.get(Calendar.YEAR)){
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        for(int i = day/7+1; i < 7; ++i){
                            for(int j = 0; j < 7; ++j){
                                if(dayTable.getValueAt(i,j) != null){
                                    check = 1;
                                    try{
                                        int r = (int)dayTable.getModel().getValueAt(i,j);
                                        if(day == r){
                                            dayTable.setValueAt(day + " " + k.getText(),i,j);
                                            i = 7;
                                            break;
                                        }
                                    }
                                    catch(Exception e){}
                                }
                                else if(check == 1){
                                    System.out.println("IDK");
                                    i = 7;
                                    break;
                                }
                            }
                        }
                    }
                    this.remove(dayTable);
                    this.add(dayTable);
                }
            }
            catch(Exception e){
                System.out.println("Nope");
            }
            ++u;
            check = 0;
        }
    }
}