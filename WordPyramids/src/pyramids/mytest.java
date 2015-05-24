package pyramids;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;

public class mytest {

	int userMin = 1;
	int userMax = 6;
	int formWidth = 1065;
	int formHeight = 843;
	int count = 0;

	private JFrame frame;
	private JButton btnMybutton;
	private JButton generateHtmlBtn;
	private JComboBox cboTopic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mytest window = new mytest();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mytest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Creates the entire Collection
		Config.entireCollection = new BigWordCollection();
		Config.gameCollection = Config.entireCollection;
		// The following is an example of how to reduce collection using Siva's
		// Methods (sort by difficulty level)
		// Config.gameCollection =
		// Config.entireCollection.getBigWordCollectionByLevel(1);
		System.out.println("Collection size before removing duplicates: "
				+ Config.gameCollection.size());
		Config.gameCollection.removeDuplicates();
		System.out.println("Collection size after removing duplicates: "
				+ Config.gameCollection.size());
		// I created printCollection() for testing purposes, feel free to edit
		// it as necessary
		//Config.gameCollection.printCollection();

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(70, 130, 180));
		frame.setResizable(false);
		frame.setBounds(100, 100, formWidth, formHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Vector int_items = new Vector();
		int x;
		for (x = 1; x <= 10; x++)
			int_items.add(x);

		Vector string_items = new Vector();
		string_items.add("Animals");
		string_items.add("Places");

		final DefaultComboBoxModel model1 = new DefaultComboBoxModel(int_items);
		JComboBox cboMin = new JComboBox(model1);
		cboMin.setBounds(118, 52, 147, 20);
		frame.getContentPane().add(cboMin);
		cboMin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				userMin = (int) model1.getSelectedItem();
			}
		});

		final DefaultComboBoxModel model2 = new DefaultComboBoxModel(int_items);
		JComboBox cboMax = new JComboBox(model2);
		cboMax.setBounds(336, 52, 147, 20);
		cboMax.setSelectedIndex(5);
		frame.getContentPane().add(cboMax);
		cboMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				userMax = (int) model2.getSelectedItem();
			}
		});

		JLabel lblMin = new JLabel("Min");
		lblMin.setBounds(69, 55, 46, 14);
		frame.getContentPane().add(lblMin);

		JLabel lblMax = new JLabel("Max");
		lblMax.setBounds(285, 55, 46, 14);
		frame.getContentPane().add(lblMax);

		cboTopic = new JComboBox(populateTopicBox());

		cboTopic.setBounds(546, 52, 147, 20);
		frame.getContentPane().add(cboTopic);
		cboTopic.setSelectedIndex(0);
		JLabel lblTopic = new JLabel("Topic");
		lblTopic.setBounds(500, 55, 46, 14);
		frame.getContentPane().add(lblTopic);

		JCheckBox ckbxRandom = new JCheckBox("Scramble");
		ckbxRandom.setBackground(new Color(70, 130, 180));
		ckbxRandom.setBounds(746, 159, 97, 23);
		frame.getContentPane().add(ckbxRandom);

		JButton btnPlay = new JButton("Play Game");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Clean game board
				// loop through all dynamically created buttons and destroy
				if (count > 0)
					clean_board();

				play_game(userMin, userMax);
				count++;

			}
		});
		btnPlay.setBounds(746, 37, 200, 50);
		frame.getContentPane().add(btnPlay);

		generateHtmlBtn = new JButton("Generate HTML");
		generateHtmlBtn.addActionListener(new GenerateHTMLButtonHandler());
		generateHtmlBtn.setBounds(746, 100, 200, 50);
		frame.getContentPane().add(generateHtmlBtn);

	}

	private void clean_board() {

		Component[] controls = frame.getContentPane().getComponents();

		for (int e = 0; e < controls.length; e++) {
			if (controls[e] instanceof JButton
					&& controls[e].getName() == "Letter") {

				frame.getContentPane().remove(controls[e]);
				frame.getContentPane().invalidate();
				frame.repaint();

			}

		}

	}

	private Object[] populateTopicBox() {
		ArrayList<String> topicStrings = new ArrayList<String>();
		Hashtable<String, ArrayList<BigWord>> selects = Config.entireCollection
				.getBigWordsTopicsTable();
		for (Entry<String, ArrayList<BigWord>> entry : selects.entrySet()) {
			String key = entry.getKey();
			topicStrings.add(key);

			// do what you have to do here
			// In your case, an other loop.
		}
		return topicStrings.toArray();
	}

	private void play_game(int userMin, int userMax) {

		int i, x, y, h, p;
		int length = 88;
		int height = 88;
		x = formWidth / 2 - ((userMax / 2) * length);
		y = 580;
		int level = 44;
		p = 0;

		// TODO: Setting this to English for now, needed for collectionByWordLength
		Config.LANGUAGE = "English";

		//This should give the words to be used based on topic
		//will now need to be broken down into the proper form to be selected for game
		//the list of lists method mentioned in class
		BigWordCollection wordList = Config.entireCollection
				.getBigWordCollectionByTopic(cboTopic.getSelectedItem()
						.toString());

		// Basic test for getting ArrayList of ArrayLists of logical characters
		ArrayList<ArrayList<String>> example = getLogicalList(wordList);

        for( ArrayList<String> word: example){

            for( String letter: word){
                System.out.print(" " + letter);
            }
            System.out.println("");

        }

		//this sequence shows how to pull the word, then parse to the usable arraylist
		BigWord test = wordList.getBigWord(0);
		//this can be separated by a conditional based on language selected, big word class provides option to get other langauge
		WordProcessor process = new WordProcessor(test.getEnglish());
		//store this arraylist based on sized in topicWord ArrayList<ArrayList<String>>, will need to be created***
		ArrayList<String> testWord = Parser.stripSpaces(process.getLogicalChars());
		//proof of each letter being separate
		for(String element: testWord){
			System.out.println(element);
		}
		//from here will need to then choose the word (its ArrayList<String>> from topic list) from first index, on up to the max
		//will need to be n-1 of the selection, then add that to the gameList, similar in construction to the topic list but only with playable words
		//that will then be appropriate for the html output
		//**If no word is found of a certain length, output a list of approiate length with blank spaces**

		ArrayList<String> list = new ArrayList<String>();
		list.add("J");
		list.add("A");
		list.add("G");
		list.add("U");
		list.add("A");
		list.add("R");

		list.add("A");
		list.add("G");
		list.add("U");
		list.add("A");
		list.add("R");

		list.add("G");
		list.add("U");
		list.add("A");
		list.add("R");

		list.add("U");
		list.add("A");
		list.add("R");

		list.add("A");
		list.add("R");

		list.add("R");

		for (h = userMin - 1; h < userMax; h++) {

			for (i = userMin; i <= userMax - h; i++) {

				btnMybutton = new JButton(list.get(p));
				btnMybutton.setName("Letter");
				p += 1;

				// x,y,length, height
				if (i != userMin)
					x = x + length;

				btnMybutton.setBounds(x, y, length, height);
				frame.getContentPane().add(btnMybutton);
				frame.revalidate();
				frame.repaint();

			}
			y -= 88;
			x = formWidth / 2 - ((userMax / 2) * length) + level;
			level += length / 2;
		}

	}


    // Select a random BigWord from a BigWordCollection
    private BigWord getRandomBigWord(BigWordCollection collection)
    {
        Random rand = new Random();
        int index = rand.nextInt(collection.size());

        BigWord bw = collection.getBigWord(index);

        return bw;
    }

    // Get an ArrayList of String ArrayLists containing logical characters of words
    // TODO: This only cares about topic and isn't very efficient
    // It also simply ignores empty collections
    private ArrayList<ArrayList<String>> getLogicalList(BigWordCollection topic)
    {
        ArrayList<ArrayList<String>> logicalWords =
            new ArrayList<ArrayList<String>>();

        WordProcessor wp = new WordProcessor();
        ArrayList<String> list = null;
        BigWordCollection c = null;

        for (int i = userMax; i >= 1; i--) {

            c = topic.getBigWordCollectionByWordLength(i);
            if(c.isEmpty())
                continue;

            BigWord next = getRandomBigWord(c);

            wp.setWord(next.getEnglish());
            list = Parser.stripSpaces(wp.getLogicalChars());

            logicalWords.add(list);

            System.out.println(i);
            System.out.println(next.getEnglish());
        }


        return logicalWords;

    }

	private class GenerateHTMLButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

			ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
			ArrayList<String> one = new ArrayList<String>();
			ArrayList<String> two = new ArrayList<String>();
			ArrayList<String> three = new ArrayList<String>();
			// this will have to be linked to the breakdown of the word to
			// function dynamically
			one.add("T");
			two.add("T");
			two.add("O");
			three.add("T");
			three.add("O");
			three.add("W");
			list.add(one);
			list.add(two);
			list.add(three);
			saveNewFile(HtmlBodyCreator.createBody(list));
			String htmlFilePath = "Pyramid.html"; // path to your new file
			File htmlFile = new File(htmlFilePath);

			// open the default web browser for the HTML page
			try {
				Desktop.getDesktop().browse(htmlFile.toURI());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private boolean saveNewFile(String contents) {
			File file = new File("Pyramid.html");
			try {
				FileOutputStream output = new FileOutputStream(file);
				output.write(contents.getBytes());
				output.close();
				System.out.println("File Saved Successfully");
				System.out
						.println("File Saved Here: " + file.getAbsolutePath());
				return true;
			} catch (IOException e) {
				System.out.println("Error saving file");
			}
			return false;
		}

	}
}
