package Conteneur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;



public class Pendu {
	private static int index;
	private static String motLigne;
	private static HashMap <Integer,String> dictionnaire = new HashMap <Integer,String>();
	private static int nbVie = 0;
	private static String motFinal;
    private static StringBuilder sb = new StringBuilder();
    private static ArrayList <Character> lettres = new ArrayList <Character>();
	private static boolean etat;



	
	public static void main(String[] args) {
		/* TODO Auto-generated method stub
			1. Lire le fichier motLigne par motLigne (1 mot par motLigne) OK
			2. Choisir un mot random: 
				indice random: OK
				afficher le mot � cet indice OK
			3. Diviser le mot en char:
				Stocker le mot aleatoirement choisi OK
				Le diviser OK
			4. Scanner user lettre
			5. Comparer lettre en parcourant le mot
					Si oui �crit toutes les occurences de la lettre
					Si non - 1 chance (10 chances max par exemple, quand GUI dessinera le pendu petit � petit)
			Si r�ussi sans gacher toutes les chances f�licitation / Sinon game over
		*/
		
		lireFichier();
		choisirEtAfficherMotRandom(); 
		d�composerMot(getMot(index));
		sb = initialiserSB();
		compteARebours();
		//transformerMotEnTableau();
	}
	
	public static String lireFichier() {
		
		try {
			
			FileReader frd = new FileReader("C:\\Users\\rayan\\eclipse-workspace\\MyFirstProgram\\src\\Conteneur\\Dico.txt");
			BufferedReader brd = new BufferedReader(frd);
			
			// on ne fait pas motLigne = brd.readLine ici car sinon motLigne sera �gal au premier mot qui est A
			
			while((motLigne = brd.readLine()) != null) {
				// afficher tout le contenue du fichier: System.out.println(motLigne);
				index++;
				dictionnaire.put(index,motLigne); // ajoute le mot � l'ArrayList
				
			}
			//Afficher Array: System.out.println(dictionnaire);
		}
		catch(IOException e) {
			System.out.print("Un probl�me est survenu");
			e.printStackTrace(); // permet de localiser l'erreur s'il y en a
			
		}
		return motLigne;
	}
	
	
	public static String getMot(int cl�) {
		return dictionnaire.get(cl�);
	}
	
	public static int getTailleMot() {
		return getMot(index).length();
	}
	
	
	public static int getVie() {
		return nbVie;
	}
	
	
	public static int choisirEtAfficherMotRandom() {
		//TODO Mot � choisir avec Random dont la limite sera la taille de notre fichier (donc ici repr�sent� par index)
		Random rd = new Random();
		int x = rd.nextInt(dictionnaire.size()); // valeur max = nb de mots dans le fichier
		
		index = x;
		//Afficher nb random: System.out.println(x);
		//Afficher le mot Random: System.out.println("Mot random: " + dictionnaire.get(x));			

		return index;
	}
	
	
	public static char d�composerMot(String mot) {
		char motD�compos� = 0;
		for (int i = 0; i < mot.length(); i++) {
			motD�compos� = mot.charAt(i);
			//Affiche le mot d�compos�: System.out.println(motD�compos�);
		}
		return motD�compos�;
	}
	
	

	public static StringBuilder initialiserSB() {
		for (int i = 0; i < getTailleMot();i++) {
			sb.append("_");
		}
		sb.setLength(getTailleMot()); // Limiter la longueur � la taille maximale
		motFinal = sb.toString();
		System.out.println("Mot en cours: "+motFinal);

		//Afficher taille du sb:  System.out.println(sb.length());
		//Afficher taille du mot: System.out.println(getTailleMot());
	
		return sb;
	}	
	
	
	public static void comparerLettreALettre() {
		
        int position = 0;
        boolean etat = false;
        //boolean etatFinal = false;
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Tapper une lettre: ");
        char lettreTest�e = sc.next().charAt(0);

        if (lettreDejaTest�e(lettreTest�e)) { // pas besoin de mettre == true
            System.out.println("Lettre d�j� test�e, veuillez sp�cifier une nouvelle lettre: ");
        } 
        
        else {
        	
            String mot = getMot(index);
            while (position < getTailleMot()) {
            	
                char lettreCourante = mot.charAt(position);
                
                if (lettreTest�e == lettreCourante) {
                    sb.setCharAt(position, lettreTest�e);
                    etat = true;
                }
                position++;
            }
            
            if (etat) {
                System.out.println("Lettre trouv�e !");
                //System.out.println(sb);
                motFinal = sb.toString();
                System.out.println("Mot en cours: "+motFinal);
            } 
            
            else {
                System.out.println("Lettre non trouv�e !");
                System.out.println(motFinal);
                nbVie++;
            }
            lettres.add(lettreTest�e); // Ajouter la lettre test�e � l'ext�rieur de la boucle
        }
        //return etatFinal;
    }
	//TODO 2 pbs � r�soudre: mot mis � jour mais lettres font des trucs bizarres, 
	//quand lettre en double bouge tout,
	//si lettre deja teste�
	
	
	public static void compteARebours(){
		// pour dire diff�rent . equals mettre juste un ! avant le String.equals(autreString)
		while(!motFinal.equals(getMot(index)) && nbVie < 11) {
			
			comparerLettreALettre();
			System.out.println("Il vous reste: "+(11-nbVie)+" vie");
			
			if (nbVie == 11) { // Condition de d�faite
				System.err.println(("GAME OVER"));
				System.out.println("Le mot � deviner �tait: "+ getMot(index));
			}
			// Condition de victoire: mot trouv� en 11 coups ou moins
			// Tr�s important: pas mettre ==  car compare valeur memoire et non lettres, pour comparer lettres: .equals
			else if (motFinal.equals(getMot(index))  && nbVie <=11){ 
				System.out.println("F�licitation vous avez devin� le mot !");
			}
		}
	}
	
	public static boolean lettreDejaTest�e(char lettreTest�e) {
	    for (int i = 0; i < lettres.size(); i++) {
	        if (lettres.get(i) == lettreTest�e) {
	            System.out.println("ATTENTION: Lettre d�j� test�e !");
	            return true; // Si la lettre est d�j� dans la liste, retourne true
	        }
	    }
	    lettres.add(lettreTest�e); // Si la lettre n'est pas dans la liste, l'ajoute et swith etat
	    return false; 
	}
	
	
	public static void oneShot(){
		// TODO faire la methode oneShot pour trouver le mot en un coup
		
	}
		

}
