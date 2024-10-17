package org.connect4.game;
/**
 * Egy adott színű korongot képvisel.
 */
public final class Disc {
    /** A korong színe, amelynek 'sárga' vagy 'piros' kell lennie. */
    private final String discColor;
    /**
     * Létrehoz egy adott színű korongot.
     *
     * @param color a korong színe, amelynek 'sárga' vagy 'piros' kell lennie.
     * @throws IllegalArgumentException ha a szín nem 'sárga' vagy 'piros'.
     */
    public Disc(final String color) {
        if (!color.equals("Sárga") && !color.equals("Piros")) {
            throw new IllegalArgumentException(
                    "A korong színének 'sárga' vagy 'piros'-nak kell lennie."
            );
        }
        this.discColor = color;  // Az érvényes színt eltárolja
    }
    /**
     * Visszaadja a korong színét.
     *
     * @return a korong színe.
     */
    public String getColor() {
        return discColor;  // Visszaadja a discColor változóban tárolt színt
    }
    /**
     * Visszaadja a korong sztring reprezentációját.
     *
     * @return a korong színét sztringként.
     */
    @Override
    public String toString() {
        return discColor;  // A korong színét adja vissza sztringként
    }
}
