import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.*;
import java.io.*;
import java.awt.font.*;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

public class GUIPanel extends JPanel {
	private final GameState state;
	private int width, height;
	
	public GUIPanel(GameState state, int width, int height, int borderSize) {
		super();
		this.state = state;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		// setBorder(BorderFactory.createLineBorder(new Color(119, 136, 153), borderSize));
		setBackground(new Color(248, 248, 255));
		// setBackground(new Color(248, 248, 255));
		// this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		NextPiecePanel nextPiecePanel = new NextPiecePanel(state, (width * 7) / 8, (width * 7) / 8, borderSize);
		this.add(nextPiecePanel);
		ScorePanel scorePane = new ScorePanel(state, (width * 7) / 8, 140, borderSize);
		// JPanel scorePane = new JPanel();
		// JLabel scoreLabel = new JLabel("Score");
		// scorePane.setPreferredSize(new Dimension(width, 100));
		// scorePane.setBackground(new Color(248, 248, 255));
		// scorePane.add(scoreLabel);
		// scorePane.add(scoreSubPane);
		this.add(scorePane);
	}
}

class NextPiecePanel extends JPanel {
	private int width, height;
	private GameState state;
	
	public NextPiecePanel(GameState state, int width, int height, int borderSize) {
		super();
		this.state = state;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		setBorder(BorderFactory.createLineBorder(new Color(119, 136, 153), borderSize));
		setBackground(new Color(230, 236, 255));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D pieceGraphics = (Graphics2D) g.create((width - Block.SIZE.width * 4) / 2, (height - Block.SIZE.height * 4) / 2, Block.SIZE.width * 4, Block.SIZE.height * 4);
		state.drawNextPiece(pieceGraphics);
	}
}

class ScorePanel extends JPanel {
	private int width, height;
	private GameState state;
	private int labelFontSize = 42;
	private int digitsFontSize = 48;
	
	public ScorePanel(GameState state, int width, int height, int borderSize) {
		super();
		this.state = state;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setAlignmentX(CENTER_ALIGNMENT);
		setBackground(new Color(248, 248, 255));
		ScoreLabel scoreLab = new ScoreLabel(width, 
				((height / 2) * labelFontSize) / digitsFontSize,
				borderSize);
		DigitsPanel digitPanel = new DigitsPanel(width, 
				((height / 2) * digitsFontSize) / labelFontSize,
				borderSize);
		this.add(scoreLab);
		this.add(digitPanel);
	}
	
	class ScoreLabel extends JPanel {
		private int width, height;
		
		public ScoreLabel(int width, int height, int borderSize) {
			super();
			this.width = width;
			this.height = height;
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			setBackground(new Color(248, 248, 255));
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g;
			String str = "SCORE";
			Font baseFont = g2D.getFont();
			g2D.setFont(GUIFonts.getTetrisFont((float)labelFontSize));
			FontMetrics fm = g2D.getFontMetrics();
			int strWidth = fm.stringWidth(str);
			int strHeight = fm.getHeight();
			g2D.drawString(str, (width - strWidth) / 2, 
									(height + strHeight) / 2);
			g2D.setFont(baseFont);
		}
	}
	
	class DigitsPanel extends JPanel {
		private int width, height;

		public DigitsPanel(int width, int height, int borderSize) {
			super();
			// this.state = state;
			this.width = width;
			this.height = height;
			// hello = GUIFonts.getTetrisFont((float)digitsFontSize);
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			setBackground(new Color(248, 248, 255));
			// Border border = BorderFactory.createLineBorder(new Color(119, 136, 153), borderSize);
			// setBorder(border);
			// setBackground(Color.white);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// g.setColor(Color.black);
			// g.drawRect(0, 0, width - 1, height - 1);
			// g.fillRect(0, 0, width - 1, height - 1);
			// g.setColor(Color.white);
			Graphics2D g2D = (Graphics2D) g;
			String scoreStr = Integer.toString(state.getScore());
	        // FontRenderContext frc = g2D.getFontRenderContext();
	        //         GlyphVector gv = (GUIFonts.TETRIS_FONT).createGlyphVector(frc, scoreStr);
			Font baseFont = g2D.getFont();
			g2D.setFont(GUIFonts.getTetrisFont((float)digitsFontSize));
			FontMetrics fm = g2D.getFontMetrics();
			int strWidth = fm.stringWidth(scoreStr);
			int strHeight = fm.getHeight();
	        // g2D.drawGlyphVector(gv, width / 2 - GUIFonts.SIZE, (height * 7) / 8);
	        g2D.drawString(scoreStr, (width - strWidth) / 2, 
									(height + strHeight) / 2);
			g2D.setFont(baseFont);
		}
	}	
}

class GUIFonts {
	// public static final float SIZE = 48f;
	public static final Font TETRIS_FONT = (getFont("tetri.ttf"));
		
	public static Font getTetrisFont(float size) {
		return TETRIS_FONT.deriveFont(size);
	}
		
	private static Font getFont(String name) {
	    Font font = null;
	    String fName = "fonts/" + name;
	    try {
	      InputStream is = ScorePanel.class.getResourceAsStream(fName);
	      font = Font.createFont(Font.TRUETYPE_FONT, is);
	    } catch (Exception ex) {
	      ex.printStackTrace();
	      System.err.println(fName + " not loaded.  Using SansSerif font.");
	      font = new Font("SansSerif", Font.PLAIN, 1);
	    }
	    return font;
	}
}
