package ru.spbau.cli.lexer.tokens;

import ru.spbau.cli.visitor.Visitor;

/**
 * Token which represents string with double quotes
 */
public class DoubleQuotedStringToken implements TokenInterface {
    private String value;

    public DoubleQuotedStringToken(String source) {
        value = source;
    }

    /**
     * Get raw value of token
     *
     * @return String
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Set raw value of token
     *
     * @param value
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
