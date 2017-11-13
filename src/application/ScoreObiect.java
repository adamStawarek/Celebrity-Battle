package application;

public class ScoreObiect {
	int id;
	String name;
	int score;
	
	public ScoreObiect(int id2, String name2, int score2) {
		// TODO Auto-generated constructor stub
		this.id = id2;
		this.name = name2;
		this.score = score2;
	}

	@Override
	public String toString() {
		return "id: " + id + "                     name: " + name + "                           score: " + score;
	}
	
}
