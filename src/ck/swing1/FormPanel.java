package ck.swing1;

import java.awt.Dimension;

import javax.swing.JPanel;

public class FormPanel extends JPanel {

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
	}
}
