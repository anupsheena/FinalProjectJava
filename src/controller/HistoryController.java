package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.DailyConsumption;
import model.Meal;
import model.MealFileHandling;
import model.Profile;

/**
 * This class is the controller for the History view element. It displays a
 * table of entires that corresponds with the selections the user has made
 * 
 * @author Anup Sheena
 */

public class HistoryController implements Initializable {

	@FXML
	DatePicker dPickerDate, dPickerDateRangeOne, dPickerDateRangeTwo;

	@FXML
	ChoiceBox<String> selectMonth, selectMonthRangeOne, selectMonthRangeTwo;

	@FXML
	Button btnDateSearch, btnDateRangeSearch, btnMonthSearch, btnMonthRangeSearch;

	@FXML
	ScrollPane scrollHistory;

	@FXML
	Label lblNetCalories, lblUsername;

	@FXML
	ScrollPane scrollScene;

	ArrayList<DailyConsumption> dList;
	Profile selectedProfile = LoginController.selectedProfile;
	double goalCals = selectedProfile.getGoalIntake();

	/**
	 * when page initialized, sets username in page header, and then reads the data
	 * from the meals.dat file
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			lblUsername.setText(LoginController.username);
			// clears meal list
			MealFileHandling.setMealList(new ArrayList<Meal>());
			MealFileHandling.setDailylist(new ArrayList<DailyConsumption>());
			// reads unique data from file
			String username = LoginController.username + "meals.dat";
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

		// get arrayList of daily consumption objects after they are populated
		dList = MealFileHandling.getDailyList();
		System.out.print(dList.size());

	}

	/**
	 * return all the meals eaten on selected date as well as total, and net
	 * calories for that day
	 * 
	 * @param event
	 */
	@FXML
	void dateSearch(ActionEvent event) {

		if (dPickerDate.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Empty Date");
			alert.setHeaderText("No Date Entered");
			alert.setContentText("You must select a date");
			alert.showAndWait();
		}

		else {
			// sets header
			GridPane gPaneHistory = new GridPane();
			scrollHistory.setContent(gPaneHistory);
			gPaneHistory.setPrefWidth(537);
			RowConstraints row = new RowConstraints(60);
			gPaneHistory.getRowConstraints().add(row);
			GridPane Title = new GridPane();
			Title.getRowConstraints().add(row);
			gPaneHistory.add(Title, 0, 0);
			GridPane.setHalignment(Title, HPos.CENTER);
			Title.setStyle("-fx-background-color: #5085a5");
			GridPane.setColumnSpan(Title, GridPane.REMAINING);
			Label name = new Label();
			Label amount = new Label();
			Label cals = new Label();
			name.setText("Food");
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

			// gets arrayList of meals for user specified date
			String date = dPickerDate.getValue().toString();
			date = date.substring(5);
			ArrayList<Meal> todaysMeals = DailyConsumption.getTodaysMeals(dList, date);

			// loops through meal arrayList and populates table with data
			for (int i = 0; i < todaysMeals.size(); i++) {
				RowConstraints row1 = new RowConstraints(50);
				gPaneHistory.getRowConstraints().add(row1);
				gPaneHistory.getColumnConstraints().add(col);
				gPaneHistory.getColumnConstraints().add(col);
				gPaneHistory.getColumnConstraints().add(col);
				name = new Label();
				amount = new Label();
				cals = new Label();
				name.setText(todaysMeals.get(i).getName());
				amount.setText(Double.toString(todaysMeals.get(i).getAmount()));
				cals.setText(Double.toString(todaysMeals.get(i).getCalories()));
				gPaneHistory.add(name, 0, i + 1);
				GridPane.setHalignment(name, HPos.CENTER);
				gPaneHistory.add(amount, 1, i + 1);
				GridPane.setHalignment(amount, HPos.CENTER);
				gPaneHistory.add(cals, 2, i + 1);
				GridPane.setHalignment(cals, HPos.CENTER);

			}

			// gets number of rows of table
			int sizee = todaysMeals.size() + 1;
			double todaysDefecit = 0;

			// adds total row
			gPaneHistory.getRowConstraints().add(row);
			GridPane total = new GridPane();
			total.getRowConstraints().add(row);
			gPaneHistory.add(total, 0, sizee);
			GridPane.setHalignment(total, HPos.CENTER);
			total.setStyle("-fx-background-color: #5085a5");
			GridPane.setColumnSpan(total, GridPane.REMAINING);
			name = new Label();
			amount = new Label();
			cals = new Label();
			// gets total cals and defecit for today
			double todaysCals = DailyConsumption.getTodaysCalories(dList, date);
			name.setText("Total Calories:    " + Double.toString(todaysCals));
			if (todaysCals == 0)
				todaysDefecit = todaysCals - goalCals;
			amount.setText("Net Calories:  " + Double.toString(todaysDefecit));
			cals.setText("");
			total.add(name, 0, 0);
			GridPane.setColumnSpan(name, 2);
			GridPane.setHalignment(name, HPos.CENTER);
			GridPane.setValignment(name, VPos.CENTER);
			total.add(amount, 1, 0);
			GridPane.setColumnSpan(amount, 2);
			GridPane.setHalignment(amount, HPos.CENTER);
			GridPane.setValignment(amount, VPos.CENTER);
			total.add(cals, 2, 0);
			GridPane.setHalignment(cals, HPos.CENTER);
			GridPane.setValignment(cals, VPos.CENTER);
			total.getColumnConstraints().add(col);
			total.getColumnConstraints().add(col);
			total.getColumnConstraints().add(col);
			name.setFont(new Font(16));
			name.setTextFill(Color.web("White"));
			amount.setFont(new Font(16));
			amount.setTextFill(Color.web("White"));
			cals.setFont(new Font(16));
			cals.setTextFill(Color.web("White"));
			total.setPrefWidth(537);

		}
	}

