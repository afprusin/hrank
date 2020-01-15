package com.afprusin.hrank.algorithms.strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Decided to make index ranges [inclusive, exclusive], vs [inclusive, inclusive].  Have to update all of the boundary logic

public class SuperFunctionalStringsSuffixTree {

    private static final long MODULO_CUTOFF = 1000000007;
    private static final int CHARACTERS = 26;

    // TODO: Will probably have to convert this to single-strip memory allocation
    private class SuffixNode {
        private short[] lowIndex = new short[CHARACTERS];
        private short[] highIndex = new short[CHARACTERS];
        private SuffixNode[] pointers = new SuffixNode[CHARACTERS];
    }

    private class SuffixTree {
        SuffixNode root = new SuffixNode();

        /**
         * @param input the input byte array representing an a-z string
         * @param lowIndex the low index in the byte array, inclusive
         * @param highIndex the high index in the byte array, exclusive
         */
        public void addString(byte[] input, short lowIndex, short highIndex) {

        }

        // TODO: Might need to convert this to an iterative method D:
        public void addString(byte[] input, short lowIndex, short highIndex, SuffixNode currentNode) {
            byte currentCharacter = input[lowIndex];
            short existingLow = currentNode.lowIndex[currentCharacter];
            short existingHigh = currentNode.highIndex[currentCharacter];
            short currentLow = lowIndex;
            short currentHigh = highIndex;
            short baseLow = lowIndex;

            // Advance past matching characters; these are duplicated substrings - no maths needed
            while (input[existingLow] == input[currentLow] &&
                    currentLow < currentHigh && existingLow < existingHigh) {
                existingLow++;
                currentLow++;
                // If we've reached the end of this node's representation, move to next node if available
                if (existingLow >= existingHigh &&
                        currentLow < currentHigh &&
                        currentNode.pointers[currentCharacter] != null) {
                    currentNode = currentNode.pointers[currentCharacter];
                    currentCharacter = input[currentLow];
                    baseLow = currentLow;
                    existingLow = currentNode.lowIndex[currentCharacter];
                    existingHigh = currentNode.highIndex[currentCharacter];
                }
            }
            // If this substring is longer than what is indexed, and no additional node is available,
            //  then extend the current string (replace old bounds with new substring bounds
            if (currentLow < currentHigh &&
                    existingLow >= existingHigh &&
                    currentNode.pointers[currentCharacter] != null) {
                currentNode.lowIndex[currentCharacter] = baseLow;
                currentNode.highIndex[currentCharacter] = currentHigh;
            }
            // If this substring is shorter than what is indexed, and not equal, split the node

            // If the substring diverges from what has been seen, insert a node between this and the next node
            // TODO: What to do if we've already reached the limit of the other substring?
            if (lowIndex <= highIndex) {

                SuffixNode insertNode = new SuffixNode();

                currentNode.pointers[currentCharacter] = insertNode;
                currentNode.highIndex[currentCharacter] = lowIndex;
            }

            }
            for (int i = lowIndex; i <= highIndex; i++) {

            }

        }
    }


// TODO: Maths here, wonderful maths

//        private int getUpdatedSum(ResultStore store, int length, int characterCount, int sum) {
//            sum += store.get(length, characterCount);
//            if (sum > MODULO_CUTOFF) {
//                sum -= MODULO_CUTOFF;
//            }
//            return sum;
//        }
//    }

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
        TextMap usedSubstrings = new TextMap();
        return inputCases.stream()
                .map(currentCase -> getSumForSingleCase(currentCase, results, usedSubstrings))
                .collect(Collectors.toList());
    }

    private int getSumForSingleCase(byte[] singleCase, ResultStore store, TextMap usedSubstrings) {
        int currentSum = 0;

        for (int i = 0; i < singleCase.length; i++) {
            currentSum = usedSubstrings.addToSum(singleCase, store, i, currentSum);
        }

        usedSubstrings.reset();
        System.out.println("\n\n DONE \n\n");

        return currentSum;
    }

    public static void main(String[] args) {
        SuperFunctionalStringsSuffixTree superFunctionalStrings = new SuperFunctionalStringsSuffixTree();
        superFunctionalStrings.solveProblem().forEach(System.out::println);
    }

}