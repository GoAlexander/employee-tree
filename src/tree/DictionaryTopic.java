package tree;

public class DictionaryTopic extends DictionaryElem {
	private String theTopic;

	public DictionaryTopic(String topic) {
		theTopic = topic;
	}

	public String getType() {
		return "Topic";
	}

	public String getInfo() {
		return theTopic;
	}

	public String toString() {
		return theTopic;
	}
}
