package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javafx.geometry.Point2D;

public class Client extends Thread {
	
	private DatagramSocket socket;
    private InetAddress address;
    private byte[] buf;
    private byte[] buf2 = new byte[1024];
    private Main game;
    String ip;
    int port;
    boolean canAcess=true;
 
    public Client( Main game,String ip, int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        socket.setSoTimeout(6000);
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
        //socket.receive(packet);
        try {
            socket.receive(packet);
           
        }
        catch (SocketTimeoutException e) {
            game.txtWaitingForClient.setText("Cannot acess the server :(");
        	
        	System.out.println("Server is dead");
        	canAcess=false;
        }
        
        String received = new String(packet.getData(), 0, packet.getLength());
        
        
        if (received.contains("fire")) {
        	String[] parts = received.split(";");
        	game.currentCombo=Integer.parseInt(parts[1]);
        	if(game.currentCombo==100) {
    			game.shoot3(game.player2,game.bullets2,4);
    		}
    		else if(game.currentCombo==0) {
    			game.shootCombo1(game.player2, game.bullets2);
    		}
    		else if(game.currentCombo==1) {
    			game.shootCombo2(game.player2, game.bullets2);
    		}
    		else {
    			game.shootCombo3(game.player2, game.bullets2);
    		}
        	System.out.println("Combo client: "+Integer.parseInt(parts[1]));
            //game.shoot3(game.player2, game.bullets2,4);
        }
        else if(received.contains(";")) {
        	String[] parts = received.split(";");
        	game.player2.SetRotate(Double.parseDouble(parts[0]));
        	game.player2.setVelocity(new Point2D(Double.parseDouble(parts[1]),Double.parseDouble(parts[2])));
        }
        
        return received;
    }
 
    public void close() {
        socket.close();
    }
}
