import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class client {
	private String nom;
	private String prenom;
	private int age;
	private String num_tel;

	//public static void client(String n, String p, int a, String nt){

	//}
}

class chauffeur {
	private String nom;
	private String prenom;
	private voiture voiture_chauffeur;
	private String num_tel;

	public static void chauffeur(String n, String p, voiture v, String nt){
		nom = n;
		prenom = p;
		voiture = v;
		num_tel = nt;
	}
}

class voiture {
	private String marque;
	private String modele;
	private String matricule;
	private int nombre_places;
	private double prix;

	public static void voiture(String m, String md, String mat, int np, double p){
		marque = m;
		modele = md;
		matricule = mat;
		nombre_places = np;
		prix = p;
	}
}

class Commande {
    private Chauffeur monChauffeur;
    private String adresse_dep;
    private String adresse_arr;
    private int montant;

    public static void main(String[] args) {
        maCommande.passerCommande();
        // Le reste de votre code...
    }

	public class Commande {
	    private ChauffeurCommande monChauffeur;
	    private String adresse_dep;
	    private String adresse_arr;
	    private int montant;

	    // ...

	    private int getMontant() {
	    	return montant;
	    }

	    private boolean passerCommande() {
	        try {
	            // Charger le pilote JDBC SQLite
	            Class.forName("org.sqlite.JDBC");

	            // Connexion à la base de données SQLite (utilisez le chemin correct vers votre fichier .db)
	            Connection connection = DriverManager.getConnection("jdbc:sqlite:taxmex.db");

	            // Exécuter une requête pour récupérer les noms et prénoms des chauffeurs
	            String query = "SELECT nom_chauffeur, prenom_chauffeur, tarif_chauffeur FROM chauffeur";
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            // Afficher "Choisissez un de nos chauffeurs :"
	            System.out.println("Choisissez un de nos chauffeurs :");

	            // Afficher les noms et prénoms des chauffeurs
	            while (resultSet.next()) {
	                String nom = resultSet.getString("nom_chauffeur");
	                String prenom = resultSet.getString("prenom_chauffeur");
	                int tarif = resultSet.getInt("tarif_chauffeur");
	                System.out.println(nom + " " + prenom + " prix :" + tarif);
	            }

	            // Fermer les ressources
	            resultSet.close();
	            preparedStatement.close();
	            connection.close();
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            return false;
	        }

	        // Scanner et traitement de l'entrée utilisateur
	        Scanner sc = new Scanner(System.in);
	        System.out.println("Veuillez saisir le nom du chauffeur :");
	        String nomChauffeur = sc.next();
	        System.out.println("Veuillez saisir le prénom du chauffeur :");
	        String prenomChauffeur = sc.next();
	        System.out.println("Où devons nous aller vous chercher ?");
	        adresse_arr = sc.next();
	        System.out.println("Où devons nous vous déposer ?");
	        adresse_dep = sc.next();

	        System.out.println("Vous avez choisi :" + nomChauffeur + " " + prenomChauffeur +
	                " pour vous amener de :" + adresse_dep + " jusqu'à :" + adresse_arr +
	                ", cela ferait :" + montant + "$, souhaitez-vous valider la commande ?");
	        String reponse = sc.next();

	        if (reponse.equalsIgnoreCase("oui")) {
	            this.monChauffeur = new ChauffeurCommande(nomChauffeur, prenomChauffeur);
	            this.adresse_dep = adresse_dep;
	            this.adresse_arr = adresse_arr;
	            // Vous devez définir la valeur de montant ici (je ne vois pas où elle est définie dans votre code)
	            this.montant = montant;
	            return true;
	        } else {
	            System.out.println("La commande a bien été annulée, veuillez patienter...");
	            menu();
	            return false;
	        }
	    }
	    
	    // ...

}

class GestionnaireConnexionSQLite {

