import java.io.InputStream;
import java.awt.Font;

public class TetrisFont {
	public static final Font TETRIS_FONT = (getFont("tetri.ttf"));
		
	public static Font getTetrisFont(float size) {
		return TETRIS_FONT.deriveFont(size);
	}
		
	private static Font getFont(String name) {
	    Font font = null;
	    String fName = "fonts/" + name;
	    try {
	      InputStream is = TetrisFont.class.getResourceAsStream(fName);
	      font = Font.createFont(Font.TRUETYPE_FONT, is);
	    } catch (Exception ex) {
	      ex.printStackTrace();
	      System.err.println(fName + " not loaded.  Using SansSerif font.");
	      font = new Font("SansSerif", Font.PLAIN, 1);
	    }
	    return font;
	}
}
