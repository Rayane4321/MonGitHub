
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;




class ApplicationGraphique extends JFrame {
    private JComboBox<String> dirigeantComboBox;
    private JComboBox<String> empireComboBox;
    private Dirigeant dirigeantActuel;
    private Empire empireActuel;
    private int tour;
    private DefaultTableModel statsTableModel;
    private int religionDirigeantTourActuel;
    private int diplomatieDirigeantTourActuel;
    private int scienceEmpireTourActuel;
    private int militaireEmpireTourActuel;
    private int cultureEmpireTourActuel;
    private int argentEmpireTourActuel;
    private int gloire;
    private ArrayList<Dirigeant> dirigeants;
    private ArrayList<Empire> empires;
    private int religionModifiee = 0;
    private int diplomatieModifiee = 0;
    private int scienceModifiee = 0;
    private int militaireModifiee = 0;
    private int cultureModifiee = 0;
    private int argentModifiee = 0;
    private int[] essor;
    private int[] declin;



    public ApplicationGraphique() {
        setTitle("Succession");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        dirigeants = new ArrayList<>();
        dirigeants.add(new Dirigeant("Dirigeant 1", 5, 2));
        dirigeants.add(new Dirigeant("Dirigeant 2", 1, 5));
        dirigeants.add(new Dirigeant("Dirigeant 3", 5, 5));

       
        int[] essor2 = {4,2,4,2};
        int[] declin2 = {2,1,2,1};        
        int[] essor3 = {5,3,5,3};    
        int[] declin3 = {3,1,3,1};
        int[] essor1 = {4,4,4,4};
        int[] declin1 = {2,1,1,1};
        
        empires = new ArrayList<>();
        empires.add(new Empire("Empire 1", essor1, declin1,true));
        empires.add(new Empire("Empire 2", essor2, declin2,true));
        empires.add(new Empire("Empire 3", essor3, declin3,true));



        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        dirigeantComboBox = new JComboBox<>();
        empireComboBox = new JComboBox<>();
        for (Dirigeant dirigeant : dirigeants) {
            dirigeantComboBox.addItem(dirigeant.getNom());
        }
        for (Empire empire : empires) {
            empireComboBox.addItem(empire.getNom());
        }
        controlPanel.add(dirigeantComboBox);
        controlPanel.add(empireComboBox);

        JButton nouveauTourButton = new JButton("Nouveau Tour");
        controlPanel.add(nouveauTourButton);

        JButton modifierStatButton = new JButton("Modifier Statistique");
        controlPanel.add(modifierStatButton);


        dirigeantActuel = dirigeants.get(0);
        empireActuel = empires.get(0);
        tour = 0;
        religionDirigeantTourActuel = dirigeantActuel.getReligion();
        diplomatieDirigeantTourActuel = dirigeantActuel.getDiplomatie();
        scienceEmpireTourActuel = empireActuel.getScience();
        militaireEmpireTourActuel = empireActuel.getMilitaire();
        cultureEmpireTourActuel = empireActuel.getCulture();
        argentEmpireTourActuel = empireActuel.getArgent();
        gloire = 0;


        statsTableModel = new DefaultTableModel();
        statsTableModel.addColumn("Tour");
        statsTableModel.addColumn("Religion");
        statsTableModel.addColumn("Diplomatie");
        statsTableModel.addColumn("Militaire");
        statsTableModel.addColumn("Science");
        statsTableModel.addColumn("Culture");
        statsTableModel.addColumn("Argent");
        statsTableModel.addColumn("Gloire");

        JTable statsTable = new JTable(statsTableModel);
        JScrollPane scrollPane = new JScrollPane(statsTable);
        controlPanel.add(scrollPane);


        modifierStatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choixStat = JOptionPane.showInputDialog("Entrez le numéro de la statistique que vous souhaitez modifier (1: Religion, 2: Diplomatie, 3: Science, 4: Militaire, 5: Culture, 6: Argent) :");
                int nouvelleValeur = Integer.parseInt(JOptionPane.showInputDialog("Entrez la nouvelle valeur de la statistique :"));

                int statAModifier = Integer.parseInt(choixStat);
                switch (statAModifier) {
                    case 1:
                        religionModifiee = nouvelleValeur;
                        break;
                    case 2:
                        diplomatieModifiee =  nouvelleValeur;
                        break;
                    case 3:
                        scienceModifiee =  nouvelleValeur;
                        break;
                    case 4:
                        militaireModifiee =  nouvelleValeur;
                        break;
                    case 5:
                        cultureModifiee =  nouvelleValeur;
                        break;
                    case 6:
                        argentModifiee =  nouvelleValeur;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Numéro de statistique non valide. Veuillez utiliser un numéro de 1 à 6.");
                }
            }
        });



        JButton phaseButton = new JButton("Passer à la phase de déclin");
        controlPanel.add(phaseButton);


        phaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Vérifiez dans quelle phase vous êtes actuellement
                if (empireActuel.isEnEssor()) {
                    // Si vous êtes en phase d'essor, passez à la phase de déclin
                    empireActuel.passerEnPhaseDeclin();
                    phaseButton.setText("Passer à la phase d'essor");
                } else { // en déclin
                    empireActuel.passerEnPhaseEssor();
                    phaseButton.setText("Passer à la phase de déclin");
                }
            }
        });
        
        nouveauTourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // obtenir les indices des éléments sélectionnés dans les listes déroulantes dirigeantComboBox et empireComboBox
                int numeroDirigeant = dirigeantComboBox.getSelectedIndex();
                int numeroEmpire = empireComboBox.getSelectedIndex();
                if (numeroDirigeant >= 0 && numeroDirigeant < dirigeants.size() && numeroEmpire >= 0 && numeroEmpire < empires.size()) {
                    // Dirigeant et Empire du tour correspondant aux indices sélectionnés
                    Dirigeant dirigeantTour = dirigeants.get(numeroDirigeant);
                    Empire empireTour = empires.get(numeroEmpire);

                    if (tour == 0) {
                        // creation dirigeant et empire fictif qui utilisent les stats du dirigeant et empire choisi
                        Dirigeant dirigeantFictif = new Dirigeant("Dirigeant Fictif", dirigeantTour.getReligion(), dirigeantTour.getDiplomatie());
                        Empire empireFictif = new Empire("Empire Fictif", empireTour.getStatsEssor(), empireTour.getStatsDeclin(), empireTour.isEnEssor());
                        dirigeantActuel = dirigeantFictif;
                        empireActuel = empireFictif;
                        essor = empireActuel.getStatsEssor(); 
                        declin = empireActuel.getStatsDeclin(); 
                    } else {
                        empireActuel.updateStatsActuelles();

                        // Cumuler les stats du tour avec les stats cumulées
                        empireActuel.cumulerStats(); 

                        MAJStats();

                        // MAJ les statistiques actuelles de l'empire en fonction des statistiques cumulatives et de la phase actuelle
                        empireActuel.updateStatsActuelles();

                        // prend en compte les stats modifiées
                        updateEntityStats(dirigeantActuel, empireActuel, dirigeantTour, empireTour);
                        resetStatsModifs();
                    }

                    // MAJ gloire en fonction du nombre de territoires
                    int territoires = Integer.parseInt(JOptionPane.showInputDialog("Nombre de territoires que vous possédez : "));
                    gloire += territoires;

                    tour++;
                    // Cette ligne détermine le nom du dirigeant actuel pour le tour en cours.
                    // Si c'est le premier tour, le nom est "Dirigeant 1". Sinon, nom est obtenu à partir de l'objet dirigeant actuel.
                    String nomDirigeant = (tour == 1) ? "Dirigeant 1" : dirigeantActuel.getNom();
                    // boîte de dialogue avec informations sur le tour en cours, nom du dirigeant et empire actuel 
                    JOptionPane.showMessageDialog(null, "Tour " + tour + " avec " + nomDirigeant + " et " + empireTour.getNom());

                    int[] statsTourEtCumule = empireActuel.getStatsTourEtCumule();

                    // Affichage des stats du tour sur une nouvelle ligne du tableau de dans l'interface graphique
                    statsTableModel.addRow(new Object[]{
                        tour,
                        dirigeantActuel.getReligion(), 
                        dirigeantActuel.getDiplomatie(),
                        statsTourEtCumule[0], 
                        statsTourEtCumule[1], 
                        statsTourEtCumule[2], 
                        statsTourEtCumule[3],
                        gloire
                    });
                }
            }
        });

        getContentPane().add(controlPanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        
    }


        public void resetStatsModifs() {
            religionModifiee = 0;
            diplomatieModifiee = 0;
            scienceModifiee = 0;
            militaireModifiee = 0;
            cultureModifiee = 0;
            argentModifiee = 0;
        }

        public void MAJStats() {
            int[] statsActuelles = empireActuel.getStatsActuelles();
            religionDirigeantTourActuel = dirigeantActuel.getReligion();
            diplomatieDirigeantTourActuel = dirigeantActuel.getDiplomatie();
            scienceEmpireTourActuel = statsActuelles[1];
            militaireEmpireTourActuel = statsActuelles[0];
            cultureEmpireTourActuel = statsActuelles[2];
            argentEmpireTourActuel = statsActuelles[3];

            // Messages de débogage
            System.out.println("Stats cumulatives : " + Arrays.toString(empireActuel.getStatsCumulatives()));
            System.out.println("Stats phase actuelle : " + Arrays.toString(empireActuel.isEnEssor() ? empireActuel.getStatsEssor() : empireActuel.getStatsDeclin()));
            System.out.println("Stats ACTUELLES : " + Arrays.toString(statsActuelles));
            System.out.println("Religion après mise à jour : " + religionDirigeantTourActuel);
            System.out.println("Diplomatie après mise à jour : " + diplomatieDirigeantTourActuel);
            System.out.println("Science après mise à jour : " + scienceEmpireTourActuel);
            System.out.println("Militaire après mise à jour : " + militaireEmpireTourActuel);
            System.out.println("Culture après mise à jour : " + cultureEmpireTourActuel);
            System.out.println("Argent après mise à jour : " + argentEmpireTourActuel);
            System.out.println("En essor : " + empireActuel.isEnEssor());
        }

        // Additionne Stats tours avec stats tour actuel
        private void updateEntityStats(Dirigeant dirigeant, Empire empire, Dirigeant dirigeantTour, Empire empireTour) {
            dirigeant.setReligion(religionModifiee + religionDirigeantTourActuel + dirigeantTour.getReligion());
            dirigeant.setDiplomatie(diplomatieModifiee + diplomatieDirigeantTourActuel + dirigeantTour.getDiplomatie());
            empire.setScience(scienceModifiee + scienceEmpireTourActuel + empireTour.getScience());
            empire.setMilitaire(militaireModifiee + militaireEmpireTourActuel + empireTour.getMilitaire());
            empire.setCulture(cultureModifiee + cultureEmpireTourActuel + empireTour.getCulture());
            empire.setArgent(argentModifiee + argentEmpireTourActuel + empireTour.getArgent());

            // Ajoutez des messages de débogage pour voir les valeurs
            System.out.println("Religion après mise à jour : " + dirigeant.getReligion());
            System.out.println("Diplomatie après mise à jour : " + dirigeant.getDiplomatie());
            System.out.println("Science après mise à jour : " + empire.getScience());
            System.out.println("Militaire après mise à jour : " + empire.getMilitaire());
            System.out.println("cultureTA : " + cultureEmpireTourActuel+ " MAJ "+empire.getCulture());
            System.out.println("argentTA : " + argentEmpireTourActuel+empire.getArgent());
        }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationGraphique app = new ApplicationGraphique();
            app.setVisible(true);
        });
    }
}

