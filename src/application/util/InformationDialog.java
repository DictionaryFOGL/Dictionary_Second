package application.util;

import java.util.Optional;

import application.model.WordCard;
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
	public static boolean deleteCard(WordCard card) {
		Alert alert=new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("sender: "+card.getSenderName()+"\nwords: "+card.getWord().getWords()+"\nsay: "+card.getSaySomething());
		alert.setContentText("Are you sure to delete the card?");
		Optional<ButtonType> result=alert.showAndWait();
		if(result.get() == ButtonType.OK) return true;
		else return false;
	}
	public static boolean deleteFriend(String name) {
		Alert alert=new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Friend: "+name);
		alert.setContentText("Are you sure to delete your friend?");
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
	public static void copied(String content) {
		baseAlert(AlertType.INFORMATION,"Copy",content,"The content above has been copied");
	}
	public static void picGet(String name) {
		baseAlert(AlertType.INFORMATION,"Picture","name: "+name,"The snapshot has been saved");
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
	public static void pwdChangFailed() {
		baseAlert(AlertType.ERROR, "New password not save", null, "Password save failed! Please try again");
	}
	public static void pwdChanged() {
		baseAlert(AlertType.INFORMATION, "Saved!", null, "Your new password has been saved!");
	}
	public static void wrongOldPwd() {
		baseAlert(AlertType.ERROR, "Password error", null, "Old password error!");
	}
	public static void sayTooMuch() {
		baseAlert(AlertType.INFORMATION, "Invalid", "Too much words", "10 characters most!");
	}
	public static void noBodySent() {
		baseAlert(AlertType.INFORMATION, "SendNothing", null, "No friend has been choosen!");
	}
	public static void invalidInputForUsr() {
		baseAlert(AlertType.INFORMATION, "Empty", null, "Empty Username!");
	}
	private static void baseAlert(AlertType type,String title,String headerText,String contentText) {
		Alert alert=new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
