package user;

import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Rserve_C {
	 	public REXP x = null;
	    public RConnection conn = null;
		public int sid1;	// 메인카테고리 id
		public int sid2;	// 서브카테고리 id
		
		   public void init() throws RserveException{
			   conn = new RConnection();	   
		   } 
		    
		   public void assign(String arg) throws RserveException {			 
			   conn.eval("library(N2H4)");
			   conn.eval(arg);
		   }
		   
		   public String[] category() throws REngineException, REXPMismatchException {
			   RList l = conn.eval("cate<-getMainCategory()").asList();
			   String[] rCate = l.at("cate").asStrings();
			   return rCate;

		   }
		   
		   public String[] subCategory() throws REngineException, REXPMismatchException{
			   conn.eval("tcate<-cate$sid1["+sid1+"]");
			   RList l = conn.eval("subCate<-cbind(sid1=tcate,getSubCategory(sid1=tcate))").asList();
			   String[] rSubCate = l.at("subCate").asStrings();
			   return rSubCate;
		   }
		   
		   
		   public void assignCategory(int flagCate)throws REngineException, REXPMismatchException  { 		    
			   sid1 = flagCate;
		   }		   
		   
		   public void assignSubCategory(int subFlagCate) {
			   	 sid2 = subFlagCate;
			   
		   }
		   

}