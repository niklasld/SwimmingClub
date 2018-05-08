import java.util.*;
import java.io.*;

public class Menu{
   
   Scanner scan;
   Files file = new Files();
   private int run = 1;
   private boolean loginMatch = false;
   private boolean admin = false;
   private boolean coach = false;
   private int loginId;
   
   public void setAdmin(ArrayList<User> users, boolean admin, int userId){
      if (admin == true) {
         users.get(userId).setAdmin(admin);
      }
    
   }
   public void startMenu(ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations){
      int choice = -1;
      while(run == 1){
         choice = file.scanInt("\t1. Log in \n\t2. Quit\n");
         switch(choice){
         
            case 1:
               login(users);
               mainMenu(users, records, coachRelations);
               break;
            case 2:
               run=0;
               break; 
            default:
               System.out.println("Invalid option, please choose login or quit"); 
               break;
         } 
      }
   }
   
   public void mainMenu(ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations){
      if(loginMatch==true) {
         int choice = -1;
         while(run ==1) {
            System.out.println("\t1. Check Lane record \n\t2. Check personal best \n\t3. Check all time club best \n\t4. Check coach \n\t5. Quit");
            if(coach == true || admin == true){               
               System.out.println("\n\tCoach menu:\n\t6. show top 5 \n\t7. Search Swimmers");
            }
            if(admin == true){
               System.out.println("\n\tAdmin menu:\n\t8. Create user\n\t9. Show payments");
            }
            choice = file.scanInt("");
            switch(choice){
               case 1:
                  //check lane record (user)
                  showRecords(records);
                  break;
               case 2:
                  //check personal best
                  checkPersonalBest(records, users, "200m_Crawl");
                  checkPersonalBest(records, users, "500m_Crawl");
                  checkPersonalBest(records, users, "200m_Freestyle");
                  checkPersonalBest(records, users, "500m_Freestyle");
                  break;
               case 3: 
                  //check all time club best
                  checkClubBest(users, records);
                  break;
               case 4: 
                  // check your coach
                  checkCoach(users, coachRelations);
                  break;
               case 5: 
                  // quit
                  run = 0;
                  break;
               case 6: 
                  //Show top 5
                  checkTopFive(users, records);
                  break;
               case 7:
                  // search swimmers
                  swimSearch(users, records, coachRelations);
                  
                  break;
               case 8:
                  // create user (admin menu)
                  if(admin == true) {
                     file.addUserFromInput(users);
                     file.updateFiles(users, records, coachRelations);
                  }
                  break;
               case 9:
                  // show payments due
                  break;
               default:
                  System.out.println("Invalid option please select one of the menu functions or quit the program");
                  break;
            }
         }
      }
   }
   

   
   
