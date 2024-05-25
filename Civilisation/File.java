package Civilisation;



public interface FileInt{
    public String toString();
    public boolean estVide();
    public boolean estRemplie();
    public void depiler();
    public void empiler();
    public void afficherFile();
}


class File implements FileInt {
    private int[] elements;
    private int max, fin, debut;

    File(int m) { // FIFO (First In, First Out) => Premier arrivé, premier sorti
        this.fin = -1;
        this.debut = -1;
        max = m;
        this.elements = new int[max];
    }

    public String toString(String message) {
        return "***Erreur*** " + message;
    }

    public boolean estVide() {
        return debut == -1;
    }

    public boolean estRemplie() {
        return fin == max - 1;
    }

    public void depiler() {
        if (estVide()) {
            System.out.println(toString("File vide"));
        } else {
            debut++;
            elements[debut] = 0;
            if (debut > fin) {
                debut = -1;
                fin = -1;
            }
        }
    }

    public void empiler(int val) {
        if (estRemplie()) {
            System.out.println(toString("File remplie"));
        } else {
            fin++;
            elements[fin] = val;
            if (debut == -1) {
                debut = 0;
            }
        }
    }

    public void afficherFile() {
        if (!estVide()) {
            for (int i = debut; i <= fin; i++) {
                System.out.print(elements[i] + " ");
            }
            System.out.println();
        } else {
            System.out.println(toString("File vide"));
        }
    }
}

class MainF {
    public static void main(String[] args) {

        File f1 = new File(10);

        f1.empiler(2);
        f1.empiler(0);
        f1.empiler(8);
        f1.empiler(1);
        f1.empiler(2);
        f1.empiler(1);
        f1.empiler(3);
        f1.empiler(3);
        f1.empiler(5);

        f1.depiler();
        f1.depiler();

        f1.afficherFile();

    }
}
