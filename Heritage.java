package Conteneur;

import Pacpac.Shonen;

public class Heritage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Bandedessinée asterix = new Bandedessinée();
		Manga dragonBall = new Manga();
		Shonen fma = new Shonen();
		
		System.out.println("Pages db"+dragonBall.pages+"Nb cases"+dragonBall.cases);
		System.out.println("Asterix"+asterix.pages+"Noir&Blanc? :" +asterix.noirEtBlanc);
		System.out.println(fma.pages+fma.style);
		fma.coloriser();
		fma.lire();

		

	}

}
