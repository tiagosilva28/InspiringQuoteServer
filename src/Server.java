import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class Server {

    public static String generateQuote() throws IOException {
        FileReader quotes = new FileReader("resources/quotes.txt");
        BufferedReader quotesBuffer = new BufferedReader(quotes);
        ArrayList <String> lines = new ArrayList<>();
        String line;
        while((line = quotesBuffer.readLine()) !=null ){
            lines.add(line);
        }
        quotesBuffer.close();
        Random random = new Random();
        int randomIndex = random.nextInt(lines.size());
        return lines.get(randomIndex);
    }
    public static void main(String[] args) throws IOException {
        // write your code here
        // OPEN AN UDP SOCKET
        int portNumber = 8081;
        String hostName = "127.0.0.1"; // localhost
        DatagramSocket socket = new DatagramSocket(portNumber);


        // CREATE A DATAGRAM PACKET AND RECEIVE DATA FROM THE THE SOCKET
        byte[] recvBuffer = new byte[1024];

        while (socket.isBound()){
            DatagramPacket receivedPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
            System.out.println(Messages.WAITING_PACKAGE);
            socket.receive(receivedPacket); // BLOCKING METHOD

            String receivedString = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println("Received: " + receivedString);

            //Send a message to our Client
            InetAddress address = receivedPacket.getAddress();
            int port = receivedPacket.getPort();
            String response = "";
            if(receivedString.equals("hit me")){
                response = generateQuote();
            }
            else {
                response = Messages.UNSUPPORTED_MESSAGE;
            }
            byte [] responseBytes = response.getBytes(StandardCharsets.UTF_8);
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, address, port);
            socket.send(responsePacket);


        }

        // CLOSE THE SOCKET
        socket.close();
    }
}
