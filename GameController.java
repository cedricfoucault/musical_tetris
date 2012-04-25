import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;
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

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import java.util.concurrent.atomic.AtomicInteger;


public class GameController {
    // the state of the current game
	private final GameState state;
	// countdown used for the fall of the active piece
	// (if countdown is < 0 then the piece drop down 1 row)
	private long fallCountdown;
	// countdown used for the accelerated fall in case of soft drop
	private long motionCountdown;
	// delay for the soft drop (motionCountdown will be reset to this value)
	private static final long DROP_MOTION_DELAY = 50;
	// used to know the time at which the user pressed the last key
	private long keyPressTime;
	// boolean values used to know which keys are currently pressed
	private boolean rightPressed, leftPressed, rotatePressed, dropPressed;
	
	// the main application for the whole game
    private final TetrisApplication mainApplication;
    // the content panel for the whole application
	private final JPanel contentPane;
    // layout manager to switch between menu panel and game panel
    private final CardLayout paneSwitch;
    // the panel for the main menu
	private final MenuPanel menuPane;
    // the panel for when a game is running
	private final GamePanel gamePane;
	// the key listener for when a game is running
	private final KeyInputHandler gameKeyListener;
	// used to init the start level for a new game
	private int startLevel;
	// running is true while the game loop is running
	private boolean running;
	// the timer used to iterate the game loop indefinitely
	private Timer timer;
	// the delay between 2 frames used for the timer
	private final int timerDelay = 15;
	// used to calculate the actual delta time between 2 frames
	private long lastLoopTime;
	
	// the dimension of the screen
	public static final Dimension RESOLUTION = new Dimension(900, 640);
	// the size of a Tetris block
	private static final Dimension BLOCK_SIZE = new Dimension(28, 28);
	// the size for the BoardPanel including the borders
	private static final Dimension BOARD_FRAME_SIZE = 
		new Dimension(BLOCK_SIZE.width * 12, BLOCK_SIZE.height * 22);
	// the width of the BoardPanel borders
	private static final int MARGIN = BLOCK_SIZE.width;
	
	GameController(TetrisApplication mainApplication) {
		// init the state of the game
		this.mainApplication = mainApplication;
		startLevel = 1;
		state      = new GameState(startLevel);
		initFallCountdown();
		initMotionCountdown();
		
		// init the sound
		GameSound.init();
		
		// Graphics
		// init the game panel and menu
		paneSwitch = new CardLayout();
		contentPane  = new JPanel(paneSwitch);
		menuPane   = new MenuPanel(new MenuListener());
		gamePane   = new GamePanel(state,
			RESOLUTION.width, RESOLUTION.height,
			BOARD_FRAME_SIZE.width, BOARD_FRAME_SIZE.height, MARGIN);
			
        contentPane.setBackground(Color.BLACK);
        contentPane.setDoubleBuffered(true);
		contentPane.add(menuPane, "Menu");
		contentPane.add(gamePane, "Game"); 
		
		// init the listeners
        gamePane.setFocusable(true);
        gamePane.addComponentListener(new ComponentAdapter() {
                     public void componentShown(ComponentEvent cEvt) {
                        Component src = (Component) cEvt.getSource();
                        src.requestFocusInWindow();
                     }
        });
        menuPane.setFocusable(true);
        menuPane.addComponentListener(new ComponentAdapter() {
             public void componentShown(ComponentEvent cEvt) {
                Component src = (Component) cEvt.getSource();
                src.requestFocusInWindow();
             }
        });
        gameKeyListener = new KeyInputHandler();
        gamePane.addKeyListener(gameKeyListener);
        gamePane.setFocusTraversalKeysEnabled(false);
        state.addBoardListener(new BoardController());
		state.addStateListener(new StateController());
		
		// display the menu
		displayMenuPanel();
	}
	
	public JPanel getContentPane() {
	    return contentPane;
	}

	public void displayMenuPanel() {
        SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
                paneSwitch.show(contentPane, "Menu");
                // menuPane.requestFocusInWindow();
	        }
	    });
	}
	
	public void displayGamePanel() {
        SwingUtilities.invokeLater(new Runnable() {
	        public void run() {	            
                paneSwitch.show(contentPane, "Game");
                // gamePane.requestFocusInWindow();
	        }
	    });
	}
	
	private void startGame() {
		running = true;
        // init the game state
		initState();
		// display the game panel
		displayGamePanel();
	}
	
	private void startGameLoop() {
	    // init the timer for the game loop and start it
        timer = new Timer(timerDelay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameLoopIteration();
            }
        });
        lastLoopTime = System.currentTimeMillis();
        timer.start();
	}
	
	private void exit() {
	    mainApplication.exit();
	}
	
	private void stopGame() {
	    // display the menu panel
        displayMenuPanel();
	}
	
	private void initState() {
		state.init(startLevel);
	}
	
	private void gameLoopIteration() {
	    // compute the time that has gone since the last frame
	    long dTime = System.currentTimeMillis() - lastLoopTime;
		lastLoopTime = System.currentTimeMillis();
	    
	    // UPDATE STATE
		if (state.isOver() || running == false) {
	    // go out of the loop if the game is over 
	    // or running has been set to false
			running = false;
			timer.stop();
			return;
		}
		if (!state.isPaused()) {
		// update the state of the game if it is not paused
			updateState(dTime);
        // handle the soft drop movement
			handleInputMotion(dTime, lastLoopTime);
		}
		// UPDATE GRAPHICS
		updateGraphics();
	}
	
	private void updateGraphics() {
        // contentPane.repaint();
        gamePane.repaint();
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
	
	public void updatePieceMotion(MoveType movetype, long dTime) {
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
			updatePieceMotion(MoveType.DROP, dTime);
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
	
	void resetMotionCountdown(MoveType movetype) {
		switch(movetype) {
			case DROP: {
				motionCountdown = DROP_MOTION_DELAY;
				break;	
			}
		}
	}
    
	public class MenuListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	        String command = e.getActionCommand();
	        if (command.equals("EXIT")) {
	            mainApplication.exit();
	        } else {
	            // set the start level for the new game
	            startLevel = Integer.parseInt(command);
	            startGame();
	            startGameLoop();
	        }
	    }
	}
	
	public class BoardController implements BoardListener {
		public void fullRowDetected(FullRowEvent e) {
			state.killRows(e.fullRows);
			GameSound.playLineClear(e.nRows);
			state.incLinesCompleted(e.nRows);
			state.incScore(e.nRows);
		}
		public void land() {
		}		
	}
	
	public class StateController implements StateListener {
		public void levelUp() {
			GameSound.playLevelUp();
		}
	}
	
	public class KeyInputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				// left move
				leftPressed = true;
				if (state.canMovePiece(MoveType.LEFT)) {
					state.movePiece(MoveType.LEFT);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				// right move
				rightPressed = true;
				if (state.canMovePiece(MoveType.RIGHT)) {
					state.movePiece(MoveType.RIGHT);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				// rotate
				rotatePressed = true;
				if (state.canMovePiece(MoveType.ROTATE)) {
					state.movePiece(MoveType.ROTATE);
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
				running = false;
				stopGame();
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
		}
	}
}

