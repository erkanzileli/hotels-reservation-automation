package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ProfileController implements Initializable{
	
	@FXML
    private JFXTextField txtName;

    @FXML
    private JFXToggleButton toggleRegulation;

    @FXML
    private JFXTextField txtSurname;

    @FXML
    private JFXTextField txtPhoneNumber;

    @FXML
    private JFXDatePicker txtBirthDate;

    @FXML
    private JFXPasswordField txtOldPasswd;

    @FXML
    private JFXPasswordField txtNewPasswd;

    @FXML
    private JFXPasswordField txtNewPasswdAgain;

    @FXML
    private void changeRegulationMode() {
    	if(toggleRegulation.isSelected()) {
    		txtName.setEditable(true);
    		txtSurname.setEditable(true);
    		txtPhoneNumber.setEditable(true);
    		txtBirthDate.setEditable(true);
    		txtOldPasswd.setEditable(true);
    		txtNewPasswd.setEditable(true);
    		txtNewPasswdAgain.setEditable(true);
    	}else {
    		txtName.setEditable(false);
    		txtSurname.setEditable(false);
    		txtPhoneNumber.setEditable(false);
    		txtBirthDate.setEditable(false);
    		txtOldPasswd.setEditable(false);
    		txtNewPasswd.setEditable(false);
    		txtNewPasswdAgain.setEditable(false);
    	}
    }

    @FXML
    private void save() {

    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
