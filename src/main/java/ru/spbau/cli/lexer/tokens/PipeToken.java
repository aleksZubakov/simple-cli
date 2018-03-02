package ru.spbau.cli.lexer.tokens;

import ru.spbau.cli.visitor.Visitor;

/**
 * Token which represent pipe symbol
 */
public class PipeToken implements TokenInterface {


    public PipeToken() {}

    public PipeToken(String value) {

    }

    @Override

    public String getValue() {
        return "|";
    }

    @Override
    public void setValue(String val) {

    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
