public class Game {
	private boolean running;
	private GameState state;
	private long keyPressTime;
	private boolean rightPressed, leftPressed, rotatePressed, downPressed;
	private boolean longKeyPress;
	private static final long LONG_PRESS_TIME = 200;
	private static final int RESOLUTION_HEIGHT = 800;
	private static final int RESOLUTION_WIDTH = 600;
	
	Game() {
		addKeyListener(new KeyInputHandler());
	}
	
	private void gameloop() {
		long lastLoopTime = System.currentTimeMillis();
		long dTime;
		while (running) {
			// LOGIC PART
			delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			// update the state of the game
			state.update(dTime);
			// update the piece movement
			// drop motion is triggered instantly
			if (downPressed && 
			!(rightPressed || leftPressed || rotatePressed)) {
				state.updatePieceMotion(DOWN, delta);
			}
			// horizontal motion is triggered only in case of long key press
			if (longKeyPress) {
				// if the long key press flag is true 
				// and a single button is pressed
				// update the piece movement accordingly
				if (rightPressed && 
					!(leftPressed || rotatePressed || downPressed)) {
					state.updatePieceMotion(RIGHT, delta);
				} else if (leftPressed && 
					!(rightPressed || rotatePressed || downPressed)) {
					state.updatePieceMotion(LEFT, delta);
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
			(state.getBoard()).draw(g);
			(state.getCurrentPiece()).draw(g);

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
	
	private class KeyInputHandler extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
				if (state.canMovePiece(LEFT)) {
					state.movePiece(LEFT);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
				if (state.canMovePiece(RIGHT)) {
					state.movePiece(RIGHT);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				rotatePressed = true;
				if (state.canMovePiece(ROTATE)) {
					state.movePiece(ROTATE);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
				if (state.canMovePiece(DROP)) {
					state.movePiece(DROP);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				pausePressed = true;
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
				downPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				pausePressed = false;
			}
			longKeyPress = false;
		}

		public void keyTyped(KeyEvent e) {
			// if we hit escape, then quit the game
			if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}
	}
}