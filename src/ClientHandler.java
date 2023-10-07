import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    //declare variables
    Socket clientSocket;
    int clientId;
    Database db;
    //Constructor
    public ClientHandler (Socket socket, int clientId, Database db) {
        clientSocket = socket;
        this.clientId = clientId;
        this.db = db;
        //complete the constructor
    }

    public void run() {
        /*System.out.println("ClientHandler started...");
              Create I/O streams to read/write data, PrintWriter and BufferedReader
              Receive messages from the client and send replies, until the user types "stop"
                  System.out.println("Client sent the artist name " + clientMessage);
                  Request the number of titles from the db
                  Send reply to Client:
                  outToClient.println("Number of titles: " + titlesNum + " records found");

              System.out.println("Client " + clientId + " has disconnected");
              outToClient.println("Connection closed, Goodbye!");
              Close I/O streams and socket*/

        try {
            System.out.println("ClientHandler started...");

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            String clientMessage;
            int titlesNum;

            while (!(clientMessage = inFromClient.readLine()).toLowerCase().equals("stop")) {
                System.out.println("Client sent the artist name " + clientMessage);
                titlesNum = db.getTitles(clientMessage);
                outToClient.println("Number of titles: " + titlesNum + " records found");
            }
            outToClient.println("Connection closed, Goodbye!");
            inFromClient.close();
            outToClient.close();
            clientSocket.close();
            System.out.println("Client " + clientId + " has disconnected");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
