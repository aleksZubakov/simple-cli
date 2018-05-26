package ru.spbau.cli.lexer.tokens;

import ru.spbau.cli.visitor.Visitor;

/**
 * This is interface for high-level lexer token
 */
public interface TokenInterface {

    /**
     * Returns the raw value of given token.
     * Raw value is part of original string, which forms a token
     */
    String getValue();


    /**
     * Set the value of given token.
     * Useful for setting unusual value
     */
    void setValue(String val);

    /**
     * Abstract method, which should be written for implementing
     * visitor pattern.
     */
    void accept(Visitor v);

}
