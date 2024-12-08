import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class Card extends JPanel
{
    public static enum Value
    {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    public static enum Suit
    {
        SPADES, CLUBS, DIAMONDS, HEARTS
    }

    private Suit _suit;
    private Value _value;
    private Boolean _faceup;
    private Point _location; 
    private Point whereAmI;
	private Image faceImage;
    private int x; 
    private int y;
    private final int x_offset = 10;
    private final int y_offset = 20;
    private final int new_x_offset = x_offset + (CARD_WIDTH - 30);
    final static public int CARD_HEIGHT = 150;
    final static public int CARD_WIDTH = 100;
    final static public int CORNER_ANGLE = 25;

    // Gambar untuk kartu
    private Image image; 

    Card(Suit suit, Value value)
    {
        _suit = suit;
        _value = value;
        _faceup = false;
        _location = new Point();
        x = 0;
        y = 0;
        _location.x = x;
        _location.y = y;
        whereAmI = new Point();
        loadImage(); // Panggil metode untuk memuat gambar kartu
    }

    Card()
    {
        _suit = Card.Suit.CLUBS;
        _value = Card.Value.ACE;
        _faceup = false;
        _location = new Point();
        x = 0;
        y = 0;
        _location.x = x;
        _location.y = y;
        whereAmI = new Point();
        loadImage(); // Panggil metode untuk memuat gambar kartu
    }

    private void loadImage() 
{
    try {
        // Gunakan absolute path atau relative path yang lebih fleksibel
        String baseDir = System.getProperty("user.dir"); // Direktori project saat ini
        
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
            case TWO -> "2";
            case THREE -> "3";
            case FOUR -> "4";
            case FIVE -> "5";
            case SIX -> "6";
            case SEVEN -> "7";
            case EIGHT -> "8";
            case NINE -> "9";
            case TEN -> "10";
            case JACK -> "j";
            case QUEEN -> "q";
            case KING -> "k";
        };

        // Gunakan beberapa jalur kemungkinan
        String[] possiblePaths = {
            baseDir + "/MySolitaire/deck/" + suitName + " " + valueName + ".jpg",
            baseDir + "/deck/" + suitName + " " + valueName + ".jpg",
            baseDir + "/assets/deck/" + suitName + " " + valueName + ".jpg",
            "MySolitaire/deck/" + suitName + " " + valueName + ".jpg",
            "deck/" + suitName + " " + valueName + ".jpg"
        };

        // Coba setiap path
        for (String path : possiblePaths) {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                faceImage = ImageIO.read(imgFile);
                image = faceImage; // Setel image untuk kompatibilitas dengan kode sebelumnya
                return;
            }
        }

        // Jika tidak ditemukan di semua path
        System.err.println("Gambar kartu tidak ditemukan untuk: " + _value + " of " + _suit);
        
        // Opsional: Gunakan gambar default/placeholder jika gambar tidak ditemukan
        // faceImage = ImageIO.read(getClass().getResourceAsStream("/default_card.jpg"));
    } catch (IOException e) {
        System.err.println("Error loading face image for " + _value + " of " + _suit + ": " + e.getMessage());
        e.printStackTrace();
    }

	}

    public Suit getSuit()
    {
        return _suit;
    }

    public Value getValue()
    {
        return _value;
    }

    public void setWhereAmI(Point p)
    {
        whereAmI = p;
    }

    public Point getWhereAmI()
    {
        return whereAmI;
    }

    public Point getXY()
    {
        return new Point(x, y);
    }

    public Boolean getFaceStatus()
    {
        return _faceup;
    }

    public void setXY(Point p)
    {
        x = p.x;
        y = p.y;
    }

    public void setSuit(Suit suit)
    {
        _suit = suit;
        loadImage(); // Muat gambar baru setelah suit diperbarui
    }

    public void setValue(Value value)
    {
        _value = value;
        loadImage(); // Muat gambar baru setelah value diperbarui
    }

    public Card setFaceup()
    {
        _faceup = true;
        return this;
    }

    public Card setFacedown()
    {
        _faceup = false;
        return this;
    }

    @Override
    public boolean contains(Point p)
    {
        Rectangle rect = new Rectangle(whereAmI.x, whereAmI.y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        return (rect.contains(p));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        RoundRectangle2D rect2 = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
                CORNER_ANGLE, CORNER_ANGLE);
        g2d.setColor(Color.WHITE);
        g2d.fill(rect2);
        g2d.setColor(Color.black);
        g2d.draw(rect2);
        
        // DRAW THE CARD SUIT AND VALUE IF FACEUP
        if (_faceup)
        {
            if (image != null) 
            {
                // Gambar gambar kartu dari folder "deck"
                g2d.drawImage(image, _location.x, _location.y, CARD_WIDTH, CARD_HEIGHT, this);
            } 
            else 
            {
                // Jika gambar tidak ditemukan, tampilkan suit dan value secara manual
                switch (_suit)
                {
                    case HEARTS:
                        drawSuit(g2d, "Hearts", Color.RED);
                        break;
                    case DIAMONDS:
                        drawSuit(g2d, "Diamonds", Color.RED);
                        break;
                    case SPADES:
                        drawSuit(g2d, "Spades", Color.BLACK);
                        break;
                    case CLUBS:
                        drawSuit(g2d, "Clubs", Color.BLACK);
                        break;
                }
                switch (_value)
                {
                    case ACE: drawValue(g2d, "A"); break;
                    case TWO: drawValue(g2d, "2"); break;
                    case THREE: drawValue(g2d, "3"); break;
                    case FOUR: drawValue(g2d, "4"); break;
                    case FIVE: drawValue(g2d, "5"); break;
                    case SIX: drawValue(g2d, "6"); break;
                    case SEVEN: drawValue(g2d, "7"); break;
                    case EIGHT: drawValue(g2d, "8"); break;
                    case NINE: drawValue(g2d, "9"); break;
                    case TEN: drawValue(g2d, "10"); break;
                    case JACK: drawValue(g2d, "J"); break;
                    case QUEEN: drawValue(g2d, "Q"); break;
                    case KING: drawValue(g2d, "K"); break;
                }
            }
        } 
        else 
        {
            // DRAW THE BACK OF THE CARD IF FACEDOWN
            RoundRectangle2D rect = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
                    CORNER_ANGLE, CORNER_ANGLE);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fill(rect);
            g2d.setColor(Color.black);
            g2d.draw(rect);
        }
    }

    private void drawSuit(Graphics2D g, String suit, Color color)
    {
        g.setColor(color);
        g.drawString(suit, _location.x + x_offset, _location.y + y_offset);
        g.drawString(suit, _location.x + x_offset, _location.y + CARD_HEIGHT - 5);
    }

    private void drawValue(Graphics2D g, String value)
    {
        g.drawString(value, _location.x + new_x_offset, _location.y + y_offset);
        g.drawString(value, _location.x + new_x_offset, _location.y + y_offset + CARD_HEIGHT - 25);
    }
}
