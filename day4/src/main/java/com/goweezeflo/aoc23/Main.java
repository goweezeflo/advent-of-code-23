package com.goweezeflo.aoc23;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        ClassLoader classLoader = Main.class.getClassLoader();
        URL inputPath = classLoader.getResource("input" + File.separator + "input.txt");
        File inputFile = new File(Objects.requireNonNull(inputPath).toURI());
        try {
            String inputData = FileUtils.readFileToString(inputFile, "UTF-8");
            String[] lines = inputData.split("\n");
            for (String line : lines) { // Process lines
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
