package org.connect4.database;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private static Connection connection;
    private Database databaseManager;

    @BeforeAll
    static void setupDatabase() throws SQLException {
        // In-memory SQLite adatbázis létrehozása
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        try (var statement = connection.createStatement()) {
            String createTable = """
                    CREATE TABLE high_scores (
                        player_name TEXT PRIMARY KEY,
                        wins INTEGER DEFAULT 0
                    )
                    """;
            statement.execute(createTable);
        }
    }

    @BeforeEach
    void setup() {
        databaseManager = new Database(); // DatabaseManager az osztályod neve
    }

    @AfterAll
    static void teardownDatabase() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    @Test
    void testIsPlayerInDatabase_PlayerDoesNotExist() {
        boolean result = databaseManager.isPlayerInDatabase("NonExistentPlayer");
        assertFalse(result, "The player should not exist in the database.");
    }

    @Test
    void testDisplayHighScores() throws SQLException {
        // Alapadatok beszúrása
        try (var statement = connection.createStatement()) {
            statement.execute("INSERT INTO high_scores (player_name, wins) VALUES ('Player1', 10)");
            statement.execute("INSERT INTO high_scores (player_name, wins) VALUES ('Player2', 8)");
        }

        // Teszt: nincs konkrét assert, csak a működést figyeljük
        Assertions.assertDoesNotThrow(() -> databaseManager.displayHighScores());
    }


}

