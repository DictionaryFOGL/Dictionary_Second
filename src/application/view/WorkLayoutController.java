package application.view;

import application.Main;
import javafx.scene.layout.BorderPane;

public class WorkLayoutController extends BorderPane{
	private Main mainApp;
	
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}
}
