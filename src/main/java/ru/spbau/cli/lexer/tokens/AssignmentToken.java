package ru.spbau.cli.lexer.tokens;

import ru.spbau.cli.visitor.Visitor;

/**
 * Token which represent assignment:
 * {@code <lvalue>=<rvalue>}
 */
public class AssignmentToken implements TokenInterface {
    private String lvalue;
    private String rvalue;

    public AssignmentToken(String varName, String varValue) {
        lvalue = varName;
        rvalue = varValue;
    }

    /**
     * Returns value in "lvalue=rvalue" format
     * @return
     */
    @Override
    public String getValue() {
        return lvalue + "=" + rvalue;
    }

    public String getRvalue() {
        return rvalue;
    }
    public String getLvalue() {
        return lvalue;
    }

    @Override
    public void setValue(String val) {
    /*TODO rewrite implementation*/
        rvalue = val;
    }

    @Override
    public void accept(Visitor v){
        v.visit(this);
    }
}
