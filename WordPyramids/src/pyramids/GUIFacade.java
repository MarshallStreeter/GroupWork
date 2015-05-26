package pyramids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;

public class GUIFacade {

	private ArrayList<ArrayList<String>> gameWords;
	private static GUIFacade instance;

	private GUIFacade() {

	}

	/**
	 * Only want one instance acting with the GUI
	 * 
	 * @return
	 */
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
	private Hashtable<Integer, ArrayList<ArrayList<String>>> sortTopicList(
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
			int max) {
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
		return gameWords;
	}

	private ArrayList<ArrayList<String>> relatedGameWords(
			Hashtable<Integer, ArrayList<ArrayList<String>>> table, int min,
			int max) {
		ArrayList<ArrayList<String>> gameWords = new ArrayList<ArrayList<String>>();
		LinkedList<ArrayList<String>> seedWord = null;
		for (int i = min; i <= max; i++) {

			if (i == min||seedWord == null) {
				if (table.containsKey(i)) {
					Random rand = new Random();
					int size = table.get(i).size();

					int listSelection = rand.nextInt((table.get(i).size()));
					if (listSelection == size) {
						gameWords.add(table.get(i).get(listSelection - 1));
						seedWord = new LinkedList<ArrayList<String>>();
						seedWord.add(table.get(i).get(listSelection - 1));
					} else {
						gameWords.add(table.get(i).get(listSelection));
						seedWord = new LinkedList<ArrayList<String>>();
						seedWord.add(table.get(i).get(listSelection));
					}

				} else {
					ArrayList<String> temp = new ArrayList<String>();
					for (int z = 0; z < i; z++) {
						temp.add(" ");
					}
					gameWords.add(temp);
				}
			} else {
				if (table.containsKey(i)) {
					ArrayList<String> seed = seedWord.getFirst();
					boolean wordRelated = false;
					for(ArrayList<String> word : table.get(i)){
						wordRelated = wordIsRelated(seed,word);
						if(wordRelated){
							gameWords.add(word);
							seedWord.removeFirst();
							seedWord.add(word);
							break;
						}
					}
					if(!wordRelated){
						ArrayList<String> temp = new ArrayList<String>();
						for (int z = 0; z < i; z++) {
							temp.add(" ");
						}
						gameWords.add(temp);
					}
				

				} else {
					ArrayList<String> temp = new ArrayList<String>();
					for (int z = 0; z < i; z++) {
						temp.add(" ");
					}
					gameWords.add(temp);
				}
			}
		}
		return gameWords;
	}
	private boolean wordIsRelated(ArrayList<String> seed, ArrayList<String> word){
		
		for(int i = 0; i < seed.size();i++){
			boolean allowContinue = false;
			for(int z = 0; z <word.size();z++){
				//check if the letter exists in the next string
				//if so allow continue
				if(seed.get(i).equals(word.get(z))){
					allowContinue = true;
					break;
				}
			}
			//if letter was not present in the next word, no point to continue
			if(!allowContinue)
				return false;
		}
		return true;
	}
	public void generateWords(boolean unrelated, String topic, int userMin,
			int userMax, boolean english,boolean scramble) {
		Hashtable<Integer, ArrayList<ArrayList<String>>> topicList = sortTopicList(
				Config.entireCollection.getBigWordCollectionByTopic(topic),
				english);

		if (unrelated) {
			gameWords = unrelatedGameWords(topicList, userMin, userMax);
		} else {
			gameWords = relatedGameWords(topicList,userMin, userMax);
		}
		if(scramble){
			for(int i = 0; i < gameWords.size();i++){
				Collections.shuffle(gameWords.get(i));
			}
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
		return Parser.stripSpaces(process.getLogicalChars());
	}
}
