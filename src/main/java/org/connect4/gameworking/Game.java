package org.connect4.gameworking;

import org.connect4.Main;
import org.connect4.database.Database;
import org.connect4.gamelogic.FileManager;
import org.connect4.gamelogic.GameState;
import org.connect4.gamelogic.Logic;
import org.connect4.model.Board;
import org.connect4.player.ComputerPlayer;
import org.connect4.player.HumanPlayer;
import org.connect4.player.Player;

/**
 * The Game class manages the main game logic, including player turns,
 * game state, and interactions with the user and the database.
 */
public class Game {
    private static final String GAME_START_MSG = "A játék kezdődik...";
    private static final String GAME_END_MSG = "A játék véget ért!";
    private static final String PLAYER_TURN_MSG = " lép.";
    private static final String WIN_MSG = " nyert!";
    private static final String COLUMN_FULL_MSG = "Ez az oszlop megtelt. Próbálj egy másikat!";

    private InputReader inputReader;
    public GameState gameState;
    public FileManager fileManager;
    private Logic gameLogic;
    private BoardRenderer boardRenderer;
    public Player humanPlayer;
    private Player computerPlayer;
    public boolean isHumanTurn;
    public Database database;

    /**
     * Constructs a new Game instance, initializing the game components.
     *
     * @param inputReader The input reader for user interaction.
     */
    public Game(InputReader inputReader) {
        this.inputReader = inputReader;
        this.database = new Database();  // Inicializáljuk az adatbázist
        this.gameState = new GameState(new Board(Main.ROWS, Main.COLUMNS));
        this.fileManager = new FileManager();
        this.gameLogic = new Logic(gameState.getBoard());
        this.boardRenderer = new BoardRenderer();
    }

    /**
     * Displays the main menu and handles user input for game options.
     */
    public void showMenu() {
        while (true) {
            System.out.println("1. Játék kezdése");
            System.out.println("2. High Score-ok megtekintése");
            System.out.println("3. Kilépés");
            System.out.print("Melyiket választja: ");
            int choice = Integer.parseInt(inputReader.nextLine());

            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    database.displayHighScores();
                    break;
                case 3:
                    database.close();
                    System.out.println("Kilépés a játékból. Viszlát!");
                    return;
                default:
                    System.out.println("Rossz választás. Próbáld újra");
            }
        }
    }

    /**
     * Starts a new game and manages the main game loop.
     */
    private void startGame() {
        System.out.println(GAME_START_MSG);
        resetGame();
        initializePlayers();

        while (true) {
            boardRenderer.render(gameState.getBoard());
            Player currentPlayer = getCurrentPlayer();
            playTurn(currentPlayer);

            if (gameLogic.checkForWin(currentPlayer.getColor())) {
                processWin(currentPlayer);
                database.addWin(currentPlayer.getName());  // Pontszám hozzáadása a nyertesnek
                break;
            }

            isHumanTurn = !isHumanTurn;
        }

        fileManager.saveGameToFile(gameState.getBoard());
        System.out.println(GAME_END_MSG);
    }

    /**
     * Resets the game state for a new game.
     */
    private void resetGame() {
        this.gameState = new GameState(new Board(Main.ROWS, Main.COLUMNS)); // Új GameState inicializálása új táblával
        this.gameLogic = new Logic(gameState.getBoard()); // Új logika inicializálása az új táblával
        this.isHumanTurn = true; // Az ember kezd
    }

    /**
     * Initializes the players for the game, prompting the user for input.
     */
    public void initializePlayers() {
        System.out.print("Enter player name: ");
        String playerName = inputReader.nextLine();
        this.humanPlayer = new HumanPlayer(playerName, "Sárga");
        this.computerPlayer = new ComputerPlayer("Computer", "Piros");
    }

    /**
     * Determines the current player based on the turn order.
     *
     * @return The current player.
     */
    private Player getCurrentPlayer() {
        return isHumanTurn ? humanPlayer : computerPlayer;
    }

    /**
     * Executes the current player's turn, handling their move.
     *
     * @param currentPlayer The player whose turn it is.
     */
    public void playTurn(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + PLAYER_TURN_MSG);
        int column = (currentPlayer instanceof HumanPlayer)
                ? getPlayerMove(currentPlayer)
                : currentPlayer.makeMove(gameState.getBoard().getColumns());

        if (!gameState.getBoard().dropDisc(currentPlayer, column)) {
            System.out.println(COLUMN_FULL_MSG);
            playTurn(currentPlayer); // újra próbálkozás
        }
    }

    /**
     * Handles the actions when a player wins the game.
     *
     * @param currentPlayer The player who won the game.
     */
    private void processWin(Player currentPlayer) {
        boardRenderer.render(gameState.getBoard());
        System.out.println(currentPlayer.getName() + WIN_MSG);
    }

    /**
     * Prompts the human player for their move and returns the selected column.
     *
     * @param currentPlayer The human player.
     * @return The column selected by the player.
     */
    public int getPlayerMove(Player currentPlayer) {
        System.out.printf("%s, válassz oszlopot (0-%d): ", currentPlayer.getName(), Main.COLUMNS - 1);
        return Integer.parseInt(inputReader.nextLine());
    }
}
