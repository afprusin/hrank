package com.afprusin.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: This still exceeds time/memory limits for H.R. - might not be possible in Java with some extreme creativity

public class SuperFunctionalStrings {

    private static final long MODULO_CUTOFF = 1000000007;

    private static class TextMap {
        private final Object[] byCharacter = new Object[26];

        public int addToSum(byte[] text, ResultStore store, int first, int sum) {
            boolean[] usedChars = new boolean[26];
            int usedCharCount = 0;
            Object[] currentNodes = byCharacter;

            for (int i = first; i < text.length; i++) {
                final byte currentCharacter = text[i];
                if (!usedChars[currentCharacter]) {
                    usedChars[currentCharacter] = true;
                    usedCharCount++;
                }
                if (currentNodes[currentCharacter] == null) {
                    currentNodes[currentCharacter] = new Object[26];
                    sum = getUpdatedSum(store, (i - first) + 1, usedCharCount, sum);
                }
                currentNodes = (Object[])currentNodes[currentCharacter];
            }
            return sum;
        }

        private int getUpdatedSum(ResultStore store, int length, int characterCount, int sum) {
            sum += store.get(length, characterCount);
            if (sum > MODULO_CUTOFF) {
                sum -= MODULO_CUTOFF;
            }
            return sum;
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

        public int get(int inputLength, int letters) {
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
        int inputLines;

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

    private int getSumForSingleCase(byte[] singleCase, ResultStore store) {
        int currentSum = 0;
        TextMap usedSubstrings = new TextMap();

        for (int i = 0; i < singleCase.length; i++) {
            currentSum = usedSubstrings.addToSum(singleCase, store, i, currentSum);
        }

        return currentSum;
    }

    public static void main(String[] args) {
        SuperFunctionalStrings superFunctionalStrings = new SuperFunctionalStrings();
        superFunctionalStrings.solveProblem().forEach(System.out::println);
    }

}