package fr.univ_smb.iae.tp4.kanyongc;

import java.io.IOException;
//import java.io.PrintWriter;
import java.net.Socket;
//import java.util.Scanner;

import java.net.ServerSocket;
import java.util.ArrayList;
import fr.univ_smb.iae.tp4.kanyongc.bulletins.BulletinMeteo;
//import fr.univ_smb.iae.tp4.kanyongc.ClientMeteo;

public class ServeurMeteo {
//Question 1 associé  
    private ArrayList<BulletinMeteo> bulletinsMeteo;
    private int port = 9090;
    private ServerSocket serveurSocket;

    // Constructeur qui génère un historique automatiquement
    // question 2 
    public ServeurMeteo() {
        this.bulletinsMeteo = BulletinMeteo.genererUnHistorique();
    }

    public ArrayList<BulletinMeteo> getBulletinsMeteo() {
        return this.bulletinsMeteo;
    }

    public void setBulletinsMeteo(ArrayList<BulletinMeteo> bulletinsMeteo) {
        this.bulletinsMeteo = bulletinsMeteo;
    }

    public void ouvrirConnexion() throws IOException {
        ServerSocket serveurSocket = new ServerSocket(this.port);
        System.out.println("Serveur prêt sur le port " + this.port);
    }
    
	// On utilise desormais les sockets pour avoir une application client-serveur
	
//Question 7 =>Nouveau bulletin aleatoire apres 5 demandes 
    public void donnerMeteo() throws IOException {
        // Initialisation d'un compteur pour suivre le nombre de requêtes traitées
        int nbRequetesTraitees = 0;
        ServerSocket serveurSocket = new ServerSocket(this.port);  // Ouvrir une connexion serveur
        
        while (true) {
            // Le serveur accepte une nouvelle connexion (requête client)
            Socket socket = serveurSocket.accept();  // Accepter une nouvelle requête client
            
            // Incrémentation du compteur à chaque nouvelle requête
            nbRequetesTraitees++;
            
            // Toutes les 5 requêtes (si le compteur est un multiple de 5)
            if (nbRequetesTraitees % 5 == 0) {
                // Afficher l'historique des bulletins météo
                System.out.println("===== Affichage de l'historique des bulletins après " + nbRequetesTraitees + " requêtes =====");
                ServeurMeteo.afficherBulletins(this.getBulletinsMeteo());
                
                // Générer et ajouter un nouveau bulletin météo aléatoire à l'historique
                BulletinMeteo nouveauBulletin = BulletinMeteo.randomBulletinMeteo();
                this.bulletinsMeteo.add(nouveauBulletin);
                System.out.println("Nouveau bulletin météo ajouté à l'historique : " + nouveauBulletin.toString());
            }

            // Ici, vous pouvez envoyer la météo au client (ce code existe probablement déjà)
            // Utiliser la méthode pour répondre au client avec les bulletins météo
            // ...
        }
    }

    //Question 4 Afficher meteo  
 // Méthode statique pour afficher tous les bulletins dans une collection donnée
    public static void afficherBulletins(ArrayList<BulletinMeteo> bulletins) {
        System.out.println("===== Affichage des bulletins meteo =====\n");
        for (BulletinMeteo bulletin : bulletins) {
            System.out.println(bulletin.toString()); // Utilisation de la méthode toString de BulletinMeteo
        }
    }
 // TP4 Elle permet de retourner TOUS les bulletins trouves
 // dans l'historique dont la zone geo correspond a celle
 // passee en parametre.
 public ArrayList<BulletinMeteo> rechercherBulletins(String zoneG) {
     ArrayList<BulletinMeteo> bulletins = new ArrayList<BulletinMeteo>();  // Liste pour stocker les bulletins trouvés
     for (BulletinMeteo bulletin : this.getBulletinsMeteo()) {  // Parcours des bulletins météo
         if (bulletin.getZone_geo().equals(zoneG)) {  // Vérification si la zone géographique correspond
             bulletins.add(bulletin);  // Si correspondance, ajout du bulletin à la liste
         }
     }
     return bulletins;  // Retourne la liste des bulletins trouvés
 }


    public static void main(String[] args) throws IOException {
        // Création du serveur et génération de l'historique automatiquement
        ServeurMeteo serveur = new ServeurMeteo();
        serveur.ouvrirConnexion();
        
        // Affiche tous les bulletins de l'historique
        System.out.println("Affichage de l'historique des bulletins météo :");
        ServeurMeteo.afficherBulletins(serveur.getBulletinsMeteo());
        
        //Question 3
        //Créer un scanner pour lire l'entrée de l'utilisateur
//        Scanner scanner = new Scanner(System.in);
//
//        // Demander à l'utilisateur d'entrer une zone géographique
//        System.out.print("\nEntrez la zone géographique à rechercher : ");
//        String zoneRecherchee = scanner.nextLine();  // Capture de l'entrée de l'utilisateur
//
//        // Recherche des bulletins pour la zone entrée par l'utilisateur
//        ArrayList<BulletinMeteo> bulletinsTrouves = serveur.rechercherBulletins(zoneRecherchee);
//
//        // Afficher les résultats de la recherche
//        if (bulletinsTrouves.isEmpty()) {
//            System.out.println("Aucun bulletin trouvé pour la zone " + zoneRecherchee);
//        } else {
//            System.out.println("\nBulletins météo trouvés pour la zone " + zoneRecherchee + " :");
//            ServeurMeteo.afficherBulletins(bulletinsTrouves);
//        }
//
//        scanner.close();  // Fermer le scanner
   
        //

       // Question 4
        // Affiche uniquement les bulletins météo de la zone "Paris"
        System.out.println("\nBulletins météo trouvés pour la zone Paris :");
        ServeurMeteo.afficherBulletins(serveur.rechercherBulletins("Paris"));

         
    }
}
