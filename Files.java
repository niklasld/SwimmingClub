import java.util.*;
import java.io.*;

public class Files {
   Scanner scan;  
   
   //General methods
   public void createFile(String input) {
      final Formatter fileCreate;
      File fileName = new File("txt//"+input);
      
      if(!fileName.exists()) {
         try {
            fileCreate = new Formatter("txt//"+input);
         }
         catch(Exception e) {
            System.out.println("Error creating file " + fileName + " (Files.java createFile method)");
         }
      }
   }
   
   public void openFile(String fileName) {
      try {
         File name = new File("txt//"+fileName);
         scan = new Scanner(name);
      }
      catch (Exception e) {
         System.out.println("Error opening file: "+fileName+" Files.java (Method: openFile)");
      }
   
   }
   
   public void closeFile(){
      scan.close();
   }
   
   public int scanInt(String message) {
      scan = new Scanner(System.in);
      int input = -1;
      
      while(input == -1) {
         System.out.println(message);
         if(scan.hasNextInt()) {
            input = scan.nextInt();
         }
         else {
            System.out.println("Error input wasnt a single number");
         }
      }
      
      return input;
   }
   
   public String scanString(String message) {
      scan = new Scanner(System.in);
      System.out.print(message);
      String input = scan.next();
      return input;
   }
   
   public void addToFile(String fileName, String input1, String input2, String input3, String input4) {
      try{
         FileWriter fileW = new FileWriter("txt//"+fileName, true);
         BufferedWriter buffW = new BufferedWriter(fileW);
         buffW.write(input1+" "+input2+" "+input3+" "+input4+"\n");
         buffW.close();
      }
      catch(Exception e){
         System.out.println("Error writing to "+fileName+" (Files.java method: addToFile)");
      }
   }
   
   public void clearFile(String fileName){
      try{
         FileWriter fileW = new FileWriter("txt//"+fileName);
         BufferedWriter buffW = new BufferedWriter(fileW);
         buffW.write("");
         buffW.close();
      }
      catch (Exception e) {
         System.out.println("Error clearing file "+fileName+" Files.java method: clearFile");
      }
   }
   
   //User specific methods
   public void readUsers(ArrayList<User> users) {
      int counter = 0;
      while(scan.hasNext()){
         
         int id = scan.nextInt();
         int pin = scan.nextInt();
         String firstName = scan.next();
         String lastName = scan.next();
         users.add(new User(id,pin,firstName,lastName)); 
         counter++;
      }
   }
   
   public void addUserFromInput(ArrayList<User> users) {
      // might need to be redone, with membership extension
      String first, last;
      int id, pin;
      
      first = scanString("Enter first name: ");
      last = scanString("Enter last name: ");
      
      //need method to calculate id.
      id = 1;
      pin = scanInt("Enter pin code: ");
      
      users.add(new User(id,pin,first,last)); 
      clearFile("Users.txt");
      addUsersToFile(users);
   }
   
   public void addUsersToFile(ArrayList<User> users) {
      String input1, input2, input3, input4;
      
      for(int i = 0; i<users.size();i++) {
         input1 = ""+users.get(i).getId();
         input2 = ""+users.get(i).getPin();
         input3 = users.get(i).getFirstName();
         input4 = users.get(i).getLastName();
         
         addToFile("Users.txt", input1, input2, input3, input4);
      }
   }
 
}