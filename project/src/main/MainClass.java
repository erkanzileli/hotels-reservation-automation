package main;

import controller.LoginController;
import controller.SignUpController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	    Parent root = new FXMLLoader(getClass().getResource("/fxml/login.fxml")).load();
	    primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

}
