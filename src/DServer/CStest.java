package DServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;

import application.model.SearchHistory;
import application.model.User;
import application.model.Word;
import application.model.WordCard;

public class CStest extends JFrame{

	private static final long serialVersionUID = 1L;
	private  JTextField SearchText;
	private  JTextPane explains;
	private  JList<String> showResults;
	private  JLabel Vag;
	private  Client client;
	String[] str;
	
	private ArrayList results;
	
	public CStest() {
	}

	public void init(){

		add(northPanel(),BorderLayout.NORTH);	
		add(westPanel(),BorderLayout.WEST);
        add(eastPanel(),BorderLayout.EAST);
		setTitle("TestUI");
		setSize(600,450);
		setVisible(true);
		//setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		client=new DictionaryFOGLClient();
		
		try {
			client.waitForAction();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//³õÊ¼»¯½çÃæ
	
	private JPanel northPanel(){
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout());
		JButton search=new JButton("test button");
		
		search.setBounds(4,50,3,4);
		search.addActionListener(new SearchListener());
		//search2.setBounds(4,50,3,4);
		
		SearchText=new JTextField(40);
		p1.add(SearchText);	
		p1.add(search);	
		
		add(p1,BorderLayout.NORTH);
		return p1;
		}
	
	private JPanel westPanel(){
		JButton search2=new JButton("test button2");
		search2.addActionListener(new SearchListener2());
		JPanel pm=new JPanel(new BorderLayout());
		Vag=new JLabel(" ");
		Vag.setFont(new Font("Ó×Ô²",Font.BOLD,13));
		pm.add(Vag,BorderLayout.NORTH);	
		JScrollPane p2=new JScrollPane();
		p2.setLayout(new ScrollPaneLayout());
        p2.setPreferredSize(new Dimension(160,70));
		String[] str={" "};
		showResults=new JList<String>();
		showResults.setVisibleRowCount(2);
		showResults.setListData(str);
		p2.setViewportView(showResults);
		pm.add(p2,BorderLayout.CENTER);
		pm.add(search2);
		return pm;
	}
	
	private JPanel eastPanel(){
		JPanel pe=new JPanel(new BorderLayout());
		explains=new JTextPane();
		explains.setFont(new Font("¿¬Ìå",Font.BOLD,16));
		explains.setText(" ");
		explains.setPreferredSize(new Dimension(400,70));
		pe.add(explains,BorderLayout.EAST);
		return pe;
	}
	

	
 	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			User user=new User("testuser","123456789", null);
			user.setGender('M');
			user.setRegisterDate(new Date());
			try {
				//client.register(user);
				//client.login("admin", "123456");
				//client.searchUser("adm");
				client.sendCard("testuser", new WordCard(new Word("Dictionary","×Öµä"),client.getUser().getUserName(),"blablabla",client.getUser().getUserID(),new Date(),1));
				//client.refreshCards();
				/*
				client.like(Website.Baidu);
				client.like(Website.Bing);
				client.like(Website.Bing);
				client.like(Website.Bing);
				client.like(Website.Youdao);
				explains.setText("Baidu:"+client.getLike().getBaidu()+"Bing:"+client.getLike().getBing()+"YouDao:"+client.getLike().getYoudao());
			
				client.addFriend("admin");
				client.addFriend("test user");
				while(!client.isFriendAdded()){
					explains.setText("friend added");
				}
				
				client.refreshFriendList();
				*/
				//client.insertHistory("spelllllllll");
				//client.insertHistory("wooooooooo");
				//while(!client.isHistoryRecorded()){
				//	explains.setText("recorded!");
				//}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				client.refreshHistory();
				String str="";
				ArrayList<SearchHistory>Result=client.getHistory();
				if(Result!=null){
					for(int i=0;i<Result.size();i++){
						str+=Result.get(i).getKeyWord()+"\n";
					}
					explains.setText(str);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			}
			
			
		
	}
	

 	class SearchListener2 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			User user=new User("biur","123423", (new Date()));
			user.setGender('M');
			user.setRegisterDate(new Date());
			try {
				client.login("biur","123423");
				//client.register(user);
				//client.logout();
			   // client.login("admin", "error");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(client.getUser()!=null)
			explains.setText(client.getUser().getUserName()+" "+client.getUser().getGender()+client.getUser().getRegisterDate()+" login");
			else{
				explains.setText("not login!");
			}
			if(client.isCardSended()){
				try {
					ArrayList<WordCard>Result=client.getCards();
					if(Result!=null){
						String str=" ";
						for(int i=0;i<Result.size();i++){
							str+=Result.get(i).getSenderName()+Result.get(i).getWord()+"\n";
						}
						explains.setText(str);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}//ËÑË÷°´Å¥¼àÌý
	
	class ResultsListener implements ListSelectionListener{
		private int  index=0;
		public void valueChanged(ListSelectionEvent e){
			
		}	
	}//·­Òë½á¹û¿ò¼àÌý
	
	public static void main(String[] args){
		CStest DictionaryGUI=new CStest();
		DictionaryGUI.init();
	}

}


