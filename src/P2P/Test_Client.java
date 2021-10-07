package P2P;

public class Test_Client {


    public static void main(String[]args){

        // this class and Clientmanagment should run parallel, in order to send and receive messages


        System.out.println("please type the number 1 "); // to choose the client number 1 with th e port 50001

        Peer p2 = new Peer();

        p2.ExcReceiver(); // p2

        p2.ExcSender(50001); // p2

    }


}
