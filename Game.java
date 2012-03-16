import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Canvas {
	private final JFrame frame;
	private final JPanel panel;
	private BufferStrategy buffer;
	
	private boolean running;
	private final GameState state = new GameState(1);
	private final GameController controller = new GameController(state);
	// private static final long LONG_PRESS_TIME = 200;
	private static final int RESOLUTION_HEIGHT = 600;
	private static final int RESOLUTION_WIDTH = 500;
	private static final Dimension RESOLUTION = new Dimension(500, 600);
	// private static final int BOARD_FRAME_SIZE.height = 600;
	// private static final int BOARD_FRAME_SIZE.width = 300;
	private static final Dimension BOARD_FRAME_SIZE = new Dimension(300, 600);
	private static final int X_MARGIN = 10;
	private static final int Y_MARGIN = 10;
	
	Game() {
		running = true;
		// open the game window
		frame = new JFrame("Tetris");
		// get hold of the content of the frame
		panel  = (JPanel) frame.getContentPane();
		// and set up the resolution of the game
		panel.setPreferredSize(RESOLUTION);
		panel.setLayout(null);
		
		// setup our canvas size and put it into the content of the frame
		setBounds(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
		panel.add(this);
		
		// set the board and block sizes
		initSizes();
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);
		
		// finally make the window visible 
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		// add listeners to our canvas so we respond to keypressed and window shut down
		frame.addKeyListener(controller.getInputHandler());
		frame.addWindowListener(controller.getWindowHandler());

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		buffer = getBufferStrategy();
	}
	
	private void initSizes() {
		Board.setSize(new Dimension(BOARD_FRAME_SIZE.width - (2 * X_MARGIN), BOARD_FRAME_SIZE.height - (2 * Y_MARGIN)));
		int block_width = Board.SIZE.width / Board.WIDTH;
		int block_height = Board.SIZE.height / Board.HEIGHT;
		Block.setSize(new Dimension(block_width, block_height));
	}
	
	private void updateGraphics() {
		// GRAPHICS PART
		// Get hold of a graphics context for the accelerated 
		// surface and blank it out
		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		try {
			g.setColor(Color.black);
			g.fillRect(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
			// Draw the board frame
			drawBoardFrame(g);
			final Image boardImage = createImage(BOARD_FRAME_SIZE.width - (2 * X_MARGIN), BOARD_FRAME_SIZE.height - (2 * Y_MARGIN));
			final Graphics2D boardGraphics = (Graphics2D) boardImage.getGraphics();
			(state.getBoard()).draw(boardGraphics, BOARD_FRAME_SIZE.width - (2 * X_MARGIN), BOARD_FRAME_SIZE.height - (2 * Y_MARGIN));
			if (state.isActivePiece()) {
				(state.getCurrentPiece()).draw(boardGraphics, BOARD_FRAME_SIZE.width - (2 * X_MARGIN), BOARD_FRAME_SIZE.height - (2 * Y_MARGIN));
			}
			g.drawImage(boardImage, X_MARGIN, Y_MARGIN, BOARD_FRAME_SIZE.width - (2 * X_MARGIN), BOARD_FRAME_SIZE.height - (2 * Y_MARGIN), null);

			// finally, we've completed drawing so clear up the graphics
			// and flip the buffer over
			// g.dispose();
		} finally {
			if (g != null) {
				g.dispose();
			}
		}
		buffer.show();
	}
	
	private void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		long dTime;
		while (running) {
			// LOGIC PART
			dTime = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			// update the state of the game
			if (!state.isOver() && !state.isPaused()) {
				controller.updateState(dTime);
				controller.handleInputMotion(dTime, lastLoopTime);
			} else {
				System.out.println("gameover");
			}
			
			updateGraphics();

			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	private void drawBoardFrame(Graphics2D g) {
		// BasicStroke stroke = new BasicStroke(10);
		// g.setStroke(stroke);
		g.setColor(Color.white);
        g.fillRect(0, 0, X_MARGIN, BOARD_FRAME_SIZE.height);
		g.fillRect(BOARD_FRAME_SIZE.width - X_MARGIN, 0, BOARD_FRAME_SIZE.width, BOARD_FRAME_SIZE.height);
		g.fillRect(0, 0, BOARD_FRAME_SIZE.width, Y_MARGIN);
		g.fillRect(0, BOARD_FRAME_SIZE.height - Y_MARGIN, BOARD_FRAME_SIZE.width, BOARD_FRAME_SIZE.height);
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		g.gameLoop();
	}
}