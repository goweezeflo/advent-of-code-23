package com.goweezeflo.aoc23;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static List<String> rows = new ArrayList<>();
    private static int previousRow = 0;
    private static int currentRow = 0;
    private static int nextRow = 1;
    private static boolean hasPreviousRow = false;
    private static boolean hasNextRow = true;
    private static final Pattern partSymbolPattern = Pattern.compile("\\*");
    private static final Pattern partNumberPattern = Pattern.compile("[0-9]");

    public static void main(String[] args) {
        String input = loadInput("input.txt");
        rows = splitIntoRows(input);
        int total = calculateRowsTotal();
        System.out.printf("Total: %d", total);
    }

    private static String loadInput(String fileName) {
        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            URL inputPath = classLoader.getResource("input" + File.separator + fileName);
            File inputFile = new File(Objects.requireNonNull(inputPath).toURI());
            return FileUtils.readFileToString(inputFile, "UTF-8");
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> splitIntoRows(String input) {
        String[] inputRows;
        inputRows = input.split("\\r\\n");
        if (inputRows.length == 1) {
            inputRows = input.split("\\n");
        }
        List<String> rows = new ArrayList<>();
        for (String row : inputRows) {
            if (!row.isEmpty()) {
                rows.add(row);
            }
        }
        return rows;
    }

    private static int calculateRowsTotal() {
        int total = 0;
        for (String row : rows) {
            detectFrame();
            System.out.print(currentRow + " " + row + " "); // Debug
            total += getFrameSum(currentRow);
            System.out.printf("{%d,%d,%d} %b, %b (%d)\n", previousRow, currentRow, nextRow, hasPreviousRow, hasNextRow, total); // Debug
            moveFrame();
        }
        return total;
    }

    private static void detectFrame() {
        if (!hasPreviousRow && currentRow >= 1) {
            hasPreviousRow = true;
        }
        if (hasNextRow && currentRow >= rows.size() - 1) {
            hasNextRow = false;
        }
    }

    private static void moveFrame() {
        currentRow++;
        if (hasPreviousRow) {
            previousRow++;
        }
        if (hasNextRow) {
            nextRow++;
        }
    }

    private static int getFrameSum(int rowIndex) {
        int frameSum = 0;
        String row = rows.get(rowIndex);
        for (int i = 0; i < row.length(); i++) {
            List<Integer> allNumbers = new ArrayList<>();
            char currentChar = row.charAt(i);
            if (isSymbol(currentChar)) {
                if (hasPreviousRow) {
                    allNumbers.addAll(getRowNumbers(previousRow, i));
                }
                allNumbers.addAll(getRowNumbers(currentRow, i));
                if (hasNextRow) {
                    allNumbers.addAll(getRowNumbers(nextRow, i));
                }
                if (allNumbers.size() == 2) {
                    int gearRatio = allNumbers.get(0) * allNumbers.get(1);
                    frameSum += gearRatio;
                }
            }
        }
        return frameSum;
    }

    private static boolean isSymbol(char currentChar) {
        Matcher charMatcher = partSymbolPattern.matcher(String.valueOf(currentChar));
        return charMatcher.find();
    }

    private static List<Integer> getRowNumbers(int rowIndex, int symbolIndex) {
        String leftNumber = getNumber(rowIndex, symbolIndex, "left");
        String middleNumber = getNumber(rowIndex, symbolIndex, "middle");
        String rightNumber = getNumber(rowIndex, symbolIndex, "right");
        String rowNumbers = leftNumber + middleNumber + rightNumber;
        String[] numbers = rowNumbers.split("x");
        List<Integer> allNumbers = new ArrayList<>();
        for (String number : numbers) {
            if (!number.isEmpty()) {
                allNumbers.add(Integer.parseInt(number));
            }
        }
        return allNumbers;
    }

    private static String getNumber(int rowIndex, int i, String direction) {
        String row = rows.get(rowIndex);
        StringBuilder numberBuilder = new StringBuilder();
        switch (direction) {
            case "left":
                int startIndex = getStartIndex(row, i-1);
                for (int j = startIndex; j <= i - 1; j++) {
                    numberBuilder.append(row.charAt(j));
                }
                String leftNumber = String.valueOf(numberBuilder);
                if (!leftNumber.isEmpty()) {
                    return leftNumber;
                }
                return "";
            case "middle":
                String number = String.valueOf(row.charAt(i));
                if (partNumberPattern.matcher(number).find()) {
                    return number;
                }
                return "x";
            case "right":
                int endIndex = getEndIndex(row, i+1);
                for (int j = i + 1; j <= endIndex; j++) {
                    numberBuilder.append(row.charAt(j));
                }
                String rightNumber = String.valueOf(numberBuilder);
                if (!rightNumber.isEmpty()) {
                    return rightNumber;
                }
                return "";
        }
        return "";
    }

    private static int getEndIndex(String row, int i) {
        if (i >= row.length() - 1) {
            return row.length() - 1;
        }
        int endIndex = i;
        while (endIndex <= row.length() - 1) {
            char character = row.charAt(endIndex);
            if (!partNumberPattern.matcher(String.valueOf(character)).find()) {
                return endIndex - 1;
            }
            endIndex++;
        }
        return row.length() - 1;
    }

    private static int getStartIndex(String row, int i) {
        if (i <= 0) {
            return 0;
        }
        int startIndex = i;
        while (startIndex >= 0) {
            char character = row.charAt(startIndex);
            if (!partNumberPattern.matcher(String.valueOf(character)).find()) {
                return startIndex + 1;
            }
            startIndex--;
        }
        return 0;
    }
}
