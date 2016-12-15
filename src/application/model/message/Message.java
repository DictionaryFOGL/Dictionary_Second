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
	
	public static void main(String[] args) {
		ResultMessage r=new ResultMessage((byte) 0x20, new ArrayList<>());
		ResultMessage rrr=new ResultMessage((byte) 0x20, new ArrayList<>());
		System.out.println(r.hashCode()+" "+rrr.hashCode());
		Message m=r;
		ResultMessage rr=(ResultMessage) m;
		System.out.println(r.hashCode()+" "+m.hashCode()+" "+rr.hashCode()+" "+r.getResults().hashCode());
		r.getResults().add(1);
		System.out.println(r.getResults().hashCode()+" "+rr.getResults().hashCode());
	}
}
