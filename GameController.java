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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.util.concurrent.atomic.AtomicInteger;


public class GameController {
	private final GameState state;
	private long fallCountdown; // in millisecond
	private long motionCountdown;
	private static final long DROP_MOTION_DELAY = 45; // ~ 30 cells / s
	// private static final long HORIZONTAL_MOTION_DELAY = 150; // 10 cells/s
	private long keyPressTime;
	private boolean rightPressed, leftPressed, rotatePressed, dropPressed;
	private boolean longKeyPress;
	
	private final JFrame frame;
    // the content panel of the whole window
	private final JPanel container;
    // layout manager to switch between menu panel and game panel
    private final CardLayout paneSwitch;
    // the panel for the main menu
	private final MenuPanel menuPane;
    // the panel for when the game is running
	private final GamePanel gamePane;
	// the key listener used when the game is running
	private final KeyInputHandler gameKeyListener;
	// used to init the start level for the game
	private final AtomicInteger startLevel;
	// running is true while the game loop is running
	private boolean running;
	private static final Dimension RESOLUTION = new Dimension(900, 640);
	private static final Dimension BLOCK_SIZE = new Dimension(28, 28);
	private static final Dimension BOARD_FRAME_SIZE = 
		new Dimension(BLOCK_SIZE.width * 12, BLOCK_SIZE.height * 22);
	// private static final Dimension BOARD_FRAME_SIZE = new Dimension(300, 590);
	private static final int MARGIN = BLOCK_SIZE.width;
	
	GameController() {
		// init the state of the game
		startLevel = new AtomicInteger(1);
		state = new GameState(startLevel.get());
		initFallCountdown();
		initMotionCountdown();
		state.addBoardListener(new BoardController());
		state.addStateListener(new StateController());
		
		// init the sound
		GameSound.init();
		
		// Graphics
		// init the game panel and menu
		paneSwitch = new CardLayout();
		container  = new JPanel(paneSwitch);
		menuPane   = new MenuPanel(
		    new StartListener(), 
		    new ExitListener(), 
		    startLevel);
		gamePane   = new GamePanel(state,
			RESOLUTION.width, RESOLUTION.height,
			BOARD_FRAME_SIZE.width, BOARD_FRAME_SIZE.height, MARGIN);
		container.add(menuPane, "Menu");
        // paneSwitch.addLayoutComponent(menuPane, "Menu");
		container.add(gamePane, "Game");
        // paneSwitch.addLayoutComponent(menuPane, "Game");     
		// init the game window
		frame = new JFrame("Tetris");
		frame.setContentPane(container);
		frame.pack();
		frame.setResizable(false);
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension screenSize = toolkit.getScreenSize();
		frame.setLocation((screenSize.width - RESOLUTION.width)  / 2,
			(screenSize.height - RESOLUTION.height) / 2);
		
		// init the listeners
		// won't be used until the game starts
        gameKeyListener = new KeyInputHandler();
        // global listeners
		frame.setFocusable(true);
		frame.addWindowListener(new WindowHandler());
	}
	
	public void openWindow() {
        frame.setVisible(true);
	}
	
	public void displayMenuPanel() {
        // paneSwitch.show(container, "Menu");
        SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
                paneSwitch.show(container, "Menu");
	        }
	    });
        // paneSwitch.first(container);
	}
	
	public void displayGamePanel() {
        // paneSwitch.show(container, "Game");
        SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
                paneSwitch.show(container, "Game");
	        }
	    });
        // paneSwitch.last(container);
	}
	
	private void startGame() {
		running = true;
        // init the game state
		initState();
		// set the game listeners
		// use the game key listener
		frame.addKeyListener(gameKeyListener);
		// disable focus to be able to use the [TAB] key
		frame.setFocusTraversalKeysEnabled(false);
		// display the game panel
		displayGamePanel();
	}
	
	private void stopGame() {
	    // set back the menu listeners
	    frame.removeKeyListener(gameKeyListener);
	    frame.setFocusTraversalKeysEnabled(false);
	    // display the menu panel
        displayMenuPanel();
	}
	
	private void initState() {
		state.init(startLevel.get());
	}
	
	private void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		long dTime;
		int delay = 10;
		while (running) {
		    // compute the time that has gone since the last frame
		    dTime = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
		    
		    // UPDATE STATE
			if (state.isOver()) {
		    // go out of the loop if the game is over
				running = false;
				System.out.println("gameover");
				try {
					System.out.flush();
				} catch (Exception e) {}
				break;
			}
			
			if (!state.isPaused()) {
			// update the state of the game if it is not paused
				updateState(dTime);
				handleInputMotion(dTime, lastLoopTime);
			}
			// UPDATE GRAPHICS
			updateGraphics(); // == 
			// Finally pause for a bit
			try { 
				Thread.sleep(delay); 
			} catch (Exception e) {}
		}
		
        // stopGame();
        //         // return to the main menu when the game is over 
        // displayMenuPanel();
	}
	
	private void updateGraphics() {
        frame.repaint();
        // SwingUtilities.invokeLater(new Runnable() {
        //     public void run() {
        //         frame.repaint();
        //     }
        // });
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
	
    public class StartListener implements ActionListener {
        StartListener() {}
        public void actionPerformed(ActionEvent e) {
            SwingWorker controllerThread = new SwingWorker<Integer, Void>() {
                @Override
                public Integer doInBackground() {
                    startGame();
                    gameLoop();
                    return (new Integer(0));
                }
                
                @Override
                public void done() {
                    stopGame();
                }
            };
            controllerThread.execute();
        }
    }
	
    // public class StartListener implements ActionListener {
    //  StartListener() {}
    //  public void actionPerformed(ActionEvent e) {
    //             startSignal.countDown();
    //  }
    // }
	
	public class ExitListener implements ActionListener {
		ExitListener() {}
		public void actionPerformed(ActionEvent e) {
            // System.exit(0);
            frame.dispose();
		}
	}
	
	public class BoardController implements BoardListener {
		BoardController() {}
		public void fullRowDetected(FullRowEvent e) {
			state.killRows(e.fullRows);
			GameSound.playLineClear(e.nRows);
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
				running = false;
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
        g.openWindow();
	}
}

