package application.view;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import application.Main;
import application.model.User;
import application.model.WordCard;
import application.util.Controller;
import application.util.ValidInput;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class WorkLayoutController implements Controller {
	private Main mainApp;
	// 1ÎªËÑË÷µ¥´Ê,-1ÎªËÑË÷ÅóÓÑ
	private int mode = 1;
	private Image mode1;
	private Image mode2;

	private AnchorPane wordLay;
	private AnchorPane friendLay;
	private AnchorPane personal;

	private WordLayoutController wordCon;
	private FriendLayoutController friendCon;
	private PersonalEditLayoutController personCon;

	@FXML
	private BorderPane myself;
	@FXML
	private ImageView modePic;
	@FXML
	private TextField input;
	@FXML
	private Button search;
	@FXML
	private ChoiceBox<String> back;
	@FXML
	private ChoiceBox<String> forward;

	@Override
	public void setMain(Main mainApp) {
		this.mainApp = mainApp;
		try {
			mode1 = new Image(new File("resources/_0005_mode_word.png").toURI().toURL().toString());
			mode2 = new Image(new File("resources/_0002_mode_friend.png").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setPaneMyself(Pane pane) {
		BorderPane myself = (BorderPane) pane;
		this.myself = myself;
	}

	public void setChildPane(AnchorPane wordLay, AnchorPane friendLay, AnchorPane personal) {
		this.wordLay = wordLay;
		this.friendLay = friendLay;
		this.personal = personal;
	}

	public void setWordControl(WordLayoutController wordCon) {
		this.wordCon = wordCon;
	}

	public void setFriControl(FriendLayoutController friendCon) {
		this.friendCon = friendCon;
	}

	public void setPersonControl(PersonalEditLayoutController personCon) {
		this.personCon = personCon;
	}

	private void wordSearchMode() {
		myself.setCenter(wordLay);
	}

	private void friendSearchMode() {
		myself.setCenter(friendLay);
		friendCon.resetPage();
	}

	public void editMode() {
		myself.setCenter(personal);
	}

	private void searchWordBase(String content) {
		wordSearchMode();
		// TODO change
		wordCon.searchResult(content);
	}

	private void searchFriendBase(String content) {
		friendSearchMode();
		friendCon.searchResult(content);
	}

	@FXML
	private void goSearch() {
		if (mode == 1) {
			searchWordBase(ValidInput.wordSearchProcessed(input.getText()));
		} else {
			searchFriendBase(ValidInput.friendSearchProcessed(input.getText()));
		}
	}

	@FXML
	private void modeChange() {
		mode = -mode;
		if (mode == -1) {
			modePic.setImage(mode2);
		} else {
			modePic.setImage(mode1);
		}
	}
	
	public void observableRcvCard(WordCard card) {
		personCon.observableRcvCard(card);
	}
	
	public void observableRcvFriend(String friend) {
		personCon.observableRcvFriend(friend);
	}
	
	public void observableDelFriend(String friend) {
		personCon.observableDelFriend(friend);
	}
	
	public void observableDelCard(WordCard card) {
		personCon.observableDelCard(card);
	}
	
	public void renewfriendData() {
		personCon.renewfriendData();
	}
	
	public void friendRcvResult(User userResult,ArrayList<String> resemble) {
		friendCon.rcvResult(userResult, resemble);
	}
	
	public void loadDataPersonal() {
		personCon.loadData();
	}
}
