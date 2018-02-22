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

		FXMLLoader LoginFxmlLoader=new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
	    Parent root = LoginFxmlLoader.load();
	    FXMLLoader SignUpFxmlLoader=new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
	    Parent root2 =SignUpFxmlLoader.load();
	    Scene loginScene=new Scene(root);
	    Scene signUpScene=new Scene(root2);
	    LoginController loginController = LoginFxmlLoader.getController();
	    loginController.setSignUpScene(signUpScene);
	    
	    
	    primaryStage.setResizable(false);
		//fatma
        primaryStage.setScene(loginScene);
		primaryStage.show();
	}

}
