package ru.spbau.cli.interploator;

import ru.spbau.cli.environment.Environment;
import ru.spbau.cli.lexer.tokens.AssignmentToken;
import ru.spbau.cli.lexer.tokens.DoubleQuotedStringToken;
import ru.spbau.cli.lexer.tokens.TokenInterface;
import ru.spbau.cli.lexer.tokens.UnquotedStringToken;
import ru.spbau.cli.visitor.Visitor;

import java.util.List;

/**
 * {@code Interpolator} allows the user to expand variables and remove extra
 * quotes from the received tokens.
 */
public class Interpolator implements Visitor {

    private final Environment env;

    public Interpolator(Environment env) {
        this.env = env;
    }

    /**
     * Interpolate current tokens.
     *
     * @param tokens represents list of token from lexer
     * @return list of interpolated tokens
     */
    public List<TokenInterface> interpolate(List<TokenInterface> tokens) {
        tokens.forEach(tok -> tok.accept(this));
        return tokens;
    }

    @Override
    public void visit(DoubleQuotedStringToken tok) {
        tok.setValue(expandVariables(tok.getValue()));
    }

    @Override
    public void visit(UnquotedStringToken tok) {
        tok.setValue(interpolateUnquotedString(tok.getValue()));
    }

    @Override
    public void visit(AssignmentToken tok) {
        tok.setValue(interpolateUnquotedString(tok.getRvalue()));
    }

    /**
     * Expands variables in given string
     * @param source raw input string
     * @return string with replaced placeholder
     */
    private String interpolateUnquotedString(String source) {
        StringBuilder res = new StringBuilder();

        /*TODO DELETE THIS AWFUL buffer string builder*/
        StringBuilder buf = new StringBuilder();

        char currentSymbol;
        int ind = 0;

        while (ind < source.length()) {
            currentSymbol = source.charAt(ind);
            if (currentSymbol == '\'') {
                if (buf.length() > 0) {
                    res.append(expandVariables(buf.toString()));
                    buf = new StringBuilder();
                }

                ind++;
                int oldInd = ind;
                ind = source.indexOf('\'', ind);
                res.append(source, oldInd, ind);
                ind++;
                continue;
            }

            if (currentSymbol == '\"') {
                if (buf.length() > 0) {
                    res.append(expandVariables(buf.toString()));
                    buf = new StringBuilder();
                }

                ind++;
                int oldInd = ind;
                ind = source.indexOf('\"', ind);
                res.append(expandVariables(source.substring(oldInd, ind)));
                ind++;
                continue;
            }

            buf.append(currentSymbol);
            ind++;
        }

        if (buf.length() > 0) {
            res.append(expandVariables(buf.toString()));
        }
        return res.toString();
    }

    /**
     * Expands variable in current raw string. Checks nothing, ignores
     * quotes.
     *
     * @param source string in which written variables references
     * @return interpolated string
     */
    private String expandVariables(String source) {
        char currentSymbol;
        int ind = 0;

        StringBuilder res = new StringBuilder();

        while (ind < source.length()) {
            currentSymbol = source.charAt(ind);

            if (currentSymbol == '$') {
                int oldInd = ind + 1;
                ind = getVariableEndPosition(source, oldInd);

                res.append(env.getVariableValue(source.substring(oldInd, ind)));
                continue;
            }

            res.append(currentSymbol);
            ind++;
        }
        return res.toString();
    }

    /**
     * Find end of variable-placeholder. Returns index of last symbol
     * of variable name or index of last symbol.
     *
     * @param source
     * @param startPos
     * @return
     */
    private int getVariableEndPosition(String source, int startPos) {
        int endPos = startPos;
        char currentSymbol = source.charAt(endPos);

        while ((Character.isAlphabetic(currentSymbol)
                || Character.isDigit(currentSymbol))
                && endPos < source.length()) {
            currentSymbol = source.charAt(endPos);
            endPos++;
        }

        if (endPos < source.length())
            endPos--;

        return endPos;
    }

}
