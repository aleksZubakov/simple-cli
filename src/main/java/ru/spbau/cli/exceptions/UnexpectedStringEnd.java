package ru.spbau.cli.exceptions;

/**
 * Exception for unexpected end of line :)
 */
public class UnexpectedStringEnd extends Exception {
    public UnexpectedStringEnd() {
        super("Unexpected end of string");
    }

    public UnexpectedStringEnd(int position) {
        super("Unexpected end of string at position" + Integer.toString(position));
    }
}

