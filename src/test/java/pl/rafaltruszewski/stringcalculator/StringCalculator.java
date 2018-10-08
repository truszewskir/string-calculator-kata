package pl.rafaltruszewski.stringcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class StringCalculator {

    private static final String DEFAULT_DELIMITER = "[,\n]";

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
