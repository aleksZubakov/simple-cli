package ru.spbau.cli.lexer.tokens;

import ru.spbau.cli.visitor.Visitor;

/**
 * Token for string without any quote
 */
public class UnquotedStringToken implements TokenInterface {
    private String value;

    public UnquotedStringToken(String source) {
        value = source;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void accept(Visitor v){
        v.visit(this);
    }
}