class Dirigeant {
    private String nom;
    private int religion;
    private int diplomatie;

    public Dirigeant(String nom, int religion, int diplomatie) {
        this.nom = nom;
        this.religion = religion;
        this.diplomatie = diplomatie;
    }

    public String getNom() {
        return nom;
    }

    public int getReligion() {
        return religion;
    }

    public int getDiplomatie() {
        return diplomatie;
    }

    public void setReligion(int religion) {
        this.religion = religion;
    }

    public void setDiplomatie(int diplomatie) {
        this.diplomatie = diplomatie;
    }
    /*public MAJStatsDirigeant(Dirigeant d, ){
        int religionModifiee = 0; 
        int diplomatieModifiee = 0;
        d.setReligion(religionModifiee + religionDirigeantTourActuel + d.getReligion());
        d.setDiplomatie(diplomatieModifiee + diplomatieDirigeantTourActuel + d.getDiplomatie());
    }*/
}

class Empire {
    private String nom;
    private int[] statsEssor;
    private int[] statsDeclin;
    private int[] statsActuelles;
    private int[] statsCumulatives;
    private boolean isEnEssor;
    private boolean changerDePhase;
    private int argent;
    private int culture;
    private int militaire;
    private int science;

    public Empire(String nom, int[] essor, int[] declin, boolean isEnEssor) {
        this.nom = nom;
        this.isEnEssor = isEnEssor;
        this.statsCumulatives = new int[4];

        // Initialise statsActuelles au tableau essor par défaut.
        statsActuelles = Arrays.copyOf(essor, essor.length);

        // Crée la variable "statsEssor" en copiant les valeurs du tableau "essor" passé en paramètre.
        this.statsEssor = new int[4];
        for (int i = 0; i < essor.length; i++) {
            this.statsEssor[i] = essor[i];
        }

        // Crée la variable "statsDeclin" en copiant les valeurs du tableau "declin" passé en paramètre.
        this.statsDeclin = new int[4];
        for (int i = 0; i < declin.length; i++) {
            this.statsDeclin[i] = declin[i];
        }
    }

