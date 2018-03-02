package ru.spbau.cli.parser.lexems;

import ru.spbau.cli.helpers.Quote;
import ru.spbau.cli.lexer.tokens.DoubleQuotedStringToken;
import ru.spbau.cli.lexer.tokens.SingleQuotedStringToken;
import ru.spbau.cli.lexer.tokens.UnquotedStringToken;

/**
 * Represents command argument
 */
public class Argument implements LexemInterface {
    private String value;
    private Quote quote;

    public Argument(String source) {
        value = source;
        quote = Quote.unquoted;
    }

    public Argument(DoubleQuotedStringToken tok) {
        value = tok.getValue();
        quote = Quote.doubleQuoted;
    }
    public Argument(SingleQuotedStringToken tok) {
        value = tok.getValue();
        quote = Quote.singleQuoted;
    }
    public Argument(UnquotedStringToken tok) {
        value = tok.getValue();
        quote = Quote.unquoted;
    }

    public String getValue() {
        return value;
    }

    public Quote getQuote() {
        return quote;
    }
}
