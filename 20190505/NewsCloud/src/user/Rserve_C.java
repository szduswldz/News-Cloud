package user;

import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Rserve_C {
	 	public REXP x = null;
	    public RConnection conn = null;
		public int sid1;
		public int sid2;
		public String press1;
		public String press2;
		public String cate;
		public String subCate;
		
		Calendar cal = java.util.Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String todayDate = format.format(cal.getTime());
		
		
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
			   conn.eval("tscate<-subCate$sid2["+sid2+"]");
			   String[] rSubCate = l.at("sub_cate_name").asStrings();
			   return rSubCate;
		   }
		   
		   public void assignCategory(int flagCate)throws REngineException, REXPMismatchException  { 		
			   sid1 = flagCate;
		   }		   
		   
		   public void assignSubCategory(int subFlagCate)throws REngineException, REXPMismatchException{
			   sid2 = subFlagCate;			  		   
		   }
		   
		   public String[] crawling() throws REngineException, REXPMismatchException {	
			   conn = new RConnection();
			   conn.eval("library(N2H4)");
			   conn.eval("cate<-getMainCategory()");
			   conn.eval("tcate<-cate$sid1["+sid1+"]");
			   conn.eval("subCate<-cbind(sid1=tcate,getSubCategory(sid1=tcate))");
			   conn.eval("tscate<-subCate$sid2["+sid2+"]");
			   conn.setStringEncoding("utf8");		   
			   conn.eval("strDate<-\""+todayDate+"\"");
			   conn.eval("newsData<-c()");
			   conn.eval("for (sid1 in tcate){\n" + 
				   		"for (sid2 in tscate){\n" + 
				   		"pageUrl<-paste0(\"http://news.naver.com/main/list.nhn?sid2=\",sid2,\"&sid1=\",sid1,\"&mid=shm&mode=LS2D&date=\",strDate)\n" + 
				   		"max<-getMaxPageNum(pageUrl)\n" + 
				   		"for (pageNum in 1:max){\n" + 
				   		"pageUrl<-paste0(\"http://news.naver.com/main/list.nhn?sid2=\",sid2,\"&sid1=\",sid1,\"&mid=shm&mode=LS2D&date=\",strDate,\"&page=\",pageNum)\n" + 
				   		"newsList<-getUrlListByCategory(pageUrl)\n" + 
				   		"for (newslink in newsList$links){\n" + 
				   		"tryi<-0\n" + 
				   		"tem<-try(getContent(newslink), silent = TRUE)\n" + 
				   		"while(tryi<=5&&class(tem)==\"try-error\"){\n" + 
				   		"tem<-try(getContent(newslink), silent = TRUE)\n" + 
				   		"tryi<-tryi+1\n" + 
				   		"}\n" + 
				   		"newsData<-rbind(newsData, tem)\n" + 
				   		"}}}}\n");      
				   
				   RList l = conn.eval("newsData").asList();
				   String[] press_names = l.at("press").asStrings();
				   return press_names;

		   }
		   
		   public String getCategory() throws REngineException, REXPMismatchException {
			   RList l = conn.eval("cate").asList();
			   String cate = l.at("cate_name["+sid1+"]").asString();
			   return cate;
		   }
		   
		   public String getSubCategory() throws REngineException, REXPMismatchException{
			   RList l = conn.eval("subCate").asList();
			   String subCate = l.at("sub_cate_name["+sid1+"]").asString();
			   return subCate;
		   }
		   public String[] getArticleSum() throws REngineException, REXPMismatchException {
			   RList l = conn.eval("newsData").asList();
			   String[] article = l.at("body").asStrings();
			   return article;	  
		   }
		   public String getDescription(String str) {
			   return str;
		   }
		      
		   public void assignPress(String p1, String p2) {
			   press1 = p1;
			   press2 = p2;
		   }
		   
		   public void execute(String category, String subCategory) {
			  cate = category;
			  subCate = subCategory;
		   }
		 
   

}