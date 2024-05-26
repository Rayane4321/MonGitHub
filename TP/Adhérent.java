
class Date{
	private int jour,mois,annee;

	public static void main(String[] args){

	}


	Date(int j, int m, int a){
		jour = j;
		mois = m;
		annee = a;

	}

	public String toString(){
		return jour + "/" + mois + "/" + annee;
	}

	int getDay(){
		return jour;
	}
	int getMonth(){
		return mois;
	}
	int getYear(){
		return annee;
	}


	public boolean estComprisEntre(Date d1, Date d2){
		// on teste d1 et d2pour savoir lequel est le max et le min
		int year1 = d1.getYear();
	    int month1 = d1.getMonth();
	    int day1 = d1.getDay();
	    
	    int year2 = d2.getYear();
	    int month2 = d2.getMonth();
	    int day2 = d2.getDay();
	    
	    int year = this.getYear();
	    int month = this.getMonth();
	    int day = this.getDay();
	    
	    if (year > year1 && year < year2) {
	        return true;
	    } else if (year == year1 && year == year2) {
	        if (month > month1 && month < month2) {
	            return true;
	        } else if (month == month1 && month == month2) {
	            return day >= day1 && day <= day2;
	        } else if (month == month1) {
	            return day >= day1;
	        } else if (month == month2) {
	            return day <= day2;
	        }
	    } else if (year == year1) {
	        if (month > month1) {
	            return true;
	        } else if (month == month1) {
	            return day >= day1;
	        }
	    } else if (year == year2) {
	        if (month < month2) {
	            return true;
	        } else if (month == month2) {
	            return day <= day2;
	        }
	    }
    
    	return false;
	}
		
}

class Adherent{
	private Date dateNaissance;
	private String nom;
	private int score;

	Adherent(Date dn, String n, int s){
		dateNaissance = dn;
		nom = n;
		score = s; 
	}

	Date getDateNaissance(){
		return dateNaissance;
	}

	int getScore(){
		return score;
	}
	String getNom(){
		return nom;
	}


	public String toString(){
		return nom + "("+ dateNaissance + ")" + "-" +"score:" + score + "points";
	}
}




class Section{

	private Adherent[] adherents;
	private String nom;
	private int nombreAdherent;

	Section(Adherent[] ad, String n, int nAd){
		nom = n;
		nombreAdherent = 0;
		adherents = new Adherent[100];
	}


	public void afficher() {
        System.out.println("Section " + nom + ", nombre d'adhérents : " + nombreAdherent);
        for (int i = 0; i < nombreAdherent; i++) {
            System.out.println(adherents[i].toString());
        }
    }

    public void ajouterAdherent(Adherent a){
    	// on teste d'abord si la section n'est pas pleine (pleine = 100)
    	if (nombreAdherent == adherents.length) { 
           	System.out.println("La section est pleine, impossible d'ajouter un nouvel adhérent.");
           	return;
           	// Le return sans valeur est utilisé ici pour sortir de la méthode sans retourner une valeur spécifique. 
           	// Il indique simplement que la méthode doit se terminer 
           	//à cet endroit et que le programme doit continuer son exécution après l'appel à ajouterAdherent().
        }

        int index = 0;
        while (index < nombreAdherent 
        	&& a.getDateNaissance().estComprisEntre(adherents[index    ].getDateNaissance(),
        											adherents[index + 1].getDateNaissance()  )) {
        	index++;
  			//la date de naissance de l'adhérent a est comprise entre la date de naissance de l'adhérent à l'index actuel (adherents[index]) 
        	//et la date de naissance de l'adhérent suivant à l'index suivant (adherents[index + 1]).
        }

        for (int i = nombreAdherent - 1; i >= index; i--) {
           	adherents[i + 1] = adherents[i];
        }
        
        adherents[index] = a;
        nombreAdherent++;
    }

    public Adherent meilleurScoreAnnee(int annee) {
        Adherent meilleurScore = null;

        for (int i = 0; i < nombreAdherent; i++) {
            if (adherents[i].getDateNaissance().getYear() == annee) {
                if (meilleurScore == null || adherents[i].getScore() > meilleurScore.getScore()) {
                    meilleurScore = adherents[i];
                }
            }
        }
        
        return meilleurScore;
    	
    }
	
}

   

class Main {
    public static void main(String[] args) {
    // Création d'une instance de Section
    Section sectionTennis = new Section("Tennis");

    // Création de quelques adhérents
    Adherent adherent1 = new Adherent(new Date(2002, 9, 12), "Alice", 93);
    Adherent adherent2 = new Adherent(new Date(2003, 2, 22), "Charlie", 75);
    Adherent adherent3 = new Adherent(new Date(2003, 3, 13), "Bob", 87);
    Adherent adherent4 = new Adherent(new Date(2003, 9, 24), "Esteban", 96);
    Adherent adherent5 = new Adherent(new Date(2004, 1, 10), "Danny", 78);

    // Ajout des adhérents à la section
    sectionTennis.ajouterAdherent(adherent1);
    sectionTennis.ajouterAdherent(adherent2);
    sectionTennis.ajouterAdherent(adherent3);
    sectionTennis.ajouterAdherent(adherent4);
    sectionTennis.ajouterAdherent(adherent5);

    // Affichage de la section
    sectionTennis.afficher();

    // Recherche de l'adhérent ayant le meilleur score en 2003
    Adherent meilleurScore = sectionTennis.meilleurScoreAnnee(2003);
    if (meilleurScore != null) {
        System.out.println("Meilleur score en 2003 : " + meilleurScore.toString());
    } 
    else {
        System.out.println("Aucun adhérent né en 2003.");
    }
}


}



