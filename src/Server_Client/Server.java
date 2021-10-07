package Server_Client;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
// multithreading: mit klasse, die von Thread zur Bearbeitung der Clients. Die dann jedem Client einen Thread zuordnet
public class Server {

    private Socket socket =null;
    private ServerSocket server = null;
    private HashMap<Integer, String> weatherInTheCities = new HashMap<>();
    private int inputFromUser;
    private String output;

    public void start() throws Exception{

        System.out.println("server is up, waiting for a client to connect...");
       //setting up the data
       weatherInTheCities.put(1 ,"Sonnig, 20 Grad");
       weatherInTheCities.put(2 ,"Sonnig, 23 Grad");
       weatherInTheCities.put(3 ,"Windig, 18 Grad");
       weatherInTheCities.put(4 ,"Regen, 19 Grad");
       weatherInTheCities.put(5 ,"Neblig, 24 Grad");

       //establishing a connection
       server = new ServerSocket(Client.PORT_NUMBER);

       while (true){

           //accepting the request
           socket = server.accept();

           //scanning the available data form the socket
           Scanner scanner = new Scanner(socket.getInputStream());

           //assigning the a number to our variable
           inputFromUser = scanner.nextInt() % weatherInTheCities.size();

           //getting the corresponding output
               output = weatherInTheCities.get(inputFromUser);


           //after we've got our output from the hash map, it will be printed out to the user
           PrintStream printStream = new PrintStream(socket.getOutputStream());

           printStream.println(output);
       }

    }


    public static void main(String[]args) throws Exception{

        Server server1 = new Server();
        server1.start();
    }


}
