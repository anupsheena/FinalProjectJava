package model;

/**
 * This class is the model class for Profile objects
 * 
 * @author Anup Sheena
 */

public class Profile {

	private String username;
	private String password;
	private int age;
	private String sex;
	private double weight;
	private double height;
	private double goalIntake;

	/**
	 * constructor for Profile
	 * 
	 * @param username   username of user
	 * @param password   password of user
	 * @param age        age of user
	 * @param sex        sex of user
	 * @param weight     weight of user
	 * @param height     height of user
	 * @param goalIntake goal calorie intake of user
	 */
	public Profile(String username, String password, int age, String sex, double weight, double height,
			double goalIntake) {
		setusername(username);
		setpassword(password);
		setAge(age);
		setWeight(weight);
		setHeight(height);
		setGoalIntake(goalIntake);
	}

	/**
	 * default constructor for Profile object
	 */
	public Profile() {

	}

	/**
	 * getter for username
	 * 
	 * @return username
	 */
	public String getusername() {
		return username;
	}

	/**
	 * getter for password
	 * 
	 * @return password
	 */
	public String getpassword() {
		return password;
	}

	/**
	 * getter for age
	 * 
	 * @return age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * getter for height
	 * 
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * getter for weight
	 * 
	 * @return weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * getter for goal calorie intake
	 * 
	 * @return goalIntake
	 */

	public double getGoalIntake() {
		return goalIntake;
	}

	/**
	 * getter for sex
	 * 
	 * @return sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * setter for age
	 * 
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * setter for height
	 * 
	 * @param height
	 */
	public void setHeight(double height) {

		this.height = height;

	}

	/**
	 * setter for weight
	 * 
	 * @param Weight
	 */
	public void setWeight(double Weight) {

		this.weight = Weight;

	}

	/**
	 * setter for goal calorie intake
	 * 
	 * @param goalIntake
	 */
	public void setGoalIntake(double goalIntake) {

		this.goalIntake = goalIntake;

	}

	/**
	 * setter for username
	 * 
	 * @param username
	 */
	public void setusername(String username) {

		this.username = username;

	}

	/**
	 * setter for password
	 * 
	 * @param password
	 */
	public void setpassword(String password) {

		this.password = password;

	}

	/**
	 * setter for sex
	 * 
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;

	}

}
