import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

class BoardPanel extends JPanel {
	private final GameState state;
	private int width, height;
	private int margin;
	
	public BoardPanel(GameState state, int width, int height, int margin) {
		super();
		// set properties
		this.state = state;
		this.width = width;
		this.height = height;
		this.margin = margin;
		Dimension size = new Dimension(width, height);
		setMaximumSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		initSizes();
		setBackground(Color.BLACK);
		setAlignmentX(CENTER_ALIGNMENT);
		// create and set the panel border (gray blocks)
		Image block_image =
		    BlockSprites.lightgray_block.getScaledInstance(
		        margin,
			    margin,	
			    Image.SCALE_DEFAULT);
		ImageIcon icon = new ImageIcon(block_image);
		setBorder(BorderFactory.createMatteBorder(margin,
			margin, margin, margin, icon));
    }
	
	public BoardPanel(GameState state, Dimension size, int margin) {
		this(state, size.width, size.height, margin);
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);		
		Graphics2D boardGraph = (Graphics2D)
		    g.create(margin, margin, Board.SIZE.width, Board.SIZE.height);
		state.drawBoard(boardGraph);
    }

	private void initSizes() {
	    // init the size of the board and of the block
		Board.setSize(new Dimension(width - 2 * margin, height - 2 * margin));
		int block_width = Board.SIZE.width / Board.WIDTH;
		int block_height = Board.SIZE.height / Board.HEIGHT;
		Block.setSize(new Dimension(block_width, block_height));
	}
}
