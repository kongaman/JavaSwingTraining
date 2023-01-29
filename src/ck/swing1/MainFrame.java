package ck.swing1;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame{
	
	private TextPanel textPanel;
	private JButton btn;

	public MainFrame()  {
		super("Hello World");
		setLayout(new BorderLayout());
		
		textPanel = new TextPanel();
		btn = new JButton("Click Me!");
		
		btn.addActionListener(e -> {
			textPanel.appendText("Hello!\n");
		});
		
		add(textPanel, BorderLayout.CENTER);
		add(btn, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,500);
		setVisible(true);
	}
	
	

}
