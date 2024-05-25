import java.util.Scanner;
import java.util.Random;




/* 
a faire:

	-> class case avec les caracteristiques du terrain et definir un taux de spawn random des ressources et obstacles
	-> tours: 	-> chaque joueur a plusieurs unites et un tour consiste a toutes les faire bouger
	 			-> le premier tour chacun choisit son dirigeant

	-> systeme economique


*/

class Civ{
	private String nomDirigeant;
	private String pays;
	private String nomCiv;


	Civ(String dir, String p, String nc){
		nomDirigeant = dir;
		pays = p;
		nomCiv = nc;
	}

	String getPays(){
		return pays;
	}
	String getNomCiv(){
		return nomCiv;
	}

	String getNomDirigeant(){
		return nomDirigeant;
	}
}


class TestUniteTerrainCase{
	public static void main(String[] args){

		Terrain t1 = new Terrain (Integer.parseInt(args[0]), Integer.parseInt(args[1]));

		Case case1 = new Case(6,8,"Bois",	t1, 1, true, true);
		Case case2 = new Case(4,3,"Desert",	t1,	2, true, true);

		Unite a 			   = new Unite ("Cavalier",  		 19,	12,	"cavalier",        5,	0,	20, 4, 2, t1, case2); 
		Unite b 			   = new Unite ("Fantassin", 		 15,	14,	"soldat",          2,	5,	15, 2, 6, t1, case2);
		Unite c 			   = new Unite ("Tour",       		  5,	30,	"anti_cavalerie",  1,	2, 	12, 3, 5, t1, case1);



		//a.combat(b);
		//a.promo();	
		//t1.afficherField();
		//t1.getUnite();

		a.deplacementUnite();
		case1.tabRandomStocke();
		System.out.println("");


		//case1.remplacement();
		//a.getCase().getFieldCase(); // comment ne pas faire que field est nul
 
	}
}


class TestDirigeantCiv{
	public static void main(String[] args){

		Civ civ1 =  new Civ ("Simon_Bolivar"   ,  "Colombie"  ,	"Empire Colombien"	);
		Civ civ2 =  new Civ ("Salahdin"        , 	"Egypte"    ,  	"Empire Fatimides");
		Civ civ3 =  new Civ ("AlexandreLeGrand",	"Macedoine"	, 	"Empire macedonien");


		Terrain t1 = new Terrain (Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		Case case1 = new Case(6,8,"Bois",	t1, 1, true, true);


		//Unite speciale
		Unite cavalierDeBogota = new Unite ("CavalierDeBogota",  22,	10,	"cavalier",        3,	0,	20, 1, 2, t1, case1);
		Unite cavalierArcher   = new Unite ("CavalierArcher", 	 30,	 5,	"cavalier",        3,	0,	20, 7, 1, t1, case1);
		Unite hoplite    	   = new Unite ("Hoplite",			 25,    10, "soldat"  ,        2,   1,  10, 8, 9, t1, case1);

		cavalierArcher.placementUnite();

	
		Dirigeant simonBolivar 	   = new Dirigeant  ("Simon_Bolivar", 		civ1, cavalierDeBogota);
		Dirigeant salahdin    	   = new Dirigeant 	("Salahdin",			civ2, cavalierArcher	);
		Dirigeant alexandreLeGrand = new Dirigeant  ("Alexandre_Le_Grand",	civ3, hoplite			);

		
		System.out.println(salahdin.toStringPhraseDirigeant());
		System.out.println(salahdin.toStringStatsUnite());

	}
}


class Unite{

	private int atk;
	private int def;
	private String type;
	private int deplacement;
	private int pv;
	private int exp;
	private String nom;
	private int xBis, yBis;
	private Terrain terrain;
	private int [][]field; 
	private int u = 9; 		
	private Case caseX;
	private int n = 0;

	public static void main(String[] args){
		
		
		
	}

	Unite(String n,int a, int d, String t, int dep, int xp, int hp, int abscisse, int ordonee, Terrain ter, Case c){
		nom = n; //variables d'en haut qui sont egales a celles ci
		atk = a;
		def = d;
		type = t;
		deplacement = dep;
		exp = xp;
		pv = hp;
		xBis = abscisse;
		yBis = ordonee;
		terrain = ter;
		caseX = c;
	}


