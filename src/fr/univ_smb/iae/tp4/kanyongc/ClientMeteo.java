package fr.univ_smb.iae.tp4.kanyongc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client qui va se connecter au serveur.
 */
public class ClientMeteo {

    private int port = 9090; // un attribut de type Entier (int) pour le numéro de port

    // Methode pour ouvrir une connexion (socket) avec le serveur
    public Socket ouvrirConnexion(String ip) throws IOException {
        Socket s = new Socket(ip, this.getPort()); // tentative de connexion au serveur
        return s;
    }

    public ClientMeteo(int p) {
        this.setPort(p);
    }

    // Methode pour demander la météo au serveur
    public String demanderMeteo(Socket socket) throws IOException {
        // Send a request to the server
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        output.println("getWeather"); // Send the request

        // Read the response from the server
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuilder messageRecu = new StringBuilder();
        String line;

        // Read until the end of the response
        while ((line = input.readLine()) != null) {
            messageRecu.append(line).append("\n");
        }
        return messageRecu.toString();
    }

    // Methode pour afficher sur la console Java le message recu du serveur
    public void afficherMeteo(String msg) {
        System.out.println("============== Meteo recue du serveur ===============\n");
        System.out.println(msg); // on affiche ce que retourne le serveur (le messageRecu)
        System.out.println("================ Fin de l'avis météo ================\n");
    }
    

    /**
     * Le client est une application Java.
     * Il demande à l'utilisateur de saisir l'adresse IP du serveur de météo
     * ou son nom (hostname).
     * Ensuite, il se connecte à ce serveur et affiche ce qui lui retourne le serveur.
     */
    
// // Méthode pour demander à l'utilisateur de saisir la zone d'avalanche
//    public String demanderZoneAvalanche() {
//        Scanner clavier = new Scanner(System.in);
//        try {
//            System.out.println("Saisir la zone d'avalanche : ");
//            return clavier.nextLine(); // Lire la ligne complète
//        } finally {
//            // clavier.close();  // Vous pourriez ne pas vouloir fermer le scanner ici pour ne pas fermer System.in
//        }
//    }

//    public static void main(String[] args) {
//        ClientMeteo client = new ClientMeteo(9090);
//        String adresseDuServeur = client.demanderSaisieIP();
//        try (Socket socket = client.ouvrirConnexion(adresseDuServeur)) {
//        	
//            
//        	client.afficherMeteo(client.demanderMeteo(socket));
//        } catch (IOException e) {
//            System.err.println("Erreur de connexion au serveur : " + e.getMessage());
//        }
//        System.exit(0);
//    }
    public static void main(String[] args) {
        ClientMeteo client = new ClientMeteo(9090);
        String adresseDuServeur = client.demanderSaisieIP();

        // Boucle qui relance la connexion 5 fois
        for (int i = 0; i < 5; i++) {
            try (Socket socket = client.ouvrirConnexion(adresseDuServeur)) {
                // Demander la météo et afficher le résultat
                client.afficherMeteo(client.demanderMeteo(socket));
            } catch (IOException e) {
                System.err.println("Erreur de connexion au serveur : " + e.getMessage());
            }

            System.out.println("Reconnexion automatique... (" + (i + 1) + "/5)\n");
        }

        System.out.println("Toutes les tentatives de connexion ont été effectuées.");
        System.exit(0);
    }

    // Methode pour demander à l'utilisateur de saisir l'adresse IP
    // du serveur sous forme d'une chaîne de caractères
    public String demanderSaisieIP() {
        Scanner clavier = new Scanner(System.in);
        try {
            System.out.println("Saisir l'adresse IP du serveur météo (port 9090): ");
            return clavier.next();
        } finally {
            clavier.close();  // Ensure the scanner is closed
        }
    }
  


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        if (port >= 8500 && port <= 9500) { // Intervalle de valeur autorisée du port 
            this.port = port;
            System.out.println("Le port a ete modifie. Il faudrait relancer le client !\n");
        } else {
            System.out.println("Valeur non autorisée: le port n'a pas ete modifie.\n");
        }
    }
}
