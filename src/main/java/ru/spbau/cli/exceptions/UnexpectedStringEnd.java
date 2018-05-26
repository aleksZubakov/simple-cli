package ru.spbau.cli.exceptions;

/**
 * Exception for situation when unexpected end of line found:)
 */
public class UnexpectedStringEnd extends RuntimeException {
    public UnexpectedStringEnd() {
        super("Unexpected end of string");
    }

    public UnexpectedStringEnd(int position) {
        super("Unexpected end of string at position" + Integer.toString(position));
    }
}



