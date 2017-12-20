package application;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import application.Main.Bullet;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obiect_Player {
	 private Image image;
	 public ImageView imageView;
	 public Point2D velocity = new Point2D(0, 0);
	 
	 
	 	public Obiect_Player(String url) {
	 		this.velocity = new Point2D(0,0);
	 		image = new Image(getClass().getResourceAsStream(url));
	        imageView = new ImageView();
	        imageView.setFitHeight(100);
	        imageView.setFitWidth(80);
	        imageView.setImage(image);	        	        	        	     
	 	} 
	 	public void ChangeImg(String url) {
	 		image = new Image(getClass().getResourceAsStream(url));
	        
	        imageView.setImage(image);
	 	}
	 	
	 	public void update() {
	        imageView.setTranslateX(imageView.getTranslateX() + velocity.getX());
	        imageView.setTranslateY(imageView.getTranslateY() + velocity.getY());
	    }
	 	
	 	public double GetPosX() {
	 		return imageView.getTranslateX();
	 	}
	 	public double GetPosY() {
	 		return imageView.getTranslateY();
	 	}

	    public void setVelocity(Point2D velocity) {
	        this.velocity = velocity;
	    }
	    

	    public Point2D getVelocity() {
	        return velocity;
	    }

	    public ImageView getView() {
	        return imageView;
	    }
	    public boolean isColliding(Object other) {
	        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
	    }
	    public double getRotate() {
	        return imageView.getRotate();
	    }
	    public void SetRotate(double d) {
	    	imageView.setRotate(d);
	    }
	    
	    public void speedUp() {
	    	 setVelocity(new Point2D(velocity.getX()*1.05, velocity.getY()*1.05));
	    }
	    public void slowDown() {
	    	 setVelocity(new Point2D(velocity.getX()*0.999, velocity.getY()*0.999));
	    }
	    public void rotateRight(int n) {
	    	imageView.setRotate(imageView.getRotate() + n);
	        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
	    }

	    public void rotateLeft(int n) {
	    	imageView.setRotate(imageView.getRotate() - n);
	        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
	    }

	    public void rotateRight() {
	    	imageView.setRotate(imageView.getRotate() + 5);
	        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
	    }

	    public void rotateLeft() {
	    	imageView.setRotate(imageView.getRotate() - 5);
	        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
	    }
	    public void rotateRightMore() {
	    	imageView.setRotate(imageView.getRotate() + 5);
	        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
	    }

	    public void rotateLeftMore() {
	    	imageView.setRotate(imageView.getRotate() - 5);
	        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
	    }
}