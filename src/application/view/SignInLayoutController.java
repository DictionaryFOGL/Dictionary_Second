package application.view;

import application.Main;
import application.model.message.LoginMessage;
import application.util.Controller;
import application.util.Encryption;
import application.util.InformationDialog;
import application.util.ValidInput;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import serverAndThread.CSConstant;

public class SignInLayoutController implements Controller,CSConstant{
	private Main mainApp;
	
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	
	@FXML
	private TextField name;
	@FXML
	private PasswordField pwd;
	@FXML
	private TextField check;
	
	@FXML
	private Label vertification;
	
	@Override
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
		vertification.setText(ValidInput.randomVerify());
		
	}

	@Override
	public void setPaneMyself(Pane pane) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void ok() {
		String Name=name.getText();
		String Pwd=pwd.getText();
		String verify=vertification.getText().toLowerCase();
		if(!check.getText().toLowerCase().equals(verify)) {
			InformationDialog.invalidCheck();
			vertification.setText(ValidInput.randomVerify());
			check.setText("");
			return;
		}
		if(!ValidInput.validUsername(Name)) {
			InformationDialog.invalidUserName();
			name.setText("");
			return;
		}
		if(!ValidInput.validPwd(Pwd)) {
			InformationDialog.invalidPwd();
			pwd.setText("");
			return;
		}
		LoginMessage message=new LoginMessage(LOGIN, Name, Encryption.MD5(Pwd), ' ');
		mainApp.writeToServer(message);
	}
	
	@FXML
	private void cancel() {
		name.setText("");
		check.setText("");
		pwd.setText("");
		vertification.setText(ValidInput.randomVerify());
	}
	
	@FXML
	private void change() {
		vertification.setText(ValidInput.randomVerify());
	}
}
