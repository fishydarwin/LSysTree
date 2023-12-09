package org.fishydarwin.lsystree.model.interpreter;

import org.fishydarwin.lsystree.model.exception.LSysCompilationException;
import org.fishydarwin.lsystree.model.instructions.LSysInstruction;
import org.fishydarwin.lsystree.registry.LSysInstructionTypeRegistry;

import java.util.ArrayList;
import java.util.List;

public class LSysInstructionCompiler {

    public static final String BracketRegexPatternString = "^(\\w*?)\\((.*?)\\)$";

    public static ParameterizedInstruction compile(String input) throws LSysCompilationException {
        if (!input.matches(BracketRegexPatternString))
            throw new LSysCompilationException(input);

        String[] inputSplit = input.substring(0, input.length() - 1).split("\\(", 2);

        LSysInstruction instruction;
        try {
            instruction = LSysInstructionTypeRegistry.matchToInstruction(inputSplit[0]);
        } catch (Exception ex) {
            throw new LSysCompilationException(input, ex.getMessage());
        }

        List<String> paramsBuilder = new ArrayList<>();
        StringBuilder param = new StringBuilder();
        for (int i = 0; i < inputSplit[1].length(); i++) {
            char token = inputSplit[1].charAt(i);
            if (token == ')') continue;
            if (token == '(') {
                int parentheses = 0;
                for (int j = i + 1; j < inputSplit[1].length(); j++) {
                    if (inputSplit[1].charAt(j) == '(') {
                        parentheses++;
                    } else if (inputSplit[1].charAt(j) == ')') {
                        parentheses--;
                        if (parentheses < 0) {
                            param.append(inputSplit[1], i, j + 1);
                            i = j;
                            break;
                        }
                    }
                }
                continue;
            }
            if (token == ',') {
                paramsBuilder.add(param.toString().trim());
                param = new StringBuilder();
            } else {
                param.append(token);
            }
        }
        paramsBuilder.add(param.toString().trim());
        String[] params = paramsBuilder.toArray(new String[0]);

        return new ParameterizedInstruction(instruction, params);
    }

}
