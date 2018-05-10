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

            System.out.println("\nMain menu:\n");

            System.out.println("\t1. Check Lane record \n\t2. Check personal best \n\t3. Check all time club best \n\t4. Check coach \n\t5. Set membership status to passive\n\t6. Quit");

            if(coach == true || admin == true){               
               System.out.println("\n\tCoach menu:\n\t7. show top 5 \n\t8. Search Swimmers");
            }
            if(admin == true){
               System.out.println("\n\tAdmin menu:\n\t9. Create user\n\t10. Show payments");
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
                  System.out.println("User is currently set to "+users.get(loginId).getPassiveMemberShip()+"\nIf member status is true the swimmer will pay 500kr a year, if false default prices are used");
                  try {
                     boolean runMemberStatus = true;
                     while(runMemberStatus == true) {
                        int input = file.scanInt("\n1. Set to true\n2. Set to false\n3. Exit");
                       
                        switch(input) {
                           case 1:
                              // set to true
                              users.get(loginId).setPassiveMemberShip(true);
                              runMemberStatus = false;
                              break;
                           case 2:
                              // set to false
                              users.get(loginId).setPassiveMemberShip(false);
                              runMemberStatus = false;
                              break;
                           case 3: 
                              runMemberStatus = false;
                           default:
                              System.out.println("Please choose 1-3"); 
                              break;
                        }
                     }
                  }
                  catch(Exception e) {
                     System.out.println("Error in setmemberShip menu(edit swimmer in files.java)");
                  }
                  break;               
               case 6: 
                  // quit
                  run = 0;
                  break;
               case 7: 
                  //Show top 5
                  try {
                     checkTopFive(users, records);
                  }
                  catch(Exception e) {
                     System.out.println("No matching number\n");
                  }
                  break;
               case 8:
                  // search swimmers
                  try {
                     swimSearch(users, records, coachRelations);
                     file.updateFiles(users, records, coachRelations);
                  }
                  catch(Exception e) {
                     System.out.println(" number dosent match the swimmer ");
                  }
                  
                  break;
               case 9:
                  // create user (admin menu)
                  if(admin == true) {
                     file.addUserFromInput(users);
                     file.updateFiles(users, records, coachRelations);
                  }
                  break;
               case 10:
                  // show payments due
                  if(admin == true) {
                     System.out.println("Swimmer who are behind payment on membership:\n");
                     for(User result : users) {
                        if(result.getPayed() == false) {
                           System.out.println("Id: "+result.getId()+"\tName: "+result.getNames()+"\tPayed:"+result.getPayed());
                        }
                     }               
                  }
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
         if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password) && users.get(i).getPayed() == true){
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
      //System.out.println("edit " + users.get(swimId).getFirstName() + " " + users.get(swimId).getLastName() + "\n");



      for (boolean runEdit = true; runEdit == true;) {
         System.out.println("\nEdit swimmer menu for the swimmer: "+users.get(swimId).getNames()+"\n");
         System.out.println("1. Edit firstname");
         System.out.println("2. Edit lastname");
         System.out.println("3. Edit username");
         System.out.println("4. Edit password");
         System.out.println("5. Edit active");
         System.out.println("6. Edit age");
         System.out.println("7. Edit membership");
         System.out.println("8. Add coach");
         System.out.println("9. Add record");
         System.out.println("10. Show/eit swim team);
         System.out.println("11. Exit\n");
         if(admin == true) {
            System.out.println("Admin menu: ");
            System.out.println("11. Edit membership payment status");
            System.out.println("12. show Membership price\n");
         }
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
               users.get(swimId).setPayed(active);
               break;
            case 6:
               //edit age
               int age = file.scanInt("Enter new age: ");
               users.get(swimId).setAge(age);
               break;
            case 7:
               //edit membership
               break;
            case 8:
               //add coach
               addCoach(coachRelations, users, swimId);
               file.updateFiles(users, records,coachRelations); 
               break;
            case 9:
               //add record
               file.addRecordFromInput(swimId, records);
               file.updateFiles(users, records, coachRelations);
               break;
            case 10:
               //exit
               runEdit = false;
               break;
            case 11:
               //Edit membership payed status
               if(admin ==  true) {
                  System.out.println("User is currently set to "+users.get(swimId).getPayed()+"\nIf member status is true the swimmer has payed for its membership");
                  try {
                     boolean runMemberStatus = true;
                     while(runMemberStatus == true) {
                        int input = file.scanInt("\n1. Set to true\n2. Set to false\n3. Exit");
                       
                        switch(input) {
                           case 1:
                              // set to true
                              users.get(swimId).setPayed(true);
                              runMemberStatus = false;
                              break;
                           case 2:
                              // set to false
                              users.get(swimId).setPayed(false);
                              runMemberStatus = false;
                              break;
                           case 3: 
                              // Exit
                              runMemberStatus = false;
                              break;
                           default:
                              System.out.println("Please choose 1-3"); 
                              break;
                        }
                     }
                  }
                  catch(Exception e) {
                     System.out.println("Error in setmemberShip menu(edit swimmer in files.java)");
                  }
               }
            case 12:
               if(admin == true) {
                  double price = users.get(swimId).getMemberShipPrice();
                  System.out.println("Swimmer is paying "+price+" per year.");
               }
               break;
      
            default:
               System.out.println("Invalid action");
               break;
         }

      }
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
      System.out.println(getBestTimes(records,users, "200m_Crawl"));
      System.out.println(getBestTimes(records,users, "500m_Crawl"));
      System.out.println(getBestTimes(records,users, "200m_Freestyle"));
      System.out.println(getBestTimes(records,users, "500m_Freestyle"));
      
   }

   public String getBestTimes (ArrayList<Record> records,ArrayList<User> users, String discipline) {
      int min=999;
      int sec=9999;
      int mili=999;
      String swimmer = "blANK";

      for(Record record: records){
         //swimmer = ""+users.get(record.getSwimId()).getFirstName()+" "+users.get(record.getSwimId()).getLastName();
         if(discipline.equals(record.getDiscipline())) {
            if(min > record.getMinutes()) {
               min = record.getMinutes();
               sec = record.getSeconds();
               mili = record.getMiliseconds();
               discipline = record.getDiscipline();
               swimmer = ""+users.get(record.getSwimId()).getFirstName()+" "+users.get(record.getSwimId()).getLastName();

            }
            else if(min == record.getMinutes()){
               if(sec > record.getSeconds()){
                  min = record.getMinutes();
                  sec = record.getSeconds();
                  mili = record.getMiliseconds();
                  discipline = record.getDiscipline();
                  swimmer = ""+users.get(record.getSwimId()).getFirstName()+" "+users.get(record.getSwimId()).getLastName();

               }
               else if (sec == record.getSeconds()){
                  if(mili > record.getMiliseconds()){
                     min = record.getMinutes();
                     sec = record.getSeconds();
                     mili = record.getMiliseconds();
                     discipline = record.getDiscipline();
                     swimmer = ""+users.get(record.getSwimId()).getFirstName()+" "+users.get(record.getSwimId()).getLastName();

                  }
               }
            }
         }
      }
      String time = "Discipline: "+discipline.replace("_"," ")+"\nSwimmer: "+swimmer+"\nRecord:\t"+min+"min "+sec+"sec "+mili+"ms\n";
      return time;
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
            //System.out.println("Invalid action");
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
      boolean result = false;
     
      for(int i = 0; i<coachRelations.size(); i++){
         if(coachRelations.get(i).getSwimId()==loginId) {
            System.out.println(users.get(loginId).getFirstName()+" "+ users.get(loginId).getLastName()+" Coach: "+ users.get(coachRelations.get(i).getCoachId()).getFirstName()+" "+users.get(coachRelations.get(i).getCoachId()).getLastName());
            result = true;
         }
      }
      if (result == false){
         System.out.println("You have no Coach\n");
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