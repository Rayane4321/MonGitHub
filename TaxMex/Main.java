import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.*;
import java.util.Scanner;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;





class Main extends JFrame {
    private CustomCursorPanel customCursorPanel;
    private GestionnaireConnexionSQLite connexion;
    

    public Main() {
        connexion = new GestionnaireConnexionSQLite();

        setTitle("TaxMex");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color green = new Color(0, 104, 71);
        Color white = Color.WHITE;
        Color red = new Color(206, 17, 38);

        // Configurer le curseur de la fenêtre principale avec le curseur personnalisé
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = toolkit.getImage("Mexico.png");
        Cursor cursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "Mexico Cursor");
        setCursor(cursor);

        // Créer un conteneur pour le contenu principal
        JPanel contentPane = new JPanel();
        // superposer les éléments 
        contentPane.setLayout(new OverlayLayout(contentPane));
        setContentPane(contentPane); // Définit le contenu principal de la fenêtre

        customCursorPanel = new CustomCursorPanel();
        contentPane.add(customCursorPanel); // Ajoute le panneau de curseur personnalisé

        // Ajout du panneau de fond dégradé
        CustomGradientPanel gradientPanel = new CustomGradientPanel();
        contentPane.add(gradientPanel);

        JLabel welcomeLabel = new JLabel();

        // Définir la couleur du texte
        Color color = new Color(0, 0, 0); 
        welcomeLabel.setForeground(color);

        // Ajouter le label au panneau        
        gradientPanel.add(welcomeLabel);


        // Charger et redimensionner les images
        ImageIcon image1 = resizeImage("elypo.png", 300, 300);

