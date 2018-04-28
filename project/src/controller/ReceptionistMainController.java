package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.PaneModel;

public class ReceptionistMainController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private GridPane gridPane;

	private List<PaneModel> listOfRooms;

	private List<String> data;

	public static PaneModel selectedRoom;

	public static JFXDialog roomInfoDialog;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getData();
		fillGridPane();
		setRoomPanes();
	}

	@FXML
	public void logout() {
		ButtonType cancelButton = new ButtonType("İptal", ButtonData.NO);
		ButtonType logoutButton = new ButtonType("Çıkış Yap", ButtonData.YES);
		Alert logoutAlert = new Alert(AlertType.CONFIRMATION, "Çıkış yapmak istiyor musunuz?", cancelButton,
				logoutButton);
		logoutAlert.headerTextProperty().set(null);
		logoutAlert.setTitle("Çıkış yapılıyor!");
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

	private void fillGridPane() {
		// SQL sorgusu sonucuna g�re listeye aktar�l�r
		listOfRooms = new ArrayList<PaneModel>();

		int listSize = data.size();

		int columnCounter = 0;
		int rowCounter = 0;

		for (int i = 0; i < listSize; i++) {
			PaneModel roomPaneModel = new PaneModel();
			gridPane.add(roomPaneModel, columnCounter, rowCounter);
			listOfRooms.add(roomPaneModel);
			// her sat�rda 7 oda
			if (columnCounter == 6) {
				columnCounter = 0;
				// sat�r doldu�unda alt sat�ra ge�i�
				rowCounter++;
				gridPane.getRowConstraints().add(new RowConstraints(150));
			} else {
				columnCounter++;
			}
		}
	}

	private void getData() {
		// SQL
		data = new ArrayList<String>();
		data.add("Room 1");
		data.add("Room 2");
		data.add("Room 3");
		data.add("Room 4");
		data.add("Room 5");
		data.add("Room 6");
		data.add("Room 7");
		data.add("Room 8");
		data.add("Room 9");
		data.add("Room 10");
		data.add("Room 11");
		data.add("Room 12");
		data.add("Room 13");
		data.add("Room 14");
		data.add("Room 15");
		data.add("Room 16");
		data.add("Room 17");
		data.add("Room 18");
		data.add("Room 19");
		data.add("Room 20");
		data.add("Room 21");
		data.add("Room 22");
		data.add("Room 23");
		data.add("Room 24");
		data.add("Room 25");
		data.add("Room 26");
		data.add("Room 27");
		data.add("Room 28");
		data.add("Room 29");
		data.add("Room 30");

	}

	private void setRoomPanes() {
		for (int index = 0; index < listOfRooms.size(); index++) {
			// header text
			Label text = new Label(data.get(index));
			text.setAlignment(Pos.CENTER);
			text.setLayoutY(5);
			text.setMinWidth(150);
			// body text
			Label content = new Label("Durum : M�sait");
			content.setAlignment(Pos.CENTER);
			content.setLayoutY(35);
			content.setMinWidth(150);
			Label description = new Label("Yatak Say�s� : 2");
			description.setAlignment(Pos.CENTER);
			description.setLayoutY(65);
			description.setMinWidth(150);
			PaneModel roomPaneModel = listOfRooms.get(index);
			roomPaneModel.getChildren().addAll(text, content, description);
			// odan�n bilgileri Pane �zerinden aktar�l�r
			roomPaneModel.idRoom = index;
			roomPaneModel.idHotel = index;
			roomPaneModel.setOnMouseClicked(e -> {
				// roomInfo dialog
				loadRoomInfoDialog();
				// Se�ilen odaya rezervasyon yap�lmak istendi�i takdirde kullan�l�r
				setSelectedRoom(roomPaneModel);
			});
		}
	}

	private void loadRoomInfoDialog() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/RoomInfo.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.CENTER);
		dialog.show();
		setRoomInfoDialog(dialog);

	}

	// Rezervasyon olu�turmak istendi�inde oda bilgilerinin di�er diyaloga
	// aktar�lmas�
	private void setSelectedRoom(PaneModel room) {
		selectedRoom = room;
	}

	/*
	 * Rezervasyon yapma iste�inin olmas� durumunda roomInfo diyalogu kapan�p
	 * rezervasyon olu�turma diyalo�u a��l�r
	 */
	private void setRoomInfoDialog(JFXDialog dialog) {
		roomInfoDialog = dialog;
	}

	public static JFXDialog getRoomInfoDialog() {
		return roomInfoDialog;
	}
}
