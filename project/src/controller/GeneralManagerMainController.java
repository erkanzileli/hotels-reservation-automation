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

import entity.GeneralManager;
import entity.Hotel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.PaneModel;
import utility.EntityManagerUtility;

public class GeneralManagerMainController implements Initializable {

	@FXML
	private StackPane root;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private GridPane gridPane;

	private List<PaneModel> listOfHotels;

	private List<String> data;

	private List<Hotel> hotels;

	public static GeneralManager generalManager = new GeneralManager();
	
	public static JFXDialog hotelDetailsDialog;

	private EntityManager entityManager;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		entityManager = EntityManagerUtility.createEntityManager();
		getData();
		fillGridPane();
		setHotelPanes();

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

	private void getData() {
		// SQL
		Query query = entityManager.createNativeQuery("SELECT * FROM GeneralManager WHERE idAccount=?1",
				GeneralManager.class);
		query.setParameter(1, generalManager.getIdAccount());
		List<GeneralManager> gm = query.getResultList();
		if (gm.isEmpty()) {
			Alert alertNotFound = new Alert(AlertType.ERROR,
					"Bilinmeyen bir hata oluÅŸtu. SaÄŸlayÄ±cÄ±nÄ±z ile iletiÅŸime geÃ§iniz");
			alertNotFound.headerTextProperty().set(null);
			alertNotFound.setTitle("Hata");
			alertNotFound.show();
		} else {
			generalManager = gm.get(0);
			query = entityManager.createNativeQuery("SELECT * FROM Hotel Where idCompany=?1 ORDER BY Hotel.name ASC",
					Hotel.class);
			query.setParameter(1, generalManager.getIdCompany());
			hotels = query.getResultList();
		}
	}

	private void fillGridPane() {
		// SQL sorgusu sonucuna gï¿½re listeye aktarï¿½lï¿½r
		listOfHotels = new ArrayList<PaneModel>();
		int listSize = hotels.size();
		int columnCounter = 0;
		int rowCounter = 0;

		for (int i = 0; i < listSize; i++) {
			PaneModel paneModel = new PaneModel();
			gridPane.add(paneModel, columnCounter, rowCounter);
			listOfHotels.add(paneModel);
			// her satï¿½rda 7 otel
			if (columnCounter == 6) {
				columnCounter = 0;
				// satï¿½r dolduï¿½unda alt satï¿½ra geï¿½iï¿½
				rowCounter++;
				gridPane.getRowConstraints().add(new RowConstraints(150));
			} else {
				columnCounter++;
			}
		}
	}

	private void setHotelPanes() {
		int index = 0;
		for (Hotel hotel : hotels) {
			// header text
			Label text = new Label(hotel.getName());
			text.setAlignment(Pos.CENTER);
			text.setMinWidth(150);
			text.setMinHeight(100);
			// body text adress vb.
			PaneModel hotelPane = listOfHotels.get(index);
			hotelPane.getChildren().addAll(text);
			// otelin bilgileri Pane uzerinden aktarilir
			hotelPane.setIdHotel(hotel.getIdHotel());
			hotelPane.setIdCompany(hotel.getIdCompany());
			hotelPane.setHotelName(hotel.getName());
			// tiklanma olayi
			hotelPane.setOnMouseClicked(e -> {
				// hotel details dialog
				HotelDetailsController.selectedHotel = hotelPane;
				Parent dialogFXML = null;
				try {
					dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/HotelDetails.fxml"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JFXDialogLayout dialogLayout = new JFXDialogLayout();
				dialogLayout.setBody(dialogFXML);
				JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.CENTER);
				dialog.show();
				hotelDetailsDialog=dialog;
			});
			index++;
		}
	}

	@FXML
	private void loadCreateHotelDialog() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/CreateHotel.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.TOP);
		dialog.show();
	}

	@FXML
	private void profile() {
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
