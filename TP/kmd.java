import java.util.*;

class Date {
    private int day;
    private int month;
    private int year;
    
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    public int getDay() {
        return day;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getYear() {
        return year;
    }
    
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }
    
    public boolean estComprisEntre(Date d1, Date d2) {
        if (this.year < d1.year || this.year > d2.year)
            return false;
        else if (this.year > d1.year && this.year < d2.year)
            return true;
        else if (this.year == d1.year && this.year == d2.year) {
            if (this.month < d1.month || this.month > d2.month)
                return false;
            else if (this.month > d1.month && this.month < d2.month)
                return true;
            else if (this.month == d1.month && this.month == d2.month)
                return this.day >= d1.day && this.day <= d2.day;
            else if (this.month == d1.month)
                return this.day >= d1.day;
            else
                return this.day <= d2.day;
        } else if (this.year == d1.year) {
            if (this.month < d1.month)
                return false;
            else if (this.month == d1.month)
                return this.day >= d1.day;
            else
                return true;
        } else { // this.year == d2.year
            if (this.month > d2.month)
                return false;
            else if (this.month == d2.month)
                return this.day <= d2.day;
            else
                return true;
        }
    }
}

class Adherent {
    private Date dateNaissance;
    private String nom;
    private int score;
    
    public Adherent(Date dateNaissance, String nom, int score) {
        this.dateNaissance = dateNaissance;
        this.nom = nom;
        this.score = score;
    }
    
    public String toString() {
        return nom + " (" + dateNaissance.toString() + ") score: " + score + " points";
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println(adherent.toString());
        
        System.out.println(date1.estComprisEntre(date2, date3));

        Section sectionTennis = new Section("Tennis");
        
        Date date1 = new Date(12, 9, 2002);
        Adherent adherent1 = new Adherent(date1, "Alice", 93);
        sectionTennis.ajouterAdherent(adherent1);
        
        Date date2 = new Date(22, 2, 2003);
        Adherent adherent2 = new Adherent(date2, "Charlie", 75);
        sectionTennis.ajouterAdherent(adherent2);
        
        Date date3 = new Date(13, 3, 2003);
        Adherent adherent3 = new Adherent(date3, "Bob", 87);
        sectionTennis.ajouterAdherent(adherent3);
        
        Date date4 = new Date(24, 9, 2003);
        Adherent adherent4 = new Adherent(date4, "Esteban", 96);
        sectionTennis.ajouterAdherent(adherent4);
        
        Date date5 = new Date(10, 1, 2004);
        Adherent adherent5 = new Adherent(date5, "Danny", 78);
        sectionTennis.ajouterAdherent(adherent5);
        
        sectionTennis.afficher();
        
        int annee = 2003;
        Adherent meilleurScore = sectionTennis.meilleur;

    }
}




class Section {
    private String nomSection;
    private Adherent[] adherents;
    private int nombreAdherents;
    
    public Section(String nomSection) {
        this.nomSection = nomSection;
        this.adherents = new Adherent[100];
        this.nombreAdherents = 0;
    }
    
    public void afficher() {
        System.out.println("Section " + nomSection + ", nombre d'adhérents : " + nombreAdherents);
        for (int i = 0; i < nombreAdherents; i++) {
            System.out.println(adherents[i].toString());
        }
    }
    
    public void ajouterAdherent(Adherent a) {
        if (nombreAdherents == adherents.length) {
            System.out.println("La section est pleine, impossible d'ajouter un nouvel adhérent.");
            return;
        }
        
        int index = 0;
        while (index < nombreAdherents && a.getDateNaissance().estComprisEntre(adherents[index].getDateNaissance(), adherents[index + 1].getDateNaissance())) {
            index++;
        }
        
        for (int i = nombreAdherents - 1; i >= index; i--) {
            adherents[i + 1] = adherents[i];
        }
        
        adherents[index] = a;
        nombreAdherents++;
    }
    
    public Adherent meilleurScoreAnnee(int annee) {
        Adherent meilleurScore = null;
        
        for (int i = 0; i < nombreAdherents; i++) {
            if (adherents[i].getDateNaissance().getYear() == annee) {
                if (meilleurScore == null || adherents[i].getScore() > meilleurScore.getScore()) {
                    meilleurScore = adherents[i];
                }
            }
        }
        
        return meilleurScore;
    }
}






