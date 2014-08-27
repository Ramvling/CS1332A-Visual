import java.awt.Graphics;
import java.awt.Color;

/**
 * This class contains a representation of a Node object.
 * @author Sarah Alsmiller
 *
 */
public class Node implements Comparable<Node> {
	/**
	 * x, the x coordinate of the node
	 * y, the y coordinate of the node
	 * w, the width/height of the node;
	 */
	public int x,y, w;
	/**
	 * The Node's neighbors. These are not kept in a data structure for ease of access
	 * when generating the AGraph and determining where the nodes connect.
	 */
	public Node up, down, left, right;
	
	/**
	 * start, boolean that denotes whether or not the node is the starting node
	 * dest, boolean that denotes whether or not the node is the destination node
	 */
	private boolean start, dest;
	
	/**
	 * visited, boolean that denotes if the node has been visited by A*
	 * inPath, denotes if node is in the path generated by A*
	 * accessible, denotes if user has marked node as inaccessible.
	 */
	public boolean visited, inPath, accessible, diag;
	
	/**
	 * g, the cost of the previous path
	 * h, heuristic guessing the cost of the future path
	 */
	public int g, h;
	
	/**
	 * Constructor
	 * @param x, x coordinate
	 * @param y, y coordinate
	 */
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		w = 10;
		accessible = true;
		this.start = false;
		this.dest = false;
		visited = false;
	}
	
	public int compareTo(Node other) {
		return this.getF() - other.getF();
	}
	
	public boolean getAccess() {
		return accessible;
	}
	public void setAccessible(boolean b) {
		accessible = b;
	}
	public void setStart(boolean b) {
		start = b;
	}
	public void setDest(boolean b) {
		dest = b;
	}
	
	/**
	 * Sets the h value based on a heuristic of where the destination node is
	 * uses the diagnol shortcut method.
	 * @param dest, the destination node
	 */
	public void setH(Node dest) {
		int xDis = Math.abs(dest.x - x);
		int yDis = Math.abs(dest.y - y);
		if (xDis > yDis) {
			h = (14 * yDis) + (10*(xDis - yDis));
		} else {
			h = (14 * xDis) + (10*(yDis - xDis));
		}
	}
	
	/**
	 * sets the g, past cost value by adding the cost to the input. 10 for a straight
	 * jump and 14 for a diagnol.
	 * @param previous, the g value of the previous node in the path
	 */
	public void setG(int previous, boolean diag) {
		if (diag) {
			g = previous + 14;
		} else {
			g = previous + 10;
		}
	}
	
	/**
	 * Gets the value of the f function by adding the h and g function, this determines
	 * the nodes's priority.
	 * @return f value
	 */
	public int getF() {
		return h + g;
	}
	
	/**
	 * Draws the nodes
	 * @param g, the gui
	 */
	public void drawNode(Graphics g) {
		g.setColor(Color.BLACK);
		if (start) {
			// if user denotes node to be start
			g.setColor(Color.RED);
			g.fillRect(x, y, w, w);
		} else if (dest) {
			//user denoted destination
			g.setColor(Color.BLUE);
			g.fillRect(x, y, w, w);
		} else if (inPath) {
			//A* denotes part of shortest path
			g.setColor(Color.BLACK);
			g.fillRect(x, y, w, w);
		}else if (visited) {
			//A* has visited this node
			g.setColor(Color.GREEN);
			g.fillRect(x, y, w, w);
		} else if (accessible) {
			// Node can be visited
			g.drawRect(x, y, w, w);
		} else {
			//user denoted inaccessible node
			g.setColor(new Color (34, 138, 93));
			g.fillRect(x, y, w, w);
		}
	}
}
