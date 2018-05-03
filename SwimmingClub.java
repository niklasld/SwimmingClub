import java.util.*;

public class SwimmingClub {
   public static void main(String[] args) {
   
      Files file = new Files();
      Menu menu = new Menu();
      
      ArrayList<User> users = new ArrayList<User>();
      ArrayList<Record> records = new ArrayList<Record>();
      
      file.createFile("Users.txt");
      file.openFile("Users.txt");
      file.readUsers(users);
      file.closeFile();
      file.createFile("Records.txt");
      file.openFile("Records.txt");
      file.readRecords(records);
      file.closeFile();
      
      menu.startMenu(users, records);
      
   }
}