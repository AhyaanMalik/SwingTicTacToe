import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Tic Tac Toe in Swing
 * 
 * @author Ahyaan Malik
 * @version 3/20/2026
 */
public class TicTacToe extends MouseAdapter implements Runnable {

    private JPanel panel;

    private static final int BOARD_DIMENSIONS = 600;

    private Color[][] boardColors;

    private Point[][] boardCenters;

    private boolean isXTurn = true;

    private JLabel score;

    private int xScore = 0;
    private int oScore = 0;

    public TicTacToe() {

        boardColors = new Color[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardColors[i][j] = Color.WHITE;
            }
        }

        boardCenters = new Point[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardCenters[i][j] = new Point(i * BOARD_DIMENSIONS / 3 + BOARD_DIMENSIONS / 6,
                        j * BOARD_DIMENSIONS / 3 + BOARD_DIMENSIONS / 6);
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

        JPanel outerPanel = new JPanel(new BorderLayout());
        JLabel mainText = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        score = new JLabel("Score: " + xScore + " - " + oScore, SwingConstants.CENTER);
        outerPanel.setPreferredSize(new Dimension(BOARD_DIMENSIONS + 100, BOARD_DIMENSIONS + 100));
        outerPanel.add(mainText, BorderLayout.NORTH);
        outerPanel.add(score, BorderLayout.SOUTH);
        // JPanel with a paintComponent method using an anonymous class.
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        g.setColor(boardColors[i][j]);
                        g.fillRect(boardCenters[i][j].x - 10, boardCenters[i][j].y - 10, 20, 20);
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

        panel.setBackground(Color.WHITE);

        // Add a mouse listener to the panel to respond to mouse events.
        panel.addMouseListener(this);

        outerPanel.add(panel);

        frame.add(outerPanel);

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
        boolean validMove = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((x > i * BOARD_DIMENSIONS / 3 && x < (i + 1) * BOARD_DIMENSIONS / 3 &&
                        y > j * BOARD_DIMENSIONS / 3 && y < (j + 1) * BOARD_DIMENSIONS / 3)
                        && boardColors[i][j] == Color.WHITE) {
                    validMove = true;

                    if (isXTurn) {
                        boardColors[i][j] = Color.RED;
                    } else {
                        boardColors[i][j] = Color.BLUE;
                    }
                }

            }

        }
        panel.repaint();

        if (checkWin()) {
            if (isXTurn) {
                System.out.println("X wins!");
                xScore++;
            } else {
                System.out.println("O wins!");
                oScore++;
            }
            score.setText("Score: " + xScore + " - " + oScore);
            // Reset the board
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    boardColors[i][j] = Color.WHITE;
                }
            }
            isXTurn = true;
            panel.repaint();
        } else if (validMove) {
            isXTurn = !isXTurn;
        }

    }

    /**
     * Checks the board for a win condition.
     * 
     * @return true if there is a win condition on the board, false otherwise
     */
    public boolean checkWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (boardColors[i][0] != Color.WHITE && boardColors[i][0] == boardColors[i][1]
                    && boardColors[i][1] == boardColors[i][2]) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (boardColors[0][j] != Color.WHITE && boardColors[0][j] == boardColors[1][j]
                    && boardColors[1][j] == boardColors[2][j]) {
                return true;
            }
        }

        // Check diagonals
        if (boardColors[0][0] != Color.WHITE && boardColors[0][0] == boardColors[1][1]
                && boardColors[1][1] == boardColors[2][2]) {
            return true;
        }
        if (boardColors[0][2] != Color.WHITE && boardColors[0][2] == boardColors[1][1]
                && boardColors[1][1] == boardColors[2][0]) {
            return true;
        }

        return false;
    }

    /**
     * The main method is responsible for creating a thread
     * that will construct and show the GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new TicTacToe());
    }
}
