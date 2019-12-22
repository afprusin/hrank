package com.afprusin.hrank.algorithms.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LilysHomework {

    private int performSolution() {
        return getNeededSwaps(getInput());
    }

    private List<Integer> getInput() {
        List<Integer> inputValues;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String metadataLine = reader.readLine();
            String valuesLine = reader.readLine();

            inputValues = Arrays.stream(valuesLine.split(" "))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read input.", e);
        }

        return inputValues;
    }

    private int getNeededSwaps(List<Integer> unsorted) {
        List<Integer> sortedIndices = IntStream.range(0, unsorted.size())
                .boxed().sorted(Comparator.comparing(unsorted::get).reversed())
                .collect(Collectors.toList());

        boolean[] wasSwapped = new boolean[sortedIndices.size()];

        int swapTotal = 0;
        for (Integer i = 0; i < wasSwapped.length; i++) {
            if (!wasSwapped[i]) {
                swapTotal += doSwapChain(i, sortedIndices, wasSwapped);
            }
        }

        return swapTotal;
    }

    private int doSwapChain(
            final Integer startingIndex,
            final List<Integer> sortedIndexes,
            final boolean[] wasSwapped) {
        int swaps = 0;
        wasSwapped[startingIndex] = true;
        Integer nextIndex = sortedIndexes.get(startingIndex);

        System.out.print(startingIndex+","+nextIndex);

        if (startingIndex.equals(nextIndex)) {
            System.out.println();
            return 0;
        }
        wasSwapped[nextIndex] = true;

        do {
            nextIndex = sortedIndexes.get(nextIndex);
            System.out.print(","+nextIndex);

            // DEBUG
            if (wasSwapped[nextIndex]) {
                System.out.println("Index was already true: " + nextIndex);
            }

            wasSwapped[nextIndex] = true;
            swaps++;
        } while (!nextIndex.equals(startingIndex));

        System.out.println();
        return swaps;
    }

    private void checkDuplicates() {
        Set<String> dupeCheck = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Arrays.stream(reader.readLine().split(" "))
                    .map(item -> item.trim())
                    .forEach(item -> {
                        boolean isDupe = dupeCheck.add(item);
                        if (!isDupe) {
                            System.out.println(item + " was duplicate");
                        }
                    });
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        System.out.println(new LilysHomework().performSolution());
        //new LilysHomework().checkDuplicates();
    }

}
