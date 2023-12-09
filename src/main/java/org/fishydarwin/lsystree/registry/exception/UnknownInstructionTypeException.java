package org.fishydarwin.lsystree.registry.exception;

public class UnknownInstructionTypeException extends Exception {
    public UnknownInstructionTypeException(String input) {
        super("Unknown instruction: " + input);
    }
}
