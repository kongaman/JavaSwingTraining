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
	
	public Serverinfo(String name, int id) {
		this.name = name;
		this.id = id;
	}
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return name;
	}
}

public class MessagePanel extends JPanel {
	
	private JTree serverTree;
	private DefaultTreeCellRenderer treeCellRenderer;
	
	public MessagePanel() {
		
		treeCellRenderer = new DefaultTreeCellRenderer();
		
		treeCellRenderer.setLeafIcon(Utils.createIcon("/ck/swing1/images/Server16.gif"));
		treeCellRenderer.setOpenIcon(Utils.createIcon("/ck/swing1/images/WebComponent16.gif"));
		treeCellRenderer.setClosedIcon(Utils.createIcon("/ck/swing1/images/WebComponentAdd16.gif"));
		
		serverTree = new JTree(createTree());
		serverTree.setCellRenderer(treeCellRenderer);
	
		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		serverTree.addTreeSelectionListener(e -> {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
			Object userObject = node.getUserObject();
			if(userObject instanceof Serverinfo) {
				int id = ((Serverinfo) userObject).getId();
				System.out.println("UserObject with id: " + id);
			}
			System.out.println(userObject);
		});
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(serverTree), BorderLayout.CENTER);
		
	}
	
	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
		
		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new Serverinfo("New York", 0));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new Serverinfo("Boston", 1));
		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new Serverinfo("Los Angeles",2 ));
		
		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new Serverinfo("London", 3));
		DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new Serverinfo("Endinburgh", 4));
		
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
