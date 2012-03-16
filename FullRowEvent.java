import java.awt.event.*;

class FullRowEvent extends ActionEvent {
	// the indices of the full rows
	private int[] fullRows;
	// the number of full rows
	private int nRows;
		
	FullRowEvent(Object source, int[] fullRows) {
		super(source, 0, "full row");
		this.fullRows = fullRows;
		// this.nRows = fullRows.length();
	}
	
	public int[] getFullRows() {
		return fullRows;
	}
	
	public int getNRows() {
		return nRows;
	}
}