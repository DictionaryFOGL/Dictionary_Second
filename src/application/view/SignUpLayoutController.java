package application.view;

import application.Main;
import application.model.message.LoginMessage;
import application.util.Controller;
import application.util.Encryption;
import application.util.InformationDialog;
import application.util.ValidInput;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import serverAndThread.CSConstant;

public class SignUpLayoutController implements Controller,CSConstant{
	private Main mainApp;
	private ToggleGroup group;
	
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	
	@FXML
	private TextField name;
	@FXML
	private PasswordField pwd1;
	@FXML
	private PasswordField pwd2;

	@FXML
	private RadioButton male;
	@FXML
	private RadioButton female;
	@FXML
	private RadioButton secret;
	
	public void setMain(Main mainApp) {
		this.mainApp=mainApp;
	}

	@Override
	public void setPaneMyself(Pane pane) {}
	
	@FXML
	private void initialize() {
		group=new ToggleGroup();
		male.setToggleGroup(group);
		female.setToggleGroup(group);
		secret.setToggleGroup(group);
	}
	
	@FXML
	private void cancel() {
		name.setText("");
		pwd1.setText("");
		pwd2.setText("");
		male.setSelected(false);
		female.setSelected(false);
		secret.setSelected(false);
	}
	
	@FXML
	private void ok() {
		String Name=name.getText();
		String pwdh=pwd1.getText();
		boolean check=ValidInput.validUsername(Name);
		if(!check) {
			InformationDialog.invalidUserName();
			return;
		}
		check=ValidInput.validPwd(pwdh);
		if(!check) {
			InformationDialog.invalidPwd();
			return;
		}
		if(!pwdh.equals(pwd2.getText())) {
			InformationDialog.differentPwd();
			return;
		}
		char gender='s';
		if(male.isSelected()) gender='m';
		else if(female.isSelected()) gender='f';
		System.out.println(name.getText()+" "+gender+" "+Encryption.MD5(pwdh));
		LoginMessage message=new LoginMessage(REGISTER, name.getText(), pwdh, gender);
		mainApp.writeToServer(message);
		cancel();
	}
}
