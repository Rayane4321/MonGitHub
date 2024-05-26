import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;





class PiocheCartesActions {

    private static List<File> images;
    private static List<File> imagesRestantes;
    private static JFrame frame;
    private static Map<File, Boolean> cartesSelectionnees;

    public static void main(String[] args) {
        // Chemin du répertoire contenant les images
        String cheminRepertoire = "C:\\Users\\rayan\\OneDrive\\Images\\Succession\\Cartes Actions";

        // Lire les fichiers images à partir du répertoire
        images = lireImages(cheminRepertoire);
        imagesRestantes = new ArrayList<>(images);
        cartesSelectionnees = new HashMap<>();

        while (!imagesRestantes.isEmpty()) {
            // Sélectionner cinq images aléatoirement parmi les images restantes
            List<File> imagesAleatoires = selectionnerImagesAleatoires(imagesRestantes, 5);

            // Créer la fenêtre principale
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, imagesAleatoires.size()));

            // Ajouter les images à la fenêtre principale
            for (File image : imagesAleatoires) {
                afficherImage(image);
            }

            // Afficher les images aléatoires
            System.out.println("Voici cinq images choisies aléatoirement :");
            frame.pack();
            frame.setVisible(true);

            // Attendre jusqu'à ce que toutes les images soient sélectionnées
            synchronized (imagesRestantes) {
                try {
                    imagesRestantes.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Fermer la fenêtre principale
            frame.setVisible(false);
            frame.dispose();
        }

        System.out.println("Toutes les cartes ont été sélectionnées.");

        // Terminer le programme
        System.exit(0);
    }

    // Méthode appelée lorsqu'une image est cliquée
    private static void onImageClicked(File imageChoisie) {
        imagesRestantes.remove(imageChoisie);
        cartesSelectionnees.put(imageChoisie, true);
        System.out.println("L'image \"" + imageChoisie.getName() + "\" a été sélectionnée.");

        // Rafraîchir la liste des images restantes
        imagesRestantes = new ArrayList<>();
        for (File image : images) {
            if (!cartesSelectionnees.containsKey(image)) {
                imagesRestantes.add(image);
            }
        }

        // Vérifier s'il n'y a plus d'images restantes
        if (imagesRestantes.isEmpty()) {
            synchronized (imagesRestantes) {
                imagesRestantes.notify();
            }
        }

        // Rafraîchir la fenêtre principale
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        // Sélectionner cinq nouvelles images aléatoirement
        List<File> nouvellesImagesAleatoires = selectionnerImagesAleatoires(imagesRestantes, 5);

        // Ajouter les nouvelles images à la fenêtre principale
        for (File image : nouvellesImagesAleatoires) {
            afficherImage(image);
        }

        // Afficher les images aléatoires mises à jour
        frame.pack();
        frame.setVisible(true);
    }

    // Méthode pour lire les fichiers images à partir d'un répertoire
    private static List<File> lireImages(String cheminRepertoire) {
        List<File> images = new ArrayList<>();
        File repertoire = new File(cheminRepertoire);
        if (repertoire.isDirectory()) {
            File[] fichiers = repertoire.listFiles();
            if (fichiers != null) {
                for (File fichier : fichiers) {
                    if (estFichierImage(fichier)) {
                        images.add(fichier);
                    }
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un répertoire valide.");
        }
        return images;
    }

    // Méthode pour vérifier si un fichier est une image
    private static boolean estFichierImage(File fichier) {
        String nomFichier = fichier.getName().toLowerCase();
        return nomFichier.endsWith(".jpg") || nomFichier.endsWith(".jpeg") || nomFichier.endsWith(".png");
    }

    // Méthode pour sélectionner des images aléatoirement
    private static List<File> selectionnerImagesAleatoires(List<File> images, int nombreImages) {
        List<File> imagesAleatoires = new ArrayList<>();
        Random random = new Random();
        int taille = images.size();
        if (taille <= nombreImages) {
            return images;
        } else {
            Set<Integer> indexSelectionnes = new HashSet<>();
            while (imagesAleatoires.size() < nombreImages) {
                int index = random.nextInt(taille);
                if (!indexSelectionnes.contains(index)) {
                    indexSelectionnes.add(index);
                    File image = images.get(index);
                    imagesAleatoires.add(image);
                }
            }
        }
        return imagesAleatoires;
    }

    // Méthode pour afficher une image
    private static void afficherImage(File image) {
        try {
            BufferedImage img = ImageIO.read(image);
            if (img != null) {
                Image dimg = img.getScaledInstance(250, 350, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                JLabel label = new JLabel(imageIcon);
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        onImageClicked(image);
                    }
                });
                frame.getContentPane().add(label);
            } else {
                System.out.println("Impossible de lire l'image : " + image.getName());
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la lecture de l'image : " + image.getName());
            e.printStackTrace();
        }
    }
}





//demander la comparaison de la reponse avec la question

class ProgrammeQuestionsReponses {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Lire le fichier de questions
        List<String> questions = lireFichierQuestions("C:\\Users\\rayan\\OneDrive\\Images\\Succession\\QuestionsReponses\\Questions.txt");

        // Demander le nombre de questions à l'utilisateur
        System.out.print("Veuillez choisir un nombre de questions : ");
        int nombreQuestions = scanner.nextInt();

        // Demander la difficulté des questions à l'utilisateur
        scanner.nextLine(); // Pour consommer la ligne vide restante
        System.out.print("Veuillez choisir la difficulté des questions (facile, moyen, difficile) : ");
        String difficulte = scanner.nextLine();

