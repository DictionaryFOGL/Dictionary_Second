package application.model;

import java.io.Serializable;

public class SearchHistory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2807371741182291669L;
	private String keyWord;
	private int likeBaidu;
	private int likeBing;
	private int likeYouDao;
	
	public SearchHistory(String keyWord,int likeBaidu,int likeBing,int likeYouDao){
		this.keyWord=keyWord;
		this.likeBaidu=likeBaidu;
		this.likeBing=likeBing;
		this.likeYouDao=likeYouDao;
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getLikeBaidu() {
		return likeBaidu;
	}
	public void setLikeBaidu(int likeBaidu) {
		this.likeBaidu = likeBaidu;
	}
	public int getLikeBing() {
		return likeBing;
	}
	public void setLikeBing(int likeBing) {
		this.likeBing = likeBing;
	}
	public int getLikeYouDao() {
		return likeYouDao;
	}
	public void setLikeYouDao(int likeYouDao) {
		this.likeYouDao = likeYouDao;
	}
	

}
