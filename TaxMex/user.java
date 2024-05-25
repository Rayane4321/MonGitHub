import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class UtilisateurTest {

    public static void main(String[] args) throws IOException {

        // Create a new Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter their nameA
        System.out.println("Veuillez entrer votre nom : ");
        String nom = scanner.nextLine();

        // Prompt the user to enter their first name
        System.out.println("Veuillez entrer votre prénom : ");
        String prenom = scanner.nextLine();

        // Prompt the user to enter their email address
        System.out.println("Veuillez entrer votre adresse e-mail : ");
        String email = scanner.nextLine();

        // Prompt the user to enter their password
        System.out.println("Veuillez entrer votre mot de passe : ");
        String motDePasse = scanner.nextLine();

        // Validate the user input
        if (Utilisateur.isValidNom(nom)) {
            System.out.println("Nom valide : " + nom);
        } else {
            System.out.println("Nom invalide : " + nom);
        }

        if (Utilisateur.isValidPrenom(prenom)) {
            System.out.println("Prénom valide : " + prenom);
        } else {
            System.out.println("Prénom invalide : " + prenom);
        }

        if (Utilisateur.isValidEmail(email)) {
            System.out.println("Adresse email valide : " + email);
        } else {
            System.out.println("Adresse email invalide : " + email);
        }

        if (Utilisateur.isValidMotDePasse(motDePasse)) {
            System.out.println("Mot de passe valide : " + motDePasse);
        } else {
            System.out.println("Mot de passe invalide : " + motDePasse);
        }

        // Save the user information to a file
        Utilisateur.enregistrerInformations(nom, prenom, email, motDePasse);
        System.out.println("Informations enregistrées avec succès.");
    }
}


class Utilisateur {

    String nom;
    String prenom;
    String email;
    String motDePasse;

    public Utilisateur(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public static boolean isValidNom(String nom) {
        return nom != null && nom.matches("[a-zA-Z]+");
    }

    public static boolean isValidPrenom(String prenom) {
        return prenom != null && prenom.matches("[a-zA-Z]+");
    }

     public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isValidMotDePasse(String motDePasse) {
        return motDePasse != null && motDePasse.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=])[a-zA-Z0-9@#$%^&+=]{8,}$");
    }
    public static void enregistrerInformations(String nom, String prenom, String email, String motDePasse) throws IOException {
        FileWriter fichier = new FileWriter("informations.txt", true);
        fichier.write(nom + "\n");
        fichier.write(prenom + "\n");
        fichier.write(email + "\n");
        fichier.write(motDePasse +"\n");
    }
}