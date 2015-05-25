package pyramids;

import java.util.*;

public class GUIFacade {

	private ArrayList<ArrayList<String>> gameWords;
	private static GUIFacade instance;

	private GUIFacade() {

	}

	public static GUIFacade instance() {
		if (instance == null) {
			instance = new GUIFacade();
		}
		return instance;
	}

	/**
	 * Sort words available for game by length
	 *
	 * @param list
	 * @return
	 */
	public Hashtable<Integer, ArrayList<ArrayList<String>>> sortTopicList(
			BigWordCollection list, boolean english) {

		Hashtable<Integer, ArrayList<ArrayList<String>>> availableWordBoard = new Hashtable<Integer, ArrayList<ArrayList<String>>>();
		for (int i = 0; i < list.size(); i++) {
			BigWord big_word = list.getBigWord(i);
			ArrayList<String> logicalWord = parseLogicalChars(big_word, english);
			int key = logicalWord.size();

			// check whether the key exists
			boolean key_exists = availableWordBoard.containsKey(key);

			// if it exists, get the value and add the new Big Word to the
			// collection
			if (key_exists) {
				ArrayList<ArrayList<String>> temp = availableWordBoard.get(key);
				temp.add(logicalWord);
				availableWordBoard.put(key, temp);
			}
			// if the key doesn't exist, then we need to create a new collection
			// and then add the word to that new collection
			else {
				ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
				temp.add(logicalWord);
				availableWordBoard.put(key, temp);
			} // end else
		} // end for

		return availableWordBoard;
	}

	private ArrayList<ArrayList<String>> unrelatedGameWords(
			Hashtable<Integer, ArrayList<ArrayList<String>>> table, int min,
			int max, boolean scramble) {
		ArrayList<ArrayList<String>> gameWords = new ArrayList<ArrayList<String>>();
		for (int i = min; i <= max; i++) {
			if (table.containsKey(i)) {
				Random rand = new Random();
				int size = table.get(i).size();

				int listSelection = rand.nextInt((table.get(i).size()));
				if (listSelection == size) {
					gameWords.add(table.get(i).get(listSelection - 1));
				} else {
					gameWords.add(table.get(i).get(listSelection));
				}

			} else {
				ArrayList<String> temp = new ArrayList<String>();
				for (int z = 0; z < i; z++) {
					temp.add(" ");
				}
				gameWords.add(temp);
			}
		}
		if(scramble){
		    ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		    WordProcessor wp = new WordProcessor();
		    for( ArrayList<String> word : gameWords){
		        wp.setLogicalChars(word);
		        temp.add(wp.getScrambledChars());
		    }
		    gameWords = temp;
		}
		return gameWords;
	}

	public void generateWords(boolean unrelated, String topic, int userMin,
			int userMax, boolean english, boolean scramble) {
		Hashtable<Integer, ArrayList<ArrayList<String>>> topicList = sortTopicList(
				Config.entireCollection.getBigWordCollectionByTopic(topic),
				english);

		if (unrelated) {
			gameWords = unrelatedGameWords(topicList, userMin, userMax, scramble);
		} else {
			//need to build related game words method
		}
	}

	public ArrayList<ArrayList<String>> getGameWords() {
		return gameWords;
	}

	/**
	 * Break word into logical chars
	 *
	 * @param word
	 * @return ArrayList of each element in its own index
	 */
	private ArrayList<String> parseLogicalChars(BigWord word, boolean english) {
		WordProcessor process;
		if (english) {
			process = new WordProcessor(word.getEnglish());
		} else {
			process = new WordProcessor(word.getTelugu());
		}
		// store this arraylist based on sized in topicWord
		// ArrayList<ArrayList<String>>, will need to be created***
		return Parser.stripSpaces(process.getLogicalChars());
	}
}
