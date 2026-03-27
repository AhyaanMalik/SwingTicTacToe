import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tic Tac Toe in Swing (Normal Mode)
 * 
 * @author Ahyaan Malik
 * @version 3/20/2026
 */
public class NormalMode extends MouseAdapter implements ActionListener {

    private JPanel panel;

    private JPanel boardPanel;

    private final Color BACKGROUND_COLOR = GameConstants.BACKGROUND_COLOR;
    private final Color SECONDARY_COLOR = GameConstants.SECONDARY_COLOR;

    private static final int BOARD_DIMENSIONS = GameConstants.BOARD_DIMENSIONS;

    private Color[][] boardColors;

    private Point[][] boardCenters;

    private boolean isXTurn = true;

    private JLabel mainText;
    private JLabel score;

    private int xScore = 0;
    private int oScore = 0;

    private JPanel bottomPanel;

    private JButton newGame;
    private JButton reset;

    private JButton backButton;

    private boolean gameOver = false;

    public NormalMode(CardLayout cardLayout, JPanel cards) {

        panel = new JPanel(new BorderLayout());

        boardColors = new Color[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardColors[i][j] = BACKGROUND_COLOR;
            }
        }

        boardCenters = new Point[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardCenters[i][j] = new Point(i * BOARD_DIMENSIONS / 3 + BOARD_DIMENSIONS / 6,
                        j * BOARD_DIMENSIONS / 3 + BOARD_DIMENSIONS / 6);
            }
        }
        // Top label
        mainText = new JLabel("Tic Tac Toe (Normal Mode)", SwingConstants.CENTER);
        panel.add(mainText, BorderLayout.NORTH);

        // Board panel
        boardPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(BOARD_DIMENSIONS, BOARD_DIMENSIONS);
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        g.setColor(boardColors[i][j]);
                        if (boardColors[i][j] == Color.RED) {
                            g.fillRect(boardCenters[i][j].x - 10, boardCenters[i][j].y - 10, 20, 20);
                        } else if (boardColors[i][j] == Color.BLUE) {
                            g.fillOval(boardCenters[i][j].x - 20, boardCenters[i][j].y - 20, 40, 40);
                            g.setColor(BACKGROUND_COLOR);
                            g.fillOval(boardCenters[i][j].x - 10, boardCenters[i][j].y - 10, 20, 20);
                        }
                    }
                }

                g.setColor(Color.BLACK);
                for (int i = 0; i < 2; i++) {
                    g.drawLine((i + 1) * BOARD_DIMENSIONS / 3, 0, (i + 1) * BOARD_DIMENSIONS / 3, BOARD_DIMENSIONS);
                    g.drawLine(0, (i + 1) * BOARD_DIMENSIONS / 3, BOARD_DIMENSIONS, (i + 1) * BOARD_DIMENSIONS / 3);
                }
            }
        };

        boardPanel.setBackground(BACKGROUND_COLOR);
        boardPanel.addMouseListener(this);
        panel.add(boardPanel, BorderLayout.CENTER);

        // Bottom panel
        score = new JLabel("Score: " + xScore + " - " + oScore, SwingConstants.CENTER);
        score.setFont(score.getFont().deriveFont(14.0f));

        newGame = new JButton("New Game");
        newGame.addActionListener(this);
        reset = new JButton("Reset");
        reset.addActionListener(this);
        backButton = new JButton("Back to Menu");

        // Experimenting with new way of implementing actionlisteners
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Menu");
            }
        });

        backButton.setBackground(BACKGROUND_COLOR);
        newGame.setBackground(BACKGROUND_COLOR);
        reset.setBackground(BACKGROUND_COLOR);

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(SECONDARY_COLOR);

        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtons.setBackground(SECONDARY_COLOR);
        leftButtons.add(backButton);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtons.setBackground(SECONDARY_COLOR);
        rightButtons.add(newGame);
        rightButtons.add(reset);

        bottomPanel.add(leftButtons, BorderLayout.WEST);
        bottomPanel.add(score, BorderLayout.CENTER);
        bottomPanel.add(rightButtons, BorderLayout.EAST);

        panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
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
        if (gameOver) {
            resetBoard();
            gameOver = false;
            return;
        }

        int x = e.getX();
        int y = e.getY();
        boolean validMove = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((x > i * BOARD_DIMENSIONS / 3 && x < (i + 1) * BOARD_DIMENSIONS / 3 &&
                        y > j * BOARD_DIMENSIONS / 3 && y < (j + 1) * BOARD_DIMENSIONS / 3)
                        && boardColors[i][j] == BACKGROUND_COLOR) {
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
            win(isXTurn);
        } else {

            if (validMove) {
                isXTurn = !isXTurn;

                if (isXTurn) {
                    mainText.setText("X's turn");
                } else {
                    mainText.setText("O's turn");
                }
            }

            if (checkFilled() && !checkWin()) {
                System.out.println("It's a draw!");
                // Reset the board
                resetBoard();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand();

        if (buttonText.equals("New Game")) {
            resetBoard();
        } else if (buttonText.equals("Reset")) {
            resetBoard();
            xScore = 0;
            oScore = 0;
            score.setText("Score: " + xScore + " - " + oScore);
        }
    }

    /**
     * Checks the board for a win condition.
     * 
     * @return true if there is a win condition on the board, false otherwise
     */
    private boolean checkWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (boardColors[i][0] != BACKGROUND_COLOR && boardColors[i][0] == boardColors[i][1]
                    && boardColors[i][1] == boardColors[i][2]) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (boardColors[0][j] != BACKGROUND_COLOR && boardColors[0][j] == boardColors[1][j]
                    && boardColors[1][j] == boardColors[2][j]) {
                return true;
            }
        }

        // Check diagonals
        if (boardColors[0][0] != BACKGROUND_COLOR && boardColors[0][0] == boardColors[1][1]
                && boardColors[1][1] == boardColors[2][2]) {
            return true;
        }
        if (boardColors[0][2] != BACKGROUND_COLOR && boardColors[0][2] == boardColors[1][1]
                && boardColors[1][1] == boardColors[2][0]) {
            return true;
        }

        return false;
    }

    /**
     * Handles the win condition by updating the score and resetting the board.
     * 
     */
    private void win(boolean isXWin) {
        if (isXWin) {
            xScore++;
            mainText.setText("X wins! Click to play again.");
        } else {
            oScore++;
            mainText.setText("O wins! Click to play again.");
        }
        score.setText("Score: " + xScore + " - " + oScore);
        gameOver = true;

    }

    /**
     * Checks the board for a draw condition (all squares filled with no winner).
     * 
     * @return true if all squares are filled and there is no winner, false
     *         otherwise
     */
    private boolean checkFilled() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardColors[i][j] == BACKGROUND_COLOR) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Resets the board
     * 
     * 
     */
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardColors[i][j] = BACKGROUND_COLOR;
            }
        }
        isXTurn = true;
        mainText.setText("Tic Tac Toe");
        panel.repaint();
    }
}
