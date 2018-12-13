package dictreenary;



import java.util.ArrayList;


public class Dictreenary implements DictreenaryInterface {

	// Fields
	// -----------------------------------------------------------
	TTNode root;
	TTNode current;
	// Constructor
	// -----------------------------------------------------------
	Dictreenary() {
	}

	// Methods
	// -----------------------------------------------------------

	public boolean isEmpty() {
		return root == null;
	}

	public void addWord(String toAdd) {
		stringAdd(normalizeWord(toAdd));
	}
	
	public boolean hasWord(String query) {
		return wordCheck(normalizeWord(query));
	}

	public String spellCheck(String query) {
		
		if (hasWord(query)==true) {
			return query;
		}
		for (int i = 0; i <query.length()-1; i++) {
			
			if(hasWord(swap(query,i))) {
				return swap(query,i);
			}
			
		}
		return null;
	}

	public ArrayList<String> getSortedWords() {
		ArrayList<String> theList = new ArrayList<String>();
		orderlyPrint(root,"",theList);
		return theList;
		
	}

	// Helper Methods
	// -----------------------------------------------------------

	private String swap (String spellCheck, int index) {
		
		char[] ch = spellCheck.toCharArray(); 
        char temp = ch[index]; 
        ch[index] = ch[index+1]; 
        ch[index+1] = temp; 
        return new String(ch); 
		
	}
	
	private String normalizeWord(String s) {
		// Edge case handling: empty Strings illegal
		if (s == null || s.equals("")) {
			throw new IllegalArgumentException();
		}
		return s.trim().toLowerCase();
	}

	
	private int compareChars(char c1, char c2) {
		return Character.toLowerCase(c1) - Character.toLowerCase(c2);
	}


	// [!] Add your own helper methods here!
	
	private void orderlyPrint(TTNode present, String wordGotten, ArrayList<String> list) {
		
		//Base Case
		if (present == null) {
			return;
		}
		// Recursive Case
		orderlyPrint(present.left,wordGotten,list);
		if (present.wordEnd) {
			list.add(wordGotten+present.letter);
		}
		orderlyPrint(present.mid,wordGotten+present.letter,list);
		orderlyPrint(present.right,wordGotten,list);
		
	}
	
	private boolean wordCheck(String toCheck) {
		int i = 0;
		current = root;
		
		while (i<toCheck.length()) {
			if (current == null) {
				return false;
			}
			
			int result = compareChars(toCheck.charAt(i),current.letter);
			if (result < 0) {
				current = current.left;
			} else if (result > 0) {
				current = current.right;
			} else {
				if (toCheck.length()-1==i) {
					return current.wordEnd;
				}
				current = current.mid;
				i++;
			}	
		}
		return false;
		
	}
	
	
	private void restDown(String buildDown, TTNode current) {
		for (int i=0; i < buildDown.length(); i++) {
			char gotten = buildDown.charAt(i);
			boolean ending = i+1 == buildDown.length();
			TTNode presently = new TTNode(gotten, ending);
			current.mid = presently;
			current = current.mid;
		}
	}
	
	private void stringAdd(String suffix) {

		if (root == null) {
			if (suffix.length() == 1) {
				root = new TTNode(suffix.charAt(0), true);
			}
			else {
				root = new TTNode(suffix.charAt(0) , false);
				restDown(suffix.substring(1), root);
			}
			return;
		}
		TTNode current = root;

		for (int i = 0 ; i <suffix.length(); i++) {
			char c = suffix.charAt(i);
			int compared = compareChars(c , current.letter);
			boolean ending = i+1 == suffix.length();
			if (compared < 0) { 
				if (current.left == null) {
					TTNode name = new TTNode (c,ending);
					current.left = name;
					restDown(suffix.substring(i+1), current.left);
					return;
				}
				current = current.left;
				i--;
			}
			else if (compared > 0) { 
				if (current.right == null) {
					TTNode name = new TTNode (c,ending);
					current.right = name;
					restDown(suffix.substring(i+1), current.right);
					return;
			}
				current = current.right;
				i--;
		}
			else { 
				if (current.mid == null) {
					restDown(suffix.substring(i+1) , current);
					return;
				}
					current = current.mid;
				
			
			}
			
		}
		 
	}
	
	
 
	// TTNode Internal Storage
	// -----------------------------------------------------------

	/*
	 * Internal storage of Dictreenary words as represented using a Ternary Tree
	 * with TTNodes
	 */
	private class TTNode {

		boolean wordEnd;
		char letter;
		TTNode left, mid, right;

		TTNode(char c, boolean w) {
			letter = c;
			wordEnd = w;
		}
		
	}

}
