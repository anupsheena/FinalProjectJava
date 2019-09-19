package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the is the file handler for the profiles file
 * 
 * @author Anup Sheena
 */
public class ProfileFileHandling {

	private static ArrayList<Profile> profileList = new ArrayList<Profile>();

	/**
	 * getter for the ArrayList of profiles
	 * 
	 * @return profileList the profile list
	 */
	public static ArrayList<Profile> getProfileList() {
		return profileList;
	}

	/**
	 * setter for the ArrayList of profiles
	 * 
	 * @param questionList the array list of questions
	 */
	public static void setProfileList(ArrayList<Profile> profileList) {
		ProfileFileHandling.profileList = profileList;
	}

	/**
	 * reads the file, and uses the setters from Profile.java to create Profile objects
	 * that are then stored in the ArrayList: profileList
	 *
	 * @param file the name of the file
	 */
	public static void readProfilesFromFile(File file) throws FileNotFoundException {
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
					Profile profiles = new Profile();
					profiles.setusername(scnRecord.next());
					profiles.setpassword(scnRecord.next());
					profiles.setAge(Integer.parseInt(scnRecord.next()));
					profiles.setSex(scnRecord.next());
					profiles.setWeight(Double.parseDouble(scnRecord.next()));
					profiles.setHeight(Double.parseDouble(scnRecord.next()));
					profiles.setGoalIntake(Double.parseDouble(scnRecord.next()));
					// adds Profile to ArrayList
					profileList.add(profiles);

				}
				scnRecord.close();
			}

		}

	}
	

	}


