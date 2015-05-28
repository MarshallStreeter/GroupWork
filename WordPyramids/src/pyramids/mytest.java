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
	private JComboBox language;
	private JCheckBox relatedCheckBox;
	private JCheckBox scrambleChkBox;

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

		// Creates the entire BigWord Collection
		Config.entireCollection = new BigWordCollection();
		Config.gameCollection = Config.entireCollection;
		Config.gameCollection.removeDuplicates();

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(70, 130, 180));
		frame.setResizable(false);
		frame.setBounds(100, 100, formWidth, formHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Vector int_items = new Vector();
		int x;
		for (x = 1; x <= Config.MAX_WORD_LENGTH; x++)
			int_items.add(x);

		final DefaultComboBoxModel model1 = new DefaultComboBoxModel(int_items);
		JComboBox cboMin = new JComboBox(model1);
		cboMin.setBounds(83, 52, 80, 24);
		frame.getContentPane().add(cboMin);
		cboMin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				userMin = (int) model1.getSelectedItem();
			}
		});

		final DefaultComboBoxModel model2 = new DefaultComboBoxModel(int_items);
		JComboBox cboMax = new JComboBox(model2);
		cboMax.setBounds(244, 52, 80, 24);
		cboMax.setSelectedIndex(5);
		frame.getContentPane().add(cboMax);
		cboMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				userMax = (int) model2.getSelectedItem();
			}
		});

		JLabel lblMin = new JLabel("Min");
		lblMin.setBounds(45, 56, 35, 14);
		frame.getContentPane().add(lblMin);

		JLabel lblMax = new JLabel("Max");
		lblMax.setBounds(204, 56, 35, 14);
		frame.getContentPane().add(lblMax);

		cboTopic = new JComboBox(populateTopicBox());

		cboTopic.setBounds(639, 52, 160, 24);
		frame.getContentPane().add(cboTopic);
		cboTopic.setSelectedIndex(0);
		JLabel lblTopic = new JLabel("Topic");
		lblTopic.setBounds(590, 56, 46, 14);
		frame.getContentPane().add(lblTopic);

		scrambleChkBox = new JCheckBox("Scramble");
		scrambleChkBox.setBackground(new Color(70, 130, 180));
		scrambleChkBox.setBounds(825, 172, 200, 50);
		frame.getContentPane().add(scrambleChkBox);

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
		btnPlay.setBounds(825, 52, 200, 50);
		frame.getContentPane().add(btnPlay);

		generateHtmlBtn = new JButton("Generate HTML");
		generateHtmlBtn.addActionListener(new GenerateHTMLButtonHandler());
		generateHtmlBtn.setBounds(825, 115, 200, 50);
		frame.getContentPane().add(generateHtmlBtn);
		String[] languages = { "English", "Telugu" };
		language = new JComboBox(languages);
		language.setBounds(447, 52, 105, 24);
		frame.getContentPane().add(language);

		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setBounds(374, 56, 70, 16);
		frame.getContentPane().add(lblLanguage);

		relatedCheckBox = new JCheckBox("Use Related Words");
		relatedCheckBox.setBounds(825, 225, 200, 50);
		relatedCheckBox.setBackground(new Color(70, 130, 180));
		frame.getContentPane().add(relatedCheckBox);

	}

	/**
     * Destroy the dynamically generated pyramid buttons.
     */
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
		Collections.sort(topicStrings);
		topicStrings.add(0, "Any");
		return topicStrings.toArray();
	}

	/**
     * Generate the word pyramid.
     */
	private void play_game(int userMin, int userMax) {

		int x, y;
		int length = Config.PYRAMID_CELL_LENGTH;
		int height = Config.PYRAMID_CELL_HEIGHT;
		x = formWidth / 2 - ((userMax / 2) * length);
		y = height + (height * userMax);

		int level = 30;

		boolean english = ((language.getSelectedItem().toString().toLowerCase()
				.equals("english")) ? true : false);
		boolean unrelated = !relatedCheckBox.isSelected();
		boolean scramble = scrambleChkBox.isSelected();

		//separates logic and storage from the GUI class that isn't related to drawing board
		GUIFacade.instance().generateWords(unrelated,
				cboTopic.getSelectedItem().toString(), userMin, userMax,
				english,scramble);

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

	private class GenerateHTMLButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			saveNewFile(HtmlBodyCreator.createBody());

			File htmlFile = new File( Config.PYRAMID_HTMLFILE_PATH );

			// open the default web browser for the HTML page
			try {
				Desktop.getDesktop().browse(htmlFile.toURI());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private boolean saveNewFile(String contents) {
			
			try {
				Writer out = new BufferedWriter(new OutputStreamWriter(
					    new FileOutputStream(Config.PYRAMID_HTMLFILE_PATH), "UTF-8"));
					try {
					    out.write(contents);
					} finally {
					    out.close();
					}
			
				return true;
			} catch (IOException e) {
				System.out.println("Error saving file");
			}
			return false;
		}

	}
}
