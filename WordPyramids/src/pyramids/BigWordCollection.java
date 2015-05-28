package pyramids;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * This class manages the collection of Big Words
 * These Words are supplied through an input file.
 * TODO / FUTURE: input file can be substituted with a SQL string
 * @author srj
 *
 */

public class BigWordCollection 
{    
	// FUTURE: We can serialize it for a later/faster retrieval 
	// this is the single collection we hold in memory
	private ArrayList<BigWord>  bigWordsList = new ArrayList<BigWord>();

	// Another collection - hashtable for a faster retrieval of the BigWord based on the key
	private Hashtable<String, BigWord>  bigWordsIDTable = new Hashtable<String,BigWord>();

	// Another collection - hashtable for a faster retrieval of the BigWord based on the key
	private Hashtable<String, ArrayList<BigWord>>  bigWordsTopicsTable = new Hashtable<String,ArrayList<BigWord>>();

	/**
	 * Default constructor calls the first overloaded constructor with the file path
	 */
	public BigWordCollection()
	{
		this(Config.INPUT_FILE);
	}


	/**
	 * A constructor that loads test data from a file path that is provided
	 * @param a_file_name is the file path to the test data file.
	 */
	public BigWordCollection(String a_file_name)
	{			
		// Create the bigWordsList first
		try 
		{	
			
			processBigWordsInputFile(a_file_name);
		} 
		catch (IOException e) 
		{
			System.out.println(a_file_name);
			System.out.println("There was an error reading or opening the file. Perhaps the file is empty or the path is bad.");
			System.exit(0);
		}

        // sort based on the length and strength
		sortByLengthAndStrength();
		
		// Then process the bigWordsList to create other collections as needed
		makeAllCollections();
	}
	
	//Group 1 Method for displaying the english words contained in the big word collection
	public void printCollection() {
		int temp = 0;
		for (int i = 0; i < bigWordsList.size(); i++) {
			System.out.println(bigWordsList.get(i).getEnglish());
			temp++;
		}
		System.out.println("Printed " + temp + " Words");
	}


	/**
	 * if the array list is provided directly, use this constructor
	 * This also creates all the other collections needed
	 */
	public BigWordCollection(ArrayList<BigWord> an_array_list)
	{
		bigWordsList = an_array_list;
		// sort based on the length and strength
		sortByLengthAndStrength();
		makeAllCollections();
	}


	/**
	 * Reads lines from a text file one by one and sends them to the addBigWord method. Catches a BigWordAdditionException if one is thrown,
	 * and exits the program as per instructions.  It is possible however, to not exit and skip to the next line. This is a one line code change
	 * that involves removing the exit statement. reads UTF-8
	 * @param filename is a string that represents the path of the file to read
	 * @throws IOException is thrown if the file fails to load
	 */
	private void processBigWordsInputFile(String filename) throws IOException 
	{
		String line_read = "";
		
		InputStream is = BigWordCollection.class.getResourceAsStream(filename);
		  InputStreamReader isr = new InputStreamReader(is,"UTF-8");
		  BufferedReader reader = new BufferedReader(isr);

		// igore the first line. we don't need the header line
		line_read = reader.readLine(); 

		//System.out.println(line_read);

		int lineNumber = 0;
		while ((line_read = reader.readLine()) != null) 
		{
			//System.out.println(line_read);

			lineNumber++;
			try 
			{
				addBigWord(line_read);
			} 
			catch (BigWordAdditionException e) 
			{
				System.out.println(e.getMessage() + "Exiting with error code 0 at test data line # " + lineNumber);
				System.exit(0);
			}
		}
		reader.close();
		
		
		
	}

	/**
	 * Adds a BigWord to the collection 
	 * @param a_big_word is added to the collection
	 * */
	public void addBigWord(BigWord a_big_word) 
	{
		bigWordsList.add(a_big_word);
	}

