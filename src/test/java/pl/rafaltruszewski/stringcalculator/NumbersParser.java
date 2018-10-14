package pl.rafaltruszewski.stringcalculator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NumbersParser {
    private static final String NEW_LINE = "\n";
    private static final String DEFAULT_DELIMITERS = "[," + NEW_LINE + "]";
    private static final Pattern MULTI_DELIMITER_REGEX = Pattern.compile("^//(\\[.+\\])+\\n");
    private static final Pattern SINGLE_DELIMITER_REGEX = Pattern.compile("//(.)\n?");

    public NumbersParser() {
    }

    public List<String> parse(String numbers) {
        if (hasDelimiters(numbers)) {
            List<String> delimiters = getDelimiters(numbers);
            String numbersWithoutDelimiter = removeDelimiters(delimiters, numbers);
            String delimitersAsRegex = prepareRegex(delimiters);
            return Arrays.asList(numbersWithoutDelimiter.split(delimitersAsRegex));
        } else {
            return Arrays.asList(numbers.split(DEFAULT_DELIMITERS));
        }
    }

    private String prepareRegex(List<String> delimiters) {
        return delimiters.stream()
                .collect(Collectors.joining("|"));
    }

    private String removeDelimiters(List<String> delimiters, String numbers) {
        String numbersWithoutDelimiter;
        if (numbers.startsWith("//[")) {
            String collected = delimiters.stream()
                    .map(e -> "[" + e + "]")
                    .collect(Collectors.joining());
            numbersWithoutDelimiter = numbers.substring(3 + collected.length());

        } else {
            String collected = delimiters.stream()
                    .collect(Collectors.joining());
            numbersWithoutDelimiter = numbers.substring(2 + collected.length());

        }

        if (numbersWithoutDelimiter.startsWith(NEW_LINE)) {
            numbersWithoutDelimiter = numbersWithoutDelimiter.substring(1);
        }

        return numbersWithoutDelimiter;
    }

    private List<String> getDelimiters(String numbers) {

        Matcher multiDelimiterMatcher = MULTI_DELIMITER_REGEX.matcher(numbers);
        if (multiDelimiterMatcher.find()) {
            String manyDelim = multiDelimiterMatcher.group(1);

            Pattern delimExtractor = Pattern.compile("(\\[.+?\\])");
            Matcher delimMatcher = delimExtractor.matcher(manyDelim);

            List<String> result = new LinkedList<>();
            while (delimMatcher.find()){
                String group = delimMatcher.group();
                result.add(group.substring(1, group.length()-1));
            }
            return result;
        }

        Matcher singleDelimiterMatcher = SINGLE_DELIMITER_REGEX.matcher(numbers);
        if (singleDelimiterMatcher.find()) {
            return Arrays.asList(singleDelimiterMatcher.group(1));
        }

        throw new RuntimeException("Numbers " + numbers + " have invalid delimiter");
    }

    private boolean hasDelimiters(String numbers) {
        return numbers.startsWith("//");
    }

}
