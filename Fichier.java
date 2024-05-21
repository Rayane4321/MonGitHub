package Conteneur;

import java.io.File;

public class Fichier {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\rayan\\OneDrive\\Bureau\\Baba.txt");
		System.out.println(file.getAbsoluteFile());

		file.delete(); 
		// efface bien le fichier mais il faut le lancer pour que l'icone parte

		if(file.exists() == true) { 
			
			System.out.println("Fichier existant");
			
		}

	}

}
