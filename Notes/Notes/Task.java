package Notes;
import java.time.LocalDate;
import Notes.Note;
import java.util.*;
import java.text.*;
import java.io.Serializable;

public class Task extends Note implements Serializable{
    boolean completed = false;
    protected Date date = null;

    public Task(String name, String text){
        super(name, text);
    }
    public boolean getCompletion(){
        return completed;
    }
    public void setCompleted(boolean state){
        completed = state;
    }
    public String getDate(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String a = (date != null) ? format.format(date) : "";
        return a;
    }
    public void setDate(String state){
        try{
            date = new SimpleDateFormat("yyyy-MM-dd").parse(state);
        }
        catch(Exception e){
            date = null;
            System.out.println("NO");
        }
        
    }
    
    public String toString(){
        //changeListtoDescription();
        return super.toString();
    }
}