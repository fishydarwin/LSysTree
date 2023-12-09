package org.fishydarwin.lsystree.model.instructions.real;

import org.bukkit.Location;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterLengthException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;

import java.util.function.Function;

public class MulInstruction implements LSysInstruction {
    private double a;
    private double b;

    @Override
    public boolean matchToInput(String input) {
        return input.equalsIgnoreCase("mul");
    }

    @Override
    public void acceptParameters(String[] parameters) throws InstructionException {
        if (parameters.length != 2) throw new InstructionParameterLengthException(2, parameters.length);
        String _a = parameters[0];
        try {
            a = Double.parseDouble(_a);
        } catch (Exception ex) { throw new InstructionParameterException("a", _a); }
        String _b = parameters[1];
        try {
            b = Double.parseDouble(_b);
        } catch (Exception ex) { throw new InstructionParameterException("b", _b); }
    }

    @Override
    public Function<Location, Object> compileInstruction() {
        return (location -> a * b);
    }
}
