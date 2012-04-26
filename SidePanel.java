import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;

public class SidePanel extends JPanel {
	private final GameState state;
	private int width, height;
	
	public SidePanel(GameState state, int width, int height, int borderSize) {
		super();
		this.state = state;
		this.width = width;
		this.height = height;
		Dimension size = new Dimension(width, height);
		setMaximumSize(size);
		setMinimumSize(size);
		setPreferredSize(size);

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
		this.state     = state;
		this.width     = width;
		this.height    = height;
		Dimension size = new Dimension(width, height);
		setMaximumSize(size);
		setMinimumSize(size);
		setPreferredSize(size);
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		setBackground(Color.BLACK);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
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
		this.state  = state;
		this.width  = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setAlignmentX(CENTER_ALIGNMENT);
		setBackground(Color.BLACK);
		ScoreLabel scoreLab    = new ScoreLabel(width, 
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
		private final String str = "SCORE";
		
		public ScoreLabel(int width, int height, int borderSize) {
			super();
			this.width  = width;
			this.height = height;
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			setBackground(Color.BLACK);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D  = (Graphics2D) g;
			Font baseFont   = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(TetrisFont.getTetrisFont((float)labelFontSize));
			FontMetrics fm  = g2D.getFontMetrics();
			int strWidth    = fm.stringWidth(str);
			int strHeight   = fm.getHeight();
			
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
			this.width = width;
			this.height = height;
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			setBackground(Color.BLACK);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D  = (Graphics2D) g;
			String scoreStr = Integer.toString(state.getScore());
			Font baseFont   = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(TetrisFont.getTetrisFont((float)digitsFontSize));
			FontMetrics fm  = g2D.getFontMetrics();
			int strWidth    = fm.stringWidth(scoreStr);
			int strHeight   = fm.getHeight();
			
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
		this.state  = state;
		this.width  = width;
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
		private final String str = "LINES";
		
		public LinesLabel(int width, int height, int borderSize) {
			super();
			this.width  = width;
			this.height = height;
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			setBackground(Color.BLACK);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D  = (Graphics2D) g;
			Font baseFont   = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(TetrisFont.getTetrisFont((float)labelFontSize));
			FontMetrics fm  = g2D.getFontMetrics();
			int strWidth    = fm.stringWidth(str);
			int strHeight   = fm.getHeight();
			
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
			this.width  = width;
			this.height = height;
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			setBackground(Color.BLACK);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D  = (Graphics2D) g;
			String linesStr = Integer.toString(state.getLinesCompleted());
			Font baseFont   = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(TetrisFont.getTetrisFont((float)digitsFontSize));
			FontMetrics fm  = g2D.getFontMetrics();
			int strWidth    = fm.stringWidth(linesStr);
			int strHeight   = fm.getHeight();

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
	private int labelFontSize  = 42;
	private int digitsFontSize = 48;
	
	public LevelPanel(GameState state, int width, int height, int borderSize) {
		super();
		this.state  = state;
		this.width  = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setAlignmentX(CENTER_ALIGNMENT);
		// setBackground(new Color(248, 248, 255));
		setBackground(Color.BLACK);
		LevelLabel linesLab    = new LevelLabel(width, 
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
		private final String str = "LEVEL";
		
		public LevelLabel(int width, int height, int borderSize) {
			super();
			this.width  = width;
			this.height = height;
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			setBackground(Color.BLACK);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D  = (Graphics2D) g;
			Font baseFont   = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(TetrisFont.getTetrisFont((float)labelFontSize));
			FontMetrics fm  = g2D.getFontMetrics();
			int strWidth    = fm.stringWidth(str);
			int strHeight   = fm.getHeight();
			
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
			this.width  = width;
			this.height = height;
			setPreferredSize(new Dimension(width, height));
			setAlignmentX(CENTER_ALIGNMENT);
			setBackground(Color.BLACK);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D  = (Graphics2D) g;
			String levelStr = Integer.toString(state.getLevel());
			Font baseFont   = g2D.getFont();
			Color baseColor = g2D.getColor();
			g2D.setFont(TetrisFont.getTetrisFont((float)digitsFontSize));
			FontMetrics fm  = g2D.getFontMetrics();
			int strWidth    = fm.stringWidth(levelStr);
			int strHeight   = fm.getHeight();

			g2D.setColor(Color.WHITE);
	        g2D.drawString(levelStr, (width - strWidth) / 2, 
									(height + strHeight) / 2);
			g2D.setFont(baseFont);
			g2D.setColor(baseColor);
		}
	}	
}
