import java.awt.Graphics2D;
import java.util.LinkedList;
import java.awt.*;

public class GameState {
	private boolean gameOver;
	private boolean paused;
	private int level;
	private int score;
	private int linesCompleted; // number of linesCompleted cleared
	private Board board;
	private boolean isActivePiece;
	private Piece currentPiece;
	private Piece nextPiece;
	private StateListener listener;
	
	
	GameState(int startLevel) {
		level          = startLevel;
		score          = 0;
		linesCompleted = 0;
		gameOver       = false;
		paused         = false;
		isActivePiece  = false;
		board          = new Board();
		nextPiece      = Piece.newRandomPiece();
	}
	
	void init(int startLevel) {
		level          = startLevel;
		score          = 0;
		linesCompleted = 0;
		gameOver       = false;
		paused         = false;
		isActivePiece  = false;
		board.empty();
	}
	
	GameState() {
		this(1);
	}
	
	void addBoardListener(BoardListener bl) {
		board.addBoardListener(bl);
	}
	
	void addStateListener(StateListener sl) {
		listener = sl;
	}
	
	// GETTERS
	Board getBoard() {
		return board;
	}
	Piece getCurrentPiece() {
		return currentPiece;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLinesCompleted() {
		return linesCompleted;
	}
	
	public void incScore(int nRows) {
		switch(nRows) {
			case 1: score += level * 40; break;
			case 2: score += level * 100; break;
			case 3: score += level * 300; break;
			case 4: score += level * 1200; break;
		}
	}
	
	public void incLinesCompleted(int nRows) {
		linesCompleted += nRows;
		updateLevel();
	}
	
	private void updateLevel() {
		int earnedLevel = 1;
		
        if (linesCompleted <= 0) {
          earnedLevel = 1;
        } else if ((linesCompleted >= 1) && (linesCompleted <= 90)) {
          earnedLevel = 1 + ((linesCompleted - 1) / 10);
        } else if (linesCompleted >= 91) {
          earnedLevel = 10;
        }
		
		if (earnedLevel > level) {
			level = earnedLevel;
			listener.levelUp();
		}
	}
	
	public boolean isOver() {
		return gameOver;
	}
	public boolean isPaused() {
		return paused;
	}
	
	boolean isActivePiece() {
		return isActivePiece;
	};
	
	boolean canFallPiece() {
		return canMovePiece(MoveType.DROP);
	}
	void moveDownPiece() {
		movePiece(MoveType.DROP);
	}
	void hardDropPiece() {
		while (canMovePiece(MoveType.DROP)) {
			movePiece(MoveType.DROP);
		}
	}
	
	boolean canMovePiece(MoveType movetype) {
		if (!isPaused() && !isOver()) {
			Piece movedPiece = (currentPiece.copy());
			movedPiece.move(movetype);
			return !(board.willCollide(movedPiece));
		} else {
			return false;
		}
	}
	
	void movePiece(MoveType movetype) {
		currentPiece.move(movetype);
		if (movetype == MoveType.DROP) {
			score++;
			if (!canFallPiece()) {
				GameSound.playLand();
			}
		} 
	}
	
	void mergePiece() {
		isActivePiece = false;
		board.merge(currentPiece);
	}
	
	boolean canSpawnPiece() {
		return !board.willCollide(nextPiece);
	}
	
	void spawnPiece() {
		currentPiece = nextPiece;
		nextPiece = Piece.newRandomPiece();
		isActivePiece = true;
	}

	void setGameOver() {
		gameOver = true;
	}
	
	void pause() {
		paused = true;
	}
	
	void resume() {
		paused = false;
	}
	
	void killRows(LinkedList<Integer> fullRows) {
		board.killRows(fullRows);
	}
	
	public void drawNextPiece(Graphics2D graphics) {
		nextPiece.draw(graphics);
	}
	
	public void drawBoard(Graphics2D g2D) {
		if (isPaused()) {
    		Font baseFont   = g2D.getFont();
    		Color baseColor = g2D.getColor();
    		String str = "PAUSED";
    		g2D.setFont(TetrisFont.getTetrisFont(52f));
    		FontMetrics fm  = g2D.getFontMetrics();
    		int strWidth    = fm.stringWidth(str);
    		int strHeight   = fm.getHeight();
    		// draw the text corresponding to the level in white
    		g2D.setColor(Color.WHITE);
    		g2D.drawString(str, 
    		    (Board.SIZE.width - strWidth) / 2,
    		    (Board.SIZE.height + strHeight) / 2
    		);
    		// put back the default properties
    		g2D.setFont(baseFont);
    		g2D.setColor(baseColor);
			
		} else {
			board.draw(g2D);
			if (isActivePiece()) {
				currentPiece.drawOnBoard(g2D);
			}
		}
	}
	
}
