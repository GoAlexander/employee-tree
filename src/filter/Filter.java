package filter;

public class Filter {
	public static boolean letter_filter(String text) {
		boolean flag = true;
		for (int n = text.length(); n > 0; n--) {//an inserted string may be more than a single character i.e a copy and paste of 'aaa123d', also we iterate from the back as super.XX implementation will put last insterted string first and so on thus 'aa123d' would be 'daa', but because we iterate from the back its 'aad' like we want
            char c = text.charAt(n - 1);//get a single character of the string
            
            if (!Character.isAlphabetic(c)) {
            	if (c!='-') {
            		flag = false;
            		break;
            	}
            }
        }
		
		return flag;
	}
	
	
	public static boolean numeric_filter(String text) {
		boolean flag = true;
		for (int n = text.length(); n > 0; n--) {//an inserted string may be more than a single character i.e a copy and paste of 'aaa123d', also we iterate from the back as super.XX implementation will put last insterted string first and so on thus 'aa123d' would be 'daa', but because we iterate from the back its 'aad' like we want
            char c = text.charAt(n - 1);//get a single character of the string
            
            if (!Character.isDigit(c)) {
            	if (c!='.') {
            		flag = false;
            		break;
            	}
            }
        }
		
		return flag;
	}
}
