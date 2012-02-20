public enum Shape {
	O_SHAPE, I_SHAPE, S_SHAPE, Z_SHAPE, L_SHAPE, J_SHAPE, T_SHAPE;
	public static final int NB = 7;
	public static Shape fromInt(int i) {
		return values()[i];
	}
}