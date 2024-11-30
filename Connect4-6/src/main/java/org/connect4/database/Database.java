package org.connect4.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kezeli a Connect-4 játék adatbázis műveleteit.
 * Kezeli a játékos adatokat és a legjobb eredményeket.
 */
public class Database {
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
    private Connection connection;

    /**
     * Inicializálja az adatbázis kapcsolatot, és létrehozza a szükséges táblát, ha az nem létezik.
     */
    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:connect4.db");
            if (connection == null) {
                LOGGER.error("Failed to create connection object.");
            } else {
                LOGGER.info("Connected to database successfully.");
                initializeDatabase();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to connect to database: {}", e.getMessage());
            connection = null;
        }
    }

    /**
     * Létrehozza a high_scores táblát, ha az még nem létezik.
     */
    private void initializeDatabase() {
        try (Statement statement = connection.createStatement()) {
            String createTableSql = "CREATE TABLE IF NOT EXISTS high_scores (" +
                    "player_name TEXT PRIMARY KEY, " +
                    "wins INTEGER DEFAULT 0)";
            statement.executeUpdate(createTableSql);
            LOGGER.info("Database table 'high_scores' initialized successfully.");
        } catch (SQLException e) {
            LOGGER.error("Failed to initialize database: {}", e.getMessage());
        }
    }

    /**
     * Hozzáad egy győzelmet a megadott játékoshoz. Ha a játékos nem létezik az adatbázisban,
     * hozzáadja őt 1 győzelemmel.
     *
     * @param playerName A játékos neve.
     */
    public void addWin(String playerName) {
        try {
            if (isPlayerInDatabase(playerName)) {
                String updateSql = "UPDATE high_scores SET wins = wins + 1 WHERE player_name = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setString(1, playerName);
                    updateStatement.executeUpdate();
                    LOGGER.info("Updated win count for player '{}'.", playerName);
                }
            } else {
                String insertSql = "INSERT INTO high_scores (player_name, wins) VALUES (?, 1)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                    insertStatement.setString(1, playerName);
                    insertStatement.executeUpdate();
                    LOGGER.info("Added new player '{}' with 1 win.", playerName);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to add win for player '{}': {}", playerName, e.getMessage());
        }
    }

    /**
     * Ellenőrzi, hogy egy játékos létezik-e az adatbázisban.
     *
     * @param playerName A játékos neve, akit ellenőrizni kell.
     * @return Igaz, ha a játékos létezik, egyébként hamis.
     */
    public boolean isPlayerInDatabase(String playerName) {
        String checkSql = "SELECT 1 FROM high_scores WHERE player_name = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
            checkStatement.setString(1, playerName);
            ResultSet rs = checkStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.error("Failed to check player in database '{}': {}", playerName, e.getMessage());
            return false;
        }
    }

    /**
     * Megjeleníti a legjobb eredményeket, győzelmek szerint csökkenő sorrendben rendezve.
     */
    public void displayHighScores() {
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT player_name, wins FROM high_scores ORDER BY wins DESC";
            ResultSet rs = statement.executeQuery(sql);

            System.out.printf("%-20s %s%n", "Name", "Wins");
            System.out.println("-------------------- -----");
            while (rs.next()) {
                String player = rs.getString("player_name");
                int wins = rs.getInt("wins");
                System.out.printf("%-20s %d%n", player, wins);
            }
            LOGGER.info("Displayed high scores successfully.");
        } catch (SQLException e) {
            LOGGER.error("Failed to display high scores: {}", e.getMessage());
        }
    }

    /**
     * Bezárja az adatbázis kapcsolatot.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LOGGER.info("Database connection closed.");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to close database connection: {}", e.getMessage());
        }
    }
}
