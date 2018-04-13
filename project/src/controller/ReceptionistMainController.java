package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import model.RoomPaneModel;

public class ReceptionistMainController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private GridPane gridPane;

	private List<RoomPaneModel> listOfRooms;

	private List<String> data;

	@FXML
	void profile() {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getData();
		fillGridPane();
		setRoomPanes();
	}

	private void fillGridPane() {
		// SQL sorgusu sonucuna göre listeye aktarýlýr
		listOfRooms = new ArrayList<RoomPaneModel>();

		int listSize = data.size();

		int columnCounter = 0;
		int rowCounter = 0;

		for (int i = 0; i < listSize; i++) {
			RoomPaneModel roomPaneModel = new RoomPaneModel();
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
			Label text = new Label(String.valueOf("Oda Numarasý:" + index + 1));
			text.setAlignment(Pos.CENTER);
			text.setLayoutY(5);
			text.setMinWidth(150);
			RoomPaneModel roomPaneModel = listOfRooms.get(index);
			roomPaneModel.getChildren().add(text);
			roomPaneModel.setOnMouseClicked(e -> {
				// roomInfo dialog
				Parent dialogFXML = null;
				try {
					dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/RoomInfo.fxml"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JFXDialogLayout dialogLayout = new JFXDialogLayout();
				dialogLayout.setBody(dialogFXML);
				JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.CENTER);
				dialog.show();
			});
		}
	}
}
