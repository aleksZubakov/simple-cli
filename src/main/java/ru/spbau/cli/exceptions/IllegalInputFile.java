package ru.spbau.cli.exceptions;

/**
 * Signals that file doesn't meet any specific requirements
 */
public class IllegalInputFile extends RuntimeException {
    public IllegalInputFile() {
        super("Illegal input file");
    }

    public IllegalInputFile(String message) {
        super(message);
    }
}
