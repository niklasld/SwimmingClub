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
   
   public void addToFile(String fileName, String test) {
      try{
         FileWriter fileW = new FileWriter("txt//"+fileName, true);
         BufferedWriter buffW = new BufferedWriter(fileW);
         buffW.write(test);
         buffW.close();
      }
      catch(Exception e){
         System.out.println("Error writing to "+fileName+" (Files.java method: addToFile)");
      }
   }
   
   public void updateFiles(ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations) {
      String infoString;
      clearFile("Users.txt");
      for(int i = 0; i<users.size();i++) {
         infoString = ""+users.get(i).getId();
         infoString += " "+users.get(i).getFirstName();
         infoString += " "+users.get(i).getLastName();
         infoString += " "+users.get(i).getUsername();
         infoString += " "+users.get(i).getPassword();
         infoString += " "+users.get(i).getAdmin();
         infoString += " "+users.get(i).getCoach();
         infoString += " "+users.get(i).getActive();
         infoString += " "+users.get(i).getAge()+"\n";
         addToFile("Users.txt", infoString);
      }
      clearFile("Records.txt");
      for(int i = 0; i<records.size();i++) {
         infoString = ""+records.get(i).getDiscipline();
         infoString += " "+records.get(i).getDate();
         infoString += " "+records.get(i).getMinutes();
         infoString += " "+records.get(i).getSeconds();
         infoString += " "+records.get(i).getMiliseconds();
         infoString += " "+records.get(i).getSwimId()+"\n";
         addToFile("Records.txt", infoString);
      }
      clearFile ("CoachRelation.txt");
      for(int i = 0; i<coachRelations.size(); i++){
         infoString = ""+coachRelations.get(i).getSwimId();
         infoString = " "+coachRelations.get(i).getCoachId()+"\n";
         addToFile("CoachRelation.txt", infoString);
         
               
               
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
      String firstName, lastName, username, password;
      int id, age;
      
      firstName = scanString("Enter first name: ");
      lastName = scanString("Enter last name: ");
      username = scanString("Enter username: ");
      password = scanString("Enter password: ");
      age = scanInt("Enter your age");
  
      id = users.size();
       
      users.add(new User(id,firstName,lastName, username, password, false, false, true, age)); 
      
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
   }
   
   //CoachRelations specific methods
   
   public void readCoach(ArrayList<CoachRelation> coachRelations){
      int counter = 0;
      while (scan.hasNext()){
         int swimId  = scan.nextInt();
         int coachId = scan.nextInt();
         
         coachRelations.add(new CoachRelation(swimId ,coachId));
      
      }   
   }

   
}