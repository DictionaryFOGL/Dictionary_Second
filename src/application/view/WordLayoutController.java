package application.view;

import java.io.File;
import java.net.MalformedURLException;

import application.Main;
import application.model.Word;
import application.util.BaiduSpider;
import application.util.BingSpider;
import application.util.Controller;
import application.util.YoudaoSpider;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WordLayoutController implements Controller{
	private Main mainApp;
	private String searchItem;
	private int count;
	
	private Word youdaoWord=null;
	private Word baiduWord=null;
	private Word bingWord=null;
	private YoudaoSpider youdao;
	private BingSpider bing;
	private BaiduSpider baidu;
	private Image likeDefault;
	private Image likeYes;
	private Image sendDefault;
	private Image sendYes;
	
	@FXML
	private ListView<String> result;
	@FXML
	private BorderPane result1;
	@FXML
	private BorderPane result2;
	@FXML
	private BorderPane result3;
	@FXML
	private VBox box1;
	@FXML
	private VBox box2;
	@FXML
	private VBox box3;
	@FXML
	private Label searchCon;
	@FXML
	private Label comefrom1;
	@FXML
	private Label comefrom2;
	@FXML
	private Label comefrom3;
	@FXML
	private Text trans1;
	@FXML
	private Text trans2;
	@FXML
	private Text trans3;
	@FXML
	private Text notFound1;
	@FXML
	private Text notFound2;
	@FXML
	private Text notFound3;
	@FXML
	private ImageView original1;
	@FXML
	private ImageView original2;
	@FXML
	private ImageView original3;
	@FXML
	private ImageView copy1;
	@FXML
	private ImageView copy2;
	@FXML
	private ImageView copy3;
	@FXML
	private ImageView save1;
	@FXML
	private ImageView save2;
	@FXML
	private ImageView save3;
	@FXML
	private ImageView like1;
	@FXML
	private ImageView like2;
	@FXML
	private ImageView like3;
	@FXML
	private ImageView send1;
	@FXML
	private ImageView send2;
	@FXML
	private ImageView send3;
	@FXML
	private CheckBox bingGet;
	@FXML
	private CheckBox baiduGet;
	@FXML
	private CheckBox youdaoGet;
	//TODO δ�ҵ���fxml����
	
	public WordLayoutController() {
		youdao = new YoudaoSpider();
		bing = new BingSpider();
		baidu = new BaiduSpider();
	}

	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
		try {
			likeDefault=new Image(new File("resources/_0007_SearchWord_prove_clicked.png").toURI().toURL().toString());
			likeYes=new Image(new File("resources/_0008_SearchWord_prove.png").toURI().toURL().toString());
			sendDefault=new Image(new File("resources/_0009_sendCard.png").toURI().toURL().toString());
			sendYes=new Image(new File("resources/_0010_sendCard_clicked.png").toURI().toURL().toString());
			Image come=new Image(new File("resources/_0001_netView.png").toURI().toURL().toString());
			Image copy=new Image(new File("resources/_0011_copy.png").toURI().toURL().toString());
			Image download=new Image(new File("resources/_0000_download.png").toURI().toURL().toString());
			original1.setImage(come);
			original2.setImage(come);
			original3.setImage(come);
			copy1.setImage(copy);
			copy2.setImage(copy);
			copy3.setImage(copy);
			save1.setImage(download);
			save2.setImage(download);
			save3.setImage(download);
			like1.setImage(likeDefault);
			like2.setImage(likeDefault);
			like3.setImage(likeDefault);
			send1.setImage(sendDefault);
			send2.setImage(sendDefault);
			send3.setImage(sendDefault);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setPaneMyself(Pane pane) {
		// TODO Auto-generated method stub
		
	}
	
	public void searchResult(String searchItem) {
		this.searchItem=searchItem;
		resultShow();
		searchBase();
		modeSet();
	}
	
	private void searchBase() {
		
	}
	
	private void modeSet() {
		//TODO �ҵ���û�ҵ���ͬmode��ʾ
	}
	
	@FXML
	private void like1() {
		
	}
	
	@FXML
	private void like2() {
		
	}
	
	@FXML
	private void like3() {
		
	}
	
	@FXML
	private void send1() {
		
	}
	
	@FXML
	private void send2() {
		
	}
	
	@FXML
	private void send3() {
		
	}
	
	@FXML
	private void go1() {
		
	}
	
	@FXML
	private void go2() {
		
	}
	
	@FXML
	private void go3() {
		
	}
	
	@FXML
	private void copy1() {
		
	}
	
	@FXML
	private void copy2() {
		
	}
	
	@FXML
	private void copy3() {
		
	}

	@FXML
	private void download1() {
		
	}
	
	@FXML
	private void download2() {
		
	}
	
	@FXML
	private void download3() {
		
	}
	
	private void resultShow() {
		if(count == 0 || count == 3) {
			result1.setVisible(true);
			result2.setVisible(true);
			result3.setVisible(true);
			box1.setVisible(true);
			box2.setVisible(true);
			box3.setVisible(true);
		} else if(count == 2) {
			result1.setVisible(true);
			result2.setVisible(true);
			result3.setVisible(false);
			box1.setVisible(true);
			box2.setVisible(true);
			box3.setVisible(false);
		} else if(count == 1) {
			result1.setVisible(true);
			result2.setVisible(false);
			result3.setVisible(false);
			box1.setVisible(true);
			box2.setVisible(false);
			box3.setVisible(false);
		}
	}
}