	/** NEW
	 * For getting a Big Word based on index 
	 * @param a_big_word is added to the collection
	 * */
	public BigWord getBigWord(int index) 
	{
		return bigWordsList.get(index);
	}

	/**
	 * Adds a BigWord to the collection 
	 * @param a_line is a single line of text with 16 values separated by 15 commas with values thirteen and fourteen being space delimited lists
	 * @throws BigWordAdditionException is thrown if the file is empty, a line has other than 15 commas, or the file contains duplicate items
	 */
	private void addBigWord(String a_line) throws BigWordAdditionException
	{
		if(a_line.equals(""))
		{
			throw new BigWordAdditionException("Line is empty. Check the empty lines in the file!");
		}

		String[] list = a_line.split(Config.DELIMETER);
		List<String> tokens = Arrays.asList(list);


		// TODO: For now, this code can not handle unstructured data
		// it is required to have delimiters
		if(tokens.size() != Config.MAX_ITEMS_PER_LINE)
		{
			System.out.println("ERROR: Not enough separators | " + a_line);
			return;
		}

		// here are the fields in the input text file
		String token1 = tokens.get(0).trim(); // ID
		String token2 = tokens.get(1).trim(); // topic
		String token3 = tokens.get(2).trim(); // telugu
		String token4 = tokens.get(3).trim(); // english
		String token5 = tokens.get(4).trim(); // clue
		String token6 = tokens.get(5).trim(); // image
		String token7 = tokens.get(6).trim(); // sound

		//Now create the BigWord
		BigWord new_BigWord = new BigWord(token1,token2,token3,token4,token5,
				token6,token7);


		//	System.out.println(new_BigWord);

		// add the BigWord to the BigWord collection
		// Once it is fully created, create the other tables as needed
		// FUTURE: We can optimize it so that all the collections are created in one pass
		bigWordsList.add(new_BigWord);

	}
	/**
	 * Returning the Array List / All BigWords of a BigWordCollection
	 * @return
	 */

	public ArrayList<BigWord> getAllBigWords()
	{
		return bigWordsList;
	}

	/**
	 * This method creates all the needed collections
	 * to be used in different games
	 * 
	 * If any other specific collections are needed, 
	 * those can be built here.
	 */
	private void makeAllCollections()
	{
		makeIDHashtable();
		makeTopicHashtable();
	}

	/**
	 * This method makes a hashtable from the array list
	 * Key = Topic; value = Big Word
	 */
	private void makeIDHashtable()
	{
		// Now create our hashtable; 
		// One time upfront cost for faster retrievals later on
		for (int i = 0; i < bigWordsList.size(); i++)
		{
			BigWord big_word = bigWordsList.get(i);
			String ID_str = big_word.getID();
			bigWordsIDTable.put(ID_str,  big_word);
		}
	}

	/**
	 * This method makes a hashtable from the array list
	 * Key = Topic; value = Big Word Collection
	 */
	private void makeTopicHashtable()
	{
		// Now create our hashtable; 
		// One time upfront cost for faster retrievals later on
		for (int i = 0; i < bigWordsList.size(); i++)
		{
			BigWord big_word = bigWordsList.get(i);
			String topic_str = big_word.getTopic();

			// check whether the key exists
			boolean key_exists = bigWordsTopicsTable.containsKey(topic_str);

			// if it exists, get the value and add the new Big Word to the collection
			if (key_exists)
			{
				ArrayList<BigWord> temp = bigWordsTopicsTable.get(topic_str);
				temp.add(big_word);
				bigWordsTopicsTable.put(topic_str, temp);
			}
			// if the key doesn't exist, then we need to create a new collection 
			// and then add the word to that new collection
			else {
				ArrayList<BigWord> temp = new ArrayList<BigWord>();
				temp.add(big_word);
				bigWordsTopicsTable.put(topic_str, temp);
			}  // end else
		} // end for

		//System.out.println("topic hashtable size " + bigWordsTopicsTable.size());
	} // end method makeTopicHashtable



