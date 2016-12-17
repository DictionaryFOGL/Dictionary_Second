package application.model.message;

import java.io.Serializable;
import java.util.ArrayList;

import application.model.User;

public class ResultMessage extends Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6145673003052551474L;
	private ArrayList<String> Results;
	private User target;

	public ResultMessage(byte type,ArrayList<String> results,User target) {
		super(type);
		this.Results=results;
		this.target=target;
	}

	public ArrayList<String> getResults() {
		return Results;
	}
	
	public User getTarget() {
		return target;
	}
}
