package ru.spbau.cli.lexer;

import ru.spbau.cli.exceptions.UnexpectedStringEnd;
import ru.spbau.cli.helpers.Quote;
import ru.spbau.cli.lexer.tokens.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Lexer for tokenize given string into simple tokens
 */
public class Lexer {

    private int currentPosition;

    private String source;

    public Lexer(String source) {
        currentPosition = 0;
        this.source = source;
    }

    /**
     * Tries to split given {@code source} into tokens
     * @return list of splited tokens
     * @throws UnexpectedStringEnd
     */
    public List<TokenInterface> parse() throws UnexpectedStringEnd {

        List<TokenInterface> result = new LinkedList<>();

        while (currentPosition < source.length()) {
            switch (source.charAt(currentPosition)) {
                case ' ':
                    currentPosition++;
                    break;

                case '\"':
                    result.add(parseQuotedString(Quote.doubleQuoted));
                    break;

                case '\'':
                    result.add(parseQuotedString(Quote.singleQuoted));
                    break;

                case '|':
                    result.add(new PipeToken());
                    currentPosition++;
                    break;

                default:
                    result.add(parseAssignmentToken());
            }
        }
        return result;
    }

    /**
     * Helper for parsing string with quote
     *
     * @param quote single or double quote
     * @return DoubleQuotedStringToken or SingleQuotedStringToken
     * @throws UnexpectedStringEnd
     */
    private TokenInterface parseQuotedString(Quote quote) throws UnexpectedStringEnd {
        StringBuilder result = new StringBuilder();

        currentPosition++;

        while (currentPosition < source.length()) {
            if (source.charAt(currentPosition) == quote.getSymbol()) {
                break;
            }

            result.append(source.charAt(currentPosition));
            currentPosition++;
        }

        if (currentPosition == source.length() || source.charAt
                (currentPosition) != quote.getSymbol()) {
            throw new UnexpectedStringEnd(currentPosition);
        }

        currentPosition++;

        if (quote.equals(Quote.doubleQuoted)) {
            return new DoubleQuotedStringToken(result.toString());
        } else {
            return new SingleQuotedStringToken(result.toString());
        }
    }

    /**
     * Helper for parsing unquotedString
     *
     * @return UnquotedStringToken
     * @throws UnexpectedStringEnd
     */
    private TokenInterface parseUnquotedString() throws UnexpectedStringEnd {

        StringBuilder result = new StringBuilder();
        while (currentPosition < source.length()) {
            char currentSymbol = source.charAt(currentPosition);
            String parsedValue;
            switch (currentSymbol) {
                case '\'':
                    parsedValue = parseQuotedString(Quote.singleQuoted).getValue();
                    parsedValue = "\'" + parsedValue + "\'";
                    result.append(parsedValue);
                    break;
                case '\"':
                    parsedValue = parseQuotedString(Quote.doubleQuoted).getValue();
                    parsedValue = "\"" + parsedValue + "\"";
                    result.append(parsedValue);
                    break;
                case ' ':
                    currentPosition++;
                    return new UnquotedStringToken(result.toString());
                case '|':
                    return new UnquotedStringToken(result.toString());
                default:
                    result.append(currentSymbol);
                    currentPosition++;
            }
        }

        return new UnquotedStringToken(result.toString());
    }


    /**
     * Method tries to parse assignment starting at current position
     *
     * @return {@code TokenInterface} which is {@code AssignmentToken} in
     * case of normally parsed assignment token or {@code UnquotedStringToken}
     * otherwise.
     */
    private TokenInterface parseAssignmentToken() throws UnexpectedStringEnd {
        TokenInterface tok = parseUnquotedString();

        String value = tok.getValue();
        StringBuilder res = new StringBuilder();
        char curSymbol;
        int pos = 0;

        while (pos < value.length()) {
            curSymbol = value.charAt(pos);

            if (curSymbol == '=') {
                if (res.length() == 0) {
                    return tok;
                }

                return new AssignmentToken(res.toString(),
                        tok.getValue().substring(pos + 1));
            }

            if (!Character.isAlphabetic(curSymbol) && !Character.isDigit(curSymbol)) {
                return tok;
            }

            res.append(curSymbol);
            pos++;
        }

        return tok;
    }
}
