import static org.junit.Assert.*;
import java.awt.Rectangle;
import java.util.Random;
import org.junit.Test;
import java.util.ArrayList;

public class AStarTest {
	Rectangle bounds = new Rectangle (0,0, 550, 550);
	Astar search = new Astar();
	AGraph test;

	
	/** 
	 * Tests every node as a source node in a randomly chosen row against every other possible
	 * choice for a destination node to see if A* produces a failure. Takes a long time to run
	 * and does not actually see if A* has found the real shortest path, though it does ensure
	 * A* is only visiting the nodes in the path, as there are no barriers.
	 */
	@Test
	public void NoObstaclesDestMovetest() {
		Random roll = new Random();
		int rowTest = roll.nextInt(55);
		AGraph test = new AGraph(bounds);
		int startIndx = 0;
		test.start = test.rows.get(rowTest).get(0);
		while (!test.start.equals(test.rows.get(rowTest).get(54))) {
			for (int i = 0; i < bounds.width/10; i ++) {
				for (int j = 0; j < bounds.height/10; j ++) {
					test.dest = test.rows.get(54 - i).get(54 - j);
					ArrayList<Node> path = search.runAstar(test);
					assertFalse(path == null);
					assertEquals(search.visits, path.size());
				}
			}
			startIndx ++;
			test.start = test.rows.get(rowTest).get(startIndx);
		}
	}
	
	/**
	 * Similar to NoObstacleDestMovetest, only iterates through every possible combination
	 * of source and destination nodes on the given AGraph. Takes a VERY long time to run.
	 * 
	 * I would recommend only running this once to ensure it works. There is no randomness
	 * here so it will give the same results every time.
	 */
	@Test
	public void EveryPossibleChoicetest() {
		for (int k = 0; k < 55; k++) {
			AGraph test = new AGraph(bounds);
			int rowTest = k;
			int startIndx = 0;
			test.start = test.rows.get(rowTest).get(0);
			while (!test.start.equals(test.rows.get(rowTest).get(54))) {
				for (int i = 0; i < bounds.width/10; i ++) {
					for (int j = 0; j < bounds.height/10; j ++) {
						test.dest = test.rows.get(54 - i).get(54 - j);
						ArrayList<Node> path = search.runAstar(test);
						assertFalse(path == null);
						assertEquals(search.visits, path.size());
					}
				}
				startIndx ++;
				test.start = test.rows.get(rowTest).get(startIndx);
			}
			System.out.println(k);
		}
	}
	
	/**
	 * Creates an AGraph that has no path from the source node to the destination node.
	 * True if runAstar returns null.
	 */
	@Test
	public void ImpossiblePath() {
		AGraph test = new AGraph(bounds);
		test.start = test.rows.get(0).get(0);
		test.dest = test.rows.get(54).get(54);
		test.rows.get(53).get(54).accessible = false;
		test.rows.get(53).get(53).accessible = false;
		test.rows.get(54).get(53).accessible = false;
		assertEquals(search.runAstar(test), null);
	}
	

}
