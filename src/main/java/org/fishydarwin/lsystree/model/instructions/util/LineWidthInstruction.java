package org.fishydarwin.lsystree.model.instructions.util;

import org.bukkit.Location;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterLengthException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;
import org.fishydarwin.lsystree.model.turtle.LSysTurtleLineWidth;

import java.util.function.Function;

public class LineWidthInstruction implements LSysInstruction {

    private LSysTurtleLineWidth width; // TODO: fancy material

    @Override
    public boolean matchToInput(String input) {
        return input.equalsIgnoreCase("line_width");
    }

    @Override
    public void acceptParameters(String[] parameters) throws InstructionException {
        if (parameters.length != 1) throw new InstructionParameterLengthException(1, parameters.length);
        String _width = parameters[0];
        try {
            width = new LSysTurtleLineWidth(Double.parseDouble(_width));
        } catch (IllegalArgumentException ex) {
            throw new InstructionParameterException("width", _width);
        }
    }

    @Override
    public Function<Location, Object> compileInstruction() {
        return (location) -> width;
    }

}
