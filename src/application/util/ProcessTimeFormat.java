package application.util;

import java.util.Date;
import java.text.SimpleDateFormat;

public class ProcessTimeFormat {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String[] month={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	private static String[] day={"1st","2nd","3rd"};
	public static String timeStr(long time) {
		Date sqldate=new Date(time);
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
		String h=(hour < 10) ? ("0"+hour) :(""+hour);
		String m=(min < 10) ? ("0"+min) : (""+min);
		return h+":"+m+" "+mark;
	}
	
	public static String dateStr(long time) {
		Date sqldate=new Date(time);
		String local=sdf.format(sqldate);
		local=local.substring(0, 10);
		String year=local.substring(0,4);
		String mon=month[Integer.parseInt(local.substring(5,7))-1];
		int Day=Integer.parseInt(local.substring(8,10));
		String d="th";
		if(Day < 4) d=day[Day];
		else d=Day+d;
		return mon+" "+d+" -- "+year;
	}
	
	public static String standard(long mills) {
		return sdf.format(mills);
	}
	public static void main(String[] args) {
		System.out.println(dateStr(System.currentTimeMillis()));
	}
}
