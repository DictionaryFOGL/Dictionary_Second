package application.view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import application.Main;
import application.model.User;
import application.model.Word;
import application.util.BaiduSpider;
import application.util.BingSpider;
import application.util.Controller;
import application.util.ValidInput;
import application.util.YoudaoSpider;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WordLayoutController implements Controller {
	private Main mainApp;
	private AnchorPane myself;
	private String searchItem;
	private int count;
	private static final String youdaoS = "www.youdao.com";
	private static final String bingS = "www.bing.com/translator";
	private static final String baiduS = "fanyi.baidu.com";
	private Text[] trans = new Text[3];

	private Word youdaoWord = null;
	private Word baiduWord = null;
	private Word bingWord = null;
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
	private HBox found1;
	@FXML
	private HBox found2;
	@FXML
	private HBox found3;
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

	@FXML
	private HBox notFound1;
	@FXML
	private HBox notFound2;
	@FXML
	private HBox notFound3;
	@FXML
	private ImageView questionmark1;
	@FXML
	private ImageView questionmark2;
	@FXML
	private ImageView questionmark3;
	@FXML
	private Text advice11;
	@FXML
	private Text advice12;
	@FXML
	private Text advice13;
	@FXML
	private Text advice21;
	@FXML
	private Text advice22;
	@FXML
	private Text advice23;
	@FXML
	private Text advice31;
	@FXML
	private Text advice32;
	@FXML
	private Text advice33;
	@FXML
	private Label emptyfrom1;
	@FXML
	private Label emptyfrom2;
	@FXML
	private Label emptyfrom3;

	public WordLayoutController() {
		youdao = new YoudaoSpider();
		bing = new BingSpider();
		baidu = new BaiduSpider();
	}

	@Override
	public void setMain(Main mainApp) {
		this.mainApp = mainApp;
		modeDefault();
		try {
			likeDefault = new Image(
					new File("resources/_0007_SearchWord_prove_clicked.png").toURI().toURL().toString());
			likeYes = new Image(new File("resources/_0008_SearchWord_prove.png").toURI().toURL().toString());
			sendDefault = new Image(new File("resources/_0009_sendCard.png").toURI().toURL().toString());
			sendYes = new Image(new File("resources/_0010_sendCard_clicked.png").toURI().toURL().toString());
			Image come = new Image(new File("resources/_0001_netView.png").toURI().toURL().toString());
			Image copy = new Image(new File("resources/_0011_copy.png").toURI().toURL().toString());
			Image download = new Image(new File("resources/_0000_download.png").toURI().toURL().toString());
			Image question=new Image(new File("resources/_0006__.png").toURI().toURL().toString());
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
			questionmark1.setImage(question);
			questionmark2.setImage(question);
			questionmark3.setImage(question);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setPaneMyself(Pane pane) {
		AnchorPane myself = (AnchorPane) pane;
		this.myself = myself;
	}

	public void searchResult(String searchItem) {
		this.searchItem = ValidInput.wordSearchProcessed(searchItem);
		resultShow();
		modeRank();
		searchBase();
		findCheck();
	}

	private void searchBase() {
		bingSearch();
		baiduSearch();
		youdaoSearch();
	}

	private void baiduSearch() {
		baidu.setWord(searchItem);
		baiduWord = baidu.getResult();
		if (baiduWord != null) {
			trans[1].setText(baiduWord.showTranslation());
		} else {
			// TODO
		}
	}

	private void bingSearch() {
		bing.setWord(searchItem);
		bingWord = bing.getResult();
		if (bingWord != null) {
			trans[2].setText(bingWord.showTranslation());
		}
	}

	private void youdaoSearch() {
		youdao.setWord(searchItem);
		youdaoWord = youdao.getResult();
		if (youdaoWord != null) {
			trans[0].setText(youdaoWord.showTranslation());
		}
	}

	private void modeRank() {
		User usr = mainApp.getUser();
		if (usr != null) {
			int baidulike = usr.getBaidu();
			int youdaolike = usr.getYoudao();
			int binglike = usr.getBing();
			if (baidulike >= youdaolike && baidulike >= binglike) {
				trans[1] = trans1;
				comefrom1(baiduS);
				if (youdaolike > binglike) {
					trans[0] = trans2;
					comefrom2(youdaoS);
					trans[2] = trans3;
					comefrom3(bingS);
				} else {
					trans[0] = trans3;
					comefrom3(youdaoS);
					trans[2] = trans2;
					comefrom2(bingS);
				}
			} else if (binglike >= youdaolike && binglike >= baidulike) {
				trans[2] = trans1;
				if (youdaolike >= baidulike) {
					trans[0] = trans2;
					trans[1] = trans3;
				} else {
					trans[1] = trans2;
					trans[0] = trans3;
				}
			} else if (youdaolike >= binglike && youdaolike >= baidulike) {
				trans[0] = trans1;
				if (binglike >= baidulike) {
					trans[2] = trans2;
					trans[1] = trans3;
				} else {
					trans[1] = trans2;
					trans[2] = trans3;
				}
			}
		} else
			modeDefault();
	}

	// TODO 服务器通信
	@FXML
	private void like1() {
		if (like1.getImage() == likeDefault) {
			if (trans[0] == trans1)
				mainApp.getUser().youdaoLike();
			else if (trans[1] == trans1)
				mainApp.getUser().baiduLike();
			else if (trans[2] == trans1)
				mainApp.getUser().bingLike();
			like1.setImage(likeYes);
		} else {
			if (trans[0] == trans1)
				mainApp.getUser().youdaoDisLike();
			else if (trans[1] == trans1)
				mainApp.getUser().baiduDisLike();
			else if (trans[2] == trans1)
				mainApp.getUser().bingDisLike();
			like1.setImage(likeDefault);
		}
	}

	@FXML
	private void like2() {
		if (like2.getImage() == likeDefault) {
			if (trans[0] == trans2)
				mainApp.getUser().youdaoLike();
			else if (trans[1] == trans2)
				mainApp.getUser().baiduLike();
			else if (trans[2] == trans2)
				mainApp.getUser().bingLike();
			like2.setImage(likeYes);
		} else {
			if (trans[0] == trans2)
				mainApp.getUser().youdaoDisLike();
			else if (trans[1] == trans2)
				mainApp.getUser().baiduDisLike();
			else if (trans[2] == trans2)
				mainApp.getUser().bingDisLike();
			like2.setImage(likeDefault);
		}
	}

	@FXML
	private void like3() {
		if (like3.getImage() == likeDefault) {
			if (trans[0] == trans3)
				mainApp.getUser().youdaoLike();
			else if (trans[1] == trans3)
				mainApp.getUser().baiduLike();
			else if (trans[2] == trans3)
				mainApp.getUser().bingLike();
			like3.setImage(likeYes);
		} else {
			if (trans[0] == trans3)
				mainApp.getUser().youdaoDisLike();
			else if (trans[1] == trans3)
				mainApp.getUser().baiduDisLike();
			else if (trans[2] == trans3)
				mainApp.getUser().bingDisLike();
			like3.setImage(likeDefault);
		}
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
		if (trans[0] == trans1)
			goBase(youdao.getPreUrl());
		else if (trans[1] == trans1)
			goBase(baidu.getPreUrl());
		else if (trans[2] == trans1)
			goBase(bing.getPreUrl());
	}

	@FXML
	private void go2() {
		if (trans[0] == trans2)
			goBase(youdao.getPreUrl());
		else if (trans[1] == trans2)
			goBase(baidu.getPreUrl());
		else if (trans[2] == trans2)
			goBase(bing.getPreUrl());
	}

	@FXML
	private void go3() {
		if (trans[0] == trans3)
			goBase(youdao.getPreUrl());
		else if (trans[1] == trans3)
			goBase(baidu.getPreUrl());
		else if (trans[2] == trans3)
			goBase(bing.getPreUrl());
	}

	private void goBase(String url) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void copy1() {
		setBoardContent(searchItem + "\n" + trans1.getText());
	}

	@FXML
	private void copy2() {
		setBoardContent(searchItem + "\n" + trans2.getText());
	}

	@FXML
	private void copy3() {
		setBoardContent(searchItem + "\n" + trans2.getText());
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

	@FXML
	private void mouseIn1() {
		send1.setImage(sendYes);
	}

	@FXML
	private void mouseOut1() {
		send1.setImage(sendDefault);
	}

	@FXML
	private void mouseIn2() {
		send2.setImage(sendYes);
	}

	@FXML
	private void mouseOut2() {
		send2.setImage(sendDefault);
	}

	@FXML
	private void mouseIn3() {
		send3.setImage(sendYes);
	}

	@FXML
	private void mouseOut3() {
		send3.setImage(sendDefault);
	}

	private void setBoardContent(String content) {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable text = new StringSelection(content);
		cb.setContents(text, null);
	}
	
	private void comefrom1(String con) {
		comefrom1.setText(con);
		emptyfrom1.setText(con);
	}
	
	private void comefrom2(String con) {
		comefrom2.setText(con);
		emptyfrom2.setText(con);
	}
	
	private void comefrom3(String con) {
		comefrom3.setText(con);
		emptyfrom3.setText(con);
	}

	private void modeDefault() {
		trans[0] = trans1;
		comefrom1.setText(youdaoS);
		trans[1] = trans2;
		comefrom2.setText(baiduS);
		trans[2] = trans3;
		comefrom3.setText(bingS);
	}

	private void resultShow() {
		count = 0;
		if (youdaoGet.isSelected())
			count++;
		if (baiduGet.isSelected())
			count++;
		if (bingGet.isSelected())
			count++;

		if (count == 0 || count == 3) {
			notFound1.setVisible(true);
			notFound2.setVisible(true);
			notFound3.setVisible(true);
			found1.setVisible(true);
			found2.setVisible(true);
			found3.setVisible(true);
		} else if (count == 2) {
			notFound1.setVisible(true);
			notFound2.setVisible(true);
			notFound3.setVisible(false);
			found1.setVisible(true);
			found2.setVisible(true);
			found3.setVisible(false);
		} else if (count == 1) {
			notFound1.setVisible(true);
			notFound2.setVisible(false);
			notFound3.setVisible(false);
			found1.setVisible(true);
			found2.setVisible(false);
			found3.setVisible(false);
		}
	}
	
	private void find1(boolean status) {
		boolean opposite=!status;
		found1.setVisible(status);
		notFound1.setVisible(opposite);
	}
	
	private void find2(boolean status) {
		boolean opposite=!status;
		found2.setVisible(status);
		notFound2.setVisible(opposite);
	}
	
	private void find3(boolean status) {
		boolean opposite=!status;
		found3.setVisible(status);
		notFound3.setVisible(opposite);
	}
	
	private void findCheck() {
		if (trans[0] == trans1) {
			if(youdaoWord != null) find1(true);
			else find1(false);
		} else if (trans[1] == trans1) {
			if(baiduWord != null) find1(true);
			else find1(false);
		} else if (trans[2] == trans1) {
			if(bingWord != null) find1(true);
			else find1(false);
		}
		
		if (trans[0] == trans2) {
			if(youdaoWord != null) find2(true);
			else find2(false);
		} else if (trans[1] == trans2) {
			if(baiduWord != null) find2(true);
			else find2(false);
		} else if (trans[2] == trans2) {
			if(bingWord != null) find2(true);
			else find2(false);
		}
		
		if (trans[0] == trans3) {
			if(youdaoWord != null) find3(true);
			else find3(false);
		} else if (trans[1] == trans3) {
			if(baiduWord != null) find3(true);
			else find3(false);
		} else if (trans[2] == trans3) {
			if(bingWord != null) find3(true);
			else find3(false);
		}
	}
}