    // Méthode pour se connecter avec un nom d'utilisateur et un mot de passe
    public boolean seConnecter(String nomUtilisateur, String motDePasse) {
        // Effectuer les contrôles de validation en Java
        if (validerNomUtilisateur(nomUtilisateur) && validerMotDePasse(motDePasse)) {
            // Si les contrôles en Java passent, on peut vérifier dans la base de données
            return verifierDansBaseDeDonnees(nomUtilisateur, motDePasse);
        } else {
            // Sinon, renvoyer false si les contrôles échouent
            return false;
        }
    }

    // Méthode pour s'inscrire avec un nom d'utilisateur et un mot de passe
    public boolean sInscrire(String nomUtilisateur, String motDePasse) {
        // Effectuer les contrôles de validation en Java
        if (validerNomUtilisateur(nomUtilisateur) && validerMotDePasse(motDePasse)) {
            // Si les contrôles en Java passent, on peut procéder à l'inscription dans la base de données
            return inscrireDansBaseDeDonnees(nomUtilisateur, motDePasse);
        } else {
            // Sinon, renvoyer false si les contrôles échouent
            return false;
        }
    }

    // Méthode pour valider le nom d'utilisateur
    private boolean validerNomUtilisateur(String nomUtilisateur) {
        // Ajouter vos conditions de validation ici
        // Par exemple, vérifier la longueur, les caractères autorisés, etc.
        return !nomUtilisateur.isEmpty();
    }

    // Méthode pour valider le mot de passe
    private boolean validerMotDePasse(String motDePasse) {
        // Ajouter vos conditions de validation ici
        // Par exemple, vérifier la longueur, la complexité, etc.
        return motDePasse.length() >= 8;
    }

    // Méthode pour vérifier les informations dans la base de données SQLite
    private boolean verifierDansBaseDeDonnees(String nomUtilisateur, String motDePasse) {
        // Établir une connexion à la base de données SQLite
        try (Connection connexion = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            // Utiliser une requête préparée pour éviter les injections SQL
            String requete = "SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
                preparedStatement.setString(1, nomUtilisateur);
                preparedStatement.setString(2, motDePasse);
                // Exécuter la requête
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Retourner true si des résultats sont trouvés, indiquant une correspondance dans la base de données
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            // Gérer les erreurs de connexion à la base de données ici
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour inscrire les informations dans la base de données SQLite
    private boolean inscrireDansBaseDeDonnees(String nomUtilisateur, String motDePasse) {
        // Établir une connexion à la base de données SQLite
        try (Connection connexion = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            // Utiliser une requête préparée pour éviter les injections SQL
            String requete = "INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
                preparedStatement.setString(1, nomUtilisateur);
                preparedStatement.setString(2, motDePasse);
                // Exécuter la requête d'insertion
                int lignesAffectees = preparedStatement.executeUpdate();
                
                // Retourner true si au moins une ligne a été affectée (inscription réussie)
                return lignesAffectees > 0;
            }
        } catch (SQLException e) {
            // Gérer les erreurs de connexion à la base de données ici
            e.printStackTrace();
            return false;
        }
    }
}


class ChauffeurCommande {
    private String nom;
    private String prenom;

    public ChauffeurCommande(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}

class Payer {
    private static Scanner sc = new Scanner(System.in);

    private int numero_carte;
    private String date_exp;
    private String code;

    public static void main(String[] args) {
        monPayement.saisirDonneesBancaires();
    }

    public void saisirDonneesBancaires() {
        boolean formatValide = false;

        while (!formatValide) {
            System.out.println("Saisissez le numéro de votre carte (16 chiffres) :");
            String numeroCarteInput = sc.next();

            System.out.println("Saisissez la date d'expiration (format : XX/XX) :");
            String dateExpInput = sc.next();

            System.out.println("Saisissez votre code secret (3 chiffres) :");
            String codeInput = sc.next();

            // Vérifier le format avec des expressions régulières
            if (validerFormatNumeroCarte(numeroCarteInput) && validerFormatDateExp(dateExpInput) && validerFormatCode(codeInput)) {
                formatValide = true;
                this.numero_carte = Integer.parseInt(numeroCarteInput);
                this.date_exp = dateExpInput;
                this.code = codeInput;
            } else {
                System.out.println("Format invalide. Veuillez saisir les données bancaires à nouveau.");
            }
        }

        // Utilisez maintenant this.numero_carte, this.date_exp, this.code comme nécessaire
    }

