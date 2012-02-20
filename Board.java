import java.awt.*;

class Board {
	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static final boolean EMPTY_BLOCK = false;
	private boolean[][] data;
	
	Board() {
		data = new boolean[HEIGHT][WIDTH];
	}
	
	void draw(Graphics2D g, int frame_width, int frame_height) {
		int block_width = frame_width / WIDTH;
		int block_height = frame_height / HEIGHT;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (isFull(x, y)) {
					g.setColor(Color.white);
					g.fillRect(x * block_width, y * block_height, block_width - 1, block_height - 1);
		            g.setColor(Color.blue);
		            g.drawRect(x * block_width, y * block_height, block_width, block_height);
				}
			}
		}
	}
	
	public boolean isFull(int x, int y) {
		return (data[y][x] != EMPTY_BLOCK);
	}
	
	public boolean willCollide(Piece piece) {
		Point center = piece.getCenter();
		int xc = center.x, yc = center.y, x, y;
		Point[] relativeBlocks = piece.getBlocks();
		
		// System.out.println("xc, yc : " + xc + ", " + yc);
		boolean collision = ((xc < 0) || (xc >= WIDTH) || (yc < 0) || (isFull(xc, yc)));
		for (Point block : relativeBlocks) {
			if (collision) {
				break;
			} else {
				x = xc + block.x;
				y = yc + block.y;
				// System.out.println("x, y : " + (xc + block.x) + ", " + (yc + block.y));
				collision = ((x < 0) || (x >= WIDTH) || (y < 0) || (isFull(x, y)));
			}
		}
		
		return collision;
	}
	
	public void merge(Piece piece) {
		Point center = piece.getCenter();
		int xc = center.x, yc = center.y;
		Point[] relativeBlocks = piece.getBlocks();
		data[yc][xc] = true;
		for (Point block : relativeBlocks) {
			// System.out.println("x, y : " + (xc + block.x) + ", " + (yc + block.y));
			data[yc + block.y][xc + block.x] = true;
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
					data[i - gap][j] = data[i][j];	
				}
			}
			// check if the current row is full
			isFullRow = true;
			for (j = 0; j < WIDTH; j++) {
				// loop invariant : 
				// isFull == "every box between 0 and j in the ith row is full"
				if (data[i][j] == EMPTY_BLOCK) {
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
				data[i][j] = false;
			}
		}
	}
	
	
}