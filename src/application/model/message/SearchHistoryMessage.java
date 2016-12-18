package application.model.message;

import java.io.Serializable;
import java.util.ArrayList;

import application.model.SearchHistory;
import application.model.message.Message;

public class SearchHistoryMessage extends Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3282267073985226044L;
	private ArrayList<SearchHistory> history;
	public SearchHistoryMessage(byte type, ArrayList<SearchHistory> history) {
		super(type);
		this.history = history;
	}
	public ArrayList<SearchHistory> getHistory() {
		return history;
	}
}
