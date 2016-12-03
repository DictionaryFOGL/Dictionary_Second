package application.view;

import application.Main;
import application.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class StartLayoutController implements Controller{
	private BorderPane myself;
	public BorderPane baseLayout;
	
	public AnchorPane signInLayout;
	public AnchorPane signUpLayout;
	public AnchorPane aboutLayout;
	private Main mainApp;
	
	@FXML
	private Button signIn;
	@FXML
	private Button signUp;
	@FXML
	private Button about;
	@FXML
	private Button searchMode;
	
	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}
	
	public void setBase(BorderPane baseLayout) {
		this.baseLayout=baseLayout;
	}
	
	public void setChildPane(AnchorPane signInLayout,AnchorPane signUpLayout,AnchorPane aboutLayout) {
		this.signInLayout=signInLayout;
		this.signUpLayout=signUpLayout;
		this.aboutLayout=aboutLayout;
		startSignUpMode();
	}
	
	@FXML
	public void startSignInMode() {
		myself.setCenter(signInLayout);
	}
	
	@FXML
	public void startSignUpMode() {
		myself.setCenter(signUpLayout);
	}
	
	@FXML
	public void startSearchMode() {
		this.mainApp.setWork();
	}
	
	@FXML
	public void startAboutMode() {
		myself.setCenter(aboutLayout);
	}

	@Override
	public void setPaneMyself(Pane pane) {
		BorderPane myself=(BorderPane) pane;
		this.myself=myself;
	}
}
