interface File {
	public void push(int data);
	public int pop();
	public boolean estVide();
	public boolean estPleine();
}

class TestFile {
	public static void main(String[] args) {
		//File p2 = new FileTableau(5);
		File p2 = new FileListe();
		p2.push(36);
		System.out.println(p2);
		p2.push(13);
		p2.push(17);
		p2.push(15);
		System.out.println(p2);
		System.out.println("Pop : " + p2.pop());
		System.out.println(p2);
		System.out.println("Pop : " + p2.pop());
		System.out.println(p2);
	}
}

class FileListe implements File {
	private CelluleEntier liste;

	public void push(int data) {
		if (liste == null) {
			liste = new CelluleEntier(data, null);
		} else {
			liste.append(data);
		}
	}

	public int pop() {
		if (liste == null) {
			System.err.println("Erreur : file vide");
			System.exit(1);
			return 0;
		} else {
			int data = liste.get(0);
			liste = liste.remove();
			return data;
		}
	}

	public boolean estVide() {
		return (liste == null);
	}

	public boolean estPleine() {
		return false;
	}

	public String toString() {
		return liste.toString();
	}
}

class FileTableau implements File {
	private int[] tab;
	private int tete = 0;
	private int queue = 0;

	FileTableau(int taille) {
		tab = new int[taille];
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < tete; i++) {
			s = s + "# ";//tab[i] + " @ ";
		}
		for (int i = tete; i < queue; i++) {
			s = s + tab[i] + " < ";
		}
		for (int i = queue; i < tab.length; i++) {
			s = s + "- ";//tab[i] + " - ";
		}
		return s + "#";
	}

	public void push(int data) {
		if (queue == tab.length) {
			if (tete == 0) {
				System.err.println("FileTableau.push: erreur: file pleine");
				System.exit(1);
			} else {
				// Faire le décalage
				int index = 0;		
				for (int i = tete; i < queue; i++) {
					tab[index] = tab[i];
					index++;
				}
				tete = 0;
				queue = index;
			}
		}
		tab[queue] = data;
		queue = queue + 1;
	}

	public int pop() {
		// Détecter la file vide
		int data = tab[tete];
		tete = tete + 1;
		return data;
	}

	public boolean estVide() {
		return queue == 0;
	}

	public boolean estPleine() {
		return queue == tab.length;
	}	
}

interface Pile {
	public void push(int data);
	public int pop();
	public boolean estVide();
	public boolean estPleine();
}

class TestPile {
	public static void main(String[] args) {
		//Pile p2 = new PileTableau(12);
		Pile p2 = new PileListe();
		p2.push(36);
		System.out.println(p2);
		p2.push(13);
		p2.push(17);
		p2.push(15);
		System.out.println(p2);
		System.out.println("Pop : " + p2.pop());
		System.out.println(p2);
		System.out.println("Pop : " + p2.pop());
		System.out.println(p2);
	}
}

class PileListe implements Pile {
	private CelluleEntier liste;

	public void push(int data) {
		if (liste == null) {
			liste = new CelluleEntier(data, null);
		} else {
			liste = liste.add(data);
		}
	}

	public int pop() {
		if (liste == null) {
			System.err.println("Erreur : file vide");
			System.exit(1);
			return 0;
		} else {
			int data = liste.get(0);
			liste = liste.remove();
			return data;
		}
	}

	public boolean estVide() {
		return (liste == null);
	}

	public boolean estPleine() {
		return false;
	}

	public String toString() {
		return liste.toString();
	}
}

class PileTableau implements Pile {
	private int[] tab;
	private int sommet;

	PileTableau (int taille) {
		sommet = -1;
		tab = new int[taille];
	}

	public String toString() {
		String s = "#| ";
		for (int i = 0; i <= sommet; i++) {
			s = s + tab[i] + " | ";
		}
		for (int i = sommet + 1; i < tab.length; i++) {
			s = s + " | ";
		}
		return s;
	}

	public void push(int data) {
		if (sommet == tab.length - 1) {
			System.err.println("Pile.push: erreur: pile pleine");
			System.exit(1);
		}
		tab[sommet + 1] = data;
		sommet = sommet + 1;
	}

	public int pop() {
		if (sommet == -1) {
			System.err.println("Pile.pop: erreur: pile vide");
			System.exit(1);
		}
		int data = tab[sommet];
		sommet = sommet - 1;
		return data;
	}

	public boolean estVide() {
		return sommet == -1;
	}

	public boolean estPleine() {
		return sommet == tab.length - 1;
	}
}







