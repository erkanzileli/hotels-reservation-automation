package controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import entity.Customer;
import entity.Person;
import entity.Reservation;
import entity.Room;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import main.MainClass;
import model.CustomerResultListModel;
import model.PersonTableModel;
import model.RoomListModel;
import utility.EntityManagerUtility;

public class CreateReservationController implements Initializable {

	@FXML
	private JFXDialogLayout root;

	@FXML
	private JFXTextField txtHotelBrand;

	@FXML
	private JFXTextField txtHotelName;

	@FXML
	private JFXTextField txtProvince;

	@FXML
	private JFXTextField txtDistrict;

	@FXML
	private JFXTextField txtRoomNumber;

	@FXML
	private JFXTextField txtRoomType;

	@FXML
	private JFXTextField txtBedCount;

	@FXML
	private JFXTextField txtCapacity;

	@FXML
	private TableView<PersonTableModel> tablePersons;

	@FXML
	private TableColumn<?, ?> columnPersonName;

	@FXML
	private TableColumn<?, ?> columnPersonSurname;

	@FXML
	private TableColumn<?, ?> columnPersonTC;

	@FXML
	private TableColumn<?, ?> columnPersonBirth;

	@FXML
	private JFXDatePicker dateStart;

	private EntityManager entityManager;

	public static RoomListModel selected;

	public static CustomerResultListModel selectedResultModel;

	private List<PersonTableModel> persons = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entityManager = EntityManagerUtility.createEntityManager();

		columnPersonTC.setCellValueFactory(new PropertyValueFactory<>("tc"));
		columnPersonName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnPersonSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		columnPersonBirth.setCellValueFactory(new PropertyValueFactory<>("birth"));

		// textfield'lara yazma
		txtProvince.setText(selectedResultModel.getProvince());
		txtDistrict.setText(selectedResultModel.getDistrict());
		txtHotelBrand.setText(selectedResultModel.getCompanyName());
		txtHotelName.setText(selectedResultModel.getHotelName());
		txtRoomNumber.setText(String.valueOf(selected.getRoomNumber()));
		txtRoomType.setText(selected.getRoomType());
		txtBedCount.setText(String.valueOf(selected.getBedCount()));
		txtCapacity.setText(String.valueOf(selected.getCapacity()));
	}

	@FXML
	void addPerson() {
		if (persons.size() < selected.getCapacity()) {
			Dialog dialog = new Dialog<>();
			DialogPane dialogPane = new DialogPane();
			Pane pane = new Pane();
			dialog.setTitle("Odada kalacak kiþi kaydý.");
			Label tc = new Label("T.C.");
			tc.setLayoutX(15);
			tc.setLayoutY(15);

			Label name = new Label("Ýsim");
			name.setLayoutX(15);
			name.setLayoutY(50);

			Label surname = new Label("Soyisim");
			surname.setLayoutX(15);
			surname.setLayoutY(95);

			Label birth = new Label("Doðum T.");
			birth.setLayoutX(15);
			birth.setLayoutY(135);

			TextField txtTC = new TextField();
			txtTC.setLayoutX(75);
			txtTC.setLayoutY(15);

			TextField txtName = new TextField();
			txtName.setLayoutX(75);
			txtName.setLayoutY(50);

			TextField txtSurname = new TextField();
			txtSurname.setLayoutX(75);
			txtSurname.setLayoutY(95);

			DatePicker dateBirth = new DatePicker();
			dateBirth.setLayoutX(75);
			dateBirth.setLayoutY(135);
			dateBirth.setPrefWidth(150);

			ButtonType cancelButton = new ButtonType("Ýptal", ButtonData.CANCEL_CLOSE);
			ButtonType okButton = new ButtonType("Ekle", ButtonData.OK_DONE);

			pane.getChildren().addAll(tc, name, surname, birth, txtTC, txtName, txtSurname, dateBirth);
			dialogPane.setContent(pane);
			dialog.setDialogPane(dialogPane);
			dialogPane.getButtonTypes().addAll(cancelButton, okButton);
			Optional<ButtonType> optional = dialog.showAndWait();
			if (optional.isPresent()) {
				if (optional.get().getButtonData() == ButtonData.CANCEL_CLOSE) {
					dialog.close();
				} else {
					if (txtTC.getText().trim().length() == 11) {
						long longTC = 0;
						try {
							longTC = Long.parseLong(txtTC.getText().trim());
						} catch (Exception e) {
						}
						if (longTC != 0) {
							if (txtName.getText().trim().length() != 0 && txtSurname.getText().trim().length() != 0) {
								if (dateBirth.getValue() != null) {
									persons.add(new PersonTableModel(txtName.getText().trim(),
											txtSurname.getText().trim(), longTC, LocalDate.now()));
									tablePersons.getItems().setAll(persons);

								}
							}
						}
					}
				}
			}

		}
	}

	@FXML
	void save() {
		if (persons.size() > 0) {
			if (dateStart.getValue() != null) {
				Query query = entityManager.createNativeQuery("select * from customer where idAccount=?1",
						Customer.class);
				query.setParameter(1, MainClass.account.getIdAccount());
				Customer customer = (Customer) query.getResultList().get(0);
				query = entityManager.createNativeQuery(
						"insert into reservation(startDate,tcCustomer,idHotel,amount,idRoom) values(?1,?2,?3,?4,?5)");
				query.setParameter(1, dateStart.getValue());
				query.setParameter(2, customer.getTc());
				query.setParameter(3, selectedResultModel.getIdHotel());
				query.setParameter(4, selected.getAmount());
				query.setParameter(5, selected.getIdRoom());

				Reservation reservation = new Reservation(selectedResultModel.getIdHotel(), selected.getIdRoom(),
						dateStart.getValue(), selected.getAmount(), customer.getTc());
				entityManager.getTransaction().begin();
				entityManager.persist(reservation);
				entityManager.getTransaction().commit();
				entityManager.getTransaction().begin();
				persons.forEach(person -> {
					Person newPerson = new Person(person.getTc(), person.getName(), person.getSurname(),
							person.getBirth(), reservation.getIdReservation(), Date.valueOf(dateStart.getValue()),
							Date.valueOf(dateStart.getValue().minusDays(1)));
					entityManager.persist(newPerson);
				});
				Room room = entityManager.find(Room.class, selected.getIdRoom());
				room.setStatus("Rezerve-Edildi");
				entityManager.getTransaction().commit();

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Hata");
				alert.setHeaderText("Tarih seçin.");
				alert.setContentText(null);
				alert.show();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hata");
			alert.setHeaderText("Odada en az bir kiþi kalmalýdýr.");
			alert.setContentText(null);
			alert.show();
		}
	}

}
