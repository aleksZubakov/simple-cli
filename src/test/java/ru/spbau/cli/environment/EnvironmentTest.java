package ru.spbau.cli.environment;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvironmentTest {
    @Test
    public void EnvironmentAddGetSymbol() {
        Environment globalEnv = new Environment();

        globalEnv.addSymbol("abc", "125");
        assertEquals(globalEnv.getVariableValue("abc"), "125");

        globalEnv.addSymbol("abc", "123423");
        assertEquals(globalEnv.getVariableValue("abc"), "123423");

        globalEnv.addSymbol("a1", "lool");

    }
}
