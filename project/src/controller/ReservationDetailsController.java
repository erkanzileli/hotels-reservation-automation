package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import entity.Address;
import entity.Bed;
import entity.Company;
import entity.Hotel;
import entity.Person;
import entity.Reservation;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.PersonTableModel;
import utility.EntityManagerUtility;

public class ReservationDetailsController implements Initializable {

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

	public static int idReservation;

	private int capacity;

	private List<PersonTableModel> persons = new ArrayList<>();

	private List<Person> resultPersons = new ArrayList<>();

	private int idRoom;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entityManager = EntityManagerUtility.createEntityManager();
		columnPersonTC.setCellValueFactory(new PropertyValueFactory<>("tc"));
		columnPersonName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnPersonSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		columnPersonBirth.setCellValueFactory(new PropertyValueFactory<>("birth"));
		getData();
	}

	private void getData() {
		// rezervasyon bilgilerini çekme
		Reservation reservation = entityManager.find(Reservation.class, idReservation);
		dateStart.setPromptText(String.valueOf(reservation.getDate()));

		// otel bilgilerini çekme
		Query query = entityManager.createNativeQuery("select * from hotel where idHotel=?1", Hotel.class);
		query.setParameter(1, reservation.getIdHotel());
		Hotel hotel = (Hotel) query.getResultList().get(0);
		txtHotelName.setText(hotel.getName());

		// o rezervasyondaki kiþilerin bilgilerini çekme
		query = entityManager.createNativeQuery("select * from person where idReservation=?1", Person.class);
		query.setParameter(1, idReservation);

		// adres bilgilerini çekme
		query = entityManager.createNativeQuery("select * from address where idHotel=?1", Address.class);
		query.setParameter(1, hotel.getIdHotel());
		Address address = (Address) query.getResultList().get(0);
		txtProvince.setText(address.getProvince());
		txtDistrict.setText(address.getDistrict());

		// þirket adý için þirket bilgilerini çekme
		Company company = entityManager.find(Company.class, hotel.getIdCompany());
		txtHotelBrand.setText(company.getName());

		// oda bilgilerini çekme
		Room room = entityManager.find(Room.class, reservation.getIdRoom());
		txtRoomNumber.setText(String.valueOf(room.getNumber()));
		txtRoomType.setText(room.getType());
		idRoom = room.getIdRoom();
		// kiþilere ulaþma
		query = entityManager.createNativeQuery("select * from person where idReservation=?1", Person.class);
		query.setParameter(1, reservation.getIdReservation());
		resultPersons = query.getResultList();
		resultPersons.forEach(person -> {
			persons.add(
					new PersonTableModel(person.getName(), person.getSurname(), person.getTc(), person.getBirthDate()));
		});
		tablePersons.getItems().setAll(persons);
		// yatak sayýsýnýn hesaplanmasý
		query = entityManager.createNativeQuery("select * from bed where idRoom=?1", Bed.class);
		query.setParameter(1, room.getIdRoom());
		List<Bed> beds = query.getResultList();
		capacity = 0;
		beds.forEach(bed -> {
			capacity += bed.getCapacity();
		});
		txtBedCount.setText(String.valueOf(beds.size()));
		txtCapacity.setText(String.valueOf(capacity));
	}

	@FXML
	void removeReservation() {
		ButtonType cancelButton = new ButtonType("Ýptal", ButtonData.CANCEL_CLOSE);
		ButtonType okButton = new ButtonType("Onayla", ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.CONFIRMATION, "Çýkýþýnýzý yapmak istiyor musunuz?", cancelButton, okButton);
		alert.headerTextProperty().set(null);
		alert.setTitle("Çýkýþ yapýlýyor!");
		Optional<ButtonType> result = alert.showAndWait();
		result.ifPresent(buttonData -> {
			if (buttonData.getButtonData() == ButtonData.OK_DONE) {
				resultPersons.forEach(person -> {
					entityManager.getTransaction().begin();
					Person p = entityManager.find(Person.class, person.getIdPerson());
					Room room = entityManager.find(Room.class, idRoom);
					room.setStatus("Müsait");
					java.util.Date testDate = p.getEntryDate();
					System.out.println(testDate.toString());
					Calendar cal = Calendar.getInstance();
					cal.setTime(p.getEntryDate());
					cal.add(Calendar.DATE, -1);
					p.setOutDate(cal.getTime());
					entityManager.getTransaction().commit();
					Stage stage = new Stage();
					Parent root = null;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/CustomerResultList.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					stage.setScene(new Scene(root));
					stage.setTitle("Anasayfa");
					stage.setResizable(false);
					stage.show();
					txtBedCount.getScene().getWindow().hide();
				});
			}
		});
	}

}
