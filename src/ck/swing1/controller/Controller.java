package ck.swing1.controller;

import java.util.List;

import ck.swing1.gui.FormEvent;
import ck.swing1.model.AgeCategory;
import ck.swing1.model.Database;
import ck.swing1.model.EmploymentCategory;
import ck.swing1.model.Gender;
import ck.swing1.model.Person;

public class Controller {
	Database db = new Database();
	
	public List<Person> getPeople() {
		return db.getPeople();
	}
	
	public void addPerson(FormEvent ev) {
		String name = ev.getName();
		String occupation = ev.getOccupation();
		int ageCatId = ev.getAgeCategory();
		String empCat = ev.getEmpCat();
		boolean usCitizen = ev.isUsCitizen();
		String taxID = ev.getTaxId();
		String genderId = ev.getGender();
		
		AgeCategory ageCategory = null;
		switch(ageCatId) {
		case 0:
			ageCategory = AgeCategory.child;
			break;
		case 1:
			ageCategory = AgeCategory.adult;
			break;
		case 2:
			ageCategory = AgeCategory.senior;
			break;
		}
		
		EmploymentCategory empCategory;
		switch(empCat) {
		case "employed":
			empCategory = EmploymentCategory.employed;
			break;
		case "self-employed":
			empCategory = EmploymentCategory.selfEmployed;
			break;
		case "unemployed":
			empCategory = EmploymentCategory.unemployed;
			break;
		default:
			empCategory = EmploymentCategory.other;
			System.err.println(empCat);
			break;
		}
		
		Gender gender;
		if (genderId.equals("male")) {
			gender = Gender.male;
		} else {
			gender = Gender.female;
		}
		
		Person person = new Person(name, occupation, ageCategory, empCategory, taxID, usCitizen, gender);
		db.addPerson(person);
	}

}
