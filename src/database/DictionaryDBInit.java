package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DictionaryDBInit {
	private static Connection conn=null;
	private static Statement stat=null;
	private static String local="jdbc:mysql://127.0.0.1:3306";//local地址可改为服务器地址
	private static String usr="root";
	private static String pwd="24601";
	private static String dbName="Dictionary";
	private static String sheet1="UserMessage";
	private static String sheet2="history";
	private static String sheet3="mailBox";
	private static String sheet4="wordsLike";
	private static String sheet5="relationship";
	
	
	public static void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		}
		try {
			System.out.println(local+usr+pwd);
			conn=DriverManager.getConnection(local,usr,pwd);
			stat=conn.createStatement();
			System.out.println("Connection get!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createDatabaseAndList() {
		try {
			stat.execute("CREATE DATABASE "+dbName);
			stat.executeQuery("USE "+dbName);
			conn=stat.getConnection();
			stat=conn.createStatement();
			stat.execute("CREATE TABLE "+sheet1+"(ID int(6) PRIMARY KEY AUTO_INCREMENT,"
					+ "name varchar(11) NOT NULL,"
					+ "gender char(1) NOT NULL default 'm',"
					+ "password varchar(16) NOT NULL default '123456',"
					+ "pwdmd5 char(32) NOT NULL,"
					+ "signTime datetime NOT NULL)");
			System.out.println(sheet1 +"created!");
			stat.execute("CREATE TABLE "+sheet2+"(historyNum int(5) PRIMARY KEY AUTO_INCREMENT,"
					+ "userId int(6) NOT NULL,"
					+ "keyWord varchar(32) NOT NULL,"
					+ "baidu int(1) NOT NULL default 0,"
					+ "bing int(1) NOT NULL default 0,"
					+ "youdao int(1) NOT NULL default 0,"
					+ "searchTime datetime NOT NULL)");
			System.out.println(sheet2 +"created!");
			stat.execute("CREATE TABLE "+sheet3+"(senderID int(6) NOT NULL,"
					+ "receiverID int(6) NOT NULL,"
					+ "words varchar(64),"
					+ "url int(1) NOT NULL,"
					+ "time datetime NOT NULL,"
					+ "say varchar(10))");//TODO 暂定长度
			System.out.println(sheet3 +"created!");
			stat.execute("CREATE TABLE "+sheet4+"(ID int(6) PRIMARY KEY,"
					+ "baidu int(2) NOT NULL,"//TODO 暂定长度
					+ "bing int(2) NOT NULL,"
					+ "youdao int(2) NOT NULL)");
			System.out.println(sheet4 +"created!");
			stat.execute("CREATE TABLE "+sheet5+"(ID1 int(6) NOT NULL,"
					+ "ID2 int(6) NOT NULL)");
			System.out.println(sheet5 +"created!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertTestData() {
		try {
			Date d=new Date(2012, 3, 4, 5, 6, 7);
			d.setYear(111);//不明bug的时间-19正确
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			stat.executeUpdate("INSERT INTO "+sheet1+"(name,gender,password,pwdmd5,signtime) "
					+ "values('admin','f',123456,'error','"+sdf.format(d)+"')");
			stat.executeUpdate("INSERT INTO "+sheet3+"(senderID,receiverID,words,url,time,say) "
					+ "values(1,3,\"dictionary\",\"www.baidu.com\",'"+sdf.format(d)+"',\"blablabla\")");
			System.out.println("insert!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void resetAll() {
		try {
			stat.execute("DROP DATABASE "+dbName);
			closeAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeAll() {
		try {
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		connect();

		createDatabaseAndList();
		stat.executeQuery("USE "+dbName);
		insertTestData();
		closeAll();
	}
}
