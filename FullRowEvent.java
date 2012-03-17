import java.awt.event.*;
import java.util.*;

class FullRowsEvent extends ActionEvent {
	private LinkedList<Integer> fullRows;
		
	FullRowsEvent(Object source, LinkedList<Integer> fullRows) {
		super(source, 0, "full row");
		this.fullRows = fullRows;
	}
	
	public LinkedList<Integer>[] getFullRows() {
		return fullRows;
	}
}