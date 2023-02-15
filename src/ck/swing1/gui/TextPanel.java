package ck.swing1.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {
	
	private JTextArea textArea;

	public TextPanel() {
		
		textArea = new JTextArea();
		textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		textArea.setFont(new Font(Font.SERIF, Font.PLAIN, 20)); //logical font -> Swing chooses a suitable font installed on your machine
		
		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	public void appendText(String text) {
		textArea.append(text);
	}
	
	public void setText(String text) {
		textArea.setText(text);
	}
	
}