	/**
	 * Retrieve all Big Words based on key word search
	 */

	public BigWordCollection getBigWordCollectionByKeyWord(String a_key)
	{
		ArrayList<BigWord> mini_collection = new ArrayList<BigWord>(); 
		for (int i = 0; i < bigWordsList.size(); i++)
		{
			BigWord b = bigWordsList.get(i);

			boolean match_found = b.toString().toLowerCase().contains(a_key.toLowerCase());
			if (match_found)
			{
				mini_collection.add(b);
			}

		}
		return new BigWordCollection(mini_collection);
	}


	/**
	 * Retrieve the BigWord from hashtable based on the key
	 */

	public BigWord getBigWordByKey(String an_ID)
	{
		return bigWordsIDTable.get(an_ID);
	}

	/**
	 * get the size of the Big Word Collection
	 */

	public int size() 
	{
		return bigWordsList.size();
	}

	/**
	 * Returns whether the Big Word Collection is empty
	 */

	public boolean isEmpty() 
	{
		return (bigWordsList.size()==0);
	}


	/** For printing the entire collection
	 */

	public String toString( )
	{
		System.out.println("Size of the collection = " + bigWordsList.size());
		return "";
	}






	//==================================================================================================================================
	//********** To be done by Students for SE Assignment 4 (Group Project)


	/**
	 * @author Kevin Nelson
	 * 
	 * getBigWordCollectionByTopic(String some_topic)
	 * Searches BigWordCollection based on a topic
	 * If some_topic is "Any", the entire collection is returned
	 * If some_topic is null or "", then a null collection is returned
	 * @param some_topic, topic being searched
	 * @return returns BigWordCollection
	 */
	public BigWordCollection getBigWordCollectionByTopic(String some_topic)
	{

		BigWordCollection topicCollection;

		// if some_topic is "Any"
		if (some_topic == "Any") {

			topicCollection = new BigWordCollection(bigWordsList);

		} 

		// if some_topic is null or ""

		else if (some_topic == null || some_topic == "") {

			topicCollection = new BigWordCollection(new ArrayList<BigWord>());

		} 

		// else scenario

		else {

			topicCollection = new BigWordCollection(bigWordsTopicsTable.get(some_topic));

		}

		return topicCollection;
	}


	/**
	 * @author Richard Camera	
	 * Returns the Big Word Collection based on the length of the word
	 * This method matches the exact length. All other words are discarded
	 */
	public BigWordCollection getBigWordCollectionByWordLength(int a_length)
	{ 
		ArrayList<BigWord> mini_collection = new ArrayList<BigWord>();
		WordProcessor wp;
		String str;
		
		for (BigWord a_word: bigWordsList){

			// the length of the Telugu word is the strength of that word
			// the length of the Telugu word tells how many code points made that word
			
			str = a_word.getEnglish();
			if(Config.LANGUAGE == "Telugu")
				str = a_word.getTelugu();
			
			wp = new WordProcessor(str);
							
			if(wp.getLength() == a_length){
				mini_collection.add(a_word);
			}
		}
		return new BigWordCollection(mini_collection);
	}

	/**
	 * @author Richard Camera
	 * Returns the Big Word Collection based on the length (min and max) of the word
	 * This method matches all the strings between MIN and MAX (including)
	 */
	public BigWordCollection getBigWordCollectionByWordLength(int min, int max)
	{
		ArrayList<BigWord> mini_collection = new ArrayList<BigWord>();
		WordProcessor wp;
		String str;
		
		if(min != 0 || max != 0) {
			mini_collection = new ArrayList<BigWord>();	

			for (BigWord a_word: bigWordsList){

				// the length of the Telugu word is the strength of that word
				// the length of the Telugu word tells how many code points made that word
				str = a_word.getEnglish();
				if(Config.LANGUAGE == "Telugu")
					str = a_word.getTelugu();
				
				wp = new WordProcessor(str);
				int word_length = wp.getLength();
				
				if(min >= 0 && max > 0) {
					if(word_length >= min && word_length <= max){
						mini_collection.add(a_word);
					}
					continue;
				}
				if(min > 0 && max == 0) {
					if(word_length >= min){
						mini_collection.add(a_word);
					}
					continue;
				}				
			}			
		}
		else
			 mini_collection = new ArrayList<BigWord>(bigWordsList);
		
		return new BigWordCollection(mini_collection);
	}


