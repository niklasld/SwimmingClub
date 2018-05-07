public class CoachRelation {
 
   private int swimId,coachId;
 
   
   public CoachRelation(int swimId,int coachId) {
      this.swimId = swimId;
      this.coachId = coachId;
   
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
   
   
   
}