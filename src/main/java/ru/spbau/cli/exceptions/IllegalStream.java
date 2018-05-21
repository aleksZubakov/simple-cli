package ru.spbau.cli.exceptions;

public class IllegalStream extends RuntimeException {
    public IllegalStream() {
        super("Illegal stream");
    }

    public IllegalStream(String message) {
        super(message);
    }
}
