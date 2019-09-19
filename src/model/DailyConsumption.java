package model;

import java.util.ArrayList;

/**
 * This is the model class for DailyConsumption objects. These objects hold the
 * meals for a specific day.
 * 
 * @author Anup Sheena
 */
public class DailyConsumption {

	private String date;
	private ArrayList<Meal> meals;

	/**
	 * Constructs an object with specified date and specified ArrayList of Meal
	 * objects.
	 * 
	 * @param date  the date of the meals
	 * @param meals the array list of all meals eaten in that day
	 */
	public DailyConsumption(String date, ArrayList<Meal> meals) {
		super();
		this.date = date;
		this.meals = meals;
	}

	/**
	 * Default constructor for DailyConsumption object.
	 */
	public DailyConsumption() {

	}

	/**
	 * Retrieves date
	 * 
	 * @return date
	 */

	public String getDate() {
		return date;
	}

	/**
	 * Sets date
	 * 
	 * @param date
	 */

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * returns ArrayList of meal objects
	 * 
	 * @return meals the class variable meals
	 */
	public ArrayList<Meal> getMeals() {
		return meals;
	}

	/**
	 * sets the class variable of Meal Array List
	 * 
	 * @param meals user specified ArrayList of meals
	 */
	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}

	/**
	 * returns ArrayList of Meal objects for a specific day
	 * 
	 * @param dList ArrayList of DailyConsumption objects
	 * @param datee date of which meals are wanted
	 * @return todaysMeals ArrayList of Meal objects
	 */
	public static ArrayList<Meal> getTodaysMeals(ArrayList<DailyConsumption> dList, String datee) {
		ArrayList<Meal> todaysMeals = new ArrayList<Meal>();
		for (int i = 0; i < dList.size(); i++) {

			if (dList.get(i).getDate().equals(datee)) {
				todaysMeals = dList.get(i).getMeals();
			}
		}
		return todaysMeals;
	}

	/**
	 * returns calories consumed for a specific day
	 * 
	 * @param dList ArrayList of DailyConsumption objects
	 * @param datee date of which calories consumed are wanted
	 * @return calTraker calories consumed for specified day
	 */
	public static double getTodaysCalories(ArrayList<DailyConsumption> dList, String datee) {
		double calTracker = 0;
		ArrayList<Meal> todaysMeals = new ArrayList<Meal>();
		for (int i = 0; i < dList.size(); i++) {

			if (dList.get(i).getDate().equals(datee)) {
				todaysMeals = dList.get(i).getMeals();
			}
		}
		for (int j = 0; j < todaysMeals.size(); j++) {
			calTracker += todaysMeals.get(j).getCalories();
		}
		return calTracker;
	}

}