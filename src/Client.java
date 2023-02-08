import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

public class Client {
    public static void main(String[] args) throws IOException {
        final int portNumber = 8081;
        String hostName = "127.0.0.1"; // localhost
        DatagramSocket socket = new DatagramSocket();
        // CREATE A DATAGRAM PACKET AND SEND IT FROM SOCKET
        byte [] message;
        String input = "";
        while (!input.equals(Messages.EXIT)){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(Messages.HIT_ME);
            input = br.readLine();
            message = input.getBytes(Charset.defaultCharset());
            DatagramPacket sendPacket = new DatagramPacket(message, message.length, InetAddress.getByName(hostName),portNumber);
            socket.send(sendPacket);
            System.out.println(Messages.MESSAGE_SENT);


            //Recieve a packet from a server
            byte[] receiveData = new byte[1024];
            DatagramPacket recievedPacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(recievedPacket);
            String recievedString = new String(recievedPacket.getData(),0,recievedPacket.getLength());
            System.out.println(recievedString);
        }


    }
}
