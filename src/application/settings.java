package application;

import javafx.stage.Stage;

public class settings {
	public void Play() {
		Main m=new Main();
		Stage s= new Stage();
		m.start(s);
	}
	public void GoBack() {
		res2Contrloller r=new res2Contrloller();
		r.OpenMenu();
	}
}
