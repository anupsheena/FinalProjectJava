package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.DailyConsumption;
import model.Meal;
import model.MealFileHandling;

/**
 * This class is the controller for the Homepage view element. It displays meals
 * user has eaten today as well as remaining calories and calories consumed.
 * 
 * @author Anup Sheena
 */

public class HomepageController implements Initializable {

	@FXML
	Label lblConsumedCals, lblRemainingCals, lblUsername;

	@FXML
	ToggleGroup toggleSex;

	@FXML
	ScrollPane scrollScene, scrollHomepage;

	ArrayList<DailyConsumption> dList;
	ArrayList<Meal> todaysMeals;

	/**
	 * When page initialized, creates gridpane inside scroll pane that displays
	 * meals for today also sets text on consumed and remaining calories for today
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			String username = LoginController.username + "meals.dat";
			lblUsername.setText(LoginController.username);
			// clears list of meals
			MealFileHandling.setMealList(new ArrayList<Meal>());
			MealFileHandling.setDailylist(new ArrayList<DailyConsumption>());
			// reads data from file
			MealFileHandling.readMealsFromFile(new File(username));

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

		// add header
		GridPane gPaneHomepage = new GridPane();
		scrollHomepage.setContent(gPaneHomepage);
		gPaneHomepage.setPrefWidth(537);
		RowConstraints row = new RowConstraints(60);
		gPaneHomepage.getRowConstraints().add(row);
		GridPane Title = new GridPane();
		Title.getRowConstraints().add(row);
		gPaneHomepage.add(Title, 0, 0);
		GridPane.setHalignment(Title, HPos.CENTER);
		Title.setStyle("-fx-background-color: #5085a5");
		GridPane.setColumnSpan(Title, GridPane.REMAINING);
		Label name = new Label();
		Label amount = new Label();
		Label cals = new Label();
		name.setText("Item Name");
		amount.setText("Amount");
		cals.setText("Calories");
		Title.add(name, 0, 0);
		GridPane.setHalignment(name, HPos.CENTER);
		GridPane.setValignment(name, VPos.CENTER);
		Title.add(amount, 1, 0);
		GridPane.setHalignment(amount, HPos.CENTER);
		GridPane.setValignment(amount, VPos.CENTER);
		Title.add(cals, 2, 0);
		GridPane.setHalignment(cals, HPos.CENTER);
		GridPane.setValignment(cals, VPos.CENTER);
		ColumnConstraints col = new ColumnConstraints(179);
		Title.getColumnConstraints().add(col);
		Title.getColumnConstraints().add(col);
		Title.getColumnConstraints().add(col);
		name.setFont(new Font(16));
		name.setTextFill(Color.web("White"));
		amount.setFont(new Font(16));
		amount.setTextFill(Color.web("White"));
		cals.setFont(new Font(16));
		cals.setTextFill(Color.web("White"));

		// stores todays meals(date is hardcoded) in a Meals ArrayList
		dList = MealFileHandling.getDailyList();
		todaysMeals = DailyConsumption.getTodaysMeals(dList, "08-03");

		// loops through array list, and adds each meal today to the table
		for (int i = 0; i < todaysMeals.size(); i++) {

			RowConstraints row1 = new RowConstraints(50);
			gPaneHomepage.getRowConstraints().add(row1);
			gPaneHomepage.getColumnConstraints().add(col);
			gPaneHomepage.getColumnConstraints().add(col);
			gPaneHomepage.getColumnConstraints().add(col);
			name = new Label();
			amount = new Label();
			cals = new Label();
			name.setText(todaysMeals.get(i).getName());
			amount.setText(Double.toString(todaysMeals.get(i).getAmount()));
			cals.setText(Double.toString(todaysMeals.get(i).getCalories()));
			gPaneHomepage.add(name, 0, i + 1);
			GridPane.setHalignment(name, HPos.CENTER);
			gPaneHomepage.add(amount, 1, i + 1);
			GridPane.setHalignment(amount, HPos.CENTER);
			gPaneHomepage.add(cals, 2, i + 1);
			GridPane.setHalignment(cals, HPos.CENTER);

		}

		// get calories consumed today and goal calories
		double todaysCals = DailyConsumption.getTodaysCalories(dList, "08-01");
		double goalCals = LoginController.selectedProfile.getGoalIntake();

		// set consumed and goal calorie labels
		lblConsumedCals.setText(Double.toString(todaysCals));
		lblRemainingCals.setText(Double.toString(todaysCals - goalCals));

	}

	/**
	 * goes to profile page when icon clicked
	 * 
	 * @param event mouse click on icon
	 * @throws IOException
	 */
	@FXML
	void profile(MouseEvent event) throws IOException {
		Parent screen = FXMLLoader.load(getClass().getResource("/view/Profile.fxml"));
		scrollScene.setContent(screen);
	}

	/**
	 * goes to meal adder page when icon clicked
	 * 
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
	 * 
	 * @param event mouse click on icon
	 * @throws IOException
	 */
	@FXML
	void history(MouseEvent event) throws IOException {
		Parent screen = FXMLLoader.load(getClass().getResource("/view/History.fxml"));
		scrollScene.setContent(screen);
	}

}
