package Conteneur;

import java.util.Scanner;

public class Geometrique {

    public static void main(String[] args) {
    	
    	int ligne;
    	int colonne;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.print("Nb ligne:");
    	ligne = sc.nextInt();
    	
    	System.out.print("Nb colonne:");
    	colonne = sc.nextInt();
    	
    	for (int i = ligne; i > 0; i--) {
    		System.out.println();
    		for (int j = i; j > 0; j--) {
    			System.out.print(" ");
    			
    		}
    		for (int k = i; k < ligne; k++) {
    			System.out.print("*");
    		}
    		for (int l = i; l < ligne; l++) {
    			if (l == ligne - 1) {
    				System.out.print("");
    			}
    			else {
        			System.out.print("*");

    			}
    		}


		}


    	
        
    }

}
