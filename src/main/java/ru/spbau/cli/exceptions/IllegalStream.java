package ru.spbau.cli.exceptions;

/**
 * Signals that attempt to work with stream failed
 */
public class IllegalStream extends RuntimeException {
    public IllegalStream() {
        super("Illegal stream");
    }

    public IllegalStream(String message) {
        super(message);
    }
}
