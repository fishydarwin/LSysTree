package org.fishydarwin.lsystree.model.instructions.util;

import org.bukkit.Location;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterLengthException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;

import java.util.Random;
import java.util.function.Function;

public class RandomInstruction implements LSysInstruction {

    private static final Random random = new Random();
    private double from;
    private double to;

    @Override
    public boolean matchToInput(String input) {
        return input.equalsIgnoreCase("random");
    }

    @Override
    public void acceptParameters(String[] parameters) throws InstructionException {
        if (parameters.length != 2) throw new InstructionParameterLengthException(2, parameters.length);

        String _from = parameters[0];
        try {
            from = Double.parseDouble(_from);
        } catch (Exception ex) { throw new InstructionParameterException("from", _from); }

        String _to = parameters[1];
        try {
            to = Double.parseDouble(_to);
        } catch (Exception ex) { throw new InstructionParameterException("to", _to); }
    }

    @Override
    public Function<Location, Object> compileInstruction() {
        return (location -> random.nextDouble(from, to));
    }

}
