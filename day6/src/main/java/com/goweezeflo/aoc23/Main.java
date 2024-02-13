package com.goweezeflo.aoc23;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import org.apache.commons.io.FileUtils;

public class Main {

    private static List<String> rows = new ArrayList<>();

    public static void main(String[] args) throws URISyntaxException {
        String input = loadInput("input.txt");
        rows = splitIntoRows(input);
        part1();
    }

    private static void part1() {
        for (String row : rows) {
            System.out.println(row);
        }
        
    }

    private static void part2() {
        System.out.println("Part 2");
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
