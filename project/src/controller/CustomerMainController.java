package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomerMainController implements Initializable {

	@FXML
	private Pane root;

	@FXML
	private JFXDatePicker dateStart;

	@FXML
	private JFXDatePicker dateEnd;

	@FXML
	private void search() {
		if (dateStart.getValue() != null) {
			if (dateEnd.getValue() != null) {
				Parent parent = null;
				try {
					parent = FXMLLoader.load(getClass().getResource("/fxml/CustomerResultList.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Stage stage = new Stage();
				stage.setScene(new Scene(parent));
				stage.initStyle(StageStyle.DECORATED);
				stage.show();
				root.getScene().getWindow().hide();
			} else {
				createAlert(AlertType.ERROR, "Hata", "Bitiþ tarihi seçin.", null).show();
			}
		} else {
			createAlert(AlertType.ERROR, "Hata", "Baþlangýç tarihi seçin.", null).show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	// alarm diyalogu olusturma
	private Alert createAlert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

}
