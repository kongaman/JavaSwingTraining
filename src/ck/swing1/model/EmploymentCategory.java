package ck.swing1.model;

public enum EmploymentCategory {
	employed("employed", "10"),
	selfEmployed("self employed", "20"),
	unemployed("unemployed", "30"),
	other("other", "99");
	
	private String longText;
	private String shortNumber;
	
	private EmploymentCategory(String longText, String shortNumber) {
		this.longText = longText;
		this.shortNumber = shortNumber;
	}

	public String getLongText() {
		return longText;
	}

	public String getShortNumber() {
		return shortNumber;
	}
	
	@Override
	public String toString() {
		return shortNumber + " - " + longText;
	}
}
