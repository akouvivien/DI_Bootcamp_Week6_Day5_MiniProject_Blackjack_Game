import java.util.Random;
import java.util.Scanner;

public class App {
    
    private Deck deck;
    private int balance;

    public App() {
        super();
        this.deck = new Deck();
        this.balance = 500;
    }

    public void setBalance(int value) {
        this.balance = value;
    }

    public int getBalance() {
        return this.balance;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Hand userHand = new Hand();
        Hand dealerHand = new Hand();
        System.out.println("""
                =====================================
                    Bienvenu(e) dans le BLACK JACK
                                
                    Votre budget initial est de: $%s
                    
                    Prêt pour le jeu? C'est parti!!!
                                
                =====================================
                """.formatted(main.getBalance()));
        int valeurMise = 0;
        String userInput = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("""
                    Combien souhaitez-vous miser ?
                    Entrez une valeur entre 1 et %s
                                        
                    *** Entrez 'Quitter(Q)' pour quitter le jeu.
                    """.formatted(main.getBalance()));
            userInput = sc.nextLine();
            if (!userInput.equalsIgnoreCase("quitter") && !userInput.equalsIgnoreCase("q")) {
                try {
                    valeurMise = Integer.valueOf(userInput);
                    if (valeurMise > main.getBalance()) {
                        throw new Exception("La valeur de la mise ne doit pas dépasser le montant de votre budget actuel.");
                    } else if (valeurMise < 1) {
                        throw new Exception("La valeur de la mise doit être d'au moins 1$.");
                    }
                    boolean vitoireUser = main.play(userHand, dealerHand);
                    if (vitoireUser) {
                        System.out.print("""
                                **********************************************
                                    Félicitations, VOUS AVEZ GAGNÉ :-) !!!
                                **********************************************
                                """);
                    } else {
                        System.out.print("""
                                --------------------------------------------
                                    Désolé, VOUS AVEZ PERDU :-( !!!
                                --------------------------------------------
                                """);
                    }
                    System.out.println("============== RECAPITULATIF ==================");
                    System.out.println("\t Vous: " + userHand.getValeur() + " points");
                    userHand.cards.forEach(carte -> System.out.println("[+] " + carte.getTitre() + " de " + carte.getType() + " = " + carte.getValeur() + " points"));
                    System.out.println("\t Dealeur: " + dealerHand.getValeur() + " points");
                    dealerHand.cards.forEach(carte -> System.out.println("[+] " + carte.getTitre() + " de " + carte.getType() + " = " + carte.getValeur() + " points"));
                    main.setBalance(vitoireUser ? main.getBalance() + valeurMise : main.getBalance() - valeurMise);
                    System.out.println();
                    System.out.println("~~~~~~~ Budget actuel: $" + main.getBalance() + " ~~~~~~~~");
                } catch (Exception e) {
                    System.out.println("Saisie invalide. " + e.getMessage());
                    System.out.println("Veuillez saisir une valeur entre 1 et " + main.getBalance());
                }
            } else break;
        } while (main.getBalance() >= 1);
        sc.close();

        System.out.println("############### FIN DU JEU ################");
        System.out.println("Votre budget final est :: $" + main.getBalance());
    }

    public boolean play(Hand userHand, Hand dealerHand) {
        // Au départ, ni le joueur, ni le dealer n'a de carte en main.
        userHand.vider();
        dealerHand.vider();
        this.deck.setCards();

        // Chaque joueur tire 2 cartes pour débuter le jeu, le tirage se fait au hasard
        userHand.ajouterCarte(deck.prendre());
        userHand.ajouterCarte(deck.prendre());

        dealerHand.ajouterCarte(deck.prendre());
        dealerHand.ajouterCarte(deck.prendre());

        return this.verifierCarteEnMain(userHand, dealerHand);
    }

    public boolean verifierCarteEnMain(Hand userHand, Hand dealerHand) {
        if (dealerHand.isBlackjack() || userHand.getValeur() > 21) {
            return false;
        } else if (userHand.isBlackjack() || dealerHand.getValeur() > 21) {
            return true;
        } else {
            Random rand = new Random();
            System.out.println("Vous avez actuellement en main un total de : " + userHand.getValeur() + " points");
            System.out.println("Vos cartes sont :");
            userHand.cards.forEach(carte -> System.out.println("\t" + carte.getTitre() + " de " + carte.getType() + " = " + carte.getValeur() + " points"));
            Card oneCardFromDealerHand = dealerHand.cards.get(rand.nextInt(0, dealerHand.cards.size()));
            System.out.println("Le dealeur possède cette carte: \n\t" + oneCardFromDealerHand.getTitre() + " de " + oneCardFromDealerHand.getType() + " = " + oneCardFromDealerHand.getValeur() + " points");
            System.out.println("Prochain tour?");
            String choixUser = "";
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Prendre une carte (P) ou Passez votre tour (PT):");
                choixUser = scanner.nextLine();
                choixUser = choixUser.trim().toLowerCase();
                if (!(choixUser.equalsIgnoreCase("p") || choixUser.equalsIgnoreCase("pt")))
                    System.out.println("Saisie invalide");
            } while (!(choixUser.equalsIgnoreCase("p") || choixUser.equalsIgnoreCase("pt")));
            // sc.close();
            if (choixUser.equalsIgnoreCase("p")) {
                Card newCard = this.deck.prendre();
                System.out.println("Vous avez pioché: " + newCard.getTitre() + " de " + newCard.getType() + " = " + newCard.getValeur() + " points");
                userHand.ajouterCarte(newCard);
                return verifierCarteEnMain(userHand, dealerHand);
            } else { // Stand
                if (dealerHand.getValeur() <= 16) {
                    do {
                        System.out.println("C'est au tour du dealeur de prendre une carte!");
                        System.out.println("...");
                        dealerHand.ajouterCarte(this.deck.prendre());
                        System.out.println("Le dealeur a maintenant un total de " + dealerHand.getValeur() + " points");
                        System.out.println("Ses cartes sont :");
                        dealerHand.cards.forEach(carte -> System.out.println(carte.getTitre() + " de " + carte.getType() + " = " + carte.getValeur() + " points"));
                    } while (dealerHand.getValeur() <= 16);
                }
                return dealerHand.getValeur() > 21 || userHand.getValeur() > dealerHand.getValeur();
            }
        }
    }
}