package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import model.Profile;

/**
 * @author Anup Sheena
 * 
 * This class is the controller for the Profile view element. It displays the users profile information
 * 
 */

public class ProfileController implements Initializable {



	@FXML
	Label lblUsername, lblPassword, lblAge, lblSex, lblWeight, lblHeight, lblGoalIntake, lblUsernameHeader;

	@FXML
	ScrollPane scrollScene;
	

	/**
	 * sets labels to user information
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Profile profile = LoginController.selectedProfile;
		lblUsername.setText(profile.getusername());
		lblPassword.setText(profile.getpassword());
		lblAge.setText(Integer.toString(profile.getAge()));
		lblSex.setText(profile.getSex());
		lblWeight.setText(Double.toString(profile.getWeight()));
		lblHeight.setText(Double.toString(profile.getHeight()));
		lblGoalIntake.setText(Double.toString(profile.getGoalIntake()));
		lblUsernameHeader.setText(profile.getusername());
	}
	
	/**
	 * goes to homepage page when icon clicked
	 * @param event mouse click on icon
	 * @throws IOException
	 */
	@FXML
	void homepage(MouseEvent event) throws IOException {
		Parent screen = FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"));
		scrollScene.setContent(screen);
	}

	/**
	 * goes to meal adder page when icon clicked
	 * @param event mouse click on icon
	 * @throws IOException
	 */
	@FXML
	void add(MouseEvent event) throws IOException {
		Parent screen = FXMLLoader.load(getClass().getResource("/view/MealAdder.fxml"));
		scrollScene.setContent(screen);
	}

	/**
	 * goes to history page when icon clicked
	 * @param event mouse click on icon
	 * @throws IOException
	 */
	@FXML
	void history(MouseEvent event) throws IOException {
		Parent screen = FXMLLoader.load(getClass().getResource("/view/History.fxml"));
		scrollScene.setContent(screen);
	}
}

