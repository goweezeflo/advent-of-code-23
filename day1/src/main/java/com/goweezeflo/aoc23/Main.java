package com.goweezeflo.aoc23;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        ClassLoader classLoader = Main.class.getClassLoader();
        URL inputPath = classLoader.getResource("input" + File.separator + "input.txt");
        File inputFile = new File(Objects.requireNonNull(inputPath).toURI());
        try {
            String inputData = FileUtils.readFileToString(inputFile, "UTF-8");
            String[] lines = inputData.split("\n");
            int total = 0;
            for (String line : lines) {
                String numbers = "(one|two|three|four|five|six|seven|eight|nine|[1-9])";
                int firstNumber = getNumberWithRegex(line, numbers);
                int lastNumber = getNumberWithRegex(line, ".*" + numbers);
                int number;
                if (lastNumber > 0) {
                    number = firstNumber * 10 + lastNumber;
                } else {
                    number = firstNumber * 10 + firstNumber;
                }
                total += number;
                System.out.println(line + " -> " + number + " (total " + total + ")");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getNumberWithRegex(String line, String numbers) {
        Pattern firstNumPattern = Pattern.compile(numbers);
        Matcher firstNumMatcher = firstNumPattern.matcher(line);
        if (firstNumMatcher.find()) {
            return getNumber(firstNumMatcher.group(1));
        }
        return 0;
    }

    private static int getNumber(String number) {
        return switch (number) {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> Integer.parseInt(number);
        };
    }
}
