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
		alert.setContentText("Check your username or password again!");
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
}
