package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private TextField memberName;

	@FXML
	private PasswordField password;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	void forgetPassword() {

	}

	@FXML
	void login() {
		//accounts -> fxml
		memberName.getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/GeneralManagerMain.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.setScene(new Scene(root));
		stage.setTitle("Home");
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	void signUp() {
		memberName.getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/signup.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.setScene(new Scene(root));
		stage.setTitle("Sign Up");
		stage.setResizable(false);
		stage.show();
	}

}
