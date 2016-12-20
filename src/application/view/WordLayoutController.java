package application.view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import application.Main;
import application.model.User;
import application.model.Word;
import application.model.message.LikeMessage;
import application.util.BaiduSpider;
import application.util.BingSpider;
import application.util.Controller;
import application.util.InformationDialog;
import application.util.ValidInput;
import application.util.YoudaoSpider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import serverAndThread.CSConstant;

public class WordLayoutController implements Controller,CSConstant {
	private static int YOUDAO=2;
	private static int BAIDU=0;
	private static int BING=1;
	private Main mainApp;
	private AnchorPane myself;
	private String searchItem;
	private static final String youdaoS = "www.youdao.com";
	private static final String bingS = "www.bing.com/translator";
	private static final String baiduS = "fanyi.baidu.com";
	private Text[] trans = new Text[3];
	private ImageView[] likerank=new ImageView[3];
	private ObservableList<String> suggest=FXCollections.observableArrayList();

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
	
	public void logoutUISet() {
		send1.setVisible(false);
		send2.setVisible(false);
		send3.setVisible(false);
		like1.setVisible(false);
		like2.setVisible(false);
		like3.setVisible(false);
	}
	
	public void loginUISet() {
		send1.setVisible(true);
		send2.setVisible(true);
		send3.setVisible(true);
		like1.setVisible(true);
		like2.setVisible(true);
		like3.setVisible(true);
	}

	
	@FXML
	private void initialize() {
		result.setItems(suggest);
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
			
			advice11.setText("");
			advice12.setText("");
			advice13.setText("");
			advice21.setText("");
			advice22.setText("");
			advice23.setText("");
			advice31.setText("");
			advice32.setText("");
			advice33.setText("");
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
		searchCon.setText("¡¤ "+searchItem);
		if(searchItem == null || searchItem.length() == 0) {
			find1(false,false);
			find2(false,false);
			find3(false,false);
			InformationDialog.invalidInputForWord();
			return;
		}
		modeRank();
		searchBase();
		findCheck();
		historyLoad();
	}
	
	private void historyLoad() {
		if(mainApp.getUser() == null) return;
		if(mainApp.getBaiduHistory(searchItem)) {
			likerank[1].setImage(likeYes);
		} else {
			likerank[1].setImage(likeDefault);
		}
		if(mainApp.getYoudaoHistory(searchItem)) {
			likerank[0].setImage(likeYes);
		} else {
			likerank[0].setImage(likeDefault);
		}
		if(mainApp.getBingHistory(searchItem)) {
			likerank[2].setImage(likeYes);
		} else {
			likerank[2].setImage(likeDefault);
		}
	}

	private void searchBase() {
		bingSearch();
		baiduSearch();
		youdaoSearch();
		getSuggestion();
	}
	
	private void getSuggestion() {
		suggest.clear();
		ArrayList<String> Sug=bing.getSuggestion();
		if(Sug != null && Sug.size() != 0) {
			int i=0;
			for(String s:Sug) {
				suggest.add(s);
				i++;
				if(i == 1) {
					if(trans[2] == trans1) advice11.setText(s);
					else if(trans[2] == trans2) advice21.setText(s);
					else advice31.setText(s);
				} else if(i == 2) {
					if(trans[2] == trans1) advice12.setText(s);
					else if(trans[2] == trans2) advice22.setText(s);
					else advice32.setText(s);
				} else if(i == 3) {
					if(trans[2] == trans1) advice13.setText(s);
					else if(trans[2] == trans2) advice23.setText(s);
					else advice33.setText(s);
				}
			}
		}
		Sug=youdao.getSuggestion();
		if(Sug != null && Sug.size() != 0) {
			int i=0;
			for(String s:Sug) {
				suggest.add(s);
				i++;
				if(i == 1) {
					if(trans[0] == trans1) advice11.setText(s);
					else if(trans[0] == trans2) advice21.setText(s);
					else advice31.setText(s);
				} else if(i == 2) {
					if(trans[0] == trans1) advice12.setText(s);
					else if(trans[0] == trans2) advice22.setText(s);
					else advice32.setText(s);
				} else if(i == 3) {
					if(trans[0] == trans1) advice13.setText(s);
					else if(trans[0] == trans2) advice23.setText(s);
					else advice33.setText(s);
				}
			}
		}
	}

