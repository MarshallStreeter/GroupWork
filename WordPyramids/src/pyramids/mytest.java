package pyramids;


import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;

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
	private JComboBox language;

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
		String[] languages = { "English", "Telugu" };
		language = new JComboBox(languages);
		language.setBounds(118, 100, 147, 22);
		frame.getContentPane().add(language);

		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setBounds(59, 103, 56, 16);
		frame.getContentPane().add(lblLanguage);

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

	/**
	 * Populates the topic list
	 * 
	 * @return
	 */
	private Object[] populateTopicBox() {
		ArrayList<String> topicStrings = new ArrayList<String>();
		Hashtable<String, ArrayList<BigWord>> selects = Config.entireCollection
				.getBigWordsTopicsTable();
		for (Entry<String, ArrayList<BigWord>> entry : selects.entrySet()) {
			String key = entry.getKey();
			topicStrings.add(key);
		}
		return topicStrings.toArray();
	}

	private void play_game(int userMin, int userMax) {

		int x, y;
		int length = 60;
		int height = 60;
		x = formWidth / 2 - ((userMax / 2) * length);
		y = 580;

		int level = 30;



		boolean english = ((language.getSelectedItem().toString().toLowerCase()
				.equals("english")) ? true : false);
		//separates logic and storage from the GUI class that isn't related to drawing board
		GUIFacade.instance().generateWords(true,
				cboTopic.getSelectedItem().toString(), userMin, userMax,
				english);

		Font font = new Font("gautami", Font.PLAIN, 10);
		for (int h = GUIFacade.instance().getGameWords().size() - 1; h >= 0; h--) {

			for (int i = 0; i < GUIFacade.instance().getGameWords().get(h)
					.size(); i++) {

				try {
					byte[] b = GUIFacade.instance().getGameWords().get(h)
							.get(i).getBytes("UTF-8");
					btnMybutton = new JButton(new String(b, "UTF-8"));
					btnMybutton.setFont(font);
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
				btnMybutton.setName("Letter");

				// x,y,length, height
				if (i != 0)
					x = x + length;

				btnMybutton.setBounds(x, y, length, height);
				frame.getContentPane().add(btnMybutton);
				frame.revalidate();
				frame.repaint();

			}
			y -= height;
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

			saveNewFile(HtmlBodyCreator.createBody(GUIFacade.instance()
					.getGameWords()));
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
