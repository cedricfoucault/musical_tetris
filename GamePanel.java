import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

class GamePanel extends JPanel {
	private final GameState state;
	private int width, height;
	private int borderSize;

	public GamePanel(GameState state, int width, int height, 
				int boardWidth, int boardHeight, int borderSize) {
		super();
		// init the panel parameters
		this.state = state;
		this.width = width;
		this.height = height;
		this.borderSize = borderSize;
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		// create the subpanels: board panel and side panel
		BoardPanel boardPane = new BoardPanel(state, 
								boardWidth, boardHeight, borderSize);
		SidePanel guiPane = new SidePanel(
		    state, Block.SIZE.width * 6, Block.SIZE.height * 22, borderSize);
		
		// layout the panels properly
		add(Box.createHorizontalGlue());
		add(boardPane);
		add(Box.createRigidArea(new Dimension(Block.SIZE.width, height)));
		add(guiPane);
		add(Box.createHorizontalGlue());
    }
}
