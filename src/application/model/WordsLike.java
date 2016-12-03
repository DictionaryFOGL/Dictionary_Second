package application.model;

import java.io.Serializable;

public class WordsLike implements Serializable{
	private int baidu;
	private int bing;
	private int youdao;
	
	public WordsLike(int baidu,int bing,int youdao){
		setBaidu(baidu);
		setBing(bing);
		setYoudao(youdao);
	}
	public int getBaidu() {
		return baidu;
	}
	public void setBaidu(int baidu) {
		this.baidu = baidu;
	}
	public int getBing() {
		return bing;
	}
	public void setBing(int bing) {
		this.bing = bing;
	}
	public int getYoudao() {
		return youdao;
	}
	public void setYoudao(int youdao) {
		this.youdao = youdao;
	}

}