	void placementUnite(){
		//randomXYBIS(); Pour rajouter le spawn aleatoire au debut
		field = new int[terrain.getX()][terrain.getY()];
		field[xBis][yBis] = u;  							//par quoi va etre represesente l'unite
		for (int i = 1; i < field.length; i++){ 			// Dans une matrice en Java, chaque ligne peut avoir une longueur differente.
			for (int j = 1; j < field[i].length; j++){ 		// matrix.length represente le nombre de lignes de la matrice			
				System.out.print(field[i][j]+" ");       		//alors que matrix[i].length represente la longueur de la i-eme ligne de la matrice 
			}
			System.out.println("");

		}
	}


	 void deplacementUnite(){ // fait deplacer l'unite selon son nombre de deplacement disponible 
	 	while(deplacement > 0){
	 		mouvement();
	 		if (deplacement == 0){
	 			System.out.println("Fin de tour!");
	 		}
	 	}

	 }


	int mouvement(){ // fait bouger l'unite si les conditions sont remplies
		n = 0;
		placementUnite();
		System.out.println("");

		System.out.println("Pour faire bouger l'unite "+ nom +" presser D pour droite, G pour gauche, H pour Haut, B pour Bas, N pour ne pas bouger");
		Scanner sc = new Scanner(System.in);
		String reponse = sc.next(); 

		if (field[xBis][yBis] == u){	

			if (reponse.equals("D")){					// si on veut faire une methode scanner alors on peut comparer en faisant if(scanner().equals("X")) 
				if (yBis+1 < field.length && getCase().getFranchissable() == true){  	// si x+1 existe alors on fait avancer l'unite de 1 a droite alors...
            		yBis++; 															// déplacer l'unité vers la droite
            		deplacement--;														// si la case existe et si on peut se deplacer dessus (franchissable)		
				}
				else {
					System.out.println("Limite du terrain, pas possible d'aller plus a droite");
				}
			}

			else if (reponse.equals("G")){
				if (yBis-1 > 0 && getCase().getFranchissable() == true){ // pas >= car sinon l'unite sort du terrain
					yBis--;
					deplacement--;
				}
				else {
					System.out.println("Limite du terrain, pas possible d'aller plus a gauche");
				}
			}

			else if (reponse.equals("H")){
				if (xBis-1 > 0 && getCase().getFranchissable() == true){ 
					xBis--;
					deplacement--;

				}
				else {
					System.out.println("Limite du terrain, pas possible d'aller plus haut");
				}
			}

			else if(reponse.equals("B")){
				if (xBis+1 < field.length && getCase().getFranchissable() == true){
					xBis++;
					deplacement--;

				}
				else {
					System.out.println("Limite du terrain, pas possible d'aller plus bas");
				}

			}
			else if (reponse.equals("N")){
				System.out.println("Etes-vous sur? O/N");
				Scanner dc = new Scanner(System.in);
				String repNon = dc.next();
					if (repNon.equals("O")){
						deplacement = 0;
					}
					
				}
			else {
				System.out.println("Vous avez saisi une mauvaise commande, veuillez reessayer: ");
				mouvement();
				//return; // Le return est utilisé pour sortir de la méthode en cas de saisie incorrecte de l'utilisateur. 
						// Pour ne pas cumuler les mauvaises saisies une fois une bonne saisie faite
			}
			getCase().tabRandomStocke();
			// placementUnite() afficher le terrain avec la position de l'unite mise a jour, a mettre quand cette methode toute seule
			System.out.println("");
		}
		n++;
		return n;

	}

	int getN(){
		return n;
	}





	/*
	Randomiser le xbis et ybis:
	void randomXYBIS() {
	    Random rX = new Random();
	    xBis = rX.nextInt(x);
	    Random rY = new Random();
	    yBis = rY.nextInt(y);
	    System.out.print(xBis+","+yBis);
        
	}*/

	Case getCase(){
		return caseX;
	}

	int getXbis(){
		return xBis;
	}
	int getYbis(){
		return yBis;
	}
	int getExp(){
		return exp;
	}
	int getAtk(){
		return atk;
	}

	String getNomUnite(){
		return nom;
	}
	int getDeplacement(){
		return deplacement;
	}
	int getPv(){
		return pv;
	}
	int getDef(){
		return def;
	}
	Terrain getTerrain(){
		return terrain;
	}
	int[][] getField(){
		return field;
	}
		
