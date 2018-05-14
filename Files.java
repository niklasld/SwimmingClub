import java.util.*;
import java.io.*;
import java.text.*;
import java.lang.*;
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
   
   public int scanInt(String message)  {
      int input = -1;

      while(input == -1) {
         scan = new Scanner(System.in);
         System.out.print(message);
         if(scan.hasNextInt()) {
            input = scan.nextInt();
         }
         else {
            System.out.println("Error input wasnt a single number");
            if(scan.hasNextInt()) {
               input = scan.nextInt();
            }
            else {
               input = -1;
            }
         }
      }
      
      
      return input;
   }
   
   public double scanDouble(String message) {
      scan = new Scanner(System.in);
      double input = -1;
      
      while(input == -1) {
         System.out.print(message);
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
      System.out.print(message);
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
   
   public void updateFiles(ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations, ArrayList<Competition> competitions) {
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
         infoString += " "+users.get(i).getPayed();
         infoString += " "+users.get(i).getAge();
         infoString += " "+users.get(i).getMemberShip();
         infoString += " "+users.get(i).getPassiveMemberShip()+"\n";
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
      clearFile ("CoachRelations.txt");
      for(int i = 0; i<coachRelations.size(); i++){
         infoString = ""+coachRelations.get(i).getSwimId();
         infoString += " "+coachRelations.get(i).getCoachId();
         infoString += " "+coachRelations.get(i).getTeam()+"\n";
         addToFile("CoachRelations.txt", infoString);      
      }
      clearFile ("Competitions.txt");
      for(int i = 0; i<competitions.size(); i++){
         infoString = ""+competitions.get(i).getId();
         infoString += " "+competitions.get(i).getDate();
         infoString += " "+competitions.get(i).getCompetitionName();
         infoString += " "+competitions.get(i).getPlacement();
         infoString += " "+competitions.get(i).getMinutes();
         infoString += " "+competitions.get(i).getSeconds();
         infoString += " "+competitions.get(i).getMiliseconds()+"\n";
         addToFile("Competitions.txt", infoString);      
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
         boolean payed = scan.nextBoolean();
         int age = scan.nextInt();
         String memberShip = scan.next();
         boolean passiveMemberShip = scan.nextBoolean();
         users.add(new User(id,firstName,lastName, username, password, admin, coach, payed, age, memberShip, passiveMemberShip)); 
         counter++;
      }
   }
   
   public void addUserFromInput(ArrayList<User> users) {
      String firstName, lastName, username, password, memberShip;
      int id, age, choice;
      
      firstName = scanString("Enter first name: ");
      lastName = scanString("Enter last name: ");
      username = scanString("Enter username: ");
      password = scanString("Enter password: ");
      age = scanInt("Enter your age:");
      choice = scanInt("1. Excercise Swimmer\n2. Competetive Swimmer");
      if(choice == 1){
         memberShip = "Excercise";        
      }
      else{
         memberShip = "Competetive";
      }
     
      id = users.size();
       
      users.add(new User(id,firstName,lastName, username, password, false, false, true, age, memberShip, false)); 
      
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

   public void addRecordFromInput(int swimId, ArrayList<Record> records) {
      String discipline, date;
      int minutes, seconds, miliseconds;
      int inputMenu;
      discipline = " ";
      
      System.out.println("1. 200m Crawl\n2. 500m Crawl\n3. 200m Freestyle\n4. 500m Freestyle");
      
      while(discipline.equals(" ")) {
         inputMenu = scanInt("");
         switch (inputMenu){
            case 1: 
               // select discipline
               discipline = "200m_Crawl";
               break;
            case 2: 
               discipline = "500m_Crawl";
               break;
            case 3: 
               discipline = "200m_Freestyle";
               break;
            case 4:
               discipline = "500m_Freestyle";
               break;
            default:
               System.out.println("Invalid choice please choose 1-4\n");
               System.out.println("1. 200m Crawl\n2. 500m Crawl\n3. 200m Freestyle\n4. 500m Freestyle");
               break;     
         }
      }
      //discipline = scanString("Enter swim discipline: ").replace(" ", "_");
      Date myDate = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
      date = dateFormat.format(myDate);
      minutes = scanInt("Enter minutes: ");
      seconds = scanInt("Enter seconds: ");
      miliseconds = scanInt("Enter miliseconds: "); 
         
      records.add(new Record(discipline, date, minutes, seconds, miliseconds, swimId)); 
   }
   
   //CoachRelations specific methods
   public void addCoachRelation(int swimId, int coachId, String team, ArrayList<CoachRelation> coachRelations){
      coachRelations.add(new CoachRelation( swimId, coachId, team)); 
      
   } 
   
   public void readCoach(ArrayList<CoachRelation> coachRelations){
      int counter = 0;
      while (scan.hasNext()){
         int swimId  = scan.nextInt();
         int coachId = scan.nextInt();
         String team = scan.next();
         coachRelations.add(new CoachRelation(swimId ,coachId, team));
      
      }   
   }
   
   public void addTeamFromInput(ArrayList<CoachRelation> coachRelations, int swimId, int coachId){

      boolean teamRun = true;
     
      while(teamRun == true){
         int choice = scanInt("Select the option\n1. add to Team 1\n2. add to Team 2\n3. Exit\n");
   
         switch(choice){
            
            case 1:
               addCoachRelation(swimId, coachId, "Team_1",coachRelations);
               teamRun = false;
               break;
               
            case 2:
               addCoachRelation(swimId, coachId, "Team_2",coachRelations);
               teamRun = false;
               break;
            case 3:
               teamRun = false;
               break;
            default:
               System.out.println("Please select option 1 or 2");
               break;
         }
      }
      
   }
   
   //Competition specific methods
   
   public void readCompetition(ArrayList<Competition> competitions) {
      while (scan.hasNext()){
         int id  = scan.nextInt();
         String date = scan.next();
         String competitionName = scan.next();
         int placement = scan.nextInt();
         int minutes = scan.nextInt();
         int seconds = scan.nextInt();
         int miliseconds = scan.nextInt();
         competitions.add(new Competition(id, date, competitionName, placement, minutes, seconds, miliseconds));
      
      }     
   }
   
   public void addCompetitionFromInput(ArrayList<Competition> competitions, ArrayList<User> users, int swimId) {
      System.out.println("Add swimmer to competition\n");
      int choice = -1;
      String competitionType= "";
      boolean run = true;
      boolean exit = false;
      while(choice<1 || choice>5) {
         choice = scanInt("Please select the type of competition\n\t1. 200m Crawl\n\t2. 500m Crawl\n\t3. 200m Freestyle\n\t4. 500m FreeStyle\n\t5. Exit");
      }
      
      while(run==true) {
         switch(choice) {
            case 1:
               //200m Crawl
               competitionType = "200m_Crawl";
               run = false;
               break;
            case 2:
               //500m Crawl
               competitionType = "500m_Cralw";
               run = false;
               break;
            case 3:
               //200m Freestyle
               competitionType = "200m_Freestyle";
               run = false;
               break;
            case 4:
               //500m Freestyle
               competitionType = "500m_Freestyle";
               run = false;
               break;
            case 5:
               //Exit
               run = false;
               exit = true;
               break;
            default:
               //error
               System.out.println("Error files.java method: addCompetitionFromInput");
               break;
         }
      }
      if(exit==false) {
         String competitionName = scanString("Please type in the name of the competition:");
         competitionName = competitionName.replace(" ","_");
         System.out.println(competitionName);
         competitionName += "_"+competitionType;
         
         Date myDate = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         String date = dateFormat.format(myDate);
   
         int placement = scanInt("Please enter the swimmers placement: ");
         
         int minutes = scanInt("Enter time minutes only: ");
         int seconds = scanInt("Enter time seconds only: ");
         int miliseconds = scanInt("Enter time miliseconds only: ");
               
         competitions.add(new Competition(swimId, date, competitionName, placement, minutes, seconds, miliseconds));
      }
   }  
   
}