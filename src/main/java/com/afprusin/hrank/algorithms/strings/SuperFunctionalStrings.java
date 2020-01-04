package com.afprusin.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SuperFunctionalStrings {

    private enum Letter {
        A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z;

        private static final Letter[] values = Letter.values();

        public static Letter get(int characterValue) {
            return values[characterValue - 97];
        }
    }

    private static class TokenTraversal {
        private Map<Letter, TokenTraversal> traversal = new EnumMap<>(Letter.class);

        public boolean contains(Letter toPut) {
            return traversal.containsKey(toPut);
        }

        public TokenTraversal put(Letter toPut) {
            if (!traversal.containsKey(toPut)) {
                TokenTraversal nextNode = new TokenTraversal();
                traversal.put(toPut, nextNode);
                return nextNode;
            } else {
                return traversal.get(toPut);
            }
        }
    }

    private static class ResultStore {
        final BigInteger bigModulo = BigInteger.valueOf(MODULO_CUTOFF);
        int[][] results;

        public ResultStore(int maxInputLength) {
            System.out.println(maxInputLength);
            this.results = new int[maxInputLength][26];
            for (int[] row : results) {
                Arrays.fill(row, -1);
            }

            for (int i = 1; i <= maxInputLength; i++) {
                long currentValue = i;
                for (int j = 1; j <= 12; j++)  {
                    if (j > 1) {
                        currentValue *= i;
                    }
                    results[i - 1][j - 1] = (int)currentValue % MODULO_CUTOFF;
                }
            }

        }

        public int getFunctionResult(int inputLength, int letters) {
            final int storedValue = results[inputLength - 1][letters - 1];

            if (storedValue == -1) {
                final int calculatedValue =
                        BigInteger.valueOf(inputLength)
                                .pow(letters)
                                .mod(bigModulo)
                                .intValueExact();
                results[inputLength - 1][letters - 1] = calculatedValue;
                return calculatedValue;
            } else {
                return storedValue;
            }
        }
    }

    private static final int MODULO_CUTOFF = 1000000007;

    public List<Integer> solveProblem() {
        List<String> problemInput = getInput();
        final int maxStringLength = problemInput.stream()
                .map(String::length)
                .max(Integer::compareTo)
                .orElseThrow(IllegalStateException::new);
        ResultStore results = new ResultStore(maxStringLength);

        return getFunctionSum(problemInput, results);
    }

    private List<String> getInput() {
        List<String> input = new ArrayList<>();
        int inputLines = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            // Skip metadata line
            inputLines = Integer.parseInt(reader.readLine());

            for (int i = 0; i < inputLines; i++) {
                input.add(reader.readLine());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read input.", e);
        }

        return input;
    }

    private List<Integer> getFunctionSum(List<String> inputCases, ResultStore results) {
        return inputCases.stream()
                .map(currentCase -> getSumForSingleCase(currentCase, results))
                .collect(Collectors.toList());
    }

    private int getSumForSingleCase(String singleCase, ResultStore results) {
        int currentSum = 0;
        TokenTraversal traversalRoot = new TokenTraversal();
        Set<Letter> usedLetters = EnumSet.noneOf(Letter.class);
        int usedCharacterCount = 0;

        for (int i = 0; i < singleCase.length(); i++) {
            TokenTraversal currentTraversal = traversalRoot;
            usedLetters.clear();
            usedCharacterCount = 0;

            for (int j = i; j < singleCase.length(); j++) {
                final char currentChar = singleCase.charAt(j);
                final Letter currentLetter = Letter.get(currentChar);

                if (usedLetters.add(currentLetter)) {
                    usedCharacterCount++;
                }
                if (!currentTraversal.contains(currentLetter)) {
                    final int length = j - i + 1;
                    currentSum += results.getFunctionResult(length, usedCharacterCount);

                    while (currentSum >= MODULO_CUTOFF) {
                        currentSum -= MODULO_CUTOFF;
                    }
                }

                currentTraversal = currentTraversal.put(currentLetter);
            }
        }

        return currentSum;
    }

    public static void main(String[] args) {
        SuperFunctionalStrings superFunctionalStrings = new SuperFunctionalStrings();
        superFunctionalStrings.solveProblem().forEach(System.out::println);
    }

}
