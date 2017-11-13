package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SqlScores {
	public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:scoresV2.sqlite";
    
    private Connection conn;
    private Statement stat;
 
    public SqlScores() {
		
        try {
            Class.forName(SqlScores.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
 
        createTables();
    }
    public boolean createTables()  {
        String createScores = "CREATE TABLE IF NOT EXISTS scores (id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(255), score int)";
        try {
            stat.execute(createScores);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean insertScore(String name, int score) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into scores values (NULL, ?, ?);");
            prepStmt.setString(1, name);
            prepStmt.setLong(2, score );           
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu wyniku");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public List<ScoreObiect> selectScores() {
        List<ScoreObiect> scores = new LinkedList<ScoreObiect>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM scores");
            int id;
            String name;
            int score;
            while(result.next()) {
                id = result.getInt("id");
                name = result.getString("name");
                score= (int) result.getLong("score");
                
                scores.add(new ScoreObiect(id, name, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return scores;
    }
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
 
}
