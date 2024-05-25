package Civilisation;


public class Dirigeant{
	//private Unite speciale;
	private String nom;
	public Civ civilisation;
	private Unite speciale;

	

	Dirigeant(String n, Civ c, Unite s){
		nom = n; 				// on prend le nom (n) du dirigeant en attribut et on dit que nom devient n, or dans n on a mis Simon bolivar par exemple
		civilisation = c;
		speciale = s;
	}

	String getNomDirigeant(){
		return nom;
	}

	Civ getCivilisation(){
		return civilisation;
	}

	Unite getUniteSpeciale(){
		return speciale;
	}

	public String toStringStatsUnite(){
		String txt ="Les stats de l'unite sont les suivantes: ";
		String pv  = "["+getUniteSpeciale().getPv() + "PV] " ;
		String atk = "["+getUniteSpeciale().getAtk()+"ATK] ";
		String def = "["+getUniteSpeciale().getDef()+"DEF] ";
		String dep = "["+getUniteSpeciale().getDeplacement()+"DEP] ";

		return txt + pv + atk + def + dep;
	}

	public String toStringPhraseDirigeant(){
		String txtDiri  = getNomDirigeant() + " dirige l'" + getCivilisation().getNomCiv()+" en "+getCivilisation().getPays()+". ";
		String txtUnite = " Son Unite speciale est: " + getUniteSpeciale().getNomUnite();
		return txtDiri + txtUnite;


	}



}

