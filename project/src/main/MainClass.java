package main;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application {

	public static EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		entityManagerFactory = Persistence.createEntityManagerFactory("mysqlPersistenceUnit");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
