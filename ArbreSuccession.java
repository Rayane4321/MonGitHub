package Conteneur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class JeuDeSocieteHistorique {
    private List<Civilisation> civilisations;
    private Civilisation sumeriens;

    public JeuDeSocieteHistorique() {
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
                        Object selectedItem = comboBox.getSelectedItem();
                        if (selectedItem instanceof String) {
                            String succesChoisi = (String) selectedItem;
                            civilisations.get(i).setSuccesChoisi(succesChoisi);
                            System.out.println("Succes choisi pour la civilisation " + civilisations.get(i).getNom() + " : " + succesChoisi);
                            if (succesChoisi.equals("Mythe fondateur")) {
                                civilisations.get(i).ajouterCivilisationAccessible(sumeriens);
                            }
                        } else {
                            System.out.println("Erreur : L'element selectionne n'est pas une chaîne de caracteres.");
                            // Gérez le cas où l'élément sélectionné n'est pas une chaîne de caractères
                        }
                    } else {
                        System.out.println("Erreur : Le composant à l'indice " + (i * 3 + 2) + " n'est pas un JComboBox.");
                        // Gérez le cas où le composant n'est pas un JComboBox
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
}
