
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Tic Tac Toe in Swing
 * 
 * @author Ahyaan Malik
 * @version 3/20/2026
 */
public class TicTacToe extends MouseAdapter implements Runnable {

    private JPanel panel;

    private static final int BOARD_DIMENSIONS = 800;

    public TicTacToe() {

    }

    /**
     * The run method to set up the GUI.
     */
    @Override
    public void run() {
        // Our basic GUI setup, a JFrame with a JPanel inside it.
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(
                new Dimension(BOARD_DIMENSIONS, BOARD_DIMENSIONS));
        frame.setResizable(false);
        frame.addMouseListener(this);

        // JPanel with a paintComponent method using an anonymous class.
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

            }
        };
        // Add a mouse listener to the panel to respond to mouse events.
        panel.addMouseListener(this);

        frame.add(panel);

        // Display the window we've created.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * When the mouse button is pressed, change the color of the
     * square(s) according to the (x, y) location of the
     * mouse press in the JFrame.
     * 
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * The main method is responsible for creating a thread
     * that will construct and show the GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new TicTacToe());
    }
}
