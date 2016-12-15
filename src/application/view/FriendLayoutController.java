package application.view;

import java.io.File;
import java.net.MalformedURLException;

import application.Main;
import application.model.User;
import application.model.WordCard;
import application.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class FriendLayoutController implements Controller{
	private AnchorPane myself;
	private Main mainApp;
	private Image male;
	private Image female;
	private Image secret;
	private User user;
	private WordCard card1;
	private WordCard card2;
	
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
	private ImageView gender;
	
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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setPaneMyself(Pane pane) {
		AnchorPane myself=(AnchorPane) pane;
		this.myself=myself;
	}
	
	public void searchResult(String content) {
		
	}
}
