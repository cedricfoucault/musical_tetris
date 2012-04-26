import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Dimension;

public class Block {
	private BlockType blockType;
	private Point coordinates;
	static Dimension SIZE;
	boolean killed;
	
	public static void setSize(Dimension size) {
		SIZE = size;
	}
	
	public Block(int x, int y) {
		this(new Point(x, y));
	}
	
	public Block(Point coordinates) {
		this.coordinates = coordinates;
		blockType = BlockType.EMPTY;
		killed = false;
	}
	
	public void setType(BlockType blockType) {
		this.blockType = blockType;
	}
	
	public void fall() {
		coordinates.y--;
	}
	
	
	public void fall(int nRows) {
		coordinates.y -= nRows;
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
	
	public void kill() {
		killed = true;
	}
	
	public void draw(Graphics2D g) {
		int graph_x = coordinates.x * SIZE.width;
		int graph_y = Board.SIZE.height - (coordinates.y + 1) * SIZE.height;
		if (killed) {
			BlockType.KILL.draw(g, graph_x, graph_y, SIZE.width, SIZE.height);
		} else {
			blockType.draw(g, graph_x, graph_y, SIZE.width, SIZE.height);
		}
	}
}