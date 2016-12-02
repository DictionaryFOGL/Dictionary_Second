package application.model.message;

import java.util.ArrayList;

public class ResultMessage extends Message {
	
	private ArrayList Results;

	ResultMessage(byte type,ArrayList results) {
		super(type);
		setResults(results);
	}

	public ArrayList getResults() {
		return Results;
	}

	public void setResults(ArrayList results) {
		Results = results;
	}

}
