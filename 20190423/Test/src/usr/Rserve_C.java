
package usr;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;


public class Rserve_C {

    public REXP x = null;
    public RConnection c = null;
    public String retStr = "";

    public String[] returnRClass() throws RserveException, REXPMismatchException{

            c = new RConnection();
            c.setStringEncoding("utf8");
            c.eval("library(N2H4)");
            RList l = c.eval("cate<-getMainCategory()").asList();
			String[] rCate = l.at("cate_name").asStrings();
            
           return rCate;
       
    }
    public void close() {
		if(c!=null) c.close();
    }
 }