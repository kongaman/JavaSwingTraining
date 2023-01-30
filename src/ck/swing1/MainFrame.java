package ck.swing1;

import java.awt.BorderLayout;

import javax.swing.JFrame;

// Controller
public class MainFrame extends JFrame{
	
	private Toolbar toolbar;
	private TextPanel textPanel;

	public MainFrame()  {
		super("Hello World");
		setLayout(new BorderLayout());
		
		toolbar = new Toolbar();
		textPanel = new TextPanel();
		
		toolbar.setStringListener(new StringListener() {
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});
		
		add(toolbar, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,500);
		setVisible(true);
	}
	
	

}
