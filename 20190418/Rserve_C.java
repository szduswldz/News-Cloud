package user;

import org.rosuda.REngine.*;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Rserve_C {

		private RConnection conn = null;
		public int sid1;	// 메인카테고리 id
		public int sid2;	// 서브카테고리 id
		
		   public void init() throws RserveException{
			   conn = new RConnection();
		   }
		   
		   public void assign(String arg) {
			   try {
				REXP exp=conn.eval(arg);
			} catch (RserveException e) {
				e.printStackTrace();
			}
		   }
		   
		   public String[] category() throws REngineException, REXPMismatchException {
			   String[] rCate = null;
			   
			   RList l = conn.eval("cate<-getMainCategory()").asList();
			   int rows = l.at(0).length();
			   rCate = new String[rows];
			   for (int rowIdx = 0; rowIdx < rows; rowIdx++) {
					rCate[rowIdx] = l.at(0).asStrings()[rowIdx];
				}
			   return rCate;

		   }
		   
		   public String[] subCategory() throws REngineException, REXPMismatchException{
			   String[] rSubCate = null;
			   
			   assign("tcate<-cate$sid1["+sid1+"]");
			   RList l = conn.eval("subCate<-cbind(sid1=tcate,getSubCategory(sid1=tcate))").asList();
			   int rows = l.at(0).length();
			   rSubCate = new String[rows];
			   for (int rowIdx = 0; rowIdx < rows; rowIdx++) {
					rSubCate[rowIdx] = l.at(0).asStrings()[rowIdx];
				}
			   return rSubCate;
		   }
		   
		   
		   public void assignCategory(int flagCate)throws REngineException, REXPMismatchException  { //flagCate번째 카테고리를 선택		   
			    sid1 = flagCate;
		   }
		   
		   
		   public void assignSubCategory (int subFlagCate) {
			   	sid2 = subFlagCate;
			   
		   }
		   

}