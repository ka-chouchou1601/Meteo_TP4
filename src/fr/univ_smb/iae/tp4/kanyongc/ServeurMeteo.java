package fr.univ_smb.iae.tp4.kanyongc;

import java.io.IOException;


//import java.io.PrintWriter;
import java.net.Socket;

import java.net.ServerSocket;
import java.util.ArrayList;
import fr.univ_smb.iae.tp4.kanyongc.bulletins.BulletinMeteo;
import fr.univ_smb.iae.tp4.kanyongc.bulletins.Bulletin;
import fr.univ_smb.iae.tp4.kanyongc.bulletins.BulletinAvalanche;
import java.util.Scanner;
//import fr.univ_smb.iae.tp4.kanyongc.ClientMeteo;

public class ServeurMeteo {
//Question 1 associé  
    //private ArrayList<BulletinMeteo> bulletinsMeteo;

    // Changez de ArrayList<BulletinMeteo> à ArrayList<Bulletin>
    private ArrayList<Bulletin> bulletinsMeteo; // List can hold both BulletinMeteo and BulletinAvalanche
    private int port = 9090;
    private ServerSocket serveurSocket;

    // Constructeur qui génère un historique automatiquement
    // question 2 
//    public ServeurMeteo() {
//        this.bulletinsMeteo = BulletinMeteo.genererUnHistorique();
//    }
    // Constructeur : génère un historique automatiquement
    public ServeurMeteo() {
        this.bulletinsMeteo = new ArrayList<>();
        
        // Ajout de quelques bulletins météo aléatoires
        this.bulletinsMeteo.addAll(BulletinMeteo.genererUnHistorique());
        
        // Ajout de quelques bulletins d'avalanche aléatoires pour diversifier
        for (int i = 0; i < 2; i++) {  // Par exemple, on ajoute 2 bulletins d'avalanche
            this.bulletinsMeteo.add(new BulletinAvalanche("Avalanche modérée", "Chamonix", 50));
            this.bulletinsMeteo.add(new BulletinAvalanche("Avalanche forte", "Paris", 80));
            this.bulletinsMeteo.add(new BulletinAvalanche("Avalanche légère", "Grenoble", 30));
            this.bulletinsMeteo.add(new BulletinAvalanche("Avalanche modérée", "Annecy", 40));
            this.bulletinsMeteo.add(new BulletinAvalanche("Avalanche sévère", "Lyon", 90));
            this.bulletinsMeteo.add(new BulletinAvalanche("Avalanche légère", "Chambéry", 20));
        }
    }

    public ArrayList<Bulletin> getBulletinsMeteo() {
        return this.bulletinsMeteo;
    }

