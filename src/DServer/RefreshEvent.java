package DServer;

import java.util.EventObject;

class RefreshEvent  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8261846266143702024L;

	private static boolean isRefreshed=false;
	private static boolean isFriendRefreshed=false;
	private static boolean isCardRefreshed=false;
	private static boolean isUserStateRefreshed=false;
	
	public static boolean isFriendRefreshed() {
		return isFriendRefreshed;
	}
	public static void setFriendRefreshed(boolean Refreshed) {
		isFriendRefreshed = Refreshed;
	}
	public static boolean isCardRefreshed() {
		return isCardRefreshed;
	}
	public static void setCardRefreshed(boolean Refreshed) {
		isCardRefreshed = Refreshed;
	}
	public static boolean isUserStateRefreshed() {
		return isUserStateRefreshed;
	}
	public static void setUserStateRefreshed(boolean Refreshed) {
		isUserStateRefreshed = Refreshed;
	}

	public static boolean isRefreshed() {
		return isRefreshed;
	}

	public static void setRefreshed(boolean Refreshed) {
		isRefreshed = Refreshed;
	}

}
