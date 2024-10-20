package fr.univ_smb.iae.tp4.kanyongc.bulletins;

import java.util.concurrent.ThreadLocalRandom;

//import java.util.Date;
// Creation of the class Bulletin Avalanche
//Answer proposition to the last prt of question 8 having extends BulletinMeteo instead of Bulletin
public class BulletinAvalanche extends BulletinMeteo {
    private int hauteurNeigeFraiche; // Hauteur de neige fraîche
    private int niveauRisque; // Niveau de risque, par défaut à 3
    
    
    // Tableau des hauteurs de neige possibles
    private static final int[] hauteursNeige = {0, 10, 20, 30, 50};
    // Tableau des niveaux de risque possibles
    private static final int[] niveauxRisque = {0, 1, 2, 3, 4, 5};

    //Constructor avalanche 
    public BulletinAvalanche(String avis, String zone_geo, int hauteurNeigeFraiche) {
        super();// Appel du constructeur de BulletinMeteo sans parametre avant il y avait un parametre 
        this.setZone_geo(zone_geo);
        // Sélectionner aléatoirement une hauteur de neige fraîche dans le tableau prédéfini
        this.hauteurNeigeFraiche = hauteursNeige[ThreadLocalRandom.current().nextInt(0, hauteursNeige.length)];
     // Sélectionner aléatoirement un niveau de risque dans le tableau prédéfini
        this.niveauRisque = niveauxRisque[ThreadLocalRandom.current().nextInt(0, niveauxRisque.length)];
       
         
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