        // Créer des labels pour afficher les images
        JLabel label1 = new JLabel(image1);
        
        
        gradientPanel.add(label1);


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                SwingUtilities.convertPointToScreen(point, getContentPane());
                customCursorPanel.setCursorPosition(point);
            }
        });

        TitlePanel titlePanel = new TitlePanel();
        gradientPanel.add(titlePanel);

        JButton createAccountBtn = new JButton("Creer un compte");
        createAccountBtn.setBackground(green); // Fond bouton
        createAccountBtn.setForeground(Color.BLACK);
        createAccountBtn.addActionListener(e -> {
            // Afficher une boîte de dialogue pour saisir les informations de compte
            JTextField nomField = new JTextField();
            JTextField prenomField = new JTextField();
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JTextField emailField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("Nom :"));
            panel.add(nomField);
            panel.add(new JLabel("Prénom :"));
            panel.add(prenomField);
            panel.add(new JLabel("Nom d'utilisateur :"));
            panel.add(usernameField);
            panel.add(new JLabel("Mot de passe :"));
            panel.add(passwordField);
            panel.add(new JLabel("Adresse e-mail :"));
            panel.add(emailField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Création de compte", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Récupérer les valeurs saisies par l'utilisateur
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();

                System.out.println("nom:"+nom);
                System.out.println("mdp:"+password);
                System.out.println("email:"+email);
                System.out.println("username:"+username);
                System.out.println("prenom:"+prenom);

                // Vérifier si les champs sont vides
                if (nom.isEmpty() || prenom.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Appeler la méthode pour inscrire l'utilisateur dans la base de données
                boolean inscriptionReussie = sInscrire(nom, prenom, password, username, email);
                if (inscriptionReussie) {
                    JOptionPane.showMessageDialog(null, "Inscription réussie !");
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'inscription. Veuillez réessayer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        createAccountBtn.addMouseListener(new ButtonMouseListener());
        gradientPanel.add(createAccountBtn);

        JButton loginBtn = new JButton("Connexion");
        loginBtn.setBackground(white); // Fond bouton
        loginBtn.setForeground(Color.BLACK);
        loginBtn.addActionListener(e -> {
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Nom d'utilisateur:"));
            panel.add(usernameField);
            panel.add(new JLabel("Mot de passe:"));
            panel.add(passwordField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Connexion", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Utiliser la méthode seConnecter() pour vérifier l'authentification
                boolean connexionReussie = connexion.seConnecter(username, password);
                if (connexionReussie) {
                    JOptionPane.showMessageDialog(null, "Connexion réussie !");
                    
                    // Fermer la fenêtre actuelle
                    dispose();
                    
                    // Ouvrir une nouvelle fenêtre
                    Etat2 etat2 = new Etat2();
                    etat2.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Échec de la connexion. Veuillez vérifier vos informations d'identification.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        loginBtn.addMouseListener(new ButtonMouseListener());
        gradientPanel.add(loginBtn);



        JButton becomeDriverBtn = new JButton("Candidater chauffeur");
        becomeDriverBtn.setBackground(red); // Fond bouton
        becomeDriverBtn.setForeground(Color.WHITE); // police
        becomeDriverBtn.addActionListener(e -> {
            // Afficher une boîte de dialogue pour saisir les informations de postulation chauffeur
            JTextField nomField = new JTextField();
            JTextField prenomField = new JTextField();
            JTextField emailField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Nom :"));
            panel.add(nomField);
            panel.add(new JLabel("Prénom :"));
            panel.add(prenomField);
            panel.add(new JLabel("Adresse e-mail :"));
            panel.add(emailField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Postulation chauffeur", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Récupérer les valeurs saisies par l'utilisateur
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String email = emailField.getText();

                // Vérifier si les champs sont vides
                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Appeler la méthode pour postuler comme chauffeur
                boolean postulationAcceptee = postulerChauffeur(nom, prenom, email);
                if (postulationAcceptee) {
                    JOptionPane.showMessageDialog(null, "Votre postulation a été prise en compte avec succès !", "Validation", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la postulation. Veuillez réessayer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        becomeDriverBtn.addMouseListener(new ButtonMouseListener());
        gradientPanel.add(becomeDriverBtn);

        JButton aboutUsBtn = new JButton("En savoir plus");
        aboutUsBtn.addActionListener(e -> surNous());
        aboutUsBtn.addMouseListener(new ButtonMouseListener());
        gradientPanel.add(aboutUsBtn);


        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalStrut(450)); // Ajouter un espace de 50 pixels au-dessus des images
        verticalBox.add(label1);
        gradientPanel.add(verticalBox, BorderLayout.CENTER);

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(Box.createHorizontalStrut(100)); // Ajouter un espace de 50 pixels au-dessus des images
        horizontalBox.add(label1);
        gradientPanel.add(horizontalBox, BorderLayout.CENTER);

        setVisible(true);

    }
         // Méthode pour s'inscrire avec un nom d'utilisateur et un mot de passe
        public boolean sInscrire(String nomUtilisateur, String prenomUtilisateur, String motDePasse, String username, String email) {
            // Appel de la méthode de connexion pour s'inscrire
            return connexion.sInscrire(nomUtilisateur, prenomUtilisateur, motDePasse, username, email);
        }

        class CustomGradientPanel extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Dessiner le dégradé de couleurs du Mexique
                GradientPaint gradientPaint = new GradientPaint(0, 0, new Color(206, 17, 38), getWidth(), getHeight(), new Color(0, 104, 71));
                g2d.setPaint(gradientPaint);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }

        public static boolean postulerChauffeur(String nom, String prenom, String email) {
            // Informations de connexion à la base de données
            String url = "jdbc:sqlite:taxmex.db";
        
            try {
                // Établissement de la connexion à la base de données
                Connection connexion = DriverManager.getConnection(url);
        
                // Préparation de la requête SQL
                String sql = "INSERT INTO candidat_chauffeur (nom_candidat_chauffeur, prenom_candidat_chauffeur, email_candidat_chauffeur) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connexion.prepareStatement(sql);
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, prenom);
                preparedStatement.setString(3, email);
        
                // Exécution de la requête
                int lignesModifiees = preparedStatement.executeUpdate();
        
                // Fermeture des ressources
                preparedStatement.close();
                connexion.close();
        
                // Vérification du succès de l'opération
                return lignesModifiees > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false; // En cas d'erreur, retourner false
            }
        }
        
        private ImageIcon resizeImage(String filename, int width, int height) {
            try {
                BufferedImage originalImage = ImageIO.read(new File(filename));
                Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    public static void main(String[] args) {
        new Main();
    }

    public void surNous() {
        JOptionPane.showMessageDialog(this, "Bienvenue chez TaxMex\n\n" +
                "Le premier service de VTC Mexicain en France.\n\n" +
                "Nous sommes là pour vous aider à vous déplacer aussi vite que Speedy Gonzales.\n\n" +
                "Ici nos chauffeurs ne vous offrent pas de simples sachets de bonbons mais plutôt des\n" +
                "tacos et autres plats mexicains typiques. Si vous avez la moindre question, n'hésitez\n" +
                "pas à contacter notre équipe support incroyablement sympathique qui ne parle pas\n" +
                "français. Nous vous conseillons donc plutôt d'utiliser notre chat en direct qui est\n" +
                "aussi en espagnol. Vous aurez au moins le temps d'utiliser n'importe quel traducteur\n" +
                "en ligne. Si vous n'êtes pas découragés de nous appeler, notre équipe est prête à\n" +
                "vous aider avec tout ce dont vous avez besoin, que ce soit pour vous aider à trouver\n" +
                "le meilleur itinéraire ou pour vous recommander les meilleurs restaurants mexicains\n" +
                "de la ville. Nous sommes ici pour vous, 24 heures sur 24, 7 jours sur 7, pour vous\n" +
                "aider à obtenir le meilleur service de taxi mexicain possible. Alors, la prochaine\n" +
                "fois que vous avez besoin de vous déplacer dans la ville, pensez à Tax-Mex, parce\n" +
                "que nous sommes plus chauds que la salsa et plus rapides que les enchiladas!");
    }

    private class ButtonMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.doClick(); // Cliquer sur le bouton
        }
    }
}

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(new GradientPaint(0, 0, new Color(206, 17, 38), getWidth(), getHeight(), new Color(0, 104, 71)));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }


    class Etat2 extends JFrame {
    private CustomCursorPanel customCursorPanel;
    

    public Etat2() {
        setTitle("TaxMex");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color green = new Color(0, 104, 71);
        Color white = Color.WHITE;
        Color red = new Color(206, 17, 38);

        //  classe Toolkit fait partie du package java.awt et fournit diverses fonctionnalités pour interagir avec le système de fenêtrage
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // ici pour modifier le curseur
        Image cursorImage = toolkit.getImage("Mexico.png");
        Cursor cursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "Mexico Cursor");
        setCursor(cursor);

        // Créer un conteneur pour le contenu principal
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new OverlayLayout(contentPane));
        setContentPane(contentPane); // Définit le contenu principal de la fenêtre      

        customCursorPanel = new CustomCursorPanel();
        contentPane.add(customCursorPanel); // Ajoute le panneau de curseur personnalisé

        // Ajout du panneau de fond dégradé
        CustomGradientPanel gradientPanel = new CustomGradientPanel();
        contentPane.add(gradientPanel); 
  
        JLabel welcomeLabel = new JLabel();
        gradientPanel.add(welcomeLabel);

      

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                SwingUtilities.convertPointToScreen(point, getContentPane());
                customCursorPanel.setCursorPosition(point);
            }
        });

        TitlePanel titlePanel = new TitlePanel();
        gradientPanel.add(titlePanel);

        JButton commandeBtn = new JButton("Commander");
        commandeBtn.setBackground(green); // Fond bouton
        commandeBtn.setForeground(Color.BLACK);
        Commander commander = new Commander();
        commandeBtn.addActionListener(e -> {
            commander.setVisible(true);
        });



        commandeBtn.addMouseListener(new ButtonMouseListener());
        gradientPanel.add(commandeBtn);

        JButton maCommandeBtn = new JButton("Ma commande");
        maCommandeBtn.setBackground(white); // Fond bouton
        maCommandeBtn.setForeground(Color.BLACK);
        MaCommande maCommande = new MaCommande();
        maCommandeBtn.addActionListener(e -> {
            maCommande.setVisible(true);
        });



        maCommandeBtn.addMouseListener(new ButtonMouseListener());
        gradientPanel.add(maCommandeBtn);


        JButton deconexionBtn = new JButton("Se deconecter");
        deconexionBtn.setBackground(red); // Fond bouton
        deconexionBtn.setForeground(Color.white);
        deconexionBtn.addActionListener(e -> {
            Main main = new Main();
            effacerCommande();
            dispose();
            main.setVisible(true);
        });



        deconexionBtn.addMouseListener(new ButtonMouseListener());
        gradientPanel.add(deconexionBtn);


        JButton aboutUsBtn = new JButton("En savoir plus");
        aboutUsBtn.addActionListener(e -> surNous());
        aboutUsBtn.addMouseListener(new ButtonMouseListener());
        gradientPanel.add(aboutUsBtn);

        setVisible(true);

    }

    private void effacerCommande() {
        // Connexion à la base de données et requête pour récupérer les données de commande
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM commande;");
            statement.close();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        class CustomGradientPanel extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Dessiner le dégradé de couleurs du Mexique
                GradientPaint gradientPaint = new GradientPaint(0, 0, new Color(206, 17, 38), getWidth(), getHeight(), new Color(0, 104, 71));
                g2d.setPaint(gradientPaint);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }

        public static boolean postulerChauffeur(String nom, String prenom, String email) {
            // Informations de connexion à la base de données
            String url = "jdbc:sqlite:taxmex.db";
        
            try {
                // Établissement de la connexion à la base de données
                Connection connexion = DriverManager.getConnection(url);
        
                // Préparation de la requête SQL
                String sql = "INSERT INTO candidat_chauffeur (nom_candidat_chauffeur, prenom_candidat_chauffeur, email_candidat_chauffeur) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connexion.prepareStatement(sql);
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, prenom);
                preparedStatement.setString(3, email);
        
                // Exécution de la requête
                int lignesModifiees = preparedStatement.executeUpdate();
        
                // Fermeture des ressources
                preparedStatement.close();
                connexion.close();
        
                // Vérification du succès de l'opération
                return lignesModifiees > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false; // En cas d'erreur, retourner false
            }
        }
        
    public static void main(String[] args) {
        new Main();
    }

    public void surNous() {
        JOptionPane.showMessageDialog(this, "Bienvenue chez TaxMex\n\n" +
                "Le premier service de VTC Mexicain en France.\n\n" +
                "Nous sommes là pour vous aider à vous déplacer aussi vite que Speedy Gonzales.\n\n" +
                "Ici nos chauffeurs ne vous offrent pas de simples sachets de bonbons mais plutôt des\n" +
                "tacos et autres plats mexicains typiques. Si vous avez la moindre question, n'hésitez\n" +
                "pas à contacter notre équipe support incroyablement sympathique qui ne parle pas\n" +
                "français. Nous vous conseillons donc plutôt d'utiliser notre chat en direct qui est\n" +
                "aussi en espagnol. Vous aurez au moins le temps d'utiliser n'importe quel traducteur\n" +
                "en ligne. Si vous n'êtes pas découragés de nous appeler, notre équipe est prête à\n" +
                "vous aider avec tout ce dont vous avez besoin, que ce soit pour vous aider à trouver\n" +
                "le meilleur itinéraire ou pour vous recommander les meilleurs restaurants mexicains\n" +
                "de la ville. Nous sommes ici pour vous, 24 heures sur 24, 7 jours sur 7, pour vous\n" +
                "aider à obtenir le meilleur service de taxi mexicain possible. Alors, la prochaine\n" +
                "fois que vous avez besoin de vous déplacer dans la ville, pensez à Tax-Mex, parce\n" +
                "que nous sommes plus chauds que la salsa et plus rapides que les enchiladas!");
    }

    private class ButtonMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.doClick(); // Cliquer sur le bouton
        }
    }
}
    

