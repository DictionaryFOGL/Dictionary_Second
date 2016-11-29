package server;

import java.sql.Connection;
import java.sql.Statement;

interface DatabaseConstant {
	static final Connection conn=null;
	static final Statement stat=null;
	static final String local="jdbc:mysql://127.0.0.1:3306";//local地址可改为服务器地址
	static final String usr="root";
	static final String pwd="root";
	static final String dbName="Dictionary";
	static final String sheet1="UserMessage";
	static final String sheet2="history";//暂不确定
	static final String sheet3="mailBox";
	static final String sheet4="wordsLike";
}
