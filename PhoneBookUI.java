import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class PhoneBookUI extends JFrame implements Serializable{
	
	   // fields for all the buttons and stuff
	   private JPanel panel;
	   private JButton addContact;
	   private JButton removeContact;
	   private JButton modifyContact;
	   private JButton contactInfo;
	   private JButton printBook;
	   private JButton resetEntries;
	   private JButton saveBook;
	   private JButton loadBook;
	   private JLabel name;
	   private JLabel address;
	   private JLabel phone;
	   private JTextField nameText;
	   private JTextField addressText;
	   private JTextField phoneText;
	   private JTextArea display;
	   private final int WINDOW_WIDTH = 600;
	   private final int WINDOW_HEIGHT = 500; 
	    
	   // set the components
	   public PhoneBookUI() {
	      setTitle("Phone Book");
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      buildPanel();
	      add(panel);
	      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	      setVisible(true);
	   }

	   // build the buttons, set action listeners on all the buttons
	   private void buildPanel() {
	      addContact = new JButton("Add Contact");
	      addContact.addActionListener(new AddContactListener());
	      removeContact = new JButton("Remove Contact");
	      removeContact.addActionListener(new RemoveContactListener());
	      modifyContact = new JButton("Modify Contact");
	      modifyContact.addActionListener(new ModifyContactListener());
	      contactInfo = new JButton("Contact Info");
	      contactInfo.addActionListener(new ContactInfoListener());
	      printBook = new JButton("Print Entire Phonebook");
	      printBook.addActionListener(new DisplayContactListener());
	      resetEntries = new JButton("Reset Entries");
	      resetEntries.addActionListener(new ResetContactListener());
	      saveBook = new JButton("Save Phonebook to File");
	      saveBook.addActionListener(new SaveContactListener());
	      loadBook = new JButton("Load Phonebook from File");
	      loadBook.addActionListener(new LoadContactListener());
	      // create the labels and text fields where information will be entered
	      name = new JLabel("Name"); 
	      address = new JLabel("Address"); 
	      phone = new JLabel("Phone Number"); 
	      nameText = new JTextField(10); 
	      addressText = new JTextField(10); 
	      phoneText = new JTextField(10); 
	      // the display that will show the contacts with a scrollbar
	      display = new JTextArea (15,60); 
	      display.setEditable (false); 
	      JScrollPane scroll = new JScrollPane ( display ); 
	      scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
	      
	      // add everything to the panel
	      panel = new JPanel();
	      panel.add(name);
	      panel.add(nameText);
	      panel.add(phone);
	      panel.add(phoneText);
	      panel.add(address);
	      panel.add(addressText);
	      panel.add(scroll); 
	      panel.add(addContact);
	      panel.add(removeContact);
	      panel.add(modifyContact);
	      panel.add(contactInfo);
	      panel.add(printBook);
	      panel.add(resetEntries);
	      panel.add(saveBook);
	      panel.add(loadBook);
	   }

	   // the action listener for adding contact button
	   private class AddContactListener implements ActionListener {
	   
	      public void actionPerformed(ActionEvent e) {
	         
	    	  // get the text from the user input in the name textfield 
	         String userName = nameText.getText();
	         // if the user put nothing display a message to help them
	         if (userName.isEmpty()) {
		         display.append("Type information to add to phone book\n");
		         return;
	         }
	         // get the user inputs from the other text fields
	         String userAddress = addressText.getText();
	         String userPhone = phoneText.getText();
	         // create a person and add them to the tree 
	         Person personTemp = new Person(userName, userAddress, userPhone);
	         PhoneBookDriver.addContact(personTemp);

	         // reset the fields to blank between actions
	         nameText.setText("");
	         phoneText.setText("");
	         addressText.setText("");
			 // feedback for the user
	         display.append(userName + " added to phone book\n");
	      }
	   }
	   private class RemoveContactListener implements ActionListener {
		   
		      public void actionPerformed(ActionEvent e) {
		         
		    	  // get the name to remove from user input
		         String userName = nameText.getText();
		         Person personTemp = new Person(userName);
		         // if the person exists, remove them and give the user feedback
		         if (PhoneBookDriver.tree.contains(personTemp)){
		        	 PhoneBookDriver.removeContact(personTemp);
			         nameText.setText("");
			         phoneText.setText("");
			         addressText.setText("");
		        	 display.append("Contact successfully removed\n"); 
		         }
		         // if the person does not exist display a message
		         else
		        	 display.append("Contact not found\n");

		      }
		}
	   private class ModifyContactListener implements ActionListener {
		   
		      public void actionPerformed(ActionEvent e) {
		         
		    	  // if the name field is empty, tell the user to put in the info to change
		    	 if (nameText.getText().isEmpty()){
			         display.append("Enter name to be modified, enter the new information, then press modify contact\n");
			         return;
		    	 }
		    	 // iterator for tree navigation
		         Iterator<Person> itr = PhoneBookDriver.tree.iterator();
		         // person name to change and new information for address and phone num
		         String userName = nameText.getText();
		         String userPhone = phoneText.getText();
		         String userAddress = addressText.getText();
		         // iterate through, keeping track of the last person the iterator iterates over
		         Person previous = null;
		         if (itr.hasNext()) {
		             previous = itr.next();
		         }
		         // for each person check if the name matches the name to be changed
		          while (itr.hasNext()){
		        	  // if it does, change the info and display a success message
		        	  if (previous.getName().equals(userName)) {
		        		  previous.setAddress(userAddress);
		        		  previous.setPhoneNum(userPhone);
		     	          nameText.setText("");
		    	          phoneText.setText("");
		    	          addressText.setText("");
					      display.append("Contact information successfully modified\n");
		        		  return;
		        	  }
		        	  previous = itr.next();
		          }	
		          // if the person is not found display message
		          display.append("Contact information not found\n");
		          return;
		      }
		}
	   private class SaveContactListener implements ActionListener {
		   
		      public void actionPerformed(ActionEvent e) {
		         
		    	  // call method to save the file and display success message
		    	  PhoneBookDriver.writeTreeToFile();
	        	  display.append("Phone book successfully saved\n");

		      }
		}
	   private class LoadContactListener implements ActionListener {
		   
		      public void actionPerformed(ActionEvent e) {
		         
		    	  // call method to load the file and display success message
		          File file = new File("AddressBook.bin");
		          if (file.length() != 0) {
		        	PhoneBookDriver.readTreeFromFile();
	        	  	display.append("Phone book successfully loaded\n");
	        	  	return;
		          }
		          display.append("Phone book contains no information\n");
		      }
		}
	   private class DisplayContactListener implements ActionListener {
		   
		      public void actionPerformed(ActionEvent e) {
		         

		          Iterator<Person> itr = PhoneBookDriver.tree.iterator();

		          // if the tree has no people display message saying so 
		          if (!itr.hasNext()){
		        	  display.append("No contact information entered\n");
		          }
		          // otherwise display tostring method for each person
		          while (itr.hasNext()){
		        	  display.append(itr.next().toString());
		          }		         
		      }
		}
	   private class ContactInfoListener implements ActionListener {
		   
		      public void actionPerformed(ActionEvent e) {
		         
		    	  Iterator<Person> itr = PhoneBookDriver.tree.iterator();
			         // person to search for
			         String userName = nameText.getText();
			         // iterate through, keeping track of the last person the iterator iterates over
			         Person previous = null;
			         if (itr.hasNext()) {
			             previous = itr.next();
			         }
			         // for each person check if the name matches the name searched for
			          while (itr.hasNext()){
			        	  // if it does, display the info and
			        	  if (previous.getName().equals(userName)) {
						      display.append(previous.toString());
			        		  return;
			        	  }
			        	  previous = itr.next();
			          }	
			          // if the person is not found display message
			          display.append("Contact information not found\n");
			          return;
			      }
		}
	   private class ResetContactListener implements ActionListener {
		   
		      public void actionPerformed(ActionEvent e) {
		         
		    	  // iterator
		          Iterator<Person> itr = PhoneBookDriver.tree.iterator();

		          // iterate through the tree and remove each person
		          while (itr.hasNext()){
		        	  PhoneBookDriver.tree.remove(itr.next());
		          }	
		          display.append("Phonebook successfully reset\n");
		      }
		}  	   
}
	   