    public String getNom() {
        return nom;
    }

    public void cumulerStats() {
        int[] statsPhase;
        if (isEnEssor) {
            statsPhase = statsEssor;
        } else {
            statsPhase = statsDeclin;
        }
        // débogage
        System.out.println("Phase actuelle : " + (isEnEssor ? "Essor" : "Déclin"));

        for (int i = 0; i < statsCumulatives.length; i++) {
            // Ajoutez les statistiques de la phase actuelle aux statistiques cumulées
            statsCumulatives[i] += statsPhase[i];
        }
    }

    


    
    public void updateStatsActuelles() {
        int[] statsPhase;
        if (isEnEssor) {
            statsPhase = statsEssor;
        } else {
            statsPhase = statsDeclin;
        }

        for (int i = 0; i < statsActuelles.length; i++) {
            statsActuelles[i] = statsCumulatives[i] + statsPhase[i];
        }

        // Supprimez ces lignes pour éviter l'affichage inutile de statsActuelles
        System.out.println("Stats cumulatives : " + Arrays.toString(statsCumulatives));
        System.out.println("Stats phase actuelle : " + Arrays.toString(statsPhase));
        System.out.println("Stats ACTUELLES : " + Arrays.toString(statsActuelles));

        System.out.println("Mise à jour des statistiques actuelles.");
        System.out.println("En essor : " + isEnEssor);
    }

    


