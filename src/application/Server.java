package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.text.Text;

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
            else if (received.contains("fire")) {
            	game.serverFire=true;
            	String[] parts = received.split(";");
            	game.currentCombo2=Integer.parseInt(parts[1]);
            	System.out.println("Message read in Server:"+received);            	
            }
            else if (received.equals("Echo")) {
                game.txtWaitingForClient.setText("");
            }
            else if(received.contains("bonus")) {
            	
            	String[] parts = received.split(";");
            	if(parts.length==2) {
            		if(!parts[1].equals("remove"))
            			game.currentCombo=Integer.parseInt(parts[1]);
            		game.serverbonusremove=true;
            		
            	}
            	else {
            		//System.out.println("BONUS"+Double.parseDouble(parts[1])+";"+Double.parseDouble(parts[2]));           	
            		game.bonusView.setTranslateX(Double.parseDouble(parts[1]));
            		game.bonusView.setTranslateY(Double.parseDouble(parts[2]));
            		game.serverbonus=true;
            	}
            }
            else if(received.contains(";")) {
            	String[] parts = received.split(";");
            	game.player.SetRotate(Double.parseDouble(parts[0]));
            	game.player.setVelocity(new Point2D(Double.parseDouble(parts[1]),Double.parseDouble(parts[2])));
            	System.out.println((Integer.parseInt(parts[3]))/12+";"+(Integer.parseInt(parts[4]))/12);
            	game.score1=Integer.parseInt(parts[3]);
            	game.score2=Integer.parseInt(parts[4]);
            }
            else if(received.equals("StopBonus")) {
            	game.currentCombo=100;
            }
            else {
            	game.serverFire=false;
            }
            
             
            //Wysy³anie pakietu
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String answer="Answer from server";
            if(game.serverSendMesg.equals("fire")) {
            	answer="fire;"+game.currentCombo;
            }
            else
            	answer=game.player2.getRotate()+";"+game.player2.velocity.getX()+";"+game.player2.velocity.getY();
            
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
