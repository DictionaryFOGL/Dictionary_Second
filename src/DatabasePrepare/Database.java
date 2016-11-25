package DatabasePrepare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database {
	private static Connection conn=null;
	private static Statement stat=null;
	private static String local="jdbc:mysql://127.0.0.1:3306";//local��ַ�ɸ�Ϊ��������ַ
	private static String usr="root";
	private static String pwd="root";
	private static String dbName="Dictionary";
	private static String sheet1="UserMessage";
	private static String sheet2="history";//�ݲ�ȷ��
	private static String sheet3="mailBox";
	private static String sheet4="wordsLike";
	
	public static void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		}
		try {
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
					+ "password varchar(16) NOT NULL,"
					+ "pwdmd5 char(32) NOT NULL,"
					+ "signTime datetime NOT NULL)");
			System.out.println("sheet-1 created!");
			stat.execute("CREATE TABLE "+sheet3+"(senderID int(6) NOT NULL,"
					+ "receiverID int(6) NOT NULL,"
					+ "words varchar(64),"
					+ "url char(1) NOT NULL,"
					+ "time datetime NOT NULL,"
					+ "say varchar(10))");//TODO �ݶ�����
			System.out.println("sheet-3 created!");
			stat.execute("CREATE TABLE "+sheet4+"(ID int(6) PRIMARY KEY,"
					+ "baidu int(2) NOT NULL,"//TODO �ݶ�����
					+ "bing int(2) NOT NULL,"
					+ "youdao int(2) NOT NULL)");
			System.out.println("sheet-4 created!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertTestData() {
		try {
			Date d=new Date(2012, 3, 4, 5, 6, 7);
			d.setYear(111);//����bug��ʱ��-19��ȷ
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			stat.executeUpdate("INSERT INTO "+sheet1+"(name,gender,password,pwdmd5,signtime) "
					+ "values('admin','f',123456,'error','"+sdf.format(d)+"')");
			stat.executeUpdate("INSERT INTO "+sheet3+"(senderID,receiverID,url,time) "
					+ "values(1,3,'b','"+sdf.format(d)+"')");
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
		stat.executeQuery("USE "+dbName);
		createDatabaseAndList();
		insertTestData();
		closeAll();
	}
}
