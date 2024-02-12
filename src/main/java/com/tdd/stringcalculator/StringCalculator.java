package com.tdd.stringcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculator {
    private static int addMethodCallCount = 0;

    public static int add(String numbers) {
        addMethodCallCount++;
        if (numbers.isEmpty()) {
            return 0;
        }
        String delimiterRegex = ",|\n";

        if (numbers.startsWith("//")) {
            Matcher matcher = Pattern.compile("//\\[?(.*?)]?\\n(.*)").matcher(numbers);
            if (matcher.matches()) {
                String customDelimiterPart = matcher.group(1);
                String numbersPart = matcher.group(2);

                String[] customDelimiters = parseCustomDelimiters(customDelimiterPart);
                delimiterRegex = Stream.concat(Arrays.stream(customDelimiters), Stream.of(",", "\n"))
                        .map(Pattern::quote)
                        .collect(Collectors.joining("|"));
                numbers = numbersPart;
            }
        }

        String[] stringArrayNumbers = splitNumbers(numbers, delimiterRegex);
        List<Integer> integerListNumbers=parseAndValidateNumbers(stringArrayNumbers);
        return sumWithoutNegatives(integerListNumbers);
    }

    private static String[] splitNumbers(String numbers, String delimiterRegex) {
        return numbers.split(delimiterRegex);
    }

    private static String[] parseCustomDelimiters(String customDelimiterPart) {
        return customDelimiterPart.split("]\\[");
    }
    private static List<Integer> parseAndValidateNumbers(String[] numStrings) {
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
    private static int sumWithoutNegatives(List<Integer> numbers) {
        if (numbers.stream().anyMatch(n -> n < 0)) {
            throw new IllegalArgumentException("negatives not allowed: " + numbers.stream().filter(n -> n < 0).toList());
        }
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static int getCalledCount() {
        return addMethodCallCount;
    }
}
