package Conteneur;

import java.util.Scanner;
import java.util.Random;

 class figure{
	public static void main(String[]args){
		Scanner colonnes=new Scanner(System.in);
		System.out.println("Combien de colonnes?");
		int C=colonnes.nextInt();
		System.out.println("vous avez choisi"+ " "+ C + " "+ "colonnes");
		Scanner lignes= new Scanner(System.in);
		System.out.println("Combien de lignes?");
		int L=lignes.nextInt();
		System.out.println("vous avez choisi"+ " "+ L + " "+ "lignes");
		
		for(int i=1;i<L;i++){
			System.out.print("*");

			for(int j=1;j<C;j++){
				System.out.print("*");
			
			}
		System.out.println(" ");

		}

	}
}


class carre{
		public static void main(String[]args){

			System.out.println("Combien de lignes?");
			Scanner clavier1=new Scanner(System.in);
			int L=clavier1.nextInt();

			System.out.println("Combien de colonnes?");
			Scanner clavier2=new Scanner(System.in);
			int C=clavier2.nextInt();

			for(int i=1;i<L;i++){

				for(int j=1;j<C;j++){
					System.out.print("*");

			}

			System.out.println(" ");

			}
        }
	}


class triangle1{
	//programme pour le triangle principal sans vide
	public static void main(String[]args){

		System.out.println("n?");
		Scanner clavier1=new Scanner(System.in);
		int n=clavier1.nextInt();

		for (int ligne=1;ligne<n;ligne++){
			//doit etre inf a ligne et pas a n car ligne=n-1
			for (int tr1=1;tr1<ligne;tr1++){
				System.out.print("* ");
			}
			System.out.println(" ");

		}
	}
}	


class parral{
	//programme pour le triangle 2 avec avant un triangle vide
	public static void main(String[]args){

		System.out.println("n?");
		Scanner clavier1=new Scanner(System.in);
		int n=clavier1.nextInt();

		for (int ligne=0; ligne<n; ligne++) {
			for (int trVide=n-1; trVide>ligne; trVide--) {
				System.out.print(" ");

			}
			for (int tr2=1;tr2<n;tr2++){
				System.out.print("* ");

			}
		System.out.println(" ");

			
		}
	}
}

class triangle2{
	//programme pour le triangle 2 avec avant un triangle vide
	public static void main(String[]args){

		System.out.println("n?");
		Scanner clavier1=new Scanner(System.in);
		int n=clavier1.nextInt();

		for (int ligne=1; ligne<n; ligne++) {
			for (int trVide=n-1; trVide>ligne; trVide--) {
				System.out.print(" ");

			}
			for (int tr2=1;tr2<5;tr2++){
				System.out.print("* ");

			}
		System.out.println(" ");

			
		}
	}
}




class rectangleVide{
	public static void main(String[]args){
		System.out.println("Longueur");
		Scanner sc=new Scanner(System.in);
		int longueur=sc.nextInt();
		System.out.println("Largeur");
		Scanner dc=new Scanner(System.in);
		int largeur=dc.nextInt();

		for (int colonnes=1; colonnes<largeur;colonnes++){
			System.out.print("* ");

			if (colonnes==1 || colonnes==largeur-1){

				for (int j=1;j<longueur;j++){
					System.out.print("* ");

				}
			}
			else{
				for (int vide=0; vide<(longueur-2);vide++){
					System.out.print("  ");
				}
				System.out.print("* ");

			}
			

		System.out.println(" ");

		}




	}
}

class pyramide{
	public static void main(String[]args){
		System.out.println("Choisissez nombre n");
		Scanner c1=new Scanner(System.in);
		int n=c1.nextInt();
		


		//nombre de lignes
		for (int ligne=0;ligne<n;ligne++){
			// triangle d'espace
			for (int vide=ligne;vide<n;vide++){
				System.out.print(" ");
			}
			//pyramide, boucle la plus importante
			for (int tr1=0;tr1<ligne;tr1++){
				System.out.print("* ");
			}
			System.out.println("* ");
		} 
	}
}





class epee{
	public static void main(String[]args){
		System.out.println("Choisissez la largeur de la pointe");
		Scanner c1=new Scanner(System.in);
		int pointe=c1.nextInt();

		System.out.println("Choisissez la longueur de la lame");
		Scanner c2=new Scanner(System.in);
		int lame=c2.nextInt();

		System.out.println("Choisissez longueur du quillon");
		Scanner c3=new Scanner(System.in);
		int quillon=c3.nextInt();

		System.out.println("Choisissez largeur du manche");
		Scanner c4=new Scanner(System.in);
		int manche=c4.nextInt();
		
		/*//espace
		for (int i=0;i<n;i++){
			for (int j=0;j<m;j++){
				System.out.print(" ");
			}
			System.out.println(" ");
		}*/

		//Pyramide en haut de l'epee (pointe)
		for (int i=0;i<pointe;i++){
			for (int j=pointe;j>i;j--){
				System.out.print(" ");
			}
			for (int k=0;k<i;k++){
				System.out.print("* ");
			}
			System.out.println(" ");

		}
		//1er rectangle de l'epee (lame)
		for (int i=0;i<lame;i++){
			for (int j=0;j<pointe;j++){
				System.out.print("* ");
			}
		System.out.println(" ");
		}
		//2eme rectangle (quillon)
		for (int i=0;i<pointe;i++){
			for (int j=0;j<quillon;j++){
				System.out.print("* ");
			}
		System.out.println(" ");
		}
		//3eme rectangle (manche)
		for (int i=0;i<manche;i++){
			for (int j=0;j<pointe;j++){
				System.out.print("* ");
			}
		System.out.println(" ");
		}

	}
}

class pyrinvers{
	public static void main(String[]args){
		int n= Integer.parseInt(args[0]);

		for (int i = 0; i < n; i++){
			for (int vide = 0; vide < i; vide++){
				System.out.print("  ");
			}
			for (int tr1 = i; tr1 < n-1; tr1++){
				System.out.print("* ");
			}
			for (int tr1 = i; tr1 < n; tr1++){
				System.out.print("* ");
			}
			System.out.println("  ");
		}
		for (int i = 0; i < n; i++){
			for (int vide = i; vide < n; vide++){
				System.out.print("  ");
			}
			for (int tr1 = 0; tr1 < i-1; tr1++){
				System.out.print("* ");
			}
			for (int tr1 = 0; tr1 < i; tr1++){
				System.out.print("* ");
			}
			
			System.out.println("  ");
		}

	}
}