	void combat(Unite adv){// on appelle la methode dans le main
		int degats;
		if (atk > adv.def){ // attaquant gagne
			System.out.print(toStringCombat(adv)); //x.methode  
			degats = adv.pv - (atk - adv.def); //pour a attaque b: 19 atk + 5 av = 24 atk - 14 def = 10 de degat
			adv.pv = adv.pv - degats;
			this.avantageUnite(adv);
			System.out.println("L'unite " + adv.nom +" se prend " + degats +" degats" + "\n" + "PV restants de l'unite: " + adv.pv);
		}

		else if(atk == adv.def){ // egalite
			this.avantageUnite(adv);
			System.out.print(toStringCombat(adv)+" Egalite, personne ne perd de points de vie !"); 
			//tostring direct car dans une methode
		}

		else{ // defaite de l'attaquant
			pv = pv - (atk - adv.def);
			this.avantageUnite(adv);
			System.out.print(toStringCombat(adv) + " Defaite de l'attaque ! PV restant: " + pv);

			}
		}

		private String toStringCombat(Unite adv){ //afficher les combats textuels
			//StringBuilder sb = new StringBuilder(adv.nom + "|"+adv.pv+"|" +nom+"|"+pv+"|"...etc);
			//sb.append(nom+" attaque "+ adv.nom +". "+" Puissance en jeu: " + atk + "/" +adv.def);
			String str = 
			"Unites en jeu: " + nom + "["+pv+"PV]"+ " / " + adv.nom + "["+adv.pv+"PV]" + "\n"+
			nom+ "["+atk+"ATK]" +" prend pour cible "+ adv.nom +"["+adv.def+"DEF]" +"\n";
			return str;
			  
		}

		private String toStringMalusBonus(Unite adv){
			return "attaque de "+nom +" apres avantages: "+nom+"["+atk+"ATK]"+"\n";
		}

		int avantageUnite(Unite adv){
			if (type.equals(adv.type)){
				System.out.print("Aucun avantage");
			}
			else if(type.equals("cavalier") & adv.type.equals("anti_cavalerie")){
				adv.atk = adv.atk + 5;				
				System.out.print("Malus"+adv.atk);

			}
			else if(type.equals("cavalier") & adv.type.equals("soldat")){
				atk = atk + 5;
				System.out.print("Un bonus s'applique, "+toStringMalusBonus(adv));

			}
			else if(type.equals("soldat") & adv.type.equals("anti_cavalerie")){
				adv.atk = adv.atk + 5;
				System.out.print("Bonus"+adv.atk);

			}
			else if(type.equals("soldat") & adv.type.equals("cavalier")){
				adv.atk = adv.atk - 5;
				System.out.print("Malus"+adv.atk);

			}
			else if(type.equals("anti_cavalerie") & adv.type.equals("cavalier")){
				adv.atk = adv.atk + 5;
				System.out.print("Bonus"+adv.atk);

			}
			else if(type.equals("anti_cavalerie") & adv.type.equals("soldat")){
				adv.atk = adv.atk - 5;
				System.out.print("Malus"+adv.atk);
			}

			return adv.atk;

	}


		String promo(){
			String prom = new String();
			while(pv > 0){
				exp++;
				if (exp == 10){	//dire l'exp de telle unite alors...
					System.out.println("L'unite a assez d'experience pour monter en niveau, veuillez choisir une promotion: ");		
					Scanner sc = new Scanner(System.in);
					System.out.println("Puissance accrue");
					prom = sc.next();
					exp = 0;

				}

			} 
			return prom;

		}
	}
		
	class Joueur{
		private int tours;
		private boolean aQuideJoue;

		public static void main(String[] args){

			Joueur j1 = new Joueur(0,true);


		}

		Joueur(int t, boolean aQ){
			tours = t;
			aQuideJoue = aQ;


		}

		int getTour(){
			return tours;
		}

		void tourDeQui(Joueur j2){

			while(true){
				if (j2.aQuideJoue == true){ 
					aQuideJoue = false;
					//quand l'un est true alors c'est a son tour de jouer => faire une methode jeu avec la definition de ce qu'est un cours
					j2.aQuideJoue = false;

				}
				else{
					j2.aQuideJoue = true;
					aQuideJoue = false;

				}

				//stopper quand toutes les unites d'un joueur sont toutes eliminees 
					//si cette condition n'est pas realisee: fin du jeu au bout de x temps
			}
		}

