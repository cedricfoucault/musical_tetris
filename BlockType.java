import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Point;
import javax.imageio.ImageIO;

public enum BlockType {
	EMPTY (BlockSprites.gray_block), 
	O     (BlockSprites.yellow_block),
	I     (BlockSprites.cyan_block),
	S     (BlockSprites.green_block),
	Z     (BlockSprites.red_block),
	L     (BlockSprites.orange_block),
	J     (BlockSprites.blue_block),
	T     (BlockSprites.purple_block),
	KILL  (BlockSprites.gray_block);
	
	public static final int NB = 7;
	
	private final BufferedImage sprite;
	
	private BlockType(BufferedImage img) {
		sprite = img;
	}
	
	public static BlockType fromInt(int i) {
		return values()[i];
	}
	
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		if (this != EMPTY) {
			g.drawImage(sprite,
				x, y, x + width, y + height,
				0, 0, sprite.getWidth(null), sprite.getHeight(null), null);
		}
	}
	
	public void draw(Graphics2D g, Point location, int width, int height) {
		draw(g, location.x, location.y, width, height);
	}
}
