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
	
	public WordsLike() {
		this.baidu=0;
		this.bing=0;
		this.youdao=0;
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
