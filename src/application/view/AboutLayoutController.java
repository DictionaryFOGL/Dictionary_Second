package application.view;

import java.io.IOException;

import application.Main;
import application.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AboutLayoutController implements Controller{
	private Main mainApp;
	
	@FXML
	private AnchorPane myself;
	@FXML
	private Button dream;
	@FXML
	private Button fog;
	@FXML
	private Button feedback;

	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}

	@Override
	public void setPaneMyself(Pane pane) {
		AnchorPane myself=(AnchorPane) pane;
		this.myself=myself;
	}
	
	@FXML
	private void fogGithub() {
		goBase("https://github.com/GARENFEATHER");
	}
	
	@FXML
	private void dreamgazerGithub() {
		goBase("https://github.com/dreamgazer");
	}
	
	@FXML
	private void feedback() {
		goBase("https://github.com/DictionaryFOGL/Dictionary_Second");
	}
	
	private void goBase(String url) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
