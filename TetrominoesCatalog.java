import java.awt.*;

public class TetrominoesCatalog {
	private static final Point[] O_HORIZONTAL = {
		new Point(0, -1), new Point(-1, 0), new Point(-1, -1)
	};
	private static final Point[][] O = {
		O_HORIZONTAL, O_HORIZONTAL, O_HORIZONTAL, O_HORIZONTAL
	};
	private static final Point[] I_HORIZONTAL = {
		new Point(1, 0), new Point(-1, 0), new Point(-2, 0)
	};
	private static final Point[] I_VERTICAL = {
		new Point(0, 1), new Point(0, -1), new Point(0, -2)
	};
	private static final Point[][] I = {
		I_HORIZONTAL, I_VERTICAL, I_HORIZONTAL, I_VERTICAL
	};
	
	private static final Point[] S_HORIZONTAL = {
		new Point(1, 0), new Point(0, -1), new Point(-1, -1)
	};
	private static final Point[] S_VERTICAL = {
		new Point(0, 1), new Point(1, 0), new Point(1, -1)
	};
	private static final Point[][] S = {
		S_HORIZONTAL, S_VERTICAL, S_HORIZONTAL, S_VERTICAL
	};
	
	private static final Point[] Z_HORIZONTAL = {
		new Point(-1, 0), new Point(0, -1), new Point(1, -1)
	};
	private static final Point[] Z_VERTICAL = {
		new Point(1, 1), new Point(1, 0), new Point(0, -1)
	};
	private static final Point[][] Z = {
		Z_HORIZONTAL, Z_VERTICAL, Z_HORIZONTAL, Z_VERTICAL
	};
	
	private static final Point[][] L = {
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(-1, -1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(1, -1)},
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(1, 1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(-1, 1)}
	};
	
	private static final Point[][] J = {
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(1, -1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(1, 1)},
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(-1, 1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(-1, -1)}
	};
	
	private static final Point[][] T = {
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(0, -1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(1, 0)},
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(0, 1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(-1, 0)}
	};
	
	public static Point[] getRelativePoints(BlockType blockType, int orientation) {
		switch (blockType) {
			case O: return O[orientation];
			case I: return I[orientation];
			case S: return S[orientation];
			case Z: return Z[orientation];
			case L: return L[orientation];
			case J: return J[orientation];
			case T: return T[orientation];
			default: return null;
		}
	}
}