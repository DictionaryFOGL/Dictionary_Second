package application.view;

import java.io.File;
import java.net.MalformedURLException;

import application.Main;
import application.model.WordCard;
import application.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class BaseLayoutController implements Controller{
	private Main mainApp;
	private WorkLayoutController workCon;
	private BorderPane myself;
	private BorderPane workPane;
	
	@FXML
	private Button logout;
	@FXML
	private ImageView personalEdit;
	@FXML
	private Label time;
	@FXML
	private Label date;
	@FXML
	private Label name;
	
	private Image edit;
	
	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
		try {
			personalEdit.setImage(new Image(new File("resources/edit_.png").toURI().toURL().toString()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void setWorkCon(WorkLayoutController work) {
		this.workCon=work;
	}
	
	public void setWorkPane(BorderPane workPane) {
		this.workPane=workPane;
	}
	
	@FXML
	public void handleEdit() {
		myself.setCenter(workPane);
		workCon.editMode();
	}

	@Override
	public void setPaneMyself(Pane pane) {
		BorderPane myself=(BorderPane) pane;
		this.myself=myself;
	}

	public void observableRcvCard(WordCard card) {
		workCon.observableRcvCard(card);
	}
	
	public void observableRcvFriend(String friend) {
		workCon.observableRcvFriend(friend);
	}
	
	public void observableDelFriend(String friend) {
		workCon.observableDelFriend(friend);
	}
	
	public void observableDelCard(WordCard card) {
		workCon.observableDelCard(card);
	}
	
	
	public void loadDataPersonal() {
		workCon.loadDataPersonal();
	}
}
