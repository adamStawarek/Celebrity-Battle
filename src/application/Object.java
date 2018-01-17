package application;

import javafx.geometry.Point2D;
import javafx.scene.Node;
public class Object {
	 public Node view;
	 public Point2D velocity = new Point2D(0, 0);
	 
	 
	 	public Object(Node view) {
	 		this.view = view;
	 	} 
	 	
	 	
	 	public void update() {
	        view.setTranslateX(view.getTranslateX() + velocity.getX());
	        view.setTranslateY(view.getTranslateY() + velocity.getY());
	    }
	 	
	 	public double GetPosX() {
	 		return view.getTranslateX();
	 	}
	 	public double GetPosY() {
	 		return view.getTranslateY();
	 	}

	    public void setVelocity(Point2D velocity) {
	        this.velocity = velocity;
	    }
	    

	    public Point2D getVelocity() {
	        return velocity;
	    }

	    public Node getView() {
	        return view;
	    }
	    public boolean isColliding(Obiect_Player other) {
	        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
	    }
}
