package ru.spbau.cli.exceptions;

public class IllegalInputFile extends RuntimeException {
    public IllegalInputFile() {
        super("Illegal input file");
    }

    public IllegalInputFile(String message) {
        super(message);
    }
}
