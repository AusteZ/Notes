package Notes;
import java.time.LocalDate;
import Interfaces.Appendable;
import java.io.Serializable;


/**@author Austė Žuklytė*/

public abstract class NoteAbstract implements Appendable, Serializable{
    /**An abstract class for the note class*/
    protected String description;
    protected LocalDate editionDate;
    public void modify(String text){
        /**Modify the note text
         * @param String*/
        this.description = text + "\n";
        editionDate = LocalDate.now();
    }
    public void append(String text){
        /**Modify the note text
         * @param String*/
        this.description += (text + "\n");
        editionDate = LocalDate.now();
    }
}