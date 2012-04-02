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
		this.state = state;
		this.width = width;
		this.height = height;
		this.borderSize = borderSize;
		setPreferredSize(new Dimension(width, height));
		// setBackground(new Color(0, 0, 255));
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		BoardPanel boardPane = new BoardPanel(state, 
								boardWidth, boardHeight, borderSize);
		GUIPanel guiPane = new GUIPanel(state, Block.SIZE.width * 6, Block.SIZE.height * 22, borderSize);
		
		add(Box.createHorizontalGlue());
		add(boardPane);
		add(Box.createRigidArea(new Dimension(Block.SIZE.width, height)));
		add(guiPane);
		add(Box.createHorizontalGlue());
    }
}