		void turn(){ 




		//qu'est-ce qu'il se passe a chaque tour
			/* actions possibles: 
			- bouger (definir une methode bouger)
			- attaquer (methode combat)
			- limite de temps
			if temps < X
			if unite a bouge

			*/



		}

		void jeu(){
		


		}
	}

	class Terrain{

		private int[][] field;
		private int x, y;
		private Unite unite;

		public static void main(String[] args){
			Terrain t1 = new Terrain (Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			t1.afficherField();
			t1.getUnite();
			t1.getField();


			

		}

		Terrain(int abscisse, int ordonee){
			x = abscisse;
			y = ordonee;

		}			
		//mettre un setter pour unite
 		Unite getUnite(){
 			return unite;
 		}

		

		void afficherField(){
			//definir le tableau par rapport aux x et y entres en parametres
			field = new int[x][y];
			//afficher le tableau
        	for (int i = 0; i < field.length; i++) { 
            	for (int j = 0; j < field[i].length; j++) { 
                	System.out.print(field[i][j]); 
                }
                System.out.println();
            }
        }
        int getX(){
        	return x;
        }
        int getY(){
        	return y;
        }
        int[][]getField(){
        	return field;
        }
        void mouvement(){



        }


    }

    class Case{
    	private String type;
    	private Terrain terrain;
    	private int[][]field;
    	private int randomType;
    	private int xC, yC;
    	private int[][]fieldCase;
    	private boolean franchissable;
    	private Unite unite;
    	private boolean uniteDessus;
    	private int n = 0;
    	private Joueur joueur;



    	Case (int ab, int or, String t, Terrain ter, int r, boolean f, boolean uD){
    		xC = ab;
    		yC = or;
    		type = t;
    		terrain = ter;
    		randomType = r;
    		franchissable = f;
    		uniteDessus = uD;
    	}

    	public static void main(String[] args){


    	}
    	
    	void placementCase(){ // on prend le terrain et on va attribuer a chaque case un nombre random qui va correspondre a son type (voire caseType)
		//fieldCase[xC][yC] =  r1.nextInt(7);
    	fieldCase = new int[terrain.getX()][terrain.getY()];
		for (int i = 1; i < fieldCase.length; i++){ 		
			for (int j = 1; j < fieldCase[i].length; j++){ 
				Random r1 = new Random();
				// est ce qu'on veut afficher fieldCase[xC][yC] ou bien field[i][j] ?
				fieldCase[i][j] = r1.nextInt(8);
				System.out.print(fieldCase[i][j]+" ");
			}
			System.out.println("");


			}
		}

		

		Joueur getJoueur(){
			return joueur;
		}

		void tabRandomStocke(){
			placementCase(); 
			getUnite().mouvement();
			System.out.println("");// pour stocker les randoms de placementCase()
			int field[][] = new int[fieldCase.length][fieldCase.length];
			if (getUnite().getN() == 0){
				for (int i = 1; i < fieldCase.length; i++){
					for (int j = 1; j < fieldCase[i].length; j++){
						field[i][j] = fieldCase[i][j];
						System.out.print(fieldCase[i][j]+" ");
					}
					System.out.println("");

				}
			}
		}

		int[][]getFieldCase(){
			return fieldCase;
		}
        	
		Unite getUnite(){
			return unite;
		}


		Terrain getTerrain(){
    		return terrain;
    	}

		int getRandomType(){
			return randomType;
		}
		boolean getFranchissable(){
			return franchissable;
		}


    	void caseType(){ 

    		if (randomType < 6){ // les cases franchissables sont: 1 foret, 2 desert, 3 plaine, 4 neige,  5 eau 
    			// appeler ici avancer bien dans unite appele caseType dans le test d'avancement?
    			franchissable = true;
    			placementCase();


       		}
    		else{ 
    			// l'unite ne peut pas avancer si elle veut aller sur une case 6 montagne ou 7 volcan
    			franchissable = false;
    			System.out.println("Vous ne pouvez pas avancer car il y a un obstacle !");
    		}
    	}

    	void remplacement(){
    		fieldCase = new int [getUnite().getXbis()][getUnite().getYbis()];
    		getUnite().mouvement();
    		 
    	}



    }


    class Ressources{


    }





	


		


	/*class Cavalier extends Unite{
		@Override




	}*/