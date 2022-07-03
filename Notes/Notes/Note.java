package Notes;
import Notes.*;
import java.time.LocalDate;
import java.io.Serializable;
import java.text.*;
import java.lang.*;

/**@author Austė Žuklytė*/
public class Note extends NoteAbstract implements Cloneable, Serializable{
    public final LocalDate creationDate;
    private String noteName;

    /**A class to create a note.*/
    public Note(String name, String text){
        /**Constructor of note
         * @param String*/
        this();
        this.noteName = name;
        this.description = text;
    }
    private Note(){
        /**Constructor of note, sets creation and edition dates*/
        this.creationDate = LocalDate.now();
        this.editionDate = LocalDate.now();
    }
    public String getName(){
        return noteName;
    }
    public void modifyName(String text){
        noteName = text;
        this.editionDate = LocalDate.now();
    }
    public String getNote(){
        /**Gets the text of note
         * @return String*/
        return this.description;
    }
    public String getEdiDate(){
        /**Return the do date of the task
         * @return String*/
        return "" + editionDate;
    }
    public String getCreDate(){
        /**Return the do date of the task
         * @return String*/
        return "" + creationDate;
    }
    public String toString(){
        /**Puts all fields in one string
         * @return String*/
        return (noteName + "\n" + description + "\n" + editionDate + "\n" + creationDate + "\n");
    }
    public Object clone(){
        /**Klonuoja Note
         * @return Object*/
        Note cloneNote = null;
        try {
            cloneNote = (Note)super.clone();
            //cloneNote.noteFileInput = (FileInput) this.noteFileInput.clone();
            return cloneNote;
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning error occured");
        }
        return null;
        
    }
}