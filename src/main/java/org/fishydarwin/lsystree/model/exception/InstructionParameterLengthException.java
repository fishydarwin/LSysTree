package org.fishydarwin.lsystree.model.exception;

public class InstructionParameterLengthException extends InstructionException {
    public InstructionParameterLengthException(int requiredLength, int givenLength) {
        super("Invalid number of parameters: needed " + requiredLength + " but gave " + givenLength);
    }
}
