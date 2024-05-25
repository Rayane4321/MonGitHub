package Civilisation;

import java.util.Random;

public class Case{
		private int contenue;
    	private String type;
    	private Terrain terrain;
    	private int[][]field;
    	private int randomType;
    	private int xC, yC;
    	private int[][]fieldCase;
    	private boolean franchissable;
    	private Unite unite;
    	private int n = 0;
    	private Joueur joueur;



    	Case (int r){
    		contenue = 0;
    		randomType = r;



    	}


    	void setUnite(Unite unite){
    		if (this.unite == null){
    			this.unite = unite;

    		} 
    		
    		else{
    			System.out.println("pas d'unite");

    		}
    		
    	}


    	public static void main(String[] args){
    		Case c = new Case(2);
    		c.placementCase();


    	}
    	



    	public void placementCase(){ // on prend le terrain et on va attribuer a chaque case un nombre random qui va correspondre a son type (voire caseType)
		//fieldCase[xC][yC] =  r1.nextInt(7);
    	fieldCase = new int[10][10];
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

		public void tabRandomStocke(){
			placementCase(); 
			unite.mouvement();
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
