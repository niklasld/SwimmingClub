public class User {
   private String firstName, lastName, username, password, memberShip;
   private int id;
   private boolean admin = false;
   private boolean coach = false;
   private boolean payed = true;
   private boolean passiveMemberShip = false;
   private int age;
   
   public User(int id, String firstName, String lastName, String username, String password, boolean admin, boolean coach, boolean payed, int age, String memberShip, boolean passiveMemberShip) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.username = username;
      this.password = password;
      this.admin = admin;
      this.coach = coach;
      this.payed = payed;
      this.age = age;
      this.memberShip = memberShip;
      this.passiveMemberShip = passiveMemberShip;
   }
   
   public double getMemberShipPrice() {
      double price = 1600.00;
      double finalPrice;
      
      if(passiveMemberShip == true){
         finalPrice = 500;
      }
      else if(age < 18) {
         finalPrice = 1000;
      } 
      else if(age>59) {
         finalPrice = price*0.75;
      }
      else {
         finalPrice = price;
      }

      return finalPrice;
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
     
   public void setPayed(boolean payed){
      this.payed = payed;  
   }
   
   public boolean getPayed(){
      return payed;
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

   public String getNames(){
      return firstName + " " + lastName;
   }
   public void setMemberShip(String memberShip){
      this.memberShip = memberShip;
   } 
   
   public String getMemberShip(){
      return memberShip;
   }
   
   public void setPassiveMemberShip(boolean passiveMemberShip) {
      this.passiveMemberShip = passiveMemberShip;
   }
   
   public boolean getPassiveMemberShip() {
      return passiveMemberShip;
   }
   
}