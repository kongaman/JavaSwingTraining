package ck.swing1.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import ck.swing1.controller.Controller;

// Controller
public class MainFrame extends JFrame{
	
	private Toolbar toolbar;
	private TextPanel textPanel;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;

	public MainFrame()  {
		super("Hello World");
		setLayout(new BorderLayout());
		
		toolbar = new Toolbar();
		textPanel = new TextPanel();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		
		controller = new Controller();
		
		tablePanel.setData(controller.getPeople());
		
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		setJMenuBar(createMenuBar());
		
		toolbar.setStringListener(new StringListener() {
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});
		
		formPanel.setFormListener(new FormListener() {
			public void formEventOccured(FormEvent ev) {
				controller.addPerson(ev);
				tablePanel.refresh();
			}
		});
		
		add(toolbar, BorderLayout.NORTH);
		add(formPanel, BorderLayout.WEST);
		add(tablePanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500, 400));
		setSize(1000, 500);
		setVisible(true);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Export Data...");
		JMenuItem importDataItem = new JMenuItem("Import Data...");
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);
		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		
		showFormItem.addActionListener(e -> {
			JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
			formPanel.setVisible(menuItem.isSelected());
		});
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		
		importDataItem.addActionListener(e -> {
			if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
				try {
					controller.loadFromFile(fileChooser.getSelectedFile());
					tablePanel.refresh();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		exportDataItem.addActionListener(e -> {
			if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
				try {
					controller.saveToFile(fileChooser.getSelectedFile());
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		exitItem.addActionListener(e -> {
			int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit?" , "Confirm Exit",
					JOptionPane.OK_CANCEL_OPTION);
			if (action == JOptionPane.OK_OPTION) System.exit(0);
		});
		
		return menuBar;
	}

}
