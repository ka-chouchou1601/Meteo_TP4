package fr.univ_smb.iae.tp4.kanyongc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.net.Socket;

import java.net.ServerSocket;
import java.util.ArrayList;
import fr.univ_smb.iae.tp4.kanyongc.bulletins.BulletinMeteo;
import fr.univ_smb.iae.tp4.kanyongc.bulletins.Bulletin;
import fr.univ_smb.iae.tp4.kanyongc.bulletins.BulletinAvalanche;
import fr.univ_smb.iae.tp4.kanyongc.bulletins.EnsembleDeBulletinsMeteo;
import java.util.Scanner;
//import fr.univ_smb.iae.tp4.kanyongc.ClientMeteo;

public class ServeurMeteo {
//Question 1 associé  
    //private ArrayList<BulletinMeteo> bulletinsMeteo;

    // Changez de ArrayList<BulletinMeteo> à ArrayList<Bulletin>
	 private EnsembleDeBulletinsMeteo bulletinsMeteo; // Use EnsembleDeBulletinsMeteo
    //private ArrayList<Bulletin> bulletinsMeteo; // List can hold both BulletinMeteo and BulletinAvalanche
    private int port = 9090;
    private ServerSocket serveurSocket;
    

    // Constructeur qui génère un historique automatiquement
    // question 2 
//    public ServeurMeteo() {
//        this.bulletinsMeteo = BulletinMeteo.genererUnHistorique();
//    }
    // Constructeur : génère un historique automatiquement
    public ServeurMeteo() {
       // this.bulletinsMeteo = new ArrayList<>();
    	 this.bulletinsMeteo = new EnsembleDeBulletinsMeteo();
        // Ajout de quelques bulletins météo aléatoires
        this.bulletinsMeteo.addAll(BulletinMeteo.genererUnHistorique());
        
        for (int i = 0; i < 10; i++) { // Adjust the number as needed
            this.bulletinsMeteo.add(new BulletinAvalanche());
    }
    }

//    public ArrayList<Bulletin> getBulletinsMeteo() {
//        return this.bulletinsMeteo;
//    }
    //POur obtenir les bulletin meteo
    public EnsembleDeBulletinsMeteo getBulletinsMeteo() {
        return this.bulletinsMeteo;
    }

    public void setBulletinsMeteo(EnsembleDeBulletinsMeteo bulletinsMeteo) {
        this.bulletinsMeteo = bulletinsMeteo;
    }

