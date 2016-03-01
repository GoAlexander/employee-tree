package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class SwingGUI5 extends JFrame implements ActionListener, TreeSelectionListener {
	private SwingGUI5Model theAppModel;

	private JTree theTree;
	private JTextArea theTextArea;
	private JButton insertButton;
	private JButton deleteButton;
	private JButton findButton;
	private JButton editButton;

	private JButton changeLookFeelButton;

	private JTextField theTextField;

	private UIManager.LookAndFeelInfo installedLF[];

	private int current;

	protected Component buildGUI() {

		Container contentPane = this.getContentPane();
		// contentPane.setLayout (new FlowLayout());

		theTree = new JTree(theAppModel.buildDefaultTreeStructure());
		// theTree.setEditable(true);
		theTree.addTreeSelectionListener(this);
		int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
		theTree.getSelectionModel().setSelectionMode(mode);

		theTextArea = new JTextArea();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(new JScrollPane(theTree));
		panel.add(new JScrollPane(theTextArea));

		contentPane.add(panel, "Center");

		JPanel panel2 = new JPanel();

		insertButton = new JButton("Insert Person");
		insertButton.addActionListener(this);

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);

		findButton = new JButton("Find");
		findButton.addActionListener(this);

		editButton = new JButton("Edit Current Node");
		editButton.addActionListener(this);
		editButton.setEnabled(false);

		changeLookFeelButton = new JButton("change Look & Feel");
		changeLookFeelButton.addActionListener(this);

		panel2.add(insertButton);
		panel2.add(deleteButton);
		panel2.add(findButton);
		panel2.add(editButton);
		panel2.add(changeLookFeelButton);

		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(2, 1));

		panel3.add(panel2);

		theTextField = new JTextField();
		panel3.add(theTextField);

		contentPane.add(panel3, "South");

		return null;
	}

	public SwingGUI5(SwingGUI5Model appModel) {
		theAppModel = appModel;

		setTitle("Tree  example with model");
		setSize(800, 200);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.buildGUI();

		installedLF = UIManager.getInstalledLookAndFeels();
		current = 0;
		try {
			UIManager.setLookAndFeel(installedLF[current].getClassName());
		} catch (Exception ex) {
			System.out.println("Exception 1");
		}
	}

	public void actionPerformed(ActionEvent event) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) theTree.getLastSelectedPathComponent();

		String textVal = theTextField.getText();

		if (textVal == null) {
			textVal = "new person";
		} else if (textVal.equals("")) {
			textVal = "new";
		}

		if (event.getSource().equals(changeLookFeelButton)) {
			current++;
			if (current > installedLF.length - 1) {
				current = 0;
			}

			System.out.println("New Current Look&Feel:" + current);
			System.out.println("New Current Look&Feel Class:" + installedLF[current].getClassName());

			try {
				UIManager.setLookAndFeel(installedLF[current].getClassName());
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception ex) {
				System.out.println("exception");
			}
		} else {
			if (event.getSource().equals(insertButton)) {

				TreePath path = theAppModel.insertPerson(textVal);
				if (path != null) {
					theTree.scrollPathToVisible(path);
				}
			}
			if (event.getSource().equals(findButton)) {

				TreePath path = theAppModel.findPerson(textVal);
				if (path != null) {
					theTree.scrollPathToVisible(path);
				}
			}

			if (selectedNode == null)
				return;

			if (event.getSource().equals(deleteButton)) {
				if (selectedNode.getParent() != null)
					theAppModel.deletePerson(selectedNode);
				return;
			}

		}
	}

	public void valueChanged(TreeSelectionEvent event) {
		TreePath path = theTree.getSelectionPath();
		if (path == null)
			return;

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

		if (selectedNode != null) {
			theTextArea.setText(selectedNode.toString());
		}

	}
}

