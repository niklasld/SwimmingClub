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
   public void startMenu(ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations, ArrayList<Competition> competitions){
      int choice = -1;
      while(run == 1){
         choice = file.scanInt("\t1. Log in \n\t2. Quit\n");
         switch(choice){
         
            case 1:
               login(users);
               mainMenu(users, records, coachRelations, competitions);
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
   
   public void mainMenu(ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations, ArrayList<Competition> competitions){
      if(loginMatch==true) {
         int choice = -1;
         while(run ==1) {         

            System.out.println("\nMain menu:\n");

            System.out.println("\t1. Check Lane record \n\t2. Check personal best \n\t3. Check all time club best \n\t4. Check coach \n\t5. Set membership status to passive\n\t6. Show my Competitions\n\t7. Quit");

            if(coach == true || admin == true){               
               System.out.println("\n\tCoach menu:\n\t8. show top 5 \n\t9. Search Swimmers");
            }
            if(admin == true){
               System.out.println("\n\tAdmin menu:\n\t10. Create user\n\t11. Show payments");
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
                  //Show my competitions
                  showMyCompetitions(loginId,competitions);
                  break;
               
               case 7: 
                  // quit
                  run = 0;
                  break;
               case 8: 
                  //Show top 5
                  try {
                     checkTopFive(users, records);
                  }
                  catch(Exception e) {
                     System.out.println("No matching number\n");
                  }
                  break;
               case 9:
                  // search swimmers
                  try {
                     swimSearch(users, records, coachRelations, competitions);
                     file.updateFiles(users, records, coachRelations, competitions);
                  }
                  catch(Exception e) {
                     System.out.println(" number dosent match the swimmer ");
                  }
                  break;
               case 10:
                  // create user (admin menu)
                  if(admin == true) {
                     file.addUserFromInput(users);
                     file.updateFiles(users, records, coachRelations, competitions);
                  }
                  break;
               case 11:
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
      username = file.scanString("login\nchoose Username: ");
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

   public void swimSearch(ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations, ArrayList<Competition> competitions){
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
         editSwimmer(selectId, users, records, coachRelations, competitions);

      } else {
         System.out.println("No match!");
      }
   }

   public void editSwimmer(int swimId, ArrayList<User> users, ArrayList<Record> records, ArrayList<CoachRelation> coachRelations, ArrayList<Competition> competitions){
      //System.out.println("edit " + users.get(swimId).getFirstName() + " " + users.get(swimId).getLastName() + "\n");



      for (boolean runEdit = true; runEdit == true;) {
         System.out.println("\nEdit swimmer menu for the swimmer: "+users.get(swimId).getNames()+"\n");
         System.out.println("1. Edit firstname");
         System.out.println("2. Edit lastname");
         System.out.println("3. Edit username");
         System.out.println("4. Edit password");
         System.out.println("5. Edit active");
         System.out.println("6. Edit age");
         System.out.println("7. Edit Competition Status");
         System.out.println("8. Add coach");
         System.out.println("9. Add record");
         System.out.println("10. Show/edit swim team");
         System.out.println("11. add swimmer to competition");
         System.out.println("12. Exit\n");
         if(admin == true) {
            System.out.println("Admin menu: ");
            System.out.println("13. Edit membership payment status");
            System.out.println("14. show Membership price\n");
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
               //edit competition status
               changeMembershipStatus(users,swimId);
               break;
            case 8:
               //add coach
               addCoach(coachRelations, users, swimId);
               file.updateFiles(users, records,coachRelations, competitions); 
               break;
            case 9:
               //add record
               file.addRecordFromInput(swimId, records);
               file.updateFiles(users, records, coachRelations, competitions);
               break;
            case 10:
               //Show/edit swim team
               boolean teamMatch = false;
               for(CoachRelation c : coachRelations){
                  if( c.getSwimId() == swimId){
                     System.out.println(c.getTeam().replace("_", " "));
                     teamMatch = true;
                  }
               }
               if(teamMatch == false){
                  System.out.println("No team");
               }

               System.out.println("\nDo you want, to add a team");
               System.out.println("1. yes\n2. no\n");
               int choice;
               for (boolean run = true; run == true;){
                  choice = file.scanInt("");
                  if (choice == 1){
                     editTeam(coachRelations, users, swimId);
                     run = false;
                  } else if (choice == 2) {
                     System.out.println("Alright");
                     run = false;
                  } else {
                     System.out.println("Invalid action!");
                  }
               }

               break;
            case 11:
               //add swimmer to competition
               file.addCompetitionFromInput(competitions, users, swimId);
               break;
            case 12:
               //exit
               runEdit = false;
               break;
            case 13:
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
               break;
            case 14:
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
      String team = "";
      for(boolean run = true; run == true; ) {
         int choice = file.scanInt("select team:\n1. Team Junior\n2. Team Senior\n3. No team");
         if (choice == 1) {
            team = "Team_Junior";
            run = false;
         } else if (choice == 2) {
            team = "Team_Senior";
            run = false;
         } else if (choice == 3) {
            team = "No_team";
            run = false;
         }
         else {
            System.out.println("Invalid choise");
         }
      }
      file.addCoachRelation(swimId, coachId, team, coachRelations);

   }

   public void editTeam(ArrayList<CoachRelation> coachRelations, ArrayList<User> users, int swimId){
      int i = 0;
      int j = 0;
      int relation = -1;
      int[] choices = new int[50];
      choices[0] = -1;

      System.out.println("\nSelect coach relationship:\n");

      for (boolean run = true; run == true;) {
         for (CoachRelation c : coachRelations) {
            if(c.getSwimId() == swimId) {
               System.out.println("id:" + i + " Swimmer:" + users.get(c.getSwimId()).getNames() + " coach:" + users.get(c.getCoachId()).getNames());
               choices[j] = i;
               j++;
            }
            i++;
         }

         if(choices[0] != -1) {

            relation = file.scanInt("Select number: ");
            for (int c : choices) {
               if (c == relation) {
                  run = false;
               }
            }

            if (run == true) {
               System.out.println("Invalid action");
            }

         } else {
            file.addTeamFromInput(coachRelations,swimId, loginId);
            run = false;
         }
      }

      if(choices[0] != -1) {
         String team = "";

         for (boolean run = true; run == true; ) {
            System.out.println("Select team:");
            System.out.println("1. Team Junior\n2. Team Senior\n3. No team");

            int choice = file.scanInt("Select number: ");
            switch (choice) {
               case 1:
                  team = "Team_Junior";
                  run = false;
                  break;
               case 2:
                  team = "Team_Senior";
                  run = false;
                  break;
               case 3:
                  team = "No team";
                  run = false;
                  break;
               default:
                  System.out.println("invalid action");
                  break;
            }

         }

         if (relation >= 0) {
            coachRelations.get(relation).setTeam(team);
         }
      }
   }
   
   public void showMyCompetitions(int loginId,ArrayList<Competition> competitions){
      for(Competition c : competitions) {
         if(loginId == c.getId()) {
            System.out.println("Event/Discipline: "+c.getCompetitionName().replace("_"," ")+"\n"+"Date: "+c.getDate()+"\n"+"Placement: "+c.getPlacement()+"\nTime: "+c.getTimeString()+"\n");
         }
      }
   }
   public void changeMembershipStatus(ArrayList<User> users,int swimId) {
      System.out.println("\nChange Competition Status:\n");
      System.out.println(users.get(swimId).getNames()+"\nCurrent status is: "+users.get(swimId).getMemberShip()+"\n");
      int choice = file.scanInt("Do you wish to change the swimmers status?\n1. Yes\n2. No");
      if(choice == 1) {
         choice = file.scanInt("\n1. Set to Competitive\n2. Set to Excersise\n3. Exit\n");
         if(choice == 1) {
            users.get(swimId).setMemberShip("Competitive");
         }
         else if(choice == 2) {
            users.get(swimId).setMemberShip("Excercise");
         }
         else if(choice == 3){
            
         }
      }
   }
}