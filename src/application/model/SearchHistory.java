package application.model;

public class SearchHistory{
	private String keyWord;
	private int userId;
	private int likeBaidu;
	private int likeBing;
	private int likeYouDao;
	
	public SearchHistory(String keyWord,int userId,int likeBaidu,int likeBing,int likeYouDao){
		this.keyWord=keyWord;
		this.userId=userId;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
