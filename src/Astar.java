import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Stack;

/**
 * This class executes A* search.
 * @author Sarah Alsmiller
 *
 */
public class Astar {
	/**
	 * The empty set of nodes that have already been evaluated by the algorithm 
	 * Once in evaluated a node will not be looked at again, unless the algorithm  finds
	 * a smaller g(x) value to assign it.
	 */
	private ArrayList<Node> evaluated;
	
	/**
	 * the open set of nodes to be evaluated by the algorithm. When evaluating a node
	 * its neighbors are added to toEvaluate with their updated g and h values
	 * if they are accessible.
	 */
	private PriorityQueue<Node> toEvaluate;
	
	/** Maps each vertex to the vertex it came from. The current vertex is the key,
	 * and the previous vertex is the value. If a smaller g(x) is discovered for a node,
	 * this value is updated in the map.
	 */
	private HashMap<Node, Node> cameFrom;
	
	public int visits, pathCost;
	
	/**
	 * 
	 * @param g, the AGraph containing the user inputed data
	 * @return output, an ArrayList containing all of the nodes in the path in order of traversal.
	 */
	
	public ArrayList<Node> runAstar(AGraph g) {
		g.run = true;
		if ((g.start != null) && (g.dest != null)) {
			//Makes sure the user has properly set up the program
			if (g.start.equals(g.dest)) {
				//if user inputs a start node and a destinaiton node that are the same
				ArrayList<Node> output = new ArrayList<Node>();
				output.add(g.start);
				visits = 1;
				pathCost = 0;
				return output;
			}
			
			evaluated = new ArrayList<Node>();
			toEvaluate = new PriorityQueue<Node>();
			cameFrom = new HashMap<Node, Node>();
			
			//initializes the algorithm, setting the starting node's g to 0
			g.start.g = 0;
			g.start.setH(g.dest);
			toEvaluate.add(g.start);
			visits = 0;
			pathCost = 0;
			while (!toEvaluate.isEmpty()) {
				// removes the current node from the priority queue
				Node current = toEvaluate.remove();
				visits ++;
				if (current.equals(g.dest)) {
					pathCost = current.g;
					/*checks to see if the destination has been found, and initializes the
					path reconstruction method */
					path = new Stack<Node>();
					reconstruct(current);
					ArrayList<Node> output = new ArrayList<Node>();
					while (!path.isEmpty()) {
						//the stack contains the nodes inputed in reverse order, so this will
						// add them into the arrayList in traversal order.
						if (!output.contains(path.peek())) {
							output.add(path.pop());
						} else {
							path.pop();
						}
					}
					return output;
				}
				//adds current node to the evaluated set and marks the node as visited.
				evaluated.add(current);
				current.visited = true;
				//System.out.println(current.x + "," + current.y);
				checkAndAdd(current, g.dest);
			}
			return null;
		}
		return null;
	}
	
	/**
	 * A stack that temporarily holds the nodes while reconstruct rebuilds the shortest path
	 */
	private Stack<Node> path;
	
	/**
	 * A method that recursively rebuilds the shortest path by taking an input node,
	 * and checking it against the cameFrom map.
	 * @param end, the current node to evaluate.
	 */
	private void reconstruct(Node end) {
		if (cameFrom.containsKey(end)) {
			/* the starting node should be the only node in the path that is not used as a key
			 in cameFrom. */
			path.push(end);
			path.push(cameFrom.get(end));
			end.inPath = true;
			reconstruct(cameFrom.get(end));
		}
	}
	
	/**
	 * Evaluates all of the given nodes neighbors, and adds them to toEvaluate and cameFrom
	 * with the given node as its value if they fit the criterea. 
	 * @param n, the node currently being evaluated by the algorithm.
	 * @param dest, the user inputed destination node.
	 */
	private void checkAndAdd(Node n, Node dest) {
		/**
		 * An ArrayList representing all of n's accessible neighbors.
		 */
		ArrayList<Node> neighbors = new ArrayList<Node>();
		
		/*for each of n's given neighbors, if they are accessible and exist, they are
		 * added to neighbors.
		 */
		
		if ((n.up != null) && (n.up.getAccess())) {
			n.up.diag = false;
			neighbors.add(n.up);
			//checks to see for diag up left
			if ((n.up.left != null) && (n.up.left.getAccess())) {
				n.up.left.diag = true;
				neighbors.add(n.up.left);
			}
			
			//checks for diag up right
			if ((n.up.right != null) && (n.up.right.getAccess())) {
				n.up.right.diag = true;
				neighbors.add(n.up.right);
			}
		}
		if ((n.down != null) && (n.down.getAccess())) {
			n.down.diag = false;
			neighbors.add(n.down);
			//checks to see for diag down left
			if ((n.down.left != null) && (n.down.left.getAccess())) {
				n.down.left.diag = true;
				neighbors.add(n.down.left);
			}
			
			//checks for diag down right
			if ((n.down.right != null) && (n.down.right.getAccess())) {
				n.down.right.diag = true;
				neighbors.add(n.down.right);
			}
		}
		if ((n.left != null) && (n.left.getAccess())) {
			n.left.diag = false;
			neighbors.add(n.left);
		}
		if ((n.right != null) && (n.right.getAccess())) {
			n.diag = false;
			neighbors.add(n.right);
		}
		
		for (Node neighbor: neighbors) {
			if ((!evaluated.contains(neighbor) && (!toEvaluate.contains(neighbor))) || ((n.g + 14) < neighbor.g)) {
				/*if the neighbor has not been evaluated yet, and has not already been marked
				 * for evaluation, or the current path has a less past cost than the previous path
				 * the neighbor was evaluated for.
				 */
				neighbor.setG(n.g, neighbor.diag);
				neighbor.setH(dest);
				toEvaluate.add(neighbor);
				cameFrom.put(neighbor, n);
			}
		}
	}
}