	/**
	 * @author Surin Assawajaroenkoon
	 * 
	 * Returns the Big Word Collection based on the strength of the Word
	 * For English, strength = length
	 * For Telugu, different algorithm is already provided
	 * */
	public BigWordCollection getBigWordCollectionByWordStrength(int strength) 
	{		
		ArrayList<BigWord> mini_collection = new ArrayList<BigWord>();
		WordProcessor wp;
		String str;
		
		for (BigWord a_word: bigWordsList){

			// the length of the Telugu word is the strength of that word
			// the length of the Telugu word tells how many code points made that word
			
			str = a_word.getEnglish();
			if(Config.LANGUAGE == "Telugu")
				str = a_word.getTelugu();
			
			wp = new WordProcessor(str);
			
			if(wp.getWordStrength() == strength){
				mini_collection.add(a_word);
			}
		}
		return new BigWordCollection(mini_collection);
	}

	/**
	 * @author Surin Assawajaroenkoon
	 * 
	 * Returns the Big Word Collection based on the strength of the Word
	 * It returns all the words between min and max strengths
	 * For English, strength = length
	 * For Telugu, different algorithm is already provided
	 * */
	public BigWordCollection getBigWordCollectionByWordStrength(int min, int max)
	{		
		ArrayList<BigWord> mini_collection = new ArrayList<BigWord>();
		WordProcessor wp;
		String str;
		
		if(min != 0 || max != 0) {
			mini_collection = new ArrayList<BigWord>();	

			for (BigWord a_word: bigWordsList){

				// the length of the Telugu word is the strength of that word
				// the length of the Telugu word tells how many code points made that word
				str = a_word.getEnglish();
				if(Config.LANGUAGE == "Telugu")
					str = a_word.getTelugu();
				
				wp = new WordProcessor(str);
				int word_strength = wp.getWordStrength();
				
				if(min >= 0 && max > 0) {
					if(word_strength >= min && word_strength <= max){
						mini_collection.add(a_word);
					}
					continue;
				}
				if(min > 0 && max == 0) {
					if(word_strength >= min){
						mini_collection.add(a_word);
					}
					continue;
				}				
			}			
		}
		else
			 mini_collection = new ArrayList<BigWord>(bigWordsList);
		
		return new BigWordCollection(mini_collection);
	}

	/**
	 * @author sean.ford
	 * Returns the Big Word Collection based on these search parameters
	 * 		topic
	 * 		length of the word
	 * 		word types
	 * Any of these parameters can be null or empty	
	 */
	public BigWordCollection getBigWordCollectionByCriteria
	(String a_topic, int min_len, int max_len, int min_strength, int max_strength)
	{
		ArrayList<BigWord> result_collection = new ArrayList<BigWord>();
		ArrayList<BigWord> topic_collection = this.getBigWordCollectionByTopic(a_topic).getAllBigWords();
		ArrayList<BigWord> length_collection = this.getBigWordCollectionByWordLength(min_len, max_len).getAllBigWords();
		ArrayList<BigWord> strength_collection = this.getBigWordCollectionByWordStrength(min_strength, max_strength).getAllBigWords();

		if(result_collection.size() > 0 )
			result_collection.retainAll(topic_collection);
		else
			result_collection.addAll(topic_collection);

		if(result_collection.size() > 0 )
			result_collection.retainAll(length_collection);
		else
			result_collection.addAll(length_collection);

		if(result_collection.size() > 0 )
			result_collection.retainAll(strength_collection);
		else
			result_collection.addAll(strength_collection);
		
		return new BigWordCollection(result_collection);
	}

