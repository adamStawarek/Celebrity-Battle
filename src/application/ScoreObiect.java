package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreObiect {
	int id;
	String name;
	int score;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date;
	
	public ScoreObiect(int id2, String name2, int score2,Date _date) {
		// TODO Auto-generated constructor stub
		this.id = id2;
		this.name = name2;
		this.score = score2;
		date = _date;
	}

	@Override
	public String toString() {
		return "id: " + id + "                     name: " + name + "                        score: " + score+"                          date: "+dateFormat.format(date);
	}
	
}
