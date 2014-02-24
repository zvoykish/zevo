package com.zvoykish.zevo.utils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:02:35
 */
public class Randomizer {
    /**
     * Returns n random numbers in the range [low, high]
     *
     * @param n    Number of elements required
     * @param low  Lowest value possible (inclusive)
     * @param high Highest value possible (inclusive)
     * @return List of n random elements in the range [low, high]
     */
    public static List<Integer> randomize(int n, int low, int high) {
        Vector<Integer> bag = new Vector<>();
        for (int i = low; i <= high; i++) {
            bag.add(i);
        }

        List<Integer> random = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int randomIndex = (int) (Math.random() * bag.size());
            random.add(bag.remove(randomIndex));
        }
        return random;
    }

    /**
     * Returns a map of n random map locations (x -> set of y's)
     *
     * @param n      Number of elements required
     * @param width  width of the map
     * @param height Height of the map
     * @return Map of locations (x -> set of y's)
     */
    public static Map<Integer, Set<Integer>> randomizeMapLocations(int n, int width, int height) {
        Map<Integer, Set<Integer>> resultLocations = new HashMap<>();
        List<Integer> flatCoordinates = randomize(n, 0, (width * height) - 1);
        for (Integer flatCoordinate : flatCoordinates) {
            int x = flatCoordinate / height;
            int y = flatCoordinate % height;
            Set<Integer> xList = resultLocations.get(x);
            if (xList == null) {
                xList = new HashSet<>();
                resultLocations.put(x, xList);
            }
            xList.add(y);
        }
        return resultLocations;
    }

    /**
     * Randomizes the list elements
     *
     * @param list List to be affected
     * @param <T>  Type of elements in the list
     */
    public static <T> void randomize(List<T> list) {
        List<Integer> randomIndices = randomize(list.size(), 0, list.size() - 1);
        List<T> cloned = new ArrayList<>();
        for (Integer index : randomIndices) {
            cloned.add(list.get(index));
        }
        list.clear();
        list.addAll(cloned);
    }

    /**
     * Returns a random number between 0 and <i>distribution.length - 1</i><br/>
     * That number represents a random index in the array, according to the distribution probabilities defined in <i>distribution</i><br/>
     *
     * @param distribution The distribution probabilities array
     * @return Random index according to the distribution
     */
    public static int getRandomIndexForDistribution(double[] distribution) {
        double sum = 0;
        double[] accumulatedDistribution = new double[distribution.length];
        for (int i = 0; i < distribution.length; i++) {
            sum += distribution[i];
            accumulatedDistribution[i] = sum;
        }
        double chance = Math.random() * sum;
        double last = 0;
        int index = 0;
        while (index < accumulatedDistribution.length) {
            if (chance >= last && chance < accumulatedDistribution[index]) {
                return index;
            }
            else {
                last = accumulatedDistribution[index];
                index++;
            }
        }
        return -1;
    }
}
