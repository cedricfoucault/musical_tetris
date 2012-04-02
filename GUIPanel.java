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

import javax.swing.*;
import javax.swing.border.*;
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
		Dimension size = new Dimension(width, height);
		setMaximumSize(size);
		setMinimumSize(size);
		setPreferredSize(size);
		// setBorder(BorderFactory.createLineBorder(new Color(119, 136, 153), borderSize));
		// setBackground(new Color(248, 248, 255));
		setBackground(Color.BLACK);
		setAlignmentX(RIGHT_ALIGNMENT);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		NextPiecePanel nextPiecePanel = 
			new NextPiecePanel(state, width, 
				Block.SIZE.height * 4, borderSize);
		ScorePanel scorePane = 
			new ScorePanel(state, width, Block.SIZE.height * 4, borderSize);
		LinesPanel linesPane = 
			new LinesPanel(state, width, Block.SIZE.height * 4, borderSize);
		LevelPanel levelPane = 
			new LevelPanel(state, width, Block.SIZE.height * 4, borderSize);
		add(nextPiecePanel);
		add(Box.createRigidArea(new Dimension(width, Block.SIZE.height)));
		add(scorePane);
		add(linesPane);
		add(levelPane);
		add(Box.createVerticalGlue());
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
		Dimension size = new Dimension(width, height);
		setMaximumSize(size);
		setMinimumSize(size);
		setPreferredSize(size);
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		// Image gray_block = BlockSprites.lightgray_block.getScaledInstance(
		// 			borderSize,
		// 			borderSize,
		// 			Image.SCALE_DEFAULT
		// 		);
		// ImageIcon icon = new ImageIcon(gray_block);
		// setBorder(BorderFactory.createMatteBorder(borderSize,
		// 	borderSize, borderSize, borderSize, icon));
		setBackground(Color.BLACK);
		// setBackground(new Color(000, 017, 85));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Graphics2D pieceGraphics = (Graphics2D) g.create((width - Block.SIZE.width * 4) / 2, (height - Block.SIZE.height * 4) / 2, Block.SIZE.width * 4, Block.SIZE.height * 4);
		Graphics2D pieceGraphics = (Graphics2D) g.create((width - Block.SIZE.width * 4) / 2, (height - Block.SIZE.height * 2) / 2, Block.SIZE.width * 4, Block.SIZE.height * 4);
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
		// setBackground(new Color(248, 248, 255));
		setBackground(Color.BLACK);
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
			// setBackground(new Color(248, 248, 255));
			setBackground(Color.BLACK);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g;
			String str = "SCORE";
			Font baseFont = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(GUIFonts.getTetrisFont((float)labelFontSize));
			FontMetrics fm = g2D.getFontMetrics();
			int strWidth = fm.stringWidth(str);
			int strHeight = fm.getHeight();
			
			g2D.setColor(Color.WHITE);
			g2D.drawString(str, (width - strWidth) / 2, 
									(height + strHeight) / 2);
			g2D.setFont(baseFont);
			g2D.setColor(baseColor);
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
			// setBackground(new Color(248, 248, 255));
			setBackground(Color.BLACK);
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
			Color baseColor = g2D.getColor();
			g2D.setFont(GUIFonts.getTetrisFont((float)digitsFontSize));
			FontMetrics fm = g2D.getFontMetrics();
			int strWidth = fm.stringWidth(scoreStr);
			int strHeight = fm.getHeight();
			
			g2D.setColor(Color.WHITE);
	        g2D.drawString(scoreStr, (width - strWidth) / 2, 
									(height + strHeight) / 2);
			g2D.setFont(baseFont);
			g2D.setColor(baseColor);
		}
	}	
}

class LinesPanel extends JPanel {
	private int width, height;
	private GameState state;
	private int labelFontSize = 42;
	private int digitsFontSize = 48;
	
	public LinesPanel(GameState state, int width, int height, int borderSize) {
		super();
		this.state = state;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setAlignmentX(CENTER_ALIGNMENT);
		// setBackground(new Color(248, 248, 255));
		setBackground(Color.BLACK);
		LinesLabel linesLab = new LinesLabel(width, 
				((height / 2) * labelFontSize) / digitsFontSize,
				borderSize);
		DigitsPanel digitPanel = new DigitsPanel(width, 
				((height / 2) * digitsFontSize) / labelFontSize,
				borderSize);
		this.add(linesLab);
		this.add(digitPanel);
	}
	
