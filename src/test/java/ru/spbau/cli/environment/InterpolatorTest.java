package ru.spbau.cli.environment;

import org.junit.Test;
import ru.spbau.cli.interploator.Interpolator;
import ru.spbau.cli.lexer.Lexer;
import ru.spbau.cli.lexer.tokens.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class InterpolatorTest {
    @Test
    public void interpolationSingleVariablesTest() throws Exception {
        Environment env = new Environment();

        env.addSymbol("abc", "2");
        env.addSymbol("cd", "1");
        env.addSymbol("e", "echo");

        List<TokenInterface> testString = new LinkedList<>();
        testString.add(new UnquotedStringToken("$abc"));

        List<TokenInterface> res1 = new Interpolator(env).interpolate(testString);
        String simpleTestValue1 = "2";

        assertEquals(simpleTestValue1, res1.get(0).getValue());
    }

    @Test
    public void testUnquotedString() throws Exception {
        Environment env = new Environment();

        env.addSymbol("x", "ho");

        List<TokenInterface> testString = new LinkedList<>();
        testString.add(new UnquotedStringToken("ec\"$x\""));

        List<TokenInterface> res1 = new Interpolator(env).interpolate(testString);
        String simpleTestValue1 = "echo";

        assertEquals(simpleTestValue1, res1.get(0).getValue());

    }

    @Test
    public void testMixStrings() throws Exception {
        Environment env = new Environment();

        env.addSymbol("firstVariable", "2");
        env.addSymbol("secondVariable", "1");
        env.addSymbol("thirdVar", "ec");
        env.addSymbol("fourthVar", "ho");

        List<TokenInterface> testString = new LinkedList<>();
        testString.add(new UnquotedStringToken("$firstVariable"));
        testString.add(new DoubleQuotedStringToken("$thirdVar$fourthVar"));
        testString.add(new UnquotedStringToken("$secondVariable$thirdVar$fourthVar"));
        testString.add(new UnquotedStringToken("$firstVariable,$secondVariable"));

        List<TokenInterface> expectedTokens = new LinkedList<>();
        expectedTokens.add(new UnquotedStringToken("2"));
        expectedTokens.add(new DoubleQuotedStringToken("echo"));
        expectedTokens.add(new UnquotedStringToken("1echo"));
        expectedTokens.add(new UnquotedStringToken("2,1"));


        List<TokenInterface> actualTokens = new Interpolator(env).
                interpolate(testString);


        assertEquals(expectedTokens.size(), actualTokens.size());

        for (int i = 0; i < actualTokens.size(); i++) {
            assertEquals(expectedTokens.get(i).getValue(), actualTokens.get(i).getValue());

        }
    }

    @Test
    public void testFewDoubleQuotedStrings() throws Exception {
        Environment env = new Environment();

        List<TokenInterface> testString = new LinkedList<>();


        testString.add(new AssignmentToken("x", "\"val1\"\"val1\"\"val1\""));

        List<TokenInterface> actualTokens = new Interpolator(env).interpolate(testString);

        assertEquals("val1val1val1", ((AssignmentToken)actualTokens.get(0)).getRvalue());

    }

}
