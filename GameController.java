import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class GameController {
	private GameState state;
	private KeyInputHandler keyHandler;
	private WindowHandler windowHandler;
	private long fallCountdown; // in millisecond
	private long motionCountdown;
	private static final long DROP_MOTION_DELAY = 35; // ~ 30 cells / s
	// private static final long HORIZONTAL_MOTION_DELAY = 150; // 10 cells/s
	private long keyPressTime;
	private boolean rightPressed, leftPressed, rotatePressed, dropPressed;
	private boolean longKeyPress;
	// private static final long LONG_PRESS_TIME = 2000000;
	
	GameController(GameState state) {
		this.state = state;
		state.addBoardListener(new BoardController());
		state.addStateListener(new StateController());
		keyHandler = new KeyInputHandler();
		windowHandler = new WindowHandler();
		initFallCountdown();
		initMotionCountdown();
	}
	
	public KeyInputHandler getInputHandler() {
		return keyHandler;
	}
	
	public WindowHandler getWindowHandler(){
		return windowHandler;
	}
	
	public void updateState(long dTime) {
		updateFallCountdown(dTime);
		if (!isFallTimeOut()) {
			return;
		}
		if (state.isActivePiece()) {
			if (state.canFallPiece()) {
				state.moveDownPiece();
			} else {
				state.mergePiece();
			}
		} else {
			if (state.canSpawnPiece()) {
				state.spawnPiece();
			} else {
				state.setGameOver();
			}
		}
		resetFallCountdown();
	}
	
	public void updatePieceMotion(Move movetype, long dTime) {
		updateMotionCountdown(dTime);
		if (!isMotionTimeOut()) {
			return;
		}
		if (state.canMovePiece(movetype)) {
			state.movePiece(movetype);
		}
		resetMotionCountdown(movetype);	
	}
	
	public void handleInputMotion(long dTime, long lastLoopTime) {
		// update the piece movement
		// drop motion is triggered instantly
		if (dropPressed && 
		!(rightPressed || leftPressed || rotatePressed)) {
			updatePieceMotion(Move.DROP, dTime);
		}
	}
	
	private void initMotionCountdown() {
		motionCountdown = 0;
	}
	
	private void initFallCountdown() {
		fallCountdown = 0;
	}
	
	void updateFallCountdown(double dTime) {
		fallCountdown -= dTime;
	}
	void updateMotionCountdown(double dTime) {
		motionCountdown -= dTime;
	}
	boolean isFallTimeOut() {
		return fallCountdown <= 0.;
	}
	boolean isMotionTimeOut() {
		return motionCountdown <= 0.;
	}
	
	void resetFallCountdown() {
		fallCountdown = 50 * (11 - state.getLevel());
	}
	
	void resetMotionCountdown(Move movetype) {
		switch(movetype) {
			case DROP: {
				motionCountdown = DROP_MOTION_DELAY;
				break;	
			}
		}
	}
	
	public class BoardController implements BoardListener {
		BoardController() {}
		public void fullRowDetected(FullRowEvent e) {
			state.killRows(e.fullRows);
			GameSound.playLineClear();
			state.incLinesCompleted(e.nRows);
			state.incScore(e.nRows);
		}
		public void land() {
			GameSound.playLand();
		}		
	}
	
	public class StateController implements StateListener {
		StateController(){}
		public void levelUp() {
			GameSound.playLevelUp();
		}
	}
	
	public class KeyInputHandler extends KeyAdapter {	
		KeyInputHandler() {}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				// left move
				leftPressed = true;
				if (state.canMovePiece(Move.LEFT)) {
					state.movePiece(Move.LEFT);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				// right move
				rightPressed = true;
				if (state.canMovePiece(Move.RIGHT)) {
					state.movePiece(Move.RIGHT);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				// rotate
				rotatePressed = true;
				if (state.canMovePiece(Move.ROTATE)) {
					state.movePiece(Move.ROTATE);
					GameSound.playRotate();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				// soft drop
				dropPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_TAB){
				// hard drop
				state.hardDropPiece();
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				// pause
				if (state.isPaused()) {
					state.resume();
				} else {
					state.pause();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				// exit
				System.exit(0);
			}
			keyPressTime = e.getWhen();
		} 


		public void keyReleased(KeyEvent e) {			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				rotatePressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				dropPressed = false;
			}
			longKeyPress = false;
		}
	}
	
	public class WindowHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e)
        {
            System.exit(0);
        }
	}
}
