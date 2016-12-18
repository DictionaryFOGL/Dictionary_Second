package application.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ProcessTimeFormat {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String[] month={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	public static String timeStr(Date sqldate) {
		String mark;
		String local=sdf.format(sqldate);
		local=local.substring(11, 16);
		int hour=Integer.parseInt(local.substring(0,2));
		if(hour > 12) {
			hour-=12;
			mark="PM";
		} else {
			mark="AM";
		}
		int min=Integer.parseInt(local.substring(3,5));
		return hour+":"+min+" "+mark;
	}
	
	public static String dateStr(Date sqldate) {
		String local=sdf.format(sqldate);
		local=local.substring(0, 10);
		return null;
		//TODO
	}
	
	public static String standard(long mills) {
		return sdf.format(mills);
	}
}
