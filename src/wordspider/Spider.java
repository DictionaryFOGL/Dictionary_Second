package wordspider;

import java.util.ArrayList;

abstract class Spider {
	protected String keyWord;
	protected String url;
	protected String regex;
	protected ArrayList<String> explanations;
	public Spider(String keyWord){
		this.keyWord=keyWord;
		explanations=new ArrayList<String>();
	}
	protected abstract void init();
	
	public abstract ArrayList<String> getResult();
	
	public abstract boolean isfound();

}
