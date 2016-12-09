package application.view;

import application.Main;
import application.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SignUpLayoutController implements Controller{
	private Main mainApp;
	
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	
	@FXML
	private TextField name;
	@FXML
	private TextField pwd1;
	@FXML
	private TextField pwd2;

	@FXML
	private RadioButton male;
	@FXML
	private RadioButton female;
	@FXML
	private RadioButton secret;
	
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}

	@Override
	public void setPaneMyself(Pane pane) {
		// TODO Auto-generated method stub
		
	}
}