class CustomCursorPanel extends JPanel {

    private final List<Particle> particles;
    private final Random random;
    private final BufferedImage[] images;

    public CustomCursorPanel() {
        particles = new ArrayList<>();
        random = new Random();

        setOpaque(false);

        // Chargement des images
        images = new BufferedImage[4];
        try {
            images[0] = ImageIO.read(new File("nachos.png"));
            images[1] = ImageIO.read(new File("burrito.png"));
            images[2] = ImageIO.read(new File("quesadilla.png"));
            images[3] = ImageIO.read(new File("taco.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer(50, e -> {
            moveParticles();
            repaint();
        });
        timer.start();
    }

    public void setCursorPosition(Point point) {
        createParticles(point.x, point.y);
    }

    private void createParticles(int x, int y) {
        for (int i = 0; i < 1; i++) {
            int vx = (random.nextInt(4) - 2) * 2; // Vitesse horizontale aléatoire
            int vy = (random.nextInt(4) - 2) * 2; // Vitesse verticale aléatoire
            BufferedImage image = images[random.nextInt(images.length)]; // Sélection aléatoire d'une image
            particles.add(new Particle(x, y, vx, vy, image));
        }
    }

    private void moveParticles() {
        particles.removeIf(Particle::isDead);
        for (Particle particle : particles) {
            particle.move();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Particle particle : particles) {
            particle.draw(g2d);
        }
    }

    private static class Particle {
        private int x;
        private int y;
        private int vx;
        private int vy;
        private final BufferedImage image;
        private int life;

        public Particle(int x, int y, int vx, int vy, BufferedImage image) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.image = image;
            this.life = 5; // Durée de vie arbitraire
        }

        public void move() {
            x += vx;
            y += vy;
            life--;
        }

        public boolean isDead() {
            return life <= 0;
        }

        public void draw(Graphics2D g2d) {
            int width = 25; // Largeur arbitraire
            int height = 25; // Hauteur arbitraire
            g2d.drawImage(image, x, y, width, height, null);
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
    ClientActuel clientActuel = new ClientActuel("username", "motDePasse");

    public void setClientActuel(ClientActuel clientActuel) {
        this.clientActuel = clientActuel;
    }

     // Méthode pour se connecter avec un nom d'utilisateur et un mot de passe
     public boolean seConnecter(String username, String motDePasse) {
        // Effectuer les contrôles de validation en Java
        if (!validerUtilisateur(username) || !validerMotDePasse(motDePasse)) {
            System.out.println("Le nom d'utilisateur ou le mot de passe ne respecte pas les normes de sécurité.");
            return false;
        }
    
        // Vérifier dans la base de données
        boolean verificationBaseDeDonnees = verifierDansBaseDeDonnees(username, motDePasse);
        if (verificationBaseDeDonnees) {
            // Créer un objet clientActuel avec les données actuelles
            ClientActuel clientActuel2 = new ClientActuel(username, motDePasse);
            setClientActuel(clientActuel2);
            return true;
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
            return false;
        }
    }
    
    // Méthode pour s'inscrire avec un nom d'utilisateur et un mot de passe
    public boolean sInscrire(String nomUtilisateur, String prenomUtilisateur, String motDePasse, String username, String email) {
    
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
                //attente.attendre(3000);
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



class Commander extends JFrame {

    private JPanel chauffeurPanel;
    private String selectedNom = ""; // Initialisation avec une valeur par défaut
    private String selectedPrenom = ""; // Initialisation avec une valeur par défaut
    private double selectedmontant = 0.0; // Initialisation avec une valeur par défaut

    public Commander() {
        setTitle("Liste des chauffeurs");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        chauffeurPanel = new JPanel(new GridLayout(0, 1));
        add(new JScrollPane(chauffeurPanel), BorderLayout.CENTER);

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
            }
        });
        add(retourButton, BorderLayout.SOUTH);

        fetchChauffeursFromDatabase(); // Récupérer les chauffeurs depuis la base de données
    }

    private void fetchChauffeursFromDatabase() {
        // Connexion à la base de données et requête pour récupérer les chauffeurs
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nom_chauffeur, prenom_chauffeur, tarif_chauffeur FROM chauffeur");
            while (resultSet.next()) {
                String nom = resultSet.getString("nom_chauffeur");
                String prenom = resultSet.getString("prenom_chauffeur");
                double montant = resultSet.getDouble("tarif_chauffeur");

                JButton chauffeurButton = new JButton(nom + " " + prenom + " - montant : " + montant);
                chauffeurButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Enregistrement du chauffeur sélectionné dans les attributs de la classe
                        selectedNom = nom;
                        selectedPrenom = prenom;
                        selectedmontant = montant;

                        // Ouverture de la fenêtre de paiement
                        showPaiementInterface();
                    }
                });
                chauffeurPanel.add(chauffeurButton);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveChauffeurToCommande(String nom, String prenom, double montant, String adresseDepart, String adresseArrivee) {
        // Connexion à la base de données et insertion des informations dans la table "commande"
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO commande (mon_chauffeur, montant, adresse_depart, adresse_arrivee) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, nom + " " + prenom);
            preparedStatement.setDouble(2, montant);
            preparedStatement.setString(3, adresseDepart);
            preparedStatement.setString(4, adresseArrivee);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class PaiementInterface extends JFrame {

    private JTextField numeroCarteField;
    private JPasswordField codeSecretField;
    private JTextField dateExpirationField;
    private JTextField adresseDepartField;
    private JTextField adresseArriveeField;
    private String selectedNom;
    private String selectedPrenom;
    private double selectedmontant;

    public PaiementInterface(String selectedNom, String selectedPrenom, double selectedmontant) {
        setTitle("Interface de Paiement");
        setSize(400, 300); // Augmenté la taille pour inclure les champs d'adresse
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        this.selectedNom = selectedNom;
        this.selectedPrenom = selectedPrenom;
        this.selectedmontant = selectedmontant;
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel numeroCarteLabel = new JLabel("Numéro de Carte :");
        numeroCarteField = new JTextField();
        JLabel codeSecretLabel = new JLabel("Code Secret :");
        codeSecretField = new JPasswordField();
        JLabel dateExpirationLabel = new JLabel("Date d'Expiration (MM/YYYY) :");
        dateExpirationField = new JTextField();
        JLabel adresseDepartLabel = new JLabel("Adresse de Départ :");
        adresseDepartField = new JTextField();
        JLabel adresseArriveeLabel = new JLabel("Adresse d'Arrivée :");
        adresseArriveeField = new JTextField();

        JButton confirmerButton = new JButton("Confirmer");
        confirmerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmerPaiement();
                saveChauffeurToCommande(selectedNom, selectedPrenom, selectedmontant, adresseDepartField.getText(), adresseArriveeField.getText());
                dispose();
            }
        });

        panel.add(numeroCarteLabel);
        panel.add(numeroCarteField);
        panel.add(codeSecretLabel);
        panel.add(codeSecretField);
        panel.add(dateExpirationLabel);
        panel.add(dateExpirationField);
        panel.add(adresseDepartLabel);
        panel.add(adresseDepartField);
        panel.add(adresseArriveeLabel);
        panel.add(adresseArriveeField);
        panel.add(new JLabel()); // Espace vide
        panel.add(confirmerButton);

        add(panel, BorderLayout.CENTER);
    }

    private void confirmerPaiement() {
        String numeroCarte = numeroCarteField.getText().trim();
        String codeSecret = new String(codeSecretField.getPassword());
        String dateExpiration = dateExpirationField.getText().trim();
        String adresseDepart = adresseDepartField.getText().trim();
        String adresseArrivee = adresseArriveeField.getText().trim();

        // Vérification des données de carte bancaire et des adresses
        if (isValidCarte(numeroCarte) && isValidCodeSecret(codeSecret) && isValidDateExpiration(dateExpiration) && !adresseDepart.isEmpty() && !adresseArrivee.isEmpty()) {
            // Convertir la date d'expiration en java.sql.Date
            try {
                String[] dateParts = dateExpiration.split("/");
                int month = Integer.parseInt(dateParts[0]);
                int year = Integer.parseInt(dateParts[1]);

                // Créer un objet Calendar pour obtenir la date d'expiration
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, month - 1); // Les mois dans Calendar commencent à 0
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.DAY_OF_MONTH, 1); // Jour fixé à 1 pour toutes les dates d'expiration du mois

                // Obtenir la date d'expiration en java.sql.Date
                java.sql.Date expirationDate = new java.sql.Date(calendar.getTimeInMillis());

                if (expirationDate.after(new java.sql.Date(System.currentTimeMillis()))) {
                    JOptionPane.showMessageDialog(this, "Paiement réussi !");
                    dispose();
                    //etat2.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur : La date d'expiration est passée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Erreur : Format de date d'expiration incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erreur : Veuillez remplir tous les champs correctement.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Les méthodes isValidCarte, isValidCodeSecret et isValidDateExpiration doivent être déplacées ici

    // Vérifie si le numéro de carte est au format valide
    private boolean isValidCarte(String numeroCarte) {
        // Ici, vous pouvez implémenter votre propre logique de validation pour le numéro de carte
        return numeroCarte.matches("\\d{16}"); // Exemple de validation : 16 chiffres
    }

    // Vérifie si le code secret est au format valide
    private boolean isValidCodeSecret(String codeSecret) {
        // Ici, vous pouvez implémenter votre propre logique de validation pour le code secret
        return codeSecret.length() == 3; // Exemple de validation : 4 chiffres
    }

    // Vérifie si la date d'expiration est au format valide
    private boolean isValidDateExpiration(String dateExpiration) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateExpiration);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

    private void showPaiementInterface() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PaiementInterface(selectedNom, selectedPrenom, selectedmontant).setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Commander().setVisible(true);
                }
            });
    }
}

