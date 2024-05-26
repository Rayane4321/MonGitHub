// 1. ajouter un poids à chaque article + frais de livraison => poids total de la commande et d'un tableau de frais
//       mois de 2kg : 5€ / entre 2 et 5kg : 7,50€ / au-dessus de 5kg : 15€
// 2. ajouter les clients : client ? commande -> adresse de livraison du client / client -> liste de ses commandes
// 3. pouvoir modifier les quantites commandes (+/-) / valider une commande (donc plus modifiable) / payer un acompte ?

// Un nouveau type de donnée : l'article
class Article {
	private String nom;
	//private float prix; // 10,50€
	private int prix; // le prix en centimes 1050
	private int stock;

	Article(String n, int p, int s) {
		nom = n;
		prix = p;
		stock = s;
	}

	public int getPrix() {
		return prix;
	}

	public int getPrixQuantite(int q) {
		int prixTotal;
		if (q < 10) {
			prixTotal = prix * q;
		} else {
			prixTotal = prix * q * 9 / 10;
		}
		return prixTotal;
	}

	public String toString() {
		int euros = prix / 100;
		int centimes = prix % 100;
		// ... Améliorer l'affichage des centimes pour avoir le 0 devant les centimes < 10 et rien quand c'est 0 centimes
		return "[" + nom + " : " + euros + "€" + centimes + " (" + stock + ")]";
	}
}

class LigneCommande {
	private Article articleCommande;
	private int quantite;

	LigneCommande(Article a, int q) {
		articleCommande = a;
		quantite = q;
	}

	public int getPrix() {
		return articleCommande.getPrixQuantite(quantite);
	}

	public String toString() {
		return articleCommande.toString() + " x" + quantite;
	}
}

class Commande {
	// Tableau avec dans chaque case : un article avec sa quantite
	private LigneCommande[] tableauCommande;
	private int nbArticles;
	
	Commande() {
		tableauCommande = new LigneCommande[10];
		nbArticles = 0;
	}

	public void ajouterArticle(Article a, int q) {
		tableauCommande[nbArticles] = new LigneCommande(a, q);
		nbArticles = nbArticles + 1; // nbArticles += 1 ou nbArticles++
	}

	private int prixTotal() {
		// Parcourir le tableau pour collecter le prix de chaque ligne de la commande
		int prixTotal = 0;
		for (int i = 0; i < nbArticles; i++) {
			prixTotal = prixTotal + tableauCommande[i].getPrix();
		}
		return prixTotal;
	}

	public void afficher() {
		int prixTotal = prixTotal();
		int euros = prixTotal / 100;
		int centimes = prixTotal % 100;
		System.out.println("Commande n°? : ");
		for (int i = 0; i < nbArticles; i++) {
			System.out.println(tableauCommande[i].toString());
		}		
		System.out.println("Prix total : " + euros + "€" + centimes);
	}
}

class Test {
	public static void main(String[] args) {
		Article[] catalogue = new Article[100];
		int nb_articles = 0;

		catalogue[0] = new Article("Pantalon", 2500, 20);
		catalogue[1] = new Article("Veste", 3250, 12);
		catalogue[2] = new Article("Chaussettes", 605, 35);
		nb_articles = 3;
		
		for (int i = 0; i < nb_articles; i++) {
			System.out.println(catalogue[i].toString());
		}

		Commande maCommande = new Commande();
		maCommande.ajouterArticle(catalogue[1], 1);
		maCommande.ajouterArticle(catalogue[0], 2);
		maCommande.ajouterArticle(catalogue[2], 10);

		maCommande.afficher();
	}
}








