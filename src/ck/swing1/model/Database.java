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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Database {
	
	private Connection conn;
	
	private List<Person> people;

	public Database() {
		people = new LinkedList<>();
	}
	
	public void connect() throws Exception {
		
		if (conn != null) return;
		
		final String connectionUrl = "jdbc:mysql://localhost:3306/swingtest";
		final String username = "root";
		final String password = "I7P3meaTywhAHUxhlhro";

		conn = DriverManager.getConnection(connectionUrl, username, password);
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
		for (Person person : people) {
			int id = person.getId();
			checkStmt.setInt(1, id);
			ResultSet checkResultSet =  checkStmt.executeQuery();
			checkResultSet.next();
			int count = checkResultSet.getInt(1);
			System.out.println("Count for person with ID: " + id + " is " + count);
		}
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
