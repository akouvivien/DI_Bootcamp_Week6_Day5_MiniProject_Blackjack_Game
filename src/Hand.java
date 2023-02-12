import java.util.ArrayList;
import java.util.List;

public class Hand {
    List<Card> cards = new ArrayList<>();

    public int getValeur() {
        return cards.stream().mapToInt(card -> card.getValeur()).sum();
    }

    public void ajouterCarte(Card card) {
        this.cards.add(card);
    }

    public boolean isBlackjack() {
        return getValeur() == 21;
    }

    public void vider() {
        this.cards.clear();
    }
}
