package usr;
import java.text.SimpleDateFormat;
import java.util.*;

public class calendar {

	public static void main(String[] args) {
		Calendar cal = java.util.Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(cal.getTime());
		System.out.println(date);
		  
	}

}
