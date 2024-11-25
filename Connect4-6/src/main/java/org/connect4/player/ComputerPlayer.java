package org.connect4.player;

/**
 * A ComputerPlayer osztály, amely a játékosok gépi vezérlését
 * reprezentálja a Connect 4 játékban.
 */
public final class ComputerPlayer implements Player {

    /** A játékos neve. */
    private final String playerName;

    /** A játékos színe. */
    private final String playerColor;

    /**
     * Konstruktor, amely inicializálja a játékos nevét és színét.
     *
     * @param name  A játékos neve.
     * @param color A játékos színe, 'sárga' vagy 'piros' kell legyen.
     * @throws IllegalArgumentException Ha a szín nem érvényes.
     */
    public ComputerPlayer(final String name, final String color) {
        if (!color.equals("Sárga") && !color.equals("Piros")) {
            throw new IllegalArgumentException(
                    "A színnek 'Sárga' vagy 'Piros'-nak kell lennie."
            );
        }
        this.playerName = name;
        this.playerColor = color;
    }

    /**
     * Visszaadja a játékos nevét.
     *
     * @return A játékos neve.
     */
    @Override
    public String getName() {
        return playerName;
    }

    /**
     * Visszaadja a játékos színét.
     *
     * @return A játékos színe.
     */
    @Override
    public String getColor() {
        return playerColor;
    }

    /**
     * Visszaad egy oszlop számot, ahol a gép lehelyezi a korongot.
     * Random módon választ egy oszlopot.
     *
     * @param maxColumns A maximális oszlopszám, ahova a gép
     *                   lehelyezheti a korongot.
     * @return A gép által választott oszlop száma.
     */
    @Override
    public int makeMove(final int maxColumns) {
        return (int) (Math.random() * maxColumns);  // Random lépés
    }
}
