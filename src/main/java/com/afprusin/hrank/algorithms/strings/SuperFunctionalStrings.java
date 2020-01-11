package com.afprusin.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class SuperFunctionalStrings {

    private static final long MODULO_CUTOFF = 1000000007;

    private static class InputWindow {
        private int[] hashHistory;
        private int length;
        private int hashCode;

        public InputWindow(int[] hashHistory, int length) {
            this.hashHistory = hashHistory;
            this.length = length;
            this.hashCode = hashHistory[length - 1];
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object other) {
            // For now, let's assume comparisons will only happen between InputWindows
            final InputWindow otherSafe = (InputWindow)other;
            if (otherSafe.length != this.length) {
                return false;
            }
            for (int i = length - 1; i >= 0; i--) {
                if (this.hashHistory[i] != otherSafe.hashHistory[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class ResultStore {
        final BigInteger bigModulo = BigInteger.valueOf(MODULO_CUTOFF);
        int[][] results;

        public ResultStore(int maxInputLength) {
            this.results = new int[maxInputLength][26];

            BigInteger currentValue;
            for (int i = 1; i <= maxInputLength; i++) {
                BigInteger currentMultiple = BigInteger.valueOf(i);
                currentValue = BigInteger.ONE;
                for (int j = 1; j <= 26; j++)  {
                    currentValue = currentValue.multiply(currentMultiple);

                    results[i - 1][j - 1] = currentValue.mod(bigModulo).intValueExact();
                }
            }
        }

        public int getFunctionResult(int inputLength, int letters) {
            return results[inputLength - 1][letters - 1];
        }
    }

    public List<Integer> solveProblem() {
        List<byte[]> problemInput = getInput();
        final int maxStringLength = problemInput.stream()
                .map(input -> input.length)
                .max(Integer::compareTo)
                .orElseThrow(IllegalStateException::new);
        ResultStore results = new ResultStore(maxStringLength);

        return getFunctionSum(problemInput, results);
    }

    private List<byte[]> getInput() {
        List<byte[]> input = new ArrayList<>();
        int inputLines = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            inputLines = Integer.parseInt(reader.readLine());

            for (int i = 0; i < inputLines; i++) {
                final String line = reader.readLine();
                final byte[] convertedLine = new byte[line.length()];
                for (int j = 0; j < line.length(); j++) {
                    convertedLine[j] = (byte)(line.charAt(j) - 97);
                }
                input.add(convertedLine);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read input.", e);
        }

        return input;
    }

    private List<Integer> getFunctionSum(List<byte[]> inputCases, ResultStore results) {
        return inputCases.stream()
                .map(currentCase -> getSumForSingleCase(currentCase, results))
                .collect(Collectors.toList());
    }

    private int getSumForSingleCase(byte[] singleCase, ResultStore results) {
        int currentSum = 0;
        Set<InputWindow> usedSubstrings = new HashSet<>();

        int[] characterCounts = new int[singleCase.length];

        for (int i = 0; i < singleCase.length; i++) {
            //TODO: move to method
            // Get the sequential hash values for each character in the string
            int[] hashcodes = new int[singleCase.length - i];
            int currentHash = 1;
            boolean[] usedCharacters = new boolean[26];
            int usedCharacterCount = 0;

            // Pre-load values for this parent substring
            for (int j = i; j < singleCase.length; j++) {
                final byte currentCharacter = singleCase[j];
                currentHash = currentHash * 31 + currentCharacter;
                hashcodes[j - i] = currentHash;
                if (!usedCharacters[currentCharacter]) {
                    usedCharacters[currentCharacter] = true;
                    usedCharacterCount++;
                }
                characterCounts[j] = usedCharacterCount;
            }

            for (int j = singleCase.length; j > i; j--) {
                if (alreadyProcessed(usedSubstrings, hashcodes, j - i)) {
                    // TODO: Need to make sure these values match up; I've been one-off so many times it's stupid
                    currentSum += results.getFunctionResult(j - i, characterCounts[j - 1]);
                    if (currentSum > MODULO_CUTOFF) {
                        currentSum -= MODULO_CUTOFF;
                    }
                } else {
                    break;
                }
            }
        }

        System.out.println(currentSum);

        return currentSum;
    }

    private boolean alreadyProcessed(Set<InputWindow> usedSubstrings, int[] hashcodes, int length) {
        return usedSubstrings.add(new InputWindow(hashcodes, length));
    }

    public static void main(String[] args) {
        SuperFunctionalStrings superFunctionalStrings = new SuperFunctionalStrings();
        superFunctionalStrings.solveProblem().forEach(System.out::println);
    }

}