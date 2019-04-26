package user;

import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Rserve_C {
    public REXP x = null;
    public RConnection conn = null;
   public int sid1;   // 메인카테고리 id
   public int sid2;   // 서브카테고리 id
   public int pre1; //언론사1
   public int pre2;//언론사2
   
      public void init() throws RserveException{
         conn = new RConnection();    
      } 
       
      public void assign(String arg) throws RserveException {          
         conn.eval(arg);
         conn.eval("library(N2H4)");
         conn.setStringEncoding("utf8"); //카테고리 
      }
      
      /*public void assignPress(String a, string b) throws RserveException {          
          conn.eval(a);
          conn.eval(b);
          conn.eval("library(N2H4)");
          conn.setStringEncoding("utf8");
       } //언론사...?
      
      
     /* public void execute(String arg) throws RserveException {          
          conn.eval(arg);
          conn.eval("library(N2H4)");
          conn.setStringEncoding("utf8");
       }*/
      
    
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
      
      /* public String[] press_names() throws REngineException, REXPMismatchException {
      RList l = conn.eval("cate<-getMainCategory()").asList();
      String[] rCate = l.at("cate_name").asStrings();
      return rCate;
   }   //press1 press2, pressFlag 배열 다 지정?*/
  
      
      /*public void assignPress(String press1,String press2 )throws REngineException, REXPMismatchException  {       
          pre1 = press1;
          pre2 = press2;
       }         
       
      public String[] crawling() throws REngineException, REXPMismatchException {      
         //미완성      
         RList l = conn.eval("cate<-getMainCategory()").asList();
         String[] press_names = l.at("cate_name").asStrings();
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
      
     /* public String execute() throws REngineException, REXPMismatchException{
          return getCategory();
          return getSubCategory();
       }*/
      
      
      
      
      
      public String getDescription(String summary1, String summary2 ) {
         //미완성
         return str;
      }
      
      public String[] getArticleSum() {
         //미완성
         String[] article = null;
         return article;
      }


}