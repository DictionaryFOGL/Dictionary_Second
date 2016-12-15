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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
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
		try {
			Image like=new Image(new File("resources/_0021_personalEdit_prove.png").toURI().toURL().toString());
			like1.setImage(like);
			like2.setImage(like);
			like3.setImage(like);
			refreshFriend.setImage(new Image(new File("resources/_0018_friend_renew.png").toURI().toURL().toString()));
			clearCards.setImage(new Image(new File("resources/_0019_mailbox_reset.png").toURI().toURL().toString()));
			refreshCards.setImage(new Image(new File("resources/_0020_mailbox_renew.png").toURI().toURL().toString()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