	class LinesLabel extends JPanel {
		private int width, height;
		
		public LinesLabel(int width, int height, int borderSize) {
			super();
			this.width = width;
			this.height = height;
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			// setBackground(new Color(248, 248, 255));
			setBackground(Color.BLACK);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g;
			String str = "LINES";
			Font baseFont = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(GUIFonts.getTetrisFont((float)labelFontSize));
			FontMetrics fm = g2D.getFontMetrics();
			int strWidth = fm.stringWidth(str);
			int strHeight = fm.getHeight();
			
			g2D.setColor(Color.WHITE);
			g2D.drawString(str, (width - strWidth) / 2, 
									(height + strHeight) / 2);
			g2D.setFont(baseFont);
			g2D.setColor(baseColor);
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
			// setBackground(new Color(248, 248, 255));
			setBackground(Color.BLACK);
			// Border border = BorderFactory.createLineBorder(new Color(119, 136, 153), borderSize);
			// setBorder(border);
			// setBackground(Color.white);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g;
			String linesStr = Integer.toString(state.getLinesCompleted());
			Font baseFont = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(GUIFonts.getTetrisFont((float)digitsFontSize));
			FontMetrics fm = g2D.getFontMetrics();
			int strWidth = fm.stringWidth(linesStr);
			int strHeight = fm.getHeight();

			g2D.setColor(Color.WHITE);
			g2D.drawString(linesStr, (width - strWidth) / 2, 
									(height + strHeight) / 2);
			g2D.setFont(baseFont);
			g2D.setColor(baseColor);
		}
	}	
}

class LevelPanel extends JPanel {
	private int width, height;
	private GameState state;
	private int labelFontSize = 42;
	private int digitsFontSize = 48;
	
	public LevelPanel(GameState state, int width, int height, int borderSize) {
		super();
		this.state = state;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setAlignmentX(CENTER_ALIGNMENT);
		// setBackground(new Color(248, 248, 255));
		setBackground(Color.BLACK);
		LevelLabel linesLab = new LevelLabel(width, 
				((height / 2) * labelFontSize) / digitsFontSize,
				borderSize);
		DigitsPanel digitPanel = new DigitsPanel(width, 
				((height / 2) * digitsFontSize) / labelFontSize,
				borderSize);
		this.add(linesLab);
		this.add(digitPanel);
	}
	
	class LevelLabel extends JPanel {
		private int width, height;
		
		public LevelLabel(int width, int height, int borderSize) {
			super();
			this.width = width;
			this.height = height;
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			// setBackground(new Color(248, 248, 255));
			setBackground(Color.BLACK);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g;
			String str = "LEVEL";
			Font baseFont = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(GUIFonts.getTetrisFont((float)labelFontSize));
			FontMetrics fm = g2D.getFontMetrics();
			int strWidth = fm.stringWidth(str);
			int strHeight = fm.getHeight();
			
			g2D.setColor(Color.WHITE);
			g2D.drawString(str, (width - strWidth) / 2, 
									(height + strHeight) / 2);
			g2D.setFont(baseFont);
			g2D.setColor(baseColor);
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
			// setBackground(new Color(248, 248, 255));
			setBackground(Color.BLACK);
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
			String levelStr = Integer.toString(state.getLevel());
	        // FontRenderContext frc = g2D.getFontRenderContext();
	        //         GlyphVector gv = (GUIFonts.TETRIS_FONT).createGlyphVector(frc, scoreStr);
			Font baseFont = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(GUIFonts.getTetrisFont((float)digitsFontSize));
			FontMetrics fm = g2D.getFontMetrics();
			int strWidth = fm.stringWidth(levelStr);
			int strHeight = fm.getHeight();

			g2D.setColor(Color.WHITE);
	        g2D.drawString(levelStr, (width - strWidth) / 2, 
									(height + strHeight) / 2);
			g2D.setFont(baseFont);
			g2D.setColor(baseColor);
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
