package ck.swing1.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener {
	
	private JButton saveButton;
	private JButton refreshButton;
	private ToolbarListener toolbarListener;
	
	public Toolbar() {
		
		setBorder(BorderFactory.createEtchedBorder());
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		saveButton.setIcon(createIcon("/ck/swing1/images/Save16.gif"));
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(this);
		refreshButton.setIcon(createIcon("/ck/swing1/images/Refresh16.gif"));
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(saveButton);
		add(refreshButton);
		
	}
	
	private ImageIcon createIcon(String path) {
		URL url = getClass().getResource(path);
		if (url == null) {
			System.err.println("Unable to load image: " + path);	
		}
		
		ImageIcon imageIcon = new ImageIcon(url);
		return imageIcon;
	}
	
	public void setToolbarListener(ToolbarListener listener) {
		this.toolbarListener = listener;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if (clicked == saveButton) {
			if (toolbarListener != null) {
				toolbarListener.saveEventOccured();
			}
		}
		if (clicked == refreshButton) {
			if (toolbarListener != null) {
				toolbarListener.refreshEventOccured();
			}
		}
	}

	
}
