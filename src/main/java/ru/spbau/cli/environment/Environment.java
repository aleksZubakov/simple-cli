package ru.spbau.cli.environment;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    /**
     * {@code symbolTable} is a symbol table of global {@code environment}
     * for holding variables
     */
    private final Map<String, String> symbolTable;


    public Environment() {
        symbolTable = new HashMap<>();
    }

    public String getVariableValue(String variable) {
        String val = symbolTable.get(variable);


        return val != null ? val : "";
    }

    /**
     * Adds new variable into environment
     */
    public void addSymbol(String symbol, String value) {
        symbolTable.put(symbol, value);
    }

}
