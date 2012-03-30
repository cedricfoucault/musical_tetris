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

public class Game {
	private final JFrame frame;
	
	private boolean running;
	private final GameState state;
	private final GameController controller;
	// private static final int RESOLUTION_HEIGHT = 600;
	// private static final int RESOLUTION_WIDTH = 500;
	private static final Dimension RESOLUTION = new Dimension(900, 620);
	private static final Dimension BOARD_FRAME_SIZE = new Dimension(300, 590);
	// private static final int X_MARGIN = 10;
	// 	private static final int Y_MARGIN = 10;
	private static final int MARGIN = 5;
	
	Game() {
		running = true;
		// init the state of the game
		state = new GameState(1);
		controller = new GameController(state);
		GameSound.init();
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
		// add listeners to our canvas so we respond to keypressed and window shut down
		frame.setFocusable(true);
		frame.addKeyListener(controller.new KeyInputHandler());
		frame.addWindowListener(controller.new WindowHandler());
		frame.setFocusTraversalKeysEnabled(false);
	}
	
	private void updateGraphics() {
		frame.repaint();
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
				controller.updateState(dTime);
				controller.handleInputMotion(dTime, lastLoopTime);
			}
			// GRAPHICS
			updateGraphics();
			// finally pause for a bit.
			try { 
				Thread.sleep(10); 
			} catch (Exception e) {}
		}
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		g.gameLoop();
	}
}
