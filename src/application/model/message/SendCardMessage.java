package application.model.message;

import application.model.*;

public class SendCardMessage extends Message{
	private String receiverName;
	private WordCard card;
	

	public SendCardMessage(byte type, String receiverName,WordCard card) {
		super(type);
		this.receiverName=receiverName;
		this.setCard(card);
	}

	@Override
	public byte getType() {
		return type;
	}


	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public WordCard getCard() {
		return card;
	}

	public void setCard(WordCard card) {
		this.card = card;
	}



}
