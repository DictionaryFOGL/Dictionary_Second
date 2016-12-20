package application.view;

import java.util.ArrayList;
import java.sql.Date;

import application.Main;
import application.model.Word;
import application.model.WordCard;
import application.model.message.SendCardMessage;
import application.util.Controller;
import application.util.InformationDialog;
import application.util.ProcessTimeFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import serverAndThread.CSConstant;

public class SendCardController implements Controller,CSConstant{
	private Main mainApp;
	private Stage dialogStage;
	private Word word;
	private long presentTimeStamp;
	private int site;
	private ObservableList<String> l=FXCollections.observableArrayList();
	private ArrayList<String> receiverList=new ArrayList<>();
	private ArrayList<CheckBox> selection=new ArrayList<CheckBox>();
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
		friend.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new ListCell<String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						if(item != null) {
							super.updateItem(item, empty);
							CheckBox select=new CheckBox(item);
							this.setGraphic(select);
							selection.add(select);
							select.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									if(select.isSelected()) receiverList.add(select.getText());
									else receiverList.remove(select.getText());
									sendTo.setText(receiverList.toString().replaceAll(", ", "/").replaceAll("\\[|\\]", ""));
								}
							});
						}
					}
				};
			}
		});
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
		sendTo.setText("");
		say.setText("A wonderful card!");
		presentTimeStamp=System.currentTimeMillis();
		english.setText(word.getWords());
		chinese.setText(word.showTranslation());
		time.setText(ProcessTimeFormat.standard(presentTimeStamp));
	}
	
	@FXML
	private void ok() {
		String saysome=say.getText();
		if(saysome.length() > 10) {
			InformationDialog.sayTooMuch();
			say.setText("");
			return;
		}
		if(receiverList.size() == 0) {
			InformationDialog.noBodySent();
			return;
		}
		for(String name:receiverList) {
			WordCard card=new WordCard(word, mainApp.getUser().getUserName(), saysome, mainApp.getUser().getUserID(), presentTimeStamp, site);
			SendCardMessage message=new SendCardMessage(SEND_CARD, name, card);
			mainApp.writeToServer(message);
			try {
				//每隔0.1s发一张单词卡
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("send error");
				e.printStackTrace();
			}
		}
		dialogStage.close();
	}
	
	@FXML
	private void cancel() {
		dialogStage.close();
	}
	
	@FXML
	private void selectAll() {
		int limit=10;
		String namelist="";
		for(CheckBox b:selection) {
			b.setSelected(true);
		}
		receiverList.clear();
		receiverList.addAll(l);
		for(String rcvr:receiverList) {
			namelist+=rcvr+"/";
			if(namelist.length() > limit) {
				namelist=namelist.substring(0, limit)+"......";
				break;
			}
		}
		sendTo.setText(namelist);
	}
	
	@FXML
	private void resetAll() {
		for(CheckBox b:selection) {
			b.setSelected(false);
		}
		receiverList.clear();
		sendTo.setText("");
	}

	@Override
	public void setPaneMyself(Pane pane) {}
}
