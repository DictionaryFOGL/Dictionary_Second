package serverAndThread;

public interface CSConstant {
	static final byte SEND_CARD=0x20;
	static final byte SEND_SUCCESS=0x00;
	static final byte RECEIVE_CARD=0x10;
	static final byte FRIEND_ONLINE=0x21;
	static final byte FRIEND_OFFLINE=0x64;
	static final byte ADD_FRIEND=0x11;
	static final byte NEW_FRIEND=0x53;
	static final byte LIKE=0x12;
	static final byte INSERT_HISTORY=0x01;
	static final byte LOGIN=0x30;
	static final byte LOGIN_SUCCESS=0x22;
	static final byte LOGIN_FAILED=0x29;
	static final byte LOGOUT=0x31;
	static final byte LOGUT_SUCCESS=0x16;
	static final byte REGISTER=0x32;
	static final byte SEARCH_USER=0x15;
	static final byte REGISTER_SUCCESS=0x51;
	static final byte EXISTNAME=0x02;
	static final byte REFRESH_CARDS=0x52;
	static final byte RESET_CARDS=0x14;
	static final byte REFRESH_FRIEND=0x50;
	static final byte DELETE_FRIEND=0x60;
	static final byte DELETE_CARD=0x08;
	static final byte PASSWORD_CHANGE=0x05;
	static final byte PASSWORD_CHANGEFAILED=0x077;
}