    public void setBulletinsMeteo(ArrayList<Bulletin> bulletinsMeteo) {
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
                
                
                BulletinMeteo nouveauBulletin = new BulletinMeteo();
               this.bulletinsMeteo.add(new BulletinMeteo());

                
                System.out.println("Nouveau bulletin météo ajouté à l'historique : " + nouveauBulletin.toString());
            }

            
        }
    }

   
    
 // Méthode pour afficher tous les bulletins (bulletins météo et bulletins d'avalanche)
    public static void afficherBulletins(ArrayList<Bulletin> bulletins) {
        System.out.println("===== Affichage des bulletins =====\n");
        for (Bulletin bulletin : bulletins) {
            System.out.println(bulletin.toString()); // Assumes toString is overridden in Bulletin and its subclasses
        }
    }
    //Question=>10 adapting the whole code to Bulletin being the superclass
 // Modification de la méthode rechercherBulletins()
    public ArrayList<Bulletin> rechercherBulletins(String zoneG) {
        ArrayList<Bulletin> bulletins = new ArrayList<>();  // Liste pour stocker les bulletins trouvés
        for (Bulletin bulletin : this.getBulletinsMeteo()) {
            if (bulletin instanceof BulletinMeteo) {  // Vérifier si c'est un bulletin météo
                BulletinMeteo bulletinMeteo = (BulletinMeteo) bulletin;
                if (bulletinMeteo.getZone_geo().equals(zoneG)) {
                    bulletins.add(bulletin); // Ajouter à la liste des bulletins trouvés
                }
            }
        }
        return bulletins; // Return the list of found bulletins
    }
    //Question10
    //Nouvelle méthode pour rechercher et afficher les bulletins d'avalanche pour une zone spécifique
    public void rechercherBulletinsAvalanche(String zoneAvalanche) {
        ArrayList<Bulletin> bulletinsAvalanche = new ArrayList<>();

        // Iterate through the bulletins to filter those that are instances of BulletinAvalanche
        for (Bulletin bulletin : this.getBulletinsMeteo()) {
            // Check if the bulletin is an instance of BulletinAvalanche
            if (bulletin instanceof BulletinAvalanche) {
                // Cast the bulletin to BulletinMeteo to access getZone_geo()
                BulletinMeteo bulletinMeteo = (BulletinMeteo) bulletin;
                if (bulletinMeteo.getZone_geo().equals(zoneAvalanche)) {
                    bulletinsAvalanche.add(bulletin);
                }
            }
        }

        // Check if any avalanche bulletins were found and display them
        if (bulletinsAvalanche.isEmpty()) {
            System.out.println("Aucun bulletin d'avalanche trouvé pour la zone " + zoneAvalanche);
        } else {
            // Display the found avalanche bulletins
            ServeurMeteo.afficherBulletins(new ArrayList<>(bulletinsAvalanche));
        }
    }

    public static void main(String[] args) throws IOException {
        // Création du serveur et génération de l'historique automatiquement
        ServeurMeteo serveur = new ServeurMeteo();
        serveur.ouvrirConnexion();
        
        // Affiche tous les bulletins de l'historique
        System.out.println("Affichage de l'historique des bulletins météo :");
        ServeurMeteo.afficherBulletins(serveur.getBulletinsMeteo());
        
        // Question 4
        // Affiche uniquement les bulletins météo de la zone "Paris"
        System.out.println("\nBulletins météo trouvés pour la zone Paris :");
        ServeurMeteo.afficherBulletins(serveur.rechercherBulletins("Paris"));
      //Question 12
        // Ajout de deux bulletins d'avalanche manuellement (en plus de l'historique)
        BulletinAvalanche bulletinAvalanche1 = new BulletinAvalanche("Risque élevé d'avalanche", "Val Thorens", 60);
        BulletinAvalanche bulletinAvalanche2 = new BulletinAvalanche("Risque modéré d'avalanche", "Courchevel", 30);
        
        // Ajout des bulletins d'avalanche dans l'historique
        serveur.getBulletinsMeteo().add(bulletinAvalanche1);
        serveur.getBulletinsMeteo().add(bulletinAvalanche2);

        // Utiliser un scanner pour obtenir la zone de l'utilisateur
        Scanner scanner = new Scanner(System.in); // Création d'un Scanner
        System.out.println("\nEntrez la zone pour la recherche des bulletins d'avalanche :");
        String zoneAvalanche = scanner.nextLine(); // Lecture de la zone entrée par l'utilisateur

        // Recherche des bulletins d'avalanche pour la zone spécifiée
        System.out.println("\nRecherche des bulletins d'avalanche pour la zone : " + zoneAvalanche);
        serveur.rechercherBulletinsAvalanche(zoneAvalanche);  // Appel de la méthode avec la zone entrée

        scanner.close(); // Fermer le scanner
    }
}
    



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
        //Question 4 Afficher meteo  
        // Méthode statique pour afficher tous les bulletins dans une collection donnée
//           public static void afficherBulletins(ArrayList<Bulletin> bulletins) {
//               System.out.println("===== Affichage des bulletins meteo =====\n");
//               for (BulletinMeteo bulletin : bulletins) {
//                   System.out.println(bulletin.toString()); // Utilisation de la méthode toString de BulletinMeteo
//               }
//           }
        // TP4 Elle permet de retourner TOUS les bulletins trouves
        // dans l'historique dont la zone geo correspond a celle
        // passee en parametre.
       // public ArrayList<BulletinMeteo> rechercherBulletins(String zoneG) {
//            ArrayList<BulletinMeteo> bulletins = new ArrayList<BulletinMeteo>();  // Liste pour stocker les bulletins trouvés
//            for (BulletinMeteo bulletin : this.getBulletinsMeteo()) {  // Parcours des bulletins météo
//                if (bulletin.getZone_geo().equals(zoneG)) {  // Vérification si la zone géographique correspond
//                    bulletins.add(bulletin);  // Si correspondance, ajout du bulletin à la liste
//                }
//            }
//            return bulletins;  // Retourne la liste des bulletins trouvés
       // }

//Générer et ajouter un nouveau bulletin météo aléatoire à l'historique
//methode main
//BulletinMeteo nouveauBulletin = BulletinMeteo.randomBulletinMeteo();
//this.bulletinsMeteo.add(nouveauBulletin);
      

         

