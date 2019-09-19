package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
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

/**
 * @author Anup Sheena
 * This class is the controller for the Meal Adder view element. It takes the
 * values the user provides, validates them, then save them to a unique meals.dat file
 * 
 */

public class MealAdderController implements Initializable {

	@FXML
	TextField txtMealDate, txtMealName, txtMealAmount, txtMealCalories;

	@FXML
	ToggleGroup toggleMealUnit;

	@FXML
	Button btnSubmit;
	
	@FXML
	ScrollPane scrollScene;
	
	@FXML
	Label lblUsername, lblError, lblSuccess;


	/**
	 * validates entered information, and saves information to meals.dat file
	 * 
	 * @param event click of submit button
	 */
	@FXML
	void submit(ActionEvent event) {
		
		// checks to see if all the fields are properly filled, alert if not
		if(txtMealDate.getText().trim().equals("") || txtMealName.getText().trim().equals("") || 
				txtMealAmount.getText().trim().equals("") || txtMealCalories.getText().trim().equals("") ||
				Double.parseDouble(txtMealAmount.getText()) <=0 ||  Double.parseDouble(txtMealCalories.getText()) <=0
			|| !txtMealDate.getText().matches("^(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])$")) {
			lblSuccess.setText("");
			lblError.setText("Invalid Data");
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Data Error");
			alert.setHeaderText("Data Entry Error");
			alert.setContentText(
					"No fields must be left blank, amount and calories must be greater than 0, and date must be in "
					+ "the format MM-DD");
			alert.showAndWait();
		}

		// if data is valid, adds data to unique meals.dat file.
		else {
		String fieldDelimiter = ":";
		String recordDelimiter = System.getProperty("line.separator");
		RadioButton selectedUnit = (RadioButton) toggleMealUnit.getSelectedToggle();
		
		//gets username appended with meals.dat to create a unique meals data file for each user
		String username = LoginController.username + "meals.dat";
		File file = new File(username);

		try (PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {

			fileOut.append(txtMealDate.getText() + fieldDelimiter);
			fileOut.append(txtMealName.getText() + fieldDelimiter);
			fileOut.append(txtMealAmount.getText() + fieldDelimiter);
			fileOut.append(selectedUnit.getText() + fieldDelimiter);
			fileOut.append(txtMealCalories.getText() + recordDelimiter);
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e10) {
			e10.printStackTrace();
		}
		
		//displays alert box stating that the meal was successfully added
		lblError.setText("");
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Meal Successfully Added");
		alert.setContentText(
				"The data was added successfully");
		alert.showAndWait();
		lblSuccess.setText("Meal Successfully Added");
	}
	}

	/**
	 * when program launched, displays user's username in the header bar
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		lblUsername.setText(LoginController.username);

	}
	
	/**
	 * goes to profile page when icon clicked
	 * @param event mouse click on icon
	 * @throws IOException
	 */
	@FXML
	void profile(MouseEvent event) throws IOException {
		Parent screen = FXMLLoader.load(getClass().getResource("/view/Profile.fxml"));
		scrollScene.setContent(screen);
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

