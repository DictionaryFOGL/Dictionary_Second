package application.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class InformationDialog {
	public static void sendSucceeded() {
		baseAlert(AlertType.INFORMATION,"Congratulation!",null,"Your word card has been sent");
	}
	public static void existName() {
		baseAlert(AlertType.ERROR,"ExistedName",null,"The name has been used! Please choose another one!");
	}
	public static void loginFailed() {
		baseAlert(AlertType.ERROR,"Error!","Login failed","Check your username or password again!\nOr maybe you have landed on other clients");
	}
	public static boolean logoutCheck() {
		Alert alert=new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure to logout?");
		Optional<ButtonType> result=alert.showAndWait();
		if(result.get() == ButtonType.OK) return true;
		else return false;
	}
	public static void registerSucceeded() {
		baseAlert(AlertType.INFORMATION,"Congratulation!",null,"You have a new account!");
	}
	public static void operationSucceeded() {
		baseAlert(AlertType.INFORMATION,"Congratulation!",null,"Operation succeeded!");
	}
	public static void connectError() {
		baseAlert(AlertType.WARNING,"UnknownHostException",null,"Fail to connect to server!");
	}
	public static void socketError() {
		baseAlert(AlertType.WARNING,"IOException",null,"Fail to get socket stream");
	}
	public static void invalidUserName() {
		baseAlert(AlertType.WARNING,"InvalidUserName",null,"Username: 2 to 10 letters or numbers");
	}
	public static void differentPwd() {
		baseAlert(AlertType.WARNING,"Password",null,"The passwords you entered must be the same");
	}
	public static void invalidPwd() {
		baseAlert(AlertType.WARNING,"Password",null,"Password: 4 to 16 letters or numbers");
	}
	public static void invalidCheck() {
		baseAlert(AlertType.WARNING, "Verification", null, "Verification code is not correct");
	}
	private static void baseAlert(AlertType type,String title,String headerText,String contentText) {
		Alert alert=new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}