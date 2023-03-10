package ck.swing1.gui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import ck.swing1.model.EmploymentCategory;

public class EmploymentCategoryRenderer implements TableCellRenderer {
	
	private JComboBox<EmploymentCategory> combo;
	
	public EmploymentCategoryRenderer() {
		combo = new JComboBox<>(EmploymentCategory.values());
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		combo.setSelectedItem(value);
		return combo;
	}
}
