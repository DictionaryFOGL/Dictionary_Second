package application.view;

import application.Main;
import javafx.scene.layout.BorderPane;

public class StartLayoutController extends BorderPane{
	private Main mainApp;
	
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}
}
