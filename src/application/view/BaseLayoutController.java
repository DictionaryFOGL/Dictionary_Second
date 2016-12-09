package application.view;

import application.Main;
import application.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class BaseLayoutController implements Controller{
	private Main mainApp;
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
	
	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}
	
	@FXML
	public void handleTest() {
		mainApp.setWork();
	}

	@Override
	public void setPaneMyself(Pane pane) {
		// TODO Auto-generated method stub
		
	}
}
