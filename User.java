public class User {
   private String firstName, lastName, username, password;
   private int id;
   private boolean admin = false;
   private boolean coach = false;
   private boolean active = true;
   private int age;
   
   public User(int id, String firstName, String lastName, String username, String password, boolean admin, boolean coach, boolean active, int age) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.username = username;
      this.password = password;
      this.admin = admin;
      this.coach = coach;
      this.active = active;
      this.age = age;
   }
   
   public void setAge(int age){
      this.age = age;  
   }
   
   public int getAge(){
      return age;
   }
   
   public void setAdmin(boolean admin){
      this.admin = admin;  
   }
   
   public boolean getAdmin(){
      return admin;
   }
      
   public void setCoach(boolean coach){
      this.coach = coach;  
   }
   
   public boolean getCoach(){
      return coach;
   } 
     
   public void setActive(boolean active){
      this.active = active;  
   }
   
   public boolean getActive(){
      return active;
   }
      
   public void setId(int id) {
      this.id = id;
   }
   
   public int getId() {
      return id;
   }
   
   public void setUsername(String username) {
      this.username = username;
   }
     
   public String getUsername(){
      return username;
   }
   
   public void setPassword(String password){
      this.password = password;
   }
   
   public String getPassword(){
      return password;
   }
      
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }
   
   public String getFirstName() {
      return firstName;
   }
   
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }
   
   public String getLastName() {
      return lastName;
   }
}