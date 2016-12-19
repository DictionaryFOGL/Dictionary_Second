package application.view;

import java.io.File;
import java.net.MalformedURLException;

import javax.sound.midi.MidiDevice.Info;

import application.Main;
import application.model.User;
import application.model.WordCard;
import application.model.message.AddFriendMessage;
import application.model.message.LoginMessage;
import application.model.message.Message;
import application.model.message.SendCardMessage;
import application.util.Controller;
import application.util.InformationDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import serverAndThread.CSConstant;

public class PersonalEditLayoutController implements Controller ,CSConstant{
	private Main mainApp;
	private ObservableList<String> f = FXCollections.observableArrayList();
	private ObservableList<WordCard> w = FXCollections.observableArrayList();
	private ToggleGroup group;
	
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
		group=new ToggleGroup();
		male.setToggleGroup(group);
		female.setToggleGroup(group);
		secret.setToggleGroup(group);
		
		friendListRenew();
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
	
	public void friendListRenew() {
		friends.setItems(f);
		friends.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new ListCell<String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(mainApp.getUser().friendStatus(item)) {
							this.setText(item+" 在线");
							System.out.println(item+" 在线");
						} else this.setText(item);
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
	public void renewfriendData() {
		f.setAll(mainApp.getUser().getFriendList());
	}
	
	public void observableRcvCard(WordCard card) {
		w.add(card);
	}
	
	public void observableRcvFriend(String friend) {
		f.add(friend);
	}
	
	public void observableDelFriend(String friend) {
		f.remove(friend+" 在线");
	}
	
	public void observableDelCard(WordCard card) {
		w.remove(card);
	}
	
	@FXML
	private void handleCardSelected(MouseEvent arg) {
		if(arg.getButton().equals(MouseButton.PRIMARY)) {
			mainApp.showCardDialog(wordCards.getSelectionModel().getSelectedItem());
		} else if(arg.getButton().equals(MouseButton.SECONDARY)) {
			WordCard card=wordCards.getSelectionModel().getSelectedItem();
			boolean status=InformationDialog.deleteCard(card);
			if(status) {
				SendCardMessage message=new SendCardMessage(DELETE_CARD, card);
				mainApp.writeToServer(message);
				w.remove(card);
			}
		}
	}
	
	@FXML
	private void handleFriendSelected(MouseEvent arg) {
		if(arg.getButton().equals(MouseButton.PRIMARY)) {
			
		} else if(arg.getButton().equals(MouseButton.SECONDARY)) {
			String friendName=friends.getSelectionModel().getSelectedItem();
			boolean status=InformationDialog.deleteFriend(friendName);
			if(status) {
				AddFriendMessage message=new AddFriendMessage(DELETE_FRIEND, friendName);
				mainApp.writeToServer(message);
			}
		}
	}
	
	@FXML
	private void ok() {
		String oldp=old.getText();
		String newp1=new1.getText();
		String newp2=new2.getText();
		if(!oldp.equals(mainApp.getUser().getPassword())) {
			InformationDialog.wrongOldPwd();
			return;
		}
		if(!newp1.equals(newp2)) {
			InformationDialog.differentPwd();
			return;
		}
		LoginMessage change=new LoginMessage(PASSWORD_CHANGE, newp1);
		mainApp.writeToServer(change);
		clear();
	}
	
	@FXML
	private void clear() {
		old.setText("");
		new1.setText("");
		new2.setText("");
	}
	
	public void logoutUISet() {
		clear();
		id.setText("");
		youdao.setText("");
		baidu.setText("");
		bing.setText("");
		f.clear();
		w.clear();
	}
}
