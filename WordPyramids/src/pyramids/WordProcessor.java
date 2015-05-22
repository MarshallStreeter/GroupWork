package pyramids;


import java.util.ArrayList;
import java.util.Collections;

/**
 * This class provides several operations in the context of single word.
 * This class is expected to work against multiple languages so the implementation
 * should not rely on the assumption that the string is made of several characters.
 * 
 * Assuming that a string is made up several smaller strings is the safest assumption
 * in dealing with multi-bye languages
 * 
 */

public class WordProcessor {

	//word represents the string we are processing in this class
	private String word;

	// logicalChars are derived from the word
	// word can also be derived from logicalChars
	// these two are dependent on each other
	// if one changes, the other changes
	private ArrayList<String> logicalChars = new ArrayList<>();

	/**
	 * Default constructor
	 */
	public WordProcessor()
	{

	}

	/**
	 * Overloaded constructor that takes the word
	 * @param a_word
	 */

	public WordProcessor(String a_word)
	{
		setWord(a_word);
	}

	/**
	 * Overloaded constructor that takes the logical characters as input
	 * @param some_logical_chars
	 */
	public WordProcessor(ArrayList<String> some_logical_chars)
	{
		setLogicalChars(some_logical_chars);
	}

	/**
	 * set method for the word
	 * @param a_word
	 */
	public void setWord(String a_word)
	{
		word = a_word;
		parseToLogicalChars();
	}

	/**
	 * set method for the logical characters
	 * @param some_logical_chars
	 */
	public void setLogicalChars(ArrayList<String> some_logical_chars)
	{
		String new_word = "";
		logicalChars = some_logical_chars;
		for (String string : logicalChars) {
			new_word += string;
		}
		word = new_word;
	}

	/**
	 * get method for the word
	 * @return
	 */
	public String getWord()
	{
		return word;
	}

	/**
	 * get method for the logical characters
	 * @return
	 */
	public ArrayList<String> getLogicalChars()
	{
		return logicalChars;
	}

	/**
	 * Returns the length of the word
	 * length = number of logical characters
	 * @return
	 */
	public int getLength()
	{
		return logicalChars.size();
	}
	
	/**
	 * Returns the length of the word
	 * length = number of logical characters
	 * @return
	 */
	public int getLogicalLength()
	{
		return getLength();
	}

	/**
	 * Returns the number of code points in the word
	 * @return
	 */
	public int getCodePointLength()
	{
		//return word.codePointCount(0, word.length());
		return word.length();
	}

	/**
	 * This method breaks the input word into logical characters
	 * For Engligh,
	 * 	  convert the string to char array
	 * 	  and convert each char to a string
	 *    and populate logicalChars
	 * For Telugu,
	 * 	  strings are broken at logical places
	 *    and each logical character (string / a collection of unicode characters)
	 *    is placed in the logicalChars
	 */
	public void parseToLogicalChars()
	{
		logicalChars = new ArrayList<>();
		//TODO: Only English and Telugu are supported now.
		//TODO: Other languages are not supported
		//TODO: Parser.parseToLogicalCharacters(word, LANG);
		logicalChars = Parser.parseToLogicalCharacters(word);	
	}

	/**
	 * If the word starts with the logical character, 
	 * this method returns true.
	 * @param start_char
	 * @return
	 */
	public boolean startsWith(String start_char)
	{
		return word.startsWith(start_char);
	}

	/**
	 * If the word ends with the logical character, 
	 * this method returns true.
	 * @param start_char
	 * @return
	 */
	public boolean endsWith(String end_string)
	{
		return word.endsWith(end_string);
	};

	/**
	 * This method checks whether the sub_string or logical character
	 * is contained within the word
	 * @param sub_string
	 * @return
	 */
	public boolean containsString(String sub_string)
	{
		ArrayList<String> input_chars = parseInputString(sub_string);
		return containsLogicalCharSequence(input_chars);
	}

	/**
	 * This method checks whether the sub_string or logical character
	 * is contained within the word
	 * @param sub_string
	 * @return
	 */
	public boolean containsChar(String sub_string)
	{
		for (String string : logicalChars) {
			if (string.equals(sub_string)){
				return true;
			}
		}
		return false;
	}

	/**
	 * This method checks whether the logical characters
	 * are contained within the string/word.
	 * is contained within the word
	 * @param sub_string
	 * @return
	 */
	public boolean containsLogicalChars(ArrayList<String> logical_chars)
	{
		for (String string : logical_chars) {
			if(containsChar(string)){
				continue;
			}
			return false;
		}
		return true;
	}


