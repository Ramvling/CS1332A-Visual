import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 * A panel that handles taking in user input for start, destination and inaccessible nodes,
 * runs A* search on the user inputed graph when the user hits a run button, and resets the
 * graph when the user hits a reset button.
 * @author Sarah Alsmiller
 *
 */
public class APanel extends JPanel {
	/**
	 * sets size of the window
	 */
	private final int WIDTH = 1500;
	private final int HEIGHT = 1500;
	
	/**
	 * visits, the total number of visitations A* made
	 * pathCost, the total cost of the path A* found
	 */
	private int visits, pathCost;
	/**
	 * rectangle that sets size of the AGraoh.
	 */
	private final Rectangle BOUNDS = new Rectangle(50,50, 550, 550);
	
	/**
	 * Agraph object to run A* on.
	 */
	AGraph graph;
	
    public APanel() {
    	//inititializes the JPanel
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        this.graph = new AGraph(BOUNDS);
        addMouseListener(new ClickListener());
        addMouseMotionListener(new ClickListener());
        JButton run = new JButton("Run");
        add(run);
        run.addActionListener(new Listener("Run"));
        run.setFocusable(false);
        JButton reset = new JButton("Reset");
        add(reset);
        reset.addActionListener(new Listener("Reset"));
        reset.setFocusable(false);
    }
    
    public void paintComponent(Graphics g) {
    	//handles drawing the GUI
        super.paintComponent(g);
        for (ArrayList<Node> row: graph.rows){
        	//draws all the nodes in the AGraph
        	for (Node n: row) {
        		n.drawNode(g);
        	}
        }
        
        // prints instructions to the gui
        g.setColor(Color.BLACK);
        if (graph.start == null) {
        	g.drawString("Choose Source", 600, 50);
        } else if (graph.dest == null) {
        	g.drawString("Choose Destination", 600, 50);
        } else if (graph.run == true) {
        	//add more info here
        	g.drawString("Reset to run again", 600, 50);
        	g.drawString(("Visited Nodes: " + visits), 600, 70);
        	g.drawString(("Total Path Cost: " + pathCost), 600, 90);
        } else {
        	g.drawString("Draw Barriers and run when ready", 600, 50);
        }
        
        g.drawString(("Red = Source"), 600, 110);
        g.drawString(("Blue = Destination"), 600, 130);
        g.drawString(("Black = In Path"), 600, 150);
        g.drawString(("Green = Visited Node"), 600, 170);
        g.drawString(("Blue-Green = Inaccessible Node"), 600, 190);
        g.drawString(("Straight jump cost = 10"), 600, 210);
        g.drawString(("Diagonal jump cost = 14"), 600, 230);
        g.drawString(("Note: Cannot jump diagonally through inaccessible nodes"), 600, 250);
    }
    
    /**
     * Rounds given input to the nearest 10.
     * @param a, integer to round
     * @return integer rounded to the nearest 10
     */
    private int roundTo10(int a) {
    	double value = Math.round((a + 5)/10.0) * 10.0;
    	return (int) value;
    }
    
    /**
     * Listener class to handle mouse user input
     * @author Sarah Alsmiller
     *
     */
    private class ClickListener extends MouseInputAdapter {
    	/**
    	 * Takes in a mouse event from the mouse listener if the mouse has been pressed.
    	 * Sets the start, destination, and inaccessible values for the nodes that the
    	 * mouse input is in range of as applicable.
    	 */
        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            if (p.x >= BOUNDS.width) {
            	return;
            } else if (p.y >= BOUNDS.height) {
            	return;
            } else if (p.y < 0) {
            	return;
            } else if (p.x < 0) {
            	return;
            }
            int y = (roundTo10(p.y)/10) - 1;
            int x = (roundTo10(p.x)/10) - 1;
            if (graph.start == null) {
            	graph.rows.get(y).get(x).setStart(true);
            	graph.start = graph.rows.get(y).get(x);
            	System.out.println("Start set");
            	System.out.println("(" + x + ',' + y + ')');
            } else if (graph.dest == null) {
            	graph.rows.get(y).get(x).setDest(true);
            	graph.dest = graph.rows.get(y).get(x);
            	System.out.println("Dest set");
            	System.out.println("(" + x + ',' + y + ')');
            } else {
            	updateValues(p);
            }
            
            repaint();
        }
        
        /** 
         * Handles updating nodes at a given point to inaccessible
         * @param p, a point
         */
        public void updateValues(Point p) {
             int y = (roundTo10(p.y)/10) - 1;
             int x = (roundTo10(p.x)/10) - 1;
             
             if (graph.rows.get(y).get(x).equals(graph.start)) {
            	 return;
             } else if (graph.rows.get(y).get(x).equals(graph.dest)) {
            	 return;
             }
             graph.rows.get(y).get(x).setAccessible(false);
        }
        
        /**
         * handles mouse events where the mouse has been dragged accross the gui.
         */
        public void mouseDragged(MouseEvent e) {
        	Point p = e.getPoint();
        	if (p.x >= BOUNDS.width) {
            	return;
            } else if (p.y >= BOUNDS.height) {
            	return;
            } else if (p.y < 0) {
            	return;
            } else if (p.x < 0) {
            	return;
            }
        	 updateValues(p);
             repaint();
        }
    }
    
    /**
     * Listener class to handle button input
     * @author Sarah Alsmiller
     *
     */
    public class Listener implements ActionListener {
        private String action;
        public Listener(String action) {
            this.action = action;
        }

        public void actionPerformed(ActionEvent e) {
        	if (action.equals("Run")) {
        		Astar search = new Astar();
                search.runAstar(graph);
                visits = search.visits;
                pathCost = search.pathCost;
                repaint();
        	}
        	
        	if (action.equals("Reset")) {
        		graph = new AGraph(BOUNDS);
        		repaint();
        	}
        }
    }

}
