package application.view;

import java.util.ArrayList;
import java.util.Date;

import application.Main;
import application.model.Word;
import application.model.WordCard;
import application.model.message.SendCardMessage;
import application.util.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import serverAndThread.CSConstant;

public class SendCardController implements Controller,CSConstant{
	private Main mainApp;
	private Stage dialogStage;
	private Word word;
	private int site;
	private ObservableList<String> l=FXCollections.observableArrayList();
	private ArrayList<String> receiverList=new ArrayList<>();

	@FXML
	private ListView<String> friend;
	@FXML
	private Text sendTo;
	@FXML
	private TextField say;
	@FXML
	private Text english;
	@FXML
	private Text chinese;
	@FXML
	private Text time;
	
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	@FXML
	private Button selectAll;
	@FXML
	private Button deleteAll;
	
	@Override
	public void setMain(Main mainApp) {
		this.mainApp = mainApp;
		friend.setItems(l);
		l.setAll(mainApp.getUser().getFriendList());
		receiverList.clear();
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage=dialogStage;
	}
	
	public void setWordAndSite(Word word,int site) {
		this.word = word;
		this.site=site;
	}
	
	@FXML
	private void ok() {
		for(String name:receiverList) {
			WordCard card=new WordCard(word, mainApp.getUser().getUserName(), say.getText(), new Date(System.currentTimeMillis()), site);
			SendCardMessage message=new SendCardMessage(SEND_CARD, name, card);
			mainApp.writeToServer(message);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("send error");
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void cancel() {
		dialogStage.close();
	}
	
	@FXML
	private void selectAll() {
		receiverList.clear();
		receiverList.addAll(l);
	}
	
	@FXML
	private void resetAll() {
		receiverList.clear();
		sendTo.setText("");
	}

	@Override
	public void setPaneMyself(Pane pane) {
		
	}
}
