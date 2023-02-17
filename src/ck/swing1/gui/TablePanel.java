package ck.swing1.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ck.swing1.model.EmploymentCategory;
import ck.swing1.model.Person;

public class TablePanel extends JPanel {
	
	private JTable table;
	private PersonTableModel tableModel;
	private JPopupMenu popUp;
	private PersonTableListener personTableListener;
	
	public TablePanel() {
		tableModel = new PersonTableModel();
		table = new JTable(tableModel);
		popUp = new JPopupMenu();
		
		table.setDefaultRenderer(EmploymentCategory.class, new EmploymentCategoryRenderer());
		table.setDefaultEditor(EmploymentCategory.class, new EmploymentCategoryEditor());
		table.setRowHeight(21);
		
		JMenuItem removeItem = new JMenuItem("Delete row");
		
		removeItem.addActionListener(e -> {
			int row = table.getSelectedRow();
			
			if (personTableListener != null) {
				personTableListener.rowDeleted(row);
				tableModel.fireTableRowsDeleted(row, row);
			}
			
		});
		popUp.add(removeItem);
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				
				if (e.getButton() == MouseEvent.BUTTON3)
				popUp.show(table, e.getX(), e.getY());
			}
		});
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void setData(List<Person> db) {
		tableModel.setData(db);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
		
	}

	public void setPersonTableListener(PersonTableListener listener) {
		this.personTableListener = listener;
	}

}
