package ru.spbau.cli.visitor;

import ru.spbau.cli.lexer.tokens.*;

public interface Visitor {
    void visit(PipeToken tok);
    void visit(SingleQuotedStringToken tok);
    void visit(DoubleQuotedStringToken tok);
    void visit(UnquotedStringToken tok);
    void visit(AssignmentToken tok);
}
