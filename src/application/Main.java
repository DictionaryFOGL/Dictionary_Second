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
			FXMLLoadBase("view/BaseLayout.fxml",0);
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
			StartLayoutController controller2=(StartLayoutController) FXMLLoadBase("view/StartLayout.fxml",1);
			controller2.setBase(baseLayout);
			
			AnchorPane signIn=FXMLLoadBase("view/SignInLayout.fxml");
			AnchorPane signUp=FXMLLoadBase("view/SignUpLayout.fxml");
			AnchorPane about=FXMLLoadBase("view/AboutLayout.fxml");
			
			controller2.setChildPane(signIn, signUp, about);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initWorkLayout() {
		try {
			WorkLayoutController controller=(WorkLayoutController) FXMLLoadBase("view/WorkLayout.fxml",2);
			
			AnchorPane word=FXMLLoadBase("view/WordLayout.fxml");
			AnchorPane friend=FXMLLoadBase("view/FriendLayout.fxml");
			
			controller.setChildPane(word, friend);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Controller FXMLLoadBase(String source,int local) throws IOException {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource(source));
		if(local == 0) {
			baseLayout=(BorderPane) loader.load();
		} else if(local == 1) {
			startLayout=(BorderPane) loader.load();
		} else if(local == 2) {
			workLayout=(BorderPane) loader.load();
		}
		Controller c=loader.getController();
		c.setMain(this);
		if(local == 0) {
			c.setPaneMyself(baseLayout);
		} else if(local == 1) {
			c.setPaneMyself(startLayout);
		} else if(local == 2) {
			c.setPaneMyself(workLayout);
		}
		return c;
	}
	
	private AnchorPane FXMLLoadBase(String source) throws IOException {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource(source));
		AnchorPane pane=(AnchorPane) loader.load();
		Controller c=loader.getController();
		c.setMain(this);
		c.setPaneMyself(pane);
		return pane;
	}
	
	public void staticResourceLoad() {
		
	}
	
	public void setStart() {
		baseLayout.setCenter(startLayout);
		
	}
	
	public void setWork() {
		baseLayout.setCenter(workLayout);
	}
	
	public void searchEmptyMode() {
		
	}
	
	public void searchWordSuccessMode() {
		
	}

	public void searchWordFailedMode() {
		
	}
	
	public void searchFriendSuccessMode() {
		
	}
	
	public void searchFriendFailMode() {
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
