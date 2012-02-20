// import java.awt.event.*;
// // import java.awt.Canvas;
// // import java.awt.Component;
// // import java.awt.Graphics;
// // import java.awt.Graphics2D;
// import java.awt.*;
// import javax.swing.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Canvas {
	private BufferStrategy strategy;
	
	private boolean running;
	private final GameState state = new GameState(1);
	private long keyPressTime;
	private boolean rightPressed, leftPressed, rotatePressed, dropPressed;
	private boolean longKeyPress;
	private static final long LONG_PRESS_TIME = 200;
	private static final int RESOLUTION_HEIGHT = 600;
	private static final int RESOLUTION_WIDTH = 800;
	private static final int BOARD_FRAME_HEIGHT = 600;
	private static final int BOARD_FRAME_WIDTH = 300;
	
	Game() {
		running = true;
		rightPressed = false;
		leftPressed = false;
		rotatePressed = false;
		dropPressed = false;
		longKeyPress = false;
		final JFrame container = new JFrame("Tetris");
		// get hold the content of the frame and set up the resolution of the game
		final JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(RESOLUTION_WIDTH, RESOLUTION_HEIGHT));
		panel.setLayout(null);
		
		// setup our canvas size and put it into the content of the frame
		setBounds(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
		panel.add(this);
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);
		
		// finally make the window visible 
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		// add listeners to our canvas so we respond to keypressed and window shut down
		container.addKeyListener(new KeyInputHandler());
		container.addWindowListener(new WindowHandler());
		
		// request the focus so key events come to us
		requestFocus();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}
	
	private void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		long dTime;
		while (running) {
			// LOGIC PART
			dTime = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			// update the state of the game
			state.updateState(dTime);
			// update the piece movement
			// drop motion is triggered instantly
			if (dropPressed && 
			!(rightPressed || leftPressed || rotatePressed)) {
				state.updatePieceMotion(Move.DROP, dTime);
			}
			// horizontal motion is triggered only in case of long key press
			if (longKeyPress) {
				// if the long key press flag is true 
				// and a single button is pressed
				// update the piece movement accordingly
				if (rightPressed && 
					!(leftPressed || rotatePressed || dropPressed)) {
					state.updatePieceMotion(Move.RIGHT, dTime);
				} else if (leftPressed && 
					!(rightPressed || rotatePressed || dropPressed)) {
					state.updatePieceMotion(Move.LEFT, dTime);
				}
			} else {
				// if a key button has been pressed for enough time
				// set longKeyPress flag to true
				if ((rightPressed || leftPressed) 
					&& ((lastLoopTime - keyPressTime) >= LONG_PRESS_TIME)) {
						longKeyPress = true;
					}
			}
			
			// GRAPHICS PART
			// Get hold of a graphics context for the accelerated 
			// surface and blank it out
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
			// Draw the board
			final Image boardImage = createImage(BOARD_FRAME_WIDTH, BOARD_FRAME_HEIGHT);
			final Graphics2D boardGraphics = (Graphics2D) boardImage.getGraphics();
			drawBoardFrame(boardGraphics);
			(state.getBoard()).draw(boardGraphics, BOARD_FRAME_WIDTH, BOARD_FRAME_HEIGHT);
			(state.getCurrentPiece()).draw(boardGraphics, BOARD_FRAME_WIDTH, BOARD_FRAME_HEIGHT);
			g.drawImage(boardImage, 0, 0, BOARD_FRAME_WIDTH, BOARD_FRAME_HEIGHT, null);

			// finally, we've completed drawing so clear up the graphics
			// and flip the buffer over
			g.dispose();
			strategy.show();

			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	private void drawBoardFrame(Graphics2D g) {
		g.setColor(Color.white);
        g.drawRect(0, 0,  BOARD_FRAME_WIDTH - 1, BOARD_FRAME_HEIGHT - 1);
		g.setColor(Color.black);
        g.fillRect(0, 0,  BOARD_FRAME_WIDTH - 1, BOARD_FRAME_HEIGHT - 1);
	}
	
	private class KeyInputHandler extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
				if (state.canMovePiece(Move.LEFT)) {
					state.movePiece(Move.LEFT);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
				if (state.canMovePiece(Move.RIGHT)) {
					state.movePiece(Move.RIGHT);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				rotatePressed = true;
				if (state.canMovePiece(Move.ROTATE)) {
					state.movePiece(Move.ROTATE);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				dropPressed = true;
				if (state.canMovePiece(Move.DROP)) {
					state.movePiece(Move.DROP);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				// pausePressed = true;
				if (state.isPaused()) {
					state.resume();
				} else {
					state.pause();
				}
			}
			keyPressTime = System.currentTimeMillis();
		} 


		public void keyReleased(KeyEvent e) {			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				rotatePressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				dropPressed = false;
			}
			// if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// 	pausePressed = false;
			// }
			longKeyPress = false;
		}

		public void keyTyped(KeyEvent e) {
			// if we hit escape, then quit the game
			if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}
	}
	
	private class WindowHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e)
        {
            System.exit(0);
        }
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		g.gameLoop();
	}
}