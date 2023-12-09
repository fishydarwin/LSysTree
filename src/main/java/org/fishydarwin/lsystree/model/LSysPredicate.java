package org.fishydarwin.lsystree.model;

import java.util.Objects;

public class LSysPredicate {

    private final char operand;
    private final String result;

    public LSysPredicate(char operand, String result) {
        this.operand = operand;
        this.result = result;
    }

    public char getOperand() {
        return operand;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LSysPredicate predicate = (LSysPredicate) o;
        return operand == predicate.operand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operand, result);
    }

}
