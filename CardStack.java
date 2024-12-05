import java.awt.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ListIterator;
import java.util.Vector;
import javax.swing.*;

class CardStack extends JComponent {
    protected final int NUM_CARDS = 52;
    protected Vector<Card> v;
    protected boolean playStack = false;
    protected int SPREAD = 18; // Jarak antar kartu
    protected int _x = 0;
    protected int _y = 0;

    public CardStack(boolean isDeck) {
        this.setLayout(null);
        v = new Vector<Card>();

        if (isDeck) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Value value : Card.Value.values()) {
                    v.add(new Card(suit, value));
                }
            }
        } else {
            playStack = true;
        }
    }

    public boolean empty() {
        return v.isEmpty();
    }

    public void putFirst(Card c) {
        v.add(0, c);
    }

    public Card getFirst() {
        return this.empty() ? null : v.get(0);
    }

    public Card getLast() {
        return this.empty() ? null : v.lastElement();
    }

    public Card popFirst() {
        return this.empty() ? null : v.remove(0);
    }

    public void push(Card c) {
        v.add(c);
    }

    public Card pop() {
        return this.empty() ? null : v.remove(v.size() - 1);
    }

    public void shuffle() {
        Vector<Card> temp = new Vector<>();
        while (!this.empty()) {
            temp.add(this.pop());
        }
        while (!temp.isEmpty()) {
            Card c = temp.elementAt((int) (Math.random() * temp.size()));
            this.push(c);
            temp.removeElement(c);
        }
    }

    public int showSize() {
        System.out.println("Stack Size: " + v.size());
        return v.size();
    }

    public CardStack reverse() {
        Vector<Card> temp = new Vector<>();
        while (!this.empty()) {
            temp.add(this.pop());
        }
        while (!temp.isEmpty()) {
            this.push(temp.remove(0));
        }
        return this;
    }

    public void makeEmpty() {
        v.clear();
    }

    @Override
    public boolean contains(Point p) {
        Rectangle rect = new Rectangle(_x, _y, Card.CARD_WIDTH + 10, Card.CARD_HEIGHT + SPREAD * (v.size() - 1));
        return rect.contains(p);
    }

    public void setXY(int x, int y) {
        _x = x;
        _y = y;
        setBounds(_x, _y, Card.CARD_WIDTH + 10, Card.CARD_HEIGHT + SPREAD * (v.size() - 1));
    }

    public Point getXY() {
        return new Point(_x, _y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (playStack) {
            removeAll();
            ListIterator<Card> iter = v.listIterator();
            Point currentPos = new Point(0, 0);

            while (iter.hasNext()) {
                Card c = iter.next();
                c.setXY(new Point(currentPos.x, currentPos.y));
                c.setBounds(currentPos.x, currentPos.y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
                add(c);
                currentPos.y += SPREAD;
            }

            revalidate();
            repaint();
        }
    }
}
