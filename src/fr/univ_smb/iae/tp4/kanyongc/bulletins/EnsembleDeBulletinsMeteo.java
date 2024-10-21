package fr.univ_smb.iae.tp4.kanyongc.bulletins;

import java.util.ArrayList;
import java.util.Iterator;
import fr.univ_smb.iae.tp4.kanyongc.bulletins.BulletinMeteo;

public class EnsembleDeBulletinsMeteo extends ArrayList<BulletinMeteo> {
	 private static final long serialVersionUID = 1L; 
    // Affiche tous les bulletins
    public void afficherBulletins() {
        System.out.println("===== Affichage des bulletins =====\n");
        for (BulletinMeteo bulletin : this) { // this est UNE collection d'objets
            System.out.println(bulletin.toString());
        }
    }

    // Recherche des bulletins par zone géographique
//    public EnsembleDeBulletinsMeteo rechercherBulletins(String zoneG) {
//        EnsembleDeBulletinsMeteo bulletins = new EnsembleDeBulletinsMeteo();
//        for (BulletinMeteo bulletin : this) {
//            if (bulletin.getZone_geo().equals(zoneG)) // Utiliser equals pour la comparaison de chaînes
//                bulletins.add(bulletin); // Ajoute le bulletin si la zone correspond
//        }
//        return bulletins;
//    }
    
 // Recherche des bulletins par zone géographique
    public ArrayList<Bulletin> rechercherBulletins(String zoneG) {
        ArrayList<Bulletin> bulletins = new ArrayList<>();  // Liste pour stocker les bulletins trouvés
        for (BulletinMeteo bulletin : this) { // Use `this` directly
            if (bulletin.getZone_geo().equals(zoneG)) {
                bulletins.add(bulletin); // Ajouter à la liste des bulletins trouvés
            }
        }
        return bulletins; // Return the list of found bulletins
    }

//    public ArrayList<Bulletin> rechercherBulletins(String zoneG) {
//        ArrayList<Bulletin> bulletins = new ArrayList<>();  // Liste pour stocker les bulletins trouvés
//        for (Bulletin bulletin : this.getBulletinsMeteo()) {
//            if (bulletin instanceof BulletinMeteo) {  // Vérifier si c'est un bulletin météo
//                BulletinMeteo bulletinMeteo = (BulletinMeteo) bulletin;
//                if (bulletinMeteo.getZone_geo().equals(zoneG)) {
//                    bulletins.add(bulletin); // Ajouter à la liste des bulletins trouvés =>question15
//                }
//            }
//        }
//        return bulletins; // Return the list of found bulletins
//    }
    // Recherche le premier bulletin trouvé pour une zone géographique donnée
    public BulletinMeteo rechercherBulletin(String zoneG) {
        BulletinMeteo trouve = null;
        int i = 0;
        while (trouve == null && i < this.size()) {
            trouve = this.get(i);
            if (!trouve.getZone_geo().equals(zoneG)) // Utiliser equals pour la comparaison de chaînes
                trouve = null;
            i++;
        }
        return trouve;
    }

    // Supprime tous les bulletins d'une zone donnée
    public void supprimerTousLesBulletins(String zone) {
        Iterator<BulletinMeteo> iter = this.iterator();
        while (iter.hasNext()) {
            BulletinMeteo bulletin = iter.next();
            if (bulletin.getZone_geo().equals(zone)) // Utiliser equals pour la comparaison de chaînes
                iter.remove();
        }
    }
}
