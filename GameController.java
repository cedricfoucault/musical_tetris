import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;


public class GameController {
	private final GameState state;
	private long fallCountdown; // in millisecond
	private long motionCountdown;
	private static final long DROP_MOTION_DELAY = 45; // ~ 30 cells / s
	// private static final long HORIZONTAL_MOTION_DELAY = 150; // 10 cells/s
	private long keyPressTime;
	private boolean rightPressed, leftPressed, rotatePressed, dropPressed;
	private boolean longKeyPress;
	// private static final long LONG_PRESS_TIME = 2000000;
	
	private final JFrame frame;
	private boolean running;
	private static final Dimension RESOLUTION = new Dimension(900, 640);
	private static final Dimension BLOCK_SIZE = new Dimension(28, 28);
	private static final Dimension BOARD_FRAME_SIZE = 
		new Dimension(BLOCK_SIZE.width * 12, BLOCK_SIZE.height * 22);
	// private static final Dimension BOARD_FRAME_SIZE = new Dimension(300, 590);
	private static final int MARGIN = BLOCK_SIZE.width;
	
	GameController() {
		running = true;
		
		// init the state of the game
		state = new GameState(1);
		initFallCountdown();
		initMotionCountdown();
		
		// init the sound
		GameSound.init();
		
		// Graphics
		// init the game panel
		GamePanel panel = new GamePanel(state,
			RESOLUTION.width, RESOLUTION.height,
			BOARD_FRAME_SIZE.width, BOARD_FRAME_SIZE.height, MARGIN);
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension screenSize = toolkit.getScreenSize();
		// open the game window
		frame = new JFrame("Tetris");
		frame.setContentPane(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocation((screenSize.width - RESOLUTION.width)  / 2,
			(screenSize.height - RESOLUTION.height) / 2);
		frame.setVisible(true);
		
		// init the listeners
		frame.setFocusable(true);
		frame.addKeyListener(new KeyInputHandler());
		frame.addWindowListener(new WindowHandler());
		frame.setFocusTraversalKeysEnabled(false);
		state.addBoardListener(new BoardController());
		state.addStateListener(new StateController());
	}
	
	private void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		long dTime;
		while (running) {
			if (state.isOver()) {
				running = false;
				System.out.print("gameover");
				try {
					System.out.flush();
				} catch (Exception e) {}
				break;
			}
			// LOGIC PART
			dTime = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			// update the state of the game
			if (!state.isPaused()) {
				updateState(dTime);
				handleInputMotion(dTime, lastLoopTime);
			}
			// GRAPHICS
			updateGraphics();
			// finally pause for a bit.
			try { 
				Thread.sleep(10); 
			} catch (Exception e) {}
		}
	}
	
	private void updateGraphics() {
		frame.repaint();
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
		GameSound.playFall();
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
			// GameSound.playLand();
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
				if (dropPressed == false) {
					dropPressed = true;
					GameSound.playDrop(state.getLevel());
				}
			} else if (e.getKeyCode() == KeyEvent.VK_TAB){
				// hard drop
				GameSound.playHardDrop();
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
				GameSound.stopDrop();
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
	
	public static void main(String[] args) {
		GameController g = new GameController();
		g.gameLoop();
	}
}

