import java.awt.*;
import java.lang.*;
import java.util.*;

public class Piece {
	public BlockType blockType; // the block type of the tetris piece
	public int orientation; // the rotation of the tetris piece
	private Point center; // the absolute coordinates of the center block
	private Point[] relativePoints; // the relative coordinates of the 3 other blocks
	
	private static final int START_ORIENTATION = 0;
	public static final int START_X = 5;
	public static final int START_Y = 19;
	private static final Random BLOCK_GENERATOR = new Random();
	
	// CONSTRUCTORS
	Piece(Piece p) {
		blockType = p.blockType;
		orientation = p.orientation;
		center = new Point(p.center);
		relativePoints = p.relativePoints;
	}
	
	Piece(BlockType blockType, int orientation, Point center) {
		this.blockType = blockType;
		this.orientation = orientation;
		this.center = center;
		this.relativePoints = TetrominoesCatalog.getRelativePoints(blockType, orientation);	
	}
	
	Piece(BlockType blockType, int orientation, int x, int y) {
		this(blockType, orientation, new Point(x, y));
	}
	
	Piece(BlockType blockType, int orientation) {
		this(blockType, orientation, START_X, START_Y);
	}
	
	Piece(BlockType blockType) {
		this(blockType, START_ORIENTATION);
	}
	
	// GETTERS
	Point getCenter() {
		return center;
	}
	
	Point[] getRelativePoints() {
		return relativePoints;
	}
	
	void draw(Graphics2D g, int frame_width, int frame_height) {
		// int block_width = frame_width / Board.WIDTH;
		// int block_height = frame_height / Board.HEIGHT;
		int block_width = Block.SIZE.width;
		int block_height = Block.SIZE.height;
		blockType.draw(g, block_width * center.x, block_height * (Board.HEIGHT - (center.y + 1)), block_width, block_height);
		for (Point point : relativePoints) {
			blockType.draw(g, block_width * (center.x + point.x), block_height * (Board.HEIGHT - (center.y + point.y + 1)), block_width, block_height);
		}
	}
	
	// moves the piece accordingly 
	void move(Move movetype) {
		switch (movetype) {
			case ROTATE: {
				orientation = (orientation + 1) % 4;
				relativePoints = TetrominoesCatalog.getRelativePoints(blockType, orientation);
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
	
	public static Piece newRandomPiece() {
		BlockType blockType = BlockType.fromInt(BLOCK_GENERATOR.nextInt(BlockType.NB) + 1);
		return new Piece(blockType);
	}
}
