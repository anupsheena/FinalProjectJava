package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Profile;
import model.ProfileFileHandling;

/**
 * This class is the controller for the Login view element. It checks whether
 * the user's credentials are correct, then directs them to the homepage
 * 
 * @author Anup Sheena
 */

public class LoginController implements Initializable {

	@FXML
	TextField txtUsername, txtPassword;

	@FXML
	Button btnLogin;

	@FXML
	Label lblNotification;

	@FXML
	ScrollPane scrollScene;

	// so username can be passed to other controllers
	static String username;

	// so profile info can be passed to other controllers
	static Profile selectedProfile;

	/**
	 * reads profile.dat data when page initialized
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			// reads data from file
			ProfileFileHandling.readProfilesFromFile(new File("profiles.dat"));
			// gives alert if grades file is empty

		} catch (FileNotFoundException e1) {
			

			// gives alert if grades file has wrong data
		} catch (IllegalArgumentException e2) {
			Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
			alert2.setTitle("Data Error");
			alert2.setHeaderText("Incorrect Data");
			alert2.setContentText("The data in the data files is configured under the wrong format. Please exit, and"
					+ "fix this issue");
			alert2.showAndWait();

		}

	}

	// stores profileList in pList
	ArrayList<Profile> pList = ProfileFileHandling.getProfileList();

	/**
	 * goes to create profile page on icon clicked
	 * 
	 * @param event mouse click on label
	 * @throws IOException
	 */
	@FXML
	void createProfile(MouseEvent event) throws IOException {
		Parent createScreen = FXMLLoader.load(getClass().getResource("/view/ProfileCreation.fxml"));
		scrollScene.setContent(createScreen);
	}

	/**
	 * on click of login button, checks to see if username and password match one
	 * from profiles.dat if they do, directs user to home screen page
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void login(ActionEvent event) throws IOException {

		if(pList.size()==0) 
			lblNotification.setText("Username or Password is incorrect");
		
		for (int i = 0; i < pList.size(); i++) {
			if (pList.get(i).getusername().equals(txtUsername.getText())) {

				// if password and username matches go to home screen
				if (pList.get(i).getpassword().equals(txtPassword.getText())) {
					selectedProfile = pList.get(i);
					username = txtUsername.getText();
					Parent homeScreen = FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"));
					scrollScene.setContent(homeScreen);

					// if incorrect username or password, notification shown
				} else
					lblNotification.setText("Username or Password is incorrect");

			} else {
				lblNotification.setText("Username or Password is incorrect");
			}
		}

	}
}
