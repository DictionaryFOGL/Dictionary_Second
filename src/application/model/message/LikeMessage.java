package application.model.message;

import java.io.Serializable;

public class LikeMessage extends Message  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2858603891108762929L;
	private int site;
	private String keyWord;
	private boolean likeOrNot;

	public LikeMessage(byte type, boolean likeOrNot,String keyWord,int site) {
		super(type);
		this.likeOrNot = likeOrNot;
		this.keyWord=keyWord;
		this.site=site;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public int getSite() {
		return site;
	}

	public boolean isLikeOrNot() {
		return likeOrNot;
	}

}
