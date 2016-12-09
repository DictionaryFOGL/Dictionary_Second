package application.view;

import application.Main;
import application.util.Controller;
import application.util.ValidInput;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class WorkLayoutController implements Controller{
	private Main mainApp;
	//1ÎªËÑË÷µ¥´Ê,-1ÎªËÑË÷ÅóÓÑ
	private int mode=-1;
	
	
	private AnchorPane wordLay;
	private AnchorPane friendLay;
	
	private WordLayoutController wordCon;
	private FriendLayoutController friendCon;
	
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
		this.mainApp=mainApp;
	}

	@Override
	public void setPaneMyself(Pane pane) {
		BorderPane myself=(BorderPane) pane;
		this.myself=myself;
	}
	
	public void setChildPane(AnchorPane wordLay,AnchorPane friendLay) {
		this.wordLay=wordLay;
		this.friendLay=friendLay;
	}
	
	public void setWordControl(WordLayoutController wordCon) {
		this.wordCon=wordCon;
	}
	
	public void setFriControl(FriendLayoutController friendCon) {
		this.friendCon=friendCon;
	}
	
	private void wordSearchMode() {
		System.out.println(myself);
		myself.setCenter(wordLay);
	}
	
	private void friendSearchMode() {
		myself.setCenter(friendLay);
	}
	
	private void searchWordBase(String content) {
		wordSearchMode();
		//TODO change
		wordCon.searchResult(content);
	}
	
	private void searchFriendBase(String content) {
		friendSearchMode();
		friendCon.searchResult(content);
	}
	
	@FXML
	private void goSearch() {
		if(mode == 1) {
			searchWordBase(ValidInput.wordSearchProcessed(input.getText()));
		} else {
			searchFriendBase(ValidInput.friendSearchProcessed(input.getText()));
		}
	}
	
	@FXML
	private void modeChange() {
		mode=-mode;
		//TODO change picture
		
	}
}
