package application.model.message;

import java.io.Serializable;


public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7580296796931377105L;
	protected byte type;
	
	public Message(byte type){
		this.type=type;
	}
	
	public byte getType(){
		return type;
	};
}
