package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDialog.DialogTransition;

import entity.Bed;
import entity.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import model.CustomerResultListModel;
import model.RoomListModel;
import utility.EntityManagerUtility;

public class CustomerReservationRoomListController implements Initializable {

	@FXML
	private TableView<RoomListModel> tableView;

	@FXML
	private TableColumn<?, ?> columnBedCount;

	@FXML
	private TableColumn<?, ?> columnCapacity;

	@FXML
	private TableColumn<?, ?> columnAmount;

	@FXML
	private Label lblHeader;

	public static CustomerResultListModel selected;

	private EntityManager entityManager;

	private List<RoomListModel> data = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		entityManager = EntityManagerUtility.createEntityManager();

		lblHeader.setText(selected.getHotelName() + " Otelindeki Müsait Odalar");

		columnBedCount.setCellValueFactory(new PropertyValueFactory<>("bedCount"));
		columnCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
		columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		getData();
		tableView.getItems().setAll(data);

		tableView.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				RoomListModel selectedRoomListModel = tableView.getSelectionModel().getSelectedItem();
				CreateReservationController.selected = selectedRoomListModel;
				CreateReservationController.selectedResultModel = selected;
				Parent dialogFXML = null;
				try {
					dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/CreateReservation.fxml"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JFXDialogLayout dialogLayout = new JFXDialogLayout();
				dialogLayout.setBody(dialogFXML);
				CustomerResultListController.roomListDialog.setContent((Region) dialogFXML);
			}
		});

	}

	private void getData() {
		// odalarýn çekilmesi
		Query query = entityManager.createNativeQuery("select * from room where idHotel=?1 and status=?2", Room.class);
		query.setParameter(1, selected.getIdHotel());
		query.setParameter(2, "Müsait");
		List<Room> rooms = query.getResultList();
		rooms.forEach(room -> {
			// yatak sayýsýnýn hesaplanmasý
			Query query1 = entityManager.createNativeQuery("select * from bed where idRoom=?1", Bed.class);
			query1.setParameter(1, room.getIdRoom());
			List<Bed> beds = query1.getResultList();
			int bedCount = beds.size();
			int capacity = 0;
			for (Bed bed : beds) {
				capacity += bed.getCapacity();
			}
			data.add(new RoomListModel(bedCount, capacity, room.getAmount(), room.getIdRoom(), room.getNumber(),
					room.getType()));
		});
	}

}
