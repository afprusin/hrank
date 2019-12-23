package com.afprusin.hrank.algorithms.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LilysHomework {

    private int getMinimumSwaps() {
        List<Integer> unsorted = getInput();

        return Math.min(
                getNeededSwapsForSortIndexes(getSortIndexesAscending(unsorted)),
                getNeededSwapsForSortIndexes(getSortIndexesDescending(unsorted)));
    }

    private List<Integer> getInput() {
        List<Integer> inputValues;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final String metadataLine = reader.readLine();
            String valuesLine = reader.readLine();

            inputValues = Arrays.stream(valuesLine.split(" "))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read input.", e);
        }

        return inputValues;
    }



    private int getNeededSwapsForSortIndexes(List<Integer> sortIndexes) {
        final boolean[] wasSwapped = new boolean[sortIndexes.size()];

        int swapTotal = 0;
        for (int i = 0; i < wasSwapped.length; i++) {
            if (!wasSwapped[i]) {
                final int loopSize = getLoopSizeAndSetSwapped(i, sortIndexes, wasSwapped);
                swapTotal += (loopSize - 1);
            }
        }

        return swapTotal;
    }

    private List<Integer> getSortIndexesAscending(List<Integer> unsorted) {
        return getSortIndexes(unsorted, getAscendingComparator(unsorted));
    }

    private List<Integer> getSortIndexesDescending(List<Integer> unsorted) {
        return getSortIndexes(unsorted, getDescendingComparator(unsorted));
    }

    private List<Integer> getSortIndexes(List<Integer> unsorted, Comparator<Integer> comparator) {
        return IntStream.range(0, unsorted.size())
                .boxed()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private Comparator<Integer> getAscendingComparator(List<Integer> unsorted) {
        return Comparator.comparing(unsorted::get);
    }

    private Comparator<Integer> getDescendingComparator(List<Integer> unsorted) {
        return Comparator.comparing(unsorted::get).reversed();
    }

    private int getLoopSizeAndSetSwapped(
            final Integer startingIndex,
            final List<Integer> sortedIndexes,
            final boolean[] wasSwapped) {
        wasSwapped[startingIndex] = true;
        Integer nextIndex = sortedIndexes.get(startingIndex);

        int loopSize = 1;

        if (startingIndex.equals(nextIndex)) {
            return loopSize;
        }
        wasSwapped[nextIndex] = true;

        while (!nextIndex.equals(startingIndex)) {
            wasSwapped[nextIndex] = true;
            loopSize++;
            nextIndex = sortedIndexes.get(nextIndex);
        }

        return loopSize;
    }

    public static void main(String[] args) {
        System.out.println(new LilysHomework().getMinimumSwaps());
    }

}
