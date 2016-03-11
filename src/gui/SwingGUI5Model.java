package gui;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import tree.DictionaryAnchor;
import tree.DictionaryElem;
import tree.DictionaryEntry;
import tree.DictionaryTopic;

public class SwingGUI5Model {

	private ArrayList<DefaultMutableTreeNode> matches = new ArrayList<DefaultMutableTreeNode>();
	private DefaultTreeModel theModel;
	private static String alphabet = new String("ABCDEFGIJKLMNOPRSTUVWXYZ");
	private DefaultMutableTreeNode theRoot;

	public SwingGUI5Model() {

	}

	private TreePath search(DictionaryEntry new_entry, DictionaryAnchor anchor, int i) {
		matches.clear(); // очищаю ранее найденные результаты
		findInfo(new_entry, anchor);
		return selectMatch(i);
	}

	private TreePath selectMatch(
			int index) { /*
							 * если список найденных узлов не пуст и идекс
							 * соответствует размерности списка выделяю путь к
							 * этому узлу
							 */
		if (!matches.isEmpty() && index < matches.size() && index >= 0)
			return new TreePath((matches.get(index)).getPath());
		else
			return null;
	}

	public TreePath findPerson(String[] dataarr, int i) {

		// TreePath path;
		DictionaryAnchor anchor = new DictionaryAnchor();

		anchor.topic = null;
		anchor.entry = null;

		DictionaryEntry new_entry = new DictionaryEntry(dataarr);

		return this.search(new_entry, anchor, i);
	}

	public void deletePerson(DefaultMutableTreeNode selectedNode) {

		if (selectedNode != theRoot) {
			DictionaryElem elem = (DictionaryElem) selectedNode.getUserObject();
			if ("Entry".equals(elem.getType())) {
				theModel.removeNodeFromParent(selectedNode);
			}
		}
	}

	public TreePath editPerson(DefaultMutableTreeNode selectedNode, String[] arr) {
		TreePath path = null;
		DictionaryAnchor anchor = new DictionaryAnchor();

		anchor.topic = null;
		anchor.entry = null;

		if (selectedNode != theRoot) {
			DictionaryElem elem = (DictionaryElem) selectedNode.getUserObject();
			if ("Entry".equals(elem.getType())) {
				deletePerson(selectedNode);
				path = insertPerson(arr);
			}
		}
		return path;
	}

	public TreePath insertPerson(String[] dataarr) {
		TreePath path;
		DictionaryAnchor anchor = new DictionaryAnchor();

		anchor.topic = null;
		anchor.entry = null;

		DictionaryEntry new_entry = new DictionaryEntry(dataarr);

		if (this.findEntry(new_entry, anchor)) {
			// found such a person
			TreeNode[] nodes = theModel.getPathToRoot(anchor.entry);
			path = new TreePath(nodes);
		} else {
			// not found - insert the person
			if (anchor.topic != null) {
				// the proper topic has been found
				DefaultMutableTreeNode new_node = new DefaultMutableTreeNode(new_entry);
				new_node.setAllowsChildren(false);
				theModel.insertNodeInto(new_node, anchor.topic, anchor.topic.getChildCount());
				TreeNode[] nodes = theModel.getPathToRoot(new_node);
				path = new TreePath(nodes);
			} else {
				path = null;
			}
		}

		return path;
	}

	protected void findInfo(DictionaryEntry new_entry, DictionaryAnchor anchor) {

		if (anchor == null)
			return;
		anchor.topic = null;

		@SuppressWarnings("rawtypes")
		Enumeration en1 = theRoot.children();

		while (en1.hasMoreElements()) {
			DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) en1.nextElement();

			DictionaryElem elem1 = (DictionaryElem) node1.getUserObject();
			if ("Topic".equals(elem1.getType()))
				anchor.topic = node1;

			if (anchor.topic != null) {
				@SuppressWarnings("rawtypes")
				Enumeration en2 = anchor.topic.children();
				anchor.entry = null;

				while (en2.hasMoreElements()) {
					DefaultMutableTreeNode node2 = (DefaultMutableTreeNode) en2.nextElement();

					DictionaryEntry elem2 = (DictionaryEntry) node2.getUserObject();
					if ("Entry".equals(elem2.getType())) {
						if (new_entry.checkInfo(elem2)) {
							anchor.entry = node2;
							matches.add(node2);
						}
					}
				}
			}

		}
	}

	protected boolean findEntry(DictionaryEntry new_entry, DictionaryAnchor anchor) {

		// String[] info = new_entry.getInfo();
		String firstLetter = new_entry.getValue().substring(0, 1);
		boolean result = false;

		if (anchor == null)
			return false;
		anchor.topic = null;

		@SuppressWarnings("rawtypes")
		Enumeration en = theRoot.children();

		while (en.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();

			DictionaryElem elem = (DictionaryElem) node.getUserObject();
			if ("Topic".equals(elem.getType())) {
				if (firstLetter.equalsIgnoreCase(elem.getValue())) {
					anchor.topic = node;
					break;
				}
			} else {
				break;
			}
		}

		if (anchor.topic != null) {
			en = anchor.topic.children();
			anchor.entry = null;

			while (en.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();

				DictionaryEntry elem = (DictionaryEntry) node.getUserObject();
				if ("Entry".equals(elem.getType())) {
					if (new_entry.getInfo().equalsIgnoreCase(elem.getInfo())) {
						anchor.entry = node;
						result = true;
						break;
					}
				}
			}

		}
		return result;
	}

	public void removeNodeFromParent(MutableTreeNode selectedNode) {
		theModel.removeNodeFromParent(selectedNode);
	}

	public TreeNode[] getPathToRoot(MutableTreeNode newNode) {

		return theModel.getPathToRoot(newNode);
	}

	public DefaultTreeModel buildDefaultTreeStructure() {
		theRoot = new DefaultMutableTreeNode("Dictionary");
		theRoot.setAllowsChildren(true);

		for (int i = 0; i < alphabet.length(); i++) {
			DictionaryElem nodeElem = new DictionaryTopic(alphabet.substring(i, i + 1));
			DefaultMutableTreeNode topic = new DefaultMutableTreeNode(nodeElem);
			topic.setAllowsChildren(true);

			theRoot.add(topic);
		}
		theModel = new DefaultTreeModel(theRoot);
		theModel.setAsksAllowsChildren(true);

		return theModel;
	}

}
