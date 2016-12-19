package application.view;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import application.Main;
import application.model.User;
import application.model.WordCard;
import application.model.message.AddFriendMessage;
import application.model.message.SearchMessage;
import application.util.Controller;
import application.util.InformationDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import serverAndThread.CSConstant;

public class FriendLayoutController implements Controller,CSConstant{
	private AnchorPane myself;
	private Main mainApp;
	private Image male;
	private Image female;
	private Image secret;
	private User userResult;
	private WordCard card1;
	private WordCard card2;
	private String content;
	private ArrayList<String> resemble;
	private ObservableList<String> suggest=FXCollections.observableArrayList();
	
	@FXML
	private GridPane found;
	@FXML
	private GridPane notFound;
	@FXML
	private Button add;
	@FXML
	private ListView<String> resembleSuggest;
	
	@FXML
	private Text nameFound;
	@FXML
	private Label baidu;
	@FXML
	private Label youdao;
	@FXML
	private Label bing;
	@FXML
	private Button recent1;
	@FXML
	private Button recent2;
	@FXML
	private Label rContent1;
	@FXML
	private Label rContent2;
	@FXML
	private ImageView gender;
	@FXML
	private ImageView addImg;
	
	@FXML
	private Text nameNotFound;
	@FXML
	private Button recommend1;
	@FXML
	private Button recommend2;
	@FXML
	private Button recommend3;
	
	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
		try {
			female=new Image(new File("resources/_0017_female.png").toURI().toURL().toString());
			male=new Image(new File("resources/_0015_male.png").toURI().toURL().toString());
			secret=new Image(new File("resources/_0016_secret.png").toURI().toURL().toString());
			Image addimg=new Image(new File("_0013_friendSearch_addnew.png").toURI().toURL().toString());
			addImg.setImage(addimg);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	@FXML
	private void suggest1() {
		searchResult(recommend1.getText());
	}
	
	@FXML
	private void suggest2() {
		searchResult(recommend2.getText());
	}
	
	@FXML
	private void suggest3() {
		searchResult(recommend3.getText());
	}
	
	@FXML
	private void handleSelect(MouseEvent arg) {
		if(arg.getButton().equals(MouseButton.PRIMARY)) {
			String item=resembleSuggest.getSelectionModel().getSelectedItem();
			searchResult(item);
		}
	}
	
	@FXML
	private void initialize() {
		resembleSuggest.setItems(suggest);
	}
	
	@FXML
	private void addFriend() {
		String name=userResult.getUserName();
		AddFriendMessage message=new AddFriendMessage(ADD_FRIEND, name);
		mainApp.writeToServer(message);
	}
	
	@FXML
	private void recentCard1() {
		mainApp.showCardDialog(card1);
	}
	
	@FXML
	private void recentCard2() {
		mainApp.showCardDialog(card2);
	}

	@Override
	public void setPaneMyself(Pane pane) {
		AnchorPane myself=(AnchorPane) pane;
		this.myself=myself;
	}
	
	public void searchResult(String content) {
		if(content == null || content.length() == 0) {
			found.setVisible(false);
			notFound.setVisible(false);
			InformationDialog.invalidInputForUsr();
		} else {
			this.content=content;
			suggest.clear();
			SearchMessage message=new SearchMessage(SEARCH_USER, content);
			mainApp.writeToServer(message);
		}
	}
	
	private void loadResultFound() {
		notFound.setVisible(false);
		found.setVisible(true);
		nameFound.setText(userResult.getUserName());
		baidu.setText(userResult.getBaidu()+"");
		youdao.setText(userResult.getYoudao()+"");
		bing.setText(userResult.getBing()+"");
		if(resemble != null && resemble.size() != 0) {
			suggest.setAll(resemble);
		}
		if(userResult.getMailBox().size() > 0) {
			card1=userResult.getMailBox().get(0);
			recent1.setVisible(true);
			recent1.setText(card1.getSiteStr());
			rContent1.setText(card1.getWord().getWords());
			if(userResult.getMailBox().size() >= 1) {
				card2=userResult.getMailBox().get(1);
				recent2.setVisible(true);
				recent2.setText(card2.getSiteStr());
				rContent2.setText(card2.getWord().getWords());
			} else {
				recent2.setVisible(false);
				card2=null;
			}
		} else {
			recent1.setVisible(false);
			recent2.setVisible(false);
			card1=null;
			card2=null;
		}
		char gen=userResult.getGender();
		if(gen == 's') gender.setImage(secret);
		else if(gen == 'f') gender.setImage(female);
		else gender.setImage(male);
		User me=mainApp.getUser();
		if(me != null) {
			if(me.hasFriend(userResult.getUserName()) || userResult.getUserName().equals(mainApp.getUser().getUserName())) {
				add.setVisible(false);
			} else {
				add.setVisible(true);
			}
		} else add.setVisible(false);
	}
	
	private void loadResultNotFound() {
		notFound.setVisible(true);
		found.setVisible(false);
		nameNotFound.setText(content);
		if(resemble != null && resemble.size() != 0) {
			suggest.setAll(resemble);
			if(suggest.size() > 0) {
				recommend1.setVisible(true);
				recommend1.setText(suggest.get(0));
				if(suggest.size() > 1) {
					recommend2.setVisible(true);
					recommend2.setText(suggest.get(1));
					if(suggest.size() > 2) {
						recommend3.setVisible(true);
						recommend3.setText(suggest.get(2));
					} else recommend3.setVisible(false);
				} else {
					recommend2.setVisible(false);
					recommend3.setVisible(false);
				}
			} else {
				recommend1.setVisible(false);
				recommend2.setVisible(false);
				recommend3.setVisible(false);
			}
		}
	}
	
	public void rcvResult(User userResult,ArrayList<String> resemble) {
		this.userResult=userResult;
		this.resemble=resemble;
		if(userResult == null) {
			loadResultNotFound();
		} else {
			loadResultFound();
		}
	}
	
	public void resetPage() {
		nameFound.setText("Loading......");
		baidu.setText("null");
		youdao.setText("null");
		bing.setText("null");
		recent1.setVisible(false);
		recent2.setVisible(false);
		gender.setImage(null);
		add.setVisible(false);
	}
}
