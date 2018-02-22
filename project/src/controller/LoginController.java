package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	private Scene signUpscene;

	public void setSignUpScene(Scene signUpScene) {
		signUpscene=signUpScene;
	}
	
	@FXML
	private Button login;
	
	@FXML
	private Button sign_up;
	
	@FXML
	private Button forget_password;

	@FXML
	private TextField user_email;
	
	@FXML
	private PasswordField user_password;

	public void initialize(URL location, ResourceBundle resources) {

	}
	public void hi(ActionEvent actionEvent) {
		if(actionEvent.getSource().equals(login)) {
			if (user_email.getText().isEmpty() || user_password.getText().isEmpty()) {
				System.out.println("düzgün veri girin");
			} else {
				Stage stage=(Stage) login.getScene().getWindow();
				stage.setScene(signUpscene);
			}
		}else if(actionEvent.getSource().equals(forget_password)) {
			System.out.println("mal þifre unutulur mu :D");
		}else {
			System.out.println("sign");
		}
		
		
	}

}
