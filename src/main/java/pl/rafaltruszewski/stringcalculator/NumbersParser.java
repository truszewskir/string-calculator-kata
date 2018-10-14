package pl.rafaltruszewski.stringcalculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class NumbersParser {
    private static final String DEFAULT_DELIMITER = "[," + "\n" + "]";
    private static final Pattern NUMBERS_WITH_MULTI_DELIMITER = Pattern.compile("^//(\\[.+\\])\n(.+)$");
    private static final Pattern NUMBERS_WITH_SINGLE_DELIMITER = Pattern.compile("^//(.)\n?(.+)$");
    private static final Pattern SINGLE_DELIMITERS = Pattern.compile("(\\[.+?\\])");

    public NumbersParser() {
    }

    public List<Integer> parse(String numbers) {
        if (hasDelimiters(numbers)) {
            SplitArguments splitArguments = findDelimiters(numbers);
            return mapToInt(split(splitArguments));
        } else {
            return mapToInt(Arrays.asList(numbers.split(DEFAULT_DELIMITER)));
        }
    }

    private List<Integer> mapToInt(List<String> numbersAsString) {
        return numbersAsString.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private List<String> split(SplitArguments splitArguments){
        String delimitersForSplit = splitArguments.delimitersAsRegex();
        String numbersWithoutDelimiters = splitArguments.getNumbers();
        return Arrays.asList(numbersWithoutDelimiters.split(delimitersForSplit));
    }

    private SplitArguments findDelimiters(String numbersWithDelimiters){
        Matcher multiDelimiterMatcher = NUMBERS_WITH_MULTI_DELIMITER.matcher(numbersWithDelimiters);
        if (multiDelimiterMatcher.find()) {
            String manyDelimiters = multiDelimiterMatcher.group(1);

            Matcher singleDelimitersMatcher = SINGLE_DELIMITERS.matcher(manyDelimiters);

            List<String> delimiters = new LinkedList<>();
            while (singleDelimitersMatcher.find()){
                String group = singleDelimitersMatcher.group();
                delimiters.add(group.substring(1, group.length()-1));
            }

            return new SplitArguments(delimiters, multiDelimiterMatcher.group(2));
        }

        Matcher singleDelimiterMatcher = NUMBERS_WITH_SINGLE_DELIMITER.matcher(numbersWithDelimiters);
        if (singleDelimiterMatcher.find()) {
            String delimiter = singleDelimiterMatcher.group(1);
            String numbersWithoutDelimiter = singleDelimiterMatcher.group(2);

            return new SplitArguments(Collections.singletonList(delimiter), numbersWithoutDelimiter);
        }

        throw new RuntimeException("Numbers " + numbersWithDelimiters + " have invalid delimiter");
    }

    private boolean hasDelimiters(String numbers) {
        return numbers.startsWith("//");
    }

    private static class SplitArguments {
        private final List<String> delimiters;
        private final String numbers;

        private SplitArguments(List<String> delimiters, String numbers) {
            this.delimiters = delimiters;
            this.numbers = numbers;
        }

        String getNumbers() {
            return numbers;
        }

        String delimitersAsRegex() {
            return String.join("|", delimiters);
        }
    }
}
