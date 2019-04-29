package user;

import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import java.util.Calendar;

public class Rserve_C {
	 	public REXP x = null;
	    public RConnection conn = null;
		public int tcate;	// 메인카테고리 id
		public int tscate;	// 서브카테고리 id
		public int sid1;
		public int sid2;
		public String press1;
		public String press2;
		public String cate;
		public String subCate;
		
		Calendar cal = Calendar.getInstance();
		  int intYear = cal.YEAR;
		  int intMonth = cal.MONTH+1;
		  int intDay = cal.DAY_OF_MONTH;
		  String strYear = String.valueOf(intYear);
		  String strMonth = String.valueOf(intMonth);
		  String strDay = String.valueOf(intDay);   
		  String todayDate = strYear+strMonth+strDay;
		
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
			   conn.eval("cate<-getMainCategory()");
			   RList l = conn.eval("tcate<-cate$sid1["+flagCate+"]").asList();
			   tcate = l.at("tcate").asInteger();
			   
		   }		   
		   
		   public void assignSubCategory(int subFlagCate)throws REngineException, REXPMismatchException{
			   sid2 = subFlagCate;
			   conn.eval("tcate<-cate$sid1["+sid1+"]");
			   conn.eval("subCate<-cbind(sid1=tcate,getSubCategory(sid1=tcate))");
			   RList l = conn.eval("tscate<-subCate$sid2["+subFlagCate+"]").asList();
			   tscate = l.at("tscate").asInteger();		   
		   }
		   
		   public String[] crawling() throws REngineException, REXPMismatchException {	   
	   
			   conn.eval("newsData<-c()"
			   		+ "for (sid1 in "+tcate+"){\r\n" + 
			   		"    for (sid2 in "+tscate+"){\r\n" + 
			   		"      pageUrl<-paste0(\"http://news.naver.com/main/list.nhn?sid2=\",sid2,\"&sid1=\",sid1,\"&mid=shm&mode=LS2D&date=\","+todayDate+")\r\n" + 
			   		"      max<-getMaxPageNum(pageUrl)     \r\n" + 
			   		"      for (pageNum in 1:max){\r\n" + 
			   		"        pageUrl<-paste0(\"http://news.naver.com/main/list.nhn?sid2=\",sid2,\"&sid1=\",sid1,\"&mid=shm&mode=LS2D&date=\","+todayDate+",\"&page=\",pageNum)\r\n" + 
			   		"        newsList<-getUrlListByCategory(pageUrl)\r\n" + 
			   		"        for (newslink in newsList$links){\r\n" + 
			   		"          tryi<-0\r\n" + 
			   		"          tem<-try(getContent(newslink), silent = TRUE)\r\n" + 
			   		"          while(tryi<=5&&class(tem)==\"try-error\"){\r\n" + 
			   		"            tem<-try(getContent(newslink), silent = TRUE)\r\n" + 
			   		"            tryi<-tryi+1            \r\n" + 
			   		"          }\r\n" + 
			   		"        newsData<-rbind(newsData, tem)\r\n" + 
			   		"       }\r\n" + 
			   		"     } \r\n" + 
			   		"   }\r\n" + 
			   		" }\r\n" + 
			   		"");   
			   RList l = conn.eval("newsData").asList();
			   String[] press_names = l.at("press").asStrings();
			   return press_names;

		   }
		   
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
		   
		   public String[] getArticleSum() throws REngineException, REXPMismatchException {
			   RList l = conn.eval("newsData").asList();
			   String[] article = l.at("body").asStrings();
			   return article;
			  
	   }
	
		  
   

}
