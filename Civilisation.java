
package Conteneur;

	import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.*;
    import java.util.List;

    class Civilisation {
        private String nom;
        private List<String> succes;
        private List<Civilisation> civilisationsAccessibles;
        private String succesChoisi;

        public Civilisation(String nom) {
            this.nom = nom;
            this.succes = new ArrayList<>();
            this.civilisationsAccessibles = new ArrayList<>();
        }

        public String getNom() {
            return nom;
        }

        @Override
        public String toString() {
            return nom;
        }


        public void ajouterSucces(String succes) {
            this.succes.add(succes);
        }

        public List<String> getSucces() {
            return succes;
        }

        public void ajouterCivilisationAccessible(Civilisation civilisation) {
            this.civilisationsAccessibles.add(civilisation);
        }

        public List<Civilisation> getCivilisationsAccessibles() {
            return civilisationsAccessibles;
        }

        public String getSuccesChoisi() {
            return succesChoisi;
        }

        public void setSuccesChoisi(String succesChoisi) {
            this.succesChoisi = succesChoisi;
        }
    }

    class JeuDeSocieteHistorique {
        public List<Civilisation> civilisations;
        private Civilisation civSelectionnee;

        public JeuDeSocieteHistorique() {
            this.civilisations = new ArrayList<>();
        }

        public void ajouterCivilisation(Civilisation civilisation) {
            this.civilisations.add(civilisation);
        }

        public void creerArbreSuccession() {
            // Logique pour créer l'arbre de succession des civilisations
            // Ajoutez les relations de succession entre les civilisations en utilisant les méthodes appropriées des objets Civilisation

            Civilisation sumeriens = new Civilisation("Sumeriens");
            Civilisation akkadiens = new Civilisation("Akkadiens");
            Civilisation assyriens = new Civilisation("Assyriens");
            Civilisation babyloniens = new Civilisation("Babyloniens");
            Civilisation neoBabyloniens = new Civilisation("Neo-Babyloniens");
            Civilisation achemenides = new Civilisation("Achemenides");
            Civilisation lagides = new Civilisation("Lagides");
            Civilisation seleucides = new Civilisation("Seleucides");

            sumeriens.ajouterCivilisationAccessible(akkadiens);
            akkadiens.ajouterCivilisationAccessible(assyriens);
            akkadiens.ajouterCivilisationAccessible(babyloniens);
            babyloniens.ajouterCivilisationAccessible(neoBabyloniens);
            assyriens.ajouterCivilisationAccessible(neoBabyloniens);
            neoBabyloniens.ajouterCivilisationAccessible(achemenides);
            achemenides.ajouterCivilisationAccessible(lagides);
            achemenides.ajouterCivilisationAccessible(seleucides);

            sumeriens.ajouterSucces("Mythe fondateur");
            akkadiens.ajouterSucces("Controler 3 cites-etats");
            assyriens.ajouterSucces("Controler la mesopotamie");
            babyloniens.ajouterSucces("Adopter les lois d'Hamourabi");
            neoBabyloniens.ajouterSucces("Construire les jardins de Babylone");
            achemenides.ajouterSucces("Etendre l'empire perse");
            lagides.ajouterSucces("Construire la bibliotheque d'Alexandrie");
            seleucides.ajouterSucces("Fonder Antioche");
            seleucides.ajouterSucces("Poulet");
            lagides.ajouterSucces("Trouver mon papa");
            akkadiens.ajouterSucces("Pouet");

            civilisations.add(sumeriens);
        }

        public void choisirCivilisationAleatoire() {
            Random random = new Random();
            int index = random.nextInt(civilisations.size());
            civSelectionnee = civilisations.get(index);
        }

        public void choisirCivilisationManuellement(int index) {
            if (index >= 0 && index < civilisations.size()) {
                civSelectionnee = civilisations.get(index);
            } else {
                System.out.println("Index de civilisation invalide.");
            }
        }

        public void afficherArbreSuccession() {
            // Logique pour afficher l'arbre de succession de la civilisation sélectionnée
            // Utilisez les méthodes appropriées des objets Civilisation pour accéder aux informations

            if (civSelectionnee != null) {
                System.out.println("Civilisation sélectionnée : " + civSelectionnee.getNom());
                System.out.println("Succès à réaliser : " + civSelectionnee.getSuccesChoisi());
                System.out.println("Civilisations accessibles :");
                for (Civilisation civ : civSelectionnee.getCivilisationsAccessibles()) {
                    System.out.println("- " + civ.getNom());
                }
            } else {
                System.out.println("Aucune civilisation sélectionnée.");
            }
        }

        public void lancerPartie() {
            System.out.println("La partie va commencer !");
            System.out.println();

            if (civSelectionnee != null) {
                while (true) {
                    afficherArbreSuccession();
                    System.out.println();

                    String choix = JOptionPane.showInputDialog("Que voulez-vous faire ? (1 - Choisir un succès, 2 - Choisir une civilisation, 3 - Afficher tout l'arbre de succession, 4 - Quitter)");
                    if (choix == null || choix.equals("4")) {
                        break;
                    }

                    if (choix.equals("1")) {
                        String succesChoisi = JOptionPane.showInputDialog("Choisissez un succès parmi la liste : " + civSelectionnee.getSucces());
                        if (succesChoisi != null && civSelectionnee.getSucces().contains(succesChoisi)) {
                            civSelectionnee.setSuccesChoisi(succesChoisi);
                            System.out.println("Succès choisi : " + succesChoisi);
                        } else {
                            System.out.println("Succès invalide.");
                        }
                    } else if (choix.equals("2")) {
                        String civilisationChoisie = JOptionPane.showInputDialog("Choisissez une civilisation parmi la liste : " + civSelectionnee.getCivilisationsAccessibles());
                        if (civilisationChoisie != null) {
                            boolean civilisationTrouvee = false;
                            for (Civilisation civ : civSelectionnee.getCivilisationsAccessibles()) {
                                if (civ.getNom().equals(civilisationChoisie)) {
                                    civSelectionnee = civ;
                                    civilisationTrouvee = true;
                                    break;
                                }
                            }
                            if (civilisationTrouvee) {
                                System.out.println("Civilisation choisie : " + civSelectionnee.getNom());
                            } else {
                                System.out.println("Civilisation invalide.");
                            }
                        }
                    } else if (choix.equals("3")) {
                        System.out.println("Arbre de succession complet :");
                        afficherArbreSuccession();
                    } else {
                        System.out.println("Choix invalide.");
                    }

                    System.out.println();
                }
            } else {
                System.out.println("Aucune civilisation sélectionnée.");
            }
        }
    }

