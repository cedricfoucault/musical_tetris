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
		setBackground(new Color(248, 248, 255));
		setDoubleBuffered(true);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		BoardPanel boardPane = new BoardPanel(state, 
								boardWidth, boardHeight, borderSize);
		GUIPanel guiPane = new GUIPanel(state, 180, 590, borderSize);
		
		add(boardPane);
		add(guiPane);
    }
}
