import java.util.*;

public class SwimmingClub {
   public static void main(String[] args) {
      Files file = new Files();
      
      ArrayList<User> users = new ArrayList<User>();
      
      file.createFile("Users.txt");
      file.openFile("Users.txt");
      file.readUsers(users);
      file.closeFile();
      
      file.addUserFromInput(users);
      System.out.println(users.size());
   }
}