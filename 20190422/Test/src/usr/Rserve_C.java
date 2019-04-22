package usr;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;


public class Rserve_C {

    public REXP x = null;
    public RConnection c = null;
    public String retStr = "";

    public String returnRClass() throws RserveException, REXPMismatchException{

            c = new RConnection();
            x = c.eval("1+2");
            retStr = x.asString();
        
        return retStr;
    }
    public void close() {
		if(c!=null) c.close();
    }
 }


