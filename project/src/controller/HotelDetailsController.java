package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import entity.Account;
import entity.Address;
import entity.Employee;
import entity.LocalManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import model.PaneModel;
import utility.EntityManagerUtility;

public class HotelDetailsController implements Initializable {

	@FXML
	private JFXTextField txtProvince;

	@FXML
	private JFXTextField txtDistrict;

	@FXML
	private JFXTextField txtName;

	@FXML
	private JFXTextField txtPoint;

	@FXML
	private JFXTextField txtRoomCount;

	@FXML
	private JFXTextField txtBedCount;

	@FXML
	private JFXTextField txtEmployeeCount;

	@FXML
	private JFXTextField txtCleanerCount;

	@FXML
	private JFXTextField txtReceptionistCount;

	@FXML
	private JFXTextField txtManagerMemberName;

	@FXML
	private JFXTextField txtManagerName;

	@FXML
	private JFXTextField txtManagerSurname;

	@FXML
	private JFXTextField txtManagerTC;

	@FXML
	private JFXPasswordField txtPassword;

	@FXML
	private JFXPasswordField txtPasswordAgain;

	@FXML
	private JFXButton btnSaveManager;

	@FXML
	private JFXButton btnManagerAdd;

	@FXML
	private JFXButton btnManagerRemove;

	public static PaneModel selectedHotel;

	private EntityManager entityManager;

	private Account managerAccount;

	private LocalManager manager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entityManager = EntityManagerUtility.createEntityManager();
		Address address = getAddress(selectedHotel.getIdHotel());
		int roomCount = getRoomCount();
		int bedCount = getBedCount();
		int[] employees = getEmployeeCount();

