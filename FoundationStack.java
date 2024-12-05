import java.util.Stack;

public class FoundationStack extends Stack<Card> {
    public void pushCard(Card card) {
        if (this.isEmpty()) {
            if (card.getValue() == Card.Value.ACE) {
                super.push(card);
            }
        } else {
            Card topCard = this.peek();
            if (isValidMove(card, topCard)) {
                super.push(card);
            }
        }
    }

    private boolean isValidMove(Card card, Card topCard) {
        return card.getSuit() == topCard.getSuit() && card.getValue().ordinal() == topCard.getValue().ordinal() + 1;
    }

    public int showSize() {
        return this.size();
    }
}
