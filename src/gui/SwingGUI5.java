package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
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

import tree.DictionaryElem;
import tree.DictionaryEntry;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;

public class SwingGUI5 extends JFrame implements ActionListener, TreeSelectionListener {
	private SwingGUI5Model theAppModel;

	private JTree theTree;
	private JButton insertButton;
	private JButton deleteButton;
	private JButton findButton;
	private JButton editButton;

	private JButton changeLookFeelButton;

	//private JTextField theTextField;

	private UIManager.LookAndFeelInfo installedLF[];

	private int current;
	private JLabel lblEditorModeexample;
	private JPanel panel_1;
	private JLabel lblSurname;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel_1;
	private JTextField textField_2;
	private JLabel lblNewLabel_2;
	private JTextField textField_3;
	private JLabel lblNewLabel_3;
	private JButton button;
	private JTextField textField_4;
	private JSeparator separator;
	private JButton btnSave;

	protected Component buildGUI() {

		Container contentPane = this.getContentPane();
		// contentPane.setLayout (new FlowLayout());

		theTree = new JTree(theAppModel.buildDefaultTreeStructure());
		// theTree.setEditable(true);
		theTree.addTreeSelectionListener(this);
		int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
		theTree.getSelectionModel().setSelectionMode(mode);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(new JScrollPane(theTree));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setEnabled(false);
		panel.add(scrollPane);

		lblEditorModeexample = new JLabel("Editor mode (example)");
		scrollPane.setColumnHeaderView(lblEditorModeexample);
		
		//Divide right area on two part:
		//first for image
		//second for textFields and other
		JPanel image_panel = new JPanel();
		scrollPane.setViewportView(image_panel);
		image_panel.setLayout(new BoxLayout(image_panel, BoxLayout.PAGE_AXIS));
		
		//add an image
		ImageIcon img_url = new ImageIcon("/home/alexander/Desktop/image.png"); //here url of image
		
		JLabel image_label = new JLabel();
		image_label.setIcon(img_url); //set image in Jlabel
		image_panel.add(image_label);
		
		
		panel_1 = new JPanel();
		//scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(6, 2));

		lblSurname = new JLabel("Surname:");
		panel_1.add(lblSurname);

		textField = new JTextField();
		lblSurname.setLabelFor(textField);
		panel_1.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Name:");
		panel_1.add(lblNewLabel);

		textField_1 = new JTextField();
		panel_1.add(textField_1);
		textField_1.setColumns(10);

		lblNewLabel_1 = new JLabel("Middle Name");
		panel_1.add(lblNewLabel_1);

		textField_2 = new JTextField();
		panel_1.add(textField_2);
		textField_2.setColumns(10);

		lblNewLabel_2 = new JLabel("DoB");
		panel_1.add(lblNewLabel_2);

		textField_3 = new JTextField();
		panel_1.add(textField_3);
		textField_3.setColumns(10);

		lblNewLabel_3 = new JLabel("Address");
		panel_1.add(lblNewLabel_3);

		textField_4 = new JTextField();
		panel_1.add(textField_4);
		textField_4.setColumns(10);

		separator = new JSeparator();
		panel_1.add(separator);

		btnSave = new JButton("Save");
		panel_1.add(btnSave);

		image_panel.add(panel_1); //test
		// ---

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

		//add buttons above:
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 1));
		panel3.add(panel2);

		contentPane.add(panel3, "South");

		return null;
	}

	public SwingGUI5(SwingGUI5Model appModel) {
		theAppModel = appModel;

		setTitle("Tree  example with model");
		setSize(800, 350);

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

		String[] textVal = new String[5];
		textVal[0] = textField.getText();
		textVal[1] = textField_1.getText();
		textVal[2] = textField_2.getText();
		textVal[3] = textField_3.getText();
		textVal[4] = textField_4.getText();

		if (textVal.equals(""))
			textVal[0] = "new";

		if (event.getSource().equals(changeLookFeelButton))

		{
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
		} else

		{
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
			// HERE WILL BE AN EDIT BUTTON
			if (event.getSource().equals(editButton)) {

			}

		}

	}

	public void valueChanged(TreeSelectionEvent event) {
		TreePath path = theTree.getSelectionPath();
		if (path == null)
			return;

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

		if (selectedNode != null) {
			DictionaryEntry elem = (DictionaryEntry) selectedNode.getUserObject();
			textField.setText(elem.getValue());
			textField_1.setText(elem.getName());
			textField_2.setText(elem.getMiddlename());
			textField_3.setText(elem.getDob());
			textField_4.setText(elem.getAddress());
		}
		/*
		 * if (selectedNode != null) {
		 * theTextArea.setText(selectedNode.toString()); }
		 */

	}
}
