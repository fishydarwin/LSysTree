package org.fishydarwin.lsystree.model.instructions.util;

import org.bukkit.Location;
import org.fishydarwin.lsystree.model.LSysPatternMaterial;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterLengthException;
import org.fishydarwin.lsystree.model.exception.InvalidPatternMaterialException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;

import java.util.function.Function;

public class MaterialInstruction implements LSysInstruction {

    private LSysPatternMaterial material;

    @Override
    public boolean matchToInput(String input) {
        return input.equalsIgnoreCase("material");
    }

    @Override
    public void acceptParameters(String[] parameters) throws InstructionException {
        if (parameters.length != 1) throw new InstructionParameterLengthException(1, parameters.length);
        String _material = parameters[0].toUpperCase();
        try {
            material = new LSysPatternMaterial(_material);
        } catch (InvalidPatternMaterialException ex) {
            throw new InstructionParameterException("material", _material);
        }
    }

    @Override
    public Function<Location, Object> compileInstruction() {
        return (location) -> material;
    }

}
