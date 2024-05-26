package Conteneur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuccessionArbre {
    private List<Civilisation> civilisations;
    private Civilisation sumeriens;

    public SuccessionArbre() {
        civilisations = new ArrayList<>();
        sumeriens = new Civilisation("Sumeriens");
    }

    public void ajouterCivilisation(Civilisation civilisation) {
        civilisations.add(civilisation);
    }

    public void creerArbreSuccession() {
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

        sumeriens.ajouterSucces("Inventer l'écriture cunéiforme");
        sumeriens.ajouterSucces("Construire des ziggourats");
        neoBabyloniens.ajouterSucces("Reconstruire Babylone");
        achemenides.ajouterSucces("Fonder un empire perse");
        lagides.ajouterSucces("Construire la bibliothèque d'Alexandrie");
        seleucides.ajouterSucces("Contrôler les satrapies orientales");
        achemenides.ajouterSucces("Construire voie royale");
        lagides.ajouterSucces("Se marier avec Cléopâtre");
        seleucides.ajouterSucces("Contrôler la Perse");
        sumeriens.ajouterSucces("Mythe fondateur");
        akkadiens.ajouterSucces("Contrôler 3 cités-états");
        assyriens.ajouterSucces("Contrôler la Mésopotamie");
        babyloniens.ajouterSucces("Adopter les lois d'Hammourabi");
        neoBabyloniens.ajouterSucces("Construire les jardins de Babylone");
        assyriens.ajouterSucces("Construire 2 capitales");
        seleucides.ajouterSucces("Chasser des poulets");
        akkadiens.ajouterSucces("Poète");
        lagides.ajouterSucces("Mariage");

        ajouterCivilisation(sumeriens);
        ajouterCivilisation(akkadiens);
        ajouterCivilisation(assyriens);
        ajouterCivilisation(babyloniens);
        ajouterCivilisation(neoBabyloniens);
        ajouterCivilisation(achemenides);
        ajouterCivilisation(lagides);
        ajouterCivilisation(seleucides);
    }

    public void choisirSuccesManuelPourCivilisations() {
        JFrame frame = new JFrame("Arbre de Succession");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(civilisations.size(), 3));

        for (Civilisation civilisation : civilisations) {
            JLabel label = new JLabel(civilisation.getNom());
            panel.add(label);

            JLabel succesLabel = new JLabel("Succes :");
            panel.add(succesLabel);

            JComboBox<String> comboBox = new JComboBox<>(civilisation.getSucces().toArray(new String[0]));
            panel.add(comboBox);
        }

        JButton boutonValider = new JButton("Valider");
        boutonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < civilisations.size(); i++) {
                    Component composant = panel.getComponent(i * 3 + 2);
                    if (composant instanceof JComboBox) {
                        JComboBox<?> comboBox = (JComboBox<?>) composant;
                        String succesChoisi = (String) comboBox.getSelectedItem();
                        if (succesChoisi != null) {
                            civilisations.get(i).setSuccesChoisi(succesChoisi);
                            System.out.println("Succes choisi pour la civilisation " + civilisations.get(i).getNom() + " : " + succesChoisi);
                            if (succesChoisi.equals("Mythe fondateur")) {
                                civilisations.get(i).ajouterCivilisationAccessible(sumeriens);
                            }
                        } else {
                            System.out.println("Erreur : L'élément sélectionné est null.");
                        }
                    } else {
                        System.out.println("Erreur : Le composant à l'indice " + (i * 3 + 2) + " n'est pas un JComboBox.");
                    }
                }
                frame.dispose();
            }
        });

        JPanel boutonPanel = new JPanel();
        boutonPanel.add(boutonValider);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(boutonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void choisirSuccesAleatoire() {
        for (Civilisation civilisation : civilisations) {
            choisirSuccesAleatoirePourCivilisation(civilisation);
        }
    }

    public void choisirSuccesAleatoirePourCivilisation(Civilisation civilisation) {
        List<String> succes = civilisation.getSucces();
        Random random = new Random();
        int indexAleatoire = random.nextInt(succes.size());
        String succesAleatoire = succes.get(indexAleatoire);
        civilisation.setSuccesChoisi(succesAleatoire);
        System.out.println("Succes choisi pour la civilisation " + civilisation.getNom() + " : " + succesAleatoire);
    }

    public void jouer() {
        creerArbreSuccession();

        JFrame frame = new JFrame("Choix des Succes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton boutonSuccesManuel = new JButton("Choisir Succes Manuellement");
        boutonSuccesManuel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choisirSuccesManuelPourCivilisations();
            }
        });
        panel.add(boutonSuccesManuel);

        JButton boutonSuccesAleatoire = new JButton("Choisir Succes Aleatoirement");
        boutonSuccesAleatoire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choisirSuccesAleatoire();
            }
        });
        panel.add(boutonSuccesAleatoire);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        JeuDeSocieteHistorique jeu = new JeuDeSocieteHistorique();
        jeu.creerArbreSuccession();

        // Choix aléatoire ou manuel
        String choixMode = JOptionPane.showInputDialog("Choisissez le mode de sélection de civilisation : (1 - Aléatoire, 2 - Manuel)");
        if (choixMode != null && (choixMode.equals("1") || choixMode.equals("2"))) {
            if (choixMode.equals("1")) {
                jeu.choisirCivilisationAleatoire();
            } else {
                String indexCivilisationStr = JOptionPane.showInputDialog("Choisissez l'index de la civilisation : (0 - " + (jeu.civilisations.size() - 1) + ")");
                int indexCivilisation = Integer.parseInt(indexCivilisationStr);
                if (indexCivilisationStr != null) {
                    try {
                        int index = Integer.parseInt(indexCivilisationStr);
                        jeu.choisirCivilisationManuellement(index);
                    } catch (NumberFormatException e) {
                        System.out.println("Index invalide.");
                    }
                }
            }

            jeu.lancerPartie();
        } else {
            System.out.println("Mode invalide.");
        }
    }
}

    