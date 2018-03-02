package ru.spbau.cli.parser.lexems;

/**
 * Represents assignment
 */
public class Assignment implements LexemInterface {
    private String var;
    private String value;

    public Assignment(String varName, String varValue) {
        var = varName;
        value = varValue;
    }

    public String getValue() {
        return value;
    }

    public String getVar() {
            return var;
    }
}
