package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import entity.Address;
import entity.Hotel;
import entity.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.CustomerResultListModel;
import utility.EntityManagerUtility;

public class CustomerResultListController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private TableView<CustomerResultListModel> tableView;

	@FXML
	private TableColumn<?, ?> columnHotelName;

	@FXML
	private TableColumn<?, ?> columnHotelBrand;

	@FXML
	private TableColumn<?, ?> columnProvince;

	@FXML
	private TableColumn<?, ?> columnDistrict;

	@FXML
	private TableColumn<?, ?> columnFreeRoomCount;

	@FXML
	private TableColumn<?, ?> columnMinimumAmount;

	private EntityManager entityManager;

	public static JFXDialog roomListDialog;

	private List<CustomerResultListModel> tableData = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entityManager = EntityManagerUtility.createEntityManager();

		columnHotelName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
		columnHotelBrand.setCellValueFactory(new PropertyValueFactory<>("companyName"));
		columnProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
		columnDistrict.setCellValueFactory(new PropertyValueFactory<>("district"));
		columnFreeRoomCount.setCellValueFactory(new PropertyValueFactory<>("freeRoomCount"));
		columnMinimumAmount.setCellValueFactory(new PropertyValueFactory<>("minimumAmount"));

		getData();
		tableView.getItems().addAll(tableData);

		tableView.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				CustomerReservationRoomListController.selected=tableView.getSelectionModel().getSelectedItem();
				Parent dialogFXML = null;
				try {
					dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/CustomerReservationRoomList.fxml"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JFXDialogLayout dialogLayout = new JFXDialogLayout();
				dialogLayout.setBody(dialogFXML);
				JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.CENTER);
				dialog.show();
				roomListDialog = dialog;
			}
		});

	}

	private void getData() {
		Query query = entityManager.createNativeQuery(
				"SELECT  DISTINCT (h.idHotel) FROM room INNER JOIN hotel h ON room.idHotel = h.idHotel WHERE status = 'Müsait';");
		List<Integer> freeHotels = query.getResultList();
		if (!freeHotels.isEmpty()) {
			freeHotels.forEach(idHotel -> {
				// otellerin adreslerini çekilmesi
				Query query1 = entityManager.createNativeQuery("SELECT * FROM address WHERE idHotel = ?1",
						Address.class);
				query1.setParameter(1, idHotel);
				List<Address> addresses = query1.getResultList();

				String province = addresses.get(0).getProvince();
				String district = addresses.get(0).getDistrict();

				// otelin þirket adýnýn çekilmesi
				query1 = entityManager.createNativeQuery("select * from hotel where idHotel=?1", Hotel.class);
				query1.setParameter(1, idHotel);
				List<Hotel> hotelResult = query1.getResultList();
				Hotel hotel = hotelResult.get(0);

				query1 = entityManager.createNativeQuery("SELECT companyName FROM company WHERE idCompany=?1");
				query1.setParameter(1, hotel.getIdCompany());
				List<String> companyNameResult = query1.getResultList();
				String companyName = companyNameResult.get(0);

				// boþ oda sayýsýnýn çekilmesi
				query1 = entityManager.createNativeQuery(
						"select * from room where status=?1 and idHotel=?2 order by amount asc", Room.class);
				query1.setParameter(1, "Müsait");
				query1.setParameter(2, idHotel);
				List<Room> freeRooms = query1.getResultList();
				int roomCount = freeRooms.size();

				// en düþük ücretin çekilmesi
				double minimumAmount = 0;
				if (freeRooms.isEmpty()) {
					minimumAmount = freeRooms.get(0).getAmount();
				}
				tableData.add(new CustomerResultListModel(hotel.getName(), companyName, province, district, roomCount,
						minimumAmount, hotel.getIdHotel()));

			});
		}
	}

	@FXML
	private void openFilterDialog() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/Filter.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.RIGHT);
		dialog.show();
	}
}
