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
	Piece(Shape shape, int orientation, Point center, Point[] blocks) {
		this.shape = shape;
		this.orientation = orientation;
		this.center = center;
		this.blocks = blocks;
	}
	Piece(Shape shape, int orientation, Point center) {
		this.shape = shape;
		this.orientation = orientation;
		this.center = center;
		this.blocks = TetrominoesCatalog.getData(shape, orientation);	
	}
	
	
	Piece(Shape shape, int orientation, int x, int y) {
		Piece(shape, orientation, new Point(x, y));
	}
	
	Piece(Shape shape, int orientation) {
		Piece(shape, orientation, START_X, START_Y);
	}
	
	Piece(Shape shape) {
		Piece(shape, START_ORIENTATION);
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
		return (new Piece(shape, orientation, center.getLocation(), blocks));
	}
	
	public static Piece getRandomPiece() {
		shape = Shape.fromInt(shapeGenerator.nextInt(Shape.NB));
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
