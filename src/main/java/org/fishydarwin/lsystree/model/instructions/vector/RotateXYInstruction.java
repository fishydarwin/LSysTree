package org.fishydarwin.lsystree.model.instructions.vector;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterLengthException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;

import java.util.function.Function;

public class RotateXYInstruction implements LSysInstruction {

    private Vector center;
    private double degrees;

    @Override
    public boolean matchToInput(String input) {
        return input.equalsIgnoreCase("rotate_xy");
    }

    @Override
    public void acceptParameters(String[] parameters) throws InstructionException {
        if (parameters.length != 4) throw new InstructionParameterLengthException(4, parameters.length);

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

        String _degrees = parameters[3];
        try {
            degrees = Double.parseDouble(_degrees);
        } catch (Exception ex) { throw new InstructionParameterException("degrees", _degrees);}

        center = new Vector(x, y, z);
    }

    @Override
    public Function<Location, Object> compileInstruction() {
        return (location ->
                location.toVector().subtract(center)
                        .rotateAroundZ(Math.toRadians(degrees))
                        .add(center)
                        .toLocation(location.getWorld()));
    }
}
