package pl.rafaltruszewski.stringcalculator;

import java.util.Arrays;
import java.util.List;

class StringCalculator {

    public static final String DEFAULT_DELIMITER = "[,\n]";

    public int add(String numbers) {
        if(numbers.isEmpty()) return 0;

        List<String> numbersAsString = splitNumbers(numbers);

        return numbersAsString.stream()
                .mapToInt(Integer::valueOf)
                .sum();
    }

    private List<String> splitNumbers(String numbers) {
        String numbersWithoutDelimiter = numbers;

        String delimiter = DEFAULT_DELIMITER;
        if (hasDelimiter(numbers)){
            delimiter = extractDelimiter(numbers);
            numbersWithoutDelimiter = removeDelimiter(delimiter, numbers);
        }

        return Arrays.asList(numbersWithoutDelimiter.split(delimiter));
    }

    private String removeDelimiter(String delimiter, String numbers) {
        String numbersWithoutDelimiter = numbers.substring(2 + delimiter.length());
        if(numbersWithoutDelimiter.startsWith("\n")){
          numbersWithoutDelimiter = numbersWithoutDelimiter.substring(1);
        }

        return numbersWithoutDelimiter;
    }

    private String extractDelimiter(String value) {
        return value.substring(2, 3);
    }

    private boolean hasDelimiter(String value) {
        return value.startsWith("//");
    }
}