   public void login(ArrayList<User> users){
      String username = "";
      String password = "";
      username = file.scanString("login\n choose Username: ");
      password = file.scanString("choose password: ");
   
      for(int i = 0; i<users.size();i++){
         if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password) && users.get(i).getActive() == true){
            this.loginMatch = true;
            this.loginId = i;
            if(users.get(i).getAdmin() == true){
               this.admin = true;
              
            }
            if(users.get(i).getCoach() == true){
               this.coach = true;
            }
         }
      
      
      }
   }

   public void showRecords(ArrayList<Record> records) {
      for(Record record: records){
         if(record.getSwimId() == loginId){
            System.out.print(record.getDiscipline()+" ");
            System.out.print(record.getDate()+" ");
            System.out.print(record.getMinutes()+" ");
            System.out.print(record.getSeconds()+" ");
            System.out.println(record.getMiliseconds());
         }
      } 
   }

   public void swimSearch(ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations){
      String search = file.scanString("Enter swimmer name; ");
      boolean result = false;
      int resCount = 0;

      search = search.toLowerCase();

      for(User u: users){
         if(u.getFirstName().toLowerCase().contains(search) || u.getLastName().toLowerCase().contains(search)){
            System.out.println(u.getId()+": "+u.getFirstName()+" "+u.getLastName());
            result = true;
            resCount++;
         }
      }

      if(result == true){
         System.out.println(resCount+" matched your search input.\n");

         System.out.println("Select the id number for the swimmer you want to edit");
         //file.addRecordFromInput(records);

         int selectId = file.scanInt("");
         editSwimmer(selectId, users, records, coachRelations);

      } else {
         System.out.println("No match!");
      }
   }

   public void editSwimmer(int swimId, ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations){
      System.out.println("edit " + users.get(swimId).getFirstName() + " " + users.get(swimId).getLastName() + "\n");



      for (boolean runEdit = true; runEdit == true;) {

         System.out.println("1. Edit firstname");
         System.out.println("2. Edit lastname");
         System.out.println("3. Edit username");
         System.out.println("4. Edit password");
         System.out.println("5. Edit active");
         System.out.println("6. Edit age");
         System.out.println("7. Add coach");
         System.out.println("8. Add record");
         System.out.println("9. Exit");

         int select = file.scanInt("Select option: ");

         switch (select) {
            case 1:
               //edit firstname
               String firstName = file.scanString("Enter new firstname: ");
               users.get(swimId).setFirstName(firstName);
               break;
            case 2:
               //edit lastname
               String lastName = file.scanString("Enter new lastname: ");
               users.get(swimId).setLastName(lastName);
               break;
            case 3:
               //edit username
               String username = file.scanString("Enter new username: ");
               users.get(swimId).setUsername(username);
               break;
            case 4:
               //edit password
               String password = file.scanString("Enter new password: ");
               users.get(swimId).setPassword(password);
               break;
            case 5:
               //edit active
               boolean active = file.scanBoolean("Active: true/false: ");
               users.get(swimId).setActive(active);
               break;
            case 6:
               //edit age
               int age = file.scanInt("Enter new age: ");
               users.get(swimId).setAge(age);
               break;
            case 7:
               //add coach
               addCoach(coachRelations, users, swimId);
               file.updateFiles(users, records,coachRelations); 
               break;
            case 8:
               //add record
               file.addRecordFromInput(swimId, records);
               file.updateFiles(users, records, coachRelations);
               break;
            case 9:
               //exit
               runEdit = false;
               break;
            default:
               System.out.println("Invalid action");
               break;
         }

      }

      /*for(User u: users){
         if(u.getId() == swimId){
            u.setFirstName(firstName);
            u.setLastName(lastName);

         }
      }*/
   }
   
   public void checkPersonalBest(ArrayList<Record> records, ArrayList<User> users, String discipline) {
      // missing shows time for each discipline
      
      //discipline = "Nothingggg";
      int min=99;
      int sec=9999;
      int mili=999;
      
      String swimmer = ""+users.get(loginId).getFirstName()+" "+users.get(loginId).getLastName();
      
      for(Record record: records){
         if(record.getSwimId() == loginId && discipline.equals(record.getDiscipline())){
            
            if(discipline.equals(record.getDiscipline())) {
               if(min > record.getMinutes()) {
                  min = record.getMinutes(); 
                  sec = record.getSeconds();
                  mili = record.getMiliseconds();
               } 
               else if(min == record.getMinutes()){
                  if(sec > record.getSeconds()){
                     min = record.getMinutes(); 
                     sec = record.getSeconds();
                     mili = record.getMiliseconds();
                  } 
                  else if (sec == record.getSeconds()){
                     if(mili > record.getMiliseconds()){
                        min = record.getMinutes(); 
                        sec = record.getSeconds();
                        mili = record.getMiliseconds();
                     }
                  }
               }
            }
         }
      }
      if(min == 99) {
        System.out.println("No record for: "+"Discipline: "+discipline.replace("_"," ")+"\n"); 
         
      }
      else {
         System.out.println("Discipline: "+discipline.replace("_"," ")+"\nSwimmer: "+swimmer+"\nRecord:\t"+min+"min "+sec+"sec "+mili+"ms\n");
      }
   }
   
   public void checkClubBest(ArrayList<User> users, ArrayList<Record> records) {
      // missing Dates correctly 
      /*for (String best: getBestTimes(records,users, "200m_Crawl")) {
         System.out.println(best);
      }
      System.out.println(getBestTimes(records,users, "500m_Crawl"));
      System.out.println(getBestTimes(records,users, "200m_Freestyle"));
      System.out.println(getBestTimes(records,users, "500m_Freestyle"));*/
      
   }
   public void checkTopFive(ArrayList<User> users, ArrayList<Record> records){
      int recordInput = file.scanInt("1. 200m Crawl\n2. 500m Crawl\n3. 200m Freestyle\n4. 500m Freestyle");
      String discipline = ""; 
      switch(recordInput){
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
            System.out.println("Invalid action");
            break;             
      }

      for (String best: getFiveBest(records, users, discipline)){
         System.out.println(best);
      }

   }

   public String[] getFiveBest (ArrayList<Record> records, ArrayList<User> users, String discipline) {
      int min=999;
      int sec=9999;
      int mili=999;
      int index=9999;
      String[] top5 = new String[5];
      String swimmer = "blANK";
      //ArrayList<Record> topFive = (ArrayList<Record>)records.clone();
      ArrayList<Record> topFive = new ArrayList<>(records);
      //topFive = records;
      /*

      
      for(int i = 0; i<5;i++){
      
      }
      
          */

      int i = 0;
      int j = 0;

      for(int x = 0; x<5; x++) {
         i=0;
         min=999;
         sec=9999;
         mili=999;
         for (Record record : topFive) {
            //swimmer = ""+users.get(record.getSwimId()).getFirstName()+" "+users.get(record.getSwimId()).getLastName();
            if (discipline.equals(record.getDiscipline())) {
               if (min > record.getMinutes()) {
                  min = record.getMinutes();
                  sec = record.getSeconds();
                  mili = record.getMiliseconds();
                  discipline = record.getDiscipline();
                  swimmer = ""+users.get(record.getSwimId()).getNames();
                  //j++;
                  index = i;

               } else if (min == record.getMinutes()) {
                  if (sec > record.getSeconds()) {
                     min = record.getMinutes();
                     sec = record.getSeconds();
                     mili = record.getMiliseconds();
                     discipline = record.getDiscipline();
                     swimmer = ""+users.get(record.getSwimId()).getNames();
                     //j++;
                     index = i;

                  } else if (sec == record.getSeconds()) {
                     if (mili > record.getMiliseconds()) {
                        min = record.getMinutes();
                        sec = record.getSeconds();
                        mili = record.getMiliseconds();
                        discipline = record.getDiscipline();
                        swimmer = ""+users.get(record.getSwimId()).getNames();
                        //j++;
                        index = i;

                     }
                  }
               }
            }
            i++;
         }
         top5[j] = "Discipline: " + discipline.replace("_", " ") + "\nSwimmer: " + swimmer + "\nRecord:\t" + min + "min " + sec + "sec " + mili + "ms\n";
         topFive.remove(index);
         j++;
      }
      return top5;
   }
  
   public void checkCoach(ArrayList<User> users , ArrayList<CoachRelation> coachRelations){
      for(int i = 0; i<coachRelations.size(); i++){
         if(coachRelations.get(i).getSwimId()==loginId) {
            System.out.println(users.get(loginId).getFirstName()+" "+ users.get(loginId).getLastName()+" Coach: "+ users.get(coachRelations.get(i).getCoachId()).getFirstName()+" "+users.get(coachRelations.get(i).getCoachId()).getLastName());
            
         }
      }
   }    
   public void addCoach(ArrayList<CoachRelation> coachRelations, ArrayList<User> users, int swimId){
      
      System.out.println("Available coaches for the swimmer" + users.get(swimId).getFirstName() + " "+users.get(swimId).getLastName());
      
      for(int i=0;i<users.size();i++){
         if (users.get(i).getCoach() == true){
            System.out.println(users.get(i).getId() + ". " + users.get(i).getFirstName() + " " + users.get(i).getLastName());
         }
      } 
      int coachId = file.scanInt("Select the coach you want to add to the swimmer");
      file.addCoachRelation(swimId, coachId, coachRelations);
      
   }
}