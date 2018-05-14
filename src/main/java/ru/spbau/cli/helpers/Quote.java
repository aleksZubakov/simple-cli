package ru.spbau.cli.helpers;

/**
 * Represents a double, single quote or special non-quoted symbol
 */
public enum Quote {
    singleQuoted('\''),
    doubleQuoted('\"'),
    unquoted('\0');

    private final char symbol;

    Quote(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}