package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	private Scene signUpscene;

	public void setSignUpScene() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/signup.fxml"));
		signUpscene = new Scene(root);
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
		try {
			setSignUpScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void hi(ActionEvent actionEvent) {
		if (actionEvent.getSource().equals(login)) {
			if (user_email.getText().isEmpty() || user_password.getText().isEmpty()) {
				System.out.println("d�zg�n veri girin");
			} else {
				Stage stage = (Stage) login.getScene().getWindow();
				stage.setScene(signUpscene);
			}
		} else if (actionEvent.getSource().equals(forget_password)) {
			System.out.println("mal �ifre unutulur mu :D");
		} else {
			System.out.println("sign");
		}

	}

}
