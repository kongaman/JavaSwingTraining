package ck.swing1;

import java.awt.BorderLayout;

import javax.swing.JFrame;

// Controller
public class MainFrame extends JFrame{
	
	private Toolbar toolbar;
	private TextPanel textPanel;
	private FormPanel formPanel;

	public MainFrame()  {
		super("Hello World");
		setLayout(new BorderLayout());
		
		toolbar = new Toolbar();
		textPanel = new TextPanel();
		formPanel = new FormPanel();
		
		toolbar.setStringListener(new StringListener() {
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});
		
		formPanel.setFormListener(new FormListener() {
			public void formEventOccured(FormEvent e) {
				String name = e.getName();
				String occupation = e.getOccupation();
				int ageCat = e.getAgeCategory();
				String empCat = e.getEmpCat();
				boolean usCitizen = e.isUsCitizen();
				String taxID = e.getTaxId();
				String gender = e.getGender();
				
				
				textPanel.appendText(name + ": " + occupation + "\n");
				textPanel.appendText("AgeCat: " + ageCat + " - Employment: " + empCat + "\n");
				if (usCitizen) textPanel.appendText("is us citizen, tax id: " + taxID + "\n");
				textPanel.appendText("Gender: " + gender + "\n");
			}
		});
		
		add(toolbar, BorderLayout.NORTH);
		add(formPanel, BorderLayout.WEST);
		add(textPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,500);
		setVisible(true);
	}
	
	

}
