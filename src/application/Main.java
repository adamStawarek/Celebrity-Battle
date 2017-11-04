package application;
	
import java.applet.Applet;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import application.Main.Bullet;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application implements Initializable{
	
	BorderPane root;	
	Player2 player,player2;
	int WIDTH=1150,HEIGHT=700;
	public boolean isPress=false,isPress2=false,isStop=false,IsEnd=false,SuperAttack=false,IsMultiPlayer=false,EnableSuperComboPlayer1=false,EnableSuperComboPlayer2=false;
	private List<Bullet> bullets = new ArrayList<>();
	private List<Bullet> bullets2 = new ArrayList<>();
	private List<Point2D> points = new ArrayList<>();
	private java.applet.AudioClip audioClip,audioClip2,audioClip3;
	int score1=0,score2=0,n=1,k=0,t=0,r=0,timeToDisplayFinalWindow=0,attackloader=0,MAXSCORE=1200,ComboTimeCounter=600;
	public Stage stage;
	Circle c;
	int tick=0;
	double angle;
	AnimationTimer timer;	
	ImageView i;
	Image im;
	Text score;
	
			
	@Override
	public void start(Stage primaryStage) {
		try {
			root=new BorderPane();
		    root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
		    root.setId("pane");
			Scene scene = new Scene(root,WIDTH,HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setTitle("GAME");
			primaryStage.setScene(scene);
			
	        player2 = new Player2("/resources/trump.png");	       
	        player2.setVelocity(new Point2D(1, 1));
	        addPlayerObject(player2, 100, 100);
	        score=new Text(WIDTH/2, 50, "0:0");
	       

	        URL url = getClass().getResource("/sounds/shoot.wav");
	        audioClip = Applet.newAudioClip(url);	        
	        URL url2 = getClass().getResource("/sounds/t3.wav");
	        audioClip2 = Applet.newAudioClip(url2);
	        URL url3 = getClass().getResource("/sounds/h3.wav");
	        audioClip3 = Applet.newAudioClip(url3);
	        
	        Image bonus = new Image(getClass().getResourceAsStream("/resources/eagle.gif"));
	        ImageView bonusView = new ImageView();
	       // bonusView.setFitHeight(50);
	       // bonusView.setFitWidth(100);
	        bonusView.setImage(bonus);
	        
	        
	        score.setFont(Font.font(40));
	        root.getChildren().add(score);
	        player=new Player2("/resources/hilary.png");
	        player.setVelocity(new Point2D(-1,-1));
	        addPlayerObject(player, 300, 300);
	        	        
	        timer = new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	            		            		            		            	
	                onUpdate();
	                tick++;
	                check();
	                updatePic();
	                addBonus();
	                
	                if (isStop==false&&IsEnd==false&&IsMultiPlayer==false)
	                	 autoEnemy();
	                
	                removeBulletsOutOfBounds();
	                updateSuperAttack();
	               //delay();
	               
	            }
	            	
	            
	            private void addBonus() {
					// TODO Auto-generated method stub
					if(tick%400==0) {
						Random r=new Random();
						int randNumb1=r.nextInt(WIDTH-330);
						int randNumb2=r.nextInt(HEIGHT-140);
						
				        
				        bonusView.setTranslateX(randNumb1);
				        bonusView.setTranslateY(randNumb2);
				        root.getChildren().add(bonusView);
					}
					else if(tick%400>300) {
						if(root.getChildren().contains(bonusView))
							root.getChildren().remove(bonusView);
						EnableSuperComboPlayer1=false;
						EnableSuperComboPlayer2=false;
					}
					else {
						if(root.getChildren().contains(bonusView)) {
							int dist1=GetDistanceUniversal(player.GetPosX()+30, player.GetPosY()+30, bonusView.getTranslateX()+100, bonusView.getTranslateY()+20);
							int dist2=GetDistanceUniversal(player2.GetPosX()+30, player2.GetPosY()+30, bonusView.getTranslateX()+100, bonusView.getTranslateY()+20);
							if(80>dist1) {
								root.getChildren().remove(bonusView);
								System.out.println("Distance1:"+dist1+" Distance2:"+dist2);
								EnableSuperComboPlayer1=true;
							}
							else if(80>dist2) {
								root.getChildren().remove(bonusView);
								System.out.println("Distance1:"+dist1+" Distance2:"+dist2);
								EnableSuperComboPlayer2=true;
							}
							
							
						}
							
					}
						
				}


				private void updateSuperAttack() {
					// TODO Auto-generated method stub
					if(SuperAttack==true) {
						if(GetDistance()-c.getRadius()>-100&&GetDistance()-c.getRadius()<100) {
							score2++;
						}
						System.out.println(GetDistance());
						attackloader+=5;
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
				private void autoEnemy() {
										
					
					double X=player.GetPosX()-player2.GetPosX();
					double Y=player.GetPosY()-player2.GetPosY();
					double angleToShoot,difference;
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
								difference=playerAngle-angleToShoot;
								if(difference>=-5&&difference<=5)
									shoot(player,bullets);
								else if(difference<-5)
									player.rotateRight();
								else if(difference>5)
									player.rotateLeft();
								//System.out.println("2 Cwiartka player Angle "+playerAngle+" Angle to shoot "+angleToShoot+" angle "+angle);
						}
						else {													
							
								angleToShoot=90-angle;						
								difference=playerAngle-angleToShoot;
								if(difference>=-5&&difference<=5)
									shoot(player,bullets);
								else if(difference<-5)
									player.rotateRight();
								else if(difference>5)
									player.rotateLeft();
								
								//System.out.println("1 Cwiartka player Angle "+playerAngle+" Angle to shoot "+angleToShoot+" angle "+angle);
						}
						
					}
					else {
						if (player2.GetPosY()>player.GetPosY()) {
							
							angleToShoot=270-angle;
							difference=playerAngle-angleToShoot;
							if(difference>=-5&&difference<=5)
								shoot(player,bullets);
							else if(difference<-5)
								player.rotateRight();
							else if(difference>5)
								player.rotateLeft();
							
							//System.out.println("3 Cwiartka player Angle "+playerAngle+" Angle to shoot "+angleToShoot+" angle "+angle);
							
						}
						else {							
							
							angleToShoot=360-angle;
							difference=playerAngle-angleToShoot;
							if(difference>=-5&&difference<=5)
								shoot(player,bullets);
							else if(difference<-5)
								player.rotateRight();
							else if(difference>5)
								player.rotateLeft();
							
							//System.out.println("4 Cwiartka player Angle "+playerAngle+" Angle to shoot "+angleToShoot+" angle "+angle);
						}
					}										
					
				}

				private void updatePic() {
					// TODO Auto-generated method stub
					if(score2>MAXSCORE) {
						
						if (n>10) {
							player.ChangeImg("/resources/hilary2.png");
							Text txtSCORES=new Text("Player1 sucks!");
							txtSCORES.setFill(Color.BLACK);
							txtSCORES.setFont(Font.font(STYLESHEET_CASPIAN, 60));
							txtSCORES.setLayoutX(WIDTH/3);
							txtSCORES.setLayoutY(300);
							root.getChildren().add(txtSCORES);
							IsEnd=true;
							
							timeToDisplayFinalWindow++;
							if(timeToDisplayFinalWindow>240) {
							
								timer.stop();
								primaryStage.close();
								res2Contrloller r=new res2Contrloller();
								try {
									r.start(new Stage());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
						else {
							player.ChangeImg("/resources/h"+n+".png");
							
							k++;
							if (k==3||k==6||k==9||k==12||k==15||k==18||k==21||k==24||k==27||k==30||k==33||k==36||k==39||k==42)
								n++;
							
						}				
						
					}
					if(score1>MAXSCORE) {
						if (n>10) {
							player2.ChangeImg("/resources/trump2.png");			
							Text txtSCORES=new Text("Player2 sucks!");
							txtSCORES.setFill(Color.BLACK);
							txtSCORES.setFont(Font.font(STYLESHEET_CASPIAN, 60));
							txtSCORES.setLayoutX(WIDTH/3);
							txtSCORES.setLayoutY(300);
							root.getChildren().add(txtSCORES);
							IsEnd=true;
							
							timeToDisplayFinalWindow++;
							if(timeToDisplayFinalWindow>240) {
								timer.stop();
								primaryStage.close();
								res2Contrloller r=new res2Contrloller();
								try {
									r.start(new Stage());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						else {
							player2.ChangeImg("/resources/t"+n+".png");
							
							k++;
							if (k==3||k==6||k==9||k==12||k==15||k==18||k==21||k==24||k==27||k==30||k==33||k==36||k==39||k==42)
								n++;
							
						}
					}
				}

				//check if player avatar remains in the canvas
				private void check() {
					// TODO Auto-generated method stub
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
					if(player2.GetPosX()<0) {
						player2.imageView.setTranslateX(WIDTH-265);
					}
					if(player2.GetPosX()>WIDTH-265) {
						player2.imageView.setTranslateX(0);
					}
					if(player2.GetPosY()<0) {
						player2.imageView.setTranslateY(HEIGHT);
					}
					if(player2.GetPosY()>HEIGHT) {
						player2.imageView.setTranslateY(0);
					}
				}

				
				//Actually not in use
				private void delay() {
					
					
					
					if (!isPress) {
					if (player.velocity.getY()<0) {
						
						player.velocity=player.velocity.add(0,0.1);
						
		        	}
					if (player.velocity.getY()>0) {
						
						player.velocity=player.velocity.add(0,-0.1);
						
		        	}
		        	if (player.velocity.getX()<0) {
		        		player.velocity=player.velocity.add(0.1,0);
		        		
		        	}
		        	if (player.velocity.getX()>0) {
		        		player.velocity=player.velocity.add(-0.1,0);
		        		

		        	}	
		        	
		        	
					}
					if (!isPress2) {
						if (player2.velocity.getY()<0) {
							
							player2.velocity=player2.velocity.add(0,0.1);
			        	}
						if (player2.velocity.getY()>0) {
							
							player2.velocity=player2.velocity.add(0,-0.1);
			        	}
			        	if (player2.velocity.getX()<0) {
			        		
			        		player2.velocity=player2.velocity.add(0.1,0);
			        	}
			        	if (player2.velocity.getX()>0) {
			        		
			        		player2.velocity=player2.velocity.add(-0.1,0);

			        	}	
			        	
			        	
						}
				}

				private void onUpdate() {
					for (Object bullet : bullets) {
			            
			                if (bullet.isColliding(player2)) {
			                	score1++;
			                    root.getChildren().removeAll(bullet.getView());
			                }
			               
			        }
					for (Object bullet : bullets2) {
			            
		                if (bullet.isColliding(player)) {
		                	score2++;		                    
		                    root.getChildren().removeAll(bullet.getView());
		                }
		               
		        }
					
					score.setText(score1/12+" : "+score2/12);
					player.update();
					player2.update();
					for(Bullet bullet:bullets) {
						bullet.update();
					}
					for(Bullet bullet:bullets2) {
						bullet.update();
					}
				}
	        };
	        
	        
	        primaryStage.getScene().setOnKeyPressed(e -> {
	        	
	            if (e.getCode() == KeyCode.LEFT) {
	            	if((player.velocity.getX()>-5)) {
	            		player.rotateLeft();	            		
	            	}     
	            	isPress=true;
	            		
	            } else if (e.getCode() == KeyCode.RIGHT) {
	            
	            	if((player.velocity.getX()<5)) {
	            		player.rotateRight();	            		
	            	}     
	            	isPress=true;
	            } 
	            else if (e.getCode() == KeyCode.UP) {
	            	if((player.velocity.getY()>-5)) {	            		
	            		player.speedUp();
	            	}    	            	
	            	isPress=true;
	            } 
	            else if (e.getCode() == KeyCode.DOWN) {
	            	if((player.velocity.getY()<5)) {	            		
	            		player.slowDown();
	            	}     
	            	isPress=true;
	            		 
	            }
	            
	            
	           if (e.getCode() == KeyCode.A) {
	            	if((player2.velocity.getX()>-5)) {	            		
	            		player2.rotateLeft();	
	            	}     
	          
	            	isPress2=true;
	            } else if (e.getCode() == KeyCode.D) {
	            	if((player2.velocity.getX()<5)) {
	            		player2.rotateRight();
	            	}  
	            	isPress2=true;
	            } 
	            else if (e.getCode() == KeyCode.W) {
	            	if((player2.velocity.getY()>-5)) {
	            		player2.speedUp();
	            	}     	 
	            	isPress2=true;
	            } 
	            else if (e.getCode() == KeyCode.S) {
	            	if((player2.velocity.getY()<5)) {
	            		player2.slowDown();
	            	}     
	            	isPress2=true;
	            		 
	            }
	           
	           
	        });
	        
	        primaryStage.getScene().setOnKeyReleased(e -> {
	        	
	        	if (e.getCode() == KeyCode.R) {
	            	shoot2(player2,bullets2);	            	
	            }
	        	if(EnableSuperComboPlayer2) {
	        	if (e.getCode() == KeyCode.T) {
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
		            		bullets2.add(bullet);
		            		addObject(bullet, player2.getView().getTranslateX()+30, player2.getView().getTranslateY()+30);
		            	}
	        	  }
	        	if (e.getCode() == KeyCode.E) {
	        			makeSound(audioClip2);           	
	            		Bullet bullet = new Bullet(Color.BLUE);
	            		Bullet bullet2 = new Bullet(Color.YELLOW);
	            		Bullet bullet3 = new Bullet(Color.GREEN);
	            		bullet.setVelocity(player2.getVelocity().normalize().multiply(5));
	            		bullet2.setVelocity(player2.getVelocity().normalize().multiply(5));
	            		bullet3.setVelocity(player2.getVelocity().normalize().multiply(5));
	            		
	            		bullets2.add(bullet);
	            		bullets2.add(bullet2);
	            		bullets2.add(bullet3);
	            		addObject(bullet, player2.getView().getTranslateX()+30, player2.getView().getTranslateY()+20);
	            		addObject(bullet2, player2.getView().getTranslateX()+30, player2.getView().getTranslateY()+40);
	            		addObject(bullet3, player2.getView().getTranslateX()+30, player2.getView().getTranslateY()+60);
	        	}
	        	
	        	if (e.getCode() == KeyCode.Y) {
	        		if(SuperAttack==false) {
	        		SuperAttack=true;
	        		c=new Circle();
	            	c.setCenterX(player2.GetPosX()+40);
	            	c.setCenterY(player2.GetPosY()+40);
	            	c.setRadius(attackloader);
	            	c.setStroke(Color.BLACK);
	            	c.setFill(null);
	            	c.setStrokeWidth(5);	            	
	            	root.getChildren().add(c);
	        		}
	            }
	        	}
	        	
	        	 if (e.getCode() == KeyCode.N) {
		            	makeSound(audioClip);
		                Bullet bullet = new Bullet();
		                bullet.setVelocity(player.getVelocity().normalize().multiply(5));		               
		                bullets.add(bullet);
		                addObject(bullet, player.getView().getTranslateX()+30, player.getView().getTranslateY()+30);
		         }
	        	 
		         if (e.getCode() == KeyCode.M) {
		            	makeSound(audioClip3);
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
		            		bullets.add(bullet);
		            		addObject(bullet, player.getView().getTranslateX()+30, player.getView().getTranslateY()+30);
		            	}	            	
		            		                
		           }
		         
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
   
	
	private void addObject(Object object, double x, double y) {
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
	
	
	int GetDistance() {
		return (int) Math.sqrt(Math.pow(player.GetPosY()-c.getCenterY(),2)+Math.pow(player.GetPosX()-c.getCenterX(),2));
	}
	
	int GetDistanceUniversal(double x1,double y1, double x2,double y2) {
		return (int) Math.sqrt(Math.pow(y1-y2,2)+Math.pow(x1-x2,2));
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
	
	
	public void shoot2(Obiect_Player p,List<Bullet> b) {
			
		makeSound(audioClip);
        Bullet bullet = new Bullet();
        bullet.setVelocity(p.getVelocity().normalize().multiply(5));
        b.add(bullet);
        addObject(bullet, p.getView().getTranslateX()+30, p.getView().getTranslateY()+30);
		
	}
	
	public class Bullet extends Object{
		Bullet(){
			super(new Circle(5,5,5,Color.RED));			
		}
		Bullet(Color color){
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}
