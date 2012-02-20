import java.awt.*;

public class TetrominoesCatalog {
	private static final Point[] O_HORIZONTAL = {
		new Point(0, -1), new Point(-1, 0), new Point(-1, -1)
	};
	private static final Point[][] O = {
		O_HORIZONTAL, O_HORIZONTAL, O_HORIZONTAL, O_HORIZONTAL
	};
	// private static final boolean[][][] O = { 
	// 	{ 
	// 		{false, false, false, false},
	// 		{false, true, true, false},
	// 		{false, true, true, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, false, false},
	// 		{false, true, true, false},
	// 		{false, true, true, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, false, false},
	// 		{false, true, true, false},
	// 		{false, true, true, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, false, false},
	// 		{false, true, true, false},
	// 		{false, true, true, false},
	// 		{false, false, false, false}
	// 	}
	// };
	
	
	private static final Point[] I_HORIZONTAL = {
		new Point(1, 0), new Point(-1, 0), new Point(-2, 0)
	};
	private static final Point[] I_VERTICAL = {
		new Point(0, 1), new Point(0, -1), new Point(0, -2)
	};
	private static final Point[][] I = {
		I_HORIZONTAL, I_VERTICAL, I_HORIZONTAL, I_VERTICAL
	};
	// private static final boolean[][][] I = { 
	// 	{ 
	// 		{false, false, false, false},
	// 		{true, true, true, true},
	// 		{false, false, false, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, true, false},
	// 		{false, false, true, false},
	// 		{false, false, true, false},
	// 		{false, false, true, false}
	// 	},
	// 	{
	// 		{false, false, false, false},
	// 		{true, true, true, true},
	// 		{false, false, false, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, true, false},
	// 		{false, false, true, false},
	// 		{false, false, true, false},
	// 		{false, false, true, false}
	// 	}
	// };
	
	private static final Point[] S_HORIZONTAL = {
		new Point(1, 0), new Point(0, -1), new Point(-1, -1)
	};
	private static final Point[] S_VERTICAL = {
		new Point(0, 1), new Point(1, 0), new Point(1, -1)
	};
	private static final Point[][] S = {
		S_HORIZONTAL, S_VERTICAL, S_HORIZONTAL, S_VERTICAL
	};
	// private static final boolean[][][] S = { 
	// 	{ 
	// 		{false, false, false, false},
	// 		{false, false, true, true},
	// 		{false, true, true, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		false, false, true, false},
	// 		{false, false, true, true},
	// 		{false, false, false, true},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, false, false},
	// 		{false, false, true, true},
	// 		{false, true, true, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, true, false},
	// 		{false, false, true, true},
	// 		{false, false, false, true},
	// 		{false, false, false, false}
	// 	}
	// };
	
	private static final Point[] Z_HORIZONTAL = {
		new Point(-1, 0), new Point(0, -1), new Point(1, -1)
	};
	private static final Point[] Z_VERTICAL = {
		new Point(1, 1), new Point(1, 0), new Point(0, -1)
	};
	private static final Point[][] Z = {
		Z_HORIZONTAL, Z_VERTICAL, Z_HORIZONTAL, Z_VERTICAL
	};
	// private static final boolean[][][] Z = { 
	// 	{ 
	// 		{false, false, false, false},
	// 		{false, true, true, false},
	// 		{false, false, true, true},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, false, true},
	// 		{false, false, true, true},
	// 		{false, false, true, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, false, false},
	// 		{false, true, true, false},
	// 		{false, false, true, true},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, false, true},
	// 		{false, false, true, true},
	// 		{false, false, true, false},
	// 		{false, false, false, false}
	// 	}
	// };
	
	private static final Point[][] L = {
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(-1, -1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(1, -1)},
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(1, 1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(-1, 1)}
	};
	// private static final boolean[][][] L = { 
	// 	{ 
	// 		{false, false, false, false},
	// 		{false, true, true, true},
	// 		{false, true, false, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, true, false},
	// 		{false, false, true, false},
	// 		{false, false, true, true},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, false, true},
	// 		{false, true, true, true},
	// 		{false, false, false, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, true, true, false},
	// 		{false, false, true, false},
	// 		{false, false, true, false},
	// 		{false, false, false, false}
	// 	}
	// };
	
	private static final Point[][] J = {
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(1, -1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(1, 1)},
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(-1, 1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(-1, -1)}
	};
	// private static final boolean[][][] J = { 
	// 	{ 
	// 		{false, false, false, false},
	// 		{false, true, true, true},
	// 		{false, false, false, true},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, true, true},
	// 		{false, false, true, false},
	// 		{false, false, true, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, true, false, false},
	// 		{false, true, true, true},
	// 		{false, false, false, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, true, false},
	// 		{false, false, true, false},
	// 		{false, true, true, false},
	// 		{false, false, false, false}
	// 	}
	// };
	
	private static final Point[][] T = {
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(0, -1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(1, 0)},
		new Point[]{new Point(1, 0), new Point(-1, 0), new Point(0, 1)},
		new Point[]{new Point(0, 1), new Point(0, -1), new Point(-1, 0)}
	};
	// private static final boolean[][][] T = { 
	// 	{ 
	// 		{false, false, false, false},
	// 		{false, true, true, true},
	// 		{false, false, true, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, true, false},
	// 		{false, false, true, true},
	// 		{false, false, true, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, true, false},
	// 		{false, true, true, true},
	// 		{false, false, false, false},
	// 		{false, false, false, false}
	// 	},
	// 	{
	// 		{false, false, true, false},
	// 		{false, true, true, false},
	// 		{false, false, true, false},
	// 		{false, false, false, false}
	// 	}
	// };
	
	public static Point[] getBlocks(Shape shape, int orientation) {
		switch (shape) {
			case O_SHAPE: return O[orientation];
			case I_SHAPE: return I[orientation];
			case S_SHAPE: return S[orientation];
			case Z_SHAPE: return Z[orientation];
			case L_SHAPE: return L[orientation];
			case J_SHAPE: return J[orientation];
			case T_SHAPE: return T[orientation];
			default: return null;
		}
	}
}