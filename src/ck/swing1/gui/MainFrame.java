package ck.swing1.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import ck.swing1.controller.Controller;

public class MainFrame extends JFrame{
	
	private Toolbar toolbar;
	private TextPanel textPanel;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;
	private PrefsDialog prefsDialog;
	private Preferences prefs;
	private JSplitPane splitPane;
	private JTabbedPane tabPane;

	public MainFrame()  {
		super("Hello World");
		setLayout(new BorderLayout());
		
		toolbar = new Toolbar();
		textPanel = new TextPanel();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		prefsDialog = new PrefsDialog(this);
		prefs = Preferences.userRoot().node("db");
		tabPane = new JTabbedPane();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabPane);
		
		splitPane.setOneTouchExpandable(true);
		
		tabPane.addTab("Person Database", tablePanel);
		tabPane.addTab("Messages", textPanel);
		
		
		
		controller = new Controller();
		
		tablePanel.setData(controller.getPeople());
		tablePanel.setPersonTableListener(new PersonTableListener() {
			public void rowDeleted(int row) {
				controller.removePerson(row);
			}
		});
		
		prefsDialog.setPrefsListener(new PrefsListener() {
			@Override
			public void preferencesSet(String user, String password, int port) {
				prefs.put("user", user);
				prefs.put("password", password);
				prefs.putInt("port", port);
			}
		});
		
		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		int port = prefs.getInt("port", 3306);
		prefsDialog.setDefaults(user, password, port);
		
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		setJMenuBar(createMenuBar());
		
		toolbar.setToolbarListener(new ToolbarListener() {
			@Override
			public void saveEventOccured() {
				connect();
				try {
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database", "Database Problem", JOptionPane.ERROR_MESSAGE);
				}
				System.out.println("save");
			}

			@Override
			public void refreshEventOccured() {
				connect();
				try {
					controller.load();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database", "Database Problem", JOptionPane.ERROR_MESSAGE);
				}
				tablePanel.refresh();
			}
		});
		
		formPanel.setFormListener(new FormListener() {
			public void formEventOccured(FormEvent ev) {
				controller.addPerson(ev);
				tablePanel.refresh();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Window closing");
				controller.disconnect();
				dispose();
				System.gc();
			}
			
		});
		
		add(toolbar, BorderLayout.PAGE_START);
		add(splitPane, BorderLayout.CENTER);
//		add(formPanel, BorderLayout.WEST);
//		add(tablePanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(500, 400));
		setSize(1000, 500);
		setVisible(true);
	}
	
	private void connect() {
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Can't connect to database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
		}
		
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
		
		JMenuItem prefsItem = new JMenuItem("Preferences...");
		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		prefsItem.addActionListener(e -> {
			prefsDialog.setVisible(true);
		});
		
		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);
		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		windowMenu.add(prefsItem);
		
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		
		showFormItem.addActionListener(e -> {
			JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
			if(menuItem.isSelected()) {
				splitPane.setDividerLocation(formPanel.getMinimumSize().width);
			}
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
			if (action == JOptionPane.OK_OPTION) {
				WindowListener[] windowListeners = getWindowListeners();
				for (WindowListener listener : windowListeners) {
					listener.windowClosing(new WindowEvent(MainFrame.this, 0));
				}
			}
		});
		return menuBar;
	}
}
