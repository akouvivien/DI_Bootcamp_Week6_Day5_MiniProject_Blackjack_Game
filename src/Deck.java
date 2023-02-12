import java.util.*;

public class Deck {
    List<Card> cardList=new ArrayList<>();

    public Deck() {
        setCards();
    }

    public void setCards() {
        this.cardList.clear();
        String[] types = {"Coeur", "Trefle", "Pique", "Diamand"};
        Map<String, Integer> titleValueMap = new HashMap<>(){{
            put("2", 2);
            put("3", 3);
            put("4", 4);
            put("5", 5);
            put("6", 6);
            put("7", 7);
            put("8", 8);
            put("9", 9);
            put("10", 10);
            put("Jack", 10);
            put("Reine", 10);
            put("Roi", 10);
            put("Ace", 11);
        }};

        for (int i = 0; i < types.length; i++) {
            int finalI = i;
            titleValueMap.forEach((key, value) -> {
                Card card = new Card(value, types[finalI], key);
                this.cardList.add(card);
            });
        }
    }

    public List<Card> getCartesEnMain(){
        return cardList.stream().filter(c -> c.enMain).toList();
    }

    public List<Card> getCarteRestantes() {
        return cardList.stream().filter(c -> !c.enMain).toList();
    }

    public Card prendre() {
        List<Card> carteRestantes = getCarteRestantes();

        return carteRestantes.size() == 0 // This should <never> happen
                ? null
                : carteRestantes.get(new Random().nextInt(0, carteRestantes.size())).prendreCarte();
    }
}
