package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import tree.DictionaryEntry;
import filter.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class SwingGUI5 extends JFrame implements ActionListener, TreeSelectionListener {
	private SwingGUI5Model theAppModel;

	private JTree theTree;
	private JButton insertButton;
	private JButton deleteButton;
	private JButton findButton;
	private JButton editButton;

	private JButton changeLookFeelButton;

	// private JTextField theTextField;

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
	private JTextField textField_4;
	private JLabel image_label = new JLabel();
	private JButton btnCleanFields;

	private boolean DEBUG = true; // for debug
	private String img_default = "./images/default.jpg";

	protected Component buildGUI() {

		Container contentPane = this.getContentPane();
		// contentPane.setLayout (new FlowLayout());

		// ---------------------------------------------------
		// Tree section

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

		lblEditorModeexample = new JLabel("Editor:");
		lblEditorModeexample.setFont(new Font("Dialog", Font.BOLD, 17));
		scrollPane.setColumnHeaderView(lblEditorModeexample);

		// ---------------------------------------------------
		// Form section

		// Divide right area on two part:
		// first for image
		// second for textFields and other
		JPanel form_panel = new JPanel();
		scrollPane.setViewportView(form_panel);
		form_panel.setLayout(new BoxLayout(form_panel, BoxLayout.PAGE_AXIS));

		// Add an image
		// Images should be 128x128
		String img_destination = img_default; // TODO Need in
												// refactoring (get
												// path from
												// person`s class)

		image_label.setIcon(new ImageIcon(img_destination));
		image_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Create window for choosing an image
				JFileChooser img_chooser = new JFileChooser();
				img_chooser.setCurrentDirectory(new java.io.File("."));
				img_chooser.setDialogTitle("Select image");
				img_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				img_chooser.setAcceptAllFileFilterUsed(false);

				if (img_chooser.showOpenDialog(image_label) == JFileChooser.APPROVE_OPTION) {
					if (DEBUG)
						System.out.println("getSelectedFile(): " + img_chooser.getSelectedFile());

					image_label.setIcon(new ImageIcon(img_chooser.getSelectedFile().toString())); // updating
																									// an
																									// image
				} else {
					System.out.println("No Selection!"); // TODO Exception?
				}
			}
		});
		image_label.setHorizontalAlignment(SwingConstants.CENTER);
		form_panel.add(image_label);

		panel_1 = new JPanel();
		// scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(5, 2));

		lblSurname = new JLabel("Surname:");
		panel_1.add(lblSurname);

		textField = new JTextField();
		lblSurname.setLabelFor(textField);
		panel_1.add(textField);
		textField.setColumns(10);

		// ((AbstractDocument) textField.getDocument()).setDocumentFilter(new
		// StringFilter());//test

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

		// Note: Dob = birthday
		lblNewLabel_2 = new JLabel("Birthday");
		panel_1.add(lblNewLabel_2);

		textField_3 = new JTextField();
		panel_1.add(textField_3);
		textField_3.setColumns(10);

		lblNewLabel_3 = new JLabel("Number");
		panel_1.add(lblNewLabel_3);

		textField_4 = new JTextField();
		panel_1.add(textField_4);
		textField_4.setColumns(10);

		// PlainDocument doc = (PlainDocument) textField_4.getDocument(); //test
		// doc.setDocumentFilter(new IntFilter()); //test

		form_panel.add(panel_1); // add panel of textFields to Boxlayout

		contentPane.add(panel, "Center");

		// ---------------------------------------------------
		// Menu section

		JPanel panel2 = new JPanel();

		insertButton = new JButton("Insert Person");
		insertButton.addActionListener(this);

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		deleteButton.setEnabled(false);

		findButton = new JButton("Find");
		findButton.addActionListener(this);

		editButton = new JButton("Save changes");
		editButton.addActionListener(this);
		editButton.setEnabled(false);

		btnCleanFields = new JButton("Clean form");
		btnCleanFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				image_label.setIcon(new ImageIcon(img_default)); // set default
																	// image
			}
		});

		changeLookFeelButton = new JButton("change Look & Feel");
		changeLookFeelButton.addActionListener(this);

		panel2.add(insertButton);
		panel2.add(deleteButton);
		panel2.add(findButton);
		panel2.add(editButton);
		panel2.add(btnCleanFields);
		panel2.add(changeLookFeelButton);

		// add buttons above:
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 1));
		panel3.add(panel2);

		contentPane.add(panel3, "South");

		return null;
	}

	public SwingGUI5(SwingGUI5Model appModel) {
		theAppModel = appModel;

		setTitle("Tree  example with model");
		setSize(800, 360);

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

		String[] textVal = new String[6];
		textVal[0] = textField.getText();
		textVal[1] = textField_1.getText();
		textVal[2] = textField_2.getText();
		textVal[3] = textField_3.getText();
		textVal[4] = textField_4.getText();
		textVal[5] = image_label.getIcon().toString();

		// Checking fields:
		if (Filter.letter_filter(textVal[0]) == true && Filter.letter_filter(textVal[1]) == true
				&& Filter.letter_filter(textVal[2]) == true && Filter.numeric_filter(textVal[3]) == true
				&& Filter.numeric_filter(textVal[4]) == true) {
			operations(event, selectedNode, textVal);
		} else {
			System.out.println("Error!"); // TODO Exceptions???
			JOptionPane.showMessageDialog(null, "Invalid data in fields!");
		}

		if (textVal.equals(""))
			textVal[0] = "new";

		// Look&Feel button action
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
		}
	}

	// here are all actions for buttons
	private void operations(ActionEvent event, DefaultMutableTreeNode selectedNode, String[] textVal) {
		if (event.getSource().equals(insertButton)) {

			TreePath path = theAppModel.insertPerson(textVal);
			if (path != null) {
				theTree.scrollPathToVisible(path);
				theTree.setSelectionPath(path);
			}
		}
		if (event.getSource().equals(findButton)) {

			TreePath path = theAppModel.findPerson(textVal);
			if (path != null) {
				theTree.scrollPathToVisible(path);
				theTree.setSelectionPath(path);
			}
		}

		if (selectedNode == null)
			return;

		if (event.getSource().equals(deleteButton)) {
			// confirm deleting
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) { // if yes

				if (selectedNode.getParent() != null) {
					theAppModel.deletePerson(selectedNode);
					editButton.setEnabled(false);
					deleteButton.setEnabled(false);
				}
				return;

			} else // if no - do nothing
				return;
		}

		if (event.getSource().equals(editButton)) {
			// confirm editing
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit current profile?", "Edit?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) { // if yes

				TreePath path = theAppModel.editPerson(selectedNode, textVal);
				if (path != null) {
					theTree.scrollPathToVisible(path);
					theTree.setSelectionPath(path);
				}
				return;

			} else // if no - do nothing
				return;
		}
	}

	private void display(DefaultMutableTreeNode selectedNode) {
		if (selectedNode != null) {
			DictionaryEntry elem = (DictionaryEntry) selectedNode.getUserObject();
			textField.setText(elem.getValue());
			textField_1.setText(elem.getName());
			textField_2.setText(elem.getMiddlename());
			textField_3.setText(elem.getDob());
			textField_4.setText(elem.getAddress());
			image_label.setIcon(new ImageIcon(elem.getPhoto()));
		}

	}

	public void valueChanged(TreeSelectionEvent event) {
		TreePath path = theTree.getSelectionPath();
		if (path == null) {
			return;
		}
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
		display(selectedNode);
		editButton.setEnabled(true);
		deleteButton.setEnabled(true);
		/*
		 * if (selectedNode != null) {
		 * theTextArea.setText(selectedNode.toString()); }
		 */

	}
}
