package org.fishydarwin.lsystree.model.interpreter;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.fishydarwin.lsystree.model.LSysAtom;
import org.fishydarwin.lsystree.model.LSysPredicate;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.LSysCompilationException;
import org.fishydarwin.lsystree.model.exception.RecursionLimitException;
import org.fishydarwin.lsystree.model.exception.UnknownAtomException;
import org.fishydarwin.lsystree.model.turtle.LSysTurtle;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LSysProgram {

    private final String axiom;
    private final Set<LSysAtom> atoms;
    private final Set<LSysPredicate> predicates;

    private String lSysString;

    private final Map<Location, BlockData> modifiedLocations;

    public LSysProgram(String axiom, Set<LSysAtom> atoms, Set<LSysPredicate> predicates) {
        this.axiom = axiom;
        this.atoms = atoms;
        this.predicates = predicates;
        lSysString = axiom;
        modifiedLocations = new HashMap<>();
    }

    public LSysProgram(LSysProgram program) {
        this.axiom = program.axiom;
        this.atoms = program.atoms;
        this.predicates = program.predicates;
        lSysString = axiom;
        modifiedLocations = new HashMap<>();
    }

    public LSysProgram(File treeFile) throws LSysCompilationException {
        this.atoms = new HashSet<>();
        this.predicates = new HashSet<>();
        modifiedLocations = new HashMap<>();

        YamlConfiguration data = YamlConfiguration.loadConfiguration(treeFile);

        if (data.isSet("atoms")) {
            for (String atomName : data.getConfigurationSection("atoms").getKeys(false)) {
                char atomChar = atomName.charAt(0);
                LSysAtom atom = new LSysAtom(atomChar);
                for (String instructionString : data.getStringList("atoms." + atomName)) {
                    atom.addInstruction(LSysInstructionCompiler.compile(instructionString));
                }
                atoms.add(atom);
            }
        }

        if (data.isSet("predicates")) {
            for (String predicateName : data.getConfigurationSection("predicates").getKeys(false)) {
                char predicateChar = predicateName.charAt(0);
                LSysPredicate predicate =
                        new LSysPredicate(predicateChar,
                                data.getString("predicates." + predicateName,
                                "" + predicateChar));
                predicates.add(predicate);
            }
        }

        axiom = data.getString("axiom", "");
        lSysString = axiom;
    }

    public void resetIterations() {
        clearTrace();
        lSysString = axiom;
        iteration = 0;
    }

    private int iteration = 0;
    public void nextIteration() throws UnknownAtomException {
        StringBuilder newLSysString = new StringBuilder();
        mainLoop:
        for (int i = 0; i < lSysString.length(); i++) {
            char atomChar = lSysString.charAt(i);
            if (atomChar == '[' || atomChar == ']') {
                newLSysString.append(atomChar);
            } else {
                for (LSysPredicate predicate : predicates) {
                    if (predicate.getOperand() == atomChar) {
                        newLSysString.append(predicate.getResult());
                        continue mainLoop;
                    }
                }
                throw new UnknownAtomException(atomChar);
            }
        }
        lSysString = newLSysString.toString();
        iteration++;
    }

    public int getIteration() {
        return iteration;
    }

    public String getCurrentString() {
        return lSysString;
    }

    public void startTrace(Location startLocation)
            throws UnknownAtomException, InstructionException,
            RecursionLimitException, LSysCompilationException {

        clearTrace();
        LSysTurtle turtle = new LSysTurtle(startLocation);

        mainLoop:
        for (int i = 0; i < lSysString.length(); i++) {
            char atomChar = lSysString.charAt(i);
            if (atomChar == '[') {
                turtle.memorizeLocation();
            } else if (atomChar == ']') {
                turtle.restoreLocation();
            } else {
                for (LSysAtom atom : atoms) {
                    if (atom.getName() == atomChar) {
                        for (int j = 0; j < atom.getInstructions().size(); j++) {
                            ParameterizedInstruction instruction = atom.getInstructions().get(j);
                            Map<Location, BlockData> trace =
                                    turtle.trace(instruction, iteration,
                                    j >= atom.getInstructions().size() - 1);
                            if (trace != null)
                                for (Map.Entry<Location, BlockData> entry : trace.entrySet())
                                    modifiedLocations.putIfAbsent(entry.getKey(), entry.getValue());
                        }
                        continue mainLoop;
                    }
                }
                throw new UnknownAtomException(atomChar);
            }
        }

    }

    public void clearTrace() {
        for (Map.Entry<Location, BlockData> entry : modifiedLocations.entrySet())
            entry.getKey().getBlock().setBlockData(entry.getValue(), false);
        modifiedLocations.clear();
    }

}
