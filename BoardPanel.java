import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

class BoardPanel extends JPanel {
	private final GameState state;
	private int width, height;
	private int margin;
	
	public BoardPanel(GameState state, int width, int height, int margin) {
		super();
		this.state = state;
		this.width = width;
		this.height = height;
		this.margin = margin;
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createLineBorder(new Color(119, 136, 153), margin));
		// setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		setBackground(new Color(230, 236, 255));
		initSizes();
    }
	
	public BoardPanel(GameState state, Dimension size, int margin) {
		this(state, size.width, size.height, margin);
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);		
		Graphics2D boardGraph = (Graphics2D) g.create(margin, margin, Board.SIZE.width, Board.SIZE.height);
		state.drawBoard(boardGraph);
    }

	private void initSizes() {
		Board.setSize(new Dimension(width - 2 * margin, height - 2 * margin));
		int block_width = Board.SIZE.width / Board.WIDTH;
		int block_height = Board.SIZE.height / Board.HEIGHT;
		Block.setSize(new Dimension(block_width, block_height));
	}
}

// class GameBoardCanvas extends Canvas {
// 	private BufferStrategy buffer;
// 	private final GameState state;
// 	private final int width;
// 	private final int height;
// 	
// 	GameBoardCanvas(JPanel container, GameState state, int width, int height) {
// 		this.state = state;
// 		this.height = height;
// 		this.width = width;
// 
// 		setIgnoreRepaint(true);
// 		setBounds(0, 0, width - 1, height - 1);
// 		container.add(this, BorderLayout.CENTER);
// 		
// 		createBufferStrategy(2);
// 		buffer = getBufferStrategy();
// 	}
// 	
// 	public void render() {
// 		// Get hold of a graphics context for the accelerated surface
// 		Graphics2D graphics = (Graphics2D) buffer.getDrawGraphics();
// 		// Paint the game board on it
// 		try {
// 			setBackground(graphics, Color.black);
// 			state.drawBoard(graphics, width, height);
// 		} finally {
// 			if (graphics != null) {
// 				graphics.dispose();
// 			}
// 		}
// 		// Show the painted graphics
// 		buffer.show();
// 	}
// 	
// 	private void setBackground(Graphics2D graphics, Color color)  {
// 		graphics.setColor(Color.black);
// 		graphics.fillRect(0, 0, WIDTH - 1, HEIGHT - 1);
// 	}
// 	
// }