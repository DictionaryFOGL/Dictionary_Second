package application.view;

import application.Main;
import application.model.Word;
import application.util.BaiduSpider;
import application.util.BingSpider;
import application.util.Controller;
import application.util.YoudaoSpider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
	private Button original1;
	@FXML
	private Button original2;
	@FXML
	private Button original3;
	@FXML
	private Button copy1;
	@FXML
	private Button copy2;
	@FXML
	private Button copy3;
	@FXML
	private Button save1;
	@FXML
	private Button save2;
	@FXML
	private Button save3;
	@FXML
	private Button like1;
	@FXML
	private Button like2;
	@FXML
	private Button like3;
	@FXML
	private Button send1;
	@FXML
	private Button send2;
	@FXML
	private Button send3;
	@FXML
	private CheckBox bingGet;
	@FXML
	private CheckBox baiduGet;
	@FXML
	private CheckBox youdaoGet;
	//TODO 未找到的fxml设置
	
	public WordLayoutController() {
		youdao = new YoudaoSpider();
		bing = new BingSpider();
		baidu = new BaiduSpider();
	}

	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
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
		//TODO 找到与没找到不同mode显示
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
