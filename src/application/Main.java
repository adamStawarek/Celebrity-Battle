package application;
	
import java.applet.Applet;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Main.Bullet;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application implements Initializable{
	
	BorderPane root;	
	Player2 player,player2,player3;
	int WIDTH=1150,HEIGHT=700;
	public boolean isPress=false,isPress2=false,isStop=false,IsEnd=false,SuperAttack=false,IsMultiPlayer=false,connection=false;
	List<Bullet> bullets = new ArrayList<>();
    List<Bullet> bullets2 = new ArrayList<>();
	private List<Point2D> points = new ArrayList<>();
	private List<Player2> players = new ArrayList<>();
	public java.applet.AudioClip audioClip,audioClip2,audioClip3,audioClip4,audioClip5;
	public int n=1,k=0,t=0,r=0,timeToDisplayFinalWindow=0,attackloader=0,ComboTimeCounter=600;
	public static int score1=0,score2=0,MAXSCORE=1200;
	public static int time;
	int currentCombo=100,currentCombo2=100;//set for normal shooting
	public static Text txtSCORES;
	@FXML
	VBox VboxId;
	
	String clientSendMesg=" ",serverSendMesg=" ";
	public String host="localhost";
	public int port=22222;
	boolean serverFire=false,IsOnlyPlayer1=false,IsOnlyPlayer2=false,clientFire=false,serverbonus=false,serverbonusremove=false;
	public Text txtWaitingForClient;
	int bonusloader=0;
	
	Server server=null;
	Client client=null;

	
	ImageView bonusView, dangerView;
	public Stage stage;
	Circle c;
	int tick=0,player3Life=100, explosionImgCounter=0, explosionTimetoChangePic=0;
	double angle;
	AnimationTimer timer;	
	ImageView i;
	Image im;
	
	public static boolean IsHardMode=false,IsExplosion1=false, IsExplosion2=false, isOnline=false, isServer=false;
	
	Random randCombo;	
	public static String res="/resources2";
	
	int timeWithoutBonus = 0,timeWithDanger=0;
	int timeWithBonus=0,timeWithoutDanger=0;	
	public static double pl1bonus=0;
	public static double pl2bonus=0;
	
	double p1,p2;
	java.text.DecimalFormat df=new java.text.DecimalFormat(); 
	NumberFormat nf;
			
	@FXML
	ProgressBar pbPlayer1,pbPlayer2;
	
	@FXML
	Text txtTime,pl1life,pl2life,txtScore;
	
	@FXML
	ImageView pl1Img,pl2Img;
	
	@FXML
	Image i1,i2;
			
	
	@Override
	public void start(Stage primaryStage) {
		try {			
			root=new BorderPane();
		    root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,WIDTH,HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setTitle("GAME");
			primaryStage.setScene(scene);
			
	        
	       
	        score1=0;
	        score2=0;
	        txtSCORES=new Text();
	        txtSCORES.setText("");
	        txtSCORES.setFill(Color.BLACK);
			txtSCORES.setFont(Font.font(STYLESHEET_CASPIAN, 60));
			txtSCORES.setLayoutX(WIDTH/3);
			txtSCORES.setLayoutY(300);
			root.getChildren().add(txtSCORES);
	        
			if(isServer) {
				txtWaitingForClient=new Text();
				txtWaitingForClient.setText("");
				txtWaitingForClient.setFill(Color.BLACK);
				txtWaitingForClient.setFont(Font.font(STYLESHEET_CASPIAN, 60));
				txtWaitingForClient.setLayoutX(100);
				txtWaitingForClient.setLayoutY(300);
				root.getChildren().add(txtWaitingForClient);
			}
			
	        //Formatowanie textu
	        df.setMaximumFractionDigits(2); 
			df.setMinimumFractionDigits(2); 
			nf = new DecimalFormat("#0.00");
			

	        URL url = getClass().getResource("/sounds/shoot.wav");
	        audioClip = Applet.newAudioClip(url);	        
	        URL url2 = getClass().getResource("/sounds/t3.wav");
	        audioClip2 = Applet.newAudioClip(url2);
	        URL url3 = getClass().getResource("/sounds/h3.wav");
	        audioClip3 = Applet.newAudioClip(url3);
	        URL url4 = getClass().getResource("/sounds/gameOver.wav");
	        audioClip4 = Applet.newAudioClip(url4);
	        URL url5 = getClass().getResource("/sounds/explosion.wav");
	        audioClip5 = Applet.newAudioClip(url5);
	        
	        randCombo=new Random();
	        
	        Image bonus = new Image(getClass().getResourceAsStream(res+"/bonus.gif"));
	        bonusView = new ImageView();	       
	        bonusView.setImage(bonus);
	        
	        Image danger = new Image(getClass().getResourceAsStream("/sounds/danger.gif"));
	        dangerView = new ImageView();    
	        dangerView.setImage(danger);
	        dangerView.setFitHeight(80);
        	dangerView.setFitWidth(90);
        	
        	
        	
	        
	        if (res=="/resources2") {
	        	bonusView.setFitHeight(100);
	        	bonusView.setFitWidth(140);
	        	root.setId("pane2");
	        }
	        else {
	        	root.setId("pane");
	        }
	        
	        //Do zrobienia na super hard mode(difficult)
	        /*if(IsHardMode) {
	        	player3=new Player2(res+"/Player3.png");
	        	player3.setVelocity(new Point2D(2,2));
	 	        addPlayerObject(player3, 0, 0);
	        	players.add(player3);
	        }*/
	                
	        //Dodawanie graczy
	        player=new Player2(res+"/Player1.png");
	        player.setVelocity(new Point2D(0,0));
	        addPlayerObject(player, 300, 300);
	        players.add(player);
	        player2 = new Player2(res+"/Player2.png");	       
	        player2.setVelocity(new Point2D(0, 0));
	        addPlayerObject(player2, 100, 100);	         
	        players.add(player2);
	        
	        
	        
	        timer = new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	            		            		            		            	
	                onUpdate();
	                tick++;	                
	                for(Player2 p:players) {
	                	check(p);
	                }
	                
	                updatePic();
	                
	                if(isServer==false) {
	                	addBonus();
	                }else {
	                	checkBonus();
	                }
	                
	                	
	                //Online
	                if(connection) {
	                	try {
	                		String info=player.getRotate()+";"+player.velocity.getX()+";"+player.velocity.getY()+";"+score1+";"+score2;
	                		
	                		client.sendEcho(info);
	                		
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                if(clientFire) {
	                	shoot3(player2, bullets2,4);
	                }
	                if(serverFire) {
	                	if(currentCombo2==100) {
		        			shoot2(player,bullets);
		        		}
		        		else if(currentCombo2==0) {
		        			shootCombo1(player, bullets);
		        		}
		        		else {
		        			shootCombo2(player, bullets);
		        		}	
	                	
	                	//shoot2(player, bullets);
	                	serverFire=false;
	                	
	                }
	                
	                
	                if (isStop==false&&IsEnd==false&&IsMultiPlayer==false) {
	                	 autoEnemy(player);
	                	// if(IsHardMode) {
	                		// autoEnemy(player3);
	                	// }
	                }
	                
	                if(IsExplosion1)
	                	explosionHandler(player,"/p1explosion" ,"Player1");
	                if(IsExplosion2)
	                	explosionHandler(player2,"/p2explosion" ,"Player2");
	                
	                removeBulletsOutOfBounds();
	                updateSuperAttack();	
	                
	               if (IsHardMode) {
	            		   if(IsDanger()==false&&timeWithoutDanger>100) {	            		
	   	            		Random r=new Random();
	   						int randNumb1=r.nextInt(WIDTH-330);
	   						int randNumb2=r.nextInt(HEIGHT-140);				        
	   				        dangerView.setTranslateX(randNumb1);
	   				        dangerView.setTranslateY(randNumb2);
	   				        int dist=GetDistanceUniversal(bonusView.getTranslateX()+30, bonusView.getTranslateY()+30, dangerView.getTranslateX()+100, dangerView.getTranslateY()+20);
	   				         if(dist>100) {
	   				        	 root.getChildren().add(dangerView);
	   				        	 timeWithoutDanger=0;
	   				         }
	   	            	}
	   	            	
	   	            	if(IsDanger()==true&&timeWithDanger>900) {
	   	            		root.getChildren().remove(dangerView);
	   	            		timeWithDanger=0;            		
	   	            	}
	   	            	
	   	            	if(IsDanger()==true&&timeWithDanger<=900) {
	   	            		int dist1=GetDistanceUniversal(player.GetPosX()+30, player.GetPosY()+30, dangerView.getTranslateX()+100, dangerView.getTranslateY()+20);
	   						int dist2=GetDistanceUniversal(player2.GetPosX()+30, player2.GetPosY()+30, dangerView.getTranslateX()+100, dangerView.getTranslateY()+20);
	   						if(80>dist1) {
	   							//makeSound(audioClip3);
	   							root.getChildren().remove(dangerView);
	   							timeWithDanger=0;
	   							score2+=100;
	   							explosionImgCounter=1;
	   							explosionTimetoChangePic=0;
	   							IsExplosion1=true;
	   							

	   						}
	   						else if(80>dist2) {
	   							//makeSound(audioClip2);
	   							root.getChildren().remove(dangerView);
	   							timeWithoutDanger=0;
	   							score1+=100;
	   							explosionImgCounter=1;
	   							explosionTimetoChangePic=0;				
	   							IsExplosion2=true;
	   							
	   						}
	   	            	}	
	   	            	if(IsDanger()) {
	   	            		timeWithDanger++;
	   	            	}
	   	            	else {
	   	            		timeWithoutDanger++;
	   	            	}
	   	            	
	               }
	               
	            }

				private void addBonus() {
	            	
	            	if(IsBonus()==false&&timeWithoutBonus>100) {	            		
	            		Random r=new Random();
						int randNumb1=r.nextInt(WIDTH-330);
						int randNumb2=r.nextInt(HEIGHT-140);				        
				        bonusView.setTranslateX(randNumb1);
				        bonusView.setTranslateY(randNumb2);
				        root.getChildren().add(bonusView);
				        timeWithoutBonus=0;
				       if(connection) {
                			String info="bonus;"+bonusView.getTranslateX()+";"+bonusView.getTranslateY();
                			try {
								client.sendEcho(info);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                		}
                		
	            	}
	            	
	            	if(IsBonus()==true&&timeWithBonus>900) {
	            		root.getChildren().remove(bonusView);
	            		timeWithBonus=0; 
	            		 if(connection) {
	                			String info="bonus;remove";
	                			try {
									client.sendEcho(info);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                	}
	            	}
	            	
	            	if(IsBonus()==true&&timeWithBonus<=900) {
	            		int dist1=GetDistanceUniversal(player.GetPosX()+30, player.GetPosY()+30, bonusView.getTranslateX()+100, bonusView.getTranslateY()+20);
						int dist2=GetDistanceUniversal(player2.GetPosX()+30, player2.GetPosY()+30, bonusView.getTranslateX()+100, bonusView.getTranslateY()+20);
						if(80>dist1) {
							makeSound(audioClip3);
							root.getChildren().remove(bonusView);
							timeWithBonus=0;
							pl1bonus=1;
							currentCombo2=randCombo.nextInt(2);
							 if(connection) {
		                			String info="bonus;remove";
		                			try {
										client.sendEcho(info);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		                	}

						}
						else if(80>dist2) {
							makeSound(audioClip2);
							root.getChildren().remove(bonusView);
							timeWithBonus=0;
							pl2bonus=1;
							currentCombo=randCombo.nextInt(3);
							 if(connection) {
		                			String info="bonus;"+currentCombo;
								 //String info="bonus;remove";
		                			try {
										client.sendEcho(info);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		                	}
						}
	            	}	  
	            	
	            	if(IsBonus()) {
	            		timeWithBonus++;
	            	}
	            	else {
	            		timeWithoutBonus++;
	            	}
	            	
	            	if(pl1bonus>0)
	            		pl1bonus-=0.005;
	            	else
	            		currentCombo2=100;
	            	if(pl2bonus>0)
	            		pl2bonus-=0.005;
	            	else {
	            		currentCombo=100;
	            		if(connection) {
	            		try {	            			
							client.sendEcho("StopBonus");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
	            	}
				}

	            //resize the circle in combo3, and check if it hits opponent
				private void updateSuperAttack() {
					// TODO Auto-generated method stub
					if(SuperAttack==true) {
						if(GetDistance()-c.getRadius()>-100&&GetDistance()-c.getRadius()<100) {
							if(isServer==false) {
		                		score2++;
		                	}
							
						}
						System.out.println(GetDistance());
						attackloader+=10;
						c.setRadius(attackloader);
						if(attackloader>=300) {
							attackloader=0;
							SuperAttack=false;
							root.getChildren().remove(c);
						}
					}
					
						
				}


				
				private void removeBulletsOutOfBounds() {
					for(Object b:bullets) {
						if (b.GetPosX()>WIDTH-205||b.GetPosX()<0||b.GetPosY()<0||b.GetPosY()>HEIGHT) {							
							root.getChildren().remove(b.getView());							
						}
							
					}
					for(Object b:bullets2) {
						if (b.GetPosX()>WIDTH-205||b.GetPosX()<0||b.GetPosY()<0||b.GetPosY()>HEIGHT) {							
							root.getChildren().remove(b.getView());								
						}
				    }
					
				}


				//method to control enemy by the computer
				private void autoEnemy(Player2 player) {
															
					double X=player.GetPosX()-player2.GetPosX();
					double Y=player.GetPosY()-player2.GetPosY();
					double angleToShoot;
					double tan=Y/X;
					angle=(Math.toDegrees(Math.atan(tan))%360);
					double playerAngle=(player.getRotate()+90)%360;
					if (playerAngle<0)
						playerAngle*=-1;
					if(angle<0)
						angle*=-1;										
										
					if (player2.GetPosX()>player.GetPosX()) {					
						if (player2.GetPosY()>player.GetPosY()) {						
								angleToShoot=180-angle;
								autoEnemyHelper(player, angleToShoot,playerAngle);
						}
						else {																				
								angleToShoot=90-angle;			
								autoEnemyHelper(player, angleToShoot,playerAngle);
						}
						
					}
					else {
						if (player2.GetPosY()>player.GetPosY()) {							
							angleToShoot=270-angle;
							autoEnemyHelper(player, angleToShoot,playerAngle);
						}
						else {														
							angleToShoot=360-angle;
							autoEnemyHelper(player, angleToShoot,playerAngle);
						}
					}										
					
				}

				private void updatePic() {										
					if(score2>MAXSCORE) {						
						if (n>10) {
																					
							player.ChangeImg(res+"/Player1Lost.png");
							txtSCORES.setText("Player1 sucks!");
							IsEnd=true;							
							timeToDisplayFinalWindow++;
							if(timeToDisplayFinalWindow>240) {
								audioClip4.play();
								timer.stop();
								
								primaryStage.close();
								res2Contrloller r=new res2Contrloller();
								r.IsTrumpWin=false;	
								r.res=res;
								try {
									r.start(new Stage());
								} catch (Exception e) {									
									e.printStackTrace();
								}
								
								if (IsMultiPlayer==false) {
									SqlScores scr = null;
									if(MAXSCORE==1200)
										scr=new SqlScores("jdbc:sqlite:scores1.sqlite");
									else if(MAXSCORE==(3*1200))
										scr=new SqlScores("jdbc:sqlite:scores2.sqlite");	
									else if(MAXSCORE==(5*1200))
										scr=new SqlScores("jdbc:sqlite:scores3.sqlite");										
									List<ScoreObiect> scOb=scr.selectScores();
									scr.closeConnection();
							    
									int ScorePosition=0;
									for(ScoreObiect s: scOb) {
										if(s.score<(time/100)) {
											ScorePosition++;
										}
									}
									if(ScorePosition<3) {
										BSWcontrol bsw=new BSWcontrol();
										bsw.ScorePosition=ScorePosition;
										//bsw.score=score2;
										bsw.score=time/100;
										bsw.MaxScore=MAXSCORE;
										try {
											bsw.start(new Stage());
										} catch (Exception e) {
										// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}					
								}
							}
							}
						else {
							player.ChangeImg(res+"/p1explosion"+n+".png");							
							k++;
							if (k%3==0&&k<43)
								n++;
							if(k==3) {
								audioClip5.play();
							}
						}									
					}
					if(score1>MAXSCORE) {
						if (n>10) {
														
							player2.ChangeImg(res+"/Player2Lost.png");			
							txtSCORES.setText("Player2 sucks!");							
							IsEnd=true;							
							timeToDisplayFinalWindow++;
							if(timeToDisplayFinalWindow>240) {
								audioClip4.play();
								timer.stop();
								
								
								primaryStage.close();
								res2Contrloller r=new res2Contrloller();
								r.IsTrumpWin=true;	
								r.res=res;
								try {
									r.start(new Stage());
								} catch (Exception e) {									
									e.printStackTrace();
								}
							}
						}
						else {
							player2.ChangeImg(res+"/p2explosion"+n+".png");							
							k++;
							if (k%3==0&&k<43)
								n++;
							if(k==3) {
								audioClip5.play();
							}
						}
					}
				}

				//check if player avatar remains in the canvas
				private void check(Player2 player) {					
					if(player.GetPosX()<0) {
						player.imageView.setTranslateX(WIDTH-265);
					}
					if(player.GetPosX()>WIDTH-265) {
						player.imageView.setTranslateX(0);
					}
					if(player.GetPosY()<0) {
						player.imageView.setTranslateY(HEIGHT);
					}
					if(player.GetPosY()>HEIGHT) {
						player.imageView.setTranslateY(0);
					}
				}
								
				private void onUpdate() {
					for (Object bullet : bullets) {
			            
			                if (bullet.isColliding(player2)) {
			                	if(isServer==false) {
			                		score1++;
			                	}
			                    root.getChildren().removeAll(bullet.getView());
			                }
			               
			        }
					for (Object bullet : bullets2) {
			            
		                if (bullet.isColliding(player)) {
		                	if(isServer==false) {
		                		score2++;
		                	}	                	
		                    root.getChildren().removeAll(bullet.getView());
		                }
		               
		        }
					for(Player2 p:players) {
						p.update();
					}
					for(Bullet bullet:bullets) {
						bullet.update();
					}
					for(Bullet bullet:bullets2) {
						bullet.update();
					}
				}
	        };
	        
	        
	        primaryStage.getScene().setOnKeyPressed(e -> {
	        	if(IsOnlyPlayer2==false) {
	            if (e.getCode() == KeyCode.LEFT) {
	            	player.rotateLeft();	            		   
	            	isPress=true;
	            	
	            		
	            } else if (e.getCode() == KeyCode.RIGHT) {	            	
	            	player.rotateRight();	            		   
	            	isPress=true;
	            	
	            } 
	            else if (e.getCode() == KeyCode.UP) {
	            	if((player.velocity.getY()>-3)&&(player.velocity.getY()<3)&&(player.velocity.getX()>-3)&&(player.velocity.getX()<3)) {	            		
	            		player.speedUp();
	            	}    	            	
	            	isPress=true;
	            } 
	            else if (e.getCode() == KeyCode.DOWN) {            		
	            	player.slowDown();   
	            	isPress=true;
	            		 
	            }
	            else if (e.getCode() == KeyCode.N&&connection) {            		
	            	clientSendMesg="fire";
	            	try {
	            		String s="fire;"+currentCombo2;
						client.sendEcho(s);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        	}
	            
	            //handle(e);
	            if(IsOnlyPlayer1==false) {
	           if (e.getCode() == KeyCode.A) {            		
	            	player2.rotateLeft();	    	          
	            	isPress2=true;
	            	 //Test
	            	 if(e.isShiftDown()) {
	            		 shoot3(player2,bullets2,5);
	            	 }
	            	
	            } else if (e.getCode() == KeyCode.D) {
	            	player2.rotateRight();
	            	isPress2=true;
	            	 if(e.isShiftDown()) {
	            		 shoot3(player2,bullets2,5);
	            	 }
	            	 
	            } 
	            else if (e.getCode() == KeyCode.W) {
	            	if((player2.velocity.getY()>-3)&&(player2.velocity.getY()<3)&&(player2.velocity.getX()>-3)&&(player2.velocity.getX()<3)) {
	            		player2.speedUp();
	            	}     	 
	            	isPress2=true;
	            } 
	            else if (e.getCode() == KeyCode.S) {
	            	player2.slowDown();
	            	isPress2=true;	 
	            	 
	            }	
	            else if (e.getCode() == KeyCode.R) {
	            	 serverSendMesg="fire";
	            	 
	            }	
	            if (e.isShiftDown()) {
	            		shoot2(player2,bullets2);
	            		e.consume();
	            }
	            }
	  
	        });
	        
	        primaryStage.getScene().setOnKeyReleased(e -> {
	        	
	        	 if (e.getCode() == KeyCode.LEFT||e.getCode() == KeyCode.RIGHT||e.getCode() == KeyCode.N) {
	        		 clientSendMesg="";        		 
	        	 }
	        	 if (e.getCode() == KeyCode.A||e.getCode() == KeyCode.D||e.getCode() == KeyCode.R) {
	        		 serverSendMesg="";        		 
	        	 }
	        	if(IsOnlyPlayer1==false) {
	        	if (e.getCode() == KeyCode.R) {          	
	        		System.out.println(currentCombo);
	        		
	        	
	        		if(currentCombo==100) {
	        			shoot2(player2,bullets2);
	        		}
	        		else if(currentCombo==0) {
	        			shootCombo1(player2, bullets2);
	        		}
	        		else if(currentCombo==1) {
	        			shootCombo2(player2, bullets2);
	        		}
	        		else {
	        			shootCombo3(player2, bullets2);
	        		}
	        	 }
	        	}
	        	if(IsOnlyPlayer2==false) {
	        	 if (e.getCode() == KeyCode.N) {
	        		
	        		 if(currentCombo2==100) {
		        			shoot2(player,bullets);
		        		}
		        		else if(currentCombo2==0) {
		        			shootCombo1(player, bullets);
		        		}
		        		else {
		        			shootCombo2(player, bullets);
		        		}	 
	        	 }	     
	        	}
	        	 	//Pomocnicza funkcja(zamraza przeciwnika w czasie gry) do testowania przed oddaniem do usuniêcia	         
		           if (e.getCode() == KeyCode.DIGIT1) {
		            	if (isStop)
		            		isStop=false;
		            	else
		            		isStop=true;
		            	player.setVelocity(new Point2D(0,0));
		            	player2.setVelocity(new Point2D(0,0));
		              }
		           
	        	isPress2=isPress=false;           	
	        	
	        });
	        
	        
	        timer.start();		
			primaryStage.show();
			
			//ONLINE`
			if(isOnline) {
        		System.out.println("Online");
        		if(isServer) {
        			System.out.println("Server");
        			server=new Server(this,port);
        			server.start();
        			IsOnlyPlayer2=true;
        			txtWaitingForClient.setText("Waiting for another player...");
        		}
        		else {
        			System.out.println("Client");
        			client=new Client(this,host,port);
        			client.start();
        			client.sendEcho("Echo");
        			connection=true;
        			IsOnlyPlayer1=true;
        		}
        	}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    public void makeSound(java.applet.AudioClip a){
        a.play();
    }
	
    public void setMultiplayer(boolean b) {
    	IsMultiPlayer=b;
    }
   
	
	public void addObject(Object object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }
	
	private void addPlayerObject(Obiect_Player object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }
	
	public class Player2 extends Obiect_Player {
		
        Player2(String url) {
        	
            super(url);
            
        }
    }
	
	
	void explosionHandler(Player2 p, String s, String playerName) {
		p.ChangeImg(res+s+explosionImgCounter+".png");							
		explosionTimetoChangePic++;
		if (explosionTimetoChangePic%3==0 && explosionTimetoChangePic<36 && explosionImgCounter<8)
			explosionImgCounter++;
		if(explosionTimetoChangePic==3) {
			audioClip5.play();
		}
		if (explosionTimetoChangePic>=36) {
			p.ChangeImg(res+"/"+playerName+".png");
			if(playerName=="Player1");
				IsExplosion1=false;
			if(playerName=="Player2");
				IsExplosion2=false;
		}
	}
	void autoEnemyHelper(Player2 player,double angleToShoot,double playerAngle) {				
		double difference=playerAngle-angleToShoot;
		if(difference>=-5&&difference<=5)
			shoot(player,bullets);
		else if(difference<-5)
			player.rotateRight();
		else if(difference>5)
			player.rotateLeft();
	}
	
	
	int GetDistance() {
		return (int) Math.sqrt(Math.pow(player.GetPosY()-c.getCenterY(),2)+Math.pow(player.GetPosX()-c.getCenterX(),2));
	}
	
	int GetDistanceUniversal(double x1,double y1, double x2,double y2) {
		return (int) Math.sqrt(Math.pow(y1-y2,2)+Math.pow(x1-x2,2));
	}
	
	public void checkBonus() {
		
		if(serverbonus&&IsBonus()==false) {
			
			root.getChildren().add(bonusView);
			serverbonus=false;
			
		}
		if(serverbonusremove) {
			root.getChildren().remove(bonusView);
			serverbonusremove=false;
		}
		
    		  
	}
	
	public void shoot(Obiect_Player p,List<Bullet> b) {		
		t++;
		if(t%10==0) {
		makeSound(audioClip);
        Bullet bullet = new Bullet();
        bullet.setVelocity(p.getVelocity().normalize().multiply(5));
        b.add(bullet);
        addObject(bullet, p.getView().getTranslateX()+30, p.getView().getTranslateY()+30);
		}
	}
	public void shoot3(Obiect_Player p,List<Bullet> b, int k) {		
		t++;
		if(t%k==0) {
		makeSound(audioClip);
        Bullet bullet = new Bullet();
        bullet.setVelocity(p.getVelocity().normalize().multiply(5));
        b.add(bullet);
        addObject(bullet, p.getView().getTranslateX()+30, p.getView().getTranslateY()+30);
		}
	}
	
	public void shoot2(Obiect_Player p,List<Bullet> b) {		
		makeSound(audioClip);
        Bullet bullet = new Bullet();
        bullet.setVelocity(p.getVelocity().normalize().multiply(5));
        b.add(bullet);
        addObject(bullet, p.getView().getTranslateX()+30, p.getView().getTranslateY()+30);
		
	}
	
	public void shootCombo1(Obiect_Player p,List<Bullet> b) {		
		makeSound(audioClip2);
    	points.add(new Point2D(5,0));
    	points.add(new Point2D(-5,0));
    	points.add(new Point2D(0,5));
    	points.add(new Point2D(0,-5));
    	points.add(new Point2D(5,5));
    	points.add(new Point2D(-5,-5));
    	points.add(new Point2D(-5,5));
    	points.add(new Point2D(5,-5));
    	for(int i=0;i<8;i++) {
    		Bullet bullet = new Bullet();
    		bullet.setVelocity(points.get(i));	                
    		b.add(bullet);
    		addObject(bullet, p.getView().getTranslateX()+30, p.getView().getTranslateY()+30);
    	}		
	}
	
	public void shootCombo2(Obiect_Player p,List<Bullet> b) {
		
		makeSound(audioClip2);           	
		Bullet bullet = new Bullet(Color.BLUE);
		Bullet bullet2 = new Bullet(Color.YELLOW);
		Bullet bullet3 = new Bullet(Color.GREEN);
		bullet.setVelocity(p.getVelocity().normalize().multiply(5));
		bullet2.setVelocity(p.getVelocity().normalize().multiply(5));
		bullet3.setVelocity(p.getVelocity().normalize().multiply(5));
		
		b.add(bullet);
		b.add(bullet2);
		b.add(bullet3);
		addObject(bullet, p.getView().getTranslateX()+30, p.getView().getTranslateY()+20);
		addObject(bullet2, p.getView().getTranslateX()+30, p.getView().getTranslateY()+40);
		addObject(bullet3, p.getView().getTranslateX()+30, p.getView().getTranslateY()+60);
		
	}
	
	public void shootCombo3(Obiect_Player p,List<Bullet> b) {	
		if(SuperAttack==false) {
    		SuperAttack=true;
    		c=new Circle();
        	c.setCenterX(p.GetPosX()+40);
        	c.setCenterY(p.GetPosY()+40);
        	c.setRadius(attackloader);
        	c.setStroke(Color.BLACK);
        	c.setFill(null);
        	c.setStrokeWidth(5);	            	
        	root.getChildren().add(c);
    	}	
	}
		
	public class Bullet extends Object{
		public Bullet(){
			super(new Circle(5,5,5,Color.RED));			
		}
		public Bullet(Color color){
			super(new Circle(5,5,5,color));			
		}
	}
	
	public void setSmall() {
		WIDTH/=1.2;
		HEIGHT/=1.2;
	}
	
	public void setLarge() {
		WIDTH*=1.1;
		HEIGHT*=1.1;
	}
	
	public void setMedium() {
		WIDTH=1150;
		HEIGHT=700;
	}
	
	
	
	public void setScenario2() {		
		res="/resources2";
	}
	public void setScenario1() {
		res="/resources";		
	}
	public void setHardModeOn() {
		IsHardMode=true;
	}
	public void setHardModeOff() {
		IsHardMode=false;
	}
	
	private boolean IsBonus() {
		return root.getChildren().contains(bonusView);
	}
	private boolean IsDanger() {
		return root.getChildren().contains(dangerView);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	protected void handleStartAction() {		 
		
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(10),
                  new EventHandler() {
					@Override
					public void handle(Event event) {
						time++;
						pbPlayer1.setProgress(pl2bonus);
						pbPlayer2.setProgress(pl1bonus);
						txtTime.setText("Time: "+time/100);
						pl1life.setText("Life: "+(MAXSCORE-score1)/12);
						pl2life.setText("Life: "+(MAXSCORE-score2)/12);
						txtScore.setText("Score: "+score2/12+" : "+score1/12);
					}                	
                }));
        timeline.playFromStart();
	   }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		VboxId.setId("dark-scene");
		i1 = new Image(getClass().getResourceAsStream(res+"/Player2.png"));
        pl1Img.setImage(i1);
        i2 = new Image(getClass().getResourceAsStream(res+"/Player1.png"));
        pl2Img.setImage(i2);
		time=0;
		handleStartAction();		
		 
	}
	
}
