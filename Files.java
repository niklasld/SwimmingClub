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
   
   public double scanDouble(String message) {
      scan = new Scanner(System.in);
      double input = -1;
      
      while(input == -1) {
         System.out.println(message);
         if(scan.hasNextDouble()) {
            input = scan.nextDouble();
         }
         else {
            System.out.println("Error input wasnt a double number");
         }
      }
      
      return input;
   }
   
   public boolean scanBoolean(String message) {
      scan = new Scanner(System.in);
      boolean input = false;
      System.out.println(message);
      if(scan.hasNextBoolean()) {
         input = scan.nextBoolean();
      }
      else {
         System.out.println("Error input wasnt a boolean");
      }
   
      return input;
   }
   
   public String scanString(String message) {
      scan = new Scanner(System.in);
      System.out.print(message);
      String input = scan.nextLine();
      return input;
   }
   
   public void addToFile(String fileName, String input1, String input2, String input3, String input4, String input5, String input6, String input7, String input8, String input9) {
      try{
         FileWriter fileW = new FileWriter("txt//"+fileName, true);
         BufferedWriter buffW = new BufferedWriter(fileW);
         buffW.write(input1+" "+input2+" "+input3+" "+input4+" "+ input5+" " +input6+" "+input7+" "+input8+" "+input9+ "\n");
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
         String firstName = scan.next();
         String lastName = scan.next();
         String username = scan.next();
         String password = scan.next();
         boolean admin = scan.nextBoolean();
         boolean coach = scan.nextBoolean();
         boolean active = scan.nextBoolean();
         int age = scan.nextInt();
         users.add(new User(id,firstName,lastName, username, password, admin, coach, active, age)); 
         counter++;
      }
   }
   
   public void addUserFromInput(ArrayList<User> users) {
      // might need to be redone, with membership extension
      String firstName, lastName, username, password;
      int id, age;
      
      firstName = scanString("Enter first name: ");
      lastName = scanString("Enter last name: ");
      username = scanString("Enter username: ");
      password = scanString("Enter password: ");
      age = scanInt("Enter your age");
      
    
      //need method to calculate id.
      id = users.size();
      
      
           
      users.add(new User(id,firstName,lastName, username, password, false, false, true, age)); 
      clearFile("Users.txt");
      addUsersToFile(users);
      
   }
   
   public void addUsersToFile(ArrayList<User> users) {
      String input1, input2, input3, input4, input5, input6, input7, input8, input9;
      
      for(int i = 0; i<users.size();i++) {
         input1 = ""+users.get(i).getId();
         input2 = users.get(i).getFirstName();
         input3 = users.get(i).getLastName();
         input4 = users.get(i).getUsername();
         input5 = users.get(i).getPassword();
         input6 = ""+users.get(i).getAdmin();
         input7 = ""+users.get(i).getCoach();
         input8 = ""+users.get(i).getActive();
         input9 = ""+users.get(i).getAge();
         addToFile("Users.txt", input1, input2, input3, input4, input5, input6, input7, input8, input9);
      }
   }
   
   //Records specific methods
   public void readRecords(ArrayList<Record> records){
      int counter = 0;
      while(scan.hasNext()){
         
         String discipline = scan.next();
         String date = scan.next();
         int minutes = scan.nextInt();
         int seconds = scan.nextInt();
         int miliseconds = scan.nextInt();
         int swimId = scan.nextInt();
         records.add(new Record(discipline, date, minutes, seconds, miliseconds, swimId)); 
         counter++;
      }
   
   }
   public void addRecordsToFile(ArrayList<Record> records) {
      String input1, input2, input3, input4, input5, input6;
      
      for(int i = 0; i<records.size();i++) {
         input1 = records.get(i).getDiscipline();
         input2 = records.get(i).getDate();
         input3 = ""+records.get(i).getMinutes();
         input4 = ""+records.get(i).getSeconds();
         input5 = ""+records.get(i).getMiliseconds();
         input6 = ""+records.get(i).getSwimId();
         addToFile("Records.txt", input1, input2, input3, input4, input5, input6,"","","");
      }
   }
   public void addRecordFromInput(ArrayList<Record> records) {
            String discipline, date;
      int minutes, seconds, miliseconds, swimId;
      
      discipline = scanString("Enter swim discipline: ").replace(" ", "_");
      date = scanString("Enter date: ").replace(" ", "_");
      minutes = scanInt("Enter minutes: ");
      seconds = scanInt("Enter seconds: ");
      miliseconds = scanInt("Enter miliseconds: ");
      swimId = scanInt("Enter swimmer Id: "); 
           
      records.add(new Record(discipline, date, minutes, seconds, miliseconds, swimId)); 
      clearFile("Records.txt");
      addRecordsToFile(records);  
   }
   
}