import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Card extends JPanel {
    public enum Value {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    public enum Suit {
        SPADES, CLUBS, DIAMONDS, HEARTS
    }

    private Suit _suit;
    private Value _value;
    private boolean _faceup;
    private Point _location;
    private static Image backImage;
    private Image faceImage;

    public static final int CARD_HEIGHT = 150;
    public static final int CARD_WIDTH = 100;
    public static final int CORNER_ANGLE = 25;

    // Muat gambar belakang kartu
    static {
        try {
            backImage = ImageIO.read(new File("ProjekPBO33/deck/back.png")); // Sesuaikan jika nama file berbeda
        } catch (IOException e) {
            System.err.println("Error loading back image: " + e.getMessage());
        }
    }

    public Card(Suit suit, Value value) {
        _suit = suit;
        _value = value;
        _faceup = false;
        _location = new Point();
        loadFaceImage();  // Muat gambar muka kartu berdasarkan suit dan value
    }

    private void loadFaceImage() {
        try {
            // Tentukan nama folder sesuai suit
            String suitName = switch (_suit) {
                case HEARTS -> "hati";
                case DIAMONDS -> "diamonds";
                case SPADES -> "scope";
                case CLUBS -> "kriting";
            };

            // Tentukan nama file berdasarkan nilai kartu
            String valueName = switch (_value) {
                case ACE -> "as";
                case JACK -> "j";
                case QUEEN -> "q";
                case KING -> "k";
                default -> String.valueOf(_value.ordinal() + 1);
            };

            // Bentuk path file
            String fileName = "ProjekPBO33/deck/" + suitName + " " + valueName + ".jpg";

            // Cek apakah file ada
            File imgFile = new File(fileName);
            if (imgFile.exists()) {
                faceImage = ImageIO.read(imgFile);
            } else {
                System.err.println("Gambar kartu tidak ditemukan: " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Error loading face image for " + _value + " of " + _suit + ": " + e.getMessage());
        }
    }

    public void setXY(Point p) {
        _location = p;
    }

    public Point getXY() {
        return _location;
    }

    public void setFaceup() {
        _faceup = true;
    }

    public boolean isFaceup() {
        return _faceup;
    }

    public void setFaceDown() {
        _faceup = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (_faceup) {
            // Jika kartu terbuka (faceup)
            if (faceImage != null) {
                g2d.drawImage(faceImage, 0, 0, CARD_WIDTH, CARD_HEIGHT, null);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, CARD_WIDTH, CARD_HEIGHT, CORNER_ANGLE, CORNER_ANGLE);
                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(0, 0, CARD_WIDTH, CARD_HEIGHT, CORNER_ANGLE, CORNER_ANGLE);
            }
        } else {
            // Jika kartu tertutup (facedown)
            if (backImage != null) {
                g2d.drawImage(backImage, 0, 0, CARD_WIDTH, CARD_HEIGHT, null);
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRoundRect(0, 0, CARD_WIDTH, CARD_HEIGHT, CORNER_ANGLE, CORNER_ANGLE);
                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(0, 0, CARD_WIDTH, CARD_HEIGHT, CORNER_ANGLE, CORNER_ANGLE);
            }
        }
    }

    public Suit getSuit() {
        return _suit;
    }

    public Value getValue() {
        return _value;
    }
}
