package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import entity.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utility.EntityManagerUtility;

public class CreateEmployeeController implements Initializable {

	@FXML
	private JFXTextField txtMemberName;

	@FXML
	private JFXTextField txtName;

	@FXML
	private JFXTextField txtSurname;

	@FXML
	private JFXTextField txtTC;

	@FXML
	private JFXPasswordField txtPassword;

	@FXML
	private JFXPasswordField txtPasswordAgain;

	@FXML
	private JFXComboBox<String> comboPosition;

	private EntityManager entityManager;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entityManager = EntityManagerUtility.createEntityManager();
		comboPosition.getItems().addAll("Resepsiyonist", "Temizlikçi", "Diðer");
	}

	private boolean staffValidate() {
		if (comboPosition.getValue() != null) {
			if (txtTC.getText().trim().length() > 0 && txtTC.getText().trim().length() < 12) {
				long tc = 0;
				try {
					tc = Long.parseLong(txtTC.getText().trim());
				} catch (Exception e) {
				}
				if (tc != 0) {
					Query query = entityManager.createNativeQuery("select * from employee where tc=?1 and idHotel=?2");
					query.setParameter(1, tc);
					query.setParameter(2, LocalManagerMainController.thisLocalManager.getIdHotel());
					if (query.getResultList().size() == 0) {
						if (txtName.getText().trim().length() > 0 && txtSurname.getText().trim().length() > 0) {
							if (txtName.getText().trim().length() < 51 && txtSurname.getText().trim().length() < 51) {
								return true;
							} else {
								createAlert(AlertType.ERROR, "Hata", null, "Ýsim ve soyisim 50 haneyi geçemez.").show();
								return false;
							}
						} else {
							createAlert(AlertType.ERROR, "Hata", null, "Ýsim ve soyisim alanlarý doldurulmalý.").show();
							return false;
						}
					} else {
						createAlert(AlertType.ERROR, "Hata", null,
								"Otelinizde bu T.C. kimlik numarasýna sahip bir çalýþan zaten var.").show();
						return false;
					}

				} else {
					createAlert(AlertType.ERROR, "Hata", null, "T.C. kimlik numarasý 11 rakamadan oluþmalý.").show();
					return false;
				}
			} else {
				createAlert(AlertType.ERROR, "Hata", null, "T.C. kimlik numarasý 11 rakamadan oluþmalý.").show();
				return false;
			}
		} else {
			createAlert(AlertType.ERROR, "Hata", null, "Pozisyon seçiniz.").show();
			return false;
		}
	}

	private boolean receptionistValidate() {
		if (txtMemberName.getText().trim().length() > 0 && txtMemberName.getText().trim().length() < 21) {
			Query query = entityManager.createNativeQuery("SELECT * FROM Account WHERE memberName=?1");
			query.setParameter(1, txtMemberName.getText().trim());
			if (query.getResultList().isEmpty()) {
				if (txtPassword.getText().trim().equals(txtPasswordAgain.getText().trim())) {
					return true;
				} else {
					createAlert(AlertType.ERROR, "Hata", null, "Þifreler eþleþmiyor.").show();
					return false;
				}
			} else {
				createAlert(AlertType.ERROR, "Hata", null, "Bu kullanýcý adý sistemde zaten kayýtlý.").show();
				return false;
			}
		} else {
			createAlert(AlertType.ERROR, "Hata", null, "En fazla 20 haneli bir kullanýcý adý girin.").show();
			return false;
		}
	}

	@FXML
	void save() {
		if (staffValidate()) {
			String firstname = txtName.getText().trim();
			String lastname = txtSurname.getText().trim();
			long tc = Long.parseLong(txtTC.getText().trim());
			int idHotel = LocalManagerMainController.thisLocalManager.getIdHotel();
			String position = comboPosition.getValue();
			if (position.equals("Resepsiyonist")) {
				if (receptionistValidate()) {
					System.out.println("Resepsiyonist");
					String memberNamer = txtMemberName.getText().trim();
					String passwd = txtPassword.getText().trim();
					Account employeeAccount = new Account(firstname, lastname, memberNamer, passwd, position);
					entityManager.getTransaction().begin();
					entityManager.persist(employeeAccount);
					entityManager.getTransaction().commit();

					Query query = entityManager.createNativeQuery(
							"insert into employee(tc, idHotel, type, firstname, lastname, idAccount, idCompany) VALUES (?1,?2,?3,?4,?5,?6,?7)");
					query.setParameter(1, tc);
					query.setParameter(2, idHotel);
					query.setParameter(3, position);
					query.setParameter(4, firstname);
					query.setParameter(5, lastname);
					query.setParameter(6, employeeAccount.getIdAccount());
					query.setParameter(7, LocalManagerMainController.thisLocalManager.getIdCompany());
					entityManager.getTransaction().begin();
					query.executeUpdate();
					entityManager.getTransaction().commit();
					refreshHome();
				}
			} else {
				System.out.println("normal çalýþan");
				Query query = entityManager.createNativeQuery(
						"insert into employee(tc, idHotel, type, firstname, lastname,idCompany) VALUES (?1,?2,?3,?4,?5,?6)");
				query.setParameter(1, tc);
				query.setParameter(2, idHotel);
				query.setParameter(3, position);
				query.setParameter(4, firstname);
				query.setParameter(5, lastname);
				query.setParameter(6, LocalManagerMainController.thisLocalManager.getIdCompany());
				entityManager.getTransaction().begin();
				query.executeUpdate();
				entityManager.getTransaction().commit();
				refreshHome();
			}
		}
	}

	private void refreshHome() {
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/LocalManagerMain.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.setScene(new Scene(root));
		stage.setTitle("Anasayfa");
		stage.setResizable(false);
		stage.show();
		txtName.getScene().getWindow().hide();
	}

	@FXML
	void changePosition() {
		if (comboPosition.getValue().equals("Resepsiyonist")) {
			txtMemberName.setDisable(false);
			txtPassword.setDisable(false);
			txtPasswordAgain.setDisable(false);
		} else {
			txtMemberName.setDisable(true);
			txtPassword.setDisable(true);
			txtPasswordAgain.setDisable(true);
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
}
