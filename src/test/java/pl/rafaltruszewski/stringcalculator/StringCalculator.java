package pl.rafaltruszewski.stringcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class StringCalculator {

    private static final String NEW_LINE = "\n";
    private static final String DEFAULT_DELIMITERS = "[," + NEW_LINE + "]";
    public static final Pattern MULTI_DELIMITER_REGEX = Pattern.compile("^//\\[(.+)\\]\n");

    public int add(String numbers) throws NegativesNotAllowed {
        if(numbers.isEmpty()) return 0;

        List<String> numbersAsString = splitNumbers(numbers);
        List<Integer> numbersAsInt = mapToInt(numbersAsString);
        checkNegatives(numbersAsInt);

        return sum(numbersAsInt);
    }

    private int sum(List<Integer> numbersAsInt) {
        return numbersAsInt.stream()
                .filter(e -> e <= 1000)
                .mapToInt(Integer::valueOf)
                .sum();
    }

    private void checkNegatives(List<Integer> numbersAsInt) {
        List<Integer> negatives = numbersAsInt.stream()
                .filter(e -> e < 0)
                .collect(Collectors.toList());


        if(negatives.isEmpty()){
            return;
        }

        StringBuilder message = new StringBuilder();
        message.append(negatives.get(0));

        for (int i = 1; i < negatives.size(); i++){
            message.append(",")
                    .append(negatives.get(i));
        }

        throw new NegativesNotAllowed(message.toString());
    }

    private List<Integer> mapToInt(List<String> numbersAsString) {
        return numbersAsString.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private List<String> splitNumbers(String numbers) {
        if (hasDelimiter(numbers)){
            String delimiter = getDelimiter(numbers);
            String numbersWithoutDelimiter = removeDelimiter(delimiter, numbers);
            return Arrays.asList(numbersWithoutDelimiter.split(delimiter));
        }
        else {
            return Arrays.asList(numbers.split(DEFAULT_DELIMITERS));
        }
    }

    private String removeDelimiter(String delimiter, String numbers) {
        String numbersWithoutDelimiter = numbers;
        if (numbers.contains("[" + delimiter + "]")){
            numbersWithoutDelimiter = numbers.substring(4 + delimiter.length());
        }
        else {
            numbersWithoutDelimiter = numbers.substring(2 + delimiter.length());
        }

        if(numbersWithoutDelimiter.startsWith(NEW_LINE)){
          numbersWithoutDelimiter = numbersWithoutDelimiter.substring(1);
        }

        return numbersWithoutDelimiter;
    }

    private String getDelimiter(String numbers) {

        Matcher multiDelimiterMatcher = MULTI_DELIMITER_REGEX.matcher(numbers);
        if (multiDelimiterMatcher.find()){
            return multiDelimiterMatcher.group(1);
        }

        Pattern singeDelimiterRegex = Pattern.compile("//(.)\n?");
        Matcher singleDelimiterMatcher = singeDelimiterRegex.matcher(numbers);
        if(singleDelimiterMatcher.find()){
            return singleDelimiterMatcher.group(1);
        }

        throw new RuntimeException("Numbers " + numbers + " have invalid delimiter");
    }

    private boolean hasDelimiter(String numbers) {
        return numbers.startsWith("//");
    }
}
