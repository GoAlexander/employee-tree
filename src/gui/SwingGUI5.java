package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.toedter.calendar.JDateChooser;
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

	private JButton insertButton, deleteButton, findButton, editButton, changeLookFeelButton;

	private int nextSearchIndex = 0;
	private String[] nextSearchPerson;
	private String selectedfile;

	private UIManager.LookAndFeelInfo installedLF[];
	private int currentLook;

	private JLabel lblEditorModeexample, lblSurname, lblName, lblMiddlename, lblBirthday, lblNumber;
	private JPanel panel_1;
	private JTextField textFieldSurname, textFieldName, textFieldMiddlename, textFieldNumber;
	private JLabel image_label = new JLabel();
	private JButton btnCleanFields, btnSaveTree, btnLoadTree, btnFindNext;
	private JDateChooser dateChooser;
	private String fileName;
	private boolean DEBUG = true; // for debug
	private String img_default = "./images/default.jpg";

	private void setNext(int i) {
		nextSearchIndex = i;
	}

	private int getNext() {
		return nextSearchIndex;
	}

	private Component buildGUI() {

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
		// String img_destination = img_default;

		image_label.setIcon(new ImageIcon(img_default));
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
					selectedfile = img_chooser.getSelectedFile().toString();
					// Update an image and set 128x128 size
					image_label.setIcon(new ImageIcon(new ImageIcon(img_chooser.getSelectedFile().toString()).getImage()
							.getScaledInstance(128, 128, Image.SCALE_AREA_AVERAGING)));

				} else {
					System.out.println("No Selection!");
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

		textFieldSurname = new JTextField();
		lblSurname.setLabelFor(textFieldSurname);
		panel_1.add(textFieldSurname);
		textFieldSurname.setColumns(10);
		textFieldSurname.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				textFieldSurname.setForeground(Color.BLACK);

				char c = e.getKeyChar();
				if (c == KeyEvent.VK_SPACE)
					e.consume();
			}
		});

		lblName = new JLabel("Name:");
		panel_1.add(lblName);

		textFieldName = new JTextField();
		panel_1.add(textFieldName);
		textFieldName.setColumns(10);
		textFieldName.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				textFieldName.setForeground(Color.BLACK);
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_SPACE)
					e.consume();
			}
		});

		lblMiddlename = new JLabel("Middle Name");
		panel_1.add(lblMiddlename);

		textFieldMiddlename = new JTextField();
		panel_1.add(textFieldMiddlename);
		textFieldMiddlename.setColumns(10);
		textFieldMiddlename.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				textFieldMiddlename.setForeground(Color.BLACK);
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_SPACE)
					e.consume();
			}
		});

		lblBirthday = new JLabel("Birthday");
		panel_1.add(lblBirthday);

		// JDate Chooser:
		/*
		 * Big thanks for the calendar: http://toedter.com/jcalendar/
		 */
		dateChooser = new JDateChooser();
		dateChooser.setBounds(20, 20, 200, 20);
		dateChooser.setDateFormatString("dd-MM-yyyy");
		panel_1.add(dateChooser);

		lblNumber = new JLabel("Number");
		panel_1.add(lblNumber);

		textFieldNumber = new JTextField();
		panel_1.add(textFieldNumber);
		textFieldNumber.setColumns(10);
		textFieldNumber.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				textFieldNumber.setForeground(Color.BLACK);
				char c = e.getKeyChar();
				if (c == KeyEvent.VK_SPACE)
					e.consume();
			}
		});
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

		btnFindNext = new JButton("Find Next");
		btnFindNext.addActionListener(this);
		btnFindNext.setEnabled(false);

		editButton = new JButton("Save changes");
		editButton.addActionListener(this);
		editButton.setEnabled(false);

		btnCleanFields = new JButton("Clean form");
		btnCleanFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldSurname.setText("");
				textFieldName.setText("");
				textFieldMiddlename.setText("");
				dateChooser.setCalendar(null);
				textFieldNumber.setText("");
				image_label.setIcon(new ImageIcon(img_default)); // set default
																	// image
				selectedfile = img_default;
			}
		});

		btnSaveTree = new JButton("Save Tree");
		btnSaveTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileName = null;
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Select destination");
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					chooser.setAcceptAllFileFilterUsed(false);

					if (chooser.showSaveDialog(btnSaveTree) == JFileChooser.APPROVE_OPTION) {
						if (DEBUG)
							System.out.println("getSelectedFile(): " + chooser.getSelectedFile());
						fileName = chooser.getSelectedFile().toString();
					}

					theAppModel.write(fileName);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File error!");
				}
			}
		});

		btnLoadTree = new JButton("Load Tree");
		btnLoadTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileName = null;
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Select destination");
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					chooser.setAcceptAllFileFilterUsed(false);

					if (chooser.showOpenDialog(btnLoadTree) == JFileChooser.APPROVE_OPTION) {
						if (DEBUG)
							System.out.println("getSelectedFile(): " + chooser.getSelectedFile());
						fileName = chooser.getSelectedFile().toString();
						// delete the selected person
						TreePath path = theTree.getSelectionPath();
						if (path != null) {
							theAppModel.deletePerson((DefaultMutableTreeNode) path.getLastPathComponent());
						}
						theAppModel.clean();
						btnCleanFields.doClick();
					}
					theAppModel.read(fileName);
					btnFindNext.setEnabled(false);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File error!");
				}
			}
		});

		changeLookFeelButton = new JButton("change Look&Feel");
		changeLookFeelButton.addActionListener(this);

		panel2.add(insertButton);
		panel2.add(deleteButton);
		panel2.add(findButton);
		panel2.add(btnFindNext);
		panel2.add(editButton);
		panel2.add(btnCleanFields);
		// panel2.add(changeLookFeelButton);

		JPanel panel2Bottom = new JPanel();

		panel2Bottom.add(btnSaveTree);
		panel2Bottom.add(btnLoadTree);
		panel2Bottom.add(changeLookFeelButton);

		// add buttons above:
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(2, 1));
		panel3.add(panel2);
		panel3.add(panel2Bottom);

		contentPane.add(panel3, "South");

		return null;
	}

	public SwingGUI5(SwingGUI5Model appModel) {
		theAppModel = appModel;
		setTitle("Tree  example with model");
		setSize(650, 370);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Save changes before you exit?", "Exit",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (reply == JOptionPane.YES_OPTION) { // if yes - save Tree and
														// exit
					btnSaveTree.doClick();
					System.exit(0);
				}
				if (reply == JOptionPane.CANCEL_OPTION) { // if cancel - return

				}
				if (reply == JOptionPane.NO_OPTION) {
					System.exit(0); // if no - exit
				}
			}
		});

		this.buildGUI();

		installedLF = UIManager.getInstalledLookAndFeels();
		currentLook = 0;
		try {
			UIManager.setLookAndFeel(installedLF[currentLook].getClassName());
		} catch (Exception ex) {
			System.out.println("Exception 1");
		}
	}

	public void actionPerformed(ActionEvent event) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) theTree.getLastSelectedPathComponent();
		String[] textVal = new String[6];
		textVal[0] = textFieldSurname.getText();
		textVal[1] = textFieldName.getText();
		textVal[2] = textFieldMiddlename.getText();
		if (dateChooser.getDate() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			textVal[3] = sdf.format(dateChooser.getDate());
		} else
			textVal[3] = "";
		textVal[4] = textFieldNumber.getText();
		if (selectedfile != null)
			textVal[5] = selectedfile;
		else
			textVal[5] = img_default;

		// Checking fields:
		boolean flag = true;
		if (Filter.letter_filter(textVal[0]) != true) {
			textFieldSurname.setForeground(Color.RED);
			flag = false;
		}
		if (Filter.letter_filter(textVal[1]) != true) {
			textFieldName.setForeground(Color.RED);
			flag = false;
		}
		if (Filter.letter_filter(textVal[2]) != true) {
			textFieldMiddlename.setForeground(Color.RED);
			flag = false;
		}
		if (Filter.numeric_filter(textVal[4]) != true) {
			textFieldNumber.setForeground(Color.RED);
			flag = false;
		}
		if (flag == true)
			operations(event, selectedNode, textVal);
		else
			JOptionPane.showMessageDialog(null, "Invalid data in fields!");

		// Look&Feel button action
		if (event.getSource().equals(changeLookFeelButton))

		{
			currentLook++;
			if (currentLook > installedLF.length - 1) {
				currentLook = 0;
			}

			System.out.println("New Current Look&Feel:" + currentLook);
			System.out.println("New Current Look&Feel Class:" + installedLF[currentLook].getClassName());

			try {
				UIManager.setLookAndFeel(installedLF[currentLook].getClassName());
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception ex) {
				System.out.println("exception");
			}
		}
	}

	// here are all actions for buttons
	private void operations(ActionEvent event, DefaultMutableTreeNode selectedNode, String[] textVal) {
		if (event.getSource().equals(insertButton)) {
			if (textVal[0].equals("") || textVal[1].equals("") || textVal[3].equals("") || textVal[4].equals("")) {
				JOptionPane.showMessageDialog(null, "Not all fields filled!");
				return;
			}
			TreePath path = theAppModel.insertPerson(textVal);
			if (path != null) {
				theTree.scrollPathToVisible(path);
				theTree.setSelectionPath(path);
				btnFindNext.setEnabled(false);
			}
		}
		if (event.getSource().equals(findButton)) {
			setNext(0);
			nextSearchPerson = null;
			TreePath path = theAppModel.findPerson(textVal, getNext());
			if (path != null) {
				theTree.scrollPathToVisible(path);
				theTree.removeSelectionPath(path);
				theTree.setSelectionPath(path);
				btnFindNext.setEnabled(true);
				nextSearchPerson = textVal;
			} else {
				btnFindNext.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Nothing found!");
				return;
			}
			if (theAppModel.findPerson(textVal, getNext() + 1) == null)
				btnFindNext.setEnabled(false);
			setNext(1);
		}

		if (event.getSource().equals(btnFindNext)) {
			TreePath path = theAppModel.findPerson(nextSearchPerson, getNext());
			if (path != null) {
				theTree.scrollPathToVisible(path);
				theTree.setSelectionPath(path);
				setNext(getNext() + 1);
			} else
				setNext(0);
			path = theAppModel.findPerson(nextSearchPerson, getNext());
			if (path == null) {
				setNext(0);
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
					btnFindNext.setEnabled(false);
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
				if (textVal[0].equals("") || textVal[1].equals("") || textVal[3].equals("") || textVal[4].equals("")) {
					JOptionPane.showMessageDialog(null, "Not all fields filled!");
					return;
				}
				TreePath path = theAppModel.editPerson(selectedNode, textVal);
				if (path != null) {
					theTree.scrollPathToVisible(path);
					theTree.setSelectionPath(path);
					btnFindNext.setEnabled(false);
				}
				return;

			} else // if no - do nothing
				return;
		}
	}

	private void display(DefaultMutableTreeNode selectedNode) {
		if (selectedNode != null) {
			DictionaryEntry elem = (DictionaryEntry) selectedNode.getUserObject();
			textFieldSurname.setText(elem.getValue());
			textFieldName.setText(elem.getName());
			textFieldMiddlename.setText(elem.getMiddlename());

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date date = formatter.parse(elem.getBirthday());
				dateChooser.setDate(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			textFieldNumber.setText(elem.getAddress());
			image_label.setIcon(new ImageIcon(
					new ImageIcon(elem.getPhoto()).getImage().getScaledInstance(128, 128, Image.SCALE_AREA_AVERAGING)));
			selectedfile = elem.getPhoto();

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
	}
}
