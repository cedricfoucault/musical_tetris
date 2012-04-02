import java.awt.Graphics2D;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
		try {
			lightgray_block = 
				ImageIO.read(new File("images/lightgray_block.png"));
		    gray_block = ImageIO.read(new File("images/gray_block.png"));
			yellow_block = ImageIO.read(new File("images/yellow_block.png"));
			cyan_block = ImageIO.read(new File("images/cyan_block.png"));
			green_block = ImageIO.read(new File("images/green_block.png"));
			red_block = ImageIO.read(new File("images/red_block.png"));
			orange_block = ImageIO.read(new File("images/orange_block.png"));
			blue_block = ImageIO.read(new File("images/blue_block.png"));
			purple_block = ImageIO.read(new File("images/purple_block.png"));
		} catch (IOException e) {
			System.out.print("error trying to load block image\n");
			System.exit(-1);
		}
	}
}