package tree;

public class DictionaryEntry extends DictionaryElem {
	private String theName;
	private String theSurname;
	private String theMiddlename;
	private String theBirthday;
	private String theAddress;
	private String thePhoto;
	private String full;
	private String[] info = new String[5];

	/*
	 * public DictionaryEntry(String surname, String name, String middlename,
	 * String dob, String address, String photo) { theSurname = surname; theName
	 * = name; theMiddlename = middlename; theDob = dob; theAddress = address;
	 * thePhoto = photo; full = theSurname + theName + theMiddlename + theDob +
	 * theAddress; info[0] = surname; info[1] = name; info[2] = middlename;
	 * info[3] = dob; info[4] = address; }
	 */

	public DictionaryEntry(String fullInfo) {
		// String[] completeStr = full.split(" ");
		this(fullInfo.split(" "));
	}

	public DictionaryEntry(String[] completeStr) {
		theSurname = completeStr[0];
		theName = completeStr[1];
		theMiddlename = completeStr[2];
		theBirthday = completeStr[3];
		theAddress = completeStr[4];
		thePhoto = completeStr[5];
		full = theSurname + theName + theMiddlename + theBirthday + theAddress;
		info[0] = completeStr[0];
		info[1] = completeStr[1];
		info[2] = completeStr[2];
		info[3] = completeStr[3];
		info[4] = completeStr[4];
	}

	public String getType() {
		return "Entry";
	}

	public String getValue() {
		return theSurname;
	}

	public String getName() {
		return theName;
	}

	public String getMiddlename() {
		return theMiddlename;
	}

	public String getBirthday() {
		return theBirthday;
	}

	public String getAddress() {
		return theAddress;
	}

	public String getPhoto() {
		return thePhoto;
	}

	@Override
	public String toString() {
		return theSurname + " " + theName + " " + theMiddlename + " " + theBirthday + " " + theAddress;
	}

	public String getFullInfo() {
		return theSurname + " " + theName + " " + theMiddlename + " " + theBirthday + " " + theAddress + " " + thePhoto;
	}

	public String getInfo() {
		return full;
	}

	public boolean checkInfo(DictionaryEntry elem) {

		String[] data = new String[5];
		data[0] = elem.getValue();
		data[1] = elem.getName();
		data[2] = elem.getMiddlename();
		data[3] = elem.getBirthday();
		data[4] = elem.getAddress();

		boolean result = false;
		for (int i = 0; i < data.length; i++) {
			if (!this.info[i].equals("")) {
				if (this.info[i].equals(data[i]))
					result = true;
				else
					return false;
			}
		}
		return result;
	}

}
