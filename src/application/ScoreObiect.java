package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreObiect implements Comparable<ScoreObiect>{
	
	public int id;
	String name;
	int score;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date;
	String prettyDate;
	
	public String getPrettyDate() {
		return prettyDate;
	}

	public void setPrettyDate(String prettyDate) {
		this.prettyDate = prettyDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public ScoreObiect(int id2, String name2, int score2,Date _date) {
		// TODO Auto-generated constructor stub
		this.id = id2;
		this.name = name2;
		this.score = score2;
		date = _date;
		prettyDate=dateFormat.format(date);
	}

	@Override
	public String toString() {
		return "id: " + id + "                     name: " + name + "                        score: " + score+"                          date: "+dateFormat.format(date);
	}
	@Override
    public int compareTo(ScoreObiect o) {
        int porownaneWyniki = ((Integer) score).compareTo(o.score);
        return porownaneWyniki;     
    }
}
