package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import entity.Account;
import entity.Address;
import entity.Hotel;
import entity.LocalManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import utility.EntityManagerUtility;

public class CreateHotelController implements Initializable {

	@FXML
	private JFXDialogLayout root;

	@FXML
	private JFXComboBox<String> comboProvince;

	@FXML
	private JFXComboBox<String> comboDistrict;

	@FXML
	private JFXTextField txtPhoneNumber;

	@FXML
	private JFXTextField txtName;

	@FXML
	private JFXTextField txtManagerPassword;

	@FXML
	private JFXTextField txtTC;

	@FXML
	private JFXTextField txtManagerName;

	@FXML
	private JFXTextField txtManagerSurname;

	@FXML
	private JFXTextField txtManagerPasswordAgain;

	@FXML
	private JFXToggleButton toggleManagerForm;

	@FXML
	private JFXTextField txtManagerMemberName;

	private EntityManager entityManager;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entityManager = EntityManagerUtility.createEntityManager();
		fillComboProvince();
	}

	@FXML
	void changeManagerForm() {
		if (toggleManagerForm.isSelected()) {
			txtManagerName.setDisable(false);
			txtManagerSurname.setDisable(false);
			txtManagerPassword.setDisable(false);
			txtManagerPasswordAgain.setDisable(false);
			txtTC.setDisable(false);
			txtManagerMemberName.setDisable(false);
		} else {
			txtManagerName.setDisable(true);
			txtManagerSurname.setDisable(true);
			txtManagerPassword.setDisable(true);
			txtManagerPasswordAgain.setDisable(true);
			txtTC.setDisable(true);
			txtManagerMemberName.setDisable(true);
		}
	}

	private boolean validateAddress() {
		if (comboProvince.getValue() != null) {
			if (comboDistrict.getValue() != null) {
				if (txtPhoneNumber.getText().trim().length() > 0 && txtPhoneNumber.getText().trim().length() < 14) {
					long phoneNumber = 0;
					try {
						phoneNumber = Long.parseLong(txtPhoneNumber.getText().trim());
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Hata");
						alert.setHeaderText(null);
						alert.setContentText("Telefon numarasý nümerik olmalý.");
						alert.show();
					}
					if (phoneNumber != 0)
						return true;
					else {
						createAlert(AlertType.ERROR, "Hata", null, "Telefon numarasý rakamlardan oluþmalý.").show();
						return false;
					}
				} else {
					createAlert(AlertType.ERROR, "Hata", null, "En fazla 13 haneli bir telefon numarasý giriniz.")
							.show();
					return false;
				}
			} else {
				createAlert(AlertType.ERROR, "Hata", null, "Ýlçe seçiniz.").show();
				return false;
			}
		} else {
			createAlert(AlertType.ERROR, "Hata", null, "Ýl seçiniz.").show();
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

	// boyle kodlar yazmayin
	private boolean validateManager() {
		String memberName = txtManagerMemberName.getText().trim();
		if (!memberName.equals("")) {
			Query query = entityManager.createNativeQuery("SELECT * FROM Account WHERE memberName=?1", Account.class);
			query.setParameter(1, memberName);
			if ((query.getResultList().isEmpty())) {
				if (txtTC.getText().trim().length() == 11) {
					long tc = 0;
					try {
						tc = Long.parseLong(txtTC.getText().trim());
					} catch (Exception e) {
					}
					if (tc != 0) {
						query = entityManager.createNativeQuery("SELECT * FROM LocalManager WHERE tc=?1",
								LocalManager.class);
						if (query.getResultList().isEmpty()) {
							if (!txtManagerPassword.getText().trim().equals("")
									&& !txtManagerPasswordAgain.getText().trim().equals("")) {
								if (txtManagerPassword.getText().trim()
										.equals(txtManagerPasswordAgain.getText().trim())) {
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
									"Bu T.C. kimlik numarasýna sahip bir müdür sistemde mevcut.").show();
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

	private boolean validateHotelName() {
		if (txtName.getText().trim().length() > 0 && txtName.getText().trim().length() < 31) {
			return true;
		} else {
			createAlert(AlertType.ERROR, "Hata", null, "Otel adý giriniz.").show();
			return false;
		}
	}

	@FXML
	void save(ActionEvent event) {
		if (validateHotelName() && validateAddress()) {
			String hotelName = txtName.getText().trim();
			String province = comboProvince.getValue();
			String district = comboDistrict.getValue();
			long phoneNumber = Long.parseLong(txtPhoneNumber.getText().trim());
			// mudur bilgisi girilmeden kaydedilmek isteniyorsa
			if (!toggleManagerForm.isSelected()) {
				// mudur bilgisi olmadan kayýt edilmek istendiðinde
				ButtonType cancelButton = new ButtonType("Ýptal", ButtonData.CANCEL_CLOSE);
				ButtonType okButton = new ButtonType("Onayla", ButtonData.OK_DONE);
				Alert alert = new Alert(AlertType.CONFIRMATION,
						"Müdür bilgisi eklemeden otelinizi kaydetmek istiyor musunuz?", cancelButton, okButton);
				alert.headerTextProperty().set(null);
				alert.setTitle("Çýkýþ yapýlýyor!");
				Optional<ButtonType> result = alert.showAndWait();
				result.ifPresent(buttonData -> {
					if (buttonData.getButtonData() == ButtonData.OK_DONE) {
						// mudursuz otelin kaydi

						Hotel hotel = new Hotel(GeneralManagerMainController.generalManager.getIdCompany(), hotelName);
						entityManager.getTransaction().begin();
						entityManager.persist(hotel);
						entityManager.getTransaction().commit();

						System.out.println(hotel.getIdHotel());

						Address address = new Address(hotel.getIdHotel(), "Türkiye", province, district, phoneNumber);
						entityManager.getTransaction().begin();
						entityManager.persist(address);
						entityManager.getTransaction().commit();

						// Anasayfanýn güncellenmesi
						refreshHome();
					}
				});
			} else { // mudur formu aktiflestirilmis ise
				if (validateManager()) {
					// mudur bilgilerinin tamamen alinmasi
					String memberName = txtManagerMemberName.getText().trim();
					String managerName = txtManagerName.getText().trim();
					String managerSurname = txtManagerSurname.getText().trim();
					String passwd = txtManagerPassword.getText().trim();
					long managerTC = Long.parseLong(txtTC.getText().trim());

					Hotel hotel = new Hotel(GeneralManagerMainController.generalManager.getIdCompany(), hotelName);
					entityManager.getTransaction().begin();
					entityManager.persist(hotel);
					entityManager.getTransaction().commit();
					// adres kaydedilir
					Address address = new Address(hotel.getIdHotel(), "Türkiye", province, district, phoneNumber);
					entityManager.getTransaction().begin();
					entityManager.persist(address);
					entityManager.getTransaction().commit();

					// mudur icin hesap olusturulur
					Account account = new Account(managerName, managerSurname, memberName, passwd,
							"LOCALMANAGER");
					entityManager.getTransaction().begin();
					entityManager.persist(account);
					entityManager.getTransaction().commit();

					// mudurun hesabý baglanir
					LocalManager manager = new LocalManager(managerTC, account.getIdAccount(), hotel.getIdHotel(),
							hotel.getIdCompany());
					entityManager.getTransaction().begin();
					entityManager.persist(manager);
					entityManager.getTransaction().commit();

					// Anasayfanýn güncellenmesi
					refreshHome();
				}
			}

		}
	}

	private void refreshHome() {
		root.getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/GeneralManagerMain.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.setScene(new Scene(root));
		stage.setTitle("Anasayfa");
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	void setComboDistrict() {
		Query query = entityManager
				.createNativeQuery("SELECT ilce_isim FROM Districts WHERE il_isim=?1 ORDER BY ilce_isim ASC");
		query.setParameter(1, comboProvince.getValue().trim().toUpperCase(Locale.forLanguageTag("tr-TrR")));
		List<String> provinces = query.getResultList();
		comboDistrict.getItems().clear();
		comboDistrict.getItems().addAll(provinces);
	}

	private void fillComboProvince() {
		Query query = entityManager.createNativeQuery("SELECT DISTINCT il_isim FROM Districts ORDER BY il_isim ASC");
		List<String> provinces = query.getResultList();
		comboProvince.getItems().addAll(provinces);
	}

}
