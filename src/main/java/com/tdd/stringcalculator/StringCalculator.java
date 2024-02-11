package com.tdd.stringcalculator;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
    private int addCalledCount = 0;
    private static final String DEFAULT_DELIMITER = ",";

    public int Add(String numbers) {
        addCalledCount++;
        String[] numStrings = splitNumbers(numbers, DEFAULT_DELIMITER);
        List<Integer> nums = parseAndValidateNumbers(numStrings);
        return sumWithoutNegatives(nums);
    }

    private String[] splitNumbers(String numbers, String defaultDelimiter) {
        if (numbers.isEmpty()) {
            return new String[]{};
        }

        String delimiter = defaultDelimiter;

        // Check for custom delimiter syntax
        if (numbers.startsWith("//")) {
            int delimiterEndIndex = numbers.indexOf("\n");
            if (delimiterEndIndex != -1) {
                delimiter = numbers.substring(2, delimiterEndIndex);
                numbers = numbers.substring(delimiterEndIndex + 1);
            }
        }

        // Replace new lines after delimiter with an empty string
        numbers = numbers.replaceAll(Pattern.quote(delimiter) + "\n", delimiter);

        // Replace new lines between numbers with the default delimiter
        numbers = numbers.replaceAll("\n", delimiter);

        // Split by delimiter
        String[] numStrings = numbers.split(Pattern.quote(delimiter));

        // Check for invalid input like "1,\n"
        if (Arrays.stream(numStrings).anyMatch(String::isEmpty)) {
            throw new IllegalArgumentException("Invalid input");
        }

        return numStrings;
    }
    private List<Integer> parseAndValidateNumbers(String[] numStrings) {
        return Arrays.stream(numStrings)
                .map(numStr -> {
                    try {
                        return Integer.parseInt(numStr);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid number: " + numStr);
                    }
                })
                .filter(n -> n <= 1000)  // Ignore numbers > 1000
                .collect(Collectors.toList());
    }

    private int sumWithoutNegatives(List<Integer> numbers) {
        if (numbers.stream().anyMatch(n -> n < 0)) {
            throw new IllegalArgumentException("negatives not allowed: " + numbers.stream().filter(n -> n < 0).collect(Collectors.toList()));
        }
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int GetCalledCount() {
        return addCalledCount;
    }
}