		// kutucuklarin doldurulmasi
		txtName.setText(selectedHotel.getHotelName());
		txtProvince.setText(address.getProvince());
		txtDistrict.setText(address.getDistrict());
		txtRoomCount.setText(String.valueOf(roomCount));
		txtBedCount.setText(String.valueOf(bedCount));
		txtReceptionistCount.setText(String.valueOf(employees[0]));
		txtCleanerCount.setText(String.valueOf(employees[1]));
		txtEmployeeCount.setText(String.valueOf(employees[2]));
		// mudur var ise
		manager = getLocalManager();
		if (manager != null) {
			managerAccount = getLocalManagerAccount(manager.getIdAccount());
			txtManagerMemberName.setText(managerAccount.getMemberName());
			txtManagerName.setText(managerAccount.getFirstname());
			txtManagerSurname.setText(managerAccount.getLastname());
			txtManagerTC.setText(String.valueOf(manager.getTc()));
			btnManagerAdd.setVisible(false);
			btnManagerRemove.setVisible(true);
		} else {
			btnManagerAdd.setVisible(true);
			btnManagerRemove.setVisible(false);
		}

	}

	private int[] getEmployeeCount() {
		// calisan saylarinin hesaplanmasi
		int[] employees = new int[3];
		Query query = entityManager.createNativeQuery("SELECT * FROM Employee WHERE idHotel=?1", Employee.class);
		query.setParameter(1, selectedHotel.getIdHotel());
		List<Employee> result = query.getResultList();
		if (!result.isEmpty()) {
			for (Employee employee : result) {
				if ("Resepsiyonist".equals(employee.getType()))
					employees[0]++;
				if ("Temizlikçi".equals(employee.getType()))
					employees[1]++;
			}
			employees[2] = result.size();
		}
		return employees;
	}

	private int getBedCount() {
		// yatak sayisinin hesaplanmasi
		Query query = entityManager.createNativeQuery(
				"select idBed from bed INNER JOIN room r ON bed.idRoom = r.idRoom where idHotel=?1;");
		query.setParameter(1, selectedHotel.getIdHotel());
		return query.getResultList().size();
	}

	private int getRoomCount() {
		// oda sayilarinin hesaplanmasi
		Query query = entityManager.createNativeQuery("SELECT idRoom FROM Room WHERE idHotel=?1");
		query.setParameter(1, selectedHotel.getIdHotel());
		return query.getResultList().size();
	}

	private Account getLocalManagerAccount(int idAccount) {
		Query query = entityManager.createNativeQuery("SELECT * FROM Account WHERE idAccount=?1", Account.class);
		query.setParameter(1, idAccount);
		if (!query.getResultList().isEmpty())
			return (Account) query.getResultList().get(0);
		else
			return null;
	}

	private LocalManager getLocalManager() {
		// mudur bilgilerinin cekilmesi
		Query query = entityManager.createNativeQuery("SELECT * FROM LocalManager WHERE idHotel=?1",
				LocalManager.class);
		query.setParameter(1, selectedHotel.getIdHotel());
		if (!query.getResultList().isEmpty())
			return (LocalManager) query.getResultList().get(0);
		else
			return null;
	}

	private Address getAddress(int idHotel) {
		// adres bilgilerinin cekilmesi
		Query query = entityManager.createNativeQuery("SELECT * FROM Address WHERE idHotel=?1", Address.class);
		query.setParameter(1, idHotel);
		if (!query.getResultList().isEmpty())
			return (Address) query.getResultList().get(0);
		else
			return null;
	}

	// alarm diyalogu olusturma
	private Alert createAlert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	// boyle kodlar yazmayin
	private boolean validateManagerInsert() {
		String memberName = txtManagerMemberName.getText().trim();
		if (!memberName.equals("")) {
			Query query = entityManager.createNativeQuery("SELECT * FROM Account WHERE memberName=?1", Account.class);
			query.setParameter(1, memberName);
			if ((query.getResultList().isEmpty())) {
				if (txtManagerTC.getText().trim().length() == 11) {
					long tc = 0;
					try {
						tc = Long.parseLong(txtManagerTC.getText().trim());
					} catch (Exception e) {
					}
					if (tc != 0) {
						query = entityManager.createNativeQuery("SELECT * FROM LocalManager WHERE tc=?1",
								LocalManager.class);
						query.setParameter(1, tc);
						query.setParameter(2, GeneralManagerMainController.generalManager.getIdAccount());
						if (query.getResultList().isEmpty()) {
							if (!txtPassword.getText().trim().equals("")
									&& !txtPasswordAgain.getText().trim().equals("")) {
								if (txtPassword.getText().trim().equals(txtPasswordAgain.getText().trim())) {
									if (!txtManagerName.getText().trim().equals("")
											&& !txtManagerSurname.getText().trim().equals("")) {
										return true;
									} else {
										createAlert(AlertType.ERROR, "Hata", null, "Ýsim ve soyisim boþ býrakýlamaz.")
												.show();
										return false;
									}
								} else {
									createAlert(AlertType.ERROR, "Hata", null, "Þifreler uyuþmuyor.").show();
									return false;
								}
							} else {
								createAlert(AlertType.ERROR, "Hata", null, "Þifre boþ geçilemez.").show();
								return false;
							}
						} else {
							createAlert(AlertType.ERROR, "Hata", null,
									"Bu T.C. kimlik numarasýna sahip bir müdür þirketinizde mevcut.").show();
							return false;
						}
					} else {
						createAlert(AlertType.ERROR, "Hata", null, "T.C. kimlik numarasý rakamlardan oluþmalý.").show();
						return false;
					}
				} else {
					createAlert(AlertType.ERROR, "Hata", null, "T.C. kimlik numarasý 11 haneli olmalý.").show();
					return false;
				}
			} else {
				createAlert(AlertType.ERROR, "Hata", null, "Bu kullanýcý adý zaten sistemde kayýtlý.").show();
				return false;
			}
		} else {
			createAlert(AlertType.ERROR, "Hata", null, "Kullanýcý adý boþ olmamalý.").show();
			return false;
		}
	}

	@FXML
	void save() {
		if (validateManagerInsert()) {
			Account account = new Account(txtManagerName.getText().trim(), txtManagerSurname.getText().trim(),
					txtManagerMemberName.getText().trim(), txtPassword.getText().trim(), "LOCALMANAGER");
			entityManager.getTransaction().begin();
			entityManager.persist(account);
			entityManager.getTransaction().commit();
			LocalManager localManager = new LocalManager(Long.parseLong(txtManagerTC.getText().trim()),
					account.getIdAccount(), selectedHotel.getIdHotel(),
					GeneralManagerMainController.generalManager.getIdCompany());
			Query query = entityManager.createNativeQuery(
					"INSERT INTO localmanager (tc, idAccount, idHotel, idCompany) VALUES (?1,?2,?3,?4)");
			query.setParameter(1, Long.parseLong(txtManagerTC.getText().trim()));
			query.setParameter(2, account.getIdAccount());
			query.setParameter(3, selectedHotel.getIdHotel());
			query.setParameter(4, GeneralManagerMainController.generalManager.getIdCompany());
			entityManager.getTransaction().begin();
			query.executeUpdate();
			entityManager.getTransaction().commit();
			GeneralManagerMainController.hotelDetailsDialog.close();
		}
	}

	@FXML
	void removeManager() {
		ButtonType cancelButton = new ButtonType("Ýptal", ButtonData.CANCEL_CLOSE);
		ButtonType okButton = new ButtonType("Sil", ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.CONFIRMATION, "Bu otelin müdürünü silmek istiyor musunuz?", cancelButton,
				okButton);
		alert.setTitle("Çýkýþ yapýlýyor!");
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();
		result.ifPresent(buttonData -> {
			if (buttonData.getButtonData() == ButtonData.OK_DONE) {
				Account account = entityManager.find(Account.class, managerAccount.getIdAccount());
				LocalManager localManager = entityManager.find(LocalManager.class, manager.getTc());
				entityManager.getTransaction().begin();
				entityManager.remove(account);
				entityManager.remove(localManager);
				entityManager.getTransaction().commit();
				GeneralManagerMainController.hotelDetailsDialog.close();
			}
		});
	}

	@FXML
	void newManager() {
		txtManagerMemberName.setDisable(false);
		txtManagerName.setDisable(false);
		txtManagerSurname.setDisable(false);
		txtManagerTC.setDisable(false);
		txtPassword.setDisable(false);
		txtPasswordAgain.setDisable(false);
		btnSaveManager.setDisable(false);
	}

}
