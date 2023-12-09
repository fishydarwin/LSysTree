package org.fishydarwin.lsystree.model.instructions.real;

import org.bukkit.Location;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterLengthException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;

import java.util.function.Function;

public class SinInstruction implements LSysInstruction {
    private double operand;

    @Override
    public boolean matchToInput(String input) {
        return input.equalsIgnoreCase("sin");
    }

    @Override
    public void acceptParameters(String[] parameters) throws InstructionException {
        if (parameters.length != 1) throw new InstructionParameterLengthException(1, parameters.length);
        String _operand = parameters[0];
        try {
            operand = Double.parseDouble(_operand);
        } catch (Exception ex) { throw new InstructionParameterException("operand", _operand); }
    }

    @Override
    public Function<Location, Object> compileInstruction() {
        return (location -> Math.sin(operand));
    }
}
