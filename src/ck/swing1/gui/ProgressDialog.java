package ck.swing1.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressDialog extends JDialog {
	
	private JButton cancelButton;
	private JProgressBar progressBar;
	private ProgressDialogListener progressDialogListener;
	
	public ProgressDialog(Window parent, String title) {
		super(parent, title, ModalityType.APPLICATION_MODAL);
		setSize(400, 200);
		
		cancelButton = new JButton("Cancel");
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setString("Retrieving Messages");
		
		setLayout(new FlowLayout());
		
		Dimension size = cancelButton.getPreferredSize();
		size.width = 400;
		progressBar.setPreferredSize(size);
		
		add(progressBar);
		add(cancelButton);
		
		cancelButton.addActionListener(e -> {
			if(progressDialogListener != null) {
				progressDialogListener.progressDialogCanceled();
			}
		});
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(progressDialogListener != null) {
					progressDialogListener.progressDialogCanceled();
				}
			}
		});
		
		pack();
		
		setLocationRelativeTo(parent);
	}

	public void setMaximum(int value) {
		progressBar.setMaximum(value);
	}
	
	public void setValue (int value) {
		int progress = 100 * value/progressBar.getMaximum();
		String prozent = String.format("%d%% complete", progress);
		progressBar.setString("Downloading Message " + value + " of " + progressBar.getMaximum() + " (" + prozent + ")");
		progressBar.setValue(value);
	}

	@Override
	public void setVisible(final boolean visible) {
		SwingUtilities.invokeLater(() -> {
			if (visible == false) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				progressBar.setValue(0);
			}
			ProgressDialog.super.setVisible(visible);
		});
	}
	
	public void setListener(ProgressDialogListener listener) {
		this.progressDialogListener = listener;
	}
}