	/**
	 * @author sean.ford
	 * Returns the Big Word Collection based on the level
	 * There are only 3 levels - Level 1, Level 2 and Level 3
	 * For Telugu
	 * level 1: (Topic = Any; Strength = 1; Min/Max Length = 2/10)
	 * level 2: (Topic = Any; Strength = 2; Min/Max Length = 2/10)
	 * level 3: (Topic = Any; Min/Max Strength = 3/10; ; Min/Max Length = 2/10)
	 * 
	 * For English
	 * level 1: (Topic = Any; Min/Max Length = 2/10)
	 * level 2: (Topic = Any; Min/Max Length = 2/10))
	 * level 3: (Topic = Any; Min/Max Strength = 3/10; ; Min/Max Length = 2/10)
	 * * */
	public BigWordCollection getBigWordCollectionByLevel(int a_level) 
	{
		ArrayList<BigWord> result_collection = new ArrayList<BigWord>();;
				
		if(a_level > 0 && a_level < 4) {
			ArrayList<BigWord> strength_collection  = new ArrayList<BigWord>();
			
			// Get words with length between 2 and 10 for either English or Telugu regardless of level
			result_collection = this.getBigWordCollectionByWordLength(2,10).getAllBigWords();
			
			// If Language is Telugu and level is either 1 or 2 then use this
			if(Config.LANGUAGE == "Telugu" && a_level >= 1 && a_level <= 2) 
				strength_collection = this.getBigWordCollectionByWordStrength(a_level).getAllBigWords();
			
			// If level is 3 regardless of language then use this
			if(a_level == 3)
				strength_collection = this.getBigWordCollectionByWordStrength(3, 10).getAllBigWords();
			
			// Merge results
			if(result_collection.size() > 0 )
				result_collection.retainAll(strength_collection);
			else
				result_collection.addAll(strength_collection);
		}
		else
			result_collection = new ArrayList<BigWord>(bigWordsList);
		
		
		return new BigWordCollection(result_collection);
	}
	/**
	 * Checks whether Big Word Collection has any duplicate IDs
	 * @author ISRAEL Yemer
	 */
	public boolean containsDuplicateIDs() 
	{
		ArrayList<String> id_s = new ArrayList<String>();	
		for(BigWord bigWord : bigWordsList){
			if(!id_s.contains(bigWord.getID())){
				id_s.add(bigWord.getID());
				continue;
			}
			return true;
		}
		return false;
	}

	/**
	 * @author sean.ford
	 * Checks whether Big Word Collection has any duplicate Telugu words
	 */
	public boolean containsDuplicateTeluguWords()
	{
		boolean returnVal = false;
		ArrayList<String> telugaWords = new ArrayList<String>();
		for(BigWord bw : bigWordsList) {
			/*
			 * Get each big word/get Telugu string
			 * if the collection doesn't contain the string then add it and continue to next loop iteration
			 * if the telugaWords contains the string do not iterate
			 * set returnVal to true indicating there is a duplicate and break foreach loop
			 */
			if(!telugaWords.contains(bw.getTelugu())) {
				telugaWords.add(bw.getTelugu());
				continue;
			}
			
			returnVal = true;
			break;
		}	
		
		telugaWords = null;
		return returnVal;
	}

	/**
	 * Checks whether Big Word Collection has any duplicate clue words
	 * @author ISRAEL Yemer
	 */
	public boolean containsDuplicateClues() 
	{
		ArrayList<String> clues = new ArrayList<String>();	
		for(BigWord bigWord : bigWordsList){
			if(!clues.contains(bigWord.getClue())){
				clues.add(bigWord.getClue());
				continue;
			}
			return true;
		}
		return false;
	}


