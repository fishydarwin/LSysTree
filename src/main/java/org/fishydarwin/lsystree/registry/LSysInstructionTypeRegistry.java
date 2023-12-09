package org.fishydarwin.lsystree.registry;

import org.fishydarwin.lsystree.model.instructions.LSysInstruction;
import org.fishydarwin.lsystree.registry.exception.UnknownInstructionTypeException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class LSysInstructionTypeRegistry {

    private static final Set<Class<? extends LSysInstruction>> instructions = new HashSet<>();

    public static void registerInstruction(Class<? extends LSysInstruction> clazz) {
        instructions.add(clazz);
    }

    public static LSysInstruction matchToInstruction(String input)
            throws UnknownInstructionTypeException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {

        for (Class<? extends LSysInstruction> clazz : instructions) {
            LSysInstruction instruction = clazz.getDeclaredConstructor().newInstance();
            if (instruction.matchToInput(input)) return instruction;
        }

        throw new UnknownInstructionTypeException(input);
    }

}
