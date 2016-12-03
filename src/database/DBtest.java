package database;

import java.sql.SQLException;
import java.util.ArrayList;

import application.model.WordCard;

public class DBtest {
	public static void main(String args[]){
		Database DB=new DictionaryDB();
		DB.connect();

		//try {
			//User u=DB.verify("dreamgazer", "307726302");
			//java.sql.Date dt=new java.sql.Date(new java.util.Date().getTime());
			//DB.register("dreamgazer","307726302",(java.sql.Date) dt,'m');
			//DB.register("FOG","3412353444",(java.sql.Date) dt,'f');
		/*
		if(u!=null){
			System.out.println(u.getUserName());
			System.out.println(u.getGender());
		};
		*/

		//DB.sendCard(new WordCard(new Word("e...","ßÀ¡£¡£¡£"),"dreamgazer", "blablabla", 1, new Date(),1), 1);
		//DB.search("Dictionary",3);
		//DB.SearchHistory(3);
			/*
		ArrayList<WordCard> cards=DB.getCard(3);
		for(WordCard s:cards){
			System.out.println(((WordCard)s).getSaySomething());
			System.out.println(((WordCard)s).getSenderName());
			System.out.println(((WordCard)s).getWord().getWords());
			
		}

			DB.like(2,0);
			DB.like(2,2);
			DB.like(2,2);
			DB.like(2,2);
			DB.like(2,1);
			DB.like(1,1);
			System.out.println("baidu:"+DB.getWordsLike().getBaidu()+"bing:"+DB.getWordsLike().getBing()+"youdao:"+DB.getWordsLike().getYoudao());
			*/
			
	//} catch (SQLException e) {
		// TODO Auto-generated catch block
	//	e.printStackTrace();
	//}
}
}
