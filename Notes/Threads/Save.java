package Threads;

import Notes.*;
import java.io.*;
import java.util.*;

/**@author Austė Žuklytė*/
public class Save extends Thread {
	LinkedList<Note> saveLinkedList = new LinkedList<Note>();
	String fileName = "noteSaved.ser";
	
	public Save(LinkedList<Note> temporaryLinkedList, String fileName) {
		this.fileName = fileName;
		this.saveLinkedList = temporaryLinkedList;
	}
	
	public void serArr(LinkedList<Note> temporaryLinkedList) {
		/**Change the linked list saved into the class
		 * @param LinkedList*/
		saveLinkedList.clear();
		this.saveLinkedList.addAll(temporaryLinkedList);
	}
	
	public void run() {
		/**@Override to run in a new thread, save notes into a binary file*/
		try {
			FileOutputStream outFile = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(outFile);
			
			for(Note i:saveLinkedList){
                out.writeObject(i);
            }
			
			outFile.close();
			out.close();
			
		}catch(FileNotFoundException e) {
			System.out.println("File not found! :(");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}