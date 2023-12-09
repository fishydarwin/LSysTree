package org.fishydarwin.lsystree.model.exception;

public class UnknownAtomException extends Exception {
    public UnknownAtomException(char atomChar) {
        super("Unknown atom: " + atomChar);
    }
}
