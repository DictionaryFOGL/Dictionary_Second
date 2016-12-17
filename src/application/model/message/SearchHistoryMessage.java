package application.model.message;

import java.util.ArrayList;

import application.model.SearchHistory;
import application.model.message.Message;

public class SearchHistoryMessage extends Message{
	private ArrayList<SearchHistory> history;
	public SearchHistoryMessage(byte type, ArrayList<SearchHistory> history) {
		super(type);
		this.history = history;
	}
	public ArrayList<SearchHistory> getHistory() {
		return history;
	}
}
