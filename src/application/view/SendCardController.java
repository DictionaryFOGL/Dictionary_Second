package application.view;

import application.Main;
import application.util.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class SendCardController implements Controller{
	private Main mainApp;
	@FXML
	private ListView<String> friend;
	
	@FXML
	private Text sendTo;
	@FXML
	private Text say;
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
	}

	@Override
	public void setPaneMyself(Pane pane) {
		// TODO Auto-generated method stub
		
	}
}
