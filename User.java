public class User {
   private String firstName, lastName;
   private int id, pin;
   
   public User(int id, int pin, String firstName, String lastName) {
      this.id = id;
      this.pin = pin;
      this.firstName = firstName;
      this.lastName = lastName;
   }
   
   public void setId(int id) {
      this.id = id;
   }
   
   public int getId() {
      return id;
   }
   
   public void setPin(int pin) {
      this.pin = pin;
   }
   
   public int getPin() {
      return pin;
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