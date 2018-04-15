package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDialog.DialogTransition;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import model.PaneModel;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GeneralManagerMainController implements Initializable {

	@FXML
	private StackPane root;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private GridPane gridPane;

	private List<PaneModel> listOfHotels;

	private List<String> data;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getData();
		fillGridPane();
		setHotelPanes();

	}

	private void getData() {
		// SQL
		data = new ArrayList<String>();
		data.add("Hotel 1");
		data.add("Hotel 2");
		data.add("Hotel 3");
		data.add("Hotel 4");
		data.add("Hotel 5");
		data.add("Hotel 6");
		data.add("Hotel 7");
		data.add("Hotel 8");
		data.add("Hotel 9");
	}

	private void fillGridPane() {
		// SQL sorgusu sonucuna göre listeye aktarýlýr
		listOfHotels = new ArrayList<PaneModel>();

		int listSize = data.size();

		int columnCounter = 0;
		int rowCounter = 0;

		for (int i = 0; i < listSize; i++) {
			PaneModel paneModel = new PaneModel();
			gridPane.add(paneModel, columnCounter, rowCounter);
			listOfHotels.add(paneModel);
			// her satýrda 7 otel
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

	private void setHotelPanes() {
		int listSize = data.size();
		for (int index = 0; index < listSize; index++) {
			// header text
			Label text = new Label(data.get(index));
			text.setAlignment(Pos.CENTER);
			text.setMinWidth(150);
			text.setMinHeight(150);
			// body text
			PaneModel paneModel = listOfHotels.get(index);
			paneModel.getChildren().addAll(text);
			// otelin bilgileri Pane üzerinden aktarýlýr
			paneModel.idHotel = index;
			paneModel.setOnMouseClicked(e -> {
				// hotel details dialog
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
			});
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
