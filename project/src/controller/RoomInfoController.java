package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialogLayout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class RoomInfoController implements Initializable {

	@FXML
	private JFXDialogLayout root;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	private void createReservation() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/CreateReservation.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		ReceptionistMainController.getRoomInfoDialog().setContent(dialogLayout);
	}
}
