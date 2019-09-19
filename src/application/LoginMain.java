package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * sets the scene, then Launches application
 * 
 * @author Anup Sheena
 *
 */
public class LoginMain extends Application {

	/**
	 * sets stage up and catches IOexception
	 * 
	 * @param primaryStage stage that scene is being added to
	 */
	@Override
	public void start(Stage primaryStage) {

		try {
			// Load root layout from fxml file.
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene window = new Scene(root);
			primaryStage.setScene(window);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * launches the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
