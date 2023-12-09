package org.fishydarwin.lsystree.model.exception;

public class RecursionLimitException extends Exception {
    public RecursionLimitException(int depth) {
        super("Maximum recursion depth reached: " + depth);
    }
}