    // déterminer quel tableau de statistiques doit être retourné en fonction de la valeur de isEnEssor, true: Essor, False: Declin
    /*public void updateStatsActuelles() {        
        int[] statsPhase;
        if (isEnEssor) {
            statsPhase = statsEssor;
        } else {
            statsPhase = statsDeclin;
        }
        for (int i = 0; i < statsActuelles.length; i++) {
            statsActuelles[i] = statsCumulatives[i] + statsPhase[i];
        }
        //messages de débogage
        System.out.println("Stats cumulatives : " + Arrays.toString(statsCumulatives));
        System.out.println("Stats phase actuelle : " + Arrays.toString(statsPhase));
        System.out.println("Stats ACTUELLES : " + Arrays.toString(statsActuelles));

        System.out.println("Mise à jour des statistiques actuelles.");
        System.out.println("En essor : " + isEnEssor);
        


    }    */

    
    public int[] getStatsCumulatives() {
        return statsCumulatives;
    }


    public int[] getStatsTourEtCumule() {
        // Retourne une copie des statistiques actuelles (qui sont déjà cumulées)
        return Arrays.copyOf(getStatsActuelles(), 4);
    }



    public int[] getStatsDeclin(){
        return statsDeclin;
    }

    public int[] getStatsEssor(){
        return statsEssor;
    }

    public int[] getStatsActuelles(){
        return statsActuelles;
    }
     

    public boolean isEnEssor() {
        return isEnEssor;
    }

    public boolean getIsEnEssor() {
        return isEnEssor;
    }


    public void setEnEssor(boolean enEssor) {
        int[] statsPhase;
        isEnEssor = enEssor;
        statsPhase = isEnEssor ? statsEssor : statsDeclin;
    }

  


    public void passerEnPhaseEssor() {
        System.out.println("Passer en phase d'essor.");
        if (!isEnEssor) {
            setEnEssor(true);
            updateStatsActuelles();
            System.out.println("Changement de phase."+"en essor est"+isEnEssor);

        }
    }

    public void passerEnPhaseDeclin() {
        System.out.println("Passer en phase de declin.");
        if (isEnEssor) {
            setEnEssor(false);
            updateStatsActuelles();
            System.out.println("Changement de phase."+"en essor est"+isEnEssor);
        }
    }

    public int getMilitaire() {
        return statsActuelles[0];
    }

    public int getScience() {
        return statsActuelles[1];
    }

    public int getCulture() {
        return statsActuelles[2];
    }

    public int getArgent() {
        return statsActuelles[3];
    }

    public void setMilitaire(int militaire) {
        statsActuelles[0] = militaire;
    }

    public void setScience(int science) {
        statsActuelles[1] = science;
    }

    public void setCulture(int culture) {
        statsActuelles[2] = culture;
    }

    public void setArgent(int argent) {
        statsActuelles[3] = argent;
    }
}
