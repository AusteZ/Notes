package Notes;
import java.time.LocalDate;
import Notes.Note;
import java.io.Serializable;

/**@author Austė Žuklytė*/
public class Contact extends Note implements Serializable{
    private String phoneNumber;
    private String contactName;
    /**Createss a contact by extending Note*/

    public Contact(String nameInput, String phoneNumberInput, String descriptionInput){
        /**Contact constructor
         * @param String
         * @param String
         * @param String*/
        super(nameInput, descriptionInput);
        this.phoneNumber = phoneNumberInput;
    }
    public void modifyNumber(String phoneNumberInput){
        /**Change number of contact
         * @param String*/
        this.phoneNumber = phoneNumberInput;
        this.editionDate = LocalDate.now();
    }
    public String getPhoneNumber(){
        /**Get name of contact
         * @return String*/
        return phoneNumber;
    }


    public String toString(){
        /**@Override puts all contact fields into one string
         * @return String
         */
        return (getName() + "\n" + super.getNote() + "\n" + this.phoneNumber + "\n\n" + this.editionDate + "\n" + this.creationDate);
    }
}