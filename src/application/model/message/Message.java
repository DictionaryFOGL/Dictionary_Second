package application.model.message;

import java.io.Serializable;


public class Message implements Serializable{
	protected byte type;
	
	Message(byte type){
		this.type=type;
	}
	
	public byte getType(){
		return type;
	};
}
