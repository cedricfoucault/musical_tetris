import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
	private final JFrame frame;
	private final JPanel panel;
	// private final BoardPanel boardPane;
	
	private boolean running;
	private final GameState state;
	private final GameController controller;
	private static final int RESOLUTION_HEIGHT = 600;
	private static final int RESOLUTION_WIDTH = 500;
	private static final Dimension RESOLUTION = new Dimension(800, 620);
	private static final Dimension BOARD_FRAME_SIZE = new Dimension(300, 590);
	// private static final int X_MARGIN = 10;
	// 	private static final int Y_MARGIN = 10;
	private static final int MARGIN = 5;
	
	Game() {
		running = true;
		// init the state of the game
		state = new GameState(1);
		controller = new GameController(state);
		// open the game window
		frame = new JFrame("Tetris");
		panel = new JPanel();
		// and set up the resolution of the game
		panel.setPreferredSize(RESOLUTION);
		panel.setBackground(new Color(248, 248, 255));
		panel.setDoubleBuffered(true); 
		// panel.setBackground(Color.white);
		// panel.setLayout(null);
		// finally make the window visible
		frame.setContentPane(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		// add listeners to our canvas so we respond to keypressed and window shut down
		frame.addKeyListener(controller.getInputHandler());
		frame.addWindowListener(controller.getWindowHandler());
		// add our board canvas to the game
		BoardPanel boardPane = new BoardPanel(state, BOARD_FRAME_SIZE, MARGIN);
		panel.add(boardPane);
		GUIPanel guiPane = new GUIPanel(state, 180, 590, MARGIN);
		panel.add(guiPane);
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
			
			updateGraphics();

			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
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