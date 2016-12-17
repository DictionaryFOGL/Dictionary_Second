package application.model.message;

import java.io.Serializable;

public class LogoutMessage extends Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2053060538000955190L;
	private boolean isLogout;
	
	public LogoutMessage(byte type){
		super(type);
		isLogout=false;
	}

	@Override
	public byte getType() {
		return type;
	}
	
	public boolean isLogout(){
		return isLogout;
	}
	
	public void Logout(){
		isLogout=true;
	}

	
}
