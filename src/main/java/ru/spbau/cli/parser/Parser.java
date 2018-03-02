package ru.spbau.cli.parser;

import ru.spbau.cli.helpers.Commands;
import ru.spbau.cli.lexer.tokens.*;
import ru.spbau.cli.parser.lexems.*;
import ru.spbau.cli.visitor.Visitor;

import java.util.LinkedList;
import java.util.List;

/**
 * Parser for parsing more meaningful lexems from given tokens
 */
public class Parser implements Visitor {
    private List<TokenInterface> tokens;
    private List<LexemInterface> lexems;

    public Parser(List<TokenInterface> tokens) {
        this.tokens = tokens;
        lexems = new LinkedList<>();
    }

    /**
     * Parses lexems
     *
     * @return {@code List<LexemInterface>}
     */
    public List<LexemInterface> parse() {
        for (TokenInterface tok : tokens) {
            tok.accept(this);
        }

        return lexems;
    }

    @Override
    public void visit(PipeToken tok) {
        lexems.add(new Pipe(tok));
    }

    @Override
    public void visit(SingleQuotedStringToken tok) {
        lexems.add(new Argument(tok.getValue()));
    }

    @Override
    public void visit(DoubleQuotedStringToken tok) {
        if (lexems.isEmpty() || lexems.get(lexems.size() - 1) instanceof Pipe) {
            lexems.add(new Command(tok.getValue()));
            return;
        }

        lexems.add(new Argument(tok));
    }

    @Override
    public void visit(UnquotedStringToken tok) {
        if (lexems.isEmpty() || lexems.get(lexems.size() - 1) instanceof Pipe) {
            lexems.add(new Command(tok.getValue()));
            return;
        }

        lexems.add(new Argument(tok));
    }

    @Override
    public void visit(AssignmentToken tok) {
        lexems.add(new Assignment(tok.getLvalue(), tok.getRvalue()));
    }
}
