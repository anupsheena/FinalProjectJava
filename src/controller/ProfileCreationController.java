package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Profile;
import model.ProfileFileHandling;

/**
 * @author Anup Sheena
 * 
 * This class is the controller for the Profile Creation view element. It takes
 * the values the user provides, validates them, then save them to profiles.dat
 * file
 * 
 */

public class ProfileCreationController implements Initializable {

	@FXML
	TextField txtUsername, txtPassword, txtAge, txtHeight, txtWeight, txtGoalIntake;

	@FXML
	ToggleGroup toggleSex;

	@FXML
	Button btnExit, btnSubmit;

	@FXML
	GridPane gPaneProfile;

	@FXML
	ScrollPane scrollScene;

	@FXML
	Label lblError;

	ArrayList<Profile> pList = ProfileFileHandling.getProfileList();

	/**
	 * goes back to login screen
	 * 
	 * @param event mouse click of back icon
	 * @throws IOException
	 */
	@FXML
	void back(MouseEvent event) throws IOException {
		// exit
		Parent loginScreen = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		scrollScene.setContent(loginScreen);
	}

	/**
	 * validates entered information, and saves information to profiles.dat file
	 * 
	 * @param event click of submit button
	 * @throws IOException
	 */
	@FXML
	void submit(ActionEvent event) throws IOException {

		// checks to see if user already exists
		int taken = 0;
		System.out.print(pList.size());
		for (int i = 0; i < pList.size(); i++) {
			if (pList.get(i).getusername().equals(txtUsername.getText())) {
				taken = 1;
			}
		}

		// alert if user already exists
		if (taken == 1) {
			lblError.setText("Username is Taken");
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Username is taken");
			alert.setHeaderText("Username taken");
			alert.setContentText("Please enter another username.");
			alert.showAndWait();
		}

		else {

			// checks to see if all the fields are properly filled, alert if not
			if (txtUsername.getText().trim().equals("") || txtPassword.getText().trim().equals("")
					|| txtAge.getText().trim().equals("") || txtWeight.getText().trim().equals("")
					|| txtHeight.getText().trim().equals("") || txtGoalIntake.getText().trim().equals("")
					|| Integer.parseInt(txtAge.getText()) <= 0 || Double.parseDouble(txtWeight.getText()) <= 0
					|| Double.parseDouble(txtHeight.getText()) <= 0
					|| Double.parseDouble(txtGoalIntake.getText()) <= 0) {
				lblError.setText("Incorrect Data");
				lblError.setText("Invalid Data");
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Data Error");
				alert.setHeaderText("Data Entry Error");
				alert.setContentText(
						"No fields must be left blank, and age, weight, height and goal intake must be numbers");
				alert.showAndWait();

				// if data is valid, adds data to profiles.dat file.
			} else {

				String fieldDelimiter = ":";
				String recordDelimiter = System.getProperty("line.separator");
				RadioButton selectedSex = (RadioButton) toggleSex.getSelectedToggle();
				File file = new File("profiles.dat");

				try (PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {

					fileOut.append(txtUsername.getText() + fieldDelimiter);
					fileOut.append(txtPassword.getText() + fieldDelimiter);
					fileOut.append(txtAge.getText() + fieldDelimiter);
					fileOut.append(selectedSex.getText() + fieldDelimiter);
					fileOut.append(txtWeight.getText() + fieldDelimiter);
					fileOut.append(txtHeight.getText() + fieldDelimiter);
					fileOut.append(txtGoalIntake.getText() + recordDelimiter);
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e10) {
					e10.printStackTrace();
					Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
					alert2.setTitle("Data Error");
					alert2.setHeaderText("Incorrect Data");
					alert2.setContentText(
							"The data in the data files is configured under the wrong format. Please exit, and"
									+ "fix this issue");
					alert2.showAndWait();
				}
				// lead user to login page once profile created
				Parent loginScreen = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
				scrollScene.setContent(loginScreen);
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
