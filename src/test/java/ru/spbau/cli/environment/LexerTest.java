package ru.spbau.cli.environment;

import org.junit.Test;
import ru.spbau.cli.exceptions.UnexpectedStringEnd;
import ru.spbau.cli.lexer.Lexer;
import ru.spbau.cli.lexer.tokens.AssignmentToken;
import ru.spbau.cli.lexer.tokens.TokenInterface;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {
    @Test
    public void testDoubleQuotedStringParser() {
        String testString = "\"value\"";

        Lexer lexer = new Lexer(testString);

        List<TokenInterface> res = lexer.parse();
        assertEquals(1, res.size());
        assertEquals("value", res.get(0).getValue());
    }

    @Test(expected = UnexpectedStringEnd.class)
    public void testUnexpectedStringEndException() throws UnexpectedStringEnd {
        String testString = "\"value";
        Lexer lexer = new Lexer(testString);
        lexer.parse();

    }

    @Test
    public void testSingleQuotedStringParser() {
        String testString = "\'value\'";

        Lexer lexer = new Lexer(testString);

        List<TokenInterface> res = lexer.parse();
        assertEquals(1, res.size());
        assertEquals("value", res.get(0).getValue());
    }

    @Test
    public void testSequenceDoubleQuotedString() throws UnexpectedStringEnd {
        String testString = "\"value\"\"value\"";

        Lexer lexer = new Lexer(testString);

        List<TokenInterface> res = lexer.parse();
        assertEquals(2, res.size());
        assertEquals("value", res.get(0).getValue());
    }


    @Test
    public void testAnotherCommand() throws UnexpectedStringEnd {
        String testString = "a=\"$x\" | echo \"another value\"";

        Lexer lexer = new Lexer(testString);
        List<TokenInterface> res = lexer.parse();

        assertNotNull(res);
        assertEquals(4, res.size());

        assertTrue(res.get(0) instanceof AssignmentToken);

        assertEquals("a=\"$x\"", res.get(0).getValue());
    }

    @Test
    public void testAssignmentToken() throws UnexpectedStringEnd {
        String testString = "x=\"asdads\"";
        Lexer lexer = new Lexer(testString);

        List<TokenInterface> res = lexer.parse();

        assertEquals(res.size(), 1);
        assertTrue(res.get(0) instanceof AssignmentToken);
    }

    @Test
    public void testFewDoubleQuotedStrings() throws UnexpectedStringEnd {
        String testString = "x=\"val1\"\"val1\"\"val1\"";

        List<TokenInterface> res = new Lexer(testString).parse();

        assertEquals(1, res.size());

        assertTrue(res.get(0) instanceof AssignmentToken);
        System.out.println(res.get(0).getValue());
    }

}
