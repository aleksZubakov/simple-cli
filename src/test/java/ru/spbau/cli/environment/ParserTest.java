package ru.spbau.cli.environment;

import org.junit.Test;
import ru.spbau.cli.lexer.tokens.TokenInterface;
import ru.spbau.cli.lexer.tokens.UnquotedStringToken;
import ru.spbau.cli.parser.Parser;
import ru.spbau.cli.parser.lexems.Command;
import ru.spbau.cli.parser.lexems.LexemInterface;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ParserTest {
    @Test
    public void testCommandParse() {
        List<TokenInterface> testString = new LinkedList<>();
        testString.add(new UnquotedStringToken("echo"));


        List<LexemInterface> res = new Parser(testString).parse();

        assertTrue(res.get(0) instanceof Command);
    }

    @Test
    public void simpleParserTest() {
        List<TokenInterface> testString = new LinkedList<>();
        testString.add(new UnquotedStringToken("echo"));

        Parser parser = new Parser(testString);

        List<LexemInterface> res = parser.parse();

        assertTrue(res.get(0) instanceof Command);

    }

}
