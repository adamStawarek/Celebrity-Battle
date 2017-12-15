package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server extends Thread{
	private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[1024];
    private Main game;
    int Port;
 
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
             String received  = new String(packet.getData(), 0, packet.getLength());
             
            //mo¿na potem wyjebac(chyba)
            if (received.equals("end")) {
                running = false;
                continue;
            }
            else if (received.equals("left")) {
                game.player.rotateLeft();
            }
            else if (received.equals("right")) {
                game.player.rotateRight();
            }
            
            if(received.length()>1) {
            	System.out.println("Message read in Server:"+received);
            }
             
            //Wysy³anie pakietu
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String answer="Answer from server";
            if(game.serverSendMesg.equals("left"))
        		   answer="left";
            else if(game.serverSendMesg.equals("right"))
            	   answer="right";
            
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
