package application;
	
import java.applet.Applet;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import application.Main.Bullet;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.layout.BorderPane;
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
	Player2 player,player2;
	int WIDTH=1150,HEIGHT=700;
	public boolean isPress=false,isPress2=false,isStop=false,IsEnd=false,SuperAttack=false,IsMultiPlayer=false;
	private List<Bullet> bullets = new ArrayList<>();
	private List<Bullet> bullets2 = new ArrayList<>();
	private List<Point2D> points = new ArrayList<>();
	private java.applet.AudioClip audioClip,audioClip2,audioClip3,audioClip4,audioClip5;
	public int n=1,k=0,t=0,r=0,timeToDisplayFinalWindow=0,attackloader=0,ComboTimeCounter=600;
	public static int score1=0,score2=0,MAXSCORE=1200;
	int time;
	int currentCombo=100,currentCombo2=100;//set for normal shooting
	public static Text txtSCORES;

	ImageView bonusView;
	public Stage stage;
	Circle c;
	int tick=0;
	double angle;
	AnimationTimer timer;	
	ImageView i;
	Image im;
	Random randCombo;
	
	
	int timeWithoutBonus = 0;
	int timeWithBonus=0;
	
	
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
	        player2.setVelocity(new Point2D(2, 2));
	        addPlayerObject(player2, 100, 100);
	        
	        score1=0;
	        score2=0;
	        txtSCORES=new Text();
	        txtSCORES.setText("");
	        txtSCORES.setFill(Color.BLACK);
			txtSCORES.setFont(Font.font(STYLESHEET_CASPIAN, 60));
			txtSCORES.setLayoutX(WIDTH/3);
			txtSCORES.setLayoutY(300);
			root.getChildren().add(txtSCORES);
	        
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
	        
	        Image bonus = new Image(getClass().getResourceAsStream("/resources/eagle.gif"));
	        bonusView = new ImageView();	       
	        bonusView.setImage(bonus);	
	      
	        player=new Player2("/resources/hilary.png");
	        player.setVelocity(new Point2D(-2,-2));
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
	               
	            }
	            	
	            
	            private void addBonus() {
					// TODO Auto-generated method stub
	            	
	            	
	            	if(IsBonus()==false&&timeWithoutBonus>100) {	            		
	            		Random r=new Random();
						int randNumb1=r.nextInt(WIDTH-330);
						int randNumb2=r.nextInt(HEIGHT-140);				        
				        bonusView.setTranslateX(randNumb1);
				        bonusView.setTranslateY(randNumb2);
				        root.getChildren().add(bonusView);
				        timeWithoutBonus=0;
	            	}
	            	
	            	if(IsBonus()==true&&timeWithBonus>900) {
	            		root.getChildren().remove(bonusView);
	            		timeWithBonus=0;            		
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

						}
						else if(80>dist2) {
							makeSound(audioClip2);
							root.getChildren().remove(bonusView);
							timeWithBonus=0;
							pl2bonus=1;
							currentCombo=randCombo.nextInt(3);
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
	            	else
	            		currentCombo=100;
	            						
				}


				private void updateSuperAttack() {
					// TODO Auto-generated method stub
					if(SuperAttack==true) {
						if(GetDistance()-c.getRadius()>-100&&GetDistance()-c.getRadius()<100) {
							score2++;
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
							txtSCORES.setText("Player1 sucks!");
							IsEnd=true;
							
							timeToDisplayFinalWindow++;
							if(timeToDisplayFinalWindow>240) {
								audioClip4.play();
								timer.stop();
								primaryStage.close();
								res2Contrloller r=new res2Contrloller();
								r.IsTrumpWin=false;
								
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
							if(k==3) {
								audioClip5.play();
							}
						}				
						
					}
					if(score1>MAXSCORE) {
						if (n>10) {
							player2.ChangeImg("/resources/trump2.png");			
							txtSCORES.setText("Player2 sucks!");
							
							IsEnd=true;
							
							timeToDisplayFinalWindow++;
							if(timeToDisplayFinalWindow>240) {
								audioClip4.play();
								timer.stop();
								primaryStage.close();
								res2Contrloller r=new res2Contrloller();
								r.IsTrumpWin=true;
								
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
							if(k==3) {
								audioClip5.play();
							}
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
	            	//shoot2(player2,bullets2);	            	
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
	        	
	        	//Do sprawdzenia!!!!!!!!!!!!!!!!!!
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
	
	private boolean IsBonus() {
		return root.getChildren().contains(bonusView);
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
						// TODO Auto-generated method stub
					
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
		Image i1 = new Image(getClass().getResourceAsStream("/resources/trump.png"));
        pl1Img.setImage(i1);
        Image i2 = new Image(getClass().getResourceAsStream("/resources/hilary.png"));
        pl2Img.setImage(i2);
		  time=0;
		  handleStartAction();		
		 
	}
	
}
