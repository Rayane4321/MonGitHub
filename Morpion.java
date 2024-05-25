package Conteneur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Morpion extends JFrame {
    private JButton[][] boutons;
    private char tour;
    private boolean jeuEnCours;

    public Morpion() {
        // Initialisation de la fenêtre principale
        setTitle("Morpion");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        // Initialisation des boutons de la grille
        boutons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boutons[i][j] = new JButton("");
                boutons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                boutons[i][j].setFocusPainted(false);
                boutons[i][j].addActionListener(new BoutonClickListener(i, j));
                add(boutons[i][j]);
            }
        }

        // Initialisation des variables de jeu
        tour = 'X';
        jeuEnCours = true;
    }

    // Classe interne pour gérer les clics sur les boutons
    private class BoutonClickListener implements ActionListener {
        private int x;
        private int y;

        public BoutonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (jeuEnCours && boutons[x][y].getText().equals("")) {
                boutons[x][y].setText(String.valueOf(tour));
                if (verifierVictoire(tour)) {
                    jeuEnCours = false;
                    JOptionPane.showMessageDialog(null, "Le joueur " + tour + " a gagné !");
                } else if (verifierMatchNul()) {
                    jeuEnCours = false;
                    JOptionPane.showMessageDialog(null, "Match nul !");
                } else {
                    tour = (tour == 'X') ? 'O' : 'X';
                }
            }
        }
    }

    // Vérifie si le joueur donné a gagné
    private boolean verifierVictoire(char joueur) {
        // Vérification des lignes, colonnes et diagonales
        for (int i = 0; i < 3; i++) {
            if (boutons[i][0].getText().equals(String.valueOf(joueur)) &&
                boutons[i][1].getText().equals(String.valueOf(joueur)) &&
                boutons[i][2].getText().equals(String.valueOf(joueur))) {
                return true;
            }
            if (boutons[0][i].getText().equals(String.valueOf(joueur)) &&
                boutons[1][i].getText().equals(String.valueOf(joueur)) &&
                boutons[2][i].getText().equals(String.valueOf(joueur))) {
                return true;
            }
        }
        if (boutons[0][0].getText().equals(String.valueOf(joueur)) &&
            boutons[1][1].getText().equals(String.valueOf(joueur)) &&
            boutons[2][2].getText().equals(String.valueOf(joueur))) {
            return true;
        }
        if (boutons[0][2].getText().equals(String.valueOf(joueur)) &&
            boutons[1][1].getText().equals(String.valueOf(joueur)) &&
            boutons[2][0].getText().equals(String.valueOf(joueur))) {
            return true;
        }
        return false;
    }

    // Vérifie si la partie est un match nul
    private boolean verifierMatchNul() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boutons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    // Méthode principale pour lancer l'application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Morpion morpion = new Morpion();
            morpion.setVisible(true);
        });
    }
}
