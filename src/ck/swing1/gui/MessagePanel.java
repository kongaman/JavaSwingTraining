package ck.swing1.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class MessagePanel extends JPanel {
	
	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer;
	private ServerTreeCellEditor treeCellEditor;
	
	public MessagePanel() {
		
		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();
		
		serverTree = new JTree(createTree());
		serverTree.setCellRenderer(treeCellRenderer);
		serverTree.setCellEditor(treeCellEditor);
		serverTree.setEditable(true);
	
		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		treeCellEditor.addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				ServerInfo info = (ServerInfo) treeCellEditor.getCellEditorValue();
				System.out.println(info + ": " + info.getId() + "; " + info.ischecked());
			}
			
			@Override
			public void editingCanceled(ChangeEvent e) {
			}
		});
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(serverTree), BorderLayout.CENTER);
		
	}
	
	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
		
		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0, true));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1, false));
		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles", 2, true));
		
		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("London", 3, true ));
		DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("Endinburgh", 4, false));
		
		branch1.add(server1);
		branch1.add(server2);
		branch1.add(server3);
		
		branch2.add(server4);
		branch2.add(server5);
		
		top.add(branch1);
		top.add(branch2);
		
		return top;
	}
}
