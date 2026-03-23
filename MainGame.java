import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.CardLayout;
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

    private static final String MENU_CARD = "Menu";
    private static final String NORMAL_MODE_CARD = "Normal Mode";
    private static final String DICE_MODE_CARD = "Dice Mode";

    private CardLayout cardLayout;
    private JPanel cards;
    private JFrame frame;

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
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(BOARD_DIMENSIONS, BOARD_DIMENSIONS);
            }
        };

        panel = new JPanel(new BorderLayout());

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

        // CardLayout
        cards.add(panel, MENU_CARD);
        cards.add(new NormalMode(cardLayout, cards).getPanel(), NORMAL_MODE_CARD);
        // cards.add(new DiceMode(cardLayout, cards), DICE_MODE_CARD);

        cardLayout.show(cards, MENU_CARD);

        frame.add(cards);
        // Display the window we've created.
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand();

        if (buttonText.equals("Normal Mode")) {
            cardLayout.show(cards, NORMAL_MODE_CARD);
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
