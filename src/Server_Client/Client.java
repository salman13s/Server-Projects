package Server_Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    //declaration section
    Socket socket = null;
    int userInput;
    String outputToServer;
    static int PORT_NUMBER = 5000;


    public Client(String ip, int port){

        try {

            while(true) {
                socket = new Socket(ip, port);

                //a scanner will be needed to get data from the socket
                Scanner scanner = new Scanner(socket.getInputStream());

                //"printing" the stream of the socket to the server
                PrintStream output = new PrintStream(socket.getOutputStream());
                System.out.println("you are connected");
                System.out.println("choose a number for the weather:  1 -> Leipzig, 2 -> Stuttgart, 3 -> Hamburg, 4 -> Erlangen, 5 -> Konstanz ");

                // a scanner, in order to be able to read from the console
                Scanner inputScanner = new Scanner(System.in);

                userInput = inputScanner.nextInt();

                // giving the input to the server
                output.println(userInput);

                outputToServer = scanner.next();

                System.out.println(outputToServer);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[]args){

        Client client = new Client("127.0.0.1",PORT_NUMBER);

    }


}