class MaCommande extends JFrame {

    private class ButtonMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.doClick(); // Cliquer sur le bouton
        }
    }

    public MaCommande() {
        setTitle("Ma Commande");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        initComponents();
        fetchCommandeFromDatabase(); // Récupérer les données de la base de données
    }

    private void initComponents() {
        GradientPanel panel = new GradientPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 1));

        /*JButton retour = new JButton("Retour");
            retour.setBackground(Color.GRAY); // Fond bouton
            retour.setForeground(Color.white);
            retour.addActionListener(e -> {
            dispose();
        });

        retour.addMouseListener(new ButtonMouseListener());
        panel.add(retour);*/

        JLabel labelChauffeurMontant = createStyledLabel("", new Font("Arial", Font.BOLD, 80), Color.WHITE);
        contentPanel.add(labelChauffeurMontant);

        JLabel labelAdresseDepart = createStyledLabel("", new Font("Arial", Font.BOLD, 65), Color.WHITE);
        contentPanel.add(labelAdresseDepart);

        JLabel labelAdresseArrivee = createStyledLabel("", new Font("Arial", Font.BOLD, 65), Color.WHITE);
        contentPanel.add(labelAdresseArrivee);

        panel.add(contentPanel, gbc);
        add(panel);
    }

    private void fetchCommandeFromDatabase() {
        // Connexion à la base de données et requête pour récupérer les données de commande
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:taxmex.db")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT mon_chauffeur, montant, adresse_depart, adresse_arrivee FROM commande");

            while (resultSet.next()) {
                String monChauffeur = resultSet.getString("mon_chauffeur");
                double montant = resultSet.getDouble("montant");
                String adresseDepart = resultSet.getString("adresse_depart");
                String adresseArrivee = resultSet.getString("adresse_arrivee");

                // Mise à jour du texte des labels avec les données de la commande
                JPanel contentPanel = (JPanel) ((GradientPanel) getContentPane().getComponent(0)).getComponent(0);
                JLabel labelChauffeurMontant = (JLabel) contentPanel.getComponent(0);
                JLabel labelAdresseDepart = (JLabel) contentPanel.getComponent(1);
                JLabel labelAdresseArrivee = (JLabel) contentPanel.getComponent(2);

                labelChauffeurMontant.setText(monChauffeur + " : " + montant);
                labelAdresseDepart.setText("Adresse de départ : " + adresseDepart);
                labelAdresseArrivee.setText("Adresse d'arrivée : " + adresseArrivee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JLabel createStyledLabel(String text, Font font, Color foregroundColor) {
        JLabel label = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setPaint(new GradientPaint(0, 0, new Color(206, 17, 38), getWidth(), getHeight(), new Color(0, 104, 71)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g); // Draw text on top of the background
                g2d.dispose();
            }
        };
        label.setFont(font);
        label.setForeground(foregroundColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MaCommande().setVisible(true);
        });
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(new GradientPaint(0, 0, new Color(206, 17, 38), getWidth(), getHeight(), new Color(0, 104, 71)));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

class TitlePanel extends JPanel {
    private static final String[] LETTER_IMAGES = {"T.png", "A.png", "X.png", "tiret.png", "M.png", "E.png", "X.png"};
    private static final int INTERVAL = 1000; // intervalle entre chaque lettre (en millisecondes)
    private int currentLetterIndex = 0;
    private Timer timer;

    public TitlePanel() {
        setOpaque(false);
        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentLetterIndex < LETTER_IMAGES.length) {
                    addLetter(LETTER_IMAGES[currentLetterIndex]);
                    currentLetterIndex++;
                    revalidate(); // assure que le panneau est redessiné après l'ajout d'une lettre
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    private void addLetter(String imagePath) {
        try {
            Image image = ImageIO.read(new File(imagePath));
            double scaleFactor = (double) getHeight() / 5.0 / (double) image.getHeight(null);
            int scaledWidth = (int) ((double) image.getWidth(null) * scaleFactor);
            int scaledHeight = (int) ((double) image.getHeight(null) * scaleFactor);
            Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            JLabel letterLabel = new JLabel(new ImageIcon(scaledImage));
            add(letterLabel);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Centrer le texte horizontalement et verticalement
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int titleWidth = getPreferredSize().width;
        int titleHeight = getPreferredSize().height;
        int x = (panelWidth - titleWidth) / 2;
        int y = (panelHeight - titleHeight) / 2;
        // Dessiner chaque lettre à la position correcte
        Component[] components = getComponents();
        int yOffset = (titleHeight > panelHeight) ? 0 : (panelHeight - titleHeight) / 2;
        for (Component component : components) {
            int width = component.getPreferredSize().width;
            component.setBounds(x, y + yOffset, width, titleHeight);
            x += width;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // Taille préférée basée sur la taille des images de lettres
        int totalWidth = 100;
        int maxHeight = 100;
        for (String imagePath : LETTER_IMAGES) {
            try {
                Image image = ImageIO.read(new File(imagePath));
                totalWidth += (int) ((double) image.getWidth(null) * ((double) getHeight() / 5.0 / (double) image.getHeight(null)));
                maxHeight = Math.max(maxHeight, (int) ((double) image.getHeight(null) * ((double) getHeight() / 5.0 / (double) image.getHeight(null))));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new Dimension(totalWidth, maxHeight);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Title Example");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 200);

                TitlePanel titlePanel = new TitlePanel();
                frame.add(titlePanel);

                frame.setVisible(true);
            }
        });
    }
}