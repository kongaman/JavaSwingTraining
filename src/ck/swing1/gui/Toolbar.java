package ck.swing1.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {
	
	private JButton saveButton;
	private JButton refreshButton;
	private ToolbarListener toolbarListener;
	
	public Toolbar() {
		
		//setBorder(BorderFactory.createEtchedBorder());
		saveButton = new JButton();
		saveButton.addActionListener(this);
		saveButton.setIcon(Utils.createIcon("/ck/swing1/images/Save16.gif"));
		saveButton.setToolTipText("Save");
		
		refreshButton = new JButton();
		refreshButton.addActionListener(this);
		refreshButton.setIcon(Utils.createIcon("/ck/swing1/images/Refresh16.gif"));
		refreshButton.setToolTipText("Refresh");
		
		add(saveButton);
		//addSeparator();
		add(refreshButton);
		
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
