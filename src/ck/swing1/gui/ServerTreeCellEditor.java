package ck.swing1.gui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {
	
	private ServerTreeCellRenderer renderer;
	private JCheckBox checkBox;
	private ServerInfo serverInfo;
	
	public ServerTreeCellEditor() {
		renderer = new ServerTreeCellRenderer();
	}

	@Override
	public boolean isCellEditable(EventObject event) {
		if(!(event instanceof MouseEvent)) return false; //is it a mousevent?
		MouseEvent mouseEvent = (MouseEvent) event;
		JTree tree = (JTree) event.getSource();
		TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY()); //where did the click happen?
		if(path == null) return false;
		Object lastComponent = path.getLastPathComponent();
		if(lastComponent == null) return false;
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) lastComponent; //there's only DefaultmutableTreenodes in the Tree
		return treeNode.isLeaf(); //is it a leaf? leaf is editable (everything else is not)
	}
	
	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		Component component = renderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);
		if(leaf) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value; //tree is made only of those
			serverInfo = (ServerInfo) treeNode.getUserObject(); //global because needed in getCellEditorValue
			checkBox = (JCheckBox) component;
			ItemListener itemListener = new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					fireEditingStopped(); //tells the tree to stop using the editor and use the renderer again
					checkBox.removeItemListener(this);
				}
			};
			checkBox.addItemListener(null);
		}
		return component;
	}
	
	@Override
	public Object getCellEditorValue() {
		serverInfo.setChecked(checkBox.isSelected());
		return serverInfo;
	}
	/**
	 * Tree has to be set to setEditable(true) in MessagePanel!
	 * Click in tree ->
	 * 1. isCellEditable -> if FALSE nothing else will happen but if TRUE ->
	 * 2. getgetTreeCellEditorComponent is called to get the editor for that node.
	 *    The Click on the checkbox (to tick/untick it) will cause the itemListener to fireEditingStopped().
	 *    Which will tell the tree to stop using the editor and use the renderer again.
	 *    And before it does that it will call getCellEditorValue
	 * 3. before getCellEditorValue will return the serverInfo (which we temporarily stored in serverInfo-Field)
	 *    serverInfo is set to checked/unchecked depending on what happened to the checkbox during the editing.
	 */
}
