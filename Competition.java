

public class Competition {
   
   private String competitionName, date;
   private int id, placement, minutes, seconds, miliseconds;
   
   public Competition(int id, String date, String competitionName, int placement, int minutes, int seconds, int miliseconds) {
      this.id = id;
      this.date = date;
      this.competitionName = competitionName;
      this.placement = placement;
      this.minutes = minutes;
      this.seconds = seconds;
      this.miliseconds = miliseconds;
      
   }
   public String getDate() {
      return date;
   }
   
   public void setDate(String date) {
      this.date = date;
   }
   
   public void setTime(int minutes, int seconds, int miliseconds) {
      this.minutes = minutes;
      this.seconds = seconds;
      this.miliseconds = miliseconds;
   }
   
   public void setMinutes(int minutes) {
      this.minutes = minutes;
   }
   
   public void setSeconds(int seconds) {
      this.seconds = seconds;
   }
   
   public void setMiliseconds(int miliseconds) {
      this.miliseconds = miliseconds;
   }
   
   public void setId(int id) {
      this.id = id;
   }
   
   public void setCompetitionName(String competitionName) {
      this.competitionName = competitionName;
   }
   
   public void setPlacement(int placement) {
      this.placement = placement;
   }
   
   public String getTimeString() {
      String time = ""+minutes+"min "+seconds+"sec "+miliseconds+"ms";
      return time;
   }
   
   public int getMinutes() {
      return minutes;
   }
   
   public int getSeconds() {
      return seconds;
   }
   
   public int getMiliseconds() {
      return miliseconds;
   }
   
   public int getPlacement() {
      return placement;
   }
   
   public int getId() {
      return id;
   }
   
   public String getCompetitionName() {
      return competitionName;
   }
   
}