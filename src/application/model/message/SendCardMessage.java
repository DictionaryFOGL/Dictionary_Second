package application.model.message;

import java.util.ArrayList;

import application.model.*;

public class SendCardMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3920663053182906949L;
	private String receiver;
	private WordCard card;
	

	public SendCardMessage(byte type, String receiver,WordCard card) {
		super(type);
		this.receiver=receiver;
		this.card=card;
	}

	public SendCardMessage(byte type, WordCard card) {
		super(type);
		this.card = card;
	}

	@Override
	public byte getType() {
		return type;
	}

	public String getReceiverName() {
		return receiver;
	}

	public WordCard getCard() {
		return card;
	}
}
