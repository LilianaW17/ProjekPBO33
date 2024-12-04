import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class Solitaire {
    public static final int TABLE_HEIGHT = Card.CARD_HEIGHT * 4;
    public static final int TABLE_WIDTH = (Card.CARD_WIDTH * 7) + 100;
    public static final int NUM_FOUNDATION_STACKS = 4;
    public static final int NUM_TABLEAU_STACKS = 7;
    public static final Point DECK_POSITION = new Point(5, 5);
    public static final Point SHOW_POSITION = new Point(DECK_POSITION.x + Card.CARD_WIDTH + 5, DECK_POSITION.y);
    public static final Point FOUNDATION_POSITION = new Point(SHOW_POSITION.x + Card.CARD_WIDTH + 150, DECK_POSITION.y);
    public static final Point TABLEAU_POSITION = new Point(DECK_POSITION.x, FOUNDATION_POSITION.y + Card.CARD_HEIGHT + 30);

    private static CardStack deck;
    private static CardStack[] tableauStacks;
    private static FoundationStack[] foundationStacks;
    private static final JFrame frame = new JFrame("Klondike Solitaire");
    private static final JPanel gameTable = new JPanel();
    private static final JButton newGameButton = new JButton("New Game");
    private static final JTextField scoreDisplay = new JTextField("Score: 0");
    private static final JTextField timerDisplay = new JTextField("Time: 0");
    private static Timer gameTimer = new Timer();
    private static int score = 0;
    private static int elapsedTime = 0;

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(TABLE_WIDTH, TABLE_HEIGHT);
        gameTable.setLayout(null);
        gameTable.setBackground(new Color(0, 180, 0));
        frame.add(gameTable);
        frame.setVisible(true);
        initializeGameElements();
    }

    private static void initializeGameElements() {
        gameTable.removeAll(); // Bersihkan panel sebelum menambahkan elemen baru
        deck = new CardStack(true);
        deck.shuffle();
    
        tableauStacks = new CardStack[NUM_TABLEAU_STACKS];
        for (int i = 0; i < NUM_TABLEAU_STACKS; i++) {
            tableauStacks[i] = new CardStack(false);
            tableauStacks[i].setXY(TABLEAU_POSITION.x + (i * (Card.CARD_WIDTH + 10)), TABLEAU_POSITION.y);
    
            for (int j = i; j >= 0; j--) {
                Card card = deck.pop();
                if (j == i) {
                    card.setFaceup(); // Kartu paling atas terbuka
                }
                tableauStacks[i].push(card);
    
                // Atur posisi kartu dengan overlap vertikal
                card.setBounds(
                    TABLEAU_POSITION.x + (i * (Card.CARD_WIDTH + 10)),
                    TABLEAU_POSITION.y + (j * 30), // Sesuaikan overlap
                    Card.CARD_WIDTH,
                    Card.CARD_HEIGHT
                );
                gameTable.add(card);
            }
        }
    
        // Tambahkan tombol dan elemen lainnya
        newGameButton.setBounds(TABLE_WIDTH - 150, 20, 120, 30);
        newGameButton.addActionListener(e -> initializeGameElements());
        gameTable.add(newGameButton);
    
        scoreDisplay.setBounds(TABLE_WIDTH - 150, 60, 120, 30);
        timerDisplay.setBounds(TABLE_WIDTH - 150, 100, 120, 30);
        gameTable.add(scoreDisplay);
        gameTable.add(timerDisplay);
    
        gameTable.repaint(); // Perbarui tampilan panel permainan
        startGameTimer();
    }
    
    

    private static void startGameTimer() {
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime++;
                timerDisplay.setText("Time: " + elapsedTime + "s");
            }
        }, 1000, 1000);
    }
}
