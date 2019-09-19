package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Anup Sheena
 * 
 *         This class is the is the file handler for the meals file
 * 
 */
public class MealFileHandling {

	private static ArrayList<Meal> mealList = new ArrayList<Meal>();
	private static ArrayList<DailyConsumption> dailyList = new ArrayList<DailyConsumption>();

	/**
	 * getter for the ArrayList of Meals
	 * 
	 * @return the meal list
	 */
	public static ArrayList<Meal> getMealList() {
		return mealList;
	}

	/**
	 * getter for the ArrayList of DailyConsumption objects
	 * 
	 * @return the daily list
	 */
	public static ArrayList<DailyConsumption> getDailyList() {
		return dailyList;
	}

	/**
	 * setter for the ArrayList of meals
	 * 
	 * @param mealList the array list of Meals
	 */
	public static void setMealList(ArrayList<Meal> mealList) {
		MealFileHandling.mealList = mealList;
	}

	/**
	 * setter for ArrayList of DailyConsumption objects
	 * 
	 * @param dailyList the arrayList of daily consumption objects
	 */
	public static void setDailylist(ArrayList<DailyConsumption> dailyList) {
		MealFileHandling.dailyList = dailyList;
	}

	/**
	 * reads the file, and uses the setters from Meal POJO to create meal objects
	 * that are then stored in the ArrayList: mealList. Then loops through question
	 * meal objects and stores every unique day in a DailyConsumption object, and
	 * stores these objects in the ArrayList: dailyList
	 *
	 * @param file the name of the file
	 * @throws FileNotFoundException if file cannot be found
	 */
	public static void readMealsFromFile(File file) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(file)) {
			// use line separator that will be consistent across applications
			scanner.useDelimiter(System.getProperty("line.separator"));

			// while loop to loop through records
			while (scanner.hasNext()) {
				String record = scanner.next();
				Scanner scnRecord = new Scanner(record);
				// use | as a delimeter between fields
				scnRecord.useDelimiter(":");
				// while loop to loop through fields
				while (scnRecord.hasNext()) {
					Meal meals = new Meal();
					meals.setDate(scnRecord.next());
					meals.setName(scnRecord.next());
					meals.setAmount(Double.parseDouble(scnRecord.next()));
					meals.setUnit(scnRecord.next());
					meals.setCalories(Double.parseDouble(scnRecord.next()));
					// adds Meal to ArrayList
					mealList.add(meals);

				}
				scnRecord.close();
			}

			// sort mealList
			Collections.sort(mealList);
			// initialize dateTracker
			String dateTracker = mealList.get(0).getDate();
			// variable to hold temp DailyConsumption objects
			DailyConsumption dayMeal = new DailyConsumption();
			// variable to hold arraylist of meals
			ArrayList<Meal> dailyMealList = new ArrayList<Meal>();
			for (int i = 0; i < mealList.size(); i++) {

				// puts all of the same dates into Meals ArrayList
				if (mealList.get(i).getDate().equals(dateTracker)) {
					dailyMealList.add(mealList.get(i));

					// if at last index adds object to arrayList
					if (i == (mealList.size() - 1)) {
						dayMeal.setDate(dateTracker);
						dayMeal.setMeals(dailyMealList);
						dailyList.add(dayMeal);

					}

				}
				// if at last index add object to ArrayList

				else if (i == (mealList.size() - 1)) {
					dailyMealList.add(mealList.get(i));
					dayMeal.setDate(dateTracker);
					dayMeal.setMeals(dailyMealList);
					dailyList.add(dayMeal);

				}
				// when new date, adds DailyConsumption object into arrayList
				else {
					dayMeal.setDate(dateTracker);
					dayMeal.setMeals(dailyMealList);
					dailyList.add(dayMeal);

					// reset variables
					dateTracker = mealList.get(i).getDate();
					dailyMealList = new ArrayList<Meal>();
					dailyMealList.add(mealList.get(i));

				}
				// resets temp object used to store Daily Consumption
				dayMeal = new DailyConsumption();

			}

		}
	}

}
