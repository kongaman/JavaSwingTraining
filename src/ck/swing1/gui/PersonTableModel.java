package ck.swing1.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ck.swing1.model.EmploymentCategory;
import ck.swing1.model.Person;

public class PersonTableModel extends AbstractTableModel {
	
	private List<Person> db;
	private String[] colNames = {"ID", "Name", "Occupation", "AgeCategory", "EmploymenCategory", "US Citizen", "Tax-ID"};
	
	public PersonTableModel() {
	}
	
	public void setData (List<Person> db) {
		this.db = db;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return colNames[columnIndex];
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 1:
			return true;
		case 5:
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (db == null) return;
		Person person = db.get(rowIndex);
		switch (columnIndex) {
		case 1:
			person.setName((String) value);
			break;
		case 5:
			person.setUsCitizen((boolean) value);
			break;
		default:
			return;
		}
	}

	@Override
	public int getRowCount() {
		return db.size();
	}
	
	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
		case 0:
			return Integer.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return EmploymentCategory.class;
		case 5:
			return Boolean.class;
		case 6:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Person person = db.get(rowIndex);
		switch(columnIndex) {
			case 0:
				return person.getId();
			case 1:
				return person.getName();
			case 2:
				return person.getOccupation();
			case 3:
				return person.getAgeCategory();
			case 4:
				return person.getEmpCat();
			case 5:
				return person.isUsCitizen();
			case 6:
				return person.getTaxId();
		}
		return null;
	}

}
