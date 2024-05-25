package Conteneur;

import java.util.Scanner;
import java.util.ArrayList;

public class Object {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Voiture v1 = new Voiture ("458 Italia", "Ferrari", 500000 );
		Voiture v2 = new Voiture ("Veyron"    , "Bugati" , 1000000);
		Voiture v3 = new Voiture ("Camaro"    , "Chevrolet", 300000 );
		Voiture v4 = new Voiture ("Zonda", "Pagani", 900000);
		Voiture v5 = new Voiture ("Huayra", "Pagani", 1000000);
		Voiture v6 = new Voiture ("Regera", "Koenigseg", 2000000);
		Voiture v7 = new Voiture ("R8", "Audi", 600000);
		
		Concessionnaire c1 = new Concessionnaire();
		c1.addCatalogue(v1);
		c1.addCatalogue(v2);
		c1.addCatalogue(v3);
		c1.addCatalogue(v4);
		c1.addCatalogue(v5);
		c1.addCatalogue(v6);
		c1.addCatalogue(v7);
		
		
		//c1.ajouterCatalogue(c1.getCatalogue());
		c1.afficherCatalogue();
		c1.enleverCatalogue();
						

		/*if (v1.accélérer("458 Italia",v1.getVitesse()) > 450) {
			System.out.println("Attention à la limite de vitesse !");
			v1.freiner(v1.getVitesse());
			if (v1.accélérer("458 Italia", v1.getVitesse()) <=  450) {
				System.out.println("Félicitations !");
			}
		}*/
	}
}
	
class Voiture{
	private String modèle, marque;
	private int vitesse, prix;

	public Voiture(String modèle, String marque, int prix) {
		this.modèle  = modèle;
		this.marque  = marque;
		this.vitesse = 0;
		this.prix    = prix;
	}
	
	/*public void klaxonner(String name) {
		System.out.print("Votre "+name+" klaxonne");
	}
	*/
	
	public int accélérer(String name, int speed) {
		Scanner sc = new Scanner(System.in);
		System.out.println("De combien vous voulez accélérer ?");
		speed = speed + sc.nextInt();
		System.out.println("Votre "+name+" va à: " + speed + " km/h");
		return vitesse = speed;
	}
	
	public int freiner(int speed) {
		if (speed > 450) {
			speed = (speed-(speed - 450));
		}
		System.out.println("Votre nouvelle vitesse est de: "+ speed + " km/h");
		return vitesse = speed;
	}
	
	
	public int getVitesse() {
		return vitesse;
	}
	public String getModèle() {
		return modèle;
	}
	public String getMarque() {
		return marque;
	}
	public int getPrix() {
		return prix;
	}

} 


class Concessionnaire {
	private Voiture voiture;
	private Integer index = 1;
	private ArrayList <Voiture> catalogue = new ArrayList <Voiture> ();


	public Concessionnaire() {
		this.catalogue = catalogue;
	}
	
	public void addCatalogue(Voiture voiture){
		catalogue.add(voiture);
		
	}
	
	public void afficherCatalogue() {
		
		System.out.println("\t \t Le Catalogue des voitures \n");
		for(Voiture voiture: catalogue) {			
			System.out.printf(index +". [Marque]: %-10s " ,  voiture.getMarque());
			System.out.printf(" [Modèle]: %-10s " ,  voiture.getModèle());
			System.out.printf(" [Prix]:   %,9d  " ,  voiture.getPrix());

			System.out.println();
		    index ++;
		    
		}
	}
	
	public void ajouterCatalogue(ArrayList cars) {
		//TODO faire méthode ajouter;
		Scanner sc = new Scanner(System.in);
		System.out.println("Ajouter modèle: ");
		String modèle = sc.nextLine();
		
		System.out.println("Ajouter prix: ");
		int prix = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Ajouter marque: ");
		String marque = sc.nextLine();
		
		
		Voiture voiture = new Voiture (modèle, marque, prix);
		cars.add(voiture);
		
	}
	
	public void enleverCatalogue() {
		//TODO faire méthode enlever;
		Scanner sc = new Scanner(System.in);
		System.out.println("Quelle ligne supprimée");
		int i = sc.nextInt();
		
		if (i > index) System.out.println("Cette ligne n'existe pas !");
		else { 
		catalogue.remove(i-1);
		index = 1;
		afficherCatalogue();
		}
		
	}
	
	public void conseiller() {
		/*TODO dans cette méthode: le conseiller va orienter le user en lui posant des questions: 
		 * prix, modèle...  */
	}
	
	public void modifier() {
		// TODO faire modifier
	}
	
	public ArrayList getCatalogue() {
		return catalogue;
	}
	
	
}
















