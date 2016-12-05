package application.view;

import application.Main;
import application.util.Controller;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AboutLayoutController implements Controller{
	private Main mainApp;
	private AnchorPane myself;

	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}

	@Override
	public void setPaneMyself(Pane pane) {
		AnchorPane myself=(AnchorPane) pane;
		this.myself=myself;
	}

}
