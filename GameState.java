import java.awt.Graphics2D;
import java.util.LinkedList;

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
	
	
	GameState(int level) {
		this.level = level;
		score = 0;
		linesCompleted = 0;
		gameOver = false;
		paused = false;
		board = new Board();
		isActivePiece = false;
		// currentPiece = Piece.newRandomPiece();
		nextPiece = Piece.newRandomPiece();
		// resetFallCountdown();
		// fallCountdown = 0;
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
		return canMovePiece(Move.DROP);
	}
	void moveDownPiece() {
		movePiece(Move.DROP);
	}
	void hardDropPiece() {
		while (canMovePiece(Move.DROP)) {
			movePiece(Move.DROP);
		}
	}
	
	boolean canMovePiece(Move movetype) {
		if (!isPaused() && !isOver()) {
			Piece movedPiece = (currentPiece.copy());
			movedPiece.move(movetype);
			return !(board.willCollide(movedPiece));
		} else {
			return false;
		}
	}
	
	void movePiece(Move movetype) {
		if (movetype == Move.DROP) {
			score++;
		}
		currentPiece.move(movetype);
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
	
	public void drawBoard(Graphics2D graphics) {
		board.draw(graphics);
		if (isActivePiece()) {
			currentPiece.drawOnBoard(graphics);
		}
		if (isPaused()) {
			graphics.drawString("Paused", Board.SIZE.width / 2 - 25, Board.SIZE.height / 2 - 25);
		}
	}
	
}