	/**
	 * @author Kevin Nelson
	 * 
	 * getBigWordCollectionWithImages()
	 * Searches BigWordCollection for BigWords that have an image file
	 * Returns BigWordCollection of all BigWords with an image file
	 * @return returns BigWordCollection
	 */
	public BigWordCollection getBigWordCollectionWithImages()
	{
		
		// Temporary ArrayList of BigWords
		ArrayList<BigWord> tempArray = new ArrayList<BigWord>();
		
		// Traverse BigWordCollection for BigWord with images
		for (int i = 0; i < this.bigWordsList.size(); i++) {
			
			// Check BigWord for image
			if (this.bigWordsList.get(i).hasImage()) {
				
				// Add BigWord to tempArray
				tempArray.add(this.bigWordsList.get(i));
			
			}
			
		}
		
		// Create temporary BigWordCollection of BigWords with images
		BigWordCollection tempCollection = new BigWordCollection(tempArray);
		
		return tempCollection;
		
	}

	
	/**
	 * @author Kevin Nelson
	 * 
	 * getBigWordCollectionWithSounds()
	 * Searches BigWordCollection for BigWords that have sound files
	 * Returns BigWordCollection of all BigWords with a sound file
	 * @return returns BigWordCollection
	 */
	public BigWordCollection getBigWordCollectionWithSounds()
	{
		
		// Temporary ArrayList of BigWords
		ArrayList<BigWord> tempArray = new ArrayList<BigWord>();
		
		// Traverse BigWordCollection for BigWord with sound
		for (int i = 0; i < this.bigWordsList.size(); i++) {
			
			// Check BigWord for sound
			if (this.bigWordsList.get(i).hasSound()) {
				
				// Add BigWord to tempArray
				tempArray.add(this.bigWordsList.get(i));
			
			}
			
		}
		
		// Create temporary BigWordCollection of BigWords with sound
		BigWordCollection tempCollection = new BigWordCollection(tempArray);
		
		return tempCollection;
		
	}


	public Hashtable<String, ArrayList<BigWord>> getBigWordsTopicsTable() {
		return bigWordsTopicsTable;
	}

	/**
	 * This method sorts the big word collection
	 * first by Length and then by its Strength
	 */
	
	public void sortByLengthAndStrength()
	{
		Collections.sort(bigWordsList);
	}
	
	/**
	 * Removes the duplicates from collection based on the key field
	 * It reads the big words from 1 to N; and if any key is present that will be 
	 * removed from the collection.
	 * 
	 */
	public void removeDuplicates()
	{
		// FUTURE: We can serialize it for a later/faster retrieval 
		// this is the single collection we hold in memory
		//private ArrayList<BigWord>  bigWordsList = new ArrayList<BigWord>();
		Hashtable<String, BigWord> telugu_hashtable = new Hashtable<String, BigWord> ();
		for (int i = 0; i < bigWordsList.size(); i++)
		{
			BigWord a_word = (BigWord) bigWordsList.get(i);
			String telugu_word = a_word.getTelugu();
			WordProcessor x = new WordProcessor(telugu_word);
			x.stripSpaces();
			telugu_word = x.getWord();
	//		System.out.println(telugu_word);
			boolean key_exists = telugu_hashtable.containsKey(telugu_word);
			if (key_exists)  // indicates that telugu word already exists, so delete it
			{
		//		System.out.print(telugu_word);
		//		System.out.println(" will be removed");
				bigWordsList.remove(i);
			}
			else telugu_hashtable.put(telugu_word, a_word);
		}
	}
	//***********************************************************************************************************************


	/*
	 * Test routine for BigWordCollection
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("Opening ..." + Config.INPUT_FILE);
		BigWordCollection x = new BigWordCollection();
		System.out.println(x);
		
	}
}
