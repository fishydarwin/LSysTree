package org.fishydarwin.lsystree.model.interpreter;

import org.bukkit.Location;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.LSysCompilationException;
import org.fishydarwin.lsystree.model.exception.RecursionLimitException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;

public class LSysInstructionEvaluator {

    public static final int RecursionLimit = 1000;

    public static Object recursiveEvaluation(ParameterizedInstruction paramInstruction,
                                             Location startLocation,
                                             Location currentLocation,
                                             Location lastLocation,
                                             int iteration,
                                             int stackDepth,
                                             int depth)
            throws InstructionException, LSysCompilationException, RecursionLimitException {

        if (depth > RecursionLimit)
            throw new RecursionLimitException(depth);

        LSysInstruction instruction = paramInstruction.getInstruction();
        String[] params = paramInstruction.getParameters();

        String[] compiledParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {

            String param = params[i];
            param = param.replaceAll("\\$ox", String.valueOf(startLocation.getX()));
            param = param.replaceAll("\\$oy", String.valueOf(startLocation.getY()));
            param = param.replaceAll("\\$oz", String.valueOf(startLocation.getZ()));
            param = param.replaceAll("\\$x", String.valueOf(currentLocation.getX()));
            param = param.replaceAll("\\$y", String.valueOf(currentLocation.getY()));
            param = param.replaceAll("\\$z", String.valueOf(currentLocation.getZ()));
            param = param.replaceAll("\\$lx", String.valueOf(lastLocation.getX()));
            param = param.replaceAll("\\$ly", String.valueOf(lastLocation.getY()));
            param = param.replaceAll("\\$lz", String.valueOf(lastLocation.getZ()));
            param = param.replaceAll("\\$n", String.valueOf(iteration));
            param = param.replaceAll("\\$sd", String.valueOf(stackDepth));

            if (param.matches(LSysInstructionCompiler.BracketRegexPatternString))
                compiledParams[i] =
                        recursiveEvaluation(
                                LSysInstructionCompiler.compile(param),
                                startLocation,
                                currentLocation,
                                lastLocation,
                                iteration,
                                stackDepth,
                                depth + 1)
                        .toString();
            else
                compiledParams[i] = param;
        }

        instruction.acceptParameters(compiledParams);
        return instruction.compileInstruction().apply(currentLocation.clone());
    }

}
