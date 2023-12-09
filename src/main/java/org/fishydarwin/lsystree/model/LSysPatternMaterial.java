package org.fishydarwin.lsystree.model;

import org.bukkit.Material;
import org.fishydarwin.lsystree.model.exception.InvalidPatternMaterialException;

import java.util.*;

public class LSysPatternMaterial {

    private final Map<Material, Double> proportions;

    public LSysPatternMaterial(String pattern) throws InvalidPatternMaterialException {
        proportions = new HashMap<>();

        Stack<Material> nonProportionMaterials = new Stack<>();
        double proportionSum = 0;

        try {
            for (String patternComponent : pattern.split(";")) {
                if (patternComponent.contains("%")) {
                    String[] proportionSplit = patternComponent.split("%");
                    double proportion = Double.parseDouble(proportionSplit[0]) / 100.0;
                    if (proportion < 0 || proportion > 100)
                        throw new InvalidPatternMaterialException(pattern);

                    Material material = Material.valueOf(proportionSplit[1].toUpperCase());

                    proportionSum += proportion;
                    if (proportionSum > 1) throw new InvalidPatternMaterialException(pattern);

                    proportions.put(material, proportion);
                } else {
                    Material material = Material.valueOf(patternComponent.toUpperCase());
                    nonProportionMaterials.push(material);
                }
            }
        } catch (Exception ex) {
            throw new InvalidPatternMaterialException(pattern);
        }

        if (proportionSum < 1 && nonProportionMaterials.empty())
            throw new InvalidPatternMaterialException(pattern);

        double proportionDiff = (1 - proportionSum) / nonProportionMaterials.size();
        while (!nonProportionMaterials.empty()) {
            proportions.put(nonProportionMaterials.pop(), proportionDiff);
        }
    }

    private static class RandomBoundPair {
        private final double low;
        private final double high;
        public RandomBoundPair(double low, double high) {
            this.low = low;
            this.high = high;
        }
        public double getLow() { return low; }
        public double getHigh() { return high; }
    }

    private final Random random = new Random();
    public Material selectRandomMaterial() {
        // Thank you Dio for this algorithm.
        if (proportions.size() == 1) {
            return new ArrayList<>(proportions.keySet()).get(0);
        }

        Map<Material, RandomBoundPair> materialChances = new HashMap<>();
        double firstIndex = 0;
        double lastIndex = 0;

        for (Material material : proportions.keySet()) {
            lastIndex += proportions.get(material);
            materialChances.put(material, new RandomBoundPair(firstIndex, lastIndex));
            firstIndex = lastIndex;
        }

        double choice = random.nextDouble();

        for (Material material : materialChances.keySet()) {
            RandomBoundPair pair = materialChances.get(material);
            if (pair.getLow() <= choice && choice <= pair.getHigh()) {
                return material;
            }
        }

        return new ArrayList<>(proportions.keySet()).get(1);
    }

}
