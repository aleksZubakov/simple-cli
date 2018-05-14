package ru.spbau.cli.environment;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    /**
     * {@code symbolTable} is a symbol table of global {@code environment}
     * for holding variables
     */
    private Map<String, String> symbolTable;


    public Environment() {
        symbolTable = new HashMap<>();
    }

    /**
     * Returns value of given variable or returns empty string
     *
     * @param variable
     * @return
     */
    public String getVariableValue(String variable) {
        String val = symbolTable.get(variable);

        if (val != null)
            return val;
        else
            return "";
    }

    /**
     * Adds new variable into environment
     *
     * @param symbol
     * @param value
     */
    public void addSymbol(String symbol, String value) {
        symbolTable.put(symbol, value);
    }

}
