package application.model.message;

import java.io.Serializable;
import java.util.ArrayList;


public class Message implements Serializable{
	protected byte type;
	
	public Message(byte type){
		this.type=type;
	}
	
	public byte getType(){
		return type;
	};
}
