package org.fishydarwin.lsystree.model.exception;

public class InvalidPatternMaterialException extends InstructionException {
    public InvalidPatternMaterialException(String pattern) {
        super("Not a valid material pattern: " + pattern);
    }
}
