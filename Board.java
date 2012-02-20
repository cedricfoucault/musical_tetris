class Board {
	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static final int EMPTY_BLOCK = false;
	private boolean[][] data;
	
	Board() {
		data = new boolean[HEIGHT][WIDTH];
	}
	
	public boolean isFull(int x, int y) {
		return (data[y][x] != EMPTY_BLOCK);
	}
	
	public boolean willCollide(Piece piece) {
		Point center = piece.getCenter();
		int xc = center.x, yc = center.y, x, y;
		Point[] relativeBlocks = piece.getBlocks();
		
		boolean collision = isFull(xc, yc) || xc < 0 || xc >= WIDTH || y < 0;
		for (Point block : relativeBlocks) {
			if (collision) {
				break;
			} else {
				x = xc + block.x;
				y = xc + block.y;
				collision = isFull(x, y) || x < 0 || x >= WIDTH || y < 0;
			}
		}
		
		return collision;
	}
	
	public void merge(Piece piece) {
		Point center = piece.getCenter();
		Point[] relativeBlocks = piece.getBlocks();
		
		data[center.y][center.x] = true;
		for (Point block : relativeBlocks) {
			data[block.y][block.x] = true;
		}
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