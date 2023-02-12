public class Card {
    private int valeur;
    private String type;
    private String titre;

    public boolean enMain = false;

    public Card(int valeur, String type, String titre) {
        this.valeur = valeur;
        this.type = type;
        this.titre = titre;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Card prendreCarte() {
        this.enMain = true;

        return this;
    }
}
