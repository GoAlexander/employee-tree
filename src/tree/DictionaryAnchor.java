package tree;

import javax.swing.tree.*;

class DictionaryAnchor {
	public DefaultMutableTreeNode topic;
	public DefaultMutableTreeNode entry;
}

abstract class DictionaryElem {
	public abstract String getType();

	public abstract String getValue();

	public abstract String toString();
}

class DictionaryTopic extends DictionaryElem {
	private String theTopic;

	public DictionaryTopic(String topic) {
		theTopic = topic;
	}

	public String getType() {
		return "Topic";
	}

	public String getValue() {
		return theTopic;
	}

	public String toString() {
		return theTopic;
	}

}

class DictionaryEntry extends DictionaryElem {
	protected String theName;

	protected String theAddress;

	public DictionaryEntry(String name, String address) {
		theName = name;
		theAddress = address;
	}

	public DictionaryEntry(String completeStr) {
		int delim_index = completeStr.indexOf(" ");
		int length = completeStr.length();

		if (delim_index <= 0) {
			delim_index = length;
		}

		theName = completeStr.substring(0, delim_index);
		theAddress = completeStr.substring(delim_index);
	}

	public String getType() {
		return "Entry";
	}

	public String getValue() {
		return theName;
	}

	public String toString() {
		return theName + " " + theAddress;
	}

}