package ru.spbau.cli.lexer.tokens;

import ru.spbau.cli.visitor.Visitor;

public interface TokenInterface {

    String getValue();

    void setValue(String val);

    void accept(Visitor v);

}
