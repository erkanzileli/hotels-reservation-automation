package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
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
		// arama ekranýný kapama
		Stage customerMainStage = (Stage) root.getScene().getWindow();
		customerMainStage.hide();
		// otellerin listelendiði ekran
		Stage customerStage = new Stage(StageStyle.DECORATED);
		customerStage.setTitle("Otel Seçin");
		customerStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
