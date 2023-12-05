package com.goweezeflo.aoc23;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static int redLimit = 12;
    private static int greenLimit = 13;
    private static int blueLimit = 14;

    public static void main(String[] args) throws URISyntaxException {
        ClassLoader classLoader = Main.class.getClassLoader();
        URL inputPath = classLoader.getResource("input" + File.separator + "input.txt");
        File inputFile = new File(Objects.requireNonNull(inputPath).toURI());
        try {
            String inputData = FileUtils.readFileToString(inputFile, "UTF-8");
            String[] lines = inputData.split("\n");
            Map<Integer, Map<String, Integer>> games = new HashMap<>();
            for (String line : lines) { // Process lines
                System.out.println(line);
                int gameId = Integer.parseInt(line.substring(line.indexOf(" ") + 1, line.indexOf(":")));
                int red = getBallCount("red", line);
                int green = getBallCount("green", line);
                int blue = getBallCount("blue", line);
                Map<String, Integer> ballCount = new HashMap<>();
                ballCount.put("red", red);
                ballCount.put("green", green);
                ballCount.put("blue", blue);
                games.put(gameId, ballCount);
            }
            int gameIdTotal = 0;
            for (int gameId : games.keySet()) {
                Map<String, Integer> game = games.get(gameId);
                gameIdTotal += game.get("red") * game.get("green") * game.get("blue");
                System.out.println(gameId + " -> " + games.get(gameId) + " Total (" + gameIdTotal + ")");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getBallCount(String colorKey, String line) {
        Pattern colorPattern = Pattern.compile("\\d+ " + colorKey);
        Matcher colorMatcher = colorPattern.matcher(line);
        colorMatcher.find();
        int ballCount = getBallCount(colorMatcher);
        while (colorMatcher.find()) {
            int newBallCount = getBallCount(colorMatcher);
            if (newBallCount > ballCount) {
                ballCount = newBallCount;
            }
        }
        return ballCount;
    }

    private static int getBallCount(Matcher colorMatcher) {
        String color = colorMatcher.group();
        return Integer.parseInt(color.substring(0, color.indexOf(" ")));
    }
}
