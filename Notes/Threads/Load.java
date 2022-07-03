package Threads;

import Notes.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.io.EOFException;

/**@author Austė Žuklytė*/
public class Load extends Thread {
	
	LinkedList<Note> loadLinkedList = new LinkedList<Note>();
	String fileName;
	int objectCount = 0;
	
	public Load(String fileName){
		this.fileName = fileName;
	}
	
	public LinkedList<Note> getLinkedList() {
		/**Return a list of notes scanned
		 * @return LinkedList*/
		return loadLinkedList;
	}
	
	public void run() {
		/**@Override to run in a new thread, load notes from a binary file*/
		try {
			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(file);
			loadLinkedList.clear();
			try{
				while(true){
					try {
						loadLinkedList.add((Note)in.readObject());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			catch(EOFException e){}
				
			file.close();
			in.close();
		} catch(FileNotFoundException e) {
			System.out.println("Cant find file! :(");
		}
		catch(IOException e) {
			System.out.println("Cant find file! :(");
			e.printStackTrace();
		}
	}
	
}