public class GameState {
	public int level;
	public int score;
	private long fallCountdown; // in millisecond
	private long motionCountdown;
	public boolean gameOver;
	private boolean paused;
	private Board board;
	private boolean isActivePiece;
	private Piece currentPiece;
	private Piece nextPiece;
	private static final long DROP_MOTION_DELAY = 33; // ~ 30 cells / s
	private static final long HORIZONTAL_MOTION_DELAY = 100; // 10 cells / s
	
	GameState(int level) {
		gameOver = false;
		paused = false;
	}
	
	public void updateState(long dTime) {
		if (isOver() || isPaused()) {
			return;
		}
		updateFallCountdown(deltaTime);
		if (!isFallTimeOut()) {
			return;
		}
		if (isActivePiece()) {
			if (canFallPiece()) {
				moveDownPiece();
			} else {
				mergePiece();
			}
		} else {
			if (canSpawnPiece()) {
				spawnPiece();
			} else {
				setGameOver();
			}
		}
		resetFallCountdown();
	}
	
	public void updatePieceMotion(long dTime, Move movetype) {
		if (isOver() || isPaused() || !isActivePiece()) {
			return;
		}
		updateMotionCountdown(dTime);
		if (!isMoveTimeOut()) {
			return;
		}
		if (canMovePiece(movetype)) {
			movePiece(movetype);
		}
		resetMotionCountdown(movetype);
		
	}
	
	boolean isOver() {
		return gameOver;
	}
	boolean isPaused() {
		return paused;
	}
	void updateFallCountdown(double dTime) {
		fallCountdown -= dTime;
	}
	boolean isFallTimeOut() {
		return fallCountdown <= 0;
	}
	void resetFallCountdown() {
		fallCountdown = 50 * (11 - level);
	}
	
	void resetMotionCountdown(Move movetype) {
		switch(movetype) {
			case DROP: {
				motionCountdown = DROP_MOTION_DELAY;	
			}
			default: {
				motionCountdown = HORIZONTAL_MOTION_DELAY;
			}
		}
	}
	
	boolean isActivePiece() {
		return isActivePiece;
	};
	
	boolean canFallPiece() {
		canMovePiece(DROP);
	}
	void moveDownPiece() {
		movePiece(DROP);
	}
	void mergePiece() {
		isActivePiece = false;
		board.merge(currentPiece);
	}
	
	boolean canSpawnPiece() {
		return board.willCollide(nextPiece);
		// return board.isFull(Piece.START_X, Piece.START_Y);
	}
	
	void spawnPiece() {
		currentPiece = nextPiece;
		nextPiece = Piece.getRandomPiece();
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
		Piece movedPiece = (currentPiece.copy()).move(movetype);
		return !(board.willCollide(movedPiece));
	}
	void movePiece(Move movetype) {
		currentPiece.move(movetype);
	}
}
