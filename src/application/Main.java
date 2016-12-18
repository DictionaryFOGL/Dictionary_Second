package application;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import redis.clients.util.Hashing;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.javafx.application.ParametersImpl;

import application.model.*;
import application.model.message.Message;
import application.util.*;
import application.view.*;
import serverAndThread.CSConstant;
import serverAndThread.DictionaryFOGLClient;

public class Main extends Application implements CSConstant{
	private final String host="127.0.0.1";
	private final int port=20123;
	private Main myself=this;
	private Stage primaryStage;
	private BorderPane baseLayout;
	private BorderPane startLayout;
	private BorderPane workLayout;
	private Controller tempControl;
	private BaseLayoutController baseCon;
	private DictionaryFOGLClient client;
	private HashMap<String, Boolean> hBaidu=new HashMap<String, Boolean>();
	private HashMap<String, Boolean> hBing=new HashMap<String, Boolean>();
	private HashMap<String, Boolean> hYoudao=new HashMap<String, Boolean>();
	private ObjectOutputStream objectToServer;
	
	public Main() {
		Task<Void> task=new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Socket socket=new Socket(host, port);
					objectToServer=new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream objectFromServer=new ObjectInputStream(socket.getInputStream());
					client=new DictionaryFOGLClient(myself, socket, objectFromServer, hBaidu, hBing, hYoudao);
					Thread socketHeard=new Thread(client);
					socketHeard.start();
				} catch (UnknownHostException e) {
					e.printStackTrace();
					InformationDialog.connectError();
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
					InformationDialog.socketError();
					System.exit(0);
				}
				return null;
			}
		};
		System.out.println("connected!");
		new Thread(task).start();
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("DictionaryPro");
		initBaseLayout();
		initStartLayout();
		setStart();
		initWorkLayout();
		Parameters p=ParametersImpl.getParameters(this);
	}
	
	public void initBaseLayout() {
		try {
			FXMLLoadBase("view/BaseLayout.fxml",0);
			Scene scene=new Scene(baseLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					if(myself.client.getUser() != null)
						myself.writeToServer(new Message(LOGOUT));
					System.exit(0);
				}
			});
		} catch (IOException e) {
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
			baseCon.setWorkCon(controller);
			
			AnchorPane word=FXMLLoadBase("view/WordLayout.fxml");
			WordLayoutController c1=(WordLayoutController) tempControl;
			controller.setWordControl(c1);
			AnchorPane friend=FXMLLoadBase("view/FriendLayout.fxml");
			FriendLayoutController c2=(FriendLayoutController) tempControl;
			controller.setFriControl(c2);
			AnchorPane personal=FXMLLoadBase("view/PersonalEditLayout.fxml");
			PersonalEditLayoutController c3=(PersonalEditLayoutController) tempControl;
			controller.setPersonControl(c3);
			
			controller.setChildPane(word, friend, personal);
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
			baseCon=(BaseLayoutController) c;
			c.setPaneMyself(baseLayout);
		} else if(local == 1) {
			c.setPaneMyself(startLayout);
		} else if(local == 2) {
			baseCon.setWorkPane(workLayout);
			c.setPaneMyself(workLayout);
		}
		return c;
	}
	
	private AnchorPane FXMLLoadBase(String source) throws IOException {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource(source));
		AnchorPane pane=(AnchorPane) loader.load();
		Controller c=loader.getController();
		tempControl=c;
		c.setMain(this);
		c.setPaneMyself(pane);
		return pane;
	}
	
	public void setStart() {
		baseLayout.setCenter(startLayout);
		
	}
	
	public void setWork() {
		baseLayout.setCenter(workLayout);
	}
	
	public void searchEmptyMode() {
		
	}
	
	public void loadUILoginData() {
		baseCon.loadDataPersonal();
	}
	
	public User getUser() {
		return client.getUser();
	}
	
	public void changeHistory(String key,int site,boolean likeornot) {
		if(site == 2) hYoudao.put(key, likeornot);
		else if(site == 1) hBing.put(key, likeornot);
		else hBaidu.put(key, likeornot);
	}
	
	public boolean getYoudaoHistory(String key) {
		if(!hYoudao.containsKey(key)) return false;
		return hYoudao.get(key);
	}
	
	public boolean getBingHistory(String key) {
		if(!hBing.containsKey(key)) return false;
		return hBing.get(key);
	}
	
	public boolean getBaiduHistory(String key) {
		if(!hBaidu.containsKey(key)) return false;
		return hBaidu.get(key);
	}
	
	public void writeToServer(Message m) {
		try {
			objectToServer.writeObject(m);
		} catch (IOException e) {
			System.out.println("objectToServer error");
			e.printStackTrace();
		}
	}
	
	public void showSendCardDialog(Word spell,int site) {
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/SendCard.fxml"));
			AnchorPane page=(AnchorPane) loader.load();
			
			Stage dialog=new Stage();
			dialog.setTitle("SendWordCard");
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(primaryStage);
			Scene scene=new Scene(page);
			dialog.setScene(scene);
			
			SendCardController controller=loader.getController();
			controller.setMain(this);
			controller.setDialogStage(dialog);
			controller.setWordAndSite(spell,site);
			
			dialog.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
