package model;
/**
 * @author Anup Sheena
 * this is the model class for Meal objects. It implements a comparable interface, so Meal objects can be compared
 *
 */
public class Meal implements Comparable<Meal> {
	
	private String date;
	private String name;
	private double amount;
	private String unit;
	private Double calories;

	/**
	 * Constructs a Meal object  
	 * 
	 * @param date date meal was consumed
	 * @param name name of meal
	 * @param amount amount of meal
	 * @param unit unit of meal
	 * @param calories calories of meal
	 */
	public Meal(String date, String name, double amount, String unit, Double calories)  {
		super();
		this.date = date;
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.calories = calories;
	}
	
	/**
	 * default constructor for Meal object
	 */
	public Meal() {
	}
	
	/**
	 * getter for date
	 * @return date of meal consumed
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * setter for date
	 * @param date 
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * getter for name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * setter for name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getter for amount
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * setter for amount
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * getter for unit
	 * @return unit
	 */
	public String getUnit() {
		return unit;
	}
	
	/**
	 * setter for unit
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/**
	 * getter for calories
	 * @return calories
	 */
	public double getCalories() {
		return calories;
	}
	
	/**
	 * setter for calories
	 * @param calories
	 */
	public void setCalories(double calories) {
		this.calories = calories;
	}

	/**
	 * method to compare two meals
	 * @return -1 if m1>m2, 0 if m1=m2, 1 if m1>m2
	 */
	@Override
	public int compareTo(Meal m1) {
		return this.getDate().compareTo(m1.getDate());
	}
	
}
