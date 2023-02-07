package ck.swing1.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class PrefsDialog extends JDialog {

	private JButton okButton;
	private JButton cancelButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;
	private JTextField userField;
	private JPasswordField passField;
	private PrefsListener prefsListener;

	public PrefsDialog(JFrame parent) {
		super(parent, "Preferences", false);

		setSize(300, 220);
		setLocationRelativeTo(parent);

		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerModel);

		userField = new JTextField(10);
		passField = new JPasswordField(10);
		passField.setEchoChar('*');

		layoutControls();

		okButton.addActionListener(e -> {
			int port = (int) portSpinner.getValue();
			String user = userField.getText();
			char[] password = passField.getPassword();
			String pass = new String(password);

			if (prefsListener != null) {
				prefsListener.preferencesSet(user, pass, port);
			}

			setVisible(false);
		});

		cancelButton.addActionListener(e -> {

			setVisible(false);
		});
	}

	private void layoutControls() {
		
		JPanel controlsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		int space = 5;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");
		
		controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
		
		controlsPanel.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);
		
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		// Row
		gc.gridy = 0;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("User: "), gc);
		gc.gridx++;
		gc.insets = noPadding;
		gc.anchor = GridBagConstraints.LINE_START;
		controlsPanel.add(userField, gc);

		// Row
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Password: "), gc);
		gc.gridx++;
		gc.insets = noPadding;
		gc.anchor = GridBagConstraints.LINE_START;
		controlsPanel.add(passField, gc);

		// Row
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Port: "), gc);
		gc.gridx++;
		gc.insets = noPadding;
		gc.anchor = GridBagConstraints.LINE_START;
		controlsPanel.add(portSpinner, gc);

		// ButtonsPanel
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		
		Dimension btnSize = cancelButton.getPreferredSize();
		okButton.setPreferredSize(btnSize);
		
		//Add Subpanels to Dialog
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);

	}

	public void setDefaults(String user, String passowrd, int port) {
		userField.setText(user);
		passField.setText(passowrd);
		portSpinner.setValue(port);
	}

	public void setPrefsListener(PrefsListener listener) {
		this.prefsListener = listener;
	}

}
