package se.yrgo.deb.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import se.yrgo.deb.db.HighscoresDao;
import se.yrgo.deb.entity.Highscores;
import se.yrgo.deb.util.OutputHandler;

public class HighscoreServlet extends HttpServlet {
    private static final long serialVersionUID = 298073788323315801L;
    private static final Logger log = Logger.getLogger(HighscoreServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        addCorsHeaders(res);

        Highscores highscores = HighscoresDao.getHighscores();

        try {
            OutputHandler.writeFormatted(highscores, req, res);
        } catch (IOException | JAXBException ex) {
            log.log(Level.WARNING, "Error writing response", ex);
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setStatus(HttpServletResponse.SC_NO_CONTENT);
        addCorsHeaders(res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        addCorsHeaders(res);

        String playerParam = req.getParameter("player");
        String scoreParam = req.getParameter("score");

        String player;
        int score;
        try {
            player = validatePlayer(playerParam);
            score = validateScore(scoreParam);
        } catch (IllegalArgumentException ex) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().printf("Bad request: %s", ex.getMessage());
            return;
        }

        if (HighscoresDao.addHighscore(player, score)) {
            res.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static void addCorsHeaders(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
        res.setHeader("Access-Control-Max-Age", "86400");
    }

    private int validateScore(String scoreParam) {
        if (scoreParam == null) {
            throw new IllegalArgumentException("Score must be supplied");
        }

        try {
            return Integer.parseInt(scoreParam);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Score must be an integer", ex);
        }
    }

    private String validatePlayer(String playerParam) {
        if (playerParam == null) {
            throw new IllegalArgumentException("Player must be supplied");
        }

        playerParam = playerParam.trim();

        if (playerParam.length() != 3) {
            throw new IllegalArgumentException("Player must be three characters");
        }

        return playerParam;
    }
}
