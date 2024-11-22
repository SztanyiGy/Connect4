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
 * Handles the database operations for the Connect-4 game.
 * Manages player data and high scores.
 */
public class Database {
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
    private Connection connection;

    /**
     * Initializes the database connection and creates the necessary table if it doesn't exist.
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
     * Creates the high_scores table if it doesn't already exist.
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
     * Adds a win for the specified player. If the player doesn't exist in the database,
     * they are added with a win count of 1.
     *
     * @param playerName The name of the player.
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
     * Checks if a player exists in the database.
     *
     * @param playerName The name of the player to check.
     * @return True if the player exists, false otherwise.
     */
    private boolean isPlayerInDatabase(String playerName) {
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
     * Displays the high scores, sorted by the number of wins in descending order.
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
     * Closes the database connection.
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
