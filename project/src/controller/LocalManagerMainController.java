package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXDialog.DialogTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class LocalManagerMainController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private Pane pane;

	@FXML
	private JFXTreeTableView<?> tableRoom;

	@FXML
	private TreeTableColumn<?, ?> columnRoomNumber;

	@FXML
	private TreeTableColumn<?, ?> columnRoomStatus;

	@FXML
	private TreeTableColumn<?, ?> columnRoomType;

	@FXML
	private TreeTableColumn<?, ?> columnRoomPhone;

	@FXML
	private TreeTableColumn<?, ?> columnRoomBedCount;

	@FXML
	private JFXTreeTableView<?> tableEmployee;

	@FXML
	private TreeTableColumn<?, ?> columnEmployeeName;

	@FXML
	private TreeTableColumn<?, ?> columnEmployeeSurname;

	@FXML
	private TreeTableColumn<?, ?> columnEmployeePosition;

	@FXML
	private TreeTableColumn<?, ?> columnEmployeeTC;

	@FXML
	private TreeTableColumn<?, ?> columnEmployeeMemberName;

	@FXML
	void loadCreateEmployeeDialog() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/CreateEmployee.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.RIGHT);
		dialog.show();
	}

	@FXML
	void loadCreateRoomDialog() {
		Parent dialogFXML = null;
		try {
			dialogFXML = FXMLLoader.load(getClass().getResource("/fxml/CreateRoom.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setBody(dialogFXML);
		JFXDialog dialog = new JFXDialog(root, dialogLayout, DialogTransition.LEFT);
		dialog.show();
	}

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
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}