    private boolean validerFormatNumeroCarte(String numeroCarte) {
        // Vérifier si le numéro de carte a 16 chiffres
        return numeroCarte.matches("\\d{16}");
    }

    private boolean validerFormatDateExp(String dateExp) {
        // Vérifier si la date d'expiration est au format XX/XX
        return dateExp.matches("\\d{2}/\\d{2}");
    }

    private boolean validerFormatCode(String code) {
        // Vérifier si le code a 3 chiffres
        return code.matches("\\d{3}");
    }
}

public static void menu() {
	Scanner sc = new Scanner(System.in);
	if (etat == 1){
		System.out.println("Bienvenu/e sur TaxMex, l'appli d'UBER n°1 en France");
		System.out.println("Quelle action shouaitez vous faire? :");
		System.out.println("Pour créer un compte, tappez 1");
		System.out.println("Si vous avez deja un copmte, tappez 2");
		System.out.println("Si vous voulez candidater pour devenir un de nos chauffeurs, tappez 3");
		Sysyem.out.println("Si vous voulez savoir plus sur notre entreprise, tappez 4");
		int choix = sc.nextInt();
	}
	else {
		System.out.println("Quelle action shouaitez vous faire? :");
		System.out.println("Pour consulter votre compte, tappez 1");
		System.out.println("Pour reserver un vehicule, tappez 2");
		System.out.println("Pour vous déconneter, tappez 3");
		Sysyem.out.println("Si vous voulez savoir plus sur notre entreprise, tappez 4");
		int choix = sc.nextInt();
	}
	return choix;
	
}

private void SurNous(){

     System.out.println("Bienvenue chez TaxMex");
     System.out.println("");
     System.out.println("Le premier service de VTC Mexicain en France.");
     System.out.println("");
     System.out.println("Nous sommes là pour vous aider à vous déplacer aussi vite que Speedy Goncalez.");
     System.out.println("Ici nos chauffeurs ne vous offrent pas de simples sachets de bonbons mais plutot des");
     System.out.println("tacos et autres plats mexicains typiques. Si vous avez la moindre question, n'hésitez");
     System.out.println("pas à contacter notre équipe soutien incroyablement sympathique qui ne parle pas");
     System.out.println("français. Nous vous conseillons donc plutôt d’utiliser notre chat en direct qui est");
     System.out.println("aussi en espagnol. Vous aurez au moins le temps d’utiliser n’importe quel traducteur");
     System.out.println("en ligne. Si vous n’êtes pas découragés de nous appeler, notre équipe est prête à");
     System.out.println("vous aider avec tout ce dont vous avez besoin, que ce soit pour vous aider à trouver");
     System.out.println("le meilleur itinéraire ou pour vous recommander les meilleurs restaurants mexicains");
     System.out.println("de la ville. Nous sommes ici pour vous, 24 heures sur 24, 7 jours sur 7, pour vous");
     System.out.println("aider à obtenir le meilleur service de taxi mexicain possible. Alors, la prochaine");
     System.out.println("fois que vous avez besoin de vous déplacer dans la ville,pensez à Tax-Mex, parce");
     System.out.println("que nous sommes plus chauds que la salsa et plus rapides que les enchiladas!");


 }


public static void main(String[] args) {
	menu();
	if (etat == 1 && choix == 1){

	}
	else if(etat == 1 && choix == 2){

	}
	else if(etat == 1 && choix == 3){

	}
	else if(etat == 1 && choix == 4){

	}
	else if(etat == 2 && choix == 1){

	}
	else if(etat == 2 && choix == 2){
		maCommande = new Commande();
		if (maCommande() == true ){
			Payer monPayement = new Payer();
			monPayement();
		}
		else {
			menu();
		}
	}

	else if(etat == 2 && choix == 3){

	}
	else if(etat == 2 && choix == 4){
 
	}
}
}

