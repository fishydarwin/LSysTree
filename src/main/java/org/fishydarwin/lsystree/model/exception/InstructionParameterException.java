package org.fishydarwin.lsystree.model.exception;

public class InstructionParameterException extends InstructionException {
    public InstructionParameterException(String parameterName, String parameterValue) {
        super("Invalid parameter value: " + parameterName + " cannot be " + parameterValue);
    }
}
