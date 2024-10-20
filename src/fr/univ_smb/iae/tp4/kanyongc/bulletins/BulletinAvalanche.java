package fr.univ_smb.iae.tp4.kanyongc.bulletins;

//import java.util.Date;
// Creation of the class Bulletin Avalanche
//Answer proposition to the last prt of question 8 having extends BulletinMeteo instead of Bulletin
public class BulletinAvalanche extends BulletinMeteo {
    private int hauteurNeigeFraiche; // Hauteur de neige fraîche
    private int niveauRisque; // Niveau de risque, par défaut à 3

    public BulletinAvalanche(String avis, String zone_geo, int hauteurNeigeFraiche) {
        super();// Appel du constructeur de BulletinMeteo 
        this.setZone_geo(zone_geo);
        this.hauteurNeigeFraiche = hauteurNeigeFraiche;
        this.niveauRisque = 3; // Valeur par défaut
    }

    public int getHauteurNeigeFraiche() {
        return hauteurNeigeFraiche;
    }

    public int getNiveauRisque() {
        return niveauRisque;
    }

    
    // Question 9 =>Redéfinition de la méthode toString() pour la classe BulletinAvalanche
    
    @Override
    public String toString() {
        // Appel à la méthode toString() de BulletinMeteo (qui elle-même fait appel à toString() de Bulletin)
        return super.toString() + 
               " - Neige fraîche : " + hauteurNeigeFraiche + " cm - Niveau de risque : " + niveauRisque;
    }
}

