package fr.univ_smb.iae.tp4.kanyongc.bulletins;

import java.util.ArrayList;

import java.util.concurrent.ThreadLocalRandom;

public class BulletinMeteo extends Bulletin {

    // Attributs de la classe BulletinMeteo
   // private String avis; // Représente l'avis météo (ex : "Pluie", "Grand beau temps")=>transfered to bulletin
	private String zone_geo; // Représente la zone géographique concernée (ex : "Paris")
	   private static final int MAX_BULLETINS = 10; // Constante de classe
	 //Question 11: suppresion de la methode randomBulletinMeteo private 
    private static final String[] tempsQuilFait = {"Grand beau temps", "Pluie", "Quelques averses", "Brouillard givrant", "Vent fort", "Nuageux"};
    private static final String[] temperatures = {"Doux", "Chaud", "Froid", "De saison"};
    private static final String[] geoZones = {"Annecy", "Paris", "Lyon", "Chambery"};
    
    
 // Question 10 => Constructeur sans paramètre
    public BulletinMeteo() {
        super(tempsQuilFait[ThreadLocalRandom.current().nextInt(0, tempsQuilFait.length)] 
                + " - " + temperatures[ThreadLocalRandom.current().nextInt(0, temperatures.length)]);

        // Définition de la zone géographique aléatoire
        this.zone_geo = geoZones[ThreadLocalRandom.current().nextInt(0, geoZones.length)];
    }
  

    // Méthode setter pour définir la zone géographique
    public void setZone_geo(String zone) {
        this.zone_geo = zone;
    }

    // Méthode getter pour récupérer la zone géographique
    public String getZone_geo() {
        return this.zone_geo;
    }

    // Surcharge de la méthode toString pour personnaliser l'affichage d'un bulletin météo
    @Override
    public String toString() {
        return super.toString() + " (" + this.zone_geo + ")"; // Appel de toString() de Bulletin
    }
 
 // Question 6: Random bulletin meteo added to genererUnHistorique :Modification de la méthode genererUnHistorique() pour utiliser randomBulletinMeteo()
    public static ArrayList<BulletinMeteo> genererUnHistorique() {
        ArrayList<BulletinMeteo> bulletins = new ArrayList<>(); // Liste qui contiendra les bulletins générés

        // Boucle pour générer 10 bulletins météo en utilisant la constante MAX_BULLETINS
        for (int i = 0; i < MAX_BULLETINS; i++) {
           
            // Ajout du bulletin généré à la liste des bulletins
            bulletins.add(new BulletinMeteo());// Utilisation du constructeur sans paramètre afin de supprimer le randombulletinmeteo
        }

        // Retourne la liste des 10 bulletins météo générés
        return bulletins;
    }
}








    // Partie 1 : genererUnHistorique de bulletin meteo sans la methode randomBulletinMeteo :Méthode statique pour générer un historique de 10 bulletins météo aléatoires =>au hazard 
//    public static ArrayList<BulletinMeteo> genererUnHistorique() {
//        ArrayList<BulletinMeteo> bulletins = new ArrayList<>(); // Liste qui contiendra les bulletins générés
//
//        // Listes des possibilités d'avis météo, de températures et de zones géographiques
//        String[] tempsQuilFait = {"Grand beau temps", "Pluie", "Quelques averses", "Brouillard givrant", "Vent fort", "Nuageux"};
//        String[] temperatures = {"Doux", "Chaud", "Froid", "De saison"};
//        String[] geoZones = {"Annecy", "Paris", "Lyon", "Chambery"};
//        //Partie 1 : boucle pour generer les bulletin meteo 
//        //for (int i = 0; i < 10; i++) {
//
//        // Boucle pour générer 10 bulletins météo en utilisant la constante MAX_BULLETINS
//        for (int i = 0; i < MAX_BULLETINS; i++) {
//            // Génération aléatoire des indices pour sélectionner les avis météo, température et zone géographique
//            int randomTempsQuilFaitNum = ThreadLocalRandom.current().nextInt(0, tempsQuilFait.length);
//            int randomTemperaturesNum = ThreadLocalRandom.current().nextInt(0, temperatures.length);
//            int randomGeoZonesNum = ThreadLocalRandom.current().nextInt(0, geoZones.length);
//
//            // Création de l'avis météo combinant un type de temps et une température
//            String avis = tempsQuilFait[randomTempsQuilFaitNum] + " - " + temperatures[randomTemperaturesNum];
//
//            // Création d'un nouveau bulletin météo avec cet avis
//            BulletinMeteo bulletin = new BulletinMeteo(avis);
//
//            // Définition de la zone géographique du bulletin
//            bulletin.setZone_geo(geoZones[randomGeoZonesNum]);
//
//            // Ajout du bulletin à la liste des bulletins
//            bulletins.add(bulletin);
//        }
//
//        // Retourne la liste des 10 bulletins météo générés
//        return bulletins;
//    }
    // private String date_avis; // Date à laquelle l'avis a été généré
    //private static final int MAX_BULLETINS = 10; // Constante de classe
	 // Constructeur qui initialise l'avis météo et enregistre la date actuelle
    //public BulletinMeteo(String avis) {
        //super(avis); // Appel du constructeur de la classe mère
    //}
    //before adding on question 8 the super class Bulletin we had  this 
//    public BulletinMeteo(String avis) {
//        this.avis = avis;
//        this.date_avis = new java.util.Date().toString(); // Génère la date actuelle sous forme de chaîne de caractères
//    }

// Question 6: Ajout de la méthode randomBulletinMeteo() pour générer un bulletin aléatoire à la demande
//  public static BulletinMeteo randomBulletinMeteo() {
//      // Listes des possibilités d'avis météo, de températures et de zones géographiques
//      String[] tempsQuilFait = {"Grand beau temps", "Pluie", "Quelques averses", "Brouillard givrant", "Vent fort", "Nuageux"};
//      String[] temperatures = {"Doux", "Chaud", "Froid", "De saison"};
//      String[] geoZones = {"Annecy", "Paris", "Lyon", "Chambery"};
//
//      // Génération aléatoire des indices pour sélectionner les avis météo, température et zone géographique
//      int randomTempsQuilFaitNum = ThreadLocalRandom.current().nextInt(0, tempsQuilFait.length);
//      int randomTemperaturesNum = ThreadLocalRandom.current().nextInt(0, temperatures.length);
//      int randomGeoZonesNum = ThreadLocalRandom.current().nextInt(0, geoZones.length);
//
//      // Création de l'avis météo combinant un type de temps et une température
//      String avis = tempsQuilFait[randomTempsQuilFaitNum] + " - " + temperatures[randomTemperaturesNum];
//
//      // Création d'un nouveau bulletin météo avec cet avis
//      BulletinMeteo bulletin = new BulletinMeteo(avis);
//
//      // Définition de la zone géographique du bulletin
//      bulletin.setZone_geo(geoZones[randomGeoZonesNum]);
//
//      // Retourne le bulletin créé
//      return bulletin;  // Retourne un bulletin météo généré aléatoirement
//  }
//  

// Boucle pour générer 10 bulletins météo en utilisant la constante MAX_BULLETINS
//for (int i = 0; i < MAX_BULLETINS; i++) {
//    // Appel à la méthode randomBulletinMeteo() pour générer chaque bulletin
//    BulletinMeteo bulletin = randomBulletinMeteo();  // Utilisation de randomBulletinMeteo()
//    
//    // Ajout du bulletin généré à la liste des bulletins
//    bulletins.add(bulletin);
//}
//
//// Retourne la liste des 10 bulletins météo générés
//return bulletins;
//}