    // Open the connection and use the class-level ServerSocket
    public void ouvrirConnexion() throws IOException {
        this.serveurSocket = new ServerSocket(this.port); // Assign to the class-level field
        System.out.println("Serveur prêt sur le port " + this.port);
    }

    
	// On utilise desormais les sockets pour avoir une application client-serveur
	
//Question 7 =>Nouveau bulletin aleatoire apres 5 demandes 
    public void donnerMeteo() throws IOException {
        int nbRequetesTraitees = 0;

        try {
            ouvrirConnexion(); // Ouvre la connexion du serveur

            while (true) {
                Socket socket = serveurSocket.accept(); // Accepte les connexions des clients

                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String request = in.readLine();
                    nbRequetesTraitees++; // Incrémentation du compteur de requêtes

                    // Si la requête est "getWeather", envoie les bulletins météo au client
                    if (request != null && request.equalsIgnoreCase("getWeather")) {
                    	EnsembleDeBulletinsMeteo bulletins = this.getBulletinsMeteo();
                        StringBuilder response = new StringBuilder();

                        for (Bulletin bulletin : bulletins) {
                            response.append(bulletin.toString()).append("\n");
                            response.append("Interprétation : ").append(bulletin.interpreter()).append("\n");
                        }
                        
                       
                        out.println(response.toString());
                    }

                    // Toutes les 5 requêtes, affiche l'historique et ajoute un nouveau bulletin
                    if (nbRequetesTraitees % 5 == 0) {
                        System.out.println("===== Historique des bulletins après " + nbRequetesTraitees + " requêtes =====");
                        ServeurMeteo.afficherBulletins(this.getBulletinsMeteo());

                        BulletinMeteo nouveauBulletin = new BulletinMeteo();
                        this.bulletinsMeteo.add(nouveauBulletin);
                        System.out.println("Nouveau bulletin ajouté : " + nouveauBulletin.toString());
                    }

                } catch (IOException e) {
                    System.err.println("Erreur lors de la gestion de la connexion : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ouverture du serveur : " + e.getMessage());
        } finally {
            // Ferme la socket lorsque le serveur s'arrête
            if (serveurSocket != null && !serveurSocket.isClosed()) {
                serveurSocket.close();
            }
        }
    }




//    // Méthode pour afficher tous les bulletins (bulletins météo et bulletins d'avalanche)
//    public static void afficherBulletins(EnsembleDeBulletinsMeteo bulletinsMeteo ) { // Using EnsembleDeBulletinsMeteo
//        System.out.println("===== Affichage des bulletins =====\n");
//        for (Bulletin bulletin : bulletinsMeteo) { // Using EnsembleDeBulletinsMeteo
//            System.out.println(bulletin.toString()); // Assumes toString is overridden in Bulletin and its subclasses
//            bulletin.interpreter(); // Call the interpreter method for each bulletin
//        }
//    }
    
// // Méthode pour afficher tous les bulletins (bulletins météo et bulletins d'avalanche)
//    public static void afficherBulletins(ArrayList<Bulletin> bulletins) {
//        System.out.println("===== Affichage des bulletins =====\n");
//        for (Bulletin bulletin : bulletins) {
//            System.out.println(bulletin.toString()); // Assumes toString is overridden in Bulletin and its subclasses
//            bulletin.interpreter(); // Call the interpreter method for each bulletin
//        }
//    }
    
    public static void afficherBulletins(EnsembleDeBulletinsMeteo bulletins) {
        System.out.println("===== Affichage des bulletins =====\n");
        for (Bulletin bulletin : bulletins) {
            System.out.println(bulletin.toString()); // Assumes toString is overridden in Bulletin and its subclasses
            bulletin.interpreter(); // Call the interpreter method for each bulletin
        }
    }

    //Question=>10 adapting the whole code to Bulletin being the superclass
 // Modification de la méthode rechercherBulletins()
    public  EnsembleDeBulletinsMeteo rechercherBulletins(String zoneG) {
        //ArrayList<Bulletin> bulletins = new ArrayList<>();  // Liste pour stocker les bulletins trouvés
    	EnsembleDeBulletinsMeteo bulletins = new EnsembleDeBulletinsMeteo();
    	for (Bulletin bulletin : this.getBulletinsMeteo()) {
            if (bulletin instanceof BulletinMeteo) {  // Vérifier si c'est un bulletin météo
                BulletinMeteo bulletinMeteo = (BulletinMeteo) bulletin;
                if (bulletinMeteo.getZone_geo().equals(zoneG)) {
                    bulletins.add((BulletinMeteo) bulletin); //// Cast explicite nécessaire pour ajouter à EnsembleDeBulletinsMeteo
                    // Ajouter à la liste des bulletins trouvés =>question15
                }
            }
        }
        return bulletins; // Return the list of found bulletins
    }
    //Question10
    //Nouvelle méthode pour rechercher et afficher les bulletins d'avalanche pour une zone spécifique
//    public void rechercherBulletinsAvalanche(String zoneAvalanche) {
//        ArrayList<Bulletin> bulletinsAvalanche = new ArrayList<>();
//
//        // Iterate through the bulletins to filter those that are instances of BulletinAvalanche
//        for (Bulletin bulletin : this.getBulletinsMeteo()) {
//            // Check if the bulletin is an instance of BulletinAvalanche
//            if (bulletin instanceof BulletinAvalanche) {
//                // Cast the bulletin to BulletinMeteo to access getZone_geo()
//                BulletinMeteo bulletinMeteo = (BulletinMeteo) bulletin;
//                if (bulletinMeteo.getZone_geo().equals(zoneAvalanche)) {
//                    bulletinsAvalanche.add(bulletin);
//                }
//            }
//        }
//
//        // Check if any avalanche bulletins were found and display them
//        if (bulletinsAvalanche.isEmpty()) {
//            System.out.println("Aucun bulletin d'avalanche trouvé pour la zone " + zoneAvalanche);
//        } else {
//            // Display the found avalanche bulletins
//            ServeurMeteo.afficherBulletins(new ArrayList<>(bulletinsAvalanche));
//        }
//    }
    
    public void rechercherBulletinsAvalanche(String zoneAvalanche) {
        // Utilisation d'EnsembleDeBulletinsMeteo à la place d'ArrayList
        EnsembleDeBulletinsMeteo bulletinsAvalanche = new EnsembleDeBulletinsMeteo();

        // Parcourir les bulletins pour filtrer ceux qui sont des instances de BulletinAvalanche
        for (Bulletin bulletin : this.getBulletinsMeteo()) {
            if (bulletin instanceof BulletinAvalanche) {
                BulletinMeteo bulletinMeteo = (BulletinMeteo) bulletin;
                if (bulletinMeteo.getZone_geo().equals(zoneAvalanche)) {
                    bulletinsAvalanche.add(bulletinMeteo);  // Ajouter le bulletin trouvé
                }
            }
        }

        // Vérifier s'il y a des bulletins d'avalanche trouvés et les afficher
        if (bulletinsAvalanche.isEmpty()) {
            System.out.println("Aucun bulletin d'avalanche trouvé pour la zone " + zoneAvalanche);
        } else {
            // Afficher les bulletins d'avalanche trouvés
            ServeurMeteo.afficherBulletins(bulletinsAvalanche);
        }
    }



    public static void main(String[] args) throws IOException {
        // Création du serveur et génération de l'historique automatiquement
        ServeurMeteo serveur = new ServeurMeteo();
       // serveur.ouvrirConnexion();
        serveur.donnerMeteo(); // Utilisez donnerMeteo pour gérer les connexions
        
        // Affiche tous les bulletins de l'historique
        System.out.println("Affichage de l'historique des bulletins météo :");
        ServeurMeteo.afficherBulletins(serveur.getBulletinsMeteo());
        
       
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
      
// Question 4
// Affiche uniquement les bulletins météo de la zone "Paris"
// System.out.println("\nBulletins météo trouvés pour la zone Paris :");
//ServeurMeteo.afficherBulletins(serveur.rechercherBulletins("Paris"));
         

