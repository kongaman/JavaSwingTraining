package ck.swing1.test;

import ck.swing1.model.AgeCategory;
import ck.swing1.model.Database;
import ck.swing1.model.EmploymentCategory;
import ck.swing1.model.Gender;
import ck.swing1.model.Person;

public class TestDatabase {

	public static void main(String[] args) {
		System.out.println("Running database test");
		
		Database db = new Database();
		db.addPerson(new Person("Chris", "lion tamer", AgeCategory.adult, EmploymentCategory.employed, "777", true, Gender.male));
		db.addPerson(new Person("Schnucki", "AOK", AgeCategory.adult, EmploymentCategory.selfEmployed, null, false, Gender.female));
		try {
			db.connect();
			db.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.disconnect();		
	}

}
