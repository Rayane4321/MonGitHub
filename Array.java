package Conteneur;

import java.util.*;

public class Array {

	public static void main(String[] args) {
		
		ArrayList<ArrayList<String>> courses = new ArrayList();
		
		ArrayList<String> boulangerie = new ArrayList();
		boulangerie.add("Donut");
		boulangerie.add("Pain");
		boulangerie.add("Cookie");
		
		ArrayList<String> supermarché = new ArrayList();
		supermarché.add("Fromage");
		supermarché.add("Pizza");
		supermarché.add("Légumes");

		courses.add(boulangerie);
		courses.add(supermarché);
		
		System.out.println(courses.get(0).get(1)); // renvoi l'élément 1 du 2D array

		courses.get(0).remove(0);

		for (ArrayList<String> nourriture:courses) {
			System.out.print(nourriture+",\t");
			
		}
	}
}
