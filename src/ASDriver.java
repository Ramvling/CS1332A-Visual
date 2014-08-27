import javax.swing.JFrame;

/**
 * A JFrame that launches the GUI for A* search.
 * @author Sarah Alsmiller
 *
 */
public class ASDriver {
    public static void main(String[] args) {
        JFrame frame = new JFrame("A* Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        APanel panel = new APanel();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}