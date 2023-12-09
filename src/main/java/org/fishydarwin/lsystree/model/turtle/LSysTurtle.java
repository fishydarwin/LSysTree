package org.fishydarwin.lsystree.model.turtle;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;
import org.fishydarwin.lsystree.model.LSysPatternMaterial;
import org.fishydarwin.lsystree.model.exception.InstructionException;
import org.fishydarwin.lsystree.model.exception.InvalidPatternMaterialException;
import org.fishydarwin.lsystree.model.exception.LSysCompilationException;
import org.fishydarwin.lsystree.model.exception.RecursionLimitException;
import org.fishydarwin.lsystree.model.interpreter.LSysInstructionEvaluator;
import org.fishydarwin.lsystree.model.interpreter.ParameterizedInstruction;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class LSysTurtle {

    private final Location startLocation;
    private final Stack<Location> lastLocation;
    private Location currentLocation;
    private final Stack<Location> memorizedLocations;
    private LSysPatternMaterial material; //TODO: fancy material
    private LSysTurtleLineWidth width;

    public LSysTurtle(Location startLocation) throws InvalidPatternMaterialException {
        this.startLocation = startLocation.toBlockLocation();
        currentLocation = startLocation.toBlockLocation();
        lastLocation = new Stack<>();
        lastLocation.push(startLocation.toBlockLocation());
        memorizedLocations = new Stack<>();
        material = new LSysPatternMaterial("stone");
        width = new LSysTurtleLineWidth(0);
    }

    public void memorizeLocation() {
        memorizedLocations.push(currentLocation);
        lastLocation.push(currentLocation);
    }

    public void restoreLocation() {
        currentLocation = memorizedLocations.pop();
        lastLocation.pop();
    }

    public Map<Location, BlockData> trace(ParameterizedInstruction paramInstruction, int iteration, boolean draw)
            throws InstructionException, RecursionLimitException, LSysCompilationException {

        Object performedInstruction = LSysInstructionEvaluator.recursiveEvaluation(
                paramInstruction,
                startLocation, currentLocation, lastLocation.peek(),
                iteration, memorizedLocations.size(), 0);

        if (performedInstruction instanceof LSysPatternMaterial) {
            material = (LSysPatternMaterial) performedInstruction;
            return null;
        }
        if (performedInstruction instanceof LSysTurtleLineWidth) {
            width = (LSysTurtleLineWidth) performedInstruction;
            return null;
        }
        if (performedInstruction instanceof LSysModifiedBlocksWrapper) {
            return ((LSysModifiedBlocksWrapper) performedInstruction).getData();
        }
        if (!(performedInstruction instanceof Location))
            return null;

        Location nextLocation = (Location) performedInstruction;
        nextLocation = nextLocation.toBlockLocation();

        Map<Location, BlockData> previousBlocks = null;
        if (draw) {
            previousBlocks = new HashMap<>();

            Location lastLocationTop = lastLocation.pop();
            Vector direction = nextLocation.clone().subtract(lastLocationTop).toVector().normalize();

            Location iteratedDrawLocation = lastLocationTop.clone();
            sphereAtLocation(iteratedDrawLocation, previousBlocks);

            while (iteratedDrawLocation.distanceSquared(nextLocation) >= 1) {
                iteratedDrawLocation.add(direction);
                sphereAtLocation(iteratedDrawLocation, previousBlocks);
            }

            lastLocation.push(nextLocation);
        }

        currentLocation = nextLocation;
        return previousBlocks;
    }

    private void sphereAtLocation(Location location, Map<Location, BlockData> previousBlocks) {
        double radius = width.getWidth();
        for (double x = -radius; x <= radius; x++)
            for (double y = -radius; y <= radius; y++)
                for (double z = -radius; z <= radius; z++)
                    if (x * x + y * y + z * z <= radius * radius) {
                        Block place = location.clone().add(new Vector(x, y, z)).getBlock();
                        previousBlocks.putIfAbsent(place.getLocation().clone(), place.getBlockData().clone());
                        place.setType(material.selectRandomMaterial(), false);
                    }
    }

}
