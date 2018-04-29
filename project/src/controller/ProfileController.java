package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import entity.Account;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import main.MainClass;

public class ProfileController implements Initializable {

	@FXML
	private JFXTextField txtName;

	@FXML
	private JFXToggleButton toggleRegulation;

	@FXML
	private JFXTextField txtSurname;

	@FXML
	private JFXTextField txtPhoneNumber;

	@FXML
	private JFXDatePicker txtBirthDate;

	@FXML
	private JFXPasswordField txtOldPasswd;

	@FXML
	private JFXPasswordField txtNewPasswd;

	@FXML
	private JFXPasswordField txtNewPasswdAgain;

	private EntityManager entityManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// entityManager = EntityManagerUtility.createEntityManager();
		Account account = MainClass.account;
		txtName.setText(account.getFirstname());
		txtSurname.setText(account.getLastname());
		txtBirthDate.setValue(account.getBirthDate().toLocalDate());
		txtPhoneNumber.setText(String.valueOf(account.getPhoneNumber()));
	}

	@FXML
	private void changeRegulationMode() {
		if (toggleRegulation.isSelected()) {
			txtName.setEditable(true);
			txtSurname.setEditable(true);
			txtPhoneNumber.setEditable(true);
			txtBirthDate.setEditable(true);
			txtOldPasswd.setEditable(true);
			txtNewPasswd.setEditable(true);
			txtNewPasswdAgain.setEditable(true);
		} else {
			txtName.setEditable(false);
			txtSurname.setEditable(false);
			txtPhoneNumber.setEditable(false);
			txtBirthDate.setEditable(false);
			txtOldPasswd.setEditable(false);
			txtNewPasswd.setEditable(false);
			txtNewPasswdAgain.setEditable(false);
		}
	}

	@FXML
	private void save() {
		ButtonType cancelButton = new ButtonType("Hayır", ButtonData.NO);
		ButtonType logoutButton = new ButtonType("Evet", ButtonData.YES);
		Alert logoutAlert = new Alert(AlertType.CONFIRMATION, "Değişiklikleri kaydetmek istiyor musunuz?", cancelButton,
				logoutButton);
		logoutAlert.headerTextProperty().set(null);
		logoutAlert.setTitle("Profilde değişiklik yapılıyor!");
		Optional<ButtonType> result = logoutAlert.showAndWait();
		result.ifPresent(buttonData -> {
			if (buttonData.getButtonData() == ButtonData.YES) {
				// sql-update
				
			}
		});
	}

}