	/**
	 * return all the meals eaten in selected date range as well as total, and net
	 * calories for that range
	 * 
	 * @param event
	 */
	@FXML
	void dateRangeSearch(ActionEvent event) {

		if (dPickerDateRangeOne.getValue() == null || dPickerDateRangeTwo.getValue() == null
				|| dPickerDateRangeOne.getValue().compareTo(dPickerDateRangeTwo.getValue()) > 0) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Date Error");
			alert.setHeaderText("Dates Selected Are Wrong");
			alert.setContentText("You must select both dates, and the 2nd date must be greater than the 1st date");
			alert.showAndWait();
		} else {
			// add table headers
			GridPane gPaneHistory = new GridPane();
			scrollHistory.setContent(gPaneHistory);
			gPaneHistory.setPrefWidth(537);
			RowConstraints row = new RowConstraints(60);
			gPaneHistory.getRowConstraints().add(row);
			GridPane Title = new GridPane();
			Title.getRowConstraints().add(row);
			gPaneHistory.add(Title, 0, 0);
			GridPane.setHalignment(Title, HPos.CENTER);
			Title.setStyle("-fx-background-color: #5085a5");
			GridPane.setColumnSpan(Title, GridPane.REMAINING);
			Label date = new Label();
			Label name = new Label();
			Label amount = new Label();
			Label cals = new Label();
			date.setText("Date");
			name.setText("Food");
			amount.setText("Amount");
			cals.setText("Calories");
			Title.add(date, 0, 0);
			GridPane.setHalignment(date, HPos.CENTER);
			GridPane.setValignment(date, VPos.CENTER);
			Title.add(name, 1, 0);
			GridPane.setHalignment(name, HPos.CENTER);
			GridPane.setValignment(name, VPos.CENTER);
			Title.add(amount, 2, 0);
			GridPane.setHalignment(amount, HPos.CENTER);
			GridPane.setValignment(amount, VPos.CENTER);
			Title.add(cals, 3, 0);
			GridPane.setHalignment(cals, HPos.CENTER);
			GridPane.setValignment(cals, VPos.CENTER);
			ColumnConstraints col = new ColumnConstraints(134);
			Title.getColumnConstraints().add(col);
			Title.getColumnConstraints().add(col);
			Title.getColumnConstraints().add(col);
			Title.getColumnConstraints().add(col);
			date.setFont(new Font(16));
			date.setTextFill(Color.web("White"));
			name.setFont(new Font(16));
			name.setTextFill(Color.web("White"));
			amount.setFont(new Font(16));
			amount.setTextFill(Color.web("White"));
			cals.setFont(new Font(16));
			cals.setTextFill(Color.web("White"));

			// double values of date
			LocalDate dateOne = dPickerDateRangeOne.getValue();
			LocalDate dateTwo = dPickerDateRangeTwo.getValue();

			// set dateTrcker to first day as inital value for loop
			LocalDate dateTracker = dateOne;

			// size vriable to track length of table
			int size = 0;

			// track total cals
			double calorieTracker = 0.0;
			int validDates = 0;

			// loop through every date in range
			while ((dateTracker.compareTo(dateTwo) <= 0)) {

				// update MM-DD value
				String date1 = dateTracker.toString().substring(5);

				// create meals arrayList of meals in day of date range
				ArrayList<Meal> todaysMeals = DailyConsumption.getTodaysMeals(dList, date1);

				// get calories for that day
				double todaysCals = DailyConsumption.getTodaysCalories(dList, date1);
				calorieTracker += todaysCals;
				if (DailyConsumption.getTodaysCalories(dList, date1) != 0)
					validDates++;

				// incerement dateTracker variable
				dateTracker = dateTracker.plusDays(1);

//add data for 1 day
				for (int i = 0; i < todaysMeals.size(); i++) {
					RowConstraints row1 = new RowConstraints(50);
					gPaneHistory.getRowConstraints().add(row1);
					date = new Label();
					name = new Label();
					amount = new Label();
					cals = new Label();
					date.setText(todaysMeals.get(i).getDate());
					name.setText(todaysMeals.get(i).getName());
					amount.setText(Double.toString(todaysMeals.get(i).getAmount()));
					cals.setText(Double.toString(todaysMeals.get(i).getCalories()));
					gPaneHistory.getColumnConstraints().add(col);
					gPaneHistory.getColumnConstraints().add(col);
					gPaneHistory.getColumnConstraints().add(col);
					gPaneHistory.getColumnConstraints().add(col);
					gPaneHistory.add(date, 0, size + 1);
					GridPane.setHalignment(date, HPos.CENTER);
					gPaneHistory.add(name, 1, size + 1);
					GridPane.setHalignment(name, HPos.CENTER);
					gPaneHistory.add(amount, 2, size + 1);
					GridPane.setHalignment(amount, HPos.CENTER);
					gPaneHistory.add(cals, 3, size + 1);
					GridPane.setHalignment(cals, HPos.CENTER);
					gPaneHistory.setPrefWidth(537);

					// increment size
					size++;
				}

			}

			// get net calories for range
			double netCalorieTracker = calorieTracker - validDates * goalCals;

			// add total row
			int sizee = size + 1;
			gPaneHistory.getRowConstraints().add(row);
			GridPane total = new GridPane();
			total.getRowConstraints().add(row);
			gPaneHistory.add(total, 0, sizee);
			GridPane.setHalignment(total, HPos.CENTER);
			total.setStyle("-fx-background-color: #5085a5");
			GridPane.setColumnSpan(total, GridPane.REMAINING);
			Label totalCalsDateRange = new Label();
			Label calDefDateRange = new Label();
			totalCalsDateRange.setText("Total Calories: " + Double.toString(calorieTracker));
			calDefDateRange.setText("Net Calories: " + Double.toString(netCalorieTracker));
			total.add(totalCalsDateRange, 0, 0);
			GridPane.setHalignment(totalCalsDateRange, HPos.CENTER);
			GridPane.setValignment(totalCalsDateRange, VPos.CENTER);
			total.add(calDefDateRange, 1, 0);
			GridPane.setHalignment(calDefDateRange, HPos.CENTER);
			GridPane.setValignment(calDefDateRange, VPos.CENTER);
			ColumnConstraints columnTotalDateRange = new ColumnConstraints(267);
			total.getColumnConstraints().add(columnTotalDateRange);
			total.getColumnConstraints().add(columnTotalDateRange);
			totalCalsDateRange.setFont(new Font(16));
			totalCalsDateRange.setTextFill(Color.web("White"));
			calDefDateRange.setFont(new Font(16));
			calDefDateRange.setTextFill(Color.web("White"));
			total.setPrefWidth(537);

		}
	}

	/**
	 * return calorie data for selected month
	 * 
	 * @param event
	 */
	@FXML

	void monthSearch(ActionEvent event) {

		if (selectMonth.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Empty Date");
			alert.setHeaderText("No Date Entered");
			alert.setContentText("You must select a month");
			alert.showAndWait();
		}

		else {
			// sets header
			GridPane gPaneHistory = new GridPane();
			scrollHistory.setContent(gPaneHistory);
			gPaneHistory.setPrefWidth(537);
			RowConstraints row = new RowConstraints(60);
			gPaneHistory.getRowConstraints().add(row);
			GridPane Title = new GridPane();
			Title.getRowConstraints().add(row);
			gPaneHistory.add(Title, 0, 0);
			GridPane.setHalignment(Title, HPos.CENTER);
			Title.setStyle("-fx-background-color: #5085a5");
			GridPane.setColumnSpan(Title, GridPane.REMAINING);
			Label name = new Label();
			Label amount = new Label();
			Label cals = new Label();
			name.setText("Month");
			amount.setText("Calories Consumed");
			cals.setText("Net Calories");
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

			String tempMonth;
			double calorieTracker = 0;
			double netCalorieTracker = 0;
			String month = selectMonth.getValue().toString().substring(0, 2);

			// loops through every day that matches month
			for (int i = 0; i < dList.size(); i++) {
				tempMonth = dList.get(i).getDate().substring(0, 2);

				if (tempMonth.equals(month)) {
					calorieTracker += DailyConsumption.getTodaysCalories(dList, dList.get(i).getDate());
					System.out.println(dList.get(i).getDate());
					netCalorieTracker++;
				}
			}

			// get net calories consumed
			netCalorieTracker = calorieTracker - netCalorieTracker * goalCals;

			// prints result of month search if not empty month
			if (calorieTracker != 0) {
				gPaneHistory.getRowConstraints().add(row);
				name = new Label();
				amount = new Label();
				cals = new Label();
				name.setText(month);
				amount.setText(Double.toString(calorieTracker));
				cals.setText(Double.toString(netCalorieTracker));
				gPaneHistory.add(name, 0, 1);
				GridPane.setHalignment(name, HPos.CENTER);
				GridPane.setValignment(name, VPos.CENTER);
				gPaneHistory.add(amount, 1, 1);
				GridPane.setHalignment(amount, HPos.CENTER);
				GridPane.setValignment(amount, VPos.CENTER);
				gPaneHistory.add(cals, 2, 1);
				GridPane.setHalignment(cals, HPos.CENTER);
				GridPane.setValignment(cals, VPos.CENTER);
				gPaneHistory.getColumnConstraints().add(col);
				gPaneHistory.getColumnConstraints().add(col);
				gPaneHistory.getColumnConstraints().add(col);

				gPaneHistory.setPrefWidth(537);

			}
		}
	}

	/**
	 * return the calorie data for each month in selected month range as well as
	 * total, and net calories for that range
	 * 
	 * @param event
	 */
	@FXML
	void monthRangeSearch(ActionEvent event) {

		if (selectMonthRangeOne.getValue() == null || selectMonthRangeTwo.getValue() == null
				|| selectMonthRangeOne.getValue().compareTo(selectMonthRangeTwo.getValue()) > 0) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Date Error");
			alert.setHeaderText("Dates Selected Are Wrong");
			alert.setContentText("You must select both months, and the 2nd month must be greater than the 1st month");
			alert.showAndWait();
		} else {
			// sets header
			GridPane gPaneHistory = new GridPane();
			scrollHistory.setContent(gPaneHistory);
			gPaneHistory.setPrefWidth(537);
			RowConstraints row = new RowConstraints(60);
			gPaneHistory.getRowConstraints().add(row);
			GridPane Title = new GridPane();
			Title.getRowConstraints().add(row);
			gPaneHistory.add(Title, 0, 0);
			GridPane.setHalignment(Title, HPos.CENTER);
			Title.setStyle("-fx-background-color: #5085a5");
			GridPane.setColumnSpan(Title, GridPane.REMAINING);
			Label name = new Label();
			Label amount = new Label();
			Label cals = new Label();
			name.setText("Month");
			amount.setText("Calories Consumed");
			cals.setText("Net Calories");
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

			// populates List
			String tempMonth; // tracks month
			String monthOne = selectMonthRangeOne.getValue().toString().substring(0, 2);
			String monthTwo = selectMonthRangeTwo.getValue().toString().substring(0, 2);
			Double doubleMonthTwo = Double.parseDouble(monthTwo);
			String monthTracker = monthOne;
			int intMonthTracker = Integer.parseInt(monthOne);
			// tracks calories
			double calorieTracker = 0;
			double totalCalorieTracker = 0;
			double netCalorieTracker = 0;
			double totalNetCalorieTracker = 0;
			int size = 0;

			while (intMonthTracker <= doubleMonthTwo) {
				for (int i = 0; i < dList.size(); i++) {
					tempMonth = dList.get(i).getDate().substring(0, 2);

					if (monthTracker.equals(tempMonth)) {
						double todaysCals = DailyConsumption.getTodaysCalories(dList, dList.get(i).getDate());
						calorieTracker += todaysCals;
						netCalorieTracker += todaysCals - goalCals;
					}

				}

				//if meal data for month, print result as row
				if (calorieTracker > 0) {
					// prints result of month search
					gPaneHistory.getRowConstraints().add(row);
					name = new Label();
					amount = new Label();
					cals = new Label();
					name.setText(monthTracker);
					amount.setText(Double.toString(calorieTracker));
					cals.setText(Double.toString(netCalorieTracker));
					gPaneHistory.add(name, 0, size + 1);
					GridPane.setHalignment(name, HPos.CENTER);
					GridPane.setValignment(name, VPos.CENTER);
					gPaneHistory.add(amount, 1, size + 1);
					GridPane.setHalignment(amount, HPos.CENTER);
					GridPane.setValignment(amount, VPos.CENTER);
					gPaneHistory.add(cals, 2, size + 1);
					GridPane.setHalignment(cals, HPos.CENTER);
					GridPane.setValignment(cals, VPos.CENTER);
					gPaneHistory.getColumnConstraints().add(col);
					gPaneHistory.getColumnConstraints().add(col);
					gPaneHistory.getColumnConstraints().add(col);
					size++;
				}
				intMonthTracker++;
				
				//adds 0 to beggining of month if less than 10
				if (intMonthTracker <= 9)
					monthTracker = "0" + Integer.toString(intMonthTracker);
				else
					monthTracker = Integer.toString(intMonthTracker);
				totalCalorieTracker += calorieTracker;
				totalNetCalorieTracker += netCalorieTracker;
				calorieTracker = 0;
				netCalorieTracker = 0;

			}

			// total
			gPaneHistory.getRowConstraints().add(row);
			GridPane total = new GridPane();
			total.getRowConstraints().add(row);
			gPaneHistory.add(total, 0, size + 1);
			GridPane.setHalignment(total, HPos.CENTER);
			total.setStyle("-fx-background-color: #5085a5");
			GridPane.setColumnSpan(total, GridPane.REMAINING);
			Label totalMonthRange = new Label();
			Label totalCalsMonthRange = new Label();
			Label calDefMonthRange = new Label();
			totalMonthRange.setText("Total");
			totalCalsMonthRange.setText(Double.toString(totalCalorieTracker));
			calDefMonthRange.setText(Double.toString(totalNetCalorieTracker));
			total.add(totalMonthRange, 0, 0);
			GridPane.setHalignment(totalMonthRange, HPos.CENTER);
			GridPane.setValignment(totalMonthRange, VPos.CENTER);
			total.add(totalCalsMonthRange, 1, 0);
			GridPane.setHalignment(totalCalsMonthRange, HPos.CENTER);
			GridPane.setValignment(totalCalsMonthRange, VPos.CENTER);
			total.add(calDefMonthRange, 2, 0);
			GridPane.setHalignment(calDefMonthRange, HPos.CENTER);
			GridPane.setValignment(calDefMonthRange, VPos.CENTER);
			ColumnConstraints columnTotalMonthRange = new ColumnConstraints(179);
			total.getColumnConstraints().add(columnTotalMonthRange);
			total.getColumnConstraints().add(columnTotalMonthRange);
			total.getColumnConstraints().add(columnTotalMonthRange);
			totalMonthRange.setFont(new Font(16));
			totalMonthRange.setTextFill(Color.web("White"));
			totalCalsMonthRange.setFont(new Font(16));
			totalCalsMonthRange.setTextFill(Color.web("White"));
			calDefMonthRange.setFont(new Font(16));
			calDefMonthRange.setTextFill(Color.web("White"));
			total.setPrefWidth(537);

		}
	}

	/**
	 * goes to profile page when icon clicked
	 * 
	 * @param event mouse click on icon
	 * @throws IOException
	 */
	@FXML
	void profile(MouseEvent event) throws IOException {
		Parent createScreen = FXMLLoader.load(getClass().getResource("/view/Profile.fxml"));
		scrollScene.setContent(createScreen);
	}

	/**
	 * goes to meal adder page when icon clicked
	 * 
	 * @param event mouse click on icon
	 * @throws IOException
	 */
	@FXML
	void add(MouseEvent event) throws IOException {
		Parent createScreen = FXMLLoader.load(getClass().getResource("/view/MealAdder.fxml"));
		scrollScene.setContent(createScreen);
	}

	/**
	 * goes to homepage page when icon clicked
	 * 
	 * @param event mouse click on icon
	 * @throws IOException
	 */
	@FXML
	void homepage(MouseEvent event) throws IOException {
		Parent createScreen = FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"));
		scrollScene.setContent(createScreen);
	}

}
