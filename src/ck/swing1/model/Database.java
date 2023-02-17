package ck.swing1.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Database {
	
	private Connection conn;
	
	private int port;
	private String user;
	private String password;
	
	private List<Person> people;

	public Database() {
		people = new LinkedList<>();
	}
	
	public void configure(int port, String user, String password) throws Exception {
		this.port = port;
		this.user = user;
		this.password = password;
		
		if (conn != null) {
			disconnect();
			connect();
		}
	}
	
	public void connect() throws Exception {
		
		if (conn != null) return;
		
		final String connectionUrl = "jdbc:mysql://localhost:3306/swingtest";
//		final String username = "root";
//		final String password = "I7P3meaTywhAHUxhlhro";

		conn = DriverManager.getConnection(connectionUrl, user, password);
		System.out.println("connected: " + conn);
	}
	
	public void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection");
				e.printStackTrace();
			}
		}
	}
	
	public void save() throws SQLException {
		
		String checkSql = "select count(*) as count from swingtest.people where id=?";
		PreparedStatement checkStmt = conn.prepareStatement(checkSql);
		String insertSql = "insert into swingtest.people(id, name, age, employment_status, tax_id, us_citizen, gender, occupation)"
				+ " value (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStmt = conn.prepareStatement(insertSql);
		String updateSql = "update swingtest.people set name = ?, age = ?, employment_status = ?, tax_id = ?, us_citizen = ?, gender = ?, "
				+ "occupation = ? where id = ?";
		PreparedStatement updateStmt = conn.prepareStatement(updateSql);
		for (Person person : people) {
			int id = person.getId();
			String name = person.getName();
			String occupation = person.getOccupation();
			AgeCategory ageCategory = person.getAgeCategory();
			EmploymentCategory empCat = person.getEmpCat();
			String taxId = person.getTaxId();
			boolean usCitizen = person.isUsCitizen();
			Gender gender = person.getGender();
			
			checkStmt.setInt(1, id);
			ResultSet checkResultSet =  checkStmt.executeQuery();
			checkResultSet.next();
			int count = checkResultSet.getInt(1);
			System.out.println("Count for person with ID: " + id + " is " + count);
			
			if (count == 0) {
				System.out.println("Inserting person with id: " + id);
				int col = 1;
				insertStmt.setInt(col++, id);
				insertStmt.setString(col++, name);
				insertStmt.setString(col++, ageCategory.name());
				insertStmt.setString(col++, empCat.name());
				insertStmt.setString(col++, taxId);
				insertStmt.setBoolean(col++, usCitizen);
				insertStmt.setString(col++, gender.name());
				insertStmt.setString(col++, occupation);
				
				insertStmt.executeUpdate();
			} else {
				System.out.println("Updating person with id: " + id);
				int col = 1;
				updateStmt.setString(col++, name);
				updateStmt.setString(col++, ageCategory.name());
				updateStmt.setString(col++, empCat.name());
				updateStmt.setString(col++, taxId);
				updateStmt.setBoolean(col++, usCitizen);
				updateStmt.setString(col++, gender.name());
				updateStmt.setString(col++, occupation);
				updateStmt.setInt(col++, id);
				
				updateStmt.executeUpdate();
			}
		}
		updateStmt.close();
		insertStmt.close();
		checkStmt.close();
	}
	
	public void load() throws SQLException {
		people.clear();
		
		String selectSql = "select id, name, age, employment_status, tax_id, us_citizen, gender, occupation from swingtest.people order by name";
		Statement selectStmt = conn.createStatement();
		
		ResultSet results = selectStmt.executeQuery(selectSql);
		while (results.next()) {
			int id = results.getInt("id");
			String name = results.getString("name");
			String age = results.getString("age");
			String emp = results.getString("employment_status");
			String tax = results.getString("tax_id");
			boolean usCitizen = results.getBoolean("us_citizen");
			String gender = results.getString("gender");
			String occupation = results.getString("occupation");
			
			Person person = new Person(id, name, occupation, AgeCategory.valueOf(age), EmploymentCategory.valueOf(emp), tax, usCitizen,
					Gender.valueOf(gender));
			
			people.add(person);
			System.out.println(person);
		}
		
		results.close();
		selectStmt.close();
	}
	
	public void addPerson(Person person) {
		people.add(person);
	}
	
	public void removePerson(int index) {
		people.remove(index);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}
	
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Person[] persons = people.toArray(new Person[people.size()]);
		oos.writeObject(persons);
		
		oos.close();
		
	}
	
	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			Person[] persons = (Person[]) ois.readObject();
			
			people.clear();
			
			people.addAll(Arrays.asList(persons));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		ois.close();
	}
	
	//MYSQL Database
	
   // CREATE DATABASE `swingtest` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */
/**
   CREATE TABLE `people` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `age` enum('child','adult','senior') NOT NULL,
  `employment_status` varchar(45) NOT NULL,
  `tax_id` varchar(45) DEFAULT NULL,
  `us_citizen` tinyint NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `occupation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 */
	
	
}
