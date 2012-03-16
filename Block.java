import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.*;

public class Block {
	private BlockType blockType;
	private Point coordinates;
	static Dimension SIZE;
	
	public static void setSize(Dimension size) {
		SIZE = size;
	}
	
	public Block(int x, int y) {
		this(new Point(x, y));
	}
	
	public Block(Point coordinates) {
		this.coordinates = coordinates;
		blockType = BlockType.EMPTY;
	}
	
	public void setType(BlockType blockType) {
		this.blockType = blockType;
	}
	
	public void empty() {
		blockType = BlockType.EMPTY;
	}
	
	public BlockType getType() {
		return blockType;
	}
	
	public boolean isEmpty() {
		return blockType.equals(BlockType.EMPTY);
	}
	
	private void kill() {
		blockType = BlockType.EMPTY;
	}
	
	public void draw(Graphics2D g) {
		int graph_x = coordinates.x * SIZE.width;
		int graph_y = Board.SIZE.height - (coordinates.y + 1) * SIZE.height;
		blockType.draw(g, graph_x, graph_y, SIZE.width, SIZE.height);
	}
}