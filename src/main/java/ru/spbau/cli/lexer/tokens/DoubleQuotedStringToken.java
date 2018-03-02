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
     * @return raw value
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * set raw value
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
