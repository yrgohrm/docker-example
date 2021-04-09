package se.yrgo.deb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import se.yrgo.deb.entity.Highscore;
import se.yrgo.deb.entity.Highscores;

public class HighscoresDao {
    private static final Logger log = Logger.getLogger(HighscoresDao.class.getName());

    private HighscoresDao() {
    }

    public static Highscores getHighscores() {
        Highscores highscores = new Highscores();
        try (Connection conn = DataSource.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT highscore_id, player, score FROM highscore ORDER BY score DESC")) {
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("highscore_id");
                String player = res.getString("player");
                int score = res.getInt("score");
                highscores.add(new Highscore(id, player, score));
            }

            return highscores;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Unable to get highscores", ex);
            return highscores;
        }
    }

    public static boolean addHighscore(String player, int score) {
        if (player.length() != 3) {
            throw new IllegalArgumentException("Player name must be three characters long");
        }

        if (score < 1) {
            throw new IllegalArgumentException("Player score must be positive.");
        }

        try (Connection conn = DataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO highscore (player, score) VALUES (?, ?)")) {
            stmt.setString(1, player);
            stmt.setInt(2, score);
            int res = stmt.executeUpdate();
            return res == 1;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Unable to get highscores", ex);
            return false;
        }
    }
}
