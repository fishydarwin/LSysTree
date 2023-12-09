package org.fishydarwin.lsystree.model.exception;

public class LSysCompilationException extends Exception {
    public LSysCompilationException(String input) {
        super("Could not compile statement: " + input + " -- syntax error.");
    }
    public LSysCompilationException(String input, String message) {
        super("Could not compile statement: " + input + " -- error: " + message);
    }
}
