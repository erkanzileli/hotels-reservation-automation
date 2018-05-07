package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import entity.Account;
import entity.Employee;
import entity.LocalManager;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.MainClass;
import model.RoomTableModel;
import utility.EntityManagerUtility;

public class LocalManagerMainController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private Pane pane;

	@FXML
	private TableView<Employee> tableEmployees;

	@FXML
	private TableColumn<?, ?> columnEmployeeName;

	@FXML
	private TableColumn<?, ?> columnEmployeeSurname;

	@FXML
	private TableColumn<?, ?> columnEmployeePosition;

	@FXML
	private TableColumn<?, ?> columnEmployeeTC;

	@FXML
	private TableView<RoomTableModel> tableRoom;

	@FXML
	private TableColumn<?, ?> columnRoomNumber;

	@FXML
	private TableColumn<?, ?> columnRoomStatus;

	@FXML
	private TableColumn<?, ?> columnRoomType;

	@FXML
	private TableColumn<?, ?> columnRoonPhone;

	@FXML
	private TableColumn<?, ?> columnBedCount;

	private EntityManager entityManager;

	public static LocalManager thisLocalManager;

	private List<Employee> employees;

	private List<RoomTableModel> rooms;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entityManager = EntityManagerUtility.createEntityManager();

		employees = new ArrayList<>();
		rooms = new ArrayList<>();

		getLocalManagerAccount();
		getEmployees();
		getRooms();

		columnEmployeeName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
		columnEmployeeSurname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
		columnEmployeePosition.setCellValueFactory(new PropertyValueFactory<>("type"));
		columnEmployeeTC.setCellValueFactory(new PropertyValueFactory<>("tc"));

		tableEmployees.getItems().setAll(employees);
		tableEmployees.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				ButtonType cancelButton = new ButtonType("Ýptal", ButtonData.CANCEL_CLOSE);
				ButtonType okButton = new ButtonType("Sil", ButtonData.OK_DONE);
				Alert alert = new Alert(AlertType.CONFIRMATION, "Bu çalýþaný silmek istiyor musunuz?", cancelButton,
						okButton);
				alert.headerTextProperty().set(null);
				alert.setTitle("Bir çalýþan siliniyor!");
				Optional<ButtonType> result = alert.showAndWait();
				result.ifPresent(buttonData -> {
					if (buttonData.getButtonData() == ButtonData.OK_DONE) {
						Employee selectedEmployee = tableEmployees.getSelectionModel().getSelectedItem();
						if (selectedEmployee.getType().equals("RECEPTIONIST")) {
							Employee employee = entityManager.find(Employee.class, selectedEmployee.getTc());
							Account employeeAccunt = entityManager.find(Account.class, employee.getIdAccount());
							entityManager.getTransaction().begin();
							entityManager.remove(employee);
							entityManager.remove(employeeAccunt);
							entityManager.getTransaction().commit();
							refreshHome();
						} else {
							Employee employee = entityManager.find(Employee.class, selectedEmployee.getTc());
							entityManager.getTransaction().begin();
							entityManager.remove(employee);
							entityManager.getTransaction().commit();
							refreshHome();
						}
					}
				});
			}
		});

		columnRoomNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		columnRoomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		columnRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
		columnRoonPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		columnBedCount.setCellValueFactory(new PropertyValueFactory<>("bedCount"));

		tableRoom.getItems().setAll(rooms);
		tableRoom.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				
			}
		});
	}

	private void refreshHome() {
		root.getScene().getWindow().hide();
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

	private void getRooms() {
		Query query = entityManager.createNativeQuery("SELECT * FROM Room WHERE idHotel=?1", Room.class);
		query.setParameter(1, thisLocalManager.getIdHotel());
		List<Room> result = query.getResultList();
		if (!result.isEmpty()) {
			rooms = new ArrayList<>();
			result.forEach(r -> {
				Query query1 = entityManager.createNativeQuery("select idBed from bed where idRoom=?1");
				query1.setParameter(1, r.getIdRoom());
				int bedCount = 0;
				bedCount = query1.getResultList().size();
				rooms.add(new RoomTableModel(r.getIdHotel(), r.getType(), r.getNumber(), r.getPhoneNumber(), r.getStatus(), bedCount));
			});
		}
	}

	private void getEmployees() {
		Query query = entityManager.createNativeQuery("SELECT * FROM Employee WHERE idHotel=?1", Employee.class);
		query.setParameter(1, thisLocalManager.getIdHotel());
		this.employees = query.getResultList();
	}

	private void getLocalManagerAccount() {
		Query query = entityManager.createNativeQuery("Select * from LocalManager where idAccount=?1",
				LocalManager.class);
		query.setParameter(1, MainClass.account.getIdAccount());
		thisLocalManager = (LocalManager) query.getResultList().get(0);
	}

	@FXML
	void loadCreateEmployeeDialog() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/CreateEmployee.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.LEFT);
		dialog.show();
	}

	@FXML
	public void logout() {
		ButtonType cancelButton = new ButtonType("Ýptal", ButtonData.NO);
		ButtonType logoutButton = new ButtonType("Çýkýþ Yap", ButtonData.YES);
		Alert logoutAlert = new Alert(AlertType.CONFIRMATION, "Çýkýþ yapmak istiyor musunuz?", cancelButton,
				logoutButton);
		logoutAlert.headerTextProperty().set(null);
		logoutAlert.setTitle("Çýkýþ yapýlýyor!");
		Optional<ButtonType> result = logoutAlert.showAndWait();
		result.ifPresent(buttonData -> {
			if (buttonData.getButtonData() == ButtonData.YES) {
				root.getScene().getWindow().hide();
				Stage stage = new Stage();
				Parent root = null;
				try {
					root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stage.setScene(new Scene(root));
				stage.setTitle("Login");
				stage.setResizable(false);
				stage.show();
			}
		});
	}

	@FXML
	void loadCreateRoomDialog() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/CreateRoom.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.RIGHT);
		dialog.show();
	}

	@FXML
	void profile() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/Profile.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.RIGHT);
		dialog.show();
	}

}
