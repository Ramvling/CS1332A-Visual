import java.util.ArrayList;
import java.awt.Rectangle;

/**
 * A class that holds a collection of node objects that are to be drawn to the gui.
 * @author Sarah Alsmiller
 *
 */
public class AGraph {
	/**
	 * an ArrayList that holds an ArrayList that represents a row
	 */
	public ArrayList<ArrayList<Node>> rows;
	/**
	 * start, the user inputed starting node
	 * dest, the user inputed destination node
	 */
	public Node start, dest;
	
	/**
	 * notes if A* has been run on the graph.
	 */
	public boolean run;
	
	/**
	 * Constructor
	 * @param bounds, a rectangle that denotes the size of the AGraph to be generated.
	 */
	public AGraph(Rectangle bounds) {
		this.run = false;
		rows = new ArrayList<ArrayList<Node>>();
		//the coordinates the new Node is being generated at.
		int x = 0;
		int y = 0;
		for (int i = 0; i < (bounds.width / 10); i ++) {
			//an ArrayList that represents the current row being generated.
			ArrayList<Node> currentRow = new ArrayList<Node>();
			x = 0;
			for (int j = 0; j < (bounds.height / 10); j ++) {
				Node toAdd = new Node(x, y);
				x += 10;
				if (i != 0) {
					//sets top neighbors and bottom neighbors
					toAdd.up = rows.get(i - 1).get(j);
					rows.get(i - 1).get(j).down = toAdd;
				}
				if (j != 0) {
					//sets rights and lefts;
					toAdd.left = currentRow.get(j - 1);
					currentRow.get(j-1).right = toAdd;
				}
				currentRow.add(toAdd);
			}
			rows.add(currentRow);
			y += 10;
		}
	}
}
