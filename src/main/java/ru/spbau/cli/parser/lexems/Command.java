package ru.spbau.cli.parser.lexems;

/**
 * Represents command
 */
public class Command implements LexemInterface {

    private String value;

    public Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
