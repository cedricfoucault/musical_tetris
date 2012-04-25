import java.awt.*;
import java.lang.*;
import java.util.LinkedList;
import java.util.Collections;

public class Piece {
	public BlockType blockType; // the block type of the tetris piece
	public int orientation; // the rotation of the tetris piece
	private Point center; // the absolute coordinates of the center block
	private Point[] relativePoints; // the relative coordinates of the 3 other blocks
	
	private static final int START_ORIENTATION = 0;
	public static final int START_X = 5;
	public static final int START_Y = 19;
	private static final LinkedList<BlockType> PIECE_BAG = 
	    new LinkedList<BlockType>();
	private static LinkedList<BlockType> current_piece_bag;
	static {
	    PIECE_BAG.add(BlockType.O);
	    PIECE_BAG.add(BlockType.I);
	    PIECE_BAG.add(BlockType.S);
	    PIECE_BAG.add(BlockType.Z);
	    PIECE_BAG.add(BlockType.L);
	    PIECE_BAG.add(BlockType.J);
	    PIECE_BAG.add(BlockType.T);
	    generateNewPieceBag();
	}
	
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
		this.relativePoints = PieceData.getRelativePoints(blockType, orientation);	
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
	void move(MoveType movetype) {
		switch (movetype) {
			case ROTATE: {
				orientation = (orientation + 1) % 4;
				relativePoints = PieceData.getRelativePoints(blockType, orientation);
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
	
	private static void generateNewPieceBag() {
	    current_piece_bag = new LinkedList<BlockType>(PIECE_BAG);
	    Collections.shuffle(current_piece_bag);
	}
	
	public static Piece newRandomPiece() {
	    // generate a new bag if the current bag is empty
	    if (current_piece_bag.isEmpty()) {
	        generateNewPieceBag();
	    }
	    // get the next piece in the bag
	    BlockType blockType = (BlockType) current_piece_bag.removeFirst();
		return new Piece(blockType);
	}
}
