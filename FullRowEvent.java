import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class FullRowEvent extends ActionEvent {
	public LinkedList<Integer> fullRows;
	public int nRows;
		
	FullRowEvent(Object source, LinkedList<Integer> fullRows, int nRows) {
		super(source, 0, "full row");
		this.fullRows = fullRows;
		this.nRows = nRows;
	}
}