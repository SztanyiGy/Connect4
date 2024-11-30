package org.connect4.player;

/**
 * A Player interfész képviseli a Connect 4 játékosokat.
 * Minden játékosnak implementálnia kell ezt az interfészt,
 * hogy biztosítsa a megfelelő metódusokat a játék során.
 */
public interface Player {

    /**
     * Visszaadja a játékos nevét.
     *
     * @return A játékos neve.
     */
    String getName();

    /**
     * Visszaadja a játékos színét.
     *
     * @return A játékos színe.
     */
    String getColor();

    /**
     * A játékos lépése, amely meghatározza, hogy melyik oszlopba
     * szeretne egy követ elhelyezni.
     *
     * @param maxColumns A maximálisan elérhető oszlopok száma.
     * @return Az oszlop indexe, amelybe a játékos lépést tesz.
     */
    int makeMove(int maxColumns);
}
