package Civilisation;


public class Joueur{
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
