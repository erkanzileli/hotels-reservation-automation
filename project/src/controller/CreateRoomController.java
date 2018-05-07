package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import entity.Bed;
import entity.Room;
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

public class CreateRoomController implements Initializable {

	@FXML
	private JFXTextField txtRoomNumber;

	@FXML
	private JFXTextField txtRoomType;

	@FXML
	private JFXTextField txtPhoneNumber;

	@FXML
	private JFXListView<Integer> listBeds;

	@FXML
	private JFXComboBox<String> comboBedCapacity;

	@FXML
	private JFXTextField txtAmount;

	private EntityManager entityManager;

	private List<Integer> beds;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entityManager = EntityManagerUtility.createEntityManager();
		beds = new ArrayList<>();
		comboBedCapacity.getItems().addAll("1", "2");

		listBeds.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				ButtonType cancelButton = new ButtonType("Ýptal", ButtonData.CANCEL_CLOSE);
				ButtonType okButton = new ButtonType("Onayla", ButtonData.OK_DONE);
				Alert alert = new Alert(AlertType.CONFIRMATION, "Silinsin mi?", cancelButton, okButton);
				alert.headerTextProperty().set(null);
				alert.setTitle("Oda siliniyor!");
				Optional<ButtonType> result = alert.showAndWait();
				result.ifPresent(buttonData -> {
					if (buttonData.getButtonData() == ButtonData.OK_DONE) {
						beds.remove(listBeds.getSelectionModel().getSelectedIndex());
						listBeds.getItems().setAll(beds);
					}
				});
			}
		});
	}

	// alarm diyalogu olusturma
	private Alert createAlert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	private boolean roomValidate() {
		if (txtRoomNumber.getText().trim().length() > 0 && txtRoomNumber.getText().trim().length() < 1000000) {
			long roomNumber = 0;
			try {
				roomNumber = Long.parseLong(txtRoomNumber.getText().trim());
			} catch (Exception e) {
			}
			if (roomNumber != 0) {
				if (txtRoomType.getText().trim().length() > 0 && txtRoomType.getText().trim().length() < 30) {
					if (txtPhoneNumber.getText().trim().length() > 0) {
						long phoneNumber = 0;
						try {
							phoneNumber = Long.parseLong(txtPhoneNumber.getText().trim());
						} catch (Exception e) {
						}
						if (phoneNumber != 0) {
							if (txtAmount.getText().trim().length() > 0 && txtAmount.getText().trim().length() < 12) {
								double amount = 0;
								try {
									amount = Double.parseDouble(txtAmount.getText().trim());
								} catch (Exception e) {
								}
								if (amount != 0) {
									return true;
								} else {
									createAlert(AlertType.ERROR, "Hata", null, "Ücret sayýsal olmalý.").show();
									return false;
								}
							} else {
								createAlert(AlertType.ERROR, "Hata", null, "Ücret kýsmýný doðru bir þekilde girin.")
										.show();
								return false;
							}
						} else {
							createAlert(AlertType.ERROR, "Hata", null, "Telefon numarasý rakamlardan oluþmalý.").show();
							return false;
						}
					} else {
						createAlert(AlertType.ERROR, "Hata", null, "Telefon numarasý girin.").show();
						return false;
					}
				} else {
					createAlert(AlertType.ERROR, "Hata", null, "Oda tipini 0-29 karakter olacak þekilde giriniz.")
							.show();
					return false;
				}
			} else {
				createAlert(AlertType.ERROR, "Hata", null, "Oda numarasý rakamlardan oluþmalý.").show();
				return false;
			}
		} else {
			createAlert(AlertType.ERROR, "Hata", null, "Maksimum 6 haneli bir oda numarasý girin.").show();
			return false;
		}
	}

	@FXML
	void addBed() {
		if (comboBedCapacity.getValue() != null) {
			beds.add(Integer.parseInt(comboBedCapacity.getValue()));
			listBeds.getItems().setAll(beds);
		} else {
			createAlert(AlertType.ERROR, "Hata", null, "Yataðýn kaç kiþilik olduðunu seçin").show();
		}
	}

	@FXML
	void save() {
		if (roomValidate()) {
			Room room = new Room(LocalManagerMainController.thisLocalManager.getIdHotel(), txtRoomType.getText().trim(),
					Integer.parseInt(txtRoomNumber.getText().trim()), Long.parseLong(txtPhoneNumber.getText().trim()),
					"Müsait", Double.parseDouble(txtAmount.getText().trim()));
			entityManager.getTransaction().begin();
			entityManager.persist(room);
			entityManager.getTransaction().commit();
			beds.forEach(b -> {
				entityManager.getTransaction().begin();
				entityManager.persist(new Bed(room.getIdRoom(), b));
				entityManager.getTransaction().commit();
			});
			comboBedCapacity.getScene().getWindow().hide();
			Stage stage = new Stage();
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("/fxml/LocalManagerMain.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			stage.setScene(new Scene(root));
			stage.setTitle("Anasayfa");
			stage.setResizable(false);
			stage.show();
		}
	}
}
