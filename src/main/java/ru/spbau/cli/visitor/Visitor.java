package ru.spbau.cli.visitor;

import ru.spbau.cli.lexer.tokens.*;

public interface Visitor {
    default void visit(PipeToken tok) {
    }

    default void visit(SingleQuotedStringToken tok) {
    }

    default void visit(DoubleQuotedStringToken tok) {
    }

    default void visit(UnquotedStringToken tok) {
    }

    default void visit(AssignmentToken tok) {
    }
}
