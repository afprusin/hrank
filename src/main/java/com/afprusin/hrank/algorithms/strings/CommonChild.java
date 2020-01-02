package com.afprusin.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommonChild {

    private static class TaskInput {
        String first;
        String second;

        public TaskInput(String first, String second) {
            this.first = first;
            this.second = second;
        }

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }
    }

    private int getLongestChildStringSize() {
        TaskInput input = getInput();

        return findLongestTraversal(input.getFirst(), input.getSecond());
    }

    private int findLongestTraversal(String first, String second) {
        int[][] lengths = new int[first.length() + 1][second.length() + 1];
        int currentMax = 0;

        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    lengths[i][j] = lengths[i - 1][j - 1] + 1;
                } else {
                    lengths[i][j] = Math.max(lengths[i][j - 1], lengths[i - 1][j]);
                }
                if (lengths[i][j] > currentMax) {
                    currentMax = lengths[i][j];
                }
            }
        }

        return currentMax;
    }

    private TaskInput getInput() {
        TaskInput input;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            input = new TaskInput(
                    reader.readLine(),
                    reader.readLine());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read input.", e);
        }

        return input;
    }

    public static void main(String[] args) {
        System.out.println(new CommonChild().getLongestChildStringSize());
    }

}
