package application.view;

import application.Main;
import application.model.WordCard;
import application.util.Controller;
import application.util.ProcessTimeFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class WordCardLayoutController implements Controller {
	private Main mainApp;
	private WordCard card;
	
	@FXML
	private Label site;
	@FXML
	private Text words;
	@FXML
	private Text trans;
	@FXML
	private Text say;
	@FXML
	private Text time;
	@FXML
	private Text date;
	@FXML
	private Text sender;
	
	@Override
	public void setPaneMyself(Pane pane) {
		
	}

	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}
	
	private void setAll() {
		sender.setText(card.getSenderName());
		String Date=ProcessTimeFormat.dateStr(card.getTime());
		String Time=ProcessTimeFormat.timeStr(card.getTime());
		date.setText(Date);
		time.setText(Time);
		words.setText(card.getWord().getWords());
		trans.setText(card.getWord().showTranslation());
		say.setText(card.getSaySomething());
	}
	
	public void setWordCard(WordCard card) {
		this.card=card;
		setAll();
	}
}
