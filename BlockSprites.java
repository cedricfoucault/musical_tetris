import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BlockSprites {
	public static BufferedImage lightgray_block;
	public static BufferedImage gray_block;
	public static BufferedImage yellow_block;
	public static BufferedImage cyan_block;
	public static BufferedImage green_block;
	public static BufferedImage red_block;
	public static BufferedImage orange_block;
	public static BufferedImage blue_block;
	public static BufferedImage purple_block;
	
	static {
	    InputStream buffer;
		try {
		    buffer = BlockSprites.class.getResourceAsStream(
		        "images/lightgray_block.png"
		    );
		    lightgray_block = ImageIO.read(buffer);
		    buffer =
		    BlockSprites.class.getResourceAsStream("images/gray_block.png");
		    gray_block = ImageIO.read(buffer);
		    buffer =
		    BlockSprites.class.getResourceAsStream("images/yellow_block.png");
		    yellow_block = ImageIO.read(buffer);
		    buffer =
		    BlockSprites.class.getResourceAsStream("images/cyan_block.png");
		    cyan_block = ImageIO.read(buffer);
		    buffer =
		    BlockSprites.class.getResourceAsStream("images/green_block.png");
		    green_block = ImageIO.read(buffer);
		    buffer =
		    BlockSprites.class.getResourceAsStream("images/red_block.png");
		    red_block = ImageIO.read(buffer);
		    buffer =
		    BlockSprites.class.getResourceAsStream("images/orange_block.png");
		    orange_block = ImageIO.read(buffer);
		    buffer =
		    BlockSprites.class.getResourceAsStream("images/blue_block.png");
		    blue_block = ImageIO.read(buffer);
		    buffer =
		    BlockSprites.class.getResourceAsStream("images/purple_block.png");
		    purple_block = ImageIO.read(buffer);
		} catch (IOException e) {
			System.out.print("error trying to load block image\n");
			System.exit(-1);
		}
	}
}