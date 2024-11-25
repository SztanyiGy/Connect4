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
 * A Game osztály kezeli a fő játéklogikát, beleértve a játékosok lépéseit,
 * a játék állapotát, valamint a felhasználói interakciókat és az adatbázis műveleteket.
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
    public Player computerPlayer;
    public boolean isHumanTurn;
    public Database database;

    /**
     * Új Game példányt hoz létre, inicializálva a játék komponenseit.
     *
     * @param inputReader Az input olvasó a felhasználói interakcióhoz.
     */
    public Game(InputReader inputReader) {
        this.inputReader = inputReader;
        this.database = new Database();  // Az adatbázis inicializálása
        this.gameState = new GameState(new Board(Main.ROWS, Main.COLUMNS));
        this.fileManager = new FileManager();
        this.gameLogic = new Logic(gameState.getBoard());
        this.boardRenderer = new BoardRenderer();
    }

    /**
     * Megjeleníti a főmenüt és kezeli a felhasználói választásokat.
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
     * Elindít egy új játékot és kezeli a fő játékciklust.
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
     * Visszaállítja a játék állapotát egy új játékhoz.
     */
    private void resetGame() {
        this.gameState = new GameState(new Board(Main.ROWS, Main.COLUMNS)); // Új GameState inicializálása új táblával
        this.gameLogic = new Logic(gameState.getBoard()); // Új logika inicializálása az új táblával
        this.isHumanTurn = true; // Az ember kezd
    }

    /**
     * Inicializálja a játékosokat, és bekéri a felhasználótól a szükséges adatokat.
     */
    public void initializePlayers() {
        System.out.print("Add meg a játékos nevét: ");
        String playerName = inputReader.nextLine();
        this.humanPlayer = new HumanPlayer(playerName, "Sárga");
        this.computerPlayer = new ComputerPlayer("Computer", "Piros");
    }

    /**
     * Meghatározza, hogy jelenleg melyik játékos következik.
     *
     * @return A soron következő játékos.
     */
    public Player getCurrentPlayer() {
        return isHumanTurn ? humanPlayer : computerPlayer;
    }

    /**
     * Végrehajtja az aktuális játékos lépését, és kezeli a mozgásukat.
     *
     * @param currentPlayer Az aktuális játékos.
     */
    public void playTurn(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + PLAYER_TURN_MSG);
        int column = (currentPlayer instanceof HumanPlayer)
                ? getPlayerMove(currentPlayer)
                : currentPlayer.makeMove(gameState.getBoard().getColumns());

        if (!gameState.getBoard().dropDisc(currentPlayer, column)) {
            System.out.println(COLUMN_FULL_MSG);
            playTurn(currentPlayer); // Újra próbálkozás
        }
    }

    /**
     * Kezeli a nyerési eseményt, ha egy játékos nyer.
     *
     * @param currentPlayer Az a játékos, aki nyert.
     */
    private void processWin(Player currentPlayer) {
        boardRenderer.render(gameState.getBoard());
        System.out.println(currentPlayer.getName() + WIN_MSG);
    }

    /**
     * Bekéri a játékos lépését és visszaadja a választott oszlopot.
     *
     * @param currentPlayer Az emberi játékos.
     * @return Az oszlop, amelyet a játékos választott.
     */
    public int getPlayerMove(Player currentPlayer) {
        System.out.printf("%s, válassz oszlopot (0-%d): ", currentPlayer.getName(), Main.COLUMNS - 1);
        return Integer.parseInt(inputReader.nextLine());
    }
}
