    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.regex.*;
    import java.util.Scanner;

    
    class Main {
        private static int etat;
        private static int choix;
        private static Commande maCommande;
        private static String abandon;
        private static String permanence;
        private static AttenteTemps attente = new AttenteTemps();
        private static GestionnaireConnexionSQLite connexion = new GestionnaireConnexionSQLite();

        public static void menu() {
            Scanner sc = new Scanner(System.in);
            if (etat == 1) {
                System.out.println("Bienvenu/e sur TaxMex, l'appli d'UBER n°1 en France");
                System.out.println("Quelle action souhaitez vous faire? :");
                System.out.println("Pour creer un compte, tappez 1");
                System.out.println("Si vous avez deja un copmte, tappez 2");
                System.out.println("Si vous voulez candidater pour devenir un de nos chauffeurs, tappez 3");
                System.out.println("Si vous voulez savoir plus sur notre entreprise, tappez 4");
                System.out.println();
                System.out.println();
                choix = sc.nextInt();
            } else {
                System.out.println("Quelle action souhaitez vous faire? :");
                System.out.println("Pour reserver un vehicule, tappez 1");
                System.out.println("Pour vous deconneter, tappez 2");
                System.out.println("Pour consulter le trajer en cours, tappez 3");
                System.out.println("Si vous voulez savoir plus sur notre entreprise, tappez 4");
                System.out.println();
                System.out.println();
                choix = sc.nextInt();
            }
        }

        public static void surNous() {
            System.out.println("Bienvenue chez TaxMex");
            System.out.println("");
            System.out.println("Le premier service de VTC Mexicain en France.");
            System.out.println("");
            System.out.println("Nous sommes la pour vous aider a vous deplacer aussi vite que Speedy Goncalez.");
            System.out.println("Ici nos chauffeurs ne vous offrent pas de simples sachets de bonbons mais plutot des");
            System.out.println("tacos et autres plats mexicains typiques. Si vous avez la moindre question, n'hesitez");
            System.out.println("pas a contacter notre equipe soutien incroyablement sympathique qui ne parle pas");
            System.out.println("francais. Nous vous conseillons donc plutot d'utiliser notre chat en direct qui est");
            System.out.println("aussi en espagnol. Vous aurez au moins le temps d'utiliser n'importe quel traducteur");
            System.out.println("en ligne. Si vous n'etes pas decourages de nous appeler, notre equipe est prete a");
            System.out.println("vous aider avec tout ce dont vous avez besoin, que ce soit pour vous aider a trouver");
            System.out.println("le meilleur itineraire ou pour vous recommander les meilleurs restaurants mexicains");
            System.out.println("de la ville. Nous sommes ici pour vous, 24 heures sur 24, 7 jours sur 7, pour vous");
            System.out.println("aider a obtenir le meilleur service de taxi mexicain possible. Alors, la prochaine");
            System.out.println("fois que vous avez besoin de vous deplacer dans la ville, pensez a Tax-Mex, parce");
            System.out.println("que nous sommes plus chauds que la salsa et plus rapides que les enchiladas!");
            System.out.println();
            System.out.println();
        }

    public static void postulerChauffeur() {
        // Informations de connexion à la base de données
        String url = "jdbc:sqlite:taxmex.db";

        try {
            // Établissement de la connexion à la base de données
            Connection connexion = DriverManager.getConnection(url);

            // Saisie des données par l'utilisateur
            Scanner scanner = new Scanner(System.in);
            System.out.println("Veuillez saisir votre nom : ");
            String nom = scanner.nextLine();
            System.out.println();
            System.out.println("Veuillez saisir votre prenom : ");
            String prenom = scanner.nextLine();
            System.out.println();
            System.out.println("Veuillez saisir votre email : ");
            String email = scanner.nextLine();
            System.out.println();
            System.out.println();

            // Préparation de la requête SQL
            String sql = "INSERT INTO candidat_chauffeur (nom_candidat_chauffeur, prenom_candidat_chauffeur, email_candidat_chauffeur) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, email);

            // Exécution de la requête
            int lignesModifiees = preparedStatement.executeUpdate();

            // Vérification du succès de l'opération
            if (lignesModifiees > 0) {
                attente.attendre(3000);
                System.out.println("Votre demande a ete envoye avec succes, vous devrez recevoir un message de confirmation \n dans votre boite mail dans un delai de 14 jours ouvres");
                System.out.println();
                System.out.println();
            } else {
                attente.attendre(5000);
                System.out.println("Erreur lors de l'enregistrement de votre demande, ressayez ulterieurement.");
                System.out.println();
                System.out.println();
            }


            // Fermeture des ressources
            preparedStatement.close();
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        permanence = "oui";
        abandon = "non";
        etat = 1;
        while (abandon.equalsIgnoreCase("non") && permanence.equalsIgnoreCase("oui")) {
            menu();
            if (etat == 1 && choix == 1) {
                connexion.sInscrire();
                System.out.println();
                System.out.println();

            } else if (etat == 1 && choix == 2) {
                connexion.seConnecter();
                System.out.println();
                System.out.println();
                etat = 2;

            } else if (etat == 1 && choix == 3) {
                postulerChauffeur();
                System.out.println("souhaitez vous abandonner l'application ?");
                System.out.println();
                System.out.println();
                abandon = sc.nextLine();
                System.out.println();
                System.out.println();

            } else if (etat == 1 && choix == 4) {
                surNous();
                System.out.println("souhaitez vous abandonner l'application ?");
                System.out.println();
                System.out.println();
                abandon = sc.nextLine();
                System.out.println();
                System.out.println();

            } else if (etat == 2 && choix == 1) {
                maCommande = new Commande();
                if (maCommande.passerCommande()) {
                    Payer monPayement = new Payer();
                    monPayement.saisirDonneesBancaires();
                    System.out.println("souhaitez vous abandonner l'application ?");
                    System.out.println();
                    System.out.println();
                    abandon = sc.nextLine();
                    System.out.println();
                    System.out.println();
                    } else {
                        System.out.println("souhaitez vous abandonner l'application ?");
                        System.out.println();
                        System.out.println();
                        abandon = sc.nextLine();
                        System.out.println();
                        System.out.println();
                    }

            } else if (etat == 2 && choix == 2) {
                etat = 1;
                System.out.println("Deconnexion en cours...");
                attente.attendre(5000);
                System.out.println("Deconnexion reussie !");
                attente.attendre(3000);
                System.out.println();
                System.out.println();

            } else if (etat == 2 && choix == 3) {
                maCommande.getCommande();
                System.out.println("souhaitez vous retourner au menu ?");
                System.out.println();
                System.out.println();
                permanence = sc.nextLine();

            } else if (etat == 2 && choix == 4) {
                surNous();
                System.out.println("souhaitez vous abandonner l'application ?");
                System.out.println();
                System.out.println();
                abandon = sc.nextLine();
            }
        }
    }
}

class AttenteTemps {
    public void attendre(int tempsEnMillisecondes) {
        try {
            Thread.sleep(tempsEnMillisecondes);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ClientActuel {
    private String nomUtilisateur;
    private String motDePasse;

    public ClientActuel(String nomUtilisateur, String motDePasse) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
    }

    public String getUtilisateur() {
        return nomUtilisateur;
    }

    public String getmotDePasse() {
        return motDePasse;
    }

}

class ChauffeurCommande {
    private String nom;
    private String prenom;

    public ChauffeurCommande(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNomCC() {
        return nom;
    }

    public String getPrenomCC() {
        return prenom;
    }
}

class Commande {
    private ChauffeurCommande monChauffeur;
    private String adresse_dep;
    private String adresse_arr;
    private double montant;

    public boolean passerCommande() {
        String nomChauffeur = "";
        String prenomChauffeur = "";
        try {
                // Connexion à la base de données SQLite (utilisez le chemin correct vers votre fichier .db)
                Connection connection = DriverManager.getConnection("jdbc:sqlite:taxmex.db");

                // Exécuter une requête pour récupérer les noms et prénoms des chauffeurs
                String query = "SELECT id_chauffeur, nom_chauffeur, prenom_chauffeur, tarif_chauffeur FROM chauffeur";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Afficher "Choisissez un de nos chauffeurs :"
                System.out.println("Choisissez un de nos chauffeurs (tappez le numero associe) :");

                // Afficher les noms et prénoms des chauffeurs
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_chauffeur");
                    String nom = resultSet.getString("nom_chauffeur");
                    String prenom = resultSet.getString("prenom_chauffeur");
                    int montant = resultSet.getInt("tarif_chauffeur");
                    System.out.println(id + " " + nom + " " + prenom + " prix : " + montant);
                }
                System.out.println();
                System.out.println();

                // Fermer les ressources
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

                // Scanner et traitement de l'entrée utilisateur
            Scanner sc = new Scanner(System.in);
            System.out.println("Quel est votre choix ? :");
            String idChauffeur = sc.nextLine();
            System.out.println();

            try {
                // Connexion à la base de données SQLite (utilisez le chemin correct vers votre fichier .db)
                Connection connection = DriverManager.getConnection("jdbc:sqlite:taxmex.db");

                // Exécuter une requête pour récupérer les noms et prénoms des chauffeurs
                String query = "SELECT nom_chauffeur, prenom_chauffeur, tarif_chauffeur FROM chauffeur WHERE id_chauffeur = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, idChauffeur);
                ResultSet resultSet = preparedStatement.executeQuery();

                nomChauffeur = resultSet.getString("nom_chauffeur");
                prenomChauffeur = resultSet.getString("prenom_chauffeur");
                double montant = resultSet.getDouble("tarif_chauffeur");

                System.out.println("Où devons nous aller vous chercher ?");
                adresse_arr = sc.nextLine();
                System.out.println();

                System.out.println("Où devons nous vous déposer ?");
                adresse_dep = sc.nextLine();

                System.out.println();
                System.out.println();

                System.out.println("Vous avez choisi :" + nomChauffeur + " " + prenomChauffeur +
                " \n pour vous amener de :" + adresse_dep + " jusqu'à :" + adresse_arr +
                    ", cela ferait :" + montant + "$,\n souhaitez-vous valider la commande ?");
                System.out.println();
                String reponse = sc.nextLine();
                System.out.println();

                if (reponse.equalsIgnoreCase("oui")) {
                    this.monChauffeur = new ChauffeurCommande(nomChauffeur, prenomChauffeur);
                    this.adresse_dep = adresse_dep;
                    this.adresse_arr = adresse_arr;
                    // Vous devez définir la valeur de montant ici (je ne vois pas où elle est définie dans votre code)
                    this.montant = montant;
                    System.out.println();
                    System.out.println();
                } else {
                    System.out.println("La commande a bien été annulée, veuillez patienter...");
                    System.out.println();
                    System.out.println();
                }

                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        public void getCommande() {
            System.out.println("Vous avez choisi un trajet accompagné de notre chauffeur:" + monChauffeur.getNomCC() + " " + monChauffeur.getPrenomCC() + "\n adresse de depart : " + adresse_dep + " adresse d'arrive : " + adresse_arr + " pour un prix de : " + montant + "$.");
            System.out.println();
            System.out.println();
        }
    }

class Payer {
    private static Scanner sc = new Scanner(System.in);

    private String numero_carte;
    private String date_exp;
    private String code;

    // Méthode pour saisir les données bancaires
    public void saisirDonneesBancaires() {
        boolean formatValide = false;

        while (!formatValide) {
            System.out.println("Saisissez le numéro de votre carte (16 chiffres) :");
            String numeroCarteInput = sc.next();
            System.out.println();

            System.out.println("Saisissez la date d'expiration (format : XX/XX) :");
            String dateExpInput = sc.next();
            System.out.println();

            System.out.println("Saisissez votre code secret (3 chiffres) :");
            String codeInput = sc.next();
            System.out.println();

            System.out.println();
            System.out.println();

            // Vérifier le format avec des expressions régulières
            if (validerFormatNumeroCarte(numeroCarteInput) && validerFormatDateExp(dateExpInput) && validerFormatCode(codeInput)) {
                formatValide = true;
                this.numero_carte = numeroCarteInput;
                this.date_exp = dateExpInput;
                this.code = codeInput;
                System.out.println("Votre commande a bien ete valide !");
                System.out.println();
                System.out.println();
            } else {
                System.out.println("Format invalide. Veuillez saisir les données bancaires à nouveau.");
                System.out.println();
                System.out.println();
            }
        }

    }

    // Méthode pour valider le format du numéro de carte
    private boolean validerFormatNumeroCarte(String numeroCarte) {
        return numeroCarte.matches("\\d{16}");
    }

    // Méthode pour valider le format de la date d'expiration
    private boolean validerFormatDateExp(String dateExp) {
        return dateExp.matches("\\d{2}/\\d{2}");
    }

    // Méthode pour valider le format du code
    private boolean validerFormatCode(String code) {
        return code.matches("\\d{3}");
    }
}


class GestionnaireConnexionSQLite {

    private static GestionnaireConnexionSQLite connexion2 = new GestionnaireConnexionSQLite();
    private static AttenteTemps attente = new AttenteTemps();
    ClientActuel clientActuel = new ClientActuel("username", "motDePasse");

    public void setClientActuel(ClientActuel clientActuel) {
        this.clientActuel = clientActuel;
    }

     // Méthode pour se connecter avec un nom d'utilisateur et un mot de passe
    public ClientActuel seConnecter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduisez votre Username : ");
        String username = scanner.nextLine();
        System.out.println();
        System.out.println("Avez vous oublie votre mot de passe ?");
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("oui")) {
            reinitialiserMotDePasse(username);
        } else {
            System.out.println("Introduisez votre mot de passe :");
            String motDePasse = scanner.nextLine();
            System.out.println();

            // Effectuer les contrôles de validation en Java
            if (validerUtilisateur(username) && validerMotDePasse(motDePasse)) {
                // Si les contrôles en Java passent, on peut vérifier dans la base de données
                boolean verificationBaseDeDonnees = verifierDansBaseDeDonnees(username, motDePasse);

                if (verificationBaseDeDonnees) {
                    // Créer un objet clientActuel avec les données actuelles
                    ClientActuel clientActuel2 = new ClientActuel(username, motDePasse);
                    setClientActuel(clientActuel2);

                    return clientActuel;
                }
            } else {
                System.out.println("Le mot de passe saisi ne respecte pas les normes de securite \n (8 caracteres minimum et au moins une majuscule, minuscule, chiffre et symbole)");
                seConnecter();
                }
        }
        
        return null;
    }

    // Méthode pour s'inscrire avec un nom d'utilisateur et un mot de passe
    public boolean sInscrire() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quel est votre nom ?");
        String nomUtilisateur = scanner.nextLine();
        System.out.println();
        System.out.println("Quel est votre prenom ?");
        String prenomUtilisateur = scanner.nextLine();
        System.out.println();
        System.out.println("Introduisez votre Username : ");
        String username = scanner.nextLine();       
        System.out.println();
        System.out.println("Introduisez votre mot de passe :");
        String motDePasse = scanner.nextLine();
        System.out.println();
        System.out.println("Quel est votre adresse mail ?");
        String email = scanner.nextLine();
        System.out.println();
        System.out.println();

        // Effectuer les contrôles de validation en Java
        if (validerUtilisateur(username) && validerUtilisateur(nomUtilisateur) && validerUtilisateur(prenomUtilisateur) && validerUtilisateur(email) && validerMotDePasse(motDePasse)) {
            // Si les contrôles en Java passent, on peut procéder à l'inscription dans la base de données
            return inscrireDansBaseDeDonnees(nomUtilisateur, prenomUtilisateur, motDePasse, username, email);
        } else {
            System.out.println("Utilisateur ou mot de passe incorrecter");
            System.out.println("Assurez vous de respecter la syntaxe du mot de passe \n (8 caracteres minimum et au moins une majuscule, minuscule, chiffre et symbole)");

            return false;
        }
    }

    // Méthode pour valider le nom d'utilisateur
    private boolean validerUtilisateur(String username) {
        return !username.isEmpty();
    }

    // Méthode pour valider le mot de passe
    private boolean validerMotDePasse(String motDePasse) {
        // Vérifier la longueur minimale
        if (motDePasse.length() < 8) {
            return false;
        }

        // Vérifier la présence d'au moins une majuscule, un chiffre, un symbole et une minuscule
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(motDePasse);

        return matcher.matches();
    }

    // Méthode pour vérifier les informations dans la base de données SQLite
    public boolean verifierDansBaseDeDonnees(String username, String motDePasse) {
        // Établir une connexion à la base de données SQLite
        try (Connection connexion = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            // Utiliser une requête préparée pour éviter les injections SQL
            String requete = "SELECT * FROM utilisateur WHERE username_utilisateur = ? AND mot_de_passe_utilisateur = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
                preparedStatement.setString(1, username);
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
    // Méthode pour réinitialiser le mot de passe en cas d'oubli
    public boolean reinitialiserMotDePasse(String username) {
        Scanner scanner = new Scanner(System.in);
        // Vérifier si l'utilisateur existe dans la base de données
        if (utilisateurExiste(username)) {
            System.out.println("Introduisez votre nouveau mot de passe : ");
            String nouveauMotDePasse = scanner.nextLine();
            System.out.println();
            System.out.println();
            // Mettre à jour le mot de passe dans la base de données
            if (mettreAJourMotDePasse(username, nouveauMotDePasse)) {
                attente.attendre(3000);
                System.out.println("Votre mot de passe a ete change.");
                System.out.println();
                System.out.println();
                return true;
            }
        }
        return false;
    }

    // Méthode pour vérifier si un utilisateur existe dans la base de données
    private boolean utilisateurExiste(String username) {
        try (Connection connexion = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            String requete = "SELECT * FROM utilisateur WHERE username_utilisateur = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Retourne true si l'utilisateur existe
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour mettre à jour le mot de passe dans la base de données
    private boolean mettreAJourMotDePasse(String username, String nouveauMotDePasse) {
        try (Connection connexion = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            String requete = "UPDATE utilisateur SET mot_de_passe_utilisateur = ? WHERE username_utilisateur = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
                preparedStatement.setString(1, nouveauMotDePasse);
                preparedStatement.setString(2, username);
                int lignesModifiees = preparedStatement.executeUpdate();
                return lignesModifiees > 0; // Retourne true si la mise à jour a réussi
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean inscrireDansBaseDeDonnees(String nomUtilisateur,String prenomUtilisateur, String motDePasse, String username, String email) {
        // Établir une connexion à la base de données SQLite
        try (Connection connexion = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            // Utiliser une requête préparée pour éviter les injections SQL
            String requete = "INSERT INTO utilisateur (nom_utilisateur, prenom_utilisateur, mot_de_passe_utilisateur, username_utilisateur, email_utilisateur) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {
                preparedStatement.setString(1, nomUtilisateur);
                preparedStatement.setString(2, prenomUtilisateur);
                preparedStatement.setString(3, motDePasse);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, email);
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

