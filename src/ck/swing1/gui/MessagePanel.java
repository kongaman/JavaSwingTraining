package ck.swing1.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

class Serverinfo {
	private String name;
	private int id;
	private boolean checked;
	
	public Serverinfo(String name, int id, boolean checked) {
		this.name = name;
		this.id = id;
		this.checked = checked;
	}
	public int getId() {
		return id;
	}
	public boolean ischecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	@Override
	public String toString() {
		return name;
	}
}

public class MessagePanel extends JPanel {
	
	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer;
	
	public MessagePanel() {
		
		treeCellRenderer = new ServerTreeCellRenderer();
		
		serverTree = new JTree(createTree());
		serverTree.setCellRenderer(treeCellRenderer);
	
		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		serverTree.addTreeSelectionListener(e -> {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
			Object userObject = node.getUserObject();
			System.out.println(userObject);
		});
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(serverTree), BorderLayout.CENTER);
		
	}
	
	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
		
		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new Serverinfo("New York", 0, true));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new Serverinfo("Boston", 1, false));
		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new Serverinfo("Los Angeles", 2, true));
		
		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new Serverinfo("London", 3, true ));
		DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new Serverinfo("Endinburgh", 4, false));
		
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
