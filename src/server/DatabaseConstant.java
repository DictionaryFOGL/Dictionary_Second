package server;

import java.sql.Connection;
import java.sql.Statement;

interface DatabaseConstant {
	static final Connection conn=null;
	static final Statement stat=null;
	static final String local="jdbc:mysql://127.0.0.1:3306";//local��ַ�ɸ�Ϊ��������ַ
	static final String usr="root";
	static final String pwd="root";
	static final String dbName="Dictionary";
	static final String sheet1="UserMessage";
	static final String sheet2="history";//�ݲ�ȷ��
	static final String sheet3="mailBox";
	static final String sheet4="wordsLike";
}
