package application.model;

import java.io.Serializable;

public class WordsLike implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 310111043961652664L;
	
	private int baidu;
	private int bing;
	private int youdao;
	
	public WordsLike(int baidu,int bing,int youdao) {
		this.baidu=baidu;
		this.bing=bing;
		this.youdao=youdao;
	}
	public int getBaidu() {
		return baidu;
	}
	public int getBing() {
		return bing;
	}
	public int getYoudao() {
		return youdao;
	}
}
