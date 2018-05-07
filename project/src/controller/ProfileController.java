package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXButton;
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
import utility.EntityManagerUtility;

public class ProfileController implements Initializable {

	@FXML
	private JFXTextField txtName;

	@FXML
	private JFXToggleButton toggleRegulation;

	@FXML
	private JFXTextField txtSurname;

	@FXML
	private JFXPasswordField txtOldPasswd;

	@FXML
	private JFXPasswordField txtNewPasswd;

	@FXML
	private JFXPasswordField txtNewPasswdAgain;

	@FXML
	private JFXButton btnSave;

	private EntityManager entityManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// entityManager = EntityManagerUtility.createEntityManager();
		entityManager = EntityManagerUtility.createEntityManager();
		Account account = MainClass.account;
		txtName.setText(account.getFirstname());
		txtSurname.setText(account.getLastname());
	}

	@FXML
	private void changeRegulationMode() {
		if (toggleRegulation.isSelected()) {
			btnSave.setDisable(false);
			txtName.setEditable(true);
			txtSurname.setEditable(true);
			txtOldPasswd.setEditable(true);
			txtNewPasswd.setEditable(true);
			txtNewPasswdAgain.setEditable(true);
		} else {
			btnSave.setDisable(true);
			txtName.setEditable(false);
			txtSurname.setEditable(false);
			txtOldPasswd.setEditable(false);
			txtNewPasswd.setEditable(false);
			txtNewPasswdAgain.setEditable(false);
		}
	}

	private boolean validateForm() {
		if (txtName.getText().trim().length() > 0 && txtName.getText().trim().length() < 50) {
			if (txtSurname.getText().trim().length() > 0 && txtSurname.getText().trim().length() < 50) {
				if (txtOldPasswd.getText().trim().length() > 0 && txtOldPasswd.getText().trim().length() < 15) {
					Query query = entityManager.createNativeQuery(
							"select * from account where idAccount=?1 and password=?2", Account.class);
					query.setParameter(1, MainClass.account.getIdAccount());
					query.setParameter(2, MainClass.account.getPassword());
					if (!query.getResultList().isEmpty()) {
						if (txtNewPasswd.getText().trim().length() > 0 && txtNewPasswd.getText().trim().length() < 16
								&& txtNewPasswdAgain.getText().trim().length() > 0
								&& txtNewPasswdAgain.getText().trim().length() < 15) {
							if (txtNewPasswd.getText().trim().equals(txtNewPasswdAgain.getText().trim())) {
								return true;
							} else {
								createAlert(AlertType.ERROR, "Hata", null, "Yeni þifreler eþleþmiyor.").show();
								return false;
							}
						} else {
							createAlert(AlertType.ERROR, "Hata", null, "Yeni þifreler 1-15 karakter arasýnda olmalý.")
									.show();
							return false;
						}
					} else {
						createAlert(AlertType.ERROR, "Hata", null, "Eski þifre doðru deðil.").show();
						return false;
					}
				} else {
					createAlert(AlertType.ERROR, "Hata", null, "1-15 karakterden oluþan eski þifrenizi girin.").show();
					return false;
				}
			} else {
				createAlert(AlertType.ERROR, "Hata", null, "Soyisim bilgisi 1-50 karakterden oluþmalýdýr.").show();
				return false;
			}
		} else {
			createAlert(AlertType.ERROR, "Hata", null, "Ýsim bilgisi 1-50 karakterden oluþmalýdýr.").show();
			return false;
		}
	}

	// alarm diyalogu olusturma
	private Alert createAlert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	@FXML
	private void save() {
		if (validateForm()) {
			ButtonType cancelButton = new ButtonType("Hayýr", ButtonData.NO);
			ButtonType logoutButton = new ButtonType("Evet", ButtonData.YES);
			Alert logoutAlert = new Alert(AlertType.CONFIRMATION, "Deðiþiklikleri kaydetmek istiyor musunuz?",
					cancelButton, logoutButton);
			logoutAlert.headerTextProperty().set(null);
			logoutAlert.setTitle("Profilde deðiþiklik yapýlýyor!");
			Optional<ButtonType> result = logoutAlert.showAndWait();
			result.ifPresent(buttonData -> {
				if (buttonData.getButtonData() == ButtonData.YES) {
					// update account
					Account account = entityManager.find(Account.class, MainClass.account.getIdAccount());
					entityManager.getTransaction().begin();
					account.setPassword(txtNewPasswd.getText().trim());
					account.setFirstname(txtName.getText().trim());
					account.setLastname(txtSurname.getText().trim());
					entityManager.getTransaction().commit();
					MainClass.account = account;
					createAlert(AlertType.INFORMATION, "Bilgi", "Ýþlem tamamlandý.",
							"Bilgileriniz istediðiniz þekilde güncellendi.").show();
				}
			});
		}
	}

}
