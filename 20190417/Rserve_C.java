package user;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Rserve_C {

		private RConnection conn = null;
		
		   public void init() throws RserveException{
			   conn = new RConnection();
		   }
		   
		   public void assign(String Directory) {
			   try {
				REXP exp=conn.eval(Directory);
			} catch (RserveException e) {
				e.printStackTrace();
			}
		   }
		   
		   public void category() {
			   
		   }
		   
		   public void subCategory() {
			   
		   }
		   
		   public void assignCategory(int FlagCate) {
			   
		   }
		   
		   public void assignSubCategory (int subFlagCate) {
			   
		   }
		   

}