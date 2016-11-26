package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class BaseLayoutController extends BorderPane{
	private Main mainApp;
	
	@FXML
	private Label test;
	
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}
	
	@FXML
	public void handleTest() {
		mainApp.setWork();
	}
}
