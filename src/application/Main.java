package application;
	
import java.applet.Applet;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;


public class Main extends Application {
	
	BorderPane root;	
	Player2 player,player2;
	int WIDTH=1150,HEIGHT=700;
	public boolean isPress=false,isPress2=false,isStop=false,IsEnd=false,SuperAttack=false;
	private List<Bullet> bullets = new ArrayList<>();
	private List<Bullet> bullets2 = new ArrayList<>();
	private List<Point2D> points = new ArrayList<>();
	private java.applet.AudioClip audioClip,audioClip2,audioClip3;
	int score1=0,score2=0,n=1,k=0,t=0,r=0,timeToDisplayFinalWindow=0,attackloader=0;
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
			Scene scene = new Scene(root,WIDTH,HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setTitle("GAME");
			primaryStage.setScene(scene);
			//player = new Player(Color.BLUE);
	        //player.setVelocity(new Point2D(0, 0));
	        //addObject(player, 300, 300);
	        player2 = new Player2("trump.png");	       
	        player2.setVelocity(new Point2D(1, 1));
	        addPlayerObject(player2, 100, 100);
	        score=new Text(WIDTH/2, 50, "0:0");
	       

	        URL url = new URL("file:\\Users\\Adam\\Downloads\\shoot.wav");
	        audioClip = Applet.newAudioClip(url);	        
	        URL url2 = new URL("file:\\Users\\Adam\\Desktop\\wavs\\t3.wav");
	        audioClip2 = Applet.newAudioClip(url2);
	        URL url3 = new URL("file:\\Users\\Adam\\Desktop\\wavs\\h3.wav");
	        audioClip3 = Applet.newAudioClip(url3);
	        
	        
	        score.setFont(Font.font(40));
	        root.getChildren().add(score);
	        player=new Player2("hilary.png");
	        player.setVelocity(new Point2D(-1,-1));
	        addPlayerObject(player, 300, 300);
	        
	        
	        
			
			
			
	        
	        timer = new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	            	
	                onUpdate();
	                tick++;
	                check();
	                updatePic();
	                if (isStop==false&&IsEnd==false)
	                	 autoEnemy();
	                
	                removeBulletsOutOfBounds();
	                updateSuperAttack();
	               //delay();
	               
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


				@SuppressWarnings("unlikely-arg-type")
				private void removeBulletsOutOfBounds() {
					for(Bullet b:bullets) {
						if (b.GetPosX()>WIDTH||b.GetPosX()<0||b.GetPosY()<0||b.GetPosY()>HEIGHT)							
							root.getChildren().remove(b);
							
					}
					for(Bullet b:bullets2) {
						if (b.GetPosX()>WIDTH||b.GetPosX()<0||b.GetPosY()<0||b.GetPosY()>HEIGHT)							
							root.getChildren().remove(b);						
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
					if(score2>1200) {
						
						if (n>10) {
							player.ChangeImg("hilary2.png");
							Text txtSCORES=new Text("Player1 sucks!");
							txtSCORES.setFill(Color.BLACK);
							txtSCORES.setFont(Font.font(STYLESHEET_CASPIAN, 60));
							txtSCORES.setLayoutX(WIDTH/3);
							txtSCORES.setLayoutY(300);
							root.getChildren().add(txtSCORES);
							IsEnd=true;
							
							timeToDisplayFinalWindow++;
							if(timeToDisplayFinalWindow>360) {
							try {
								timer.stop();
								primaryStage.close();
						        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("res2.fxml"));
						                Parent root1 = (Parent) fxmlLoader.load();
						                Stage stage = new Stage();
						                stage.setScene(new Scene(root1));  
						                stage.show();
						        } catch(Exception e) {
						           e.printStackTrace();
						   }
							}
							
						}
						else {
							player.ChangeImg("h"+n+".png");
							
							k++;
							if (k==3||k==6||k==9||k==12||k==15||k==18||k==21||k==24||k==27||k==30||k==33||k==36||k==39||k==42)
								n++;
							
						}				
						
					}
					if(score1>1200) {
						if (n>10) {
							player2.ChangeImg("trump2.png");			
							Text txtSCORES=new Text("Player2 sucks!");
							txtSCORES.setFill(Color.BLACK);
							txtSCORES.setFont(Font.font(STYLESHEET_CASPIAN, 60));
							txtSCORES.setLayoutX(WIDTH/3);
							txtSCORES.setLayoutY(300);
							root.getChildren().add(txtSCORES);
							IsEnd=true;
							
							timeToDisplayFinalWindow++;
							if(timeToDisplayFinalWindow>360) {
							try {
							timer.stop();
							primaryStage.close();
						        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("res2.fxml"));
						                Parent root1 = (Parent) fxmlLoader.load();
						                Stage stage = new Stage();
						                stage.setScene(new Scene(root1));  
						                stage.show();
						        } catch(Exception e) {
						           e.printStackTrace();
						   }
							}
						}
						else {
							player2.ChangeImg("t"+n+".png");
							
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
						player.imageView.setTranslateX(WIDTH);
					}
					if(player.GetPosX()>WIDTH) {
						player.imageView.setTranslateX(0);
					}
					if(player.GetPosY()<0) {
						player.imageView.setTranslateY(HEIGHT);
					}
					if(player.GetPosY()>HEIGHT) {
						player.imageView.setTranslateY(0);
					}
					if(player2.GetPosX()<0) {
						player2.imageView.setTranslateX(WIDTH);
					}
					if(player2.GetPosX()>WIDTH) {
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
					
					score.setText(score1/12+":"+score2/12);
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
	            	//c.strc.getStyleClass().add("ring");
	            	root.getChildren().add(c);
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
	
    
    //The main is now launched in res2Controller
	//public static void main(String[] args) {
		//launch(args);
	//}
	
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
	
}
