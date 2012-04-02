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
	
	void draw(Graphics2D g) {
		int blockWidth = Block.SIZE.width;
		int blockHeight = Block.SIZE.height;
		int centerX = 2, centerY = 3;
		blockType.draw(g, blockWidth * centerX, blockHeight * (4 - (centerY + 1)), blockWidth, blockHeight);
		for (Point point : relativePoints) {
			blockType.draw(g, blockWidth * (centerX + point.x), blockHeight * (4 - (centerY + point.y + 1)), blockWidth, blockHeight);
		}
	}
	
	void drawOnBoard(Graphics2D g) {
		int blockWidth = Block.SIZE.width;
		int blockHeight = Block.SIZE.height;
		
		blockType.draw(g, blockWidth * center.x, blockHeight * (Board.HEIGHT - (center.y + 1)), blockWidth, blockHeight);
		
		for (Point point : relativePoints) {
			blockType.draw(g, blockWidth * (center.x + point.x), blockHeight * (Board.HEIGHT - (center.y + point.y + 1)), blockWidth, blockHeight);
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
		BlockType blockType =
			BlockType.fromInt(BLOCK_GENERATOR.nextInt(BlockType.NB) + 1);
		return new Piece(blockType);
	}
}
