package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
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

	private void fillGridPane() {
		// SQL sorgusu sonucuna göre listeye aktarýlýr
		listOfRooms = new ArrayList<PaneModel>();

		int listSize = data.size();

		int columnCounter = 0;
		int rowCounter = 0;

		for (int i = 0; i < listSize; i++) {
			PaneModel roomPaneModel = new PaneModel();
			gridPane.add(roomPaneModel, columnCounter, rowCounter);
			listOfRooms.add(roomPaneModel);
			// her satýrda 7 oda
			if (columnCounter == 6) {
				columnCounter = 0;
				// satýr dolduðunda alt satýra geçiþ
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
			Label content = new Label("Durum : Müsait");
			content.setAlignment(Pos.CENTER);
			content.setLayoutY(35);
			content.setMinWidth(150);
			Label description = new Label("Yatak Sayýsý : 2");
			description.setAlignment(Pos.CENTER);
			description.setLayoutY(65);
			description.setMinWidth(150);
			PaneModel roomPaneModel = listOfRooms.get(index);
			roomPaneModel.getChildren().addAll(text, content, description);
			// odanýn bilgileri Pane üzerinden aktarýlýr
			roomPaneModel.idRoom = index;
			roomPaneModel.idHotel = index;
			roomPaneModel.setOnMouseClicked(e -> {
				// roomInfo dialog
				loadRoomInfoDialog();
				// Seçilen odaya rezervasyon yapýlmak istendiði takdirde kullanýlýr
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

	// Rezervasyon oluþturmak istendiðinde oda bilgilerinin diðer diyaloga
	// aktarýlmasý
	private void setSelectedRoom(PaneModel room) {
		selectedRoom = room;
	}

	/*
	 * Rezervasyon yapma isteðinin olmasý durumunda roomInfo diyalogu kapanýp
	 * rezervasyon oluþturma diyaloðu açýlýr
	 */
	private void setRoomInfoDialog(JFXDialog dialog) {
		roomInfoDialog = dialog;
	}

	public static JFXDialog getRoomInfoDialog() {
		return roomInfoDialog;
	}
}
