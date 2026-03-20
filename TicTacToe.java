import java.awt.Color;
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

    private Color[][] boardColors;

    private boolean isXTurn = true;

    public TicTacToe() {

        boardColors = new Color[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardColors[i][j] = Color.WHITE;
            }
        }

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

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        g.setColor(boardColors[i][j]);
                        g.fillRect(i * BOARD_DIMENSIONS / 3, j * BOARD_DIMENSIONS / 3, BOARD_DIMENSIONS / 3,
                                BOARD_DIMENSIONS / 3);
                    }
                }

                // BLACK GRID LINES
                g.setColor(Color.BLACK);
                for (int i = 0; i < 2; i++) {
                    g.drawLine((i + 1) * BOARD_DIMENSIONS / 3, 0, (i + 1) * BOARD_DIMENSIONS / 3, BOARD_DIMENSIONS);
                    g.drawLine(0, (i + 1) * BOARD_DIMENSIONS / 3, BOARD_DIMENSIONS, (i + 1) * BOARD_DIMENSIONS / 3);
                }

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
        int x = e.getX();
        int y = e.getY();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (x > i * BOARD_DIMENSIONS / 3 && x < (i + 1) * BOARD_DIMENSIONS / 3 &&
                        y > j * BOARD_DIMENSIONS / 3 && y < (j + 1) * BOARD_DIMENSIONS / 3) {
                    if (isXTurn) {
                        boardColors[i][j] = Color.RED;
                    } else {
                        boardColors[i][j] = Color.BLUE;
                    }
                }

            }

        }

        isXTurn = !isXTurn;
        panel.repaint();

    }

    /**
     * The main method is responsible for creating a thread
     * that will construct and show the GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new TicTacToe());
    }
}
