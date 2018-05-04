package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import dao.HotelDAO;
import daoimpl.HotelDAOImpl;
import entity.Hotel;
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
	private JFXTextField txtStreet;

	@FXML
	private JFXTextField txtPostalCode;

	@FXML
	private JFXTextField txtDoorNumber;

	@FXML
	private JFXTextField txtPhoneNumber;

	@FXML
	private JFXTextField txtName;

	@FXML
	private JFXTextField txtManagerMemberName;

	@FXML
	private JFXTextField txtTC;

	@FXML
	private JFXTextField txtManagerName;

	@FXML
	private JFXTextField txtManagerSurname;

	private HotelDAO hoteldao;

	private EntityManager entityManager;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entityManager = EntityManagerUtility.createEntityManager();
		hoteldao = new HotelDAOImpl();
		fillComboProvince();
	}

	@FXML
	void save() {
		String hotelName = txtName.getText().trim();
		// adress
		String province = comboProvince.getValue();
		String district = comboDistrict.getValue();
		String street = txtStreet.getText().trim();
		String postalCode = txtPostalCode.getText().trim();
		String doorNumber = txtDoorNumber.getText().trim();
		String phoneNumber = txtPhoneNumber.getText().trim();
		// manager
		String managerName;
		String managerSurname;
		long managerTC;
		// mudur bilgisi girilmeden kaydedilmek isteniyorsa
		if (txtManagerMemberName.getText().equals("")) {
			// mudur bilgisi olmadan kayıt edilmek istendiği takdirde onay alınması
			ButtonType cancelButton = new ButtonType("İptal", ButtonData.CANCEL_CLOSE);
			ButtonType logoutButton = new ButtonType("Onayla", ButtonData.OK_DONE);

			Alert logoutAlert = new Alert(AlertType.CONFIRMATION,
					"Müdür bilgisi eklemeden otelinizi kaydetmek istiyor musunuz?", cancelButton, logoutButton);
			logoutAlert.headerTextProperty().set(null);
			logoutAlert.setTitle("Çıkış yapılıyor!");

			Optional<ButtonType> result = logoutAlert.showAndWait();

			result.ifPresent(buttonData -> {
				if (buttonData.getButtonData() == ButtonData.OK_DONE) {
					// mudursuz otelin kaydi
					hoteldao.create(new Hotel(GeneralManagerMainController.generalManager.getIdCompany(), hotelName));
					// Anasayfanın güncellenmesi
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
			});
		} else {
			// mudur bilgilerinin alınması
			managerName = txtManagerName.getText().trim();
			managerSurname = txtManagerSurname.getText().trim();
			try {
				managerTC = Long.parseLong(txtTC.getText().trim());
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.headerTextProperty().set("T.C. kısmına uygunsuz değer girildi!");
				alert.setTitle("Hata");
				alert.setContentText("T.C. kısmının nümerik olmasına özen gösteriniz.");
				alert.show();
			}
		}
	}

	@FXML
	void setComboDistrict() {
		Query query = entityManager
				.createNativeQuery("SELECT ilce_isim FROM Districts WHERE il_isim=?1 ORDER BY ilce_isim ASC");
		query.setParameter(1, comboProvince.getValue());
		List<String> provinces = query.getResultList();
		comboDistrict.getItems().clear();
		comboDistrict.getItems().addAll(provinces);
	}

	@FXML
	void search() {

	}

	private void fillComboProvince() {
		Query query = entityManager.createNativeQuery("SELECT DISTINCT il_isim FROM Districts ORDER BY il_isim ASC");
		List<String> provinces = query.getResultList();
		comboProvince.getItems().addAll(provinces);
	}

}
