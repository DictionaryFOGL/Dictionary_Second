package application.view;

import application.Main;
import application.model.User;
import application.model.WordCard;
import application.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PersonalEditLayoutController implements Controller {
	private Main mainApp;
	
	@FXML
	private ListView<WordCard> wordCards;
	@FXML
	private ListView<User> friends;
	@FXML
	private PasswordField old;
	@FXML
	private PasswordField new1;
	@FXML
	private PasswordField new2;
	@FXML
	private Text id;
	@FXML
	private Label baidu;
	@FXML
	private Label bing;
	@FXML
	private Label youdao;
	@FXML
	private Button save;
	@FXML
	private Button cancel;
	@FXML
	private ImageView clearCards;
	@FXML
	private ImageView refreshFriend;
	@FXML
	private ImageView refreshCards;
	@FXML
	private ImageView like1;
	@FXML
	private ImageView like2;
	@FXML
	private ImageView like3;
	@FXML
	private RadioButton male;
	@FXML
	private RadioButton female;
	@FXML
	private RadioButton secret;
	
	@Override
	public void setPaneMyself(Pane pane) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}

}
