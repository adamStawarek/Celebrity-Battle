package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javafx.application.Platform;

public class Server extends Thread{
	private DatagramSocket socket;
    boolean running;
    private byte[] buf = new byte[1024];
    private Main game;
    int Port;
    String received=null;
 
    public Server( Main game, int Port) throws SocketException {
        socket = new DatagramSocket(Port);
        this.game=game;
    }
    
    public void run() {
        running = true;
 
        while (running) {
        	//Odbieranie pakietu
            DatagramPacket packet  = new DatagramPacket(buf, buf.length);
            try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            //Pretwarzanie odebranej wiadomosci
             received  = new String(packet.getData(), 0, packet.getLength());
             
            //mo¿na potem wyjebac(chyba)
            if (received.equals("end")) {
                running = false;
                continue;
            }
            else if (received.equals("left")) {
                game.player.rotateLeft(3);
                game.serverFire=false;
            }
            else if (received.equals("right")) {
                game.player.rotateRight(3);
                game.serverFire=false;
            }
            else if (received.equals("fire")) {
               //game.shoottest(game.player); 
            	//game.player.shoot(game, game.player, game.bullets, game.t);
            	game.serverFire=true;
               System.out.println("Message read in Server:"+received);            	
            }
            else {
            	game.serverFire=false;
            }
            
             
            //Wysy³anie pakietu
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String answer="Answer from server";
            if(game.serverSendMesg.equals("left"))
        		   answer="left";
            else if(game.serverSendMesg.equals("right"))
            	   answer="right";
            else if(game.serverSendMesg.equals("fire"))
         	   answer="fire";
            
            packet = new DatagramPacket(answer.trim().getBytes(), answer.getBytes().length, address, port);
            
            try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        socket.close();
    }
}
