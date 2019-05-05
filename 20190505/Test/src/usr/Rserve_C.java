package usr;


import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Rserve_C {
	Calendar cal = java.util.Calendar.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	String todayDate = format.format(cal.getTime());
	
	int sid1 = 6;
	int sid2 = 7;
	
    public RConnection c = null;
    
    public String[] crawling() throws REngineException, REXPMismatchException {	
    	c = new RConnection();	
    	c.eval("library(N2H4)");
    	c.setStringEncoding("utf8");
		c.eval("cate<-getMainCategory()");
		   c.eval("tcate<-cate$sid1["+sid1+"]");
		   c.eval("subCate<-cbind(sid1=tcate,getSubCategory(sid1=tcate))");
		   c.eval("tscate<-subCate$sid2["+sid2+"]");
		   c.eval("strDate<-\""+todayDate+"\"");
		   c.eval("newsData<-c()");
		   c.eval("for (sid1 in tcate){\n" + 
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
		   
		   RList l = c.eval("newsData").asList();
		   String[] press_names = l.at("press").asStrings();
		   return press_names;
	   }
    public void close() {
		if(c!=null) c.close();
    }

    
}