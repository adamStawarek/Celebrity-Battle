package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends Thread {
	
	private DatagramSocket socket;
    private InetAddress address;
    private byte[] buf;
    private byte[] buf2 = new byte[1024];
    private Main game;
    String ip;
    int port;
 
    public Client( Main game,String ip, int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName(ip);
        this.game = game;
        this.port=port;
    }
 
    public String sendEcho(String msg) throws IOException {
       //wysy³anie wiadomoœci
    	buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
        
        //Odbieranie wiadomoœci
        packet = new DatagramPacket(buf2, buf2.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        
        if (received.equals("left")) {
            game.player2.rotateLeft();
        }
        else if (received.equals("right")) {
            game.player2.rotateRight();
        }
        
        if(received.length()>1) {
        	System.out.println("Message read in Client:"+received);
        }
        
        return received;
    }
 
    public void close() {
        socket.close();
    }
}
