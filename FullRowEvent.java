import java.awt.event.*;
import java.util.*;

public class FullRowEvent extends ActionEvent {
	public LinkedList<Integer> fullRows;
	public int nRows;
		
	FullRowEvent(Object source, LinkedList<Integer> fullRows, int nRows) {
		super(source, 0, "full row");
		this.fullRows = fullRows;
		this.nRows = nRows;
	}
}