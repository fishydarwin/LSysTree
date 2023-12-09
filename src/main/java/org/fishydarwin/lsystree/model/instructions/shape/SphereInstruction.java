package org.fishydarwin.lsystree.model.instructions.shape;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;
import org.fishydarwin.lsystree.model.LSysPatternMaterial;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterException;
import org.fishydarwin.lsystree.model.exception.InstructionParameterLengthException;
import org.fishydarwin.lsystree.model.exception.InvalidPatternMaterialException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;
import org.fishydarwin.lsystree.model.turtle.LSysModifiedBlocksWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SphereInstruction implements LSysInstruction {

    private LSysPatternMaterial material;
    private double radius;

    @Override
    public boolean matchToInput(String input) {
        return input.equalsIgnoreCase("sphere");
    }

    @Override
    public void acceptParameters(String[] parameters) throws InstructionException {
        if (parameters.length != 2) throw new InstructionParameterLengthException(2, parameters.length);
        String _radius = parameters[0];
        try {
            radius = Double.parseDouble(_radius);
        } catch (Exception ex) { throw new InstructionParameterException("radius", _radius); }
        String _material = parameters[1].toUpperCase();
        try {
            material = new LSysPatternMaterial(_material);
        } catch (InvalidPatternMaterialException ex) {
            throw new InstructionParameterException("material", _material);
        }
    }

    @Override
    public Function<Location, Object> compileInstruction() {
        return (location -> {

            Map<Location, BlockData> modifiedBlocks = new HashMap<>();

            for (double x = -radius; x <= radius; x++)
                for (double y = -radius; y <= radius; y++)
                    for (double z = -radius; z <= radius; z++)
                        if (x * x + y * y + z * z <= radius * radius) {
                            Block place = location.clone().add(new Vector(x, y, z)).getBlock();
                            if (!place.getType().isSolid()) {
                                modifiedBlocks.put(place.getLocation(), place.getBlockData().clone());
                                place.setType(material.selectRandomMaterial(), false);
                            }
                        }

            return new LSysModifiedBlocksWrapper(modifiedBlocks);
        });
    }
}
