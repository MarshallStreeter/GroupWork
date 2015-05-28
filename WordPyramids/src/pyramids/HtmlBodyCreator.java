package pyramids;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HtmlBodyCreator {

	/**
	 * Accepts an arraylist that stores an arraylist for each individual word in
	 * the pyramid will then pull each character as the logical string
	 * 
	 * @param list
	 * @return
	 */
	public static String createBody() {
		String body = "<html><head><meta charset=utf-8><title>Word Pyramid</title>"
				+ "</head><body>\n"
				+ "<div style='width:1200px; margin: 0 auto; text-align: center;'>";
		for (int i = 0; i < GUIFacade.instance().getGameWords().size(); i++) {
			// start new row
			body += "\n<div>\n";
			// input the individual letters(logical string according to siva)
			for (int z = 0; z < GUIFacade.instance().getGameWords().get(i).size(); z++) {
				byte[] b;
				try {
					b = GUIFacade.instance().getGameWords().get(i).get(z).getBytes("UTF-8");
					String test = new String(b,"UTF-8");
					body += letterBlock(test);
				} catch (UnsupportedEncodingException e) {
				
					e.printStackTrace();
				}
			
			}
			// end this row
			body += "\n</div>\n";
		}

		// end the body
		body += "</div>\n</body></html>";
		return body;

	}

	/**
	 * Build each letter box
	 */
	private static String letterBlock(String letter) {

		return String
				.format("<div style='border: 1px solid black;display:inline-block;padding:5px;width:35px;height:35px;'>%s\n</div>",
						letter);
	}
}
