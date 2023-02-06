package ck.swing1.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class PrefsDialog extends JDialog {
	
	private JButton okButton;
	private JButton cancelButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;
	
	public PrefsDialog(JFrame parent) {
		super(parent, "Preferences", false);
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		
		spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerModel);
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;
		
		//Row
		gc.gridy = 0;
		gc.gridx = 0;
		add(new JLabel("Port: "), gc);
		gc.gridx++;
		add(portSpinner, gc);
		
		//Row
		gc.gridy++;
		gc.gridx = 0;
		add(okButton, gc);
		gc.gridx++;
		add(cancelButton, gc);
		
		okButton.addActionListener(e -> {
			int value = (int) portSpinner.getValue();
			System.out.println(value);
			
			setVisible(false);
		});
		
		cancelButton.addActionListener(e -> {
			
			setVisible(false);
		});
	}

}
