package application.model.message;

public class LogoutMessage extends Message{
	
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