	/**
	 * This method checks whether *ALL* the logical characters
	 * are contained within the string/word.
	 * is contained within the word
	 * @param sub_string
	 * @return
	 */
	public boolean containsAllLogicalChars(ArrayList<String> logical_chars)
	{
		return containsLogicalChars(logical_chars);
	}

	/**
	 * This method checks whether *ALL* the logical characters
	 * are contained within the string/word.
	 * @param sub_string
	 * @return
	 */
	public boolean containsLogicalCharSequence(ArrayList<String> logical_chars)
	{	
		String temp_word = "";
		for (String string : logical_chars) {
			temp_word += string;
		}
		return word.contains(temp_word);
	};

	/**
	 * This method checks whether a word can be made out of the original word
	 * example:  original word = POST;   a_word = POT
	 * @param a_word
	 * @return
	 */
	public boolean canMakeWord(String a_word)
	{
		//parse the a_word into logical characters
		// and call containsLogicalChars on those logical characters		
		ArrayList<String> input_chars = parseInputString(a_word);
		return containsLogicalChars(input_chars);
	}

	/**
	 * This method checks whether all the words in the collection
	 * can be made out of the original word
	 * example:  original word = POST;   a_word = POT; STOP; TOP; SOP
	 * @param a_word
	 * @return
	 */
	public boolean canMakeAllWords(ArrayList<String> some_words)
	{
		// same as above method 
		// but works on the entire collection
		for (String string : some_words) {
			if(canMakeWord(string)){
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * returns true if the word contains the space
	 * @return
	 */
	public boolean containsSpace()
	{
		for (String string : logicalChars) {
			if(string.matches("\\s+")){
				return true;
			}
		}
		return false;
	};

	/**
	 * returns true if the word contains the space
	 * @return
	 */
	public boolean isPalindrome()
	{
		// find the logical characters of the word: we already have those
		// reverse the array list of those logical characters
		// in a loop, keep comparing 1 to N; 1+1, N-2 and so on
		// 
		int i = 0;
		int j = logicalChars.size() - 1;
		while (i < j) {
			if(logicalChars.get(i++).equals(logicalChars.get(j--))){
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * returns true if the word_2 is an anagram of the word
	 * @return
	 */
	public boolean isAnagram(String word_2)
	{
		// split word_2 into logical characters
		// call containsAllLogicalChars
		ArrayList<String> input_chars = parseInputString(word_2);
		return isAnagram(input_chars);
	}

	/**
	 * returns true if the logical_chars are contained with in the word
	 * @return
	 */

	public boolean isAnagram(ArrayList<String>  logical_chars)
	{
		//// call containsAllLogicalChars
		return (logical_chars.size() == logicalChars.size() && containsLogicalChars(logical_chars));
	}




	// String manipulation methods
	/**
	 * strips of leading and trailing spaces
	 * @return
	 */
	public String trim()
	{
		setWord(word.trim());
		return word;
	}

	/**
	 * strips of all spaces in the word
	 * @return
	 */
	public String stripSpaces()
	{	
		//setWord(word.replaceAll("\\s+", ""));
		// we need to do some special treatment while dealing with Telugu language
		// when we remove the spaces in between, we need to make sure that
		// the logical characters are not combined. 
		// Hence, redirecting the logic to the Parser
		setLogicalChars(Parser.stripSpaces(logicalChars));
		return word;
	}

	/**
	 * strips of all special characters and symbols from the word
	 * @return
	 */
	public String stripAllSymbols()
	{
		String new_word = "";
		for (int i = 0; i < logicalChars.size(); i++) {
			if(logicalChars.get(i).matches("[^\\p{L}\\p{N}]+")){
				logicalChars.remove(i--);
				continue;
			}
			new_word += logicalChars.get(i);
		}
		setWord(new_word);
		return word;
	};

	/**
	 * Reverse the word and returns a new word
	 * @return
	 */
	public String reverse()
	{
		// you already have logicalChars
		// reverse that array list
		// add the logical characters together
		// return that new string
		@SuppressWarnings("unchecked")
		ArrayList<String> reverseList = (ArrayList<String>) logicalChars.clone();
		Collections.reverse(reverseList);
		String reversedWord = "";
		for (String string : reverseList) {
			reversedWord += string;
		}
		return reversedWord;
	}

	/**
	 * Replaces a specific sub-string with a substitute_string
	 * if the sub-string is not found, it does nothing
	 * It does not disturb the original string
	 * It returns a new string
	 * @return
	 */
	public String replace(String sub_string, String substitute_string)
	{
		String new_word = word;	
		return new_word.replace(sub_string, substitute_string);
	}

	/**
	 * Add a logical character at the specified index
	 * It does not disturb the original string
	 * It returns a new string
	 * @return
	 */
	public String addCharacterAt(int index, String a_logical_char)
	{
		@SuppressWarnings("unchecked")
		ArrayList<String> tempList = (ArrayList<String>) logicalChars.clone();
		tempList.add(index, a_logical_char);
		String new_word = "";
		for (String string : tempList) {
			new_word += string;
		}
		return new_word;
	}

	/**
	 * Add a logical character at the end of the word
	 * It does not disturb the original string
	 * It returns a new string
	 * @return
	 */
	public String addCharacterAtEnd(String a_logical_char)
	{
		@SuppressWarnings("unchecked")
		ArrayList<String> tempList = (ArrayList<String>) logicalChars.clone();
		tempList.add(a_logical_char);
		String new_word = "";
		for (String string : tempList) {
			new_word += string;
		}
		return new_word;
	}

	/**
	 * Compares the given word with the original word
	 * If there is a match on any logical character, it returns true
	 * @return
	 */
	public boolean isIntersecting(String word_2)
	{
			if(getIntersectingRank(word_2) > 0){
				return true;
			}
		return false;
	}

	/**
	 * Compares the given word with the original word
	 * And returns the count of matches on the logical characters
	 * 
	 * Similar to getUniqueIntersectingRank.
	 * This method takes in a String/Word while getUniqueIntersectingRank 
	 * takes in an array list / collection of strings.
	 * @return
	 */
	public int getIntersectingRank(String word_2)
	{
		int count = 0;
		ArrayList<String> inputWord = parseInputString(word_2);
		for (String string : inputWord) {
			if(containsChar(string)){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Takes an arrayList of strings and checks how many times the original word matches up to the list uniquely.
	 * i.e. if the original word has two of one letter but the list has only one instance of that letter, the resulting
	 * rank would be 1.
	 * @param list
	 * @return
	 */
	
	public int getUniqueIntersectingRank(ArrayList<String> list)
	{
		ArrayList<String> temp = new ArrayList<String>(list);
		int count = 0;
		for(String s: logicalChars)
		{
			if(!s.trim().equals(""))
				if(temp.contains(s.toLowerCase()))
				{
					temp.remove(s.toLowerCase());
					count++;
				}
			
				//make sure the first case failed before checking this
				else if(!temp.contains(s.toLowerCase()) && temp.contains(s.toUpperCase()))
				{
					temp.remove(s.toUpperCase());
					count++;
				}
		}
		
		return count;
	}
	
	/**
	 * Takes an ArrayList of Strings and returns a list of unique intersections.
	 * @param list
	 * @return
	 */
	public ArrayList<String> getUniqueIntersectingLogicalChars(ArrayList<String> list)
	{
		ArrayList<String> temp = new ArrayList<String>(list);
		ArrayList<String> intersecting = new ArrayList<String>();

		for(String s: logicalChars)
		{
			if(!s.trim().equals(""))
				if(temp.contains(s.toLowerCase()))
				{
					temp.remove(s.toLowerCase());
					intersecting.add(s);
				}
				
				//make sure the first case failed before checking this
				else if(!temp.contains(s.toLowerCase()) && temp.contains(s.toUpperCase()))
				{
					temp.remove(s.toUpperCase());
					intersecting.add(s);
				}
		}
		
		return intersecting;
	}
	


	/**
	 * This method gets a logical character at the specified index
	 * @param index
	 * @return
	 */
	public String logicalCharAt(int index)
	{
		return logicalChars.get(index);
	}

	/**
	 * This method gets a unicode code point at the specified index
	 * @param index
	 * @return
	 */ 
	public int	codePointAt(int index)
	{
		return word.codePointAt(index);
	}

	// Returns the position at which the first logical character is appearing in the string

	/**
	 * This method returns the index at which the logical character is appearing
	 * It returns the first appearance of the logical character
	 * @param index
	 * @return
	 */ 
	public int indexOf(String logical_char)
	{
		for (int i = 0; i < logicalChars.size(); i++) {
			if(logicalChars.get(i).equals(logical_char)){
				return i;
			}
		}
		return -1;
	}

	/**
	 * This method compares two strings lexicographically.
	 * It is simplay a wrapper on java compareTo
	 * @param word_2
	 * @return
	 */
	public int compareTo(String word_2)
	{
		return word.compareTo(word_2);
	}


	/**
	 * This method compares two strings lexicographically, ignoring case differences.
	 * It is simplay a wrapper on java compareTo
	 * @param word_2
	 * @return
	 */
	public int	compareToIgnoreCase(String word_2)
	{
		return word.compareToIgnoreCase(word_2);
	}

	/**
	 * This method takes a String and returns a jumbled collection
	 * of logical characters.
	 * @param a_string
	 * @return
	 */
	public ArrayList<String> getScrambledChars()
	{
		ArrayList<String> temp_list = (ArrayList<String>) logicalChars.clone();
		Collections.shuffle(temp_list);
		return temp_list;
	}
	
	/**
	 * This method takes a String and returns a scrambled string
	 * (by randomizing the position of the logical characters)
	 * @param a_string
	 * @return
	 */
	public String getScrambledString()
	{
		WordProcessor wp = new WordProcessor(getScrambledChars());
		return wp.getWord();
	}

	/**
	 * This method splits the word into a 2-dimensional matrix
	 * based on the number of columns
	 * @param no_of_columns
	 * @return
	 */
	public String[][]  splitWord(int no_of_columns)
	{
		int numOfRows = Math.floorDiv(logicalChars.size(), no_of_columns)+1;
		String[][] wordArraySplit = new String[numOfRows][no_of_columns];
		int k = 0;
		for (int i = 0; i < wordArraySplit.length; i++) {
			for (int j = 0;j < wordArraySplit[0].length && k < logicalChars.size();) {
				wordArraySplit[i][j] = logicalChars.get(k);
				k = (i * wordArraySplit[0].length) + j++;
			}
		}
		return wordArraySplit;
	}

	/**
	 * Returns the string representation of WordProcessor
	 * Basially, prints the word and logicalChars
	*/
	@Override
	public String toString() {
		return word + ", " + logicalChars;
	}

	/**
	 * compares two strings; wrapper on the java method
	 */
	public boolean equals(String word_2)
	{
		return word.equals(word_2);
	}

	/**
	 * compares two strings after reversing the original word
	 */
	public boolean reverseEquals(String word_2)
	{
		String reversedWord = reverse();
		return reversedWord.equals(word_2);
	}
	
	/**
	 * This method returns an array list of logical characters for the given input string
	 * It simply relies on setWord() method 
	 * @return
	 */
	public ArrayList<String> parseInputString(String a_word){
		String wordPlaceholder =  word;
		setWord(a_word);
		ArrayList<String> input_chars = logicalChars;
		setWord(wordPlaceholder);
		return input_chars;
	}
	
	/**
	 * This method returns the word strength
	 * Strength = The maximum number of code points in a logical character
	 * @return
	 */
	
	public int getWordStrength()
	{	
		// get the length of the word in terms of logical characters
		int length = this.getLength();  
		
		// For Telugu, proceed further
		// counter variable to keep track of the strength
		// strength = number of code points in each logical characters
		int strength = 1; 
	
		
		for (int i=0; i < length; i++)
		{
			// get the logical character
			String logical_char = this.logicalCharAt(i);
			
			// len_2 gives the code point length of the logical character
			int no_code_points = logical_char.length();  
			
			// increase the strength if needed
			if (no_code_points > strength)  { strength = no_code_points;}	
		}
		
		return strength;  
	}
	
	/**
	 * This method returns the word weight
	 * Weight = The total number of code points in a logical character
	 * @return
	 */
	public int getWordWeight()
	{	
		// get the length of the word in terms of logical characters
		int length = this.getLength();  
		
		// For English, weight = length
		if (this.logicalCharAt(0).matches("[A-Za-z0-9]"))
		{
			return length;
		}
		
		// counter variable to keep track of the strength
		// weight  = total number of code points in each logical characters
		int weight = 0; 
		
		for (int i=0; i < length; i++)
		{
			// get the logical character
			String logical_char = this.logicalCharAt(i);
			
			// len_2 gives the code point length of the logical character
			weight = weight + logical_char.length();  
			
		}
	//	System.out.println( this.getWord() + " = " + weight);
		return weight;  
	}
	
}
