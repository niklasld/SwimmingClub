public class CoachRelation {
 
   private int swimId,coachId;
   private String team;
   
   public CoachRelation(int swimId,int coachId, String team) {
      this.swimId = swimId;
      this.coachId = coachId;
      this.team = team;
   } 
   public void setSwimId(int swimId){
      this.swimId = swimId;
   }
   public int getSwimId (){
      return swimId;
   }
   
   public void setCoachId(int coachId){
      this.coachId = coachId;
   }
   public int getCoachId (){
      return coachId;
   }
   public void setTeam(String team){
      this.team = team;
   }
   public String getTeam(){
      return team;
   }
   
}