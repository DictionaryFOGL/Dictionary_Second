package application.view;

import application.Main;
import application.model.User;
import application.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class FriendLayoutController implements Controller{
	
	@FXML
	private GridPane found;
	@FXML
	private GridPane notFound;
	@FXML
	private Button add;
	@FXML
	private ListView<User> result;
	
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
	private Label rSite1;
	@FXML
	private Label rSite2;
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPaneMyself(Pane pane) {
		// TODO Auto-generated method stub
		
	}
	
	public void searchResult(String content) {
		
	}
}
