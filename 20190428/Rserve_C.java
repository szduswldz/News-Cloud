package user;

import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Rserve_C {
	 	public REXP x = null;
	    public RConnection conn = null;
		public int sid1;	// 메인카테고리 id
		public int sid2;	// 서브카테고리 id
		public String press1;
		public String press2;
		public String cate;
		public String subCate;
		
		   public void init() throws RserveException{
			   conn = new RConnection();	 
		   } 
		    
		   public void assign(String arg) throws RserveException {			 
			   conn.eval(arg);
			   conn.eval("library(N2H4)");
			   conn.setStringEncoding("utf8");
		   }
		   
		   public String[] category() throws REngineException, REXPMismatchException {
			   RList l = conn.eval("cate<-getMainCategory()").asList();
			   String[] rCate = l.at("cate_name").asStrings();
			   return rCate;
		   }
		   
		   public String[] subCategory() throws REngineException, REXPMismatchException{
			   conn.eval("tcate<-cate$sid1["+sid1+"]");
			   RList l = conn.eval("subCate<-cbind(sid1=tcate,getSubCategory(sid1=tcate))").asList();
			   String[] rSubCate = l.at("sub_cate_name").asStrings();
			   return rSubCate;
		   }
		   
		   public void assignCategory(int flagCate)throws REngineException, REXPMismatchException  { 		
			   sid1 = flagCate;
		   }		   
		   
		   public void assignSubCategory(int subFlagCate) {
			   sid2 = subFlagCate;			   
		   }
		   
//		   public String[] crawling() throws REngineException, REXPMismatchException {	   
//			   //미완성	   
//			   RList l = conn.eval("cate<-getMainCategory()").asList();
//			   String[] press_names = l.at("cate_name").asStrings();
//			   return press_names;
//
//		   }
		   
		   public String getCategory() throws REngineException, REXPMismatchException {
			   RList l = conn.eval("cate<-getMainCategory()").asList();
			   String cate = l.at("cate_name["+sid1+"]").asString();
			   return cate;
		   }
		   
		   public String getSubCategory() throws REngineException, REXPMismatchException{
			   conn.eval("tcate<-cate$sid1["+sid1+"]");
			   RList l = conn.eval("subCate<-cbind(sid1=tcate,getSubCategory(sid1=tcate))").asList();
			   String subCate = l.at("sub_cate_name["+sid1+"]").asString();
			   return subCate;
		   }
		   public String getDescription(String str) {
			   if(str == "summary1") {
				   return cate;
			   } else {
				   return subCate;
			   }
		   }
		      
		   public void assignPress(String p1, String p2) {
			   press1 = p1;
			   press2 = p2;
		   }
		   public void execute(String category, String subCategory) {
			  cate = category;
			  subCate = subCategory;
		   }
		   
//		   public String[] getArticleSum() {
//		   //미완성
//		   String[] article = null;
//		   return article;
//	   }
//	
		  
   

}