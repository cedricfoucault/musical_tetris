import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Board {
	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static Dimension SIZE; // the graphical dimension of the board
	private static final int ROW_UPDATE_DELAY = 600;
	private Block[][] data;
	private BoardListener listener;
	
	Board() {
		data = new Block[HEIGHT][WIDTH];
		initData();
	}
	
	void addBoardListener(BoardListener bl) {
		listener = bl;
	}
	
	private void initData() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				data[y][x] = new Block(x, y);
			}
		}
	}
	
	public void empty() {
		initData();
	}
	
	public static void setSize(Dimension size) {
		SIZE = size;
	}
	
	public boolean isEmpty(int x, int y) {
		return (data[y][x].isEmpty());
	}

	public boolean isFull(int x, int y) {
		return !(data[y][x].isEmpty());
	}
	
	public boolean willCollide(Piece piece) {
		Point center = piece.getCenter();
		int xc = center.x, yc = center.y, x, y;
		Point[] relativePoints = piece.getRelativePoints();
		
		boolean collision = ((xc < 0) || (xc >= WIDTH) ||
		    (yc < 0) || (yc >= HEIGHT) || (isFull(xc, yc)));
		for (Point point : relativePoints) {
			if (collision) {
				break;
			} else {
				x = xc + point.x;
				y = yc + point.y;
				collision = ((x < 0) || (x >= WIDTH) ||
				    (y < 0) || (y >= HEIGHT) || (isFull(x, y)));
			}
		}
		
		return collision;
	}
	
	public void merge(Piece piece) {
		Point center = piece.getCenter();
		int xc = center.x, yc = center.y;
		Point[] relativePoints = piece.getRelativePoints();
		BlockType blockType = piece.blockType;
		data[yc][xc].setType(blockType);
		for (Point point : relativePoints) {
			data[yc + point.y][xc + point.x].setType(blockType);
		}
		listener.land();
		detectFullRows();
	}
	
	public void detectFullRows() {
		LinkedList<Integer> fullRows = new LinkedList<Integer>();
		int i, j;
		boolean isFullRow;
		int nFullRows = 0;
		
		for (i = 0; i < HEIGHT; i++) {
			// check if the current row is full
			isFullRow = true;
			for (j = 0; j < WIDTH; j++) {
				// loop invariant : 
				// isFull == 
				// "every box between 0 and j in the ith row is full"
				if (data[i][j].isEmpty()) {
					isFullRow = false;
					break;
				}
			}
			if (isFullRow) {
				nFullRows++;
				fullRows.add(new Integer(i));
			}
		}
		
		if (nFullRows > 0) {
			listener.fullRowDetected(
				new FullRowEvent(this, fullRows, nFullRows));
		}
	}
	
	public void killRows(LinkedList<Integer> fullRows) {
		ListIterator<Integer> iterList = fullRows.listIterator(0);
		int row, j;
		while (iterList.hasNext()) {
		 	row = iterList.next().intValue();
			for (j = 0; j < WIDTH; j++) {
				data[row][j].kill();
			}
		}
		RowUpdater rowUpdater = new RowUpdater(fullRows);
		Timer timer = new Timer(ROW_UPDATE_DELAY, rowUpdater);
		timer.setRepeats(false);
		timer.start();
	}
	
	private class RowUpdater implements ActionListener {
		private LinkedList<Integer> fullRows;
		RowUpdater(LinkedList<Integer> fullRows) {
			this.fullRows = fullRows;
		}
		
		public void actionPerformed(ActionEvent e) {
			ListIterator<Integer> iterList = fullRows.listIterator(0);
			int row, nextRow, i, j, gap = 0;
			
			if (iterList.hasNext()) {
				nextRow = iterList.next().intValue();
				gap++;
			} else {
				return;
			}
			
			while (iterList.hasNext()) {
				row = nextRow;
			 	nextRow = iterList.next().intValue();
				for (i = row - (gap - 1); i < (nextRow - gap); i++) {
					for (j = 0; j < WIDTH; j++) {
						data[i][j] = data[i + gap][j];
						data[i][j].fall(gap);		
					}
				}
				gap++;
			}
			
			for (i = nextRow - (gap - 1); i < HEIGHT - gap; i++) {
				for (j = 0; j < WIDTH; j++) {
					data[i][j] = data[i + gap][j];
					data[i][j].fall(gap);
				}
			}
			
			for (i = HEIGHT - gap; i < HEIGHT; i++) {
				for (j = 0; j < WIDTH; j++) {
					data[i][j] = new Block(j, i);
				}
			}
		}
	}
	
	public void draw(Graphics2D g) {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
					data[y][x].draw(g);
			}
		}
	}
}