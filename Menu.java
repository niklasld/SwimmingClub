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
   public void startMenu(ArrayList<User> users, ArrayList<Record> records){
      int choice = -1;
      while(run == 1){
         choice = file.scanInt("\t1. Log in \n\t2. Quit\n");
         switch(choice){
         
            case 1:
               login(users);
               mainMenu(users, records);
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
   
   public void mainMenu(ArrayList<User> users, ArrayList<Record> records){
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
                  checkPersonalBest(records);
                  break;
               case 3: 
                  //check all time club best
                  break;
               case 4: 
                  // check your coach
                  break;
               case 5: 
                  // quit
                  run = 0;
                  break;
               case 6: 
                  //Show top 5

                  break;
               case 7:
                  // search swimmers
                  System.out.println("temp til søgning kommer addrecord");
                  file.addRecordFromInput(records);
                  break;
               case 8:
                  // create user (admin menu)
                  if(admin == true) {
                     file.addUserFromInput(users);
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
   
   public void checkPersonalBest(ArrayList<Record> records) {
      // missing shows time for each discipline
      
      String discipline = "Nothingggg";
      int min=99;
      int sec=9999;
      int mili=999;
      
      for(Record record: records){
         if(record.getSwimId() == loginId){
            /*System.out.print(record.getDiscipline()+" ");
            System.out.print(record.getDate()+" ");
            System.out.print(record.getMinutes()+" ");
            System.out.print(record.getSeconds()+" ");
            System.out.println(record.getMiliseconds());*/
            
            discipline = record.getDiscipline();
            
            if(discipline.equals(record.getDiscipline())) {
               if(min > record.getMinutes()) {
                  min = record.getMinutes(); 
                  sec = record.getSeconds();
                  mili = record.getMiliseconds();
                  discipline = record.getDiscipline();
               } else if(min == record.getMinutes()){
                  if(sec > record.getSeconds()){
                     min = record.getMinutes(); 
                     sec = record.getSeconds();
                     mili = record.getMiliseconds();
                     discipline = record.getDiscipline();
                  } else if (sec == record.getSeconds()){
                     if(mili > record.getMiliseconds()){
                        min = record.getMinutes(); 
                        sec = record.getSeconds();
                        mili = record.getMiliseconds();
                        discipline = record.getDiscipline();
                     }
                  }
               }
            }
         }
      }
      System.out.println("Discipline: "+discipline+"\nMin: "+min+"\tSec:"+sec+"\tMilisec:"+mili+"\n");
   }
       
}