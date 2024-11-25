package org.connect4.player;

import java.util.Scanner;

/**
 * A HumanPlayer osztály, amely a játékosok emberi vezérlését
 * reprezentálja a Connect 4 játékban.
 */
public final class HumanPlayer implements Player {
    /**
     * A játékos neve.
     */
    private final String playerName;

    /**
     * A játékos színe.
     */
    private final String playerColor;

    /**
     * Konstruktor, amely inicializálja a játékos nevét és színét.
     *
     * @param name  A játékos neve, nem lehet üres.
     * @param color A játékos színe, 'sárga' vagy 'piros' kell legyen.
     * @throws IllegalArgumentException Ha a szín nem érvényes.
     */
    public HumanPlayer(final String name, final String color) {
        if (!color.equals("Sárga") && !color.equals("Piros")) {
            throw new IllegalArgumentException(
                    "A színek 'Sárga' vagy 'Piros' lehet."
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
     * Kér a játékostól egy oszlop számot, ahol le szeretné helyezni a
     * korongot.
     *
     * @param maxColumns A maximális oszlopszám, amibe a játékos
     *                   választhat.
     * @return A játékos által választott oszlop száma.
     */
    @Override
    public int makeMove(final int maxColumns) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(
                playerName + ", válassz oszlopot (0-" + (maxColumns - 1) + "): "
        );
        return scanner.nextInt();
    }
}
