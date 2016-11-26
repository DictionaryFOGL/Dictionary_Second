package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import com.sun.javafx.application.ParametersImpl;

import application.model.*;
import application.util.*;
import application.view.*;

public class Main extends Application {
	private Stage primaryStage;
	private BorderPane baseLayout;
	private BorderPane startLayout;
	private BorderPane workLayout;
	
	public Main() {
		
	}
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("DictionaryPro");
		initBaseLayout();
		initStartLayout();
		setStart();
		initWorkLayout();
		staticResourceLoad();
		Parameters p=ParametersImpl.getParameters(this);
	}
	
	public void initBaseLayout() {
		try {
			FXMLLoader loaderb=new FXMLLoader();
			loaderb.setLocation(Main.class.getResource("view/BaseLayout.fxml"));
			baseLayout=(BorderPane) loaderb.load();
			BaseLayoutController controller1=loaderb.getController();
			controller1.setMain(this);
			Scene scene=new Scene(baseLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void initStartLayout() {
		try {
			FXMLLoader loaders=new FXMLLoader();
			loaders.setLocation(Main.class.getResource("view/StartLayout.fxml"));
			startLayout=(BorderPane) loaders.load();
			StartLayoutController controller2=loaders.getController();
			controller2.setMain(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initWorkLayout() {
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/WorkLayout.fxml"));
			workLayout=(BorderPane) loader.load();
			WorkLayoutController controller3=loader.getController();
			controller3.setMain(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void staticResourceLoad() {
		
	}
	
	public void setStart() {
		baseLayout.setCenter(startLayout);
	}
	
	public void setWork() {
		baseLayout.setCenter(workLayout);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
