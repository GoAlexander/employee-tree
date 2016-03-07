package tree;

public class DictionaryEntry extends DictionaryElem {
	protected String theName;
	protected String theSurname;
	protected String theMiddlename;
	protected String theDob;
	protected String theAddress;
	protected String full;

	public DictionaryEntry(String surname, String name, String middlename, String dob, String address) {
		theSurname = surname;
		theName = name;
		theMiddlename = middlename;
		theDob = dob;
		theAddress = address;
		full = theSurname + theName + theMiddlename + theDob + theAddress;
	}

	public DictionaryEntry(String[] completeStr) {
		/*
		 * int delim_index = completeStr.indexOf(" "); int length =
		 * completeStr.length();
		 * 
		 * if (delim_index <= 0) { delim_index = length; }
		 */
		theSurname = completeStr[0];
		theName = completeStr[1];
		theMiddlename = completeStr[2];
		theDob = completeStr[3];
		theAddress = completeStr[4];
		full = theSurname + theName + theMiddlename + theDob + theAddress;
		/*
		 * theName = completeStr.substring(0, completeStr.indexOf(" "));
		 * theAddress = completeStr.substring(completeStr.indexOf(" ") + 1, );
		 */
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
	
	public String getDob() {
		return theDob;
	}
	
	public String getAddress() {
		return theAddress;
	}

	public String toString() {
		return theSurname + " " + theName + " " + theMiddlename + " " + theDob + " " + theAddress;
	}

	public String getInfo() {
		return full;
	}
}
