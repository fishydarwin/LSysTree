package org.fishydarwin.lsystree.model.instructions;

import org.bukkit.Location;
import org.fishydarwin.lsystree.model.exception.InstructionException;

import java.util.function.Function;

public interface LSysInstruction {

    boolean matchToInput(String input);
    void acceptParameters(String[] parameters) throws InstructionException;

    Function<Location, Object> compileInstruction();

}
