package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainClass;
import utility.EntityManagerUtility;

public class LoginController implements Initializable {

	@FXML
	private TextField memberName;

	@FXML
	private PasswordField password;

	private EntityManager entityManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entityManager = EntityManagerUtility.createEntityManager();
	}

	@FXML
	void forgetPassword() {

	}

	@FXML
	void login() {
		// accounts -> fxml
		String mName = memberName.getText().trim();
		String passwd = password.getText().trim();
		TypedQuery<Account> query = (TypedQuery<Account>) entityManager
				.createNativeQuery("SELECT * FROM Account WHERE MemberName=?1 AND password=?2", Account.class);
		query.setParameter(1, mName);
		query.setParameter(2, passwd);
		List<Account> result = query.getResultList();
		if (result.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "Böyle bir kullanýcý bulunamadý");
			alert.headerTextProperty().set(null);
			alert.titleProperty().set("Hata");
			alert.show();
		} else {
			Account user = result.get(0);
			MainClass.account = user;
			String type = user.getType();
			if ("SYSTEMMANAGER".equals(type)) {
				System.out.println("CreateSystemManagerPage");
			} else if ("GENERALMANAGER".equals(type)) {
				GeneralManagerMainController.generalManager.setIdAccount(user.getIdAccount());
				loadFXML("GeneralManagerMain.fxml");
			} else if ("LOCALMANAGER".equals(type)) {
				loadFXML("LocalManagerMain.fxml");
			} else if ("RECEPTIONIST".equals(type)) {
				loadFXML("ReceptionistMain.fxml");
			} else if ("CUSTOMER".equals(type)) {
				loadFXML("CustomerMain.fxml");
			} else {
				Alert alertNotFound = new Alert(AlertType.ERROR,
						"Bilinmeyen bir hata oluþtu. Saðlayýcýnýz ile iletiþime geçiniz");
				alertNotFound.headerTextProperty().set(null);
				alertNotFound.setTitle("Hata");
				alertNotFound.show();
			}
		}
	}

	private void loadFXML(String fxml) {
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/" + fxml));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.setScene(new Scene(root));
		stage.setTitle("Anasayfa");
		stage.setResizable(false);
		stage.show();
		memberName.getScene().getWindow().hide();
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
