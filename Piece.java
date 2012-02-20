import java.awt.*;
import java.lang.*;
import java.util.*;

public class Piece {
	public Shape shape; // the shape of the tetris piece
	public int orientation; // the rotation of the tetris piece
	private Point center; // the absolute coordinates of the center block
	private Point[] blocks; // the relative coordinates of the 3 other blocks
	
	private static final int START_ORIENTATION = 0;
	public static final int START_X = 5;
	public static final int START_Y = 19;
	private static final Random shapeGenerator = new Random();
	
	// CONSTRUCTORS
	// Piece(Shape shape, int orientation, Point center, Point[] blocks) {
	// 	this.shape = shape;
	// 	this.orientation = orientation;
	// 	this.center = center;
	// 	this.blocks = blocks;
	// }
	Piece(Piece p) {
		shape = p.shape;
		orientation = p.orientation;
		center = new Point(p.center);
		blocks = p.blocks;
	}
	
	Piece(Shape shape, int orientation, Point center) {
		this.shape = shape;
		this.orientation = orientation;
		this.center = center;
		this.blocks = TetrominoesCatalog.getBlocks(shape, orientation);	
	}
	
	Piece(Shape shape, int orientation, int x, int y) {
		this.shape = shape;
		this.orientation = orientation;
		this.center = new Point(x, y);
		this.blocks = TetrominoesCatalog.getBlocks(shape, orientation);
		// Piece(shape, orientation, new Point(x, y));
	}
	
	Piece(Shape shape, int orientation) {
		this.shape = shape;
		this.orientation = orientation;
		this.center = new Point(START_X, START_Y);
		this.blocks = TetrominoesCatalog.getBlocks(shape, orientation);
		// Piece(shape, orientation, START_X, START_Y);
	}
	
	Piece(Shape shape) {
		this.shape = shape;
		this.orientation = START_ORIENTATION;
		this.center = new Point(START_X, START_Y);
		this.blocks = TetrominoesCatalog.getBlocks(shape, orientation);
		// Piece(shape, START_ORIENTATION);
	}
	
	// GETTERS
	Point getCenter() {
		return center;
	}
	
	Point[] getBlocks() {
		return blocks;
	}
	
	void draw(Graphics2D g, int frame_width, int frame_height) {
		int block_width = frame_width / Board.WIDTH;
		int block_height = frame_height / Board.HEIGHT;
		g.setColor(Color.white);
		g.fillRect(center.x * block_width, center.y * block_height, block_width - 1, block_height - 1);
		g.setColor(Color.blue);
		g.drawRect(center.x * block_width, center.y * block_height, block_width, block_height);
		for (Point block : blocks) {
			g.setColor(Color.white);
			g.fillRect((center.x + block.x) * block_width, (center.y + block.y) * block_height, block_width - 1, block_height - 1);
			g.setColor(Color.blue);
			g.drawRect((center.x + block.x) * block_width, (center.y + block.y) * block_height, block_width, block_height);
		}
	}
	
	// moves the piece accordingly 
	void move(Move movetype) {
		switch (movetype) {
			case ROTATE: {
				orientation = (orientation + 1) % 4;
				blocks = TetrominoesCatalog.getBlocks(shape, orientation);
				break;
			}
			case LEFT: {
				center.x -= 1;
				break;
			}
			case RIGHT: {
				center.x += 1;
				break;
			}
			case DROP: {
				center.y -= 1;
			}
		}
	}
	
	// returns a copy of the piece
	public Piece copy() {
		return (new Piece(this));
	}
	
	public static Piece getRandomPiece() {
		Shape shape = Shape.fromInt(shapeGenerator.nextInt(Shape.NB));
		return new Piece(shape);
	}
}

// public class Point {
// 	public int x, y;
// 		
// 	Point(int x, int y) {
// 		this.x = x;
// 		this.y = y;
// 	}
// 	
// 	public void translate(int dx, int dy) {
// 		x += dx;
// 		y += dy;
// 	}
// 	
// 	public Point copy() {
// 		return (new Point(x, y));
// 	}
// }
