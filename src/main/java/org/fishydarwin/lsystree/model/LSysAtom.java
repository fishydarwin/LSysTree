package org.fishydarwin.lsystree.model;

import org.fishydarwin.lsystree.model.interpreter.ParameterizedInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LSysAtom {

    private final char name;
    private final List<ParameterizedInstruction> instructions;

    public LSysAtom(char name) {
        this.name = name;
        instructions = new ArrayList<>();
    }

    public char getName() { return name; }
    public List<ParameterizedInstruction> getInstructions() { return instructions; }

    public void addInstruction(ParameterizedInstruction instruction) {
        instructions.add(instruction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LSysAtom lSysAtom = (LSysAtom) o;
        return name == lSysAtom.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
