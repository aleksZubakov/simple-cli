package ru.spbau.cli.lexer.tokens;

import ru.spbau.cli.visitor.Visitor;

/**
 * Token which represents sinqle quoted string
 */
public class SingleQuotedStringToken implements TokenInterface {
    private String value;

    public SingleQuotedStringToken(String source) {
        value = source;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String val) {
        value = val;
    }



    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
