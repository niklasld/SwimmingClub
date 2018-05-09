import java.util.*;
import java.text.*;

public class SwimmingClub {
   public static void main(String[] args) {

      // low priority: add show coached method
      //try and catch on every outOfBounds errors
      //checkPayments
      //checkMemberShip-- forskellige rabatter
      
      Files file = new Files();
      Menu menu = new Menu();
      
      ArrayList<User> users = new ArrayList<User>();
      ArrayList<Record> records = new ArrayList<Record>();
      ArrayList<CoachRelation> coachRelations = new ArrayList<CoachRelation>();
      
      file.createFile("Users.txt");
      file.openFile("Users.txt");
      file.readUsers(users);
      file.closeFile();
      
      file.createFile("Records.txt");
      file.openFile("Records.txt");
      file.readRecords(records);
      file.closeFile();
      
      file.createFile("CoachRelations.txt");
      file.openFile("CoachRelations.txt");
      file.readCoach(coachRelations);
      file.closeFile();
      

      menu.startMenu(users, records, coachRelations);
      
   }
}