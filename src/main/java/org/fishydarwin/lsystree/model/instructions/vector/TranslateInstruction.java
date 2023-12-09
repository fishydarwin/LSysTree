package org.fishydarwin.lsystree.model.instructions.vector;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterLengthException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;

import java.util.function.Function;

public class TranslateInstruction implements LSysInstruction {

    private Vector translation;

    @Override
    public boolean matchToInput(String input) {
        return input.equals("translate");
    }

    @Override
    public void acceptParameters(String[] parameters) throws InstructionException {
        if (parameters.length != 3) throw new InstructionParameterLengthException(3, parameters.length);

        String _x = parameters[0];
        double x;
        try {
            x = Double.parseDouble(_x);
        } catch (Exception ex) { throw new InstructionParameterException("x", _x); }

        String _y = parameters[1];
        double y;
        try {
            y = Double.parseDouble(_y);
        } catch (Exception ex) { throw new InstructionParameterException("y", _y); }

        String _z = parameters[2];
        double z;
        try {
            z = Double.parseDouble(_z);
        } catch (Exception ex) { throw new InstructionParameterException("z", _z); }

        translation = new Vector(x, y, z);
    }

    @Override
    public Function<Location, Object> compileInstruction() {
        return (location -> location.add(translation));
    }

}
