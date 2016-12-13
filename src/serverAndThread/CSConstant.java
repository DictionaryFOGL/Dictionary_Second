package serverAndThread;

interface CSConstant {
	static final byte SEND_CARD=0x20;
	static final byte FRIEND_ONLINE=0x21;
	static final byte ADD_FRIEND=0x11;
	static final byte LIKE=0x12;
	static final byte LIKE_CANCEL=0x13;
	static final byte INSERT_HISTORY=0x01;
	static final byte LOGIN=0x30;
	static final byte LOGIN_SUCCESS=0x22;
	static final byte LOGOUT=0x31;
	static final byte REGISTER=0x32;
	static final byte SEARCH_USER=0x15;
	static final byte REGISTER_SUCCESS=0x51;
	static final byte EXISTNAME=0x02;
	
	static final byte SEARCH_WORD=0x00;
	static final byte SEARCH_HISTORY=0x14;
	static final byte SEARCH_FRIEND=0x23;
	static final byte RECEIVE_CARD=0x10;
	static final byte REFRESH=0x50;
	static final byte REFRESH_CARDS=0x52;
	static final byte REFRESH_FRIENDS=0x53;
	static final byte USER_UPDATE=0x60;
}
