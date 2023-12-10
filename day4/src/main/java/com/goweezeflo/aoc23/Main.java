package com.goweezeflo.aoc23;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static List<String> rows = new ArrayList<>();

    public static void main(String[] args) throws URISyntaxException {
        String input = loadInput("input2.txt");
        rows = splitIntoRows(input);
        day1();
    }

    private static void day1() {
        int totalWorth = 0;
        for (String row : rows) {
            List<Integer> winningNumbers = getWinningNumbers(row);
            List<Integer> personalNumbers = getPersonalNumbers(row);
            List<Integer> matches = getMatches(winningNumbers, personalNumbers);
            System.out.println(winningNumbers + " X " + personalNumbers + " -> " + matches); // Debug
            if (!matches.isEmpty()) {
                int multiplier = 1;
                for (int i = 0; i < matches.size(); i++) {
                    if (i > 0) {
                        multiplier *= 2;
                    }
                }
                totalWorth += multiplier;
            }
        }
        System.out.println("Total worth: " + totalWorth);
    }

    private static List<Integer> getMatches(List<Integer> winningNumbers, List<Integer> personalNumbers) {
        return winningNumbers.stream()
                .distinct()
                .filter(personalNumbers::contains).collect(Collectors.toList());
    }

    private static List<Integer> getWinningNumbers(String row) {
        String winningNumbersRow = row.substring(row.indexOf(":") + 2, row.indexOf("|") - 1);
        return getNumbers(winningNumbersRow);
    }

    private static List<Integer> getPersonalNumbers(String row) {
        String personalNumbersRow = row.substring(row.indexOf("|") + 2);
        return getNumbers(personalNumbersRow);
    }

    private static List<Integer> getNumbers(String row) {
        String[] numbersArray = row.split("\\s+");
        List<Integer> numbers = new ArrayList<>();
        for (String number : numbersArray) {
            if (!number.isEmpty()) {
                numbers.add(Integer.parseInt(number));
            }
        }
        return numbers;
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
}
