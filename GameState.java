public class GameState {
	public int level;
	public int score;
	public boolean gameOver;
	private boolean paused;
	private Board board;
	private boolean isActivePiece;
	private Piece currentPiece;
	private Piece nextPiece;
	
	GameState(int level) {
		this.level = level;
		score = 0;
		gameOver = false;
		paused = false;
		board = new Board();
		isActivePiece = false;
		// currentPiece = Piece.newRandomPiece();
		nextPiece = Piece.newRandomPiece();
		// resetFallCountdown();
		// fallCountdown = 0;
	}
	
	// GETTERS
	Board getBoard() {
		return board;
	}
	Piece getCurrentPiece() {
		return currentPiece;
	}
	
	int getLevel() {
		return level;
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
	
	boolean canMovePiece(Move movetype) {
		Piece movedPiece = (currentPiece.copy());
		movedPiece.move(movetype);
		return !(board.willCollide(movedPiece));
	}
	void movePiece(Move movetype) {
		currentPiece.move(movetype);
	}
}
