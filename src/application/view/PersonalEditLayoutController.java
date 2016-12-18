package application.view;

import java.io.File;
import java.net.MalformedURLException;

import application.Main;
import application.model.User;
import application.model.WordCard;
import application.util.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class PersonalEditLayoutController implements Controller {
	private Main mainApp;
	private ObservableList<String> f = FXCollections.observableArrayList();
	private ObservableList<WordCard> w = FXCollections.observableArrayList();
	
	@FXML
	private ListView<WordCard> wordCards;
	@FXML
	private ListView<String> friends;
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

	@FXML
	private void initialize() {
		friends.setItems(f);
		friends.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new ListCell<String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						this.setText(item);
						if(mainApp.getUser().friendStatus(item)) {
							//TODO css
							System.out.println(item);
						}
					}
				};
			}
		});
		wordCards.setItems(w);
		wordCards.setCellFactory(new Callback<ListView<WordCard>, ListCell<WordCard>>() {
			
			@Override
			public ListCell<WordCard> call(ListView<WordCard> param) {
				return new ListCell<WordCard>() {
					@Override
					protected void updateItem(WordCard item, boolean empty) {
						super.updateItem(item, empty);
						if(item != null) {
							String words=item.getWord().getWords();
							String content=words+"......"+"\t"+item.getSiteStr();
							this.setText(content);
						}
					}
				};
			}
		});
	}
	
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
			e.printStackTrace();
		}
	}
	
	public void loadData() {
		User me=mainApp.getUser();
		id.setText("ID : "+me.getUserID());
		baidu.setText(" "+me.getBaidu());
		youdao.setText(" "+me.getYoudao());
		bing.setText(" "+me.getBing());
		f.setAll(me.getFriendList());
		w.setAll(me.getMailBox());
	}
	
	public void observableRcvCard(WordCard card) {
		w.add(card);
	}
	
	public void observableRcvFriend(String friend) {
		f.add(friend);
	}
	
	public void observableDelFriend(String friend) {
		f.remove(friend);
	}
	
	public void observableDelCard(WordCard card) {
		w.remove(card);
	}
	
	
	@FXML
	private void ok() {
		
		//TODO server
	}
	
	@FXML
	private void cancel(){
		old.setText("");
		new1.setText("");
		new2.setText("");
	}
}
