import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.*;

public enum BlockType {
	EMPTY (Color.lightGray), 
	O     (Color.yellow),
	I     (Color.cyan),
	S     (Color.green),
	Z     (Color.red),
	L     (Color.orange),
	J     (Color.blue),
	T     (new Color(148, 0, 211)); // purple
	
	public static final int NB = 7;
	private final Color color;
	
	private BlockType(Color color) {
		this.color = color;
	}
	
	public static BlockType fromInt(int i) {
		return values()[i];
	}
	
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		if (this != EMPTY) {
			BasicStroke stroke = new BasicStroke(2);
			Stroke oldStroke = g.getStroke();
			g.setStroke(stroke);
			g.setColor(color);
			g.fillRect(x, y, width - 1, height - 1);
			g.setColor(Color.black);
			g.drawRect(x, y, width - 1, height - 1);
			g.setStroke(oldStroke);
		}
	}
	
	public void draw(Graphics2D g, Point location, int width, int height) {
		draw(g, location.x, location.y, width, height);
	}
}