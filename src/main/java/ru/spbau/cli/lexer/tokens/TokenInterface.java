package ru.spbau.cli.lexer.tokens;

import ru.spbau.cli.visitor.Visitor;

/**
 * This is interface for high-level lexer token
 */
public interface TokenInterface {

    String getValue();

    void setValue(String val);

    void accept(Visitor v);

}
