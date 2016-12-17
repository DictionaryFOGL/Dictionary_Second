package application.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class InformationDialog {
	public static void sendSucceeded() {
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("Congratulation!");
		alert.setHeaderText(null);
		alert.setContentText("Your word card has been sent");
		alert.showAndWait();
	}
	public static void existName() {
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("ExistedName!");
		alert.setHeaderText(null);
		alert.setContentText("The name has been used! Please choose another one!");
		alert.showAndWait();
	}
	public static void loginFailed() {
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setHeaderText("Login failed");
		alert.setContentText("Check your username or password again!\nOr maybe you have landed on other clients");
		alert.showAndWait();
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
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("Congratulation!");
		alert.setHeaderText(null);
		alert.setContentText("You have a new account!");
		alert.showAndWait();
	}
	public static void operationSucceeded() {
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("Congratulation!");
		alert.setHeaderText(null);
		alert.setContentText("Operation succeeded!");
		alert.showAndWait();
	}
	public static void connectError() {
		Alert alert=new Alert(AlertType.WARNING);
		alert.setTitle("UnknownHostException");
		alert.setHeaderText(null);
		alert.setContentText("Fail to connect to server!");
		alert.showAndWait();
	}
	public static void socketError() {
		Alert alert=new Alert(AlertType.WARNING);
		alert.setTitle("IOException");
		alert.setHeaderText(null);
		alert.setContentText("Fail to get socket stream");
		alert.showAndWait();
	}
	public static void invalidUserName() {
		Alert alert=new Alert(AlertType.WARNING);
		alert.setTitle("InvalidUserName");
		alert.setHeaderText(null);
		alert.setContentText("Username: 2 to 10 letters or numbers");
		alert.showAndWait();
	}
	public static void differentPwd() {
		Alert alert=new Alert(AlertType.WARNING);
		alert.setTitle("Password");
		alert.setHeaderText(null);
		alert.setContentText("The passwords you entered must be the same");
		alert.showAndWait();
	}
	public static void invalidPwd() {
		Alert alert=new Alert(AlertType.WARNING);
		alert.setTitle("Password");
		alert.setHeaderText(null);
		alert.setContentText("Password: 4 to 16 letters or numbers");
		alert.showAndWait();
	}
}
