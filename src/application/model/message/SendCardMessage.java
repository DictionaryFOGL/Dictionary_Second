package application.model.message;

import java.util.ArrayList;

import application.model.*;

public class SendCardMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3920663053182906949L;
	private ArrayList<String> receivers;
	private WordCard card;
	

	public SendCardMessage(byte type, ArrayList<String> receivers,WordCard card) {
		super(type);
		this.receivers=receivers;
		this.card=card;
	}

	@Override
	public byte getType() {
		return type;
	}

	public ArrayList<String> getReceiverName() {
		return receivers;
	}

	public WordCard getCard() {
		return card;
	}
}
