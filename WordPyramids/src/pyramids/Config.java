package pyramids;

import java.awt.Font;

import javax.swing.ImageIcon;

/**
 * This class provides the configurations (both system and GUI)
 * to accommodate future expansions and modularity
 * 
 * @author srj
 *
 */
public class Config  {
		
	// Two static variables for managing the entire gameCollection
		public static BigWordCollection entireCollection;
		public static BigWordCollection gameCollection;
		
		
	// Use these directories if we are using $HOME
//	public static String INPUT_FILE = System.getProperty("user.home") + "\\ICS499\\te\\input_words.txt";
//	public static String IMAGE_DIR = System.getProperty("user.home") + "\\ICS499\\te\\res\\";
//	public static String SOUND_DIR = System.getProperty("user.home") + "\\ICS499\\te\\res\\";

	// Use these directories if we are asking the users to unzip the data to a particular directory
    public static String INPUT_FILE = "input_words.txt";
	public static String IMAGE_DIR = "C:\\QuizMaster\\te\\res\\";
	public static String SOUND_DIR = "C:\\QuizMaster\\te\\res\\";
	
	// Use the internal resources directory
	// Another option is to use the internal resources directory
	
	// Display Name for the application
	public static String  APP_TITLE = "Telugu Quiz Master";
	public static String LOGO_FILE = "logo.jpg";
	public static String WELCOME_TITLE = "Welcome to Telugu Quiz Master";
	public static String WELCOME_MSG = "\nSet the level for the quiz " +
										" by clicking a button below." +
										"\n\nYou can also set detailed configuration in Config tab.";

	// Tab and Background Colors
	public static String WELCOME_PANEL_BG_COLOR = "orange";
	public static String PREVIEW_PANEL_BG_COLOR = "cyan";
	public static String READING_PANEL_BG_COLOR = "yellow";
	public static String WRITING_PANEL_BG_COLOR = "green";
	public static String VOCABULARY_PANEL_BG_COLOR = "blue";
	public static String CONFIG_PANEL_BG_COLOR = "lightGray";
	public static String WORKSHEET_PANEL_BG_COLOR = "gainsboro";
	public static String MATCH_PANEL_BG_COLOR = "gainsboro";
	
	// For Reading the input file
	public static final String DELIMETER = "\\|";
	public static final int MAX_ITEMS_PER_LINE = 7;
	
		

    // button sizes to be used
    public static final int BTN_WIDTH = 150;
    public static final int BTN_HEIGHT = 150;
    
    // These are the ICONS to be used on the buttons
    // These are scaled to fit the button sizes
    public static final ImageIcon ICON_LEVEL_1 = new ImageIcon(new ImageIcon(IMAGE_DIR + "level1.png")
    .getImage().getScaledInstance(BTN_WIDTH, BTN_HEIGHT, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_LEVEL_2 = new ImageIcon(new ImageIcon(IMAGE_DIR + "level2.png")
    .getImage().getScaledInstance(BTN_WIDTH, BTN_HEIGHT, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_LEVEL_3 = new ImageIcon(new ImageIcon(IMAGE_DIR + "level3.png")
    .getImage().getScaledInstance(BTN_WIDTH, BTN_HEIGHT, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_SOUND = new ImageIcon(new ImageIcon(IMAGE_DIR + "sound_icon.png")
        .getImage().getScaledInstance(BTN_WIDTH, BTN_HEIGHT, java.awt.Image.SCALE_SMOOTH));
        
    public static final ImageIcon ICON_PREVIOUS = new ImageIcon(new ImageIcon(IMAGE_DIR + "previous_arrow.png")
    .getImage().getScaledInstance(BTN_WIDTH, BTN_HEIGHT, java.awt.Image.SCALE_SMOOTH));

    public static final ImageIcon ICON_NEXT = new ImageIcon(new ImageIcon(IMAGE_DIR + "next_arrow.png")
    .getImage().getScaledInstance(BTN_WIDTH, BTN_HEIGHT, java.awt.Image.SCALE_SMOOTH));

    public static final ImageIcon ICON_CHECK = new ImageIcon(new ImageIcon(IMAGE_DIR + "check_icon.png")
    .getImage().getScaledInstance(BTN_WIDTH, BTN_HEIGHT, java.awt.Image.SCALE_SMOOTH));
    
    // Tabbed Icons
    public static final ImageIcon ICON_WELCOME = new ImageIcon(new ImageIcon(IMAGE_DIR + "welcome.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_BW_WELCOME = new ImageIcon(new ImageIcon(IMAGE_DIR + "bw_welcome.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
   
    public static final ImageIcon ICON_PREVIEW = new ImageIcon(new ImageIcon(IMAGE_DIR + "preview.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_BW_PREVIEW = new ImageIcon(new ImageIcon(IMAGE_DIR + "bw_preview.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
   
    
    public static final ImageIcon ICON_READING = new ImageIcon(new ImageIcon(IMAGE_DIR + "reading.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_BW_READING = new ImageIcon(new ImageIcon(IMAGE_DIR + "bw_reading.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_WRITING = new ImageIcon(new ImageIcon(IMAGE_DIR + "writing.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
   
    public static final ImageIcon ICON_BW_WRITING = new ImageIcon(new ImageIcon(IMAGE_DIR + "bw_writing.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
 
    public static final ImageIcon ICON_VOCABULARY = new ImageIcon(new ImageIcon(IMAGE_DIR + "vocabulary.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
   
    public static final ImageIcon ICON_BW_VOCABULARY = new ImageIcon(new ImageIcon(IMAGE_DIR + "bw_vocabulary.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
 
    public static final ImageIcon ICON_CONFIG = new ImageIcon(new ImageIcon(IMAGE_DIR + "config.png")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
 
    public static final ImageIcon ICON_BW_CONFIG = new ImageIcon(new ImageIcon(IMAGE_DIR + "bw_config.png")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_WORKSHEET = new ImageIcon(new ImageIcon(IMAGE_DIR + "worksheet.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_BW_WORKSHEET = new ImageIcon(new ImageIcon(IMAGE_DIR + "bw_worksheet.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
    
    public static final ImageIcon ICON_MATCH = new ImageIcon(new ImageIcon(IMAGE_DIR + "match.jpg")
    .getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
 
    // default sound to play when sound file is NOT present
    public static final String DEFAULT_SOUND = SOUND_DIR + "NO_SOUND.wav";
    public static final String DEFAULT_IMAGE = IMAGE_DIR + "silc.png";
    public static final String NOT_FOUND_IMAGE = IMAGE_DIR + "no_image.jpg";
    
	// Language and Font settings 
    public static String LANGUAGE = "Telugu"; // hard coded configuration
    public static String FONT_NAME = "Gidugu"; 
	public static int FONT_SIZE = 18;
	public static Font LABELFONT = new Font("Calibri", Font.BOLD,20);
	public static Font WELCOME_FONT = new Font("Calibri", Font.BOLD,40);
	// Static variables for the FONTs
	public static Font TELUGU_FONT;
			
}
