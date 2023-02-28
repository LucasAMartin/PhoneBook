import java.io.*;
import java.util.*;

public class PhoneBookDriver implements Serializable{

	// static global variable for my tree because I'm a savage
    public static BinarySearchTree<Person> tree = new BinarySearchTree<>();

	public static void main(String[] args) {
		// create the gui and create the file if it does not exist
		PhoneBookUI gui = new PhoneBookUI();
		createFile();
	}
	
	// method to write the tree to the file
	public static void writeTreeToFile(){
		// create a a file object using the existing file in the directory
        File file = new File("AddressBook.bin");
        // create an ObjectOutputStream and save the tree using serialization
        try (ObjectOutputStream TreeOutput = new ObjectOutputStream(new FileOutputStream(file))) {
        	TreeOutput.writeObject(tree);
            } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}
    public static void readTreeFromFile(){
		// create a a file object using the existing file in the directory
        File file = new File("AddressBook.bin");
        try (ObjectInputStream TreeInput = new ObjectInputStream(new FileInputStream(file))) {
        	// set the global tree equal to whatever tree is in the file
        	tree = (BinarySearchTree<Person>) TreeInput.readObject();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}    
    }
    
    public static void createFile() {
    	// create a file object
        File file = new File("AddressBook.bin");
        try {
        	// if the file doesnt already exist, create it 
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}		       
        // read the tree if it exists, otherwise don't. Because there is no tree.
		if (file.length() != 0)
        	readTreeFromFile();	
    }
    // method for adding someone to the phonebook
    public static void addContact(Person personI) {
        tree.add(personI);
        System.out.println(tree.size());
    }
    // method for removing someone from the phonebook
    public static void removeContact(Person personI) {
        tree.remove(personI);
    }
}