	private void baiduSearch() {
		baidu.setWord(searchItem);
		baiduWord = baidu.getResult();
		if (baiduWord != null) {
			trans[1].setText(baiduWord.showTranslation());
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
				likerank[1] =like1;
				comefrom1(baiduS);
				if (youdaolike > binglike) {
					trans[0] = trans2;
					likerank[0]=like2;
					comefrom2(youdaoS);
					trans[2] = trans3;
					likerank[2]=like3;
					comefrom3(bingS);
				} else {
					trans[0] = trans3;
					likerank[0]=like3;
					comefrom3(youdaoS);
					trans[2] = trans2;
					likerank[2]=like2;
					comefrom2(bingS);
				}
			} else if (binglike >= youdaolike && binglike >= baidulike) {
				trans[2] = trans1;
				likerank[2] =like1;
				comefrom1(bingS);
				if (youdaolike >= baidulike) {
					trans[0] = trans2;
					likerank[0]=like2;
					comefrom2(youdaoS);
					trans[1] = trans3;
					likerank[1]=like3;
					comefrom3(baiduS);
				} else {
					trans[1] = trans2;
					likerank[1]=like2;
					comefrom2(baiduS);
					trans[0] = trans3;
					likerank[0]=like3;
					comefrom3(youdaoS);
				}
			} else if (youdaolike >= binglike && youdaolike >= baidulike) {
				trans[0] = trans1;
				likerank[0] =like1;
				comefrom1(youdaoS);
				if (binglike >= baidulike) {
					trans[2] = trans2;
					likerank[2]=like2;
					comefrom2(bingS);
					trans[1] = trans3;
					likerank[1]=like3;
					comefrom3(baiduS);
				} else {
					trans[1] = trans2;
					likerank[1]=like2;
					comefrom2(baiduS);
					trans[2] = trans3;
					likerank[2]=like3;
					comefrom3(bingS);
				}
			}
		} else modeDefault();
	}

	@FXML
	private void like1() {
		if (like1.getImage() == likeDefault) {
			if (trans[0] == trans1) {
				mainApp.getUser().youdaoLike();
				mainApp.changeHistory(searchItem, YOUDAO, true);
				mainApp.writeToServer(new LikeMessage(LIKE, true, searchItem, YOUDAO));
			} else if (trans[1] == trans1) {
				mainApp.getUser().baiduLike();
				mainApp.changeHistory(searchItem, BAIDU, true);
				mainApp.writeToServer(new LikeMessage(LIKE, true, searchItem, BAIDU));
			}
			else if (trans[2] == trans1) {
				mainApp.getUser().bingLike();
				mainApp.changeHistory(searchItem, BING, true);
				mainApp.writeToServer(new LikeMessage(LIKE, true, searchItem, BING));
			}
			like1.setImage(likeYes);
		} else {
			if (trans[0] == trans1) {
				mainApp.getUser().youdaoDisLike();
				mainApp.changeHistory(searchItem, YOUDAO, false);
				mainApp.writeToServer(new LikeMessage(LIKE, false, searchItem, YOUDAO));
			} else if (trans[1] == trans1) {
				mainApp.getUser().baiduDisLike();
				mainApp.changeHistory(searchItem, BAIDU, false);
				mainApp.writeToServer(new LikeMessage(LIKE, false, searchItem, BAIDU));
			} else if (trans[2] == trans1) {
				mainApp.getUser().bingDisLike();
				mainApp.changeHistory(searchItem, BING, false);
				mainApp.writeToServer(new LikeMessage(LIKE, false, searchItem, BING));
			}
			like1.setImage(likeDefault);
		}
		mainApp.synchronizeLike();
	}

	@FXML
	private void like2() {
		if (like2.getImage() == likeDefault) {
			if (trans[0] == trans2) {
				mainApp.getUser().youdaoLike();
				mainApp.changeHistory(searchItem, YOUDAO, true);
				mainApp.writeToServer(new LikeMessage(LIKE, true, searchItem, YOUDAO));
			} else if (trans[1] == trans2) {
				mainApp.getUser().baiduLike();
				mainApp.changeHistory(searchItem, BAIDU, true);
				mainApp.writeToServer(new LikeMessage(LIKE, true, searchItem, BAIDU));
			} else if (trans[2] == trans2) {
				mainApp.getUser().bingLike();
				mainApp.writeToServer(new LikeMessage(LIKE, true, searchItem, BING));
			}
			like2.setImage(likeYes);
		} else {
			if (trans[0] == trans2) {
				mainApp.getUser().youdaoDisLike();
				mainApp.changeHistory(searchItem, YOUDAO, false);
				mainApp.writeToServer(new LikeMessage(LIKE, false, searchItem, YOUDAO));
			} else if (trans[1] == trans2) {
				mainApp.getUser().baiduDisLike();
				mainApp.changeHistory(searchItem, BAIDU, false);
				mainApp.writeToServer(new LikeMessage(LIKE, false, searchItem, BAIDU));
			} else if (trans[2] == trans2) {
				mainApp.getUser().bingDisLike();
				mainApp.changeHistory(searchItem, BING, false);
				mainApp.writeToServer(new LikeMessage(LIKE, false, searchItem, BING));
			}
			like2.setImage(likeDefault);
		}
		mainApp.synchronizeLike();
	}

	@FXML
	private void like3() {
		if (like3.getImage() == likeDefault) {
			if (trans[0] == trans3) {
				mainApp.getUser().youdaoLike();
				mainApp.changeHistory(searchItem, YOUDAO, true);
				mainApp.writeToServer(new LikeMessage(LIKE, true, searchItem, YOUDAO));
			} else if (trans[1] == trans3) {
				mainApp.getUser().baiduLike();
				mainApp.changeHistory(searchItem, BAIDU, true);
				mainApp.writeToServer(new LikeMessage(LIKE, true, searchItem, BAIDU));
			} else if (trans[2] == trans3) {
				mainApp.getUser().bingLike();
				mainApp.changeHistory(searchItem, BING, true);
				mainApp.writeToServer(new LikeMessage(LIKE, true, searchItem, BING));
			}
			like3.setImage(likeYes);
		} else {
			if (trans[0] == trans3) {
				mainApp.getUser().youdaoDisLike();
				mainApp.changeHistory(searchItem, YOUDAO, false);
				mainApp.writeToServer(new LikeMessage(LIKE, false, searchItem, YOUDAO));
			} else if (trans[1] == trans3) {
				mainApp.getUser().baiduDisLike();
				mainApp.changeHistory(searchItem, BAIDU, false);
				mainApp.writeToServer(new LikeMessage(LIKE, false, searchItem, BAIDU));
			} else if (trans[2] == trans3) {
				mainApp.getUser().bingDisLike();
				mainApp.changeHistory(searchItem, BING, false);
				mainApp.writeToServer(new LikeMessage(LIKE, false, searchItem, BING));
			}
			like3.setImage(likeDefault);
		}
		mainApp.synchronizeLike();
	}

	@FXML
	private void send1() {
		System.out.println("send1");
		if (trans[0] == trans1)
			mainApp.showSendCardDialog(youdaoWord,YOUDAO);
		else if (trans[1] == trans1)
			mainApp.showSendCardDialog(baiduWord,BAIDU);
		else if (trans[2] == trans1)
			mainApp.showSendCardDialog(bingWord,BING);
	}

	@FXML
	private void send2() {
		System.out.println("send2");
		if (trans[0] == trans2)
			mainApp.showSendCardDialog(youdaoWord,YOUDAO);
		else if (trans[1] == trans2)
			mainApp.showSendCardDialog(baiduWord,BAIDU);
		else if (trans[2] == trans2)
			mainApp.showSendCardDialog(bingWord,BING);
	}

	@FXML
	private void send3() {
		System.out.println("send3");
		if (trans[0] == trans3)
			mainApp.showSendCardDialog(youdaoWord,YOUDAO);
		else if (trans[1] == trans3)
			mainApp.showSendCardDialog(baiduWord,BAIDU);
		else if (trans[2] == trans3)
			mainApp.showSendCardDialog(bingWord,BING);
	}

	@FXML
	private void go1() {
		if (trans[0] == trans1)
			goBase(youdao.getPreUrl()+searchItem);
		else if (trans[1] == trans1)
			goBase(baidu.getPreUrl()+searchItem);
		else if (trans[2] == trans1)
			goBase(bing.getPreUrl()+searchItem);
	}

	@FXML
	private void go2() {
		if (trans[0] == trans2)
			goBase(youdao.getPreUrl()+searchItem);
		else if (trans[1] == trans2)
			goBase(baidu.getPreUrl()+searchItem);
		else if (trans[2] == trans2)
			goBase(bing.getPreUrl()+searchItem);
	}

	@FXML
	private void go3() {
		if (trans[0] == trans3)
			goBase(youdao.getPreUrl()+searchItem);
		else if (trans[1] == trans3)
			goBase(baidu.getPreUrl()+searchItem);
		else if (trans[2] == trans3)
			goBase(bing.getPreUrl()+searchItem);
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
		String content=searchItem + "\n" + trans1.getText();
		setBoardContent(content);
		InformationDialog.copied(content);
	}

	@FXML
	private void copy2() {
		String content=searchItem + "\n" + trans2.getText();
		setBoardContent(content);
		InformationDialog.copied(content);
	}

	@FXML
	private void copy3() {
		String content=searchItem + "\n" + trans2.getText();
		setBoardContent(content);
		InformationDialog.copied(content);
	}

	@FXML
	private void download1() {
		if(found1.isVisible()) snapShot(found1);
		else snapShot(notFound1);
	}

	@FXML
	private void download2() {
		if(found2.isVisible()) snapShot(found2);
		else snapShot(notFound2);
	}

	@FXML
	private void download3() {
		if(found3.isVisible()) snapShot(found3);
		else snapShot(notFound3);
	}
	
	private void snapShot(Node value) {
		String name=(mainApp.getUser() != null) ? (mainApp.getUser().getUserName()+"_") : ("guest_");
		Image img=value.snapshot(null, null);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", new File("temp/"+name+System.currentTimeMillis()+".png"));
			InformationDialog.picGet(name+".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	@FXML
	private void handleSelected(MouseEvent arg) {
		if(arg.getButton().equals(MouseButton.PRIMARY)) {
			searchResult(result.getSelectionModel().getSelectedItem());
		}
	}

	private void modeDefault() {
		trans[0] = trans1;
		comefrom1.setText(youdaoS);
		trans[1] = trans2;
		comefrom2.setText(baiduS);
		trans[2] = trans3;
		comefrom3.setText(bingS);
	}
	
	private void find1(boolean status,boolean opposite) {
		found1.setVisible(status);
		notFound1.setVisible(opposite);
	}
	
	private void find2(boolean status,boolean opposite) {
		found2.setVisible(status);
		notFound2.setVisible(opposite);
	}
	
	private void find3(boolean status,boolean opposite) {
		found3.setVisible(status);
		notFound3.setVisible(opposite);
	}
	
	private void findCheck() {
		if (trans[0] == trans1) {
			if(!youdaoGet.isSelected() && (baiduGet.isSelected() || bingGet.isSelected())) find1(false,false);
			else if(youdaoWord != null) find1(true,false);
			else find1(false,true);
		} else if (trans[1] == trans1) {
			if(!baiduGet.isSelected() && (youdaoGet.isSelected() || bingGet.isSelected())) find1(false,false);
			else if(baiduWord != null) find1(true,false);
			else find1(false,true);
		} else if (trans[2] == trans1) {
			if(!bingGet.isSelected() && (youdaoGet.isSelected() || baiduGet.isSelected())) find1(false,false);
			else if(bingWord != null) find1(true,false);
			else find1(false,true);
		}
		
		if (trans[0] == trans2) {
			if(!youdaoGet.isSelected() && (baiduGet.isSelected() || bingGet.isSelected())) find2(false,false);
			else if(youdaoWord != null) find2(true,false);
			else find2(false,true);
		} else if (trans[1] == trans2) {
			if(!baiduGet.isSelected() && (youdaoGet.isSelected() || bingGet.isSelected())) find2(false,false);
			else if(baiduWord != null) find2(true,false);
			else find2(false,true);
		} else if (trans[2] == trans2) {
			if(!bingGet.isSelected() && (youdaoGet.isSelected() || baiduGet.isSelected())) find2(false,false);
			else if(bingWord != null) find2(true,false);
			else find2(false,true);
		}
		
		if (trans[0] == trans3) {
			if(!youdaoGet.isSelected() && (baiduGet.isSelected() || bingGet.isSelected())) find3(false,false);
			else if(youdaoWord != null) find3(true,false);
			else find3(false,true);
		} else if (trans[1] == trans3) {
			if(!baiduGet.isSelected() && (youdaoGet.isSelected() || bingGet.isSelected())) find3(false,false);
			else if(baiduWord != null) find3(true,false);
			else find3(false,true);
		} else if (trans[2] == trans3) {
			if(!bingGet.isSelected() && (youdaoGet.isSelected() || baiduGet.isSelected())) find3(false,false);
			else if(bingWord != null) find3(true,false);
			else find3(false,true);
		}
	}
}