        // Filtrer les questions en fonction de la difficulté choisie
        List<String> questionsFiltrees = filtrerQuestions(questions, difficulte);

        // Sélectionner aléatoirement le nombre de questions demandé
        List<String> questionsSelectionnees = selectionnerQuestionsAleatoires(questionsFiltrees, nombreQuestions);

        // Demander les réponses à l'utilisateur et les associer aux questions
        Map<String, String> correspondancesReponses = new HashMap<>();
        System.out.println("Veuillez entrer vos réponses :");
        for (String question : questionsSelectionnees) {
            System.out.print(question + " : ");
            String userInput = scanner.nextLine();
            correspondancesReponses.put(question, userInput);
        }

        // Lire le fichier de réponses pour les questions posées
        Map<String, String> vraiesReponses = lireFichierReponses("C:\\Users\\rayan\\OneDrive\\Images\\Succession\\QuestionsReponses\\Reponses.txt");

        // Afficher les correspondances questions-réponses pour les questions posées
        System.out.println("Correspondances questions-réponses pour les questions posées :");
        for (String question : questionsSelectionnees) {
            String vraieReponse = vraiesReponses.get(question);
            System.out.println(question + " : " + vraieReponse);
        }
    }

    private static List<String> lireFichierQuestions(String nomFichier) {
        List<String> questions = new ArrayList<>();
        Path path = Paths.get(nomFichier);
        try {
            List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String ligne : lignes) {
                questions.add(ligne);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier de questions : " + e.getMessage());
        }
        return questions;
    }

    private static List<String> filtrerQuestions(List<String> questions, String difficulte) {
        List<String> questionsFiltrees = new ArrayList<>();
        for (String question : questions) {
            // Ajoutez ici la logique de filtrage en fonction de la difficulté
            if (question.contains(difficulte)) {
                questionsFiltrees.add(question);
            }
        }
        return questionsFiltrees;
    }

    private static List<String> selectionnerQuestionsAleatoires(List<String> questions, int nombreQuestions) {
        List<String> questionsSelectionnees = new ArrayList<>();
        if (nombreQuestions >= questions.size()) {
            questionsSelectionnees.addAll(questions);
        } else {
            Collections.shuffle(questions);
            questionsSelectionnees.addAll(questions.subList(0, nombreQuestions));
        }
        return questionsSelectionnees;
    }

    private static Map<String, String> lireFichierReponses(String nomFichier) {
        Map<String, String> correspondancesReponses = new HashMap<>();
        Path path = Paths.get(nomFichier);
        try {
            List<String> lignes = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String ligne : lignes) {
                String[] parties = ligne.split("\\|");
                if (parties.length == 2) {
                    String question = parties[0];
                    String reponse = parties[1];
                    correspondancesReponses.put(question, reponse);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier de réponses : " + e.getMessage());
        }
        return correspondancesReponses;
    }
}


class Quiz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Bienvenue dans le quiz !");
        
        String questionsFilePath = "C:\\Users\\rayan\\OneDrive\\Images\\Succession\\QuestionsReponses\\Questions.txt";
        String answersFilePath = "C:\\Users\\rayan\\OneDrive\\Images\\Succession\\QuestionsReponses\\Reponses.txt";

        System.out.print("Veuillez choisir la difficulté (facile, moyen, difficile) : ");
        String difficulty = scanner.nextLine();
        
        System.out.print("Veuillez choisir le nombre de questions : ");
        int numQuestions = scanner.nextInt();
        
        List<String> questions = readQuestionsFromFile(questionsFilePath);
        List<String> answers = readAnswersFromFile(answersFilePath);
        
        List<String> selectedQuestions = selectQuestions(questions, answers, difficulty, numQuestions);
        
        int score = askQuestions(selectedQuestions, answers);
        
        System.out.println("Votre score : " + score + "/" + numQuestions);
        System.out.println("Merci d'avoir participé au quiz !");
    }
    
    private static List<String> readQuestionsFromFile(String filePath) {
        List<String> questions = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        
        return questions;
    }
    
    private static List<String> readAnswersFromFile(String filePath) {
        List<String> answers = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                answers.add(line);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        
        return answers;
    }
    private static List<String> selectQuestions(List<String> questions, List<String> answers, String difficulty, int numQuestions) {
        List<String> selectedQuestions = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < questions.size(); i++) {
            String question = questions.get(i);
            String answer = answers.get(i);
            
            String[] questionData = question.split("\\|");
            String questionText = questionData[0].trim();
            String questionDifficulty = questionData[1].trim();
            
            if (questionDifficulty.equalsIgnoreCase(difficulty)) {
                selectedQuestions.add(questionText + "|" + answer);
            }
            
            if (selectedQuestions.size() == numQuestions) {
                break;
            }
        }
        
        return selectedQuestions;
    }
    
    private static int askQuestions(List<String> questions, List<String> answers) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        
        for (String question : questions) {
            String[] questionData = question.split("\\|");
            String questionText = questionData[0].trim();
            String correctAnswer = questionData[1].trim();
            
            System.out.println(questionText);
            System.out.print("Votre réponse : ");
            String userAnswer = scanner.nextLine();
            
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                System.out.println("Bonne réponse !");
                score++;
            } else {
                System.out.println("Mauvaise réponse !");
                System.out.println("Bonne réponse : " + correctAnswer);
            }
            System.out.println("----------------------------------");

        }
        
        return score;
    }
    
}

