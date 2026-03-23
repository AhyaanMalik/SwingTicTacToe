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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main Game to show the options for type of modes.
 * 
 * @author Ahyaan Malik
 * @version 3/20/2026
 */
public class MainGame implements ActionListener, Runnable {

    private JPanel panel;

    private JPanel buttons;

    private JLabel mainText;
    private JButton normalMode;
    private JButton diceMode;

    private final Color BACKGROUND_COLOR = Color.PINK;

    private static final int BOARD_DIMENSIONS = 600;

    public MainGame() {
    }

    /**
     * The run method to set up the GUI.
     */
    @Override
    public void run() {
        // Our basic GUI setup, a JFrame with a JPanel inside it.
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        panel = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(BOARD_DIMENSIONS, BOARD_DIMENSIONS);
            }
        };

        mainText = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        mainText.setFont(mainText.getFont().deriveFont(48.0f));
        panel.add(mainText, BorderLayout.NORTH);

        buttons = new JPanel(new FlowLayout());
        normalMode = new JButton("Normal Mode");
        normalMode.addActionListener(this);
        diceMode = new JButton("Dice Mode");
        diceMode.addActionListener(this);
        buttons.add(normalMode);
        buttons.add(diceMode);
        panel.add(buttons, BorderLayout.CENTER);

        panel.setBackground(BACKGROUND_COLOR);

        frame.add(panel);

        // Display the window we've created.
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand();

        if (buttonText.equals("Normal Mode")) {
            SwingUtilities.invokeLater(new NormalMode());
        } else if (buttonText.equals("Dice Mode")) {
            System.out.println("Coming Soon!");
        }
    }

    /**
     * The main method is responsible for creating a thread
     * that will construct and show the GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MainGame());
    }
}
