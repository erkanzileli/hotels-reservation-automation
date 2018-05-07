package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import entity.Account;
import entity.Company;
import entity.GeneralManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utility.EntityManagerUtility;

public class CreateCompanyController implements Initializable {

	@FXML
	private JFXTextField txtCompanyName;

	@FXML
	private JFXTextField txtManagerSurname;

	@FXML
	private JFXTextField txtManagerName;

	@FXML
	private JFXTextField txtManagerTC;

	@FXML
	private JFXTextField txtManagerMemberName;

	@FXML
	private JFXPasswordField txtManagerPasswordAgain;

	@FXML
	private JFXPasswordField txtManagerPassword;

	private EntityManager entityManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entityManager = EntityManagerUtility.createEntityManager();

	}

	@FXML
	void save() {
		if (validateForm()) {
			Company company = new Company(txtCompanyName.getText().trim());
			Account account = new Account(txtManagerName.getText().trim(), txtManagerSurname.getText().trim(),
					txtManagerMemberName.getText().trim(), txtManagerPassword.getText().trim(), "GENERALMANAGER");
			entityManager.getTransaction().begin();
			entityManager.persist(account);
			entityManager.persist(company);
			entityManager.getTransaction().commit();
			GeneralManager gm = new GeneralManager(Long.parseLong(txtManagerTC.getText().trim()),
					account.getIdAccount(), company.getIdCompany());
			entityManager.getTransaction().begin();
			entityManager.persist(gm);
			entityManager.getTransaction().commit();
			SystemManagerMainController.newCompanyDialog.close();
		}
	}

	private boolean validateForm() {
		if (txtCompanyName.getText().trim().length() > 0 && txtCompanyName.getText().trim().length() < 50) {
			if (txtManagerMemberName.getText().trim().length() > 0
					&& txtManagerMemberName.getText().trim().length() < 20) {
				Query query = entityManager.createNativeQuery("select * from account where memberName=?1",
						Account.class);
				query.setParameter(1, txtManagerMemberName.getText().trim());
				if (query.getResultList().isEmpty()) {
					if (txtManagerTC.getText().trim().length() > 0) {
						long tc = 0;
						try {
							tc = Long.parseLong(txtManagerTC.getText().trim());
						} catch (Exception e) {
						}
						if (tc != 0) {
							query = entityManager.createNativeQuery("select * from generalmanager where tc=?1");
							query.setParameter(1, tc);
							if (query.getResultList().isEmpty()) {
								if (txtManagerName.getText().trim().length() > 0
										&& txtManagerName.getText().trim().length() < 50) {
									if (txtManagerSurname.getText().trim().length() > 0
											&& txtManagerSurname.getText().trim().length() < 50) {
										if (txtManagerPassword.getText().trim().length() > 0
												&& txtManagerPassword.getText().trim().length() < 16) {
											if (txtManagerPassword.getText().trim()
													.equals(txtManagerPasswordAgain.getText().trim())) {
												return true;
											} else {
												createAlert(AlertType.ERROR, "Hata", null, "Þifreler eþleþmiyor.")
														.show();
												return false;
											}
										} else {
											createAlert(AlertType.ERROR, "Hata", null,
													"Maksimum 15 haneli bir þifre girin.").show();
											return false;
										}
									} else {
										createAlert(AlertType.ERROR, "Hata", null,
												"Maksimum 49 karakterden oluþan bir soyisim girin.").show();
										return false;
									}
								} else {
									createAlert(AlertType.ERROR, "Hata", null,
											"Maksimum 49 karakterden oluþan bir isim girin.").show();
									return false;
								}
							} else {
								createAlert(AlertType.ERROR, "Hata", null, "Bu T.C. kimlik numarasý kullanýmda.")
										.show();
								return false;
							}
						} else {
							createAlert(AlertType.ERROR, "Hata", null, "T.C. numarasý 11 rakamdan oluþmalý.").show();
							return false;
						}
					} else {
						createAlert(AlertType.ERROR, "Hata", null, "Bir T.C. kimlik numarasý girin.").show();
						return false;
					}
				} else {
					createAlert(AlertType.ERROR, "Hata", null, "Bu kullanýcý adý kullanýmda.").show();
					return false;
				}
			} else {
				createAlert(AlertType.ERROR, "Hata", null, "Maksimum 19 karakterlik bir kullanýcý adý girin.").show();
				return false;
			}
		} else {
			createAlert(AlertType.ERROR, "Hata", null, "Maksimum 49 karakterlik bir þirket adý girin.").show();
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
}
