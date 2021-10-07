package P2P;


public class ClientManagment {


    public static void main(String args[]){

        // starting the clients, at first there is only two

        // this class and Test_Client should run parallel, in order to send and receive messages

        // this example works only at the time for the 2 specific clients, namely 0 and 1
        // if changes need to be made ports must also be changed.

        System.out.println("please type the number 0"); // picking the client number 0 with the port 50006

        Peer p = new Peer();



        p.ExcSender(50006); // p
        p.ExcReceiver();// p



    }



}
