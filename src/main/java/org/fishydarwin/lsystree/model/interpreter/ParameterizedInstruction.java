package org.fishydarwin.lsystree.model.interpreter;

import org.fishydarwin.lsystree.model.instructions.LSysInstruction;

public class ParameterizedInstruction {
    private final LSysInstruction instruction;
    private final String[] parameters;

    public ParameterizedInstruction(LSysInstruction instruction, String[] parameters) {
        this.instruction = instruction;
        this.parameters = parameters;
    }

    public LSysInstruction getInstruction() {
        return instruction;
    }

    public String[] getParameters() {
        return parameters;
    }
}
