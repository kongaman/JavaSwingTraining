package ck.swing1.gui;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import ck.swing1.model.EmploymentCategory;

public class EmploymentCategoryEditor extends AbstractCellEditor implements TableCellEditor {
	
	private JComboBox<EmploymentCategory> combo;
	
	public EmploymentCategoryEditor() {
		combo = new JComboBox<EmploymentCategory>(EmploymentCategory.values());
	}

	@Override
	public Object getCellEditorValue() {
		EmploymentCategory selectedItem = (EmploymentCategory) combo.getSelectedItem();
		System.out.println("Short: " + selectedItem.getShortNumber());
		System.out.println("Long: " + selectedItem.getLongText());
		System.out.println("Name: " + selectedItem.name());
		System.out.println("Ordinal: " + selectedItem.ordinal());
		System.out.println("EmploymentCategory (toString auto-used): " + selectedItem);
		return combo.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		combo.setSelectedItem(value);
		combo.addActionListener(e -> {
			fireEditingStopped();
		});
		return combo;
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		return true;
	}
}
