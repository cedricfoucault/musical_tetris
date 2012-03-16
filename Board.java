import java.awt.*;

class Board {
	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	private Block[][] data;
	static Dimension SIZE; // the graphical dimension of the board
	
	Board() {
		data = new Block[HEIGHT][WIDTH];
		initData();
	}
	
	private void initData() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				data[y][x] = new Block(x, y);
			}
		}
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
		
		boolean collision = ((xc < 0) || (xc >= WIDTH) || (yc < 0) || (yc >= HEIGHT) || (isFull(xc, yc)));
		for (Point point : relativePoints) {
			if (collision) {
				break;
			} else {
				x = xc + point.x;
				y = yc + point.y;
				collision = ((x < 0) || (x >= WIDTH) || (y < 0) || (y >= HEIGHT) || (isFull(x, y)));
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
		clean();
	}
	
	public void clean() {
		int i, j, gap;
		boolean isFullRow;

		gap = 0;
		for (i = 0; i < HEIGHT - gap; i++) {
			// loop invariant : gap == the number of full rows between 0 and i
			// collapse the pile according to the gap
			if (gap > 0) {
				for (j = 0 ; j < WIDTH; j++) {
					data[i - gap][j].setType(data[i][j].getType());
				}
			}
			// check if the current row is full
			isFullRow = true;
			for (j = 0; j < WIDTH; j++) {
				// loop invariant : 
				// isFull == "every box between 0 and j in the ith row is full"
				if (data[i][j].isEmpty()) {
					isFullRow = false;
					break;
				}
			}
			// increment the gap if the current row is full
			if (isFullRow) {
				gap++;
			}
		}

		// end of loop : i == BOARD_HEIGHT - gap
		// each full row leads a row at the top of the board to empty itself
		for (; i < HEIGHT; i++) {
			for (j = 0; j < WIDTH; j++) {
				data[i][j].empty();
			}
		}
	}
	
	public void draw(Graphics2D g, int frame_width, int frame_height) {
		int block_width = frame_width / WIDTH;
		int block_height = frame_height / HEIGHT;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
					data[y][x].draw(g);
					// (data[y][x]).draw(g, block_width * x, block_height * (HEIGHT - (y + 1)), block_width, block_height);
			}
		}
	}
}