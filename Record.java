public class Record{
   
   String discipline;
   String date;
   int minutes;
   int seconds;
   int miliseconds;
   int swimId;
   public Record(String discipline, String date, int minutes, int seconds, int miliseconds, int swimId){
   
      this.discipline = discipline;
      this.date = date;
      this.minutes = minutes;
      this.seconds = seconds;
      this.miliseconds = miliseconds; 
      this.swimId = swimId; 
   }
   public void setSwimId(int swimId){
      this.swimId = swimId;
   } 
   public int getSwimId(){
      return swimId;
   }
   public void setDiscipline(String discipline){
      this.discipline = discipline;
   }
   public String getDiscipline(){
      return discipline;
   }
   public void setDate(String date){
      this.date = date;
   }
   public String getDate(){
      return date;
   }
   public void setMinutes(int minutes){
      this.minutes = minutes;
   }
   public int getMinutes(){
      return minutes;
   }
   public void setSeconds(int seconds){
      this.seconds = seconds;
   }
   public int getSeconds(){
      return seconds;
   }
   public void setMiliseconds(int miliseconds){
      this.miliseconds = miliseconds;
   }
   public int getMiliseconds(){
      return miliseconds;
   }      
}