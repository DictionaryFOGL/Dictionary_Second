package application.view;

import application.Main;
import application.util.Controller;
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
	//1为搜索单词,-1为搜索朋友
	private int mode=1;
	
	private BorderPane myself;
	private AnchorPane wordLay;
	private AnchorPane friendLay;
	
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
	
	private void wordSearchMode() {
		myself.setCenter(wordLay);
	}
	
	private void friendSearchMode() {
		myself.setCenter(friendLay);
	}
	
	private void searchWordBase(String content) {
		wordSearchMode();
		//TODO controller变量没有……卧槽
	}
	
	private void searchFriendBase(String content) {
		
	}
	
	@FXML
	private void goSearch() {
		
	}
	
	@FXML
	private void modeChange() {
		mode=-mode;
		//TODO change picture
		
	}
}
