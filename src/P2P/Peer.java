package P2P;

import java.net.*;
import java.util.*;

public class Peer extends Thread{

    // attributes
    private boolean running;
    private int actualPort;
    private DatagramSocket socket;
    private Runnable sender;
    private Runnable receiver;
    private byte[] in, out;
    private List<String>data = new LinkedList<>();

    public Runnable getSender() {
        return sender;
    } // Thread for the sender

    public Runnable getReceiver() {
        return receiver;
    } // Thread for the receiver

    /* Constructor */
    public Peer(){

       in = new byte[1024];
       out = new byte[1024];

        //System.out.println("choose a number to pick a client (0-2)");
       Scanner scnr = new Scanner(System.in);
       int client;

           client  = scnr.nextInt();

        // depends on the chosen client, a port number and data will be assigned to the client
           switch (client) {

               case 0:
                   this.actualPort = 50001;
                   data.add("Beatles - I Wanna Be Your Man");
                   data.add("Sportfreunde Stiller - Ein Kompliment");
                   break;

               case 1:

                   this.actualPort = 50006;
                   data.add("Beatles - All My Loving");
                   data.add("Rolling Stones - Satisfaction");
                   break;
               case 2:
                   this.actualPort = 50003;
                   data.add("Michael Jackson - Thriller");
                   data.add("Razorlight - Wire to Wire");
                   break;

               default:

                   System.out.println("please insert a valid number");

           }


   }
    /* end constructor */


    // a method for the sender
    public void ExcSender(int port){

        sender = new Runnable() {
            @Override
            public void run() {
                running = true;
                socket = null;
                Scanner scanner = new Scanner(System.in);


                try {
                    //initializing the socket
                    socket = new DatagramSocket();



                }catch (Exception e){
                    e.printStackTrace();
                }

                // setting up the address
                InetAddress address = null;
                try {
                    // setting the address to the local host
                    address = InetAddress.getLocalHost();

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }



                while (running){

                    // allowing the clients to write their input, that will be sent
                    String inp ="";
                    System.out.println("please select the data you want to send(by typing the number of the data):\n");


                    // displaying the available data
                    for (int i = 0; i < data.size(); i++){
                        System.out.println(i+". "+ data.get(i));

                    }

                    System.out.println("\n");

                    // scanning the user's input
                    int va = scanner.nextInt();

                    // returns the data the user wanted to send
                    if (va < data.size()){

                        inp = data.get(va);

                    }

                       //checks if the typed number is valid, i.e not greater than the existing numbers
                       while (va >= data.size()){
                           System.out.println("please insert a valid number!");
                           va = scanner.nextInt();
                       }

                    // sending the packet out using the out array
                    out = inp.getBytes();

                    DatagramPacket packet = new DatagramPacket(out,out.length,address,port);

                    try {
                        System.out.println("sent: " + inp);
                        socket.send(packet);
                        Thread.sleep(3000); // after sending, the user will be asked if he want to send something else

                        // allows the user to send other messages
                        System.out.println("\ndo you want to send something else ?(y/n)\n");


                        String s = scanner.nextLine();


                        //checks if the user wants to send something else or just to close the connection
                            while (true){

                                if (s.equals("y")){
                                    run();
                                }

                                // if one client terminates the connection
                                else if (s.equals("n")){
                                    System.out.println("bye...");
                                    scanner.close();
                                    System.exit(0);
                                    running = false;
                                    break;

                                }else {
                                    s = scanner.nextLine();
                                }
                            }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };


        new Thread(getSender()).start(); // thread starts
    }

    // a method for receiving that contains the receiver attribute
    public void ExcReceiver(){

        //start receiver
        receiver = new Runnable() {
            @Override
            public void run() {

                running = true;

                try {

                    // a new socket for the other client that receives
                    DatagramSocket socket = new DatagramSocket(actualPort);

                    while (true) {
                        // receiving the packet, specifying the buffer length and the packet size
                        DatagramPacket packet = new DatagramPacket(in, in.length);
                        socket.receive(packet); // the packet being received through the socket
                        int p = packet.getPort();
                        InetAddress ad = packet.getAddress();
                        packet = new DatagramPacket(in, in.length, ad, p);
                        String st = new String(conv(packet.getData()));//,0,packet.getLength());
                        //checks if the object already exists
                        if (data.contains(st)){
                            System.out.println("this object exists already!\n");

                        }else {
                            // when the object does not exist already, it will be added to the existing data
                            data.add(st);
                            System.out.println("received: " + st);

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //end receiver

        new Thread(getReceiver()).start(); // the receiver starts
    }


   // a helper function that converts a byte array into a string (helper method)
    public static StringBuilder conv(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;

        }
        return ret;
    }
}